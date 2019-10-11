package com.pos.sdkdemo.commu;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.basewin.commu.Commu;
import com.basewin.commu.define.CommuListener;
import com.basewin.commu.define.CommuParams;
import com.basewin.commu.define.CommuStatus;
import com.basewin.commu.define.CommuType;
import com.basewin.commu.define.paramsBean;
import com.basewin.commu.exception.WebServiceException;
import com.basewin.utils.ParamUtil;
import com.basewin.utils.Timestamp;
import com.pos.sdkdemo.R;
import com.pos.sdkdemo.base.BaseActivity;
import com.pos.sdkdemo.utils.BCDHelper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by huyang on 2016/12/17.
 * Socket Demo
 */

public class SSLSocketDemo extends BaseActivity implements View.OnClickListener {
    private  static final String TAG = "SSLSocketDemo";
    private EditText edt_ip;
    private EditText edt_port;
    private EditText send_data;
    private Button send;
    private Button clear;
    private Button btn_startwebservice;
    private Button btn_starthttp;
    private Spinner sp_timeout;

    private Commu commu;
    private int timeout = 30;
    private static final int MSG_CLEAR_TEXT = 0;
    private static final int MSG_SHOW_TEXT = 1;
    private static final int MSG_SOCKEt_COMMU = 2;
    private static final int MSG_HTTP_COMMU  = 3;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            switch (msg.what){
                case MSG_CLEAR_TEXT:
                    clearView();
                    break;
                case MSG_SHOW_TEXT:
                    break;
                case MSG_SOCKEt_COMMU:
                    TestSocketCommu();
                    break;
                case MSG_HTTP_COMMU:
                    try {
                        testHttpPost();
                    } catch (WebServiceException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };

    @Override
    protected  View onCreateView(LayoutInflater inflater)
    {
        return inflater.inflate(R.layout.activity_socket_test, null);
    }
    @Override
    protected void onInitView()
    {
        initView();
    }


    private void initView(){
        edt_ip = (EditText) findViewById(R.id.edt_ip);
        optimizSoftKeyBoard(edt_ip);
        edt_port = (EditText) findViewById(R.id.edt_port);
        send_data = (EditText) findViewById(R.id.sent_content);
        send = (Button) findViewById(R.id.btn_send);
        clear = (Button) findViewById(R.id.btn_clear);
        sp_timeout = (Spinner) findViewById(R.id.sp_timeout);
        btn_startwebservice = (Button) findViewById(R.id.btn_startwebservice);
        btn_starthttp = (Button) findViewById(R.id.btn_starhttp);
        send.setOnClickListener(this);
        clear.setOnClickListener(this);
        btn_startwebservice.setOnClickListener(this);
        btn_starthttp.setOnClickListener(this);
        sp_timeout.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        timeout = 30;
                        break;
                    case 1:
                        timeout = 60;
                        break;
                    case 2:
                        timeout = 90;
                        break;
                    case 3:
                        timeout = 120;
                        break;
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                timeout = 30;
            }
        });
    }
    //clear the window
    private void clearView(){
        edt_ip.setText("");
        edt_port.setText("");
        send_data.setText("");
        CLearLog();
    }

    //get the text from the editText
    private String getEditTextText(EditText et) {
        String s = "";
        if (et != null) {
            s = et.getText().toString().trim();
        }
        return s;
    }

    private boolean isValid(String str){
        if(str == null){
            return false;
        }
        Pattern p = Pattern.compile("[0-9]*");
        Matcher m = p.matcher(str);
        return m.matches();
    }


    private CommuParams getParams() {
        String ip = getEditTextText(edt_ip);
        String port = getEditTextText(edt_port);
        if(ip.equals("")||port.equals("")){
            return null;
        }

        CommuParams params = new CommuParams();
        params.setIp(ip);
        if (isValid(port)) {
            params.setPort(Integer.parseInt(port));
        }
        params.setType(CommuType.SOCKET);

        params.setTimeout(timeout);//set the timeout time
        params.setIfSSL(true);
        return params;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_clear:
                handler.sendEmptyMessage(MSG_CLEAR_TEXT);
                break;
            case R.id.btn_send:
                LOGD("start socket communication...");
                handler.sendEmptyMessage(MSG_SOCKEt_COMMU);
                break;
            case R.id.btn_starhttp:
                LOGD("start http communication...");
                handler.sendEmptyMessage(MSG_HTTP_COMMU);
                break;
        }
    }
    //socket communication
    private void TestSocketCommu(){
        new Thread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                String testString = getEditTextText(send_data);
                commu = Commu.getInstence();
                // XML配置了则不需要这步
                // if parameters is configured in XML,skip this step.
                if(getParams()!=null){
                    commu.setCommuParams(getParams());
                }
                commu.dataCommu(SSLSocketDemo.this, BCDHelper.stringToBcd(testString, testString.length()), new CommuListener() {

                    @Override
                    public void OnStatus(int status, byte[] arg1) {
                        // TODO Auto-generated method stub
                        Log.d(TAG, "OnStatus:"+status);
                        switch (status){
                            case CommuStatus.INIT_COMMU:
                                LOGD("CommuStatus : " + CommuStatus.getStatusMsg(CommuStatus.INIT_COMMU));
                                break;
                            case CommuStatus.CONNECTING:
                                LOGD("CommuStatus : " + CommuStatus.getStatusMsg(CommuStatus.CONNECTING));
                                break;
                            case CommuStatus.SENDING:
                                LOGD("CommuStatus : " + CommuStatus.getStatusMsg(CommuStatus.SENDING));
                                break;
                            case CommuStatus.RECVING:
                                LOGD("CommuStatus : " + CommuStatus.getStatusMsg(CommuStatus.RECVING));

                                break;
                            case CommuStatus.FINISH:
                                LOGD("CommuStatus : " + CommuStatus.getStatusMsg(CommuStatus.FINISH));
                                LOGD("received data " + BCDHelper.hex2DebugHexString(arg1,arg1.length) + "\n");
                                break;
                        }

                    }

                    @Override
                    public void OnError(int arg0, String arg1) {
                        // TODO Auto-generated method stub
                        LOGD("OnError errorCode = " + arg0 + "  errorDetial = " + arg1);
                        LOGD(getString(R.string.communication_error) + "OnError errorCode = " + arg0 + "  errorDetial = " + arg1 + "\n");

                    }
                });

            }
        }).start();
    }
    //http communication
    private void testHttpPost() throws WebServiceException
    {
        //测试HTTP通讯接口
        //api to test HTTP
        CommuParams params = new CommuParams();
        //设置通讯类型为Http
        //set the type of communication as http
        params.setType(CommuType.HTTP);
        params.clearHttpParams();
        params.addHttpParams("method", "erp.addr.get");
        params.addHttpParams("format", "json");
        params.addHttpParams("timestamp", Timestamp.GetTimesTamp());
        params.addHttpParams("appid", "2");
        //对于参数是否需要按照字母排序，如果需要，调用此接口
        //the method can be called to sort the parameters as alphabetical order
        params.sortParams();
        //如果对于参数需要进行签名，可以获取此结构
        //get the secretParams used to encrypt the parameters
        List<paramsBean> secretParams = params.getHttpParams();
        Map<String, String> signParaMap = new HashMap<>();
        String[] params_key = new String[secretParams.size()];
        String[] params_value = new String[secretParams.size()];
        for (int i = 0; i < secretParams.size(); i++) {
            params_key[i] = secretParams.get(i).GetKey();
            params_value[i] = secretParams.get(i).GetValue();
        }
        signParaMap.clear();
        for (int i = 0; i < params_key.length; i++) {
            signParaMap.put(params_key[i], params_value[i]);
        }
        params.addHttpParams("sign", ParamUtil.signRequestParam(signParaMap, "encrypted key"));
        //再次进行参数排序
        //sort the parameters again.
        params.sortParams();
        //整理通讯参数
        //settle the Http parameters.
        params.initHttpParams();
        Commu.getInstence().dataCommu(SSLSocketDemo.this, null, new CommuListener() {

            @Override
            public void OnStatus(int code, byte[] data) {
                // TODO Auto-generated method stub
                LOGD("http communication："+code);
                if (data != null) {
                    Log.d(TAG, "webservice communication："+data.toString());
                }

                //如果是json数据
                //if it is json data.
                /*
                try {
                    JSONObject Jpaser = new JSONObject(data.toString());
//                    下面进行json数据的处理
//                    the following content is to handle the json data.

                } catch (JSONException e) {
                    e.printStackTrace();
                }*/
                //如果是XML数据
                //if it is XML data
                /*
                try {
                    XmlPullParser parser = Xml.newPullParser();
                    // parser.setInput(receiveMsg, "UTF-8");
                    StringReader ss = new StringReader(data.toString());
                    parser.setInput(ss);
                    //下面进行XML数据的处理
                    //the following content is to handle the xml data
                }
                catch (Exception e) {
                    e.printStackTrace();
                }*/
            }

            @Override
            public void OnError(int code, String msg) {
                // TODO Auto-generated method stub
                LOGD("OnError errorCode = " + code + "  errorDetial = " + msg);
                LOGD(getString(R.string.communication_error) + "OnError errorCode = " + code + "  errorDetial = " + msg + "\n");
            }
        });
    }
}
