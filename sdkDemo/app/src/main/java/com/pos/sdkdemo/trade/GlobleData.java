package com.pos.sdkdemo.trade;

import android.content.Context;
import android.content.Intent;

import com.pos.sdkdemo.trade.bean.Card;
import com.pos.sdkdemo.trade.interfac.OnAARequestOnlineProcess;
import com.pos.sdkdemo.trade.interfac.OnConfirmCardInfo;
import com.pos.sdkdemo.trade.interfac.OnConfirmCertInfo;
import com.pos.sdkdemo.trade.interfac.OnError;
import com.pos.sdkdemo.trade.interfac.OnFindingCard;
import com.pos.sdkdemo.trade.interfac.OnRequestInputPIN;
import com.pos.sdkdemo.trade.interfac.OnRequestSinature;
import com.pos.sdkdemo.trade.interfac.OnSelectApplication;
import com.pos.sdkdemo.trade.interfac.OnTransactionResult;
import com.pos.sdkdemo.trade.net8583.Base8583Package;

import java.util.HashMap;
import java.util.Map;

public class GlobleData {

    public static final int ONERROR=0x10;
    private static volatile GlobleData instance;
    private Context context;

    public Card card = null;
    public Map<String,Object> datas = null;
    private Base8583Package send8583Package = null;
    public OnlinePBOClistener pboClistener = null;

    private OnFindingCard onFindingCard = null;
    private OnSelectApplication onSelectApplication = null;
    private OnConfirmCardInfo onConfirmCardInfo = null;
    private OnRequestInputPIN onRequestInputPIN = null;
    private OnRequestSinature onRequestSinature = null;
    private OnConfirmCertInfo onConfirmCertInfo = null;
    private OnAARequestOnlineProcess onAARequestOnlineProcess = null;
    private OnTransactionResult onTransactionResult = null;
    private OnError onError = null;

    private GlobleData() {
        pboClistener = new OnlinePBOClistener();
    }

    public static GlobleData getInstance() {
        if (instance == null) {
            synchronized (GlobleData.class) {
                if (instance == null) {
                    instance = new GlobleData();
                }
            }
        }
        return instance;
    }

    public void init(){
        card = new Card();
        send8583Package = null;
        if(datas==null){
            datas= new HashMap<>();
        }else {
            datas.clear();
        }

    }

    public void cleanData() {
        card = new Card();
        send8583Package = null;
        if(datas==null){
            datas= new HashMap<>();
        }else {
            datas.clear();
        }
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Base8583Package getSend8583Package() {
        if(send8583Package==null){
            throw new NullPointerException("8583Package is null, please call setSend8583Package() first");
        }
        return send8583Package;
    }

    public void setSend8583Package(Base8583Package send8583Package) {
        this.send8583Package = send8583Package;
    }

    public OnlinePBOClistener getPboClistener() {
        return pboClistener;
    }

    public OnFindingCard getOnFindingCard() {
        return pboClistener.getOnFindingCard();
    }

    public void setOnFindingCard(OnFindingCard onFindingCard) {
        this.pboClistener.setOnFindingCard(onFindingCard);
    }

    public OnSelectApplication getOnSelectApplication() {
        return pboClistener.getOnSelectApplication();
    }

    public void setOnSelectApplication(OnSelectApplication onSelectApplication) {
        this.pboClistener.setOnSelectApplication(onSelectApplication);
    }

    public OnConfirmCardInfo getOnConfirmCardInfo() {
        return pboClistener.getOnConfirmCardInfo();
    }

    public void setOnConfirmCardInfo(OnConfirmCardInfo onConfirmCardInfo) {
        this.pboClistener.setOnConfirmCardInfo(onConfirmCardInfo);
    }

    public OnRequestInputPIN getOnRequestInputPIN() {
        return pboClistener.getOnRequestInputPIN();
    }

    public void setOnRequestInputPIN(OnRequestInputPIN onRequestInputPIN) {
        this.pboClistener.setOnRequestInputPIN(onRequestInputPIN);
    }

    public OnRequestSinature getOnRequestSinature() {
        return this.pboClistener.getOnRequestSinature();
    }

    public void setOnRequestSinature(OnRequestSinature onRequestSinature) {
        this.pboClistener.setOnRequestSinature(onRequestSinature);
    }

    public OnConfirmCertInfo getOnConfirmCertInfo() {
        return pboClistener.getOnConfirmCertInfo();
    }

    public void setOnConfirmCertInfo(OnConfirmCertInfo onConfirmCertInfo) {
        this.pboClistener.setOnConfirmCertInfo(onConfirmCertInfo);
    }

    public OnAARequestOnlineProcess getOnAARequestOnlineProcess() {
        return pboClistener.getOnAARequestOnlineProcess();
    }

    public void setOnAARequestOnlineProcess(OnAARequestOnlineProcess onAARequestOnlineProcess) {
        this.pboClistener.setOnAARequestOnlineProcess(onAARequestOnlineProcess);
    }

    public OnTransactionResult getOnTransactionResult() {
        return pboClistener.getOnTransactionResult();
    }

    public void setOnTransactionResult(OnTransactionResult onTransactionResult) {
        this.pboClistener.setOnTransactionResult(onTransactionResult);
    }

    public OnError getOnError() {
        return onError;
    }

    public void setOnError(OnError onError) {
        this.onError = onError;
    }
}
