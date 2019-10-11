package com.pos.sdkdemo.trade.net;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.basewin.commu.Commu;
import com.basewin.commu.define.CommuListener;
import com.basewin.commu.define.CommuParams;
import com.basewin.commu.define.CommuType;
import com.basewin.packet8583.factory.Iso8583Manager;
import com.pos.sdkdemo.trade.Config;
import com.pos.sdkdemo.trade.Dao.db.TransactionDataDB;
import com.pos.sdkdemo.trade.GlobleData;
import com.pos.sdkdemo.trade.TransTypeConstant;
import com.pos.sdkdemo.trade.net8583.Base8583Package;

import java.io.IOException;

public class NetManager {

    private static Commu init(){
        CommuParams params = new CommuParams();
        params.setIp(Config.IP);
        params.setType(CommuType.SOCKET);
        params.setPort(Integer.parseInt(Config.PORT));
        params.setTimeout(40);
        params.setIfSSL(Config.SupportSSL);
        if (Config.SupportSSL) {
            params.setCer("Cer_YZF.crt");
        }
        Commu commu = new Commu();
        commu.setCommuParams(params);
        return commu;
    }


    public static void connect(Context context, Base8583Package datas,CommuListener listener){
        try {
            Iso8583Manager iso8583Manager = datas.package8583(GlobleData.getInstance().datas,
                    GlobleData.getInstance().card);
            if(TransTypeConstant.isSaveDB((Integer) GlobleData.getInstance().datas.get(Config.TRANS_TYPE))){
                Log.e("feeling", "save data");
                TransactionDataDB.save(iso8583Manager);
            }
            byte[] sendData=iso8583Manager.pack();
            init().dataCommu(context,sendData,listener);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
