package com.pos.sdkdemo.apn;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.LayoutInflater;
import android.view.View;

import com.pos.sdkdemo.R;
import com.pos.sdkdemo.base.BaseActivity;
import com.pos.sdkdemo.widgets.EnterDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * APN
 */
public class ApnActivity extends BaseActivity {

    private static final String action_add = "com.android.action.SET_APN";
    private static final String action_delete = "com.android.action.DEL_APN";
    private static final String action_querylist = "com.android.action.GET_APN";
    private static final String action_recvquerylist = "com.android.action.APN_LIST";
    private static final String action_set = "com.android.action.SET_CURRENT_APN";
    private static final String action_query = "com.android.action.SEARCH_APN";
    private static final String action_recvquery = "com.android.action.SEARCH_APN_RESULT";

    private QueryListBroadcastReceiver recvQueryList = null;
    private QueryBroadcastReceiver recvQuery = null;
    public class QueryListBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            ArrayList<String> apnList = intent.getStringArrayListExtra("apn_data");
            LOGD("query apn list success! size = " +apnList.size());
            if (apnList.size() > 0)
            {
                String[] apnS = new String[apnList.size()];
                for (int i=0;i<apnList.size();i++)
                    apnS[i] = apnList.get(i);
                new EnterDialog(context).showListDialog("apn list",apnS);
            }
        }
    }

    public class QueryBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            boolean isExist = intent.getBooleanExtra("is_apn_exist",false);
            LOGD("query apn success!  apn exist = "+isExist);
        }
    }

    @Override
    protected View onCreateView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.activity_apnmodule, null, false);
    }

    @Override
    protected void onInitView() {
        recvQueryList = new QueryListBroadcastReceiver();
        IntentFilter filter = new IntentFilter(action_recvquerylist);
        registerReceiver(recvQueryList, filter);
        recvQuery = new QueryBroadcastReceiver();
        IntentFilter filter2 = new IntentFilter(action_recvquery);
        registerReceiver(recvQuery, filter2);
    }

    /**
     *
     * @param view
     */
    public void addApn(View view)
    {
        LOGD("add apn");
        Intent intent = new Intent(action_add);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("apn", "3gwap");
            jsonObject.put("authtype", "mms");
            jsonObject.put("current", "xx");
            jsonObject.put("mmsc", "http://mmsc.myuni.com.cn");
            jsonObject.put("mmsport", "80");
            jsonObject.put("mmsproxy", "010.000.000.172");
//            jsonObject.put("name", "xx");
//            jsonObject.put("user", "xx");
//            jsonObject.put("password", "xx");
            jsonObject.put("port", "xx");
            jsonObject.put("proxy", "xx");
            jsonObject.put("server", "xx");
            jsonObject.put("type", "xx");
            jsonObject.put("mcc", "460");
            jsonObject.put("mnc", "01");
            jsonObject.put("numeric", "xx");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        intent.putExtra("apn_data", jsonObject.toString());
        sendBroadcast(intent);
        LOGD("add apn complete!");
    }

    /**
     *
     * @param view
     */
    public void deleteApn(View view)
    {
        LOGD("delete apn");
        Intent intent = new Intent(action_delete);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("apn", "xx");
            jsonObject.put("type", "xx");
            jsonObject.put("numeric", "xx");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        intent.putExtra("apn_data", jsonObject.toString());
        sendBroadcast(intent);
        LOGD("delete apn complete!");
    }

    /**
     *
     * @param view
     */
    public void queryApnList(View view)
    {
        LOGD("query apn list");
        Intent intent = new Intent(action_querylist);
        sendBroadcast(intent);
    }

    /**
     *
     * @param view
     */
    public void setApn(View view)
    {
        LOGD("set apn");
        Intent intent = new Intent(action_set);
        intent.putExtra("apn_id","xx");
        sendBroadcast(intent);
        LOGD("set apn complete!");
    }

    /**
     *
     * @param view
     */
    public void queryApnNowInUse(View view)
    {
        LOGD("query apn");
        Intent intent = new Intent(action_set);
        intent.putExtra("apn","xx");
        intent.putExtra("numeric","xx");
        sendBroadcast(intent);
        LOGD("query apn complete!");
    }


}
