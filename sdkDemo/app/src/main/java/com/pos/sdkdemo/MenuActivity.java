package com.pos.sdkdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.pos.sdkdemo.guides.GuiderActivity;
import com.pos.sdkdemo.utils.GlobalData;

/**
 * main menu
 */
public class MenuActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        GlobalData.getInstance().ifEntransActivityExist = true;
    }

    public void enterTransModule(View view)
    {
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
        startActivity(new Intent(MenuActivity.this,TransModuleEntranceActivity.class));
    }

    public void enterSpecialCardsModule(View view)
    {
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
        startActivity(new Intent(MenuActivity.this,SpecialCardsModuleEntranceActivity.class));
    }

    public void enterSystemModule(View view)
    {
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
        startActivity(new Intent(MenuActivity.this,SystemModuleEntranceActivity.class));
    }

    public void buildProjects(View view)
    {
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
        startActivity(new Intent(MenuActivity.this,GuiderActivity.class));
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        GlobalData.getInstance().ifEntransActivityExist = false;
    }
}
