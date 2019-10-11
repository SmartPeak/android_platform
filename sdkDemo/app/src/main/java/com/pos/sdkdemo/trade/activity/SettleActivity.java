package com.pos.sdkdemo.trade.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.basewin.aidl.OnPrinterListener;
import com.basewin.commu.define.CommuListener;
import com.basewin.commu.define.CommuStatus;
import com.basewin.packet8583.factory.Iso8583Manager;
import com.basewin.services.PrinterBinder;
import com.basewin.services.ServiceManager;
import com.pos.sdkdemo.R;
import com.pos.sdkdemo.base.DemoApplication;
import com.pos.sdkdemo.trade.Config;
import com.pos.sdkdemo.trade.Dao.db.BillBeanDB;
import com.pos.sdkdemo.trade.Dao.db.TransactionDataDB;
import com.pos.sdkdemo.trade.GlobleData;
import com.pos.sdkdemo.trade.bean.BillBean;
import com.pos.sdkdemo.trade.net.NetManager;
import com.pos.sdkdemo.trade.net8583.BatchUpEnd8583;
import com.pos.sdkdemo.trade.net8583.Settlement8583;
import com.pos.sdkdemo.trade.net8583.SignOut8583;
import com.pos.sdkdemo.utils.DialogUtil;
import com.pos.sdkdemo.utils.PosUtil;
import com.pos.sdkdemo.utils.SettleUtil;
import com.pos.sdkdemo.utils.ToastUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SettleActivity extends Activity {
public static final int PRINT_SETTLE=0x01;
    public static final int PRINT_DETAIL=0x02;
    public static final int PRINT_FAILURE=0x03;
    private static final int SIGN_OUT = 0X04;
    private Iso8583Manager receiveIso8583 = null;
    private Dialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settle);
        initView();
        initData();
    }

    private void initView(){

    }

    private void initData(){
        receiveIso8583 = new Iso8583Manager(this);
        receiveIso8583.Load8583XMLconfigByTag("ISO8583Config");
        isSettlement();
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    private void settlement(){
        loadingDialog = DialogUtil.showLoadingDialog(this,getString(R.string.net_work));
        GlobleData.getInstance().datas.put("field48",SettleUtil.getInitField48());
        new Thread(new Runnable() {
            @Override
            public void run() {
                NetManager.connect(SettleActivity.this, new Settlement8583(), new CommuListener() {
                    @Override
                    public void OnStatus(int i, byte[] bytes) {
                        if( i==CommuStatus.FINISH){
                            Log.e("feeling", "settlement: ");
                            try {
                                receiveIso8583.unpack(bytes);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            String field48Result = receiveIso8583.getBit(48);
                            if (TextUtils.isEmpty(field48Result) || field48Result.length() != 62) {
                                Log.e("feeling", "OnStatus: 结算失败");
                                return;
                            } else {
                                GlobleData.getInstance().datas.put("field48Result", field48Result);
                            }
                            try {
                                int d = Integer.parseInt(field48Result.substring(30, 31));
                                int f = Integer.parseInt(field48Result.substring(61, 62));
                                SettleUtil.updateStatement(d);
                            } catch (Exception e) {
                                e.printStackTrace();
                                Log.e("feeling","结算失败");
                                return;
                            }
                            netBatchUpEnd();
                        }

                    }

                    @Override
                    public void OnError(int i, String s) {
                        Log.e("feeling","结算失败");
                    }
                });
            }
        }).start();

    }

    private void netBatchUpEnd(){
        GlobleData.getInstance().datas.put("field60_3", "207");
        NetManager.connect(SettleActivity.this, new BatchUpEnd8583(), new CommuListener() {
            @Override
            public void OnStatus(int i, byte[] bytes) {
                if(i==CommuStatus.FINISH){
                    try {
                        receiveIso8583.unpack(bytes);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if(receiveIso8583.getBit(39).equals("00")){
                        printHandler.sendEmptyMessage(PRINT_SETTLE);
                    }
                }
            }

            @Override
            public void OnError(int i, String s) {

            }
        });
    }

    private void signOut(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                NetManager.connect(SettleActivity.this, new SignOut8583(), new CommuListener() {
                    @Override
                    public void OnStatus(int i, byte[] bytes) {
                        if(i==CommuStatus.FINISH){
                            TransactionDataDB.deleteAll();
                            String s = PosUtil.getBatch(SettleActivity.this);
                            String s1 = PosUtil.StrNumAuto(s);
                            Log.e("feeling", "batch:"+s1);
                            PosUtil.setBatch(SettleActivity.this,s1);
                            Log.e("feeling", "batch: "+PosUtil.getBatch(SettleActivity.this));
                            finish();
                        }
                    }

                    @Override
                    public void OnError(int i, String s) {
                           finish();
                    }
                });
            }
        }).start();
    }

    private void isSettlement(){
        DialogUtil.showDialog(this, getString(R.string.whether_settlement), new DialogUtil.OnClickListener() {
            @Override
            public void onConfirm() {
                SettleUtil.initBill();
                settlement();
            }

            @Override
            public void onCancel() {
                finish();
            }
        });
    }

    private void printSettle(){
        loadingDialog = DialogUtil.showLoadingDialog(this,getString(R.string.net_work));
        String sbatch = PosUtil.getBatch(DemoApplication.getInstance());
        Long batch = Long.valueOf(sbatch);
        BillBean billBean = BillBeanDB.selectBillByID(batch);
        String cAmount= "";
        String dAmount="";
        try {
            cAmount = PosUtil.fenToYuan(billBean.getCAmount());
            dAmount=PosUtil.fenToYuan(billBean.getDAmount());
        } catch (Exception e) {
            e.printStackTrace();
        }

        String settle="************************\n" +
                "        Statements Slip      \n" +
                "************************\n" +
                "对账："+ billBean.getIsFlat()+"\n"+
                "借记:" + dAmount+"     "+"笔数："+billBean.getDebitNum() +"\n"+
                "贷记:" + cAmount+"     "+"笔数："+billBean.getCreditNum()+"\n"+
                "************************\n";
        print(settle,PRINT_DETAIL);
    }

    private void printTradeDetail(){
        DialogUtil.showDialog(this, getString(R.string.print_detail_trade), new DialogUtil.OnClickListener() {
            @Override
            public void onConfirm() {
                String data = SettleUtil.getDetial();
                print(data,PRINT_FAILURE);
            }

            @Override
            public void onCancel() {
                printHandler.sendEmptyMessage(SIGN_OUT);
            }
        });
    }

    private void printFailureTradeDetail(){
        DialogUtil.showDialog(this, getString(R.string.print_failure), new DialogUtil.OnClickListener() {
            @Override
            public void onConfirm() {
                String data = SettleUtil.getFailureDetial();
                print(data,SIGN_OUT);
            }

            @Override
            public void onCancel() {
                printHandler.sendEmptyMessage(SIGN_OUT);
            }
        });
    }


    private void print(String data,int tag){
        JSONObject printJson = new JSONObject();
        JSONArray printTest = new JSONArray();
        // add text printer
        JSONObject json1 = new JSONObject();
        try {
            // Add text printing
            json1.put("content-type", "txt");
            json1.put("content", data);
            json1.put("size", "2");
            json1.put("position", "left");
            json1.put("offset", "0");
            json1.put("bold", "0");
            json1.put("italic", "0");
            json1.put("height", "-1");
            // lineSpace
            printTest.put(json1);
            printJson.put("spos", printTest);
            // 设置底部空3行
            // Set at the bottom of the empty 3 rows
            // ServiceManager.getInstence().getPrinter().printBottomFeedLine(3);
            ServiceManager.getInstence().getPrinter().print(printJson.toString(), null, new PrinterListener(tag));
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Handler printHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(loadingDialog!=null&&loadingDialog.isShowing()){
                loadingDialog.dismiss();
            }
            switch (msg.what){
                case PRINT_SETTLE:
                    printSettle();
                    break;
                case PRINT_DETAIL:
                    printTradeDetail();
                    break;
                case PRINT_FAILURE:
                    printFailureTradeDetail();
                    break;
                case SIGN_OUT:
                    signOut();
                    break;
            }
        }
    };



    class PrinterListener implements OnPrinterListener {
        private final String TAG = "Print";
        private int msg;

        public PrinterListener(int msg) {
            this.msg = msg;
        }

        @Override
        public void onStart() {
            // TODO 打印开始
            // Print start
            Log.e(TAG, "onStart: ");
        }

        @Override
        public void onFinish() {
            // TODO 打印结束
            // End of the print
            Log.e(TAG, "onFinish: ");
            if(loadingDialog!=null){
                loadingDialog.dismiss();
            }
            printHandler.sendEmptyMessage(msg);
        }

        @Override
        public void onError(int errorCode, String detail) {
            // TODO 打印出错
            // print error
            Log.e(TAG,"print error" + " errorcode = " + errorCode + " detail = " + detail);
            if (errorCode == PrinterBinder.PRINTER_ERROR_NO_PAPER) {
                Toast.makeText(SettleActivity.this, "paper runs out during printing", Toast.LENGTH_SHORT).show();
            }
            if (errorCode == PrinterBinder.PRINTER_ERROR_OVER_HEAT) {
                Toast.makeText(SettleActivity.this, "over heat during printing", Toast.LENGTH_SHORT).show();
            }
            if (errorCode == PrinterBinder.PRINTER_ERROR_OTHER) {
                Toast.makeText(SettleActivity.this, "other error happen during printing", Toast.LENGTH_SHORT).show();
            }

        }
    }
}
