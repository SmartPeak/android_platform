package com.pos.sdkdemo.trade.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;

import com.pos.sdkdemo.R;
import com.pos.sdkdemo.trade.Config;
import com.pos.sdkdemo.trade.GlobleData;
import com.pos.sdkdemo.trade.adapter.MenuGrideAdapter;
import com.pos.sdkdemo.trade.adapter.MenuListAdapter;
import com.pos.sdkdemo.trade.bean.MenuBean;

import java.util.ArrayList;
import java.util.List;

import static com.pos.sdkdemo.trade.TransTypeConstant.MERCHANT_CONFIG;
import static com.pos.sdkdemo.trade.TransTypeConstant.NET_SETTING;
import static com.pos.sdkdemo.trade.TransTypeConstant.SETTLE;
import static com.pos.sdkdemo.trade.TransTypeConstant.SING_IN;
import static com.pos.sdkdemo.trade.TransTypeConstant.getNameByTransType;

public class ManageActivity extends Activity implements View.OnClickListener {
    private ListView listView;
    private MenuListAdapter menuAdapter;
    private List<MenuBean> datas=null;
    private ImageView back;

    private final int[] icons={R.drawable.cash_07,R.drawable.cash_08,
            R.drawable.prol_07,R.drawable.cash_02};
    private final int[] transTypes={ SING_IN
                                    ,SETTLE,NET_SETTING,MERCHANT_CONFIG};
    private final Class[] activity = {SignInActivity.class,SettleActivity.class,
            NetSettingActivity.class,MerchantConfigActivity.class};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage);
        initView();
        initData();
    }


    private void initView(){
        back = (ImageView) findViewById(R.id.back);
        listView= (ListView) findViewById(R.id.list_view);
        back.setOnClickListener(this);
    }

    private void initData(){
        if(datas==null){
            datas=new ArrayList<>();
        }
        for (int i = 0; i < activity.length; i++) {
            MenuBean menuBean = new MenuBean();
            menuBean.setTransType(transTypes[i]);
            menuBean.setName(getNameByTransType(this,transTypes[i]));
            menuBean.setResId(icons[i]);
            menuBean.setActivity(activity[i]);
            datas.add(menuBean);
        }

        menuAdapter = new MenuListAdapter(this,datas);
        listView.setAdapter(menuAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GlobleData.getInstance().init();
                GlobleData.getInstance().cleanData();
                GlobleData.getInstance().datas.put(Config.TRANS_TYPE,datas.get(position).getTransType());
                Intent intent = new Intent(ManageActivity.this,datas.get(position).getActivity());
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
        }
    }
}
