package com.pos.sdkdemo.cards.M1card;

import com.pos.sdk.cardreader.PosCardReaderInfo;
import com.pos.sdk.cardreader.PosCardReaderManager;
import com.pos.sdk.cardreader.PosMifareCardReader;
import com.pos.sdk.utils.PosByteArray;
import com.pos.sdk.utils.PosTlv;

import android.content.Context;

public class MifareReader{
	private static MifareReader instance = null;
	private PosMifareCardReader mifareCardReader = null;
	public static MifareReader getInstance(Context context)
	{
		if (instance == null) {
			instance = new MifareReader(context);	
		}
		return instance;
	}
	
	private MifareReader(Context context)
	{
		mifareCardReader = PosCardReaderManager.getDefault(context).getMifareCardReader();
	}
	
	public int open()
	{
		return mifareCardReader.open();
	}
	public int detect()
	{
		return mifareCardReader.detect();
	}
	
	public int authenticateSectorWithKeyA(int sectorIndex, byte[] key)
	{
		return mifareCardReader.auth('A', sectorIndex*4, key);
	}


	public int authenticateSectorWithKeyB(int sectorIndex, byte[] key)
	{
		return mifareCardReader.auth('B', sectorIndex*4, key);
	}


	public int blockToSector(int blockIndex)
	{
		  return blockIndex/4;
	}


	public int getBlockCount()
	{
		PosCardReaderInfo info = mifareCardReader.getCardReaderInfo();
		PosTlv posTlv = new PosTlv(info.mAttribute);
		if (info.mCardType == PosMifareCardReader.CARD_TYPE_MIFARE_CLASSIC) {
			while(posTlv.isValidObject())
			{
				if (posTlv.getTag() == 0x42) {
					byte[] sakbytes = posTlv.getData();
					if (sakbytes != null) {
						int saktype = (int)(sakbytes[sakbytes.length-1] & 0xff);
						if (saktype == 0x08) {
							return 64;
						}else if (saktype == 0x18) {
							return 256;
						}
					}
				}
				posTlv.nextObject();
			}
		}
		return 0;
	}


	public int getBlockCountInSector(int sectorIndex)
	{
		  return 4; 
	}


	public int getSectorCount()
	{
		PosCardReaderInfo info = mifareCardReader.getCardReaderInfo();
		PosTlv posTlv = new PosTlv(info.mAttribute);
		if (info.mCardType == PosMifareCardReader.CARD_TYPE_MIFARE_CLASSIC) {
			while(posTlv.isValidObject())
			{
				if (posTlv.getTag() == 0x42) {
					byte[] sakbytes = posTlv.getData();
					if (sakbytes != null) {
						int saktype = (int)(sakbytes[sakbytes.length-1] & 0xff);
						if (saktype == 0x08) {
							return 16;
						}else if (saktype == 0x18) {
							return 64;
						}
					}
				}
				posTlv.nextObject();
			}
		}
		return 0;
	}


	public int sectorToBlock(int sectorIndex)
	{
		return sectorIndex*4;
	}
	
	public int read(int blkNo, PosByteArray rspBuf)
	{
		return mifareCardReader.read(blkNo, rspBuf);
	}
	
	public int write(int blkNo,byte[] buffer)
	{
		return mifareCardReader.write(blkNo, buffer);
	}
	public int removeCard()
	{
		return mifareCardReader.removeCard();
	}
}
