package com.pos.sdkdemo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.pos.sdkdemo.commu.SSLSocketDemo;
import com.pos.sdkdemo.database.DatabaseTestActivity;
import com.pos.sdkdemo.iso8583.Iso8583TestActivity;
import com.pos.sdkdemo.pboc.Pboc;
import com.pos.sdkdemo.pinpad.PinpadTestActivity;
import com.pos.sdkdemo.print.PrintTestActivity;
import com.pos.sdkdemo.scan.ScanTestDemoActivity;
import com.pos.sdkdemo.sign.EleSignatureActivity;
import com.pos.sdkdemo.trade.activity.MenuActivity;

/**
 * 主界面
 */
public class TransModuleEntranceActivity extends Activity implements View.OnClickListener {
    private static final Class[] activitys = {
            Pboc.class,
            PrintTestActivity.class,
            EleSignatureActivity.class,
            SSLSocketDemo.class,
            ScanTestDemoActivity.class,
            PinpadTestActivity.class,
            Iso8583TestActivity.class,
            DatabaseTestActivity.class,
            MenuActivity.class
    };
    private static final int[] model = {
            R.string.pboc,
            R.string.print_test,
            R.string.ele_signature,
            R.string.commu_text,
            R.string.scan_test,
            R.string.pinpad_test,
            R.string.iso8583_test,
            R.string.database_text,
            R.string.sale_trade
    };
    private static int button_width = 300, button_height = -2;
    private static int[] model_ids;
    private ScrollView rootfather;
    private LinearLayout root;
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        rootfather = new ScrollView(context);
        root = new LinearLayout(context);
        rootfather.addView(root);
        super.onCreate(savedInstanceState);
        setContentView(rootfather);
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        button_width = (display.getWidth() - 80) / 2;

        root.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
        root.setOrientation(LinearLayout.VERTICAL);
        root.setGravity(Gravity.CENTER | Gravity.TOP);
        root.setBackgroundColor(Color.WHITE);

        genIds();
        int index = 0;
        for (int i = 0; i < Math.round(((double) activitys.length) / 2); i++) {
            LinearLayout layout = genH();
            layout.addView(genButton(model[index], model_ids[index]));
            if ((index + 1) == model_ids.length) {
                root.addView(layout);
                break;
            }
            layout.addView(genButton(model[index + 1], model_ids[index + 1]));
            root.addView(layout);
            index = index + 2;
        }
    }

    private LinearLayout genH() {
        LinearLayout layout = new LinearLayout(context);
        layout.setLayoutParams(new LinearLayout.LayoutParams(-1, -2));
        layout.setOrientation(LinearLayout.HORIZONTAL);
        layout.setGravity(Gravity.LEFT);
        return layout;
    }

    private Button genButton(int con, int id) {
        Button b = new Button(context);
        LinearLayout.LayoutParams ll = new LinearLayout.LayoutParams(button_width, button_height);
        ll.setMargins(20, 5, 20, 5);
        ll.setMargins(20, 5, 20, 5);
        ll.setMargins(20, 10, 20, 10);
        b.setLayoutParams(ll);
        b.setText(con);
        b.setId(id);
        b.setGravity(Gravity.CENTER);
        b.setOnClickListener(this);
        b.setBackground(buttonBackgound());
        return b;
    }

    private Drawable buttonBackgound() {
        GradientDrawable gd = new GradientDrawable();
        gd.setStroke(1, Color.RED);
        gd.setCornerRadius(5);
        gd.setColor(Color.WHITE);
        return gd;
    }

    public void genIds() {
        model_ids = new int[activitys.length];
        int id = 0x01;
        for (int i = 0; i < activitys.length; i++) {
            model_ids[i] = id;
            id++;
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        for (int i = 0; i < model_ids.length; i++) {
            if (id == model_ids[i]) {
                startActivity(new Intent(context, activitys[i]));
            }
        }
    }


}
