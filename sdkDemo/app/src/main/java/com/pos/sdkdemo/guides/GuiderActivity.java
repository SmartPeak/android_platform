package com.pos.sdkdemo.guides;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.pos.sdkdemo.MenuActivity;
import com.pos.sdkdemo.R;
import com.pos.sdkdemo.guides.buildprojects.asprojects;
import com.pos.sdkdemo.guides.buildprojects.eclipseprojects;
import com.pos.sdkdemo.utils.GlobalData;

import static com.pos.sdkdemo.R.id.bt_skip;


public class GuiderActivity extends Activity implements View.OnClickListener{

    private ImageView eclipse,android_studio;
    private Button skip;
    private CheckBox checkBox;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_build_guider);
        initView();
    }

    private void initView()
    {
        if(!GlobalData.getInstance().getGuiderShows()){
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            startActivity(new Intent(GuiderActivity.this,MenuActivity.class));
            finish();
            return;
        }
        eclipse = (ImageView)findViewById(R.id.imageView1);
        android_studio = (ImageView)findViewById(R.id.imageView2);
        eclipse.setOnClickListener(this);
        android_studio.setOnClickListener(this);

        skip = (Button)findViewById(bt_skip);
        skip.setOnClickListener(this);
        checkBox =(CheckBox)findViewById(R.id.checkbox_nevershow);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b)
                {
                    GlobalData.getInstance().setGuiderShows(false);
                }
                else
                {
                    GlobalData.getInstance().setGuiderShows(true);
                }
            }
        });
        if (GlobalData.getInstance().ifEntransActivityExist)
        {
            skip.setVisibility(View.INVISIBLE);
            checkBox.setVisibility(View.INVISIBLE);
        }
        startAnimation();
    }

    @Override
    public void onClick(View view) {
        switch(view.getId())
        {
            case R.id.imageView1:
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                startActivity(new Intent(GuiderActivity.this,eclipseprojects.class));
                break;
            case R.id.imageView2:
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                startActivity(new Intent(GuiderActivity.this,asprojects.class));
                break;
            case R.id.bt_skip:
                finish();
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                startActivity(new Intent(GuiderActivity.this,MenuActivity.class));
                break;
        }
    }

    private void startAnimation()
    {
        final AlphaAnimation in = new AlphaAnimation(0.8f, 1.0f);
        in.setDuration(1000);
        final AlphaAnimation out = new AlphaAnimation(1.0f, 0.8f);
        out.setDuration(1000);
        in.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationRepeat(Animation arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animation arg0) {
                // TODO Auto-generated method stub
                eclipse.startAnimation(out);
                android_studio.startAnimation(out);
            }
        });
        out.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationRepeat(Animation arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animation arg0) {
                // TODO Auto-generated method stub
                eclipse.startAnimation(in);
                android_studio.startAnimation(in);
            }
        });
        eclipse.startAnimation(in);
        android_studio.startAnimation(in);
    }
}
