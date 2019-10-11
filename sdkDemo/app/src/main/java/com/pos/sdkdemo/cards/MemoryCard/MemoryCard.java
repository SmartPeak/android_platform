package com.pos.sdkdemo.cards.MemoryCard;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.basewin.interfaces.OnDetectListener;
import com.basewin.services.ServiceManager;
import com.pos.sdk.card.PosCardInfo;
import com.pos.sdk.cardreader.PosCardReaderManager;
import com.pos.sdk.cardreader.PosMemoryCardReader;
import com.pos.sdk.utils.PosByteArray;
import com.pos.sdk.utils.PosUtils;
import com.pos.sdkdemo.R;
import com.pos.sdkdemo.base.BaseActivity;

/**
 * Memory卡 (Memory Card)
 */
public class MemoryCard extends BaseActivity {

	private Button start;
	private View v;
	PosMemoryCardReader cardReader = null;
	int ret;

	@Override
	protected View onCreateView(LayoutInflater inflater) {
		v = inflater.inflate(R.layout.activity_card_memory, null, false);
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
		cardReader = PosCardReaderManager.getDefault(MemoryCard.this).getMemoryCardReader();
		if (cardReader != null) {
			int ret = -1;
			LOGD("****** Memory test******");
			ret = cardReader.status();
			LOGD("status:: " + (ret == 0 ? "ok" : "fail"));
			PosByteArray rspBuf = new PosByteArray();
			ret = cardReader.open(PosMemoryCardReader.MEMORY_CARD_TYPE_SLE4442, rspBuf);
			LOGD("open:: " + (ret == 0 ? "ok, rspBuf= " + PosUtils.bytesToHexString(rspBuf.buffer) : "fail"));
			if (ret == 0) {
//				// pac.
//				rspBuf = new PosByteArray();
//				ret = cardReader.pac(rspBuf);
//				LOGD("pac:: " + (ret == 0 ? "ok, rspBuf= " + PosUtils.bytesToHexString(rspBuf.buffer) : "fail"));
//				// verify
//				ret = cardReader.verify(PosUtils.hexStringToBytes("FFFFFF"));
//				LOGD("verify:: " + (ret == 0 ? "ok" : "fail"));
			}
		} else {
			LOGD("Memory cardreader is not support!");
		}
	}

	private void CardTest() {
		PosByteArray rspBuf = new PosByteArray();
		// read
		rspBuf = new PosByteArray();
		ret = cardReader.read(0x20, 0x10, rspBuf);
		LOGD("read:: " + (ret == 0 ? "ok, rspBuf= " + PosUtils.bytesToHexString(rspBuf.buffer) : "fail"));
//		// write
//		ret = cardReader.write(0x20, rspBuf.buffer);
//		LOGD("write:: " + (ret == 0 ? "ok" : "fail"));
//
//		// Update.
//		ret = cardReader.update(PosUtils.hexStringToBytes("FFFFFF"));
//		LOGD("update:: " + (ret == 0 ? "ok" : "fail"));
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		ret = cardReader.close();
		LOGD("close:: " + (ret == 0 ? "ok" : "fail"));
	}
}
