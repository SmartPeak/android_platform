package com.pos.sdkdemo.trade.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.pos.sdkdemo.R;
import com.pos.sdkdemo.TransModuleEntranceActivity;
import com.pos.sdkdemo.guides.GuiderActivity;
import com.pos.sdkdemo.trade.Config;
import com.pos.sdkdemo.trade.GlobleData;
import com.pos.sdkdemo.trade.adapter.MenuGrideAdapter;
import com.pos.sdkdemo.trade.bean.MenuBean;

import java.util.ArrayList;
import java.util.List;

import static com.pos.sdkdemo.trade.TransTypeConstant.ACTION_AUTH;
import static com.pos.sdkdemo.trade.TransTypeConstant.ACTION_SALE;
import static com.pos.sdkdemo.trade.TransTypeConstant.ACTION_VOID;
import static com.pos.sdkdemo.trade.TransTypeConstant.MANAGER;
import static com.pos.sdkdemo.trade.TransTypeConstant.MOUDLE;
import static com.pos.sdkdemo.trade.TransTypeConstant.getNameByTransType;

public class MenuActivity extends Activity {

    private GridView gridView;
    private MenuGrideAdapter menuAdapter;
    private List<MenuBean> datas=null;

    private final int[] icons={R.drawable.cash_00,
            R.drawable.cash_01,
            R.drawable.cash_03,
            R.drawable.pro_07,
            R.drawable.cash_02};
    private final int[] transTypes={ACTION_SALE,
            ACTION_VOID,
            ACTION_AUTH,
            MANAGER,
            MOUDLE};
    private final Class[] activity = {InputMoneyActivity.class,InputTraceActivity.class,
            MenuAuthActivity.class,ManageActivity.class, GuiderActivity.class};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu2);
        initView();
        initData();
    }

    private void initView(){
        gridView= (GridView) findViewById(R.id.grid_view);
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

        menuAdapter = new MenuGrideAdapter(this,datas);
        gridView.setAdapter(menuAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GlobleData.getInstance().init();
                GlobleData.getInstance().cleanData();
                Intent intent = new Intent(MenuActivity.this,datas.get(position).getActivity());
                GlobleData.getInstance().datas.put(Config.TRANS_TYPE,datas.get(position).getTransType());
                startActivity(intent);
            }
        });
    }
}
