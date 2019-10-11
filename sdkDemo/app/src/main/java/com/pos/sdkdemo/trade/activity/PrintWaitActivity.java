package com.pos.sdkdemo.trade.activity;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.basewin.aidl.OnPrinterListener;
import com.basewin.services.PrinterBinder;
import com.basewin.services.ServiceManager;
import com.pos.sdkdemo.R;
import com.pos.sdkdemo.trade.Config;
import com.pos.sdkdemo.trade.GlobleData;
import com.pos.sdkdemo.trade.TransTypeConstant;
import com.pos.sdkdemo.trade.bean.Card;
import com.pos.sdkdemo.utils.DialogUtil;
import com.pos.sdkdemo.utils.PosUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

public class PrintWaitActivity extends Activity {

    private JSONObject printJson = new JSONObject();
    private PrinterListener printer_callback = new PrinterListener();
    private Dialog loadingDialog;
    private Card card;
    private String amount="";
    public String printData ="";
    private int transType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print_wait);
        initView();
        initData();
    }

    private void initView(){
        loadingDialog = DialogUtil.showLoadingDialog(this,getString(R.string.printing));
    }

    private void initData(){
        transType = (int) GlobleData.getInstance().datas.get(Config.TRANS_TYPE);
        card=GlobleData.getInstance().card;
        amount = (String) GlobleData.getInstance().datas.get(Config.AMOUNT);
        String batch =  (String) GlobleData.getInstance().datas.get("batch");
        String trace =  (String) GlobleData.getInstance().datas.get("trace");
        printData="************************\n" +
                "        Sales Slip      \n" +
                "************************\n" +
                "Merchant Name: Test Merchant  \n"+
                "MerchantNo:"+Config.MerchantNo+"\n"+
                "TerminalNo:"+Config.TerminalNo+"\n"+
                "OPERATOR NO:"+Config.OPERATOR_NO+"\n"+
                "CARD NUM:"+card.getPan()+"\n"+
                "EXE DATE:"+card.getExpDate()+"\n"+
                "TRANS TYPE: "+TransTypeConstant.getNameByTransType(this,transType)+"\n"+
                "BATCH NO: "+ batch +"\n"+
                "VOUCHER NO: "+ trace +"\n"+
                "DATE:"+new Date().toString()+"\n"+
                "AMOUNT:"+amount+"\n"+
                "************************\n\n";
    }

    @Override
    protected void onStart() {
        super.onStart();
        printTrade();
    }


    private void printTrade(){
        JSONArray printTest = new JSONArray();
        // add text printer
        JSONObject json1 = new JSONObject();
        try {
            // Add text printing
            json1.put("content-type", "txt");
            json1.put("content", printData);
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
            ServiceManager.getInstence().getPrinter().print(printJson.toString(), null, printer_callback);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class PrinterListener implements OnPrinterListener {
        private final String TAG = "Print";

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
            finish();
        }

        @Override
        public void onError(int errorCode, String detail) {
            // TODO 打印出错
            // print error
            Log.e(TAG,"print error" + " errorcode = " + errorCode + " detail = " + detail);
            if (errorCode == PrinterBinder.PRINTER_ERROR_NO_PAPER) {
                Toast.makeText(PrintWaitActivity.this, "paper runs out during printing", Toast.LENGTH_SHORT).show();
            }
            if (errorCode == PrinterBinder.PRINTER_ERROR_OVER_HEAT) {
                Toast.makeText(PrintWaitActivity.this, "over heat during printing", Toast.LENGTH_SHORT).show();
            }
            if (errorCode == PrinterBinder.PRINTER_ERROR_OTHER) {
                Toast.makeText(PrintWaitActivity.this, "other error happen during printing", Toast.LENGTH_SHORT).show();
            }

        }
    }
}
