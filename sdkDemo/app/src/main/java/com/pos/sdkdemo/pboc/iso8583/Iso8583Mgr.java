package com.pos.sdkdemo.pboc.iso8583;

import android.content.Context;
import android.util.Log;
import com.basewin.packet8583.exception.Packet8583Exception;
import com.basewin.packet8583.factory.Iso8583Manager;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * @author zhongrenshun
 * @date
 * @function
 * @lastmodify
 */
public class Iso8583Mgr {
    private static final String TAG = "Iso8583Mgr";
    private Iso8583Manager manager;
    private Iso8583Manager manager2;

    public Iso8583Mgr(Context context) {
        manager = new Iso8583Manager(context);
        manager.Load8583XMLconfigByTag("ISO8583Config");
        manager2 = new Iso8583Manager(context);
        manager2.Load8583XMLconfigByTag("ISO8583Config");
    }

    /*
     * 封包
     * packet
     */
    public byte[] packData() throws Packet8583Exception, UnsupportedEncodingException {
        manager.setBit("tpdu", "6000030000");
        manager.setBit("header", "8888888888");
        manager.setBit("msgid", "0430");
        manager.setBit("2", "470000");
        manager.setBit("3", "111111");
        manager.setBit("4", "000000000001");
        manager.setBit("8", "11111111111");
        manager.setBit("11", "222222");
        manager.setBit("12", "333333");
        manager.setBit("13", "4444");
        manager.setBit("14", "5555");
        manager.setBit("15", "6666");
        manager.setBit("22", "220");
        manager.setBit("23", "200");
        manager.setBit("24", "7777");
        manager.setBit("25", "25");
        manager.setBit("26", "26");
        manager.setBit("32", "88888");
        manager.setBit("35", "999999");
        manager.setBit("36", "10101010101010101010");
        manager.setBit("37", "111111111111");
        manager.setBit("38", "222222");
        manager.setBit("39", "00");
        manager.setBit("41", "12345678");
        manager.setBit("42", "123456789012345");
        manager.setBit("44", "0000000000");
        manager.setBit("48", "12345678");
        manager.setBit("49", "000");
        manager.setBinaryBit("52", new byte[]{(byte) 0xff, (byte) 0x00, (byte) 0xff, (byte) 0x00, (byte) 0xff, (byte) 0x00, (byte) 0xff, (byte) 0x00});
        manager.setBit("53", "12345678");
        manager.setBit("54", "00000000");
        // 设置binary域提供了两种方法，一种是bit字符串，一种是byte数组，两种皆可
        manager.setBinaryBit("55", new byte[]{(byte) 0xff, (byte) 0x00, (byte) 0xff, (byte) 0x00});
        // manager.setBit("55", "11111111000000001111111100000000");
        manager.setBit("58", "0000000000000000");
        manager.setBit("60", "12345678901234567890");
        manager.setBit("62", "11111111000000001111111100000000");
        manager.setBit("63", "12345678901234567890");
        manager.setBinaryBit("64", new byte[]{(byte) 0xff, (byte) 0x00, (byte) 0xff, (byte) 0x00, (byte) 0xff, (byte) 0x00, (byte) 0xff, (byte) 0x00});
        // manager.setBit("64",
        // "1111111100000000111111110000000011111111000000001111111100000000");

        // 打包调用
        byte[] needunpack = null;
        try {
            needunpack = manager.pack();
        } catch (Packet8583Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return needunpack;

    }

    /*
     * 解包
     * unpack
     */
    public void unpackData(byte[] needunpack) {
        // 测试解包
        //unpack test
        int len = Integer.valueOf(needunpack[0]) * 255 + Integer.valueOf(needunpack[1]) & 0xff;
        byte[] needunpack2 = new byte[len];
        // 解包需要去掉前面两个字节的长度数据
        //Need to get rid of the previous data,This data is two bytes
        //unpack
        System.arraycopy(needunpack, 2, needunpack2, 0, len);
        try {
            manager2.unpack(needunpack2);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // Log.d("8583", "解包数据为 = " +
        // BCDHelper.hex2DebugHexString(manager2.pack(),
        // manager2.pack().length));

        //解包后获取各个域的数据
        //After unpacking, can get the domain of data
        String data_2 = manager2.getBit("2");
        String data_3 = manager2.getBit(3);
        Log.d(TAG, "data_2 = " + data_2 + "  data_3 = " + data_3);
    }


    public String getBitData(int id) {
        return manager2.getBit(id);
    }

    public String getBitData(String id) {
        return manager2.getBit(id);
    }


}
