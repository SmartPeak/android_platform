package com.pos.sdkdemo.trade.bean;

import android.text.TextUtils;
import android.util.Log;

import com.basewin.define.CardType;
import com.basewin.define.OutputCardInfoData;
import com.basewin.define.OutputMagCardInfo;
import com.basewin.define.OutputPBOCAAData;
import com.basewin.define.OutputQPBOCResult;
import com.basewin.services.PBOCBinder;
import com.basewin.services.ServiceManager;
import com.basewin.utils.AppUtil;
import com.pos.sdkdemo.utils.BCDHelper;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Card {
    private static String TAG = Card.class.getSimpleName();
    private Map<String, Object> map = new HashMap<>();
    /**
     * 00  未指明  01 手工  02 磁条  03 条形码 04  光学字符阅读 05  (插卡)集成电路卡 07（挥卡）  快速 PBOC 借/贷记 IC 卡读入（非接触式）
     */
    //
    public int type = 0;// 获取卡数据方式
    public OutputMagCardInfo magCardInfo;
    public OutputCardInfoData icCardInfo;
    public OutputPBOCAAData icAAData;
    public OutputQPBOCResult qicCard;
    private String pan = "";
    private String expDate = "";
    public byte[] password = null;
    private String money = "0";//金额 单位元
    private boolean fallback;

    private byte[] TC = null;

    public byte[] getTC() {
        return TC;
    }

    public void setTC(byte[] TC) {
        this.TC = TC;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }

    /**
     * 卡号
     *
     * @return
     */
    public String getPan() {
        String card = "";
        switch (type) {
            case CardType.MAG_CARD:
                card = magCardInfo != null ? magCardInfo.getPAN() : "";
                break;
            case CardType.RF_CARD:
                card = qicCard != null ? qicCard.getPAN() : "";
                break;
            case CardType.IC_CARD:
                card = icCardInfo != null ? icCardInfo.getPAN() : "";
                break;
        }
        if (TextUtils.isEmpty(card)) {
            card = pan;
        }
        return card;
    }

    public void print() {
        if (type == CardType.MAG_CARD) {
            Log.e(TAG, "track2: " + magCardInfo.getTrack2HexString());
            Log.e(TAG, "track3: " + magCardInfo.getTrack3HexString());
        } else if (type == CardType.IC_CARD) {
            Log.e(TAG, "track: " + icCardInfo.getTrack());
        } else if (type == CardType.RF_CARD) {
            Log.e(TAG, "track: " + qicCard.getTrack());
        } else {

        }

    }

    public String getTrack2() {
        return getTrack2(this);
    }

    public String getTrack2ToD() {
        return getTrack2(this).replace("=", "D");
    }

    public static String getTrack2(Card card) {
        if (card == null)
            return "";
        if (card.type == CardType.IC_CARD) {
            return card.icCardInfo.getTrack();
        } else if (card.type == CardType.MAG_CARD) {
            return card.magCardInfo.getTrack2HexString();
        } else if (card.type == CardType.RF_CARD) {
            return card.qicCard.getTrack();
        } else {
            return "";
        }


    }

    public String getTrack3() {
        return getTrack3(this);
    }

    public String getTrack3ToD() {
        if (TextUtils.isEmpty(getTrack3())) {
            return "";
        }
        return getTrack3().replace("=", "D");
    }

    public static String getTrack3(Card card) {
        if (card == null)
            return "";

        if (card.type == CardType.IC_CARD) {
            return "";
        } else if (card.type == CardType.MAG_CARD) {
            return card.magCardInfo.getTrack3HexString();
        } else if (card.type == CardType.RF_CARD) {
            return "";
        } else {
            return "";
        }
    }

    /**
     * 是否是ic类型的卡
     *
     * @return
     */
    public boolean isIC() {
        return this.type == CardType.IC_CARD || this.type == CardType.RF_CARD;
    }

    /**
     * 是否是ic 插卡
     *
     * @return
     */
    public boolean isICCard() {
        return type == CardType.IC_CARD;
    }

    public static boolean isICCARD(Card card) {
        if (card.type == CardType.IC_CARD || card.type == CardType.RF_CARD) {
            return true;
        }

        return false;
    }

    public static boolean isICCARD(int type) {
        if (type == CardType.IC_CARD || type == CardType.RF_CARD) {
            return true;
        }
        return false;
    }

    public String get55() {
        try {
            if (this.type == CardType.IC_CARD) {
                return this.icAAData.get55Field();
            } else if (this.type == CardType.MAG_CARD) {
                return "";
            } else if (this.type == CardType.RF_CARD) {
//                return this.qicCard.get55Field();
//                return BCDHelper.bcdToString(this.qicCard.getKernelData());
                return BCDHelper.bcdToString(getRFField55());
            } else {
                return "";
            }
        } catch (Exception e) {

        }
        return "";
    }

    public static String get55(Card card) {
        if (card == null)
            return "";

        if (card.type == CardType.IC_CARD) {
            return card.icAAData.get55Field();
        } else if (card.type == CardType.MAG_CARD) {
            return "";
        } else if (card.type == CardType.RF_CARD) {
//			return card.qicCard.get55Field();
//            return BCDHelper.bcdToString(card.qicCard.getKernelData());
            return BCDHelper.bcdToString(getRFField55());
        } else {
            return "";
        }
    }

    private static byte[] getRFField55() {
        PBOCBinder binder = null;
        try {
            binder = ServiceManager.getInstence().getPboc();

            if (binder == null) {
                return new byte[0];
            } else {
                byte[] arr = new byte[2048];
                int offset = 0;
                byte[] byte9F26 = binder.getEmvTlvData(0x9F26);
                if (byte9F26 != null) {
                    offset = AppUtil.TLVAppend((short) 0x9F26, byte9F26, 0, arr, 0, byte9F26.length);
                }
                byte[] byte9F27 = binder.getEmvTlvData(0x9F27);
                if (byte9F27 != null) {
                    offset = AppUtil.TLVAppend((short) 0x9F27, byte9F27, 0, arr, offset, byte9F27.length);
                }
                byte[] byte9F10 = binder.getEmvTlvData(0x9F10);
                if (byte9F10 != null) {
                    offset = AppUtil.TLVAppend((short) 0x9F10, byte9F10, 0, arr, offset, byte9F10.length);
                }
                byte[] byte9F37 = binder.getEmvTlvData(0x9F37);
                if (byte9F37 != null) {
                    offset = AppUtil.TLVAppend((short) 0x9F37, byte9F37, 0, arr, offset, byte9F37.length);
                }
                byte[] byte9F36 = binder.getEmvTlvData(0x9F36);
                if (byte9F36 != null) {
                    offset = AppUtil.TLVAppend((short) 0x9F36, byte9F36, 0, arr, offset, byte9F36.length);
                }
                byte[] byte95 = binder.getEmvTlvData(0x95);
                if (byte95 != null) {
                    offset = AppUtil.TLVAppend((short) 0x95, byte95, 0, arr, offset, byte95.length);
                }
                byte[] byte9A = binder.getEmvTlvData(0x9A);
                if (byte9A != null) {
                    offset = AppUtil.TLVAppend((short) 0x9A, byte9A, 0, arr, offset, byte9A.length);
                }
                byte[] byte9C = binder.getEmvTlvData(0x9C);
                if (byte9C != null) {
                    offset = AppUtil.TLVAppend((short) 0x9C, byte9C, 0, arr, offset, byte9C.length);
                }
                byte[] byte9F02 = binder.getEmvTlvData(0x9F02);
                if (byte9F02 != null) {
                    offset = AppUtil.TLVAppend((short) 0x9F02, byte9F02, 0, arr, offset, byte9F02.length);
                }
                byte[] byte5F2A = binder.getEmvTlvData(0x5F2A);
                if (byte5F2A != null) {
                    offset = AppUtil.TLVAppend((short) 0x5F2A, byte5F2A, 0, arr, offset, byte5F2A.length);
                }
                byte[] byte82 = binder.getEmvTlvData(0x82);
                if (byte82 != null) {
                    offset = AppUtil.TLVAppend((short) 0x82, byte82, 0, arr, offset, byte82.length);
                }
                byte[] byte9F1A = binder.getEmvTlvData(0x9F1A);
                if (byte9F1A != null) {
                    offset = AppUtil.TLVAppend((short) 0x9F1A, byte9F1A, 0, arr, offset, byte9F1A.length);
                }
                byte[] byte9F33 = binder.getEmvTlvData(0x9F33);
                if (byte9F33 != null) {
                    offset = AppUtil.TLVAppend((short) 0x9F33, byte9F33, 0, arr, offset, byte9F33.length);
                }
                byte[] byte9F35 = binder.getEmvTlvData(0x9F35);
                if (byte9F35 != null) {
                    offset = AppUtil.TLVAppend((short) 0x9F35, byte9F35, 0, arr, offset, byte9F35.length);
                }
                byte[] byte84 = binder.getEmvTlvData(0x84);
                if (byte84 != null) {
                    offset = AppUtil.TLVAppend((short) 0x84, byte84, 0, arr, offset, byte84.length);
                }
                byte[] byte9F09 = binder.getEmvTlvData(0x9F09);
                if (byte9F09 != null) {
                    offset = AppUtil.TLVAppend((short) 0x9F09, byte9F09, 0, arr, offset, byte9F09.length);
                }
                byte[] byte9F1E = binder.getEmvTlvData(0x9F1E);
                if (byte9F1E != null) {
                    offset = AppUtil.TLVAppend((short) 0x9F1E, byte9F1E, 0, arr, offset, byte9F1E.length);
                }
                byte[] byte9F03 = binder.getEmvTlvData(0x9F03);
                if (byte9F03 != null) {
                    offset = AppUtil.TLVAppend((short) 0x9F03, byte9F03, 0, arr, offset, byte9F03.length);
                }

                byte[] byte9F41 = binder.getEmvTlvData(0x9F41);
                if (byte9F41 != null) {
                    offset = AppUtil.TLVAppend((short) 0x9F41, byte9F03, 0, arr, offset, byte9F41.length);
                }

                byte[] byte9F34 = binder.getEmvTlvData(0x9F34);
                if (byte9F34 != null) {
                    offset = AppUtil.TLVAppend((short) 0x9F34, byte9F03, 0, arr, offset, byte9F34.length);
                }
                byte[] field55Bytes = new byte[offset];
                System.arraycopy(arr, 0, field55Bytes, 0, offset);

                return field55Bytes;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new byte[0];
        }

    }

    public String get23() {
        if (this.type == CardType.IC_CARD) {
//            TLog.d("icAAData=" + icAAData);
//            if (this.icAAData == null) {
//                return "";
//            } else {
//                if (TextUtils.isEmpty(this.icAAData.getCardSeqNum())) {
//                    return "";
//                }
//            }
            return this.icCardInfo.getCardSN();
        } else if (this.type == CardType.MAG_CARD) {
            return "";
        } else if (this.type == CardType.RF_CARD) {
            return this.qicCard.getCardSN();
        } else {
            return "";
        }
    }

    public static String get23(Card card) {
        if (card == null)
            return "";

        if (card.type == CardType.IC_CARD) {
            return card.icAAData.getCardSeqNum();
        } else if (card.type == CardType.MAG_CARD) {
            return "";
        } else if (card.type == CardType.RF_CARD) {
            return card.qicCard.getCardSN();
        } else {
            return "";
        }
    }

    public String getICData() {
        if (this.type == CardType.IC_CARD) {
            return this.icCardInfo.getICData();
        } else if (this.type == CardType.MAG_CARD) {
            return "";
        } else if (this.type == CardType.RF_CARD) {
            return this.qicCard.getICData();
        } else {
            return "";
        }
    }

    public static String getICData(Card card) {
        if (card == null)
            return "";

        if (card.type == CardType.IC_CARD) {
            return card.icCardInfo.getICData();
        } else if (card.type == CardType.MAG_CARD) {
            return "";
        } else if (card.type == CardType.RF_CARD) {
            return card.qicCard.getICData();
        } else {
            return "";
        }
    }

    public static String getMaskPan(Card card) {
        if (card == null)
            return "";

        if (card.type == CardType.IC_CARD) {
            if (card.icCardInfo == null)
                return "";
            else {
                return card.icCardInfo.getMaskedPAN();
            }

        } else if (card.type == CardType.MAG_CARD) {
            if (card.magCardInfo == null)
                return "";
            else {
                return card.magCardInfo.getMaskedPAN();
            }

        } else if (card.type == CardType.RF_CARD) {
            if (card.qicCard == null)
                return "";
            else {
                return card.qicCard.getMaskedPan();
            }
        } else {
            return "";
        }

    }

    public String getMaskPan() {
        return getMaskPan(this);
    }

    public String getExpDate() {
        String tmp = "";
        if (this.type == CardType.MAG_CARD) {
            tmp = magCardInfo != null ? magCardInfo.getExpiredDate() : "";
        } else if (this.type == CardType.IC_CARD) {
            tmp = icCardInfo != null ? icCardInfo.getExpiredDate() : "";
        } else if (this.type == CardType.RF_CARD) {
            tmp = qicCard != null ? qicCard.getExpiredDate() : "";
        }
        //TODO 重要问题 需要解决的
//        if (IDUtil.isEC(FlowControl.MapHelper.getAction())) {
//            expDate = tmp.substring(0, 4);
//        } else {
        try {
            expDate = tmp.substring(2, 4) + tmp.substring(0, 2);
        } catch (Exception e) {
            expDate = "";
        }
        return expDate;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    public void setExpDate(String expDate) {
        this.expDate = expDate;
    }

    public boolean isFallback() {
        return fallback;
    }

    public void setFallback(boolean fallback) {
        this.fallback = fallback;
    }

    @Override
    public String toString() {
        return "Card{" +
                "map=" + map +
                ", type=" + type +
                ", magCardInfo=" + magCardInfo +
                ", icCardInfo=" + icCardInfo +
                ", icAAData=" + icAAData +
                ", qicCard=" + qicCard +
                ", pan='" + pan + '\'' +
                ", expDate='" + expDate + '\'' +
                ", password=" + Arrays.toString(password) +
                ", money='" + money + '\'' +
                ", TC=" + Arrays.toString(TC) +
                '}';
    }
}
