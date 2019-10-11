package com.pos.sdkdemo.database;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.basewin.database.model.BaseModel;
import com.pos.sdkdemo.R;
import com.pos.sdkdemo.base.BaseActivity;
import com.pos.sdkdemo.interfaces.OnChoseListener;
import com.pos.sdkdemo.interfaces.OnEnterListener;
import com.pos.sdkdemo.widgets.EnterDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by huyang on 2017/1/22.
 */

public class DatabaseTestActivity extends BaseActivity implements View.OnClickListener {


    private Button btn_add;
    private Button btn_delete;
    private Button btn_revise;
    private Button btn_query;
    private Button btn_databaseclear;

    private EditText trace,referenceNo,merchant_name,merchant_no,terminal_no,func,card_number,operatorNo,exp_date,
            batch_no,auth_no,date_time,amount,ticket_no,status;

    private int traceNo = 0;
    private TransactionData datatmp;
    private List<TransactionData> dataList;

    private TransactionDataDao DbServer = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DbServer = new TransactionDataDao();
        DbServer.init("transaction_data","trace");
        datatmp = new TransactionData();
    }

    @Override
    protected  View onCreateView(LayoutInflater inflater)
    {
        return inflater.inflate(R.layout.activity_database_test, null);
    }
    @Override
    protected void onInitView()
    {
        initView();
    }

    private void initView(){
        btn_add = (Button) findViewById(R.id.btn_add);
        btn_add.setOnClickListener(this);
        btn_delete = (Button) findViewById(R.id.btn_delete);
        btn_delete.setOnClickListener(this);
        btn_revise = (Button) findViewById(R.id.btn_modify);
        btn_revise.setOnClickListener(this);
        btn_query = (Button) findViewById(R.id.btn_query);
        btn_databaseclear = (Button) findViewById(R.id.btn_databaseclear);
        btn_databaseclear.setOnClickListener(this);
        btn_query.setOnClickListener(this);
        trace= (EditText) findViewById(R.id.trace);
        referenceNo= (EditText) findViewById(R.id.referenceNo);
        merchant_name= (EditText) findViewById(R.id.merchant_name);
        merchant_no= (EditText) findViewById(R.id.merchant_no);
        terminal_no= (EditText) findViewById(R.id.terminal_no);
        func= (EditText) findViewById(R.id.func);
        card_number= (EditText) findViewById(R.id.card_number);
        operatorNo= (EditText) findViewById(R.id.operatorNo);
        exp_date= (EditText) findViewById(R.id.exp_date);
        batch_no= (EditText) findViewById(R.id.batch_no);
        auth_no= (EditText) findViewById(R.id.auth_no);
        date_time= (EditText) findViewById(R.id.date_time);
        amount= (EditText) findViewById(R.id.amount);
        ticket_no= (EditText) findViewById(R.id.ticket_no);
        status= (EditText) findViewById(R.id.status);
        optimizSoftKeyBoard(trace);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_add:
                insert(getTransactionData());
                break;
            case R.id.btn_delete:
                String[] memu = new String[]{"delete by trace no","delete all"};
                new EnterDialog(this).showListChoseDialog("please chose menu", memu, new OnChoseListener() {
                    @Override
                    public void Chose(int i) {
                        if (i == 0)
                        {
                            if (!isValid(trace)) {
                                LOGD("invalid trace input");
                                new EnterDialog(DatabaseTestActivity.this).showEnterDialog("please enter trace no", new OnEnterListener() {
                                    @Override
                                    public void onEnter(String text) {
                                        delete(text);
                                    }
                                });
                                return;
                            }
                            delete(getEditTextText(trace));
                        }
                        else {
                            DbServer.deleteAll();
                        }
                    }
                });


                break;
            case R.id.btn_modify:
                if (!isValid(trace)) {
                    LOGD("invalid trace input");
                    new EnterDialog(this).showEnterDialog("please enter trace no", new OnEnterListener() {
                        @Override
                        public void onEnter(String text) {
                            modify(text,getTransactionData());
                        }
                    });
                }
                else
                {
                    modify(getEditTextText(trace),getTransactionData());
                }
                break;
            case R.id.btn_query:
                String[] memuQ = new String[]{"query by trace no","query all"};
                new EnterDialog(this).showListChoseDialog("please chose menu", memuQ, new OnChoseListener() {
                    @Override
                    public void Chose(int i) {
                        if (i == 0)
                        {
                            if (!isValid(trace)) {
                                LOGD("invalid trace input");
                                return;
                            }
                            datatmp = query(getEditTextText(trace));
                            if (datatmp!=null) {
                                LOGD("queried data:"+datatmp.toString());
                                new EnterDialog(DatabaseTestActivity.this).showListDialog("data",new String[]{datatmp.toString()});
                            }else {
                                LOGD("the queried data is not existed");
                            }
                        }
                        else {
                            List<BaseModel> listData = DbServer.queryAll();
                            List<String> listS = new ArrayList<String>();
                            for (BaseModel base :
                                    listData) {
                                listS.add(((TransactionData)base).toString());
                            }
                            String[] arrayS = new String[listS.size()];
                            for (i = 0;i<listS.size();i++)
                            {
                                arrayS[i] = listS.get(i);
                            }
                            new EnterDialog(DatabaseTestActivity.this).showListDialog("data",arrayS);
                        }
                    }
                });

                break;
            case R.id.btn_databaseclear:
                CLearLog();
                break;
        }
    }

    //insert into database
    private void insert(TransactionData data)
    {
        DbServer.insert(data);
        LOGD("insert data:"+data.toString());
    }

    //delete database by trace
    private void delete(String trace)
    {
        DbServer.deleteBy(trace);
        LOGD("delete "+trace);
    }

    //modify database by trace
    private void modify(String trace,TransactionData data){
        DbServer.modifyBy(trace,data);
        LOGD("modify "+trace);
    }

    //query database by trace
    private TransactionData query(String trace){
        return (TransactionData) DbServer.queryBy(trace);
    }

    private String getAutotrace(int trace){
        StringBuffer sb = new StringBuffer("");
        String str = trace+"";
        if(str.length()<5){
            for (int i=0;i<5-str.length();i++){
                sb.append("0");
            }
            sb.append(str);
        }
        return sb.toString();
    }

    //get the text from the editText
    private String getEditTextText(EditText et) {
        String s = "";
        if (et != null) {
            s = et.getText().toString().trim();
        }
        return s;
    }

    private TransactionData getTransactionData(){
        if(datatmp == null){
            datatmp = new TransactionData();
        }
        datatmp.setAmount(getEditTextText(amount));
        datatmp.setAuth_no(getEditTextText(auth_no));
        datatmp.setBatch_no(getEditTextText(batch_no));
        datatmp.setCard_number(getEditTextText(card_number));
        datatmp.setDate_time(getEditTextText(date_time));
        datatmp.setExp_date(getEditTextText(exp_date));
        if (getEditTextText(func).isEmpty())
        {
            datatmp.setFunc(1);
        }
        else
            datatmp.setFunc(Integer.parseInt(getEditTextText(func)));
        datatmp.setMerchant_name(getEditTextText(merchant_name));
        datatmp.setMerchant_no(getEditTextText(merchant_no));
        datatmp.setOperatorNo(getEditTextText(operatorNo));
        datatmp.setReferenceNo(getEditTextText(referenceNo));
        datatmp.setTerminal_no(getEditTextText(terminal_no));
        datatmp.setTicket_no(getEditTextText(ticket_no));
        datatmp.setTrace(getEditTextText(trace));
        if (getEditTextText(status).isEmpty())
            datatmp.setStatus(0);
        else
            datatmp.setStatus(Integer.parseInt(getEditTextText(status)));
        return datatmp;
    }
    private boolean isValid(EditText et_inputtrace){
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(et_inputtrace.getText().toString()).matches();
    }

    private String showAll(List<TransactionData> datalist){
        StringBuffer sb = new StringBuffer("");
        sb.append("transation data:");
        if(datalist!=null){
            for(int i=0;i<datalist.size();i++){
                TransactionData data = datalist.get(i);
                sb.append("[ trace:"+data.getTrace()+"\n"
                        +",merchant name:" +data.getMerchant_name()+"\n"
                +",merchant no:"+data.getMerchant_no()+"\n"
                +",operator no:"+data.getOperatorNo());
            }
        }
        return sb.toString();
    }
}
