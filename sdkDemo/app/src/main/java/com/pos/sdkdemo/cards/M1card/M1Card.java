package com.pos.sdkdemo.cards.M1card;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.pos.sdk.cardreader.PosCardReaderInfo;
import com.pos.sdk.cardreader.PosCardReaderManager;
import com.pos.sdk.cardreader.PosMifareCardReader;
import com.pos.sdk.utils.PosByteArray;
import com.pos.sdk.utils.PosUtils;
import com.pos.sdkdemo.R;
import com.pos.sdkdemo.base.BaseActivity;

/**
 * M1卡 (M1 Card)
 */
public class M1Card extends BaseActivity {

    String keyA0 = "FFFFFF";
    int ret;
    PosMifareCardReader cardReader = null;
    private Button start;
    private View v;

    @Override
    protected View onCreateView(LayoutInflater inflater) {
        v = inflater.inflate(R.layout.activity_card_m1, null, false);
        return v;
    }

    @Override
    protected void onInitView() {

        if (v != null) {
            start = (Button) v.findViewById(R.id.identify_card);
            start.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new Thread(new Runnable() {
                        public void run() {
                            CardTest();
                        }
                    }).start();

                }
            });

        }
        cardReader = PosCardReaderManager.getDefault(M1Card.this).getMifareCardReader();

        if (cardReader != null) {
            LOGD("****** Mifare test******");
            int ret = cardReader.open(PosMifareCardReader.CARD_TYPE_MIFARE_CLASSIC);// PosMifareCardReader.CARD_TYPE_MIFARE_DESFIRE
            LOGD("open:: " + (ret == 0 ? "ok" : "fail"));
        } else {
            LOGD("Mifare cardreader is not support!");
        }
    }

    private void CardTest() {
        LOGD("start to detect");
        int cnt = 0;
        boolean detected = false;
        while (cnt++ < MAX_TRY_CNT) {
            if (cardReader.detect() == 0) {
                detected = true;
                break;
            }
            PosUtils.delayms(50);
        }
        LOGD("detect:: " + (detected ? "ok" : "fail"));
        if (detected) {
            PosCardReaderInfo info = cardReader.getCardReaderInfo();
            LOGD("getCardReaderInfo:: " + (info != null ? info.toString() : "null"));

            switch (info.mCardType) {
                case PosMifareCardReader.CARD_TYPE_MIFARE_ULTRALIGHT: {
                    // Auth.
                    ret = cardReader.auth('T', 0, PosUtils.hexStringToBytes("49454D4B41455242214E4143554F5946"), null);
                    LOGD("auth:: " + (ret == 0 ? "ok" : "fail"));
                    if (ret == 0) {
                        PosByteArray rspBuf = new PosByteArray();
                        // Read
                        ret = cardReader.read(0x08, rspBuf);
                        LOGD("readBlock:: "
                                + (ret == 0 ? "ok, rspBuf= " + PosUtils.bytesToHexString(rspBuf.buffer) : "fail"));
                        // Write
                        if (rspBuf.buffer != null) {
                            ret = cardReader.write(0x08, PosUtils.hexStringToBytes("FFFFFFFF"));
                            LOGD("writeBlock:: " + (ret == 0 ? "ok" : "fail"));
                        }
                        // Operate.
                    }
                    break;
                }
                case PosMifareCardReader.CARD_TYPE_MIFARE_CLASSIC:
                case PosMifareCardReader.CARD_TYPE_MIFARE_PLUS:
                    // Auth.
                    ret = cardReader.auth('A', 0x08, PosUtils.hexStringToBytes("FFFFFFFFFFFF"), null);
                    LOGD("auth:: " + (ret == 0 ? "ok" : "fail"));
                    if (ret == 0) {
                        PosByteArray rspBuf = new PosByteArray();
                        // Read
                        ret = cardReader.read(0x08, rspBuf);
                        LOGD("read:: " + (ret == 0 ? "ok, rspBuf= " + PosUtils.bytesToHexString(rspBuf.buffer) : "fail"));
                        // Write
                        if (rspBuf.buffer != null) {
                            ret = cardReader.write(0x08, PosUtils.hexStringToBytes("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF"));
                            LOGD("write:: " + (ret == 0 ? "ok" : "fail"));
                        }
                        // Operate.
                    }

                    break;
                case PosMifareCardReader.CARD_TYPE_MIFARE_DESFIRE: {
                    // get version.
                    PosByteArray rspBuf = new PosByteArray();
                    ret = cardReader.transmitApdu(new byte[]{0x60}, rspBuf);
                    LOGD("transmitApdu:: getversion ret= "
                            + (ret == 0 ? "ok, rspBuf= " + PosUtils.bytesToHexString(rspBuf.buffer) : "fail"));
                    // Auth.
                    ret = cardReader.transmitApdu(PosUtils.hexStringToBytes("0A00000000000000000000000000000000"), rspBuf);
                    LOGD("transmitApdu:: auth ret= "
                            + (ret == 0 ? "ok, rspBuf= " + PosUtils.bytesToHexString(rspBuf.buffer) : "fail"));
                    break;
                }
                default:
                    break;
            }
            ret = cardReader.removeCard();
            LOGD("removeCard:: " + (ret == 0 ? "ok" : "fail"));
        }


    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        ret = cardReader.close();
        LOGD("close:: " + (ret == 0 ? "ok" : "fail"));
    }

}
