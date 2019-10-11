package com.pos.sdkdemo.utils;

import com.pos.sdk.card.PosCardInfo;
import com.pos.sdk.card.PosCardManager;
import com.pos.sdk.utils.PosTlv;

import java.io.UnsupportedEncodingException;

/**
 * 公用函数类
 */
public class Tools {

    public static String[] decodeMagTrack(PosCardInfo info) throws UnsupportedEncodingException {
        if (info.mAttribute != null) {
            String[] track = new String[3];
            byte[] tlvData;
            PosTlv tlv = new PosTlv(info.mAttribute);
            while (tlv.isValidObject()) {
                switch (tlv.getTag()) {
                    case PosCardManager.MAGCARD_TRACK_TLV_TAG_1:
                        tlvData = tlv.getData();
                        if (tlvData != null && tlvData.length > 0) {
                            track[0] = new String(tlvData, "ISO-8859-1");
                        }
                        break;
                    case PosCardManager.MAGCARD_TRACK_TLV_TAG_2:
                        tlvData = tlv.getData();
                        if (tlvData != null && tlvData.length > 0) {
                            track[1] = new String(tlvData, "ISO-8859-1");
                        }
                        break;
                    case PosCardManager.MAGCARD_TRACK_TLV_TAG_3:
                        tlvData = tlv.getData();
                        if (tlvData != null && tlvData.length > 0) {
                            track[2] = new String(tlvData, "ISO-8859-1");
                        }
                        break;
                    default:
                        break;
                }
                tlv.nextObject();
            }
            return track;
        }
        return null;
    }
}
