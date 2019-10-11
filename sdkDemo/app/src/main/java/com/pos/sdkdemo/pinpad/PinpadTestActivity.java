package com.pos.sdkdemo.pinpad;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.File;

import com.basewin.define.BwPinpadSource;
import com.basewin.define.GlobalDef;
import com.basewin.define.KeyType;
import com.basewin.define.PinpadType;
import com.basewin.log.LogUtil;
import com.basewin.services.PinpadBinder;
import com.basewin.services.ServiceManager;
import com.basewin.utils.AppUtil;
import com.pos.sdkdemo.R;
import com.pos.sdkdemo.base.BaseActivity;
import com.pos.sdkdemo.utils.BCDHelper;
import com.pos.sdkdemo.utils.GlobalData;

public class PinpadTestActivity extends BaseActivity {

    private static final String TAG = PinpadTestActivity.class.getName();
    private TextView tv_pinpad_pw;
    private TextView tv_pinpad_result;
    private VirtualPWKeyboardView pwKeyboard;

    private String defProtectKey = "11111111111111111111111111111111";
    private String defMainKey = "F40379AB9E0EC533F40379AB9E0EC533";
    private String defMainKeyKcv = "82E13665";
    private String defMacKey = "58D46F8C4CA35891C76595E92D499E0F";
    private String defMacKeyKcv = "B865B501";
    private String defPinKey = "58D46F8C4CA35891C76595E92D499E0F";
    private String defPinKeyKcv = "B865B501";
    private String defTDKey = "58D46F8C4CA35891C76595E92D499E0F";
    private String defTDKeyKcv = "B865B501";

    private EditText et_protect;
    private EditText et_main;
    private EditText et_main_kcv;
    private EditText et_mac;
    private EditText et_mac_kcv;
    private EditText et_pin;
    private EditText et_pin_kcv;
    private EditText et_td;
    private EditText et_td_kcv;
    private EditText et_tmkindex;
    private Spinner sp1,sp_area;

    private int type = KeyType.PIN_KEY;
    private int area = 1;
    private int tmkindex = 1;

    @Override
    protected View onCreateView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.activity_pinpad_test, null);
    }

    @Override
    protected void onInitView() {
        setTitle("pinpad");
        initView();
        et_protect.setText(defProtectKey);
        optimizSoftKeyBoard(et_protect);
        et_main.setText(defMainKey);
        et_main_kcv.setText(defMainKeyKcv);
        et_mac.setText(defMacKey);
        et_mac_kcv.setText(defMacKeyKcv);
        et_pin.setText(defPinKey);
        et_pin_kcv.setText(defPinKeyKcv);
        et_td.setText(defTDKey);
        et_td_kcv.setText(defTDKeyKcv);
    }

    private void setHint(String s) {
        Log.e("LOG:", s);
        LOGD(s);
    }

    public void loadProtectKey(View view) {
        String protect = et_protect.getText().toString().trim();
        if (TextUtils.isEmpty(protect)) {
            setHint("protect key  is  null  ！");
            return;
        }
        try {
            boolean iRet = ServiceManager.getInstence().getPinpad().loadProtectKeyByArea(area,protect);
            if (iRet) {
                showToast("load protect Key Success!area = "+area+" protect key = "+protect);
                GlobalData.getInstance().setPinpadVersion(PinpadInterfaceVersion.PINPAD_INTERFACE_VERSION3);
                GlobalData.getInstance().setArea(area);
            } else {
                showToast("load protect Key error!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadMainKey(View view) {
        try {
            //Demo main key
            String main = et_main.getText().toString().trim();
            String mainKcv = et_main_kcv.getText().toString().trim();
            if (TextUtils.isEmpty(main)) {
                setHint("main key  is  null  ！");
                return;
            }
            boolean iRet = false;
            if (TextUtils.isEmpty(mainKcv)) {
                iRet = ServiceManager.getInstence().getPinpad().loadMainKeyByArea(area,tmkindex,main);
            } else {
                iRet = ServiceManager.getInstence().getPinpad().loadMainKeyWithKcvByArea(area,tmkindex,main, mainKcv);
            }
            if (iRet) {
                showToast("load Main Key Success!");
                GlobalData.getInstance().setTmkId(tmkindex);
            } else {
                showToast("error");
            }
        } catch (Exception e) {
            e.printStackTrace();
            setHint("loadMainKey error !");
        }
    }

    public void loadMacKey(View view) {
        try {
            //Demo main key
            String mac = et_mac.getText().toString().trim();
            String macKcv = et_mac_kcv.getText().toString().trim();
            if (TextUtils.isEmpty(mac)) {
                setHint("mac key  is  null  ！");
                return;
            }
            boolean iRet = false;
            if (TextUtils.isEmpty(macKcv)) {
                iRet = ServiceManager.getInstence().getPinpad().loadMacKeyByArea(area,tmkindex,mac, null);
            } else {
                iRet = ServiceManager.getInstence().getPinpad().loadMacKeyByArea(area,tmkindex,mac, macKcv);
            }

            if (iRet) {
                showToast("load mac Key Success!");
            } else {
                showToast("error");
            }
        } catch (Exception e) {
            e.printStackTrace();
            setHint("loadMacKey error !");
        }
    }

    public void loadPinKey(View view) {
        try {
            //Demo main key
            String pin = et_pin.getText().toString().trim();
            String pin_kcv = et_pin_kcv.getText().toString().trim();
            if (TextUtils.isEmpty(pin)) {
                setHint("pin key  is  null  ！");
                return;
            }
            boolean iRet = false;
            if (TextUtils.isEmpty(pin_kcv)) {
                iRet = ServiceManager.getInstence().getPinpad().loadPinKeyByArea(area,tmkindex,pin, null);
            } else {
                iRet = ServiceManager.getInstence().getPinpad().loadPinKeyByArea(area,tmkindex,pin, pin_kcv);
            }

            if (iRet) {
                showToast("load pin Key Success!");
                GlobalData.getInstance().setPinkeyFlag(true);
            } else {
                showToast("load pin Key error");
                showToast("pin key:" + pin);
                showToast("pin kcv:" + pin_kcv);
            }
        } catch (Exception e) {
            e.printStackTrace();
            setHint("loadPinKey error !");
        }
    }

    public void loadTDKey(View view) {
        try {
            //Demo main key
            String tdkey = et_td.getText().toString().trim();
            String td_kcv = et_td_kcv.getText().toString().trim();
            if (TextUtils.isEmpty(tdkey)) {
                setHint("td key  is  null  ！");
                return;
            }
            boolean iRet = false;
            if (TextUtils.isEmpty(td_kcv)) {
                iRet = ServiceManager.getInstence().getPinpad().loadTDKeyByArea(area,tmkindex,tdkey, null);
            } else {
                iRet = ServiceManager.getInstence().getPinpad().loadTDKeyByArea(area,tmkindex,tdkey, td_kcv);
            }

            if (iRet) {
                showToast("load td Key Success!");
            } else {
                showToast("load td Key error");
                showToast("td key:" + tdkey);
                showToast("td kcv:" + td_kcv);
            }
        } catch (Exception e) {
            e.printStackTrace();
            setHint("loadTDKey error !");
        }
    }

    public void calMAC(View view) {
//        KeyType
//        ServiceManager.getInstence().getPinpad().calcMAC()
        String demoData = "55B939BBCEBAC6E0D800229BC6E98BD42BADCC1727A5F243D8";

        try {
            String mac = ServiceManager.getInstence().getPinpad().calcMACByArea(area,tmkindex,demoData, BwPinpadSource.MAC_ECB);
            setHint("calMAC:");
            setHint("demoData :" + demoData);
            setHint("calMAC...");
            setHint("mac key :" + et_mac.getText().toString().trim());
            setHint("demoData mac value :" + mac);

        } catch (Exception e) {
            e.printStackTrace();
            setHint("calMAC error!");
        }
    }

    public void encryptedTrack(View view) {
        String demoData = "6210985200013865013=00001209540611111";
        try {
            String s = ServiceManager.getInstence().getPinpad().encryptMagTrackByArea(area, tmkindex, demoData);
            setHint("encryptedTrack:");
            setHint("demoData :" + demoData);
            setHint("encrypted value :" + s);
        } catch (Exception e) {
            e.printStackTrace();
            setHint("encryptedTrack error!");
        }

    }

    public void inputonlinepin(View view) {
    	Log.d("Test","系统环境：" +AppUtil.getProp("ro.build.type", "user"));
    	AppUtil.setProp(PinpadType.pinpadsupport, "false");
    	try {
			ServiceManager.getInstence().getPinpad().setPinpadMode(GlobalDef.MODE_FIXED);
			ServiceManager.getInstence().getPinpad().setPinpadBeep(true);
//			ServiceManager.getInstence().getPinpad().setPinpadClickBeepAssert("success.ogg");
//			ServiceManager.getInstence().getPinpad().setPinpadVoiceAssert("success.ogg");
//			LogUtil.si(getClass(), "Click文件是否存在"+new File(PinpadType.beepPath+"beep.wav").exists());
//			LogUtil.si(getClass(), "Voice文件是否存在"+new File(PinpadType.beepPath+"voice.wav").exists());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        String cardNumber = getCardNumber();
        if (TextUtils.isEmpty(cardNumber)) {
            setHint("cat Number  is  null !");
            return;
        }
        PWDialog pwDialog = new PWDialog(this,PinpadInterfaceVersion.PINPAD_INTERFACE_VERSION3,area,tmkindex);
        pwDialog.setListener(new PWDialog.PWListener() {
            @Override
            public void onConfirm(byte[] bytes, boolean b) {
            	if (b == false) {
            		setHint("Password:" +new String(bytes));
				}
            	else
            		setHint("Password:null");
            }

            @Override
            public void onCancel() {
                setHint("Password Cancel");
            }

            @Override
            public void onError(int i) {
                setHint("Password Error " +i);
            }
        });
        pwDialog.showForPW(cardNumber);
    }

    private String encryptedData = "";

    public void encryptedData(View view) {
        String demoData = "621098520001122424412421412141209540611111000000";
        try {
            String s = ServiceManager.getInstence().getPinpad().encryptDataByArea(area, tmkindex, type, demoData);
            setHint("encryptedData:");
            setHint("demoData :" + demoData);
            setHint("encrypted value :" + s);
        } catch (Exception e) {
            e.printStackTrace();
            setHint("encryptedData error!");
        }
    }

    public void decryptData(View view) {
        if (TextUtils.isEmpty(encryptedData)) {
            encryptedData = "D384769820F592469360AB286D0B62681C5EC8D67800DB4B";
        }
        try {
            String s = ServiceManager.getInstence().getPinpad().decryptDataByArea(area, tmkindex, type, encryptedData);
            setHint("decryptData:");
            setHint("demoData :" + encryptedData);
            setHint("decrypt value :" + s);
        } catch (Exception e) {
            e.printStackTrace();
            setHint("decryptData error!");
        }
    }


    private void initView() {
        et_protect = (EditText) findViewById(R.id.et_protect);
        et_main = (EditText) findViewById(R.id.et_main);
        et_main_kcv = (EditText) findViewById(R.id.et_main_kcv);
        et_mac = (EditText) findViewById(R.id.et_mac);
        et_mac_kcv = (EditText) findViewById(R.id.et_mac_kcv);
        et_pin = (EditText) findViewById(R.id.et_pin);
        et_pin_kcv = (EditText) findViewById(R.id.et_pin_kcv);
        et_td = (EditText) findViewById(R.id.et_td);
        et_td_kcv = (EditText) findViewById(R.id.et_td_kcv);
        et_tmkindex = (EditText)findViewById(R.id.et_tmkindex);
        et_tmkindex.setText("1");
        tmkindex = 1;
        sp1 = (Spinner) findViewById(R.id.spinner);
        sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        PinpadTestActivity.this.type = KeyType.PIN_KEY;
                        break;
                    case 1:
                        PinpadTestActivity.this.type = KeyType.TRACK_KEY;
                        break;
                    case 2:
                        PinpadTestActivity.this.type = KeyType.MAC_KEY;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        sp_area = (Spinner)findViewById(R.id.sp_pinpad_area);
        sp_area.setSelection(0);
        area = 1;
        sp_area.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                area = i+1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    /**
     * cardno for test
     * @return
     */
    private String getCardNumber() {
        return "6210885200013000000";
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void showResult(String msg) {
        tv_pinpad_result.setVisibility(View.VISIBLE);
        pwKeyboard.setVisibility(View.GONE);
        tv_pinpad_result.setText(msg);
    }

    private void showToast(String msg) {
        setHint(msg);
//        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public void enterPinpadTestVersion1(View view)
    {

    }

    public void enterPinpadTestVersion2(View view)
    {

    }
    
    public void enterPinpadTestDukpt(View view)
    {
        Intent intent = new Intent(this, PinpadTestActivityForDukpt.class);
        startActivity(intent);
    }

    private int getTmkIndex()
    {
        String index = et_tmkindex.getText().toString();
        if (index != null)
        {
            if (Integer.parseInt(index) >= 1 && Integer.parseInt(index) <=1000)
                return Integer.parseInt(index);
        }
        return 1;
    }

    /**
     * format pinpad ,clear all keys
     * @param view
     */
    public void format(View view)
    {
        try {
            ServiceManager.getInstence().getPinpad().format();
            LOGD("format success!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
