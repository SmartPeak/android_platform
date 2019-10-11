package com.pos.sdkdemo.cards.IccCard;

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
import com.pos.sdk.cardreader.PosIccCardReader;
import com.pos.sdk.utils.PosByteArray;
import com.pos.sdk.utils.PosUtils;
import com.pos.sdkdemo.R;
import com.pos.sdkdemo.base.BaseActivity;

/**
 * 接触式芯片卡 (IccCard)
 */
public class IccCard extends BaseActivity {

	private Button start;
	private View v;
	PosIccCardReader cardReader = null;
	int ret;

	@Override
	protected View onCreateView(LayoutInflater inflater) {
		v = inflater.inflate(R.layout.activity_card_icc, null, false);
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

		cardReader = PosCardReaderManager.getDefault(IccCard.this).getIccCardReader();
		if (cardReader != null) {
			LOGD("****** ICC test******");
			ret = cardReader.open();
			LOGD("open:: " + (ret == 0 ? "ok" : "fail"));
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
		} else {
			LOGD("ICC cardreader is not support!");
		}

	}

	private void CardTest() {
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
