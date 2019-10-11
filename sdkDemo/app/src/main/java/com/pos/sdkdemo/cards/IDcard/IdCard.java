package com.pos.sdkdemo.cards.IDcard;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.basewin.interfaces.OnApduCmdListener;
import com.basewin.interfaces.OnDetectListener;
import com.basewin.services.ServiceManager;
import com.basewin.utils.BCDHelper;
import com.pos.sdk.card.PosCardInfo;
import com.pos.sdk.cardreader.PosCardReaderInfo;
import com.pos.sdk.cardreader.PosCardReaderManager;
import com.pos.sdk.cardreader.PosSidCardReader;
import com.pos.sdk.utils.PosByteArray;
import com.pos.sdk.utils.PosUtils;
import com.pos.sdkdemo.R;
import com.pos.sdkdemo.base.BaseActivity;

/**
 * 身份证 (IDCard)
 */
public class IdCard extends BaseActivity {

	private Button start;
	private View v;
	PosSidCardReader cardReader = null;
	int ret;

	@Override
	protected View onCreateView(LayoutInflater inflater) {
		v = inflater.inflate(R.layout.activity_card_idcard, null, false);
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
		cardReader = PosCardReaderManager.getDefault(IdCard.this).getSidCardReader();
		if (cardReader != null) {
			LOGD("****** SID test******");
			int ret = cardReader.open();
			LOGD("open:: " + (ret == 0 ? "ok" : "fail"));
		} else {
			LOGD("SID cardreader is not support!");
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

			PosByteArray rspBuf = new PosByteArray();
			ret = cardReader.transmitCmd(PosUtils.hexStringToBytes("0084000008"), rspBuf);
			LOGD("transmitCmd:: " + (ret == 0 ? "ok, radom=" + PosUtils.bytesToHexString(rspBuf.buffer) : "fail"));

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
