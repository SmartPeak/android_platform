package com.pos.sdkdemo.utils;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.text.TextUtils;

/**
 * @author liudeyu
 * @date 2016年2月29日 下午2:54:59
 * @version 1.0.0
 * @function
 * @lastmodify
 */
public class StringHelper {
	private final String TAG = "StringHelper";
	private final static int MAXAMTNUM = 12;

	/**
	 * 格式化金额
	 * 
	 * @param amtStr
	 */
	public static String changeAmout(String amtStr) {
		
		/**
		 * @Description 解决下面注释写法会导致输入金额时错乱问题
		 * @author xieruihua
		 * @date 2016-4-12 16:08:13
		 */
		if (TextUtils.isEmpty(amtStr)) {
			throw new IllegalArgumentException("the input parameters can't be empty!");
		}
		String str = amtStr.toString();
		// 最长12位
		if (str.replace(".", "").length() > MAXAMTNUM) {
			str = str.substring(0, str.contains(".") ? MAXAMTNUM + 1 : MAXAMTNUM);
			ToastUtils.shortShow("最大输入" + MAXAMTNUM + "位金额");
		}
		String reg = "^(([1-9]\\d{0,9})|0)(\\.\\d{1,4})?$";
		if (!amtStr.matches(reg)) {
			throw new IllegalArgumentException("the input parameters must be amount!");
		}
		
		double amount = Double.valueOf(str.replaceAll("\\.", "")) / 100;
		DecimalFormat df = new DecimalFormat("#########0.00");
		return df.format(amount);
		
//		String cuttedStr = str;
//		/* 删除字符串中的dot */
//		for (int i = str.length() - 1; i >= 0; i--) {
//			char c = str.charAt(i);
//			if ('.' == c) {
//				cuttedStr = str.substring(0, i) + str.substring(i + 1);
//				break;
//			}
//		}
//		/* 删除前面多余的0 */
//		int NUM = cuttedStr.length();
//		int zeroIndex = -1;
//		for (int i = 0; i < NUM - 2; i++) {
//			char c = cuttedStr.charAt(i);
//			if (c != '0') {
//				break;
//			} else if (i == NUM - 3) {
//				zeroIndex = i;
//				break;
//			}
//		}
//		if (zeroIndex != -1) {
//			cuttedStr = cuttedStr.substring(zeroIndex);
//		}
//		/* 不足3位补0 */
//		if (cuttedStr.length() < 3) {
//			cuttedStr = "0" + cuttedStr;
//		}
//
//		// 最长12位
//		if (str.length() > MAXAMTNUM) {
//			cuttedStr = cuttedStr.substring(0, MAXAMTNUM);
//			ToastUtils.shortShow("最大输入" + MAXAMTNUM + "位金额");
//		}
//		/* 加上dot，以显示小数点后两位 */
//		if (cuttedStr.length() > 2) {
//			cuttedStr = cuttedStr.substring(0, cuttedStr.length() - 2) + "." + cuttedStr.substring(cuttedStr.length() - 2);
//		}
//		return cuttedStr;
	}

	/**
	 * 去掉字符串中的冒号
	 */
	public static String getStringWithoutDot(String srcString) {
		return srcString.replace(".", "");
	}

	/**
	 * 屏蔽卡号
	 * 
	 * @Title: formatCardNum
	 * @Description: TODO
	 * @param cardNum
	 * @param hidden
	 * @return
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

	public static String fillCardStar(String cardno) {
		if (cardno == null || cardno.length() < 10)
			return cardno;
		return cardno.substring(0, 6) + "******"
				+ cardno.substring(cardno.length() - 4);
	}

	/**
	 * @Title: fillFrontSpace
	 * @Description: 字符前空格填充
	 * @param str
	 * @param num
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
	 * @Title: fillBackSpace
	 * @Description: 字符后空格填充
	 * @param str
	 * @param num
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
	 * @Title: formatAmout
	 * @Description: 格式化金额
	 * @param amt
	 * @author xieruihua
	 * @return: String
	 */
	public static String formatAmout(double amt) {
		DecimalFormat df = new DecimalFormat("#########0.00");
		String am = df.format(amt).replace(".", "");
		return String.format("%0" + Math.max(12 - am.length(), 1) + "d", 0) + am;
	}
	
	
	/**
	 * @Title: formatAmout
	 * @Description: 格式化金额
	 * @param amt
	 * @author xieruihua
	 * @return: String
	 */
	public static String formatAmout(String amt) {
		DecimalFormat df = new DecimalFormat("#########0.00");
		double differ = Double.valueOf(amt);
		return df.format(Math.abs(differ));
	}
    /**
     * 金额相减
     * @param amt 原金额
     * @param disPrice 优惠金额
     * @return
     */
	public static String formatSubtract(String amt,String disPrice){
		DecimalFormat df = new DecimalFormat("#########0.00");
		double amtValue =Double.parseDouble(amt);
		double disPriceValue =Double.parseDouble(disPrice);
		return df.format(Math.abs(amtValue-disPriceValue));
	}
	/**
	 * @Title: formatTimeStamp
	 * @Description: 时间字符串格式化yyyy/MM/dd hh:mm:ss
	 * @param timestring
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
	 * @Title: getNoRefundAmount
	 * @Description: 获取还未退金额
	 * @param maxAmt
	 * @param minAmt
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
	
	
	/**
	 * 
	 * @Title: ifBiger 
	 * @Description: 比较金额大小
	 * @param amt1
	 * @param amt2
	 * @return
	 * @return: boolean
	 */
	public static boolean ifBiger(String amt1, String amt2) {
		if (TextUtils.isEmpty(amt1) || TextUtils.isEmpty(amt2)) {
			throw new IllegalArgumentException("the input parameters can't be empty!");
		}
		String reg = "^(([1-9]\\d{0,9})|0)(\\.\\d{1,2})?$";
		if (!amt1.matches(reg) || !amt2.matches(reg)) {
			throw new IllegalArgumentException("the input parameters must be amount!");
		}
		return Double.valueOf(amt1) >= Double.valueOf(amt2);
	}
	
	
	public static String formatSearchNum(String s, int num) {
		if (s == null) {
			return "";
		}
		if (s.length() >= num) {
			return s;
		}
		return String.format("%0" + (num - s.length()) + "d", 0) + s;
	}
	/**
	 * 获取转换后的卡别
	 * @param srv 卡别代号 
	 * @return 卡别
	 */
	public static String formatSrvEntryMode(String srv){
		String result="";
		if(srv.equals("021")||srv.equals("022")){
			result="(S)";
		}
		else if(srv.equals("051")||srv.equals("052")){
			result="(I)";
		}
		else if(srv.equals("071")||srv.equals("072")){
			result="(C)";
		}
		else {
			result="(S)";
		}
		return result;
	}
}
