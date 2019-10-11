package com.pos.sdkdemo.cards.ViccCard;

import java.util.Arrays;

import com.pos.sdk.accessory.PosAccessoryManager;
import com.pos.sdk.cardreader.PosCardReaderManager;
import com.pos.sdk.cardreader.PosViccCardReader;
import com.pos.sdk.utils.PosByteArray;
import com.pos.sdk.utils.PosUtils;
import com.pos.sdkdemo.R;
import com.pos.sdkdemo.base.BaseActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

/**
 * Vicc卡 (Vicc Card)
 */
public class ViccCard extends BaseActivity {

	private Button start;
	private View v;
	PosViccCardReader cardReader = null;
	int ret;

	@Override
	protected View onCreateView(LayoutInflater inflater) {
		v = inflater.inflate(R.layout.activity_card_vicc, null, false);
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
		PosAccessoryManager.getDefault().setRFRegister(PosAccessoryManager.RF_REGISTER_TYPE_A,
				PosAccessoryManager.RF_REGISTER_ADDR, 0x00);
		PosAccessoryManager.getDefault().setRFRegister(PosAccessoryManager.RF_REGISTER_TYPE_B,
				PosAccessoryManager.RF_REGISTER_ADDR, 0x00);
		cardReader = PosCardReaderManager.getDefault(ViccCard.this).getViccCardReader();
		if (cardReader != null) {
			LOGD("****** Vicc test******");
			int ret = -1;
			PosByteArray rspBuf = new PosByteArray();

			ret = cardReader.open();
			LOGD("open:: " + (ret == 0 ? "ok" : "fail"));
		} else {
			LOGD("Vicc cardreader is not support!");
		}

	}

	private void CardTest() {
		PosByteArray rspBuf = new PosByteArray();

		LOGD("start to detect");
		int cnt = 0;
		boolean detected = false;
		while (cnt++ < MAX_TRY_CNT) {
			if (cardReader.inventory(rspBuf) == 0) {
				detected = true;
				break;
			}
			PosUtils.delayms(50);
		}

		LOGD("detect:: " + (detected ? "ok, rspBuf= " + PosUtils.bytesToHexString(rspBuf.buffer) : "fail"));
		if (detected) {
			int offset = 0;
			// flag.
			int flag = (int) (rspBuf.buffer[offset++] & 0xff);
			// dsfid
			int dfsid = (int) (rspBuf.buffer[offset++] & 0xff);
			// uidBytes.
			byte[] uidBytes = Arrays.copyOfRange(rspBuf.buffer, offset, rspBuf.len);
			// select.
			ret = cardReader.select(uidBytes);
			LOGD("select:: " + (ret == 0 ? "ok" : "fail"));

			// reset.
			ret = cardReader.reset(uidBytes);
			LOGD("reset:: " + (ret == 0 ? "ok" : "fail"));

			// readBlock.
			rspBuf = new PosByteArray();
			ret = cardReader.readBlock(0x08, rspBuf);
			LOGD("readBlock:: " + (ret == 0 ? ("ok, flag= 0x" + Integer.toHexString(rspBuf.buffer[0]) + ", data= "
					+ PosUtils.bytesToHexString(rspBuf.buffer, 1, rspBuf.len - 1)) : "fail"));

			// writeBlock.
			ret = cardReader.writeBlock(0x08, Arrays.copyOfRange(rspBuf.buffer, 1, rspBuf.len));
			LOGD("writeBlock:: " + (ret == 0 ? "ok" : "fail"));

			// getSystemInfo.
			rspBuf = new PosByteArray();
			ret = cardReader.getSystemInfo(rspBuf);
			LOGD("getSystemInfo:: " + (ret == 0 ? "ok, rspBuf= " + PosUtils.bytesToHexString(rspBuf.buffer) : "fail"));
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
