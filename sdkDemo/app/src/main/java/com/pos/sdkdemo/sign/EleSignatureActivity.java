package com.pos.sdkdemo.sign;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.basewin.aidl.OnPrinterListener;
import com.basewin.services.ServiceManager;
import com.basewin.widgets.HandWriteView;
import com.pos.sdkdemo.R;
import com.pos.sdkdemo.base.BaseActivity;
import com.pos.sdkdemo.print.PicUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by huyang on 2016/12/6.
 * Electronic signature module
 * 电子签名模块
 */

public class EleSignatureActivity extends BaseActivity implements View.OnClickListener{
    private final String TAG = EleSignatureActivity.class.getName();
    private HandWriteView handWriteView;
    private Button btn_cancel;
    private Button btn_save;
    private Button btn_watch;
    private Button btn_print;
    private String path;
    private Handler handler = null;
    private Bitmap smallBitmap = null;
    private static final int SELECT_BITMAP = 1;
    @Override
    protected  View onCreateView(LayoutInflater inflater)
    {
        return inflater.inflate(R.layout.activity_elesignature, null);
    }
    @Override
    protected void onInitView()
    {
        initView();
    }


    private void initView() {
        handWriteView = (HandWriteView) findViewById(R.id.handwriteview);
        btn_cancel = (Button) findViewById(R.id.cancel);
        btn_save = (Button) findViewById(R.id.save);
        btn_watch = (Button) findViewById(R.id.watch);
        btn_print = (Button) findViewById(R.id.print);
        btn_cancel.setOnClickListener(this);
        btn_save.setOnClickListener(this);
        btn_watch.setOnClickListener(this);
        btn_print.setOnClickListener(this);
        //set the handview background
        handWriteView.setBackgroudView(((BitmapDrawable) getResources().getDrawable(R.drawable.timg)).getBitmap());
    }

    /**
     * save the bitmap in SD card
     * @param bitmap
     * @return the path of the bitmap
     */
    private String saveBitmap(Bitmap bitmap) {
        try {
            File file = new File(Environment.getExternalStorageDirectory(), "sign_map" + ".png");

            //transfer the background color from transparent to white
            Canvas canvas = new Canvas(bitmap);
            canvas.drawColor(Color.WHITE, PorterDuff.Mode.DARKEN);

            FileOutputStream stream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            LOGD("bitmap saving succeed"+"\n"+"path:"+file.getAbsolutePath());
            return file.getAbsolutePath();
        } catch (Exception e) {
            LOGD("bitmap saving failed");
            e.printStackTrace();
        }
        return "";
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cancel:
                clearView();
                break;
            case R.id.save:
                saveSignature();
                break;
            case R.id.watch:
                watchBitmap();
                break;
            case R.id.print:
                printBitmap(smallBitmap);
                break;
        }
    }
    private void clearView(){
        handWriteView.clear();
        btn_save.setEnabled(true);
        CLearLog();
    }
    private void saveSignature(){
        LOGD(" #####  bSign :" + handWriteView.isValid());
        if (!handWriteView.isValid()) {
            LOGD(getString(R.string.sign_screen));
        } else {
            //close the active property of the button.
            btn_save.setEnabled(false);
            LOGD(getString(R.string.please_wait));
            new Thread() {
                @Override
                public void run() {

                    //getCacheBitmap() method can get a bitmap with signature
                    smallBitmap = handWriteView.getCachebBitmap();
                    //save the bitmap in SD card
                    path = saveBitmap(smallBitmap);
                }
            }.start();

        }
    }

    private void watchBitmap()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, SELECT_BITMAP);
    }

    private void printBitmap(Bitmap bitmap)
    {
        if (smallBitmap == null)
        {
            LOGD("please sign bitmap first!");
            return;
        }
        LOGD("Print bitmap");
        JSONArray printTest = new JSONArray();
        JSONObject printJson = new JSONObject();
        // add text printer
        try {
            // add picture
            JSONObject json11 = new JSONObject();
            json11.put("content-type", "jpg");
            json11.put("position", "center");

            printTest.put(json11);
            printJson.put("spos", printTest);
            // 设置底部空3行
            // Set at the bottom of the empty 3 rows
//            ServiceManager.getInstence().getPrinter().printBottomFeedLine(3);
            Bitmap qr = BitmapFactory.decodeResource(getResources(), R.drawable.test);
            Bitmap[] bitmaps = new Bitmap[]{bitmap};
            ServiceManager.getInstence().getPrinter().print(printJson.toString(), bitmaps, new OnPrinterListener() {
                @Override
                public void onError(int i, String s) {
                    LOGD("print error:"+i+" "+s);
                }

                @Override
                public void onFinish() {
                    LOGD("print finish!");
                }

                @Override
                public void onStart() {
                    LOGD("print start!");
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }

        ContentResolver resolver = getContentResolver();
        if (requestCode == SELECT_BITMAP) {
            try {
                //获得图片的uri
                Uri originalUri = data.getData();
                LOGD("get the uri of the picture");
                //将图片内容解析成字节数组
                byte[] mContent = PicUtils.getBytesFromInputStream(resolver.openInputStream(Uri.parse(originalUri.toString())), 3500000);
                LOGD("Parse the image into an array of bytes ");
                //Converts a byte array to a bitmap object that can be called by a ImageView (将字节数组转换为ImageView可调用的Bitmap对象)
                smallBitmap = PicUtils.getPicFromBytes(mContent, null);
                Log.d(TAG, "Converts an image content resolution to an array of ImageView ");
                if (smallBitmap != null) {
                    //he bitmap object is cut, the width of 240, height according to the geometric proportion zoom (将bitmap对象进行裁剪，宽度为240,高度按等比比例缩放)
                    if (smallBitmap.getWidth() > 384)
                        smallBitmap = PicUtils.zoomImage(smallBitmap, 384);
                    smallBitmap = PicUtils.switchColor(smallBitmap);//将彩色图片转换成黑白图片
                } else {
                    Toast.makeText(this, "This picture is not available. Please choose another one. ", Toast.LENGTH_SHORT).show();
                }


            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }

    }

}
