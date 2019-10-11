package com.pos.sdkdemo.cards.PsamCard;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.pos.sdk.cardreader.PosCardReaderInfo;
import com.pos.sdk.cardreader.PosCardReaderManager;
import com.pos.sdk.cardreader.PosPsamCardReader;
import com.pos.sdk.utils.PosByteArray;
import com.pos.sdk.utils.PosUtils;
import com.pos.sdkdemo.R;
import com.pos.sdkdemo.base.BaseActivity;

/**
 * PSAM卡 (PSAM Card)
 */
public class PsamCard extends BaseActivity {

    PosPsamCardReader cardReader = null;
    int ret;
    private Button start;
    private View v;

    @Override
    protected View onCreateView(LayoutInflater inflater) {
        v = inflater.inflate(R.layout.activity_card_psam, null, false);
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

    }

    private void CardTest() {
        cardReader = PosCardReaderManager.getDefault(PsamCard.this).getPsamCardReader();
        if (cardReader != null) {
            LOGD("****** PSAM test******");
            int ret = cardReader.open(0x01);
            LOGD("open:: " + (ret == 0 ? "ok" : "fail"));
            LOGD("start to detect");
            int cnt = 0;
            boolean detected = false;
            while (cnt++ < 100) {
                if (cardReader.detect() == 0) {
                    detected = true;
                    break;
                }
                PosUtils.delayms(50);
            }
            LOGD("detect:: " + (detected ? "ok" : "fail"));
        } else {
            LOGD("PSAM cardreader is not support!");
        }
        ret = cardReader.reset();
        LOGD("reset:: " + (ret == 0 ? "ok" : "fail"));
        if (ret == 0) {
            PosCardReaderInfo info = cardReader.getCardReaderInfo();
            LOGD("reset:: " + (info != null ? info.toString() : "null"));

            PosByteArray rspBuf = new PosByteArray();
            PosByteArray swBuf = new PosByteArray();
            ret = cardReader.transmitApdu(PosUtils.hexStringToBytes("0084000008"), rspBuf, swBuf);
            LOGD("transmitApdu:: " + (ret == 0
                    ? "ok, radom=" + PosUtils.bytesToHexString(rspBuf.len > 0 ? rspBuf.buffer : swBuf.buffer)
                    : "fail"));
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
