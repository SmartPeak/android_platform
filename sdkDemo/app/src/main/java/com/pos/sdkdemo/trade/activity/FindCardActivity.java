package com.pos.sdkdemo.trade.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.util.Log;

import com.basewin.define.CardType;
import com.basewin.define.InputPBOCInitData;
import com.basewin.define.OutputCardInfoData;
import com.basewin.define.OutputMagCardInfo;
import com.basewin.define.OutputQPBOCResult;
import com.basewin.define.PBOCErrorCode;
import com.basewin.define.PBOCOption;
import com.basewin.define.PBOCTransactionResult;
import com.basewin.services.ServiceManager;
import com.bw.jni.message.KernelDataRecord;
import com.pos.sdkdemo.R;
import com.pos.sdkdemo.pboc.pinpad.StringHelper;
import com.pos.sdkdemo.trade.Config;
import com.pos.sdkdemo.trade.GlobleData;
import com.pos.sdkdemo.trade.interfac.OnConfirmCardInfo;
import com.pos.sdkdemo.trade.interfac.OnError;
import com.pos.sdkdemo.trade.interfac.OnFindingCard;
import com.pos.sdkdemo.trade.interfac.OnRequestInputPIN;
import com.pos.sdkdemo.trade.interfac.OnRequestSinature;
import com.pos.sdkdemo.trade.interfac.OnTransactionResult;
import com.pos.sdkdemo.utils.DialogUtil;
import com.pos.sdkdemo.utils.ToastUtils;

import static com.pos.sdkdemo.trade.Config.AMOUNT;

public class FindCardActivity extends Activity implements OnFindingCard,OnConfirmCardInfo,OnRequestSinature,OnRequestInputPIN,OnTransactionResult,OnError {
    public static final String TAG="feeling";
    public static final int CONFIRM_CARD_NO=0x00;
    public static final int REQUEST_PIN_PAD=0x01;
    public static final int REQUEST_SINATURE=0x02;
    private int cardtype = 0;
    String amount = "";
    String cardno="";
    private boolean ifOnlinePin=true;
    private boolean isFindCard = true;


    private Handler mHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_card);
        initView();
        initData();
        initHandler();
    }

    @Override
    protected void onStart() {
        super.onStart();
        initData();
        onStartTrade();
    }

    private void initView(){
        isFindCard = true;
    }

    private void initData(){
        amount = (String) GlobleData.getInstance().datas.get(Config.AMOUNT);
    }

    private void initHandler(){
        mHandler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if(msg.what==CONFIRM_CARD_NO){
                    DialogUtil.showDialog(FindCardActivity.this, "Plesea confirm card num",cardno, new DialogUtil.OnClickListener() {
                        @Override
                        public void onConfirm() {
                            if(cardtype==CardType.MAG_CARD){
                                isFindCard=false;
                                mHandler.sendEmptyMessage(REQUEST_PIN_PAD);
                            }else {
                                try {
                                    ServiceManager.getInstence().getPboc().confirmCardInfo();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        @Override
                        public void onCancel() {
                            finish();
                            try {
                                ServiceManager.getInstence().getPboc().stopTransfer();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }else if(msg.what==REQUEST_PIN_PAD){
                    Intent intent1 =new Intent(FindCardActivity.this,PinPadActivity.class);
                    intent1.putExtra("cardtype",cardtype);
                    intent1.putExtra("cardno",cardno);
                    intent1.putExtra("amount",amount);
                    intent1.putExtra("ifOnlinePin",ifOnlinePin);
                    startActivity(intent1);
                    finish();
                }else if(msg.what==REQUEST_SINATURE){
                    Intent intent1 =new Intent(FindCardActivity.this,SinatureActivity.class);
                    intent1.putExtra("cardtype",cardtype);
                    intent1.putExtra("cardno",cardno);
                    intent1.putExtra("amount",amount);
                    intent1.putExtra("ifOnlinePin",ifOnlinePin);
                    startActivity(intent1);
                    finish();
                }
            }
        };
    }

    private void onStartTrade(){
        try {
        Intent in = new Intent();
        //setting pboc process the amount(设置pboc流程的金额)
        in.putExtra(InputPBOCInitData.AMOUNT_FLAG, formatAmount(amount));
        in.putExtra(InputPBOCInitData.IS_PBOC_FORCE_ONLINE, false);
        //setting pboc process support the card type(设置pboc流程寻卡类型)
        in.putExtra(InputPBOCInitData.USE_DEVICE_FLAG, InputPBOCInitData.USE_MAG_CARD | InputPBOCInitData.USE_RF_CARD | InputPBOCInitData.USE_IC_CARD);
        //set trans timeout(设置寻卡超时时间)
        in.putExtra(InputPBOCInitData.TIMEOUT, 30);
        GlobleData.getInstance().setOnFindingCard(this);
        GlobleData.getInstance().setOnConfirmCardInfo(this);
        GlobleData.getInstance().setOnRequestInputPIN(this);
        GlobleData.getInstance().setOnRequestSinature(this);
        GlobleData.getInstance().setOnTransactionResult(this);
        GlobleData.getInstance().setOnError(this);
        ServiceManager.getInstence().getPboc().startTransfer(PBOCOption.ONLINE_PAY, in,GlobleData.getInstance().pboClistener);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * format amount
     *
     * @return
     */
    public long formatAmount(String amount) {
        return Long.parseLong(StringHelper.changeAmout(amount).replace(".", ""));
    }

    @Override
    public void findingCard(int cardType, Intent intent) {
        Log.e("feeling", "findingCard: " + cardType);
        switch (cardType) {
            case CardType.MAG_CARD:
                cardtype = CardType.MAG_CARD;
                // MAG card data entity class
                OutputMagCardInfo magCardInfo = new OutputMagCardInfo(intent);
                GlobleData.getInstance().card.magCardInfo = magCardInfo;
                cardno = magCardInfo.getPAN();
                mHandler.sendEmptyMessage(CONFIRM_CARD_NO);
                ifOnlinePin=true;
                break;
            case CardType.IC_CARD:
                cardtype = CardType.IC_CARD;
                Log.e("findingCard: ","PBOC CardType:IC card");
                break;
            case CardType.RF_CARD:
                cardtype = CardType.RF_CARD;
                Log.e("findingCard: ","PBOC CardType:RF card");
                break;
        }
        GlobleData.getInstance().card.setType(cardType);
    }

    @Override
    public void confirmCardInfo(Intent info) {
        isFindCard=false;
        OutputCardInfoData out = new OutputCardInfoData(info);
        GlobleData.getInstance().card.icCardInfo=out;
        cardno = out.getPAN();
        mHandler.sendEmptyMessage(CONFIRM_CARD_NO);
    }

    @Override
    public void requestInputPIN(boolean b, int i) {
        Log.e(TAG, "requestInputPIN: ");
        ifOnlinePin=b;
        mHandler.sendEmptyMessage(REQUEST_PIN_PAD);

    }

    @Override
    public void requestSinature() {
        Log.e(TAG, "requestSinature: ");
        mHandler.sendEmptyMessage(REQUEST_SINATURE);
    }

    @Override
    public void transactionResult(int result, Intent data) {
        Log.e(TAG, "transactionResult: ");
        if (result == PBOCTransactionResult.QPBOC_ARQC) {
            OutputQPBOCResult rf_data = new OutputQPBOCResult(data);
            GlobleData.getInstance().card.qicCard=rf_data;
            KernelDataRecord mDataRecord = new KernelDataRecord();
            mDataRecord.setPiccDataRecord(rf_data.getKernelData(), null);
            Intent intent =new Intent(FindCardActivity.this,PinPadActivity.class);
            startActivity(intent);
            finish();
        }else if(result == PBOCTransactionResult.TERMINATED){
            if(isFindCard){
                ToastUtils.shortShow(R.string.find_card_again);
            }else {
                ToastUtils.shortShow(R.string.trade_failure);
                try {
                    ServiceManager.getInstence().getPboc().stopTransfer();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                finish();
            }

        }
    }

    @Override
    public void onError(Intent intent) {
        if(isFindCard){
            ToastUtils.shortShow(R.string.find_card_again);
        }else {
            int code = intent.getIntExtra("code", 65282);
            String errorMsg = PBOCErrorCode.getErrorMsgByCode(code);
            ToastUtils.shortShow(errorMsg);
            finish();
            try {
                ServiceManager.getInstence().getPboc().stopTransfer();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }


}
