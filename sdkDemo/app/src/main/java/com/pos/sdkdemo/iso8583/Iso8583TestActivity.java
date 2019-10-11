package com.pos.sdkdemo.iso8583;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.basewin.log.LogUtil;
import com.basewin.packet8583.factory.Iso8583Manager;
import com.basewin.services.ServiceManager;
import com.pos.sdkdemo.R;
import com.pos.sdkdemo.base.BaseActivity;
import com.pos.sdkdemo.interfaces.OnEnterListener;
import com.pos.sdkdemo.utils.BCDHelper;
import com.pos.sdkdemo.widgets.EnterDialog;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class Iso8583TestActivity extends BaseActivity {
    Iso8583Manager miso8583Manager = null;
    private String bit_id = "1";
    private String bit_value = "";
    private EditText edit_iso_config;

    @Override
    protected View onCreateView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.activity_iso8583_test, null);
    }

    @Override
    protected void onInitView() {
        setTitle("ISO8583");
        initView();
    }

    public void setBitValue(View view) {
        //enter bit id
        new EnterDialog(Iso8583TestActivity.this).showEnterDialog("please enter bit id", new OnEnterListener() {
            @Override
            public void onEnter(String text) {
                bit_id = text;
                //enter bit value
                new EnterDialog(Iso8583TestActivity.this).showEnterDialog("please enter bit value", new OnEnterListener() {
                    @Override
                    public void onEnter(String text) {
                        bit_value = text;
                        //set bit
                        Iso8583Manager iso8583Manager = get8583();
                        try {
                            LogUtil.i(getClass(), "bit_id = " + bit_id);
                            LogUtil.i(getClass(), "bit_value = " + bit_value);
                            LogUtil.i(getClass(), "iso8583Manager.hashcode = " + iso8583Manager.hashCode());
                            iso8583Manager.setBit(bit_id, bit_value);
                        } catch (UnsupportedEncodingException e) {
                            Toast.makeText(Iso8583TestActivity.this, "setting error", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                });
            }
        });

    }

    public void deleteBitValue(View view) {
        //enter bit id
        new EnterDialog(Iso8583TestActivity.this).showEnterDialog("please enter bit id", new OnEnterListener() {
            @Override
            public void onEnter(String text) {
                bit_id = text;
                //delete bit
                Iso8583Manager iso8583Manager = get8583();
                try {
                    iso8583Manager.deleteBit(bit_id);
                } catch (Exception e) {
                    Toast.makeText(Iso8583TestActivity.this, "setting error", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });

    }

    public void Switch8583config(View view) {
        Iso8583Manager iso = new Iso8583Manager(this);
        String p = "";
        String p1 = "";
        try {
            setTest8583value(iso);
            byte[] pack = iso.pack();
            p = BCDHelper.bcdToString(pack);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Iso8583Manager iso8583Manager = getNew8583();
        try {
            p1 = BCDHelper.bcdToString(iso8583Manager.pack());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String msg = "config : " + "ISO8583Config" + " \n";
        msg += "msg :" + p + "\n";

        msg += "config : " + get8583Config() + " \n";
        msg += "msg :" + p1 + "\n";
        setShowResult(msg);

    }

    public void pack8583(View view) {
        try {
            byte[] pack = get8583().pack();
            setShowResult("pack :" + BCDHelper.bcdToString(pack));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void unpack8583(View view) {
        try {
            byte[] pack = get8583().pack();

            Iso8583Manager iso = new Iso8583Manager(this);
            String config = get8583Config();
            if (!TextUtils.isEmpty(config)) {
                set8583config(iso, config);
            }
            iso.unpack(pack);
            setShowResult(getIsoMsgAll(iso));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showMacData(View view) {
        Iso8583Manager iso8583Manager = get8583();
        try {
            byte[] msgids = iso8583Manager.getMacData("msgid", "63");
            setShowResult(" You must determine that the Transmission key and master key and the work key has been loaded.! \n" + BCDHelper.bcdToString(msgids));
        } catch (Exception e) {
            Toast.makeText(this, "setting error", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    public void submit(View view) {
        try {
            Iso8583Manager iso8583Manager = get8583();
            String isoMsgAll = getIsoMsgAll(iso8583Manager);
            setShowResult(isoMsgAll);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "error  Please check the length of each domain!", Toast.LENGTH_SHORT).show();
        }
    }

    //Please set according to the tag in the XML file
    // ISO8583Config or ISO8583Config-option
    private Iso8583Manager set8583config(Iso8583Manager iso8583Manager, String xmltag) {
        iso8583Manager.Load8583XMLconfigByTag(xmltag);
        return iso8583Manager;
    }

    private Iso8583Manager get8583() {
        if (miso8583Manager == null) {
            miso8583Manager = new Iso8583Manager(this);

            // warning   warning    warning  warning
            // test   value.
            String config = get8583Config();
            if (!TextUtils.isEmpty(config)) {
                set8583config(miso8583Manager, config);
            } else {
                Toast.makeText(this, "config is not null !", Toast.LENGTH_SHORT).show();
            }
            // test   value.
            try {
                setTest8583value(miso8583Manager);
            } catch (Exception e) {
                Toast.makeText(this, "error  Please check the length of each domain!", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
        return miso8583Manager;
    }

    private Iso8583Manager getNew8583() {
        miso8583Manager = null;
        return get8583();
    }

    private void unpack(Iso8583Manager iso8583Manager, byte[] bytes) {
        try {
            iso8583Manager.unpack(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private byte[] pack(Iso8583Manager iso8583Manager) {
        try {
            return iso8583Manager.pack();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }

    private String getIsoBit(Iso8583Manager iso8583Manager, String key) {
        String bit = iso8583Manager.getBit(key);
        return bit;
    }

    private Iso8583Manager setTest8583value(Iso8583Manager packManager) throws Exception {
        packManager.setBit("tpdu", "6000030000");
        packManager.setBit("header", "613100310003");
        packManager.setBit("preposition", "123456789012345678901234567890123456789012345678901234567890123456789");
        packManager.setBit("msgid", "0200");
        packManager.setBit(3, "000000");
        packManager.setBit(4, "000000000001");
        packManager.setBit(22, "051");
        packManager.setBit(25, "00");
        packManager.setBit(26, "12");
        packManager.setBit(37, "123456789012");
        packManager.setBit(49, "156");
        packManager.setBit(53, "2600000000000000");
        packManager.setBinaryBit(59, new byte[]{(byte) 0xef, 0x1e, 0x2f, 0x3d, (byte) 0xab, (byte) 0xcd, (byte) 0xef, (byte) 0xff});
        packManager.setBit(60, "22" + "000001" + "000" + "6" + "01");
        packManager.setBit(64, "0000000000000000");
        byte[] macInput = packManager.getMacData("msgid", "63");
        Log.e("size:", macInput.length + "");
        String s = ServiceManager.getInstence().getPinpad().calcMACByArea(1, 1, BCDHelper.bcdToString(macInput), 2);
        Log.e("s:", s.length() + "");
        packManager.setBit(64, s);
        return packManager;
    }

    private String getIsoMsgAll(Iso8583Manager iso8583Manager) {
        String msg = "";
        String tpdu = iso8583Manager.getBit("tpdu");
        String header = iso8583Manager.getBit("header");
        String msgid = iso8583Manager.getBit("msgid");
        msg += TextUtils.isEmpty(tpdu) ? "" : "tpdu:" + tpdu + " \n";
        msg += TextUtils.isEmpty(header) ? "" : "header:" + header + " \n";
        msg += TextUtils.isEmpty(msgid) ? "" : "msgid:" + msgid + " \n";
        for (int i = 0; i < 65; i++) {
            String bit = iso8583Manager.getBit(i);
            if (!TextUtils.isEmpty(bit)) {
                msg += i + ": " + bit + "\n";
            }
        }
        return msg;
    }


    private void initView() {
//        tv_8583_result = (TextView) findViewById(R.id.tv_8583_result);
        edit_iso_config = (EditText) findViewById(R.id.edit_iso_config);
        optimizSoftKeyBoard(edit_iso_config);
    }

    private void setShowResult(String msg) {
        LOGD(msg);
    }

    private String get8583Config() {
        return edit_iso_config.getText().toString().trim();
    }

}
