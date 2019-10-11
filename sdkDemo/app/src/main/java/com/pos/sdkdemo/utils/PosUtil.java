package com.pos.sdkdemo.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.basewin.define.CardType;
import com.basewin.utils.StringUtil;
import com.pos.sdkdemo.trade.bean.Card;
import com.pos.sdkdemo.trade.Config;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 内容摘要: <br>
 * 创建时间:  2016/7/18 16:46<br>
 * 描述:  <br>
 */
public class PosUtil {
    /**
     * 分 to 元
     *
     * @param amtStr
     * @return
     */
    public static String changeAmout(String amtStr) {
        String str = amtStr;
        String cuttedStr = str;
        /* 删除字符串中的dot */
        for (int i = str.length() - 1; i >= 0; i--) {
            char c = str.charAt(i);
            if ('.' == c) {
                cuttedStr = str.substring(0, i) + str.substring(i + 1);
                break;
            }
        }
        /* 删除前面多余的0 */
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
        if (cuttedStr.length() < 3) {
            cuttedStr = "000".substring(0, 3 - cuttedStr.length()) + cuttedStr;
        }

        // 最长12位
        if (str.length() > 12) {
            cuttedStr = cuttedStr.substring(0, 12);
        }

		/* 加上dot，以显示小数点后两位 */
        if (cuttedStr.length() > 2) {
            cuttedStr = cuttedStr.substring(0, cuttedStr.length() - 2) + "."
                    + cuttedStr.substring(cuttedStr.length() - 2);
        }

        return cuttedStr;
    }

    //专用域 批次和流水的转换成6位的格式
    public static String numToStr6(String s) {
        if (TextUtils.isEmpty(s)) {
            s = "000001";
        }
        if (s.length() > 6) {
            s = "000001";
        } else if (s.length() < 6) {
            StringBuffer str = new StringBuffer();
            for (int i = 0; i < 6 - s.length(); i++) {
                str.append("0");
            }
            s = str.append(s).toString().trim();
        } else {

        }
        return s;
    }

    //专用域 金额的转换成12位的格式
    public static String numToStr12(String s) {
        if (TextUtils.isEmpty(s)) {
            s = "000001";
        }
        if (s.length() > 12) {
            s = "000001";
        } else if (s.length() < 12) {
            StringBuffer str = new StringBuffer();
            for (int i = 0; i < 12 - s.length(); i++) {
                str.append("0");
            }
            s = str.append(s).toString().trim();
        } else {

        }
        return s;
    }

    public static String strTo30(String s) {
        if (TextUtils.isEmpty(s)) {
            s = "";
        }
        if (s.length() > 30) {
            return s;
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 30 - s.length(); i++) {
            sb.append(" ");
        }
        sb.append(s, 0, s.length());
        return sb.toString();
    }

    /**
     * 左补0
     *
     * @param s
     * @return
     */
    public static String strTo0(String s, int l) {
        if (TextUtils.isEmpty(s)) {
            s = "";
        }
        if (s.length() > l) {
            return s;
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < l - s.length(); i++) {
            sb.append("0");
        }
        sb.append(s, 0, s.length());
        return sb.toString();
    }

    //专用域 批次和流水 字符串数字增加1 并且返回6位的字符串数字
    public static String StrNumAuto(String s) {
        int i = Integer.parseInt(s);
        i++;
        String s1 = String.valueOf(i);
        return numToStr6(s1);
    }

    /**
     * 分转元
     *
     * @param msg
     * @return
     */
    public static String centToYuan(String msg) {
        if (!msg.matches(CURRENCY_FEN_REGEX)) {
            return "";
        }
        return BigDecimal.valueOf(Long.valueOf(msg)).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP).toString();
    }

    public static String yuanTo12(String msg) {
        if (TextUtils.isEmpty(msg)) {
            return "000000000000";
        }
        StringBuilder sb = new StringBuilder();
        msg = msg.replace(".", "");
        if (msg.length() < 12) {
            int i = 12 - msg.length();
            for (int j = 0; j < i; j++) {
                sb.append("0");
            }
        }
        sb.append(msg);
        return sb.toString();
    }

    /**
     * 金额为分的格式
     */
    public static final String CURRENCY_FEN_REGEX = "\\-?[0-9]+";

    /**
     * 将分为单位的转换为元并返回金额格式的字符串 （除100）
     *
     * @param amount
     * @return
     * @throws Exception
     */
    public static String fenToYuan(String amount) throws Exception {
        if (!amount.toString().matches(CURRENCY_FEN_REGEX)) {
            throw new Exception("金额格式有误");
        }
        int i1 = Integer.parseInt(amount);
        int flag = 0;
        String amString = String.valueOf(i1);
        if (amString.charAt(0) == '-') {
            flag = 1;
            amString = amString.substring(1);
        }
        StringBuffer result = new StringBuffer();
        if (amString.length() == 1) {
            result.append("0.0").append(amString);
        } else if (amString.length() == 2) {
            result.append("0.").append(amString);
        } else {
            String intString = amString.substring(0, amString.length() - 2);
            for (int i = 1; i <= intString.length(); i++) {
                if ((i - 1) % 3 == 0 && i != 1) {
                    result.append(",");
                }
                result.append(intString.substring(intString.length() - i, intString.length() - i + 1));
            }
            result.reverse().append(".").append(amString.substring(amString.length() - 2));
        }
        if (flag == 1) {
            return "-" + result.toString();
        } else {
            return result.toString();
        }
    }

    /**
     * 通过卡类型 返回 22域服务点数据
     * 通过读卡方式 得到这个 服务点参数
     */
    public static String _22(Card card) {
        StringBuilder code = new StringBuilder();
        switch (card.type) {
            case CardType.MAG_CARD:
                code.append("02");
                break;
            case CardType.RF_CARD:
                code.append("07");
                break;
            case CardType.IC_CARD:
                code.append("05");
                break;
            case 1:
                code.append("01");
                break;
            default:
                code.append("00");
                break;
        }
        if (card.password == null) {
            code.append("2");
        } else {
            code.append("1");
        }
        return code.toString();
    }

    public static boolean isICBy22(String _22) {
        String s = "00";
        try {
            s = _22.substring(0, 2);
        } catch (Exception e) {
            Log.d("error","22域转成刷卡类型码时异常！");
        }
        if ("05".equals(s) || "07".equals(s)) {
            return true;
        }
        return false;
    }

    public static int cardTypeBy22(String _22) {
        String s = "00";
        try {
            s = _22.substring(0, 2);
        } catch (Exception e) {
            Log.d("error","22域转成刷卡类型码时异常！");
        }
        if ("05".equals(s)) {
            return CardType.IC_CARD;
        } else if ("07".equals(s)) {
            return CardType.RF_CARD;
        } else if ("02".equals(s)) {
            return CardType.MAG_CARD;
        } else {
            return -1;
        }
    }

    public static String moto62(String cv, String sf, String tx, String nm) {
        StringBuffer sb = new StringBuffer("92");
        if (!TextUtils.isEmpty(cv) && cv.length() == 3) {
            sb.append("CV003" + cv);
        }
        if (!TextUtils.isEmpty(sf) && sf.length() == 6) {
            sb.append("SF006" + sf);
        }
        if (!TextUtils.isEmpty(tx) && tx.length() == 11) {
            sb.append("TX011" + tx);
        }
        if (!TextUtils.isEmpty(nm) && nm.length() < 30) {
            String s = String.valueOf(nm.length());
            sb.append("TX" + strTo0(s, 3) + tx);
        }
        return sb.toString();
    }

    /**
     * 屏蔽卡号
     * Shielding card number
     *
     * @param cardNum
     * @param hidden
     * @return
     * @Title: formatCardNum
     * @author liudeyu
     * @Description: TODO
     * @return: String
     */
    public static String formatPan(String cardNum, boolean hidden) {
        if (cardNum == null) {
            return "";
        }
        if (cardNum.length() < 13 || cardNum.length() > 20) {
            return "";
        }

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
     * MMDD  to  MM/DD
     *
     * @param msg
     * @return
     */
    public static String fromatMMDD(String msg) {
        if (TextUtils.isEmpty(msg)) {
            return "";
        }
        StringBuffer sb = new StringBuffer(msg);
        try {
            msg = sb.insert(2, "/").toString();
        } catch (Exception e) {
            Log.e("error","MMDD  to  MM/DD 失败");
        }
        return msg;
    }

    /**
     * tlv解包
     *
     * @param data
     * @param offset
     * @return
     */
    public static short[] TLVParse(byte[] data, int offset) {
        if (data != null && offset >= 0 && data.length > (offset + 2)) {
            int i = offset;
            short tag = 0;
            short valueOffset = 0;
            short valueLength = 0;

            // TAG标签的属性为bit，由16进制表示，占1～2个字节长度。
            // 若tag标签的第一个字节（注：字节排序方向为从左往右数，
            // 第一个字节即为最左边的字节。bit排序规则同理。）的后
            // 四个bit为“1111”，则说明该tag占两个字节，例如“9F33”；
            // 否则占一个字节，例如“95”。
            if ((data[i] & 0x0F) == 0x0F) {
                tag = byte2ToShort(data, i);
                i++;
            } else {
                tag = (short) (data[i] & 0xFF);
            }
            i++;
            // 子域长度（即L本身）的属性也为bit，占1～3个字节长度。具体编码规则如下：
            // a)当L字段最左边字节的最左bit位（即bit8）为0，表示该L字段占一个字节，
            // 它的后续7个bit位（即bit7～bit1）表示子域取值的长度，采用二进制数
            // 表示子域取值长度的十进制数。例如，某个域取值占3个字节，那么其子域
            // 取值长度表示为“00000011”。所以，若子域取值的长度在1～127字节之间，
            // 那么该L字段本身仅占一个字节。
            // b)当L字段最左边字节的最左bit位（即bit8）为1，表示该L字段不止占一个
            // 字节，那么它到底占几个字节由该最左字节的后续7个bit位（即bit7～bit1）
            // 的十进制取值表示。例如，若最左字节为10000010，表示L字段除该字节外，
            // 后面还有两个字节。其后续字节的十进制取值表示子域取值的长度。例如，
            // 若L字段为“1000 0001 1111 1111”，表示该子域取值占255个字节。所以，
            // 若子域取值的长度在127～255字节之间，那么该L字段本身需占两个字节。
            int ll = 1;// 代表有几个字节字节的长度
            if ((data[i] & 0x80) == 0x80) {
                ll = data[i] & 0x7F;
                i++;
            }
            if (ll == 2) {
                valueLength = byte2ToShort(data, i);
                i++;
            } else {
                valueLength = (short) (data[i] & 0xFF);
            }
            i++;
            valueOffset = (short) i;// 代表偏移多少字节是这个TAG代表的值
            short[] taginfo = new short[3];
            taginfo[0] = tag;
            taginfo[1] = valueOffset;
            taginfo[2] = valueLength;
            return taginfo;
        }
        return null;
    }

    /**
     * tlv打包
     *
     * @param src
     * @param src_offset
     * @param dst
     * @param dst_offset
     * @param length
     * @return
     */
    public static int TLVAppend(short tag, byte[] src, int src_offset, byte[] dst, int dst_offset, int length) {
        int i = dst_offset;
        byte[] buffer = shortToByte2(tag);
        if ((buffer[0] & 0xF) == 15) {
            System.arraycopy(buffer, 0, dst, i, 2);
            i++;
        } else {
            dst[i] = buffer[1];
        }
        i++;
        if (length <= 127) {
            dst[i] = ((byte) (length & 0xFF));
        } else if (length <= 255) {
            dst[i] = -127;
            i++;
            dst[i] = ((byte) (length & 0xFF));
        } else {
            dst[i] = -126;
            i++;
            dst[i] = ((byte) (length / 256));
            i++;
            dst[i] = ((byte) (length % 256));
        }
        i++;
        System.arraycopy(src, src_offset, dst, i, length);
        i += length;

        return i;
    }

    public static short byte2ToShort(byte[] buffer, int offset) {
        return (short) bytesToNumber(buffer, offset, 2, false);
    }

    public static long bytesToNumber(byte[] buffer, int offset, int length, boolean bigEndian) {
        if (buffer == null || offset < 0 || length < 1) {
            throw new NullPointerException("invalid byte array ");
        }
        if (buffer.length < (offset + length)) {
            throw new IndexOutOfBoundsException("invalid len: " + buffer.length);
        }
        long number;
        length = offset + length;
        if (bigEndian) {
            int i = length - 1;
            number = (buffer[i] & 0xff);
            i--;
            for (; i >= offset; i--) {
                number = number << 8;
                number = number | (buffer[i] & 0xff);
            }
        } else {
            int i = offset;
            number = (buffer[i] & 0xff);
            i++;
            for (; i < length; i++) {
                number = number << 8;
                number = number | (buffer[i] & 0xff);
            }
        }
        return number;
    }

    public static byte[] shortToByte2(short number) {
        byte[] buffer = new byte[2];
        numberToBytes(number, buffer, 0, buffer.length, false);
        return buffer;
    }

    public static int numberToBytes(long number, byte[] buffer, int offset, int length, boolean bigEndian) {
        long tmp_num = number;
        if (buffer == null || offset < 0 || length < 1 || buffer.length < (offset + length)) {
            return -1;
        }
        length = offset + length;

        if (bigEndian) {
            for (int i = offset; i < length; i++) {
                buffer[i] = (byte) (tmp_num & 0xff);
                tmp_num = tmp_num >> 8;
            }
        } else {
            for (int i = length - 1; i >= offset; i--) {
                buffer[i] = (byte) (tmp_num & 0xff);
                tmp_num = tmp_num >> 8;
            }
        }
        return offset;
    }

    public static int parseF55(byte[] F55_Field) {
        int i = 0;
        short[] tagInfo = null;
        byte[] buffer = null;

        for (i = 0; i < F55_Field.length; ) {
            tagInfo = TLVParse(F55_Field, i);
            if (tagInfo != null && tagInfo.length == 3 && F55_Field.length >= (tagInfo[1] + tagInfo[2])) {
                i = tagInfo[1];
                int length = tagInfo[2];
                buffer = new byte[length];
                System.arraycopy(F55_Field, i, buffer, 0, length);

                i = tagInfo[1] + tagInfo[2];
            }
        }

        return 0;
    }

    /**
     * TLV  tag,length,value      TLVParse [9F36][2][06 CC]
     *
     * @param F55_Field
     * @param istag     true 带tag带长度的  例子：9F36206CC  false:06CC
     * @param tag       例子：9F36  只取9F36d 数据
     * @return
     */
    public static String parseF55(byte[] F55_Field, boolean istag, String... tag) {
        if (F55_Field == null) {
            return "";
        }
        int i = 0;
        short[] tagInfo = null;
        byte[] buffer = null;
        StringBuffer sb = new StringBuffer();

        for (i = 0; i < F55_Field.length; ) {
            tagInfo = TLVParse(F55_Field, i);
            if (tagInfo != null && tagInfo.length == 3 && F55_Field.length >= (tagInfo[1] + tagInfo[2])) {
                i = tagInfo[1];
                int length = tagInfo[2];
                buffer = new byte[length];
                System.arraycopy(F55_Field, i, buffer, 0, length);

                for (int k = 0; k < tag.length; k++) {
                    if (StringUtil.toHexString(shortToByte2(tagInfo[0]), false).equals(tag[k]) && (tagInfo[2] > 0)) {
                        if (istag) {
                            if ("00".equals(tag[k].substring(0, 2))) {
                                sb.append(StringUtil.toHexString(shortToByte2(tagInfo[0]), false).substring(2, 4)).append(String.format("%02x", tagInfo[2])).append(StringUtil.toHexString(buffer));
                            } else {
                                sb.append(StringUtil.toHexString(shortToByte2(tagInfo[0]), false)).append(String.format("%02x", tagInfo[2])).append(StringUtil.toHexString(buffer));
                            }
                        } else {
                            sb.append(StringUtil.toHexString(buffer).trim());
                        }
                    }
                }

                i = tagInfo[1] + tagInfo[2];
            }
        }
        return sb.toString().replace(" ", "");
    }

    public static String parseF55(String F55_Field, boolean istag, String... tag) {
        byte[] bitBytes = StringUtil.hexStringToBytes(F55_Field);
        return parseF55(bitBytes, istag, tag);
    }

    /**
     * 打印需求
     *
     * @param local55
     * @param net55
     * @return
     */
    public static String print55(String local55, String net55, String tag) {
        String _55 = "";
        _55 = PosUtil.parseF55(local55, false, tag);
        if (TextUtils.isEmpty(_55)) {
            _55 = PosUtil.parseF55(net55, false, tag);
        }
        return TextUtils.isEmpty(_55) ? "" : _55;

    }

    public static String print55(String local55, String tag) {
        String _55 = "";
        _55 = PosUtil.parseF55(local55, false, tag);
        return TextUtils.isEmpty(_55) ? "" : _55;

    }

    /**
     * 去除字符串尾部的'F'字符
     *
     * @param str
     * @return
     */
    public static String removeTailF(String str) {
        int length = 0;
        if ((str == null) || (str.length() < 0)) {
            return "";
        }
        for (length = str.length();
             length > 0; length--) {
            if (str.charAt(length - 1) != 'F') {
                break;
            }
        }
        if (length == str.length()) {
            return str;
        }
        return str.substring(0, length);
    }

    public static boolean isValidDate(String strValue) {
        int d = Integer.parseInt(strValue.substring(2, 4));
        int m = Integer.parseInt(strValue.substring(0, 2));
        int y = new Date().getYear();

        if (d < 1 || m < 1 || m > 12) {
            return false;
        }

        if (m == 2) {
            if (isLeapYear(y)) {
                return d <= 29;
            } else {
                return d <= 28;
            }
        } else if (m == 4 || m == 6 || m == 9 || m == 11) {
            return d <= 30;
        } else {
            return d <= 31;
        }
    }

    private static boolean isLeapYear(int y) {
        return y % 4 == 0 && (y % 400 == 0 || y % 100 != 0);
    }

    public static boolean isInner(String ip) {
        String reg = "(10|172|192)\\.([0-1][0-9]{0,2}|[2][0-5]{0,2}|[3-9][0-9]{0,1})\\.([0-1][0-9]{0,2}|[2][0-5]{0,2}|[3-9][0-9]{0,1})\\.([0-1][0-9]{0,2}|[2][0-5]{0,2}|[3-9][0-9]{0,1})";
        Pattern p = Pattern.compile(reg);
        Matcher matcher = p.matcher(ip);
        return matcher.find();
    }

    public static int str2Int(String str, int def) {
        int re = def;
        try {
            re = Integer.parseInt(str);
        } catch (NumberFormatException e) {
            Log.d("error","NumberFormatException");
        }
        Log.d("error","re=" + re);
        return re;
    }

    public static String parseTAG(String data, String tag, int len) {
        int tagIndex = data.indexOf(tag);
        int lenIndex = tagIndex + tag.length();
        int valueIndex = lenIndex + len;
        String lenStr = data.substring(lenIndex, valueIndex);
        int valueLen;
        try {
            valueLen = Integer.parseInt(lenStr);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return null;
        }
        Log.d("error","valueLen:" + valueLen);
        return data.substring(valueIndex, valueIndex + valueLen);
    }


    /**
     * 获取流水号(自增)
     *
     * @return
     */
    public static String getTraceAuto(Context context) {
        String s = Config.getTrace(context);
        String s1 = PosUtil.StrNumAuto(s);
        setTrace(context,s1);
        return PosUtil.numToStr6(s);
    }

    /**
     * 获取流水号(不自增)
     *
     * @return
     */
    public static String getTrace(Context context) {
        String s = Config.getTrace(context);
        return PosUtil.numToStr6(s);
    }

    /**
     * 设置流水号
     *
     * @param msg
     */
    public static void setTrace(Context context,String msg) {
        Log.e("feeling","保存流水号：" + msg);
        String s = PosUtil.numToStr6(msg);
        Config.setTrace(context,s);
    }

    /**
     * 获取批次号
     *
     * @return
     */
    public static String getBatch(Context context) {
        String s = Config.getBatch(context);
        return PosUtil.numToStr6(s);
    }

    /**
     * 设置批次号
     *
     * @param no
     */
    public static void setBatch(Context context,String no) {
        Config.setBatch(context,no);
    }

    public static String getSecureSession(final String serviceCode) {
        //此处要修改53域和磁道加密
        String pinFlag = "0";
        String encryptMethod = "6";
        if (0x31 == serviceCode.charAt(2)) {
            pinFlag = "2";
        }
        String trackEncrypt = "0";
        if (!("96".equals(serviceCode.substring(0, 2)))) {
            trackEncrypt = "1";
        }

        if (!Config.isRSA) {
            encryptMethod = "4";
        }

        return (pinFlag + encryptMethod + trackEncrypt + "0000000000000");
    }

    /**
     * 屏蔽卡号
     *
     * @param cardNum
     * @return
     */
    public static String hiddenCardNum(String cardNum) {
        String str = "********************";
        try {
            cardNum = cardNum.substring(0, 6) + str.substring(0, cardNum.length() - 10) + cardNum.substring(cardNum.length() - 4);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cardNum;
    }
}
