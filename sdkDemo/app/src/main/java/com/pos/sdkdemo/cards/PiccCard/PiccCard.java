package com.pos.sdkdemo.cards.PiccCard;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.basewin.interfaces.OnDetectListener;
import com.basewin.services.ServiceManager;
import com.pos.sdk.card.PosCardInfo;
import com.pos.sdk.cardreader.PosCardReaderInfo;
import com.pos.sdk.cardreader.PosCardReaderManager;
import com.pos.sdk.cardreader.PosMemoryCardReader;
import com.pos.sdk.cardreader.PosPiccCardReader;
import com.pos.sdk.utils.PosByteArray;
import com.pos.sdk.utils.PosUtils;
import com.pos.sdkdemo.R;
import com.pos.sdkdemo.base.BaseActivity;

/**
 * 非接芯片卡 (Picc Card)
 */
public class PiccCard extends BaseActivity {

	private Button start;
	private View v;
	PosPiccCardReader cardReader = null;
	int ret;

	@Override
	protected View onCreateView(LayoutInflater inflater) {
		v = inflater.inflate(R.layout.activity_card_picc, null, false);
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
		cardReader = PosCardReaderManager.getDefault(PiccCard.this).getPiccCardReader();
		if (cardReader != null) {
			LOGD("****** PICC test******");
			int ret = cardReader.open();
			LOGD("open:: " + (ret == 0 ? "ok" : "fail"));
		} else {
			LOGD("PICC cardreader is not support!");
		}
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
			PosByteArray rspBuf = new PosByteArray();
			PosByteArray swBuf = new PosByteArray();
			ret = cardReader.transmitApdu(PosUtils.hexStringToBytes("0084000008"), rspBuf, swBuf);
			LOGD("transmitApdu:: " + (ret == 0
					? "ok, radom=" + PosUtils.bytesToHexString(rspBuf.len > 0 ? rspBuf.buffer : swBuf.buffer)
					: "fail"));

			ret = cardReader.close();
			LOGD("removeCard:: " + (ret == 0 ? "ok" : "fail"));
		}


	}

	/**
	 * Felic card detect
	 */
	private void command() {
		LOGD("start to detect");
		int ret=-1;
		cardReader = PosCardReaderManager.getDefault(this).getPiccCardReader();
		if (cardReader != null) {
			ret = cardReader.open();
		} else {
			cardReader = PosCardReaderManager.getDefault(this).getPiccCardReader();
		}

		PosByteArray rspBuf = new PosByteArray();

		String setParamCom = "610400033C20630103670400001388680132690201006A0101";//Set Params
		byte[] paramComm = PosUtils.hexStringToBytes(setParamCom);
		ret = cardReader.transmitRawCmd(paramComm, rspBuf);
		LOGD("set param: "+ret);

		String[] pollingCom = {"00", "01", "03", "07", "0F"};
		String basePollingCom = "64060600FFFF00";
		boolean detect= true;
		while (detect) {
			String command = "";
			for (int i = 0; i < pollingCom.length; i++) {
				command = basePollingCom + pollingCom[1];//Felica polling 64060600FFFF0000
				byte[] btComm = PosUtils.hexStringToBytes(command);
				cardReader.transmitRawCmd(btComm, rspBuf);
				Log.e("result: ", ret + "");
				if (rspBuf.len > 0) {
                    LOGD("rspBuf: "+PosUtils.bytesToHexString(rspBuf.buffer));
					detect=false;
					break;
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
