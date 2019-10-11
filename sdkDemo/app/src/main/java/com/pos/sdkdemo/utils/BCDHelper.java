package com.pos.sdkdemo.utils;


public class BCDHelper {
    /**
     * @param
     * @return :byte[]
     * @Title: StrToBCD
     * @Description: TODO(用BCD码压缩数字字符串)
     * @version : v1.0.0
     * @author : WANJJ
     * @date: 2011-11-17
     * <p/>
     * Modification History:messageHeader
     * Date  Author  Version  Description
     * ---------------------------------------------------------*
     * 2011-11-17  wanjj v1.0.0   修改原因
     */

    //
    private static char ConvertHexChar(char ch) {
        if ((ch >= '0') && (ch <= '9'))
            return (char) (ch - 0x30);
        else if ((ch >= 'A') && (ch <= 'F'))
            return (char) (ch - 'A' + 10);
        else if ((ch >= 'a') && (ch <= 'f'))
            return (char) (ch - 'a' + 10);
        else
            return (char) (-1);
    }

    //byte值转成INT值
    public static int byte2int(byte val) {
        return (val >= 0 ? val : (val + 256));
    }

    public static byte[] StrToBCD(String str) {
        return StrToBCD(str, str.length());
    }

    public static byte[] StrToBCD(String str, int numlen) {
        if (numlen % 2 != 0)
            numlen++;

        while (str.length() < numlen) {
            str = "0" + str;  //前导补0
        }

        byte[] bStr = new byte[str.length() / 2];
        char[] cs = str.toCharArray();
        int i = 0;
        int iNum = 0;
        for (i = 0; i < cs.length; i += 2) {
            //TODO: 过滤空格
            int iTemp = 0;
            if (cs[i] >= '0' && cs[i] <= '9') {
                iTemp = (cs[i] - '0') << 4;
            } else {
                //  判断是否为a~f 
                if (cs[i] >= 'a' && cs[i] <= 'f') {
                    cs[i] -= 32;
                }
                iTemp = (cs[i] - '0' - 7) << 4;
            }
            //  处理低位 
            if (cs[i + 1] >= '0' && cs[i + 1] <= '9') {
                iTemp += cs[i + 1] - '0';
            } else {
                //  判断是否为a~f 
                if (cs[i + 1] >= 'a' && cs[i + 1] <= 'f') {
                    cs[i + 1] -= 32;
                }
                iTemp += cs[i + 1] - '0' - 7;
            }
            bStr[iNum] = (byte) iTemp;
            iNum++;
        }
        return bStr;
    }

    public static byte[] stringToBcd(String src, int numlen) {
        int inum = 0;
        if ((numlen % 2) > 0) return null;
        byte[] dst = new byte[numlen / 2];

        for (int i = 0; i < numlen; ) {
            //TODO: 过滤空格
            char hghch = ConvertHexChar(src.charAt(i));
            char lowch = ConvertHexChar(src.charAt(i + 1));

            dst[inum++] = (byte) (hghch * 16 + lowch);
            i += 2;
        }
        return dst;
    }

    public static byte[] stringToBcd(String src) {
        int inum = 0;
        int numlen = src.length();
        if ((numlen % 2) > 0) return null;
        byte[] dst = new byte[numlen / 2];

        for (int i = 0; i < numlen; ) {
            //TODO: 过滤空格
            char hghch = ConvertHexChar(src.charAt(i));
            char lowch = ConvertHexChar(src.charAt(i + 1));

            dst[inum++] = (byte) (hghch * 16 + lowch);
            i += 2;
        }
        return dst;
    }


    public static char[] asciiToBcd(String src) {
        int inum = 0;

        String str = src.trim().replaceAll(" ", "");

        int numlen = str.length();
        if ((numlen % 2) > 0) return null;
        char[] dst = new char[numlen / 2];

        for (int i = 0; i < numlen; ) {
            //TODO: 过滤空格
            char hghch = ConvertHexChar(str.charAt(i));
            char lowch = ConvertHexChar(str.charAt(i + 1));

            dst[inum++] = (char) (hghch * 16 + lowch);
            i += 2;
        }
        return dst;
    }


    /**
     * 将BCD码串转成ASCII码串，如 hex("\x21\x31\x24") 转成 "213124"
     *
     * @param bcdNum 是代表BCD码串
     * @param offset 是代表从第几个BCD码字节开始转�?
     * @param numlen 是代表BCD码字节数
     * @return
     */
    public static String bcdToString(byte[] bcdNum, int offset, int numlen) {
        int len = numlen;

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < len; i++) {
            sb.append(Integer.toHexString((bcdNum[i + offset] & 0xF0) >> 4));
            sb.append(Integer.toHexString(bcdNum[i + offset] & 0x0F));
        }
        return sb.toString().toUpperCase();
    }

    public static String bcdToString(byte[] bcdNum) {
        int len = bcdNum.length;

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < len; i++) {
            sb.append(Integer.toHexString((bcdNum[i] & 0xF0) >> 4));
            sb.append(Integer.toHexString(bcdNum[i] & 0x0F));
        }
        return sb.toString().toUpperCase();
    }

    /**
     * 把数组转换为十六进制字符串格式显�?
     * @param bts
     * @param offset
     * @param count
     * @return
     */
    //    public static   String BytesToHexString(byte [] bts, int  offset, int  count) {
    //        StringBuilder sb = new  StringBuilder(bts.length * 2);
    //        for  (int  i = 0; i < count; i++) {
    //            sb.append(Integer.toHexString(bts[i + offset]));
    //        }
    //        return  sb.toString();
    //    }


    /**
     * 用于调试
     * 16进制数组转化成调试用字符�?大写字母)，比如[0x03][0x3f]转化�?03 3F"
     *
     * @param b
     * @return
     */
    public static String hex2DebugHexString(byte[] b, int len) {
        int[] x = new int[len];
        String[] y = new String[len];
        StringBuilder str = new StringBuilder();
        // 转换成Int数组,然后转换成String数组
        int j = 0;
        for (; j < len; j++) {
            x[j] = b[j] & 0xff;
            y[j] = Integer.toHexString(x[j]);
            while (y[j].length() < 2) {
                y[j] = "0" + y[j];
            }
            str.append(y[j]);
            str.append(" ");
        }
        return new String(str).toUpperCase();
    }

}
