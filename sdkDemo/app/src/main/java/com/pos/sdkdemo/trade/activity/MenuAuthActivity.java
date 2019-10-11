package com.pos.sdkdemo.trade.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.pos.sdkdemo.R;
import com.pos.sdkdemo.trade.Config;
import com.pos.sdkdemo.trade.GlobleData;
import com.pos.sdkdemo.trade.TransTypeConstant;
import com.pos.sdkdemo.trade.adapter.MenuListAdapter;
import com.pos.sdkdemo.trade.bean.MenuBean;

import java.util.ArrayList;
import java.util.List;

import static com.pos.sdkdemo.trade.TransTypeConstant.ACTION_AUTH;
import static com.pos.sdkdemo.trade.TransTypeConstant.ACTION_AUTH_COMPLETE;
import static com.pos.sdkdemo.trade.TransTypeConstant.ACTION_CANCEL;
import static com.pos.sdkdemo.trade.TransTypeConstant.ACTION_COMPLETE_VOID;
import static com.pos.sdkdemo.trade.TransTypeConstant.ACTION_SALE;
import static com.pos.sdkdemo.trade.TransTypeConstant.ACTION_VOID;
import static com.pos.sdkdemo.trade.TransTypeConstant.MANAGER;
import static com.pos.sdkdemo.trade.TransTypeConstant.getNameByTransType;

public class MenuAuthActivity extends Activity implements View.OnClickListener{
    private ListView listView;
    private MenuListAdapter menuAdapter;
    private List<MenuBean> datas=null;
    private ImageView back;
    private TextView title;

    private final int[] icons={ R.drawable.cash_03,
                                R.drawable.cash_04,
                                R.drawable.prelicensing_sel_01,
                                R.drawable.cash_01,};
    private final int[] transTypes={ACTION_AUTH,ACTION_AUTH_COMPLETE,ACTION_CANCEL,ACTION_COMPLETE_VOID};
    private final Class[] activity = {InputMoneyActivity.class,InputDateActivity.class,InputDateActivity.class,InputTraceActivity.class};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_auth);
        initView();
        initData();
    }

    private void initView(){
        title = (TextView) findViewById(R.id.title);
        back = (ImageView) findViewById(R.id.back);
        listView= (ListView) findViewById(R.id.list_view);
        back.setOnClickListener(this);
        title.setText("Auth");
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
                Intent intent = new Intent(MenuAuthActivity.this,datas.get(position).getActivity());
                GlobleData.getInstance().datas.put(Config.TRANS_TYPE,datas.get(position).getTransType());
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
