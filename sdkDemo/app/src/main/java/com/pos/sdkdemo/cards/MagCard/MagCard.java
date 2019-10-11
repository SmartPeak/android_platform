package com.pos.sdkdemo.cards.MagCard;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.pos.sdk.cardreader.PosCardReaderManager;
import com.pos.sdk.cardreader.PosMagCardReader;
import com.pos.sdk.utils.PosUtils;
import com.pos.sdkdemo.R;
import com.pos.sdkdemo.base.BaseActivity;

/**
 * 磁条卡 (Mag Card)
 */
public class MagCard extends BaseActivity {

    PosMagCardReader cardReader = null;
    int ret;
    private Button start;
    private View v;

    @Override
    protected View onCreateView(LayoutInflater inflater) {
        v = inflater.inflate(R.layout.activity_card_mag, null, false);
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
        cardReader = PosCardReaderManager.getDefault(MagCard.this).getMagCardReader();
        if (cardReader != null) {
            LOGD("****** Mag test******");
            int ret = cardReader.open();
            LOGD("open:: " + (ret == 0 ? "ok" : "fail"));
        } else {
            LOGD("Mag cardreader is not support!");
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
            byte[] stripDataBytes = null;
            for (int i = PosMagCardReader.CARDREADER_TRACE_INDEX_1; i <= PosMagCardReader.CARDREADER_TRACE_INDEX_3; i++) {
                stripDataBytes = cardReader.getTraceData(i);
                if (stripDataBytes != null) {
                    LOGD("getTraceData:: strip" + i + "'s data= " + new String(stripDataBytes));
                }
            }
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
