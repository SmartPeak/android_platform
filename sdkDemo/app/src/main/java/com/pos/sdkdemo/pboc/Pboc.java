package com.pos.sdkdemo.pboc;

import com.basewin.aidl.OnCheckCardListener;
import com.basewin.define.CardType;
import com.basewin.define.InputPBOCInitData;
import com.basewin.define.OutputMagCardInfo;
import com.basewin.define.PBOCOption;
import com.basewin.define.TransactionType;
import com.basewin.log.LogUtil;
import com.basewin.services.ServiceManager;
import com.basewin.utils.CUPParam;
import com.basewin.utils.LoadParamManage;
import com.basewin.zxing.utils.QRUtil;
import com.pos.sdk.emvcore.PosEmvParam;
import com.pos.sdkdemo.R;
import com.pos.sdkdemo.base.BaseActivity;
import com.pos.sdkdemo.database.TradeParams;
import com.pos.sdkdemo.interfaces.OnConfirmListener;
import com.pos.sdkdemo.interfaces.OnNumKeyListener;
import com.pos.sdkdemo.pboc.pinpad.StringHelper;
import com.pos.sdkdemo.pinpad.PinpadTestActivity;
import com.pos.sdkdemo.utils.GlobalData;
import com.pos.sdkdemo.utils.TimerCountTools;
import com.pos.sdkdemo.widgets.EnterDialog;
import com.pos.sdkdemo.widgets.KeyBoardView;

import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;


/**
 * PBOC模块(PBOC Model)
 */
public class Pboc extends BaseActivity implements View.OnClickListener {

    private LinearLayout load_params, onlineTrans;
    private TextView amount;
    private FrameLayout fl_keyboard;
    private KeyBoardView keyBoardView;

    @Override
    protected View onCreateView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.activity_pboc, null, false);
    }

    @Override
    protected void onInitView() {
        fl_keyboard = (FrameLayout) findViewById(R.id.fl_keyboard);
        fl_keyboard.removeAllViews();
        keyBoardView = new KeyBoardView(this);
        keyBoardView.getKeyBoardView();
        fl_keyboard.addView(keyBoardView.getKeyBoardView(), new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
        keyBoardView.setOnNumKeyListener(new KeyBoardListener());


        load_params = (LinearLayout) findViewById(R.id.load_params);
        onlineTrans = (LinearLayout) findViewById(R.id.onlineTrans);
        amount = (TextView) findViewById(R.id.amount);

        //login(签到，载入参数)
        load_params.setOnClickListener(this);
        //Online Trans(联机交易)
        onlineTrans.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.load_params:
                showProcessDialog("downloading params");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        load_params();
                    }
                }).start();
                break;
            case R.id.onlineTrans:
                if (!GlobalData.getInstance().getPinkeyFlag()) {
                    new EnterDialog(this).showConfirmDialog("Warning!!!", "please inject pinkey first", new OnConfirmListener() {

                        @Override
                        public void OK() {
                            // TODO Auto-generated method stub
                            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                            startActivity(new Intent(Pboc.this,PinpadTestActivity.class));
                        }

                        @Override
                        public void Cancel() {
                            // TODO Auto-generated method stub

                        }
                    });
                }
                else {
                    new EnterDialog(this).showConfirmDialog("trans chose", "please chose see flow or trans", "trans flow", "trans", new OnConfirmListener() {
                        @Override
                        public void OK() {
                            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                            startActivity(new Intent(Pboc.this,onlineTransFlowActivity.class));
                        }

                        @Override
                        public void Cancel() {
                            onlineTrans();
                        }
                    });
                }
                break;
        }
    }

    //login(签到，载入参数)
    private void load_params() {
        LOGD("login !");
        try {
            boolean bRet;
            PosEmvParam posEmvParam = ServiceManager.getInstence().getPboc().getPosTermPara();
            System.arraycopy(new byte[]{0x01,0x56}, 0, posEmvParam.TransCurrCode, 0, 2);
            System.arraycopy(new byte[]{0x01,0x56}, 0, posEmvParam.CountryCode, 0, 2);
            ServiceManager.getInstence().getPboc().setPosTermPara(posEmvParam);
            posEmvParam = ServiceManager.getInstence().getPboc().getPosTermPara();
            LogUtil.i(getClass(),"posEmvParam:"+posEmvParam.toString());
            LoadParamManage.getInstance().DeleteAllTerParamFile();
            for (int j = 0; j < CUPParam.aid_data.length; j++) {

                bRet = ServiceManager.getInstence().getPboc().updateAID(0, CUPParam.aid_data[j]);

                LOGD("download " +j+" aid ["+CUPParam.aid_data[j]+"]" + " bRet = " + bRet);
            }
            for (int j = 0; j < TradeParams.aid.length; j++) {

                bRet = ServiceManager.getInstence().getPboc().updateAID(0, TradeParams.aid[j]);

                LOGD("download " +j+" aid ["+TradeParams.aid[j]+"]" + " bRet = " + bRet);
            }
            for (int i = 0; i < CUPParam.ca_data.length; i++) {
                bRet =
                        ServiceManager.getInstence().getPboc().updateRID(0,
                                CUPParam.ca_data[i]);
                LOGD("download " +i+" rid ["+CUPParam.ca_data[i]+"]" + " bRet = [" + bRet+"]");
            }
            /*for (int i = 0; i < TradeParams.capk.length; i++) {
                bRet =
                        ServiceManager.getInstence().getPboc().updateRID(0,
                                TradeParams.capk[i]);
                LOGD("download " +i+" rid ["+ TradeParams.capk[i]+"]" + " bRet = [" + bRet+"]");
            }*/
            GlobalData.getInstance().setLogin(true);
            dismissDialog();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Online Trans(联机交易)
    private void onlineTrans() {
        if (!GlobalData.getInstance().getLogin())
        {
            new EnterDialog(this).showConfirmDialog("attention!","please load params first!", new OnConfirmListener() {

                @Override
                public void OK() {

                }

                @Override
                public void Cancel() {

                }
            });
        }
        else
        {
/*
        	try {
				final TimerCountTools tools = new TimerCountTools();
				final Intent in = new Intent();
                in.putExtra(InputPBOCInitData.AMOUNT_FLAG, formatAmount());
                in.putExtra(InputPBOCInitData.IS_PBOC_FORCE_ONLINE, true);
				final onlinePBOCListener onlineCallback = new onlinePBOCListener(Pboc.this, StringHelper.changeAmout(amount.getText().toString()),tools);
				ServiceManager.getInstence().getPboc().checkCards(false, true, true, false, 10, new OnCheckCardListener() {

					@Override
					public void onFindingCard(int arg0, Intent arg1) {
						// TODO Auto-generated method stub
						switch(arg0)
						{
						case CardType.MAG_CARD:
							// MAG card data entity class
							OutputMagCardInfo magCardInfo = new OutputMagCardInfo(arg1);

							LOGD("PBOC Mag card number:" + magCardInfo.getPAN());
							LOGD("PBOC Mag card track 2:" + magCardInfo.getTrack2HexString());
							LOGD("PBOC Mag card track 3:" + magCardInfo.getTrack3HexString());
							LOGD("PBOC Term of validity:" + magCardInfo.getExpiredDate());
							LOGD("PBOC Service Code: " + magCardInfo.getServiceCode());
							break;
						case CardType.IC_CARD:
							LOGD("PBOC CardType:IC card");
							tools.start();
							try {
								ServiceManager.getInstence().getPboc().startEmvProcess(PBOCOption.ONLINE_PAY, in, onlineCallback);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							break;
						case CardType.RF_CARD:
							LOGD("PBOC CardType:RF card");
							tools.start();
							try {
								ServiceManager.getInstence().getPboc().startEmvProcess(PBOCOption.ONLINE_PAY, in,onlineCallback );
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							break;

						}
					}

					@Override
					public void onError(Intent arg0) {
						// TODO Auto-generated method stub

					}
				});
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
*/
            try {
                TimerCountTools tools = new TimerCountTools();
                tools.start();
                LOGD("PBOC normal");
                Intent in = new Intent();
                //setting pboc process the amount(设置pboc流程的金额)
                in.putExtra(InputPBOCInitData.AMOUNT_FLAG, formatAmount());
                in.putExtra(InputPBOCInitData.IS_PBOC_FORCE_ONLINE, true);
                //setting pboc process support the card type(设置pboc流程寻卡类型)
                in.putExtra(InputPBOCInitData.USE_DEVICE_FLAG, InputPBOCInitData.USE_MAG_CARD | InputPBOCInitData.USE_RF_CARD |InputPBOCInitData.USE_IC_CARD);
                //set trans timeout(设置寻卡超时时间)
                in.putExtra(InputPBOCInitData.TIMEOUT, 30);

                //卡片不支持Q，需要设置此属性才能支持借贷记
//                in.putExtra(InputPBOCInitData.IS_QPBOC_SUPPORT_FLAG, false);
                //start pboc process[trade model,parameter settings,callback](开始流程[交易类型,设置参数,回调])
                ServiceManager.getInstence().getPboc().startTransfer(TransactionType.ONLINE_PAY, in, new onlinePBOCListener(Pboc.this, StringHelper.changeAmout(amount.getText().toString()),tools));
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    /**
     * format amount
     *
     * @return
     */
    public Long formatAmount() {
        return Long.parseLong(StringHelper.changeAmout(this.amount.getText().toString()).replace(".", ""));
    }

    private class KeyBoardListener implements OnNumKeyListener {
        @Override
        public void onClick(View view) {
            StringBuilder builder = new StringBuilder();

            builder.append(amount.getText());
            switch (view.getId()) {
                case R.id.num00:
                    builder.append(00);
                case R.id.num0:
                    builder.append(0);
                    break;
                case R.id.num1:
                    builder.append(1);
                    break;
                case R.id.num2:
                    builder.append(2);
                    break;
                case R.id.num3:
                    builder.append(3);
                    break;
                case R.id.num4:
                    builder.append(4);
                    break;
                case R.id.num5:
                    builder.append(5);
                    break;
                case R.id.num6:
                    builder.append(6);
                    break;
                case R.id.num7:
                    builder.append(7);
                    break;
                case R.id.num8:
                    builder.append(8);
                    break;
                case R.id.num9:
                    builder.append(9);
                    break;
                case R.id.num_back:
                    builder = builder.delete(builder.length() - 1, builder.length());
                    break;
                default:
                    break;
            }
            amount.setText(StringHelper.changeAmout(builder.toString()));
        }

    }

    public void freshProcessDialog(final String title) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showProcessDialog(title);
            }
        });
    }

    public void dismissDialog() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                dismissProcessDialog();
            }
        });
    }
}
