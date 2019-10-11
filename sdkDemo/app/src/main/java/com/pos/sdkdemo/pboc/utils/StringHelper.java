package com.pos.sdkdemo.pboc.utils;

import android.text.TextUtils;
import com.basewin.log.LogUtil;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * @author liudeyu
 * @version 1.0.0
 * @date 2016年2月29日 下午2:54:59
 * @function
 * @lastmodify
 */
public class StringHelper {
    private final static int MAXAMTNUM = 12;
    private final String TAG = "StringHelper";

    /**
     * 格式化金额
     * Format the amount
     *
     * @param amtStr
     */
    public static String changeAmout(String amtStr) {
        String str = amtStr.toString();
        String cuttedStr = str;
        /* 删除字符串中的dot */
        //Delete dot  in the string
        for (int i = str.length() - 1; i >= 0; i--) {
            char c = str.charAt(i);
            if ('.' == c) {
                cuttedStr = str.substring(0, i) + str.substring(i + 1);
                break;
            }
        }
		/* 删除前面多余的0 */
        //Delete the previous 0
        int NUM = cuttedStr.length();
        int zeroIndex = -1;
        for (int i = 0; i < NUM - 2; i++) {
            char c = cuttedStr.charAt(i);
            if (c != '0') {
                zeroIndex = i;
                break;
            } else if (i == NUM - 3) {
                zeroIndex = i;
                break;
            }
        }
        if (zeroIndex != -1) {
            cuttedStr = cuttedStr.substring(zeroIndex);
        }
		/* 不足3位补0 */
        //If the length is less than 3 adding 0
        if (cuttedStr.length() < 3) {
            cuttedStr = "0" + cuttedStr;
        }

        // 最长12位
        //max length is 12
        if (str.length() > MAXAMTNUM) {
            cuttedStr = cuttedStr.substring(0, MAXAMTNUM);
            LogUtil.e(StringHelper.class, "最大输入" + MAXAMTNUM + "位金额");
        }
		/* 加上dot，以显示小数点后两位 */
        //need  to two decimal places ,add dot
        if (cuttedStr.length() > 2) {
            cuttedStr = cuttedStr.substring(0, cuttedStr.length() - 2) + "." + cuttedStr.substring(cuttedStr.length() - 2);
        }
        return cuttedStr;
    }

    /**
     * 去掉字符串中的冒号
     * emove the point of the string
     */
    public static String getStringWithoutDot(String srcString) {
        return srcString.replace(".", "");
    }

    /**
     * 屏蔽卡号
     * Shielding card number
     *
     * @param cardNum
     * @param hidden
     * @return
     * @Title: formatCardNum
     * @Description: TODO
     * @return: String
     */
    public static String formatCardNum(String cardNum, boolean hidden) {
        if (cardNum == null)
            return "";
        if (cardNum.length() < 13 || cardNum.length() > 20)
            return "";

        String Number = "";
        String cardF = cardNum.substring(0, 6);
        String cardB = cardNum.substring(cardNum.length() - 4);
        String padd = "*******************";
        if (hidden) {
            Number = cardF + padd.substring(0, cardNum.length() - 10) + cardB;
        } else {
            Number = cardNum;
        }
        return Number;
    }

    /**
     * @param str
     * @param num
     * @Title: fillFrontSpace
     * @Description: 字符前空格填充
     * @author xieruihua
     * @return: String
     */
    public static String fillFrontSpace(Object obj, int num) {
        if (obj == null) {
            return "";
        }
        int max = String.valueOf(obj).replaceAll("[\u4e00-\u9fa5]", "aa").length();
        int min = String.valueOf(obj).length();
        return String.format("%1$" + (num + min - max) + "s", obj);
    }

    /**
     * @param str
     * @param num
     * @Title: fillBackSpace
     * @Description: 字符后空格填充
     * @author xieruihua
     * @return: String
     */
    public static String fillBackSpace(Object obj, int num) {
        if (obj == null) {
            return "";
        }
        int max = String.valueOf(obj).replaceAll("[\u4e00-\u9fa5]", "aa").length();
        int min = String.valueOf(obj).length();
        return String.format("%-" + (num + min - max) + "s", obj);
    }

    /**
     * @param amt
     * @Title: formatAmout
     * @Description: 格式化金额
     * @author xieruihua
     * @return: String
     */
    public static String formatAmout(double amt) {
        DecimalFormat df = new DecimalFormat("#########0.00");
        String am = df.format(amt).replace(".", "");
        return String.format("%0" + Math.max(12 - am.length(), 1) + "d", 0) + am;
    }

    /**
     * @param timestring
     * @Title: formatTimeStamp
     * @Description: 时间字符串格式化yyyy/MM/dd hh:mm:ss
     * @return: String
     */
    public static String formatTimeStamp(String timestamp) {
        if (timestamp == null) {
            return "";
        }
        try {
            Date date = new SimpleDateFormat("yyyyMMddhhmmss").parse(timestamp);
            return new SimpleDateFormat("yyyy/MM/dd hh:mm:ss").format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 是否A大于B，用于判断金额字符串
     *
     * @return
     */
    public static boolean ifBigerThan(String amt1, String amt2) {
        Long lamt1 = Long.parseLong(amt1.replace(".", ""));
        Long lamt2 = Long.parseLong(amt2.replace(".", ""));
        return (lamt1 >= lamt2 ? true : false);
    }

    /**
     * 是否A大于B，用于判断金额字符串
     *
     * @return
     */
    public static boolean ifBigerThan2(String amt1, String amt2) {
        Long lamt1 = Long.parseLong(amt1.replace(".", ""));
        Long lamt2 = Long.parseLong(amt2.replace(".", ""));
        return (lamt1 > lamt2 ? true : false);
    }

    /**
     * @param maxAmt
     * @param minAmt
     * @Title: getNoRefundAmount
     * @Description: 获取还未退金额
     * @return: String
     */
    public static String getNoRefundAmount(String maxAmt, String minAmt) {
        if (TextUtils.isEmpty(maxAmt) || TextUtils.isEmpty(minAmt)) {
            throw new IllegalArgumentException("the input parameters can't be empty!");
        }
        DecimalFormat df = new DecimalFormat("#########0.00");
        double differ = Double.valueOf(maxAmt) - Double.valueOf(minAmt);
        return df.format(Math.abs(differ));
    }
}
