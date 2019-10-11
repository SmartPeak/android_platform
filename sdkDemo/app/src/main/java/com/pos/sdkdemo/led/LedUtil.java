package com.pos.sdkdemo.led;

import com.basewin.services.ServiceManager;

/**
 * 作者: lyw <br>
 * 内容摘要: <br>
 * 创建时间:  2016/9/5 15:31<br>
 */
public class LedUtil {

    /**
     * 打开一个led
     * open  a led
     * @param led
     */
    public static void open(int led ) {
        try {
            ServiceManager.getInstence().getLed().enableLedIndex(led, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 控制一个led,打开或者关闭,开启时间onMs,关闭时间offMs
     * control a led  ,open  or  close,onTime:onMs,offTime:offMs
     * @param led
     */
    public static void open(int led ,int onMs , int offMs) {
        try {
            ServiceManager.getInstence().getLed().enableLedIndex(led, onMs, offMs);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭led
     * close  a  led
     * @param led
     */
    public static void close(int led ) {
        try {
            ServiceManager.getInstence().getLed().enableLedIndex(led, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 关闭led
     * close  all  led
     * @param
     */
    public static void closeAll( ) {
        try {
            ServiceManager.getInstence().getLed().enableLedIndex(BwLedType.LED_BLUE, false);
            ServiceManager.getInstence().getLed().enableLedIndex(BwLedType.LED_GREEN, false);
            ServiceManager.getInstence().getLed().enableLedIndex(BwLedType.LED_YELLOW, false);
            ServiceManager.getInstence().getLed().enableLedIndex(BwLedType.LED_RED, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
