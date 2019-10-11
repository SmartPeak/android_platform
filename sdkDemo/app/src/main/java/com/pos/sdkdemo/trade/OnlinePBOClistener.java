package com.pos.sdkdemo.trade;

import android.content.Context;
import android.content.Intent;
import android.os.Looper;
import android.util.Log;

import com.basewin.aidl.OnPBOCListener;
import com.basewin.services.ServiceManager;
import com.pos.sdkdemo.interfaces.OnChoseListener;
import com.pos.sdkdemo.trade.interfac.OnAARequestOnlineProcess;
import com.pos.sdkdemo.trade.interfac.OnConfirmCardInfo;
import com.pos.sdkdemo.trade.interfac.OnConfirmCertInfo;
import com.pos.sdkdemo.trade.interfac.OnError;
import com.pos.sdkdemo.trade.interfac.OnFindingCard;
import com.pos.sdkdemo.trade.interfac.OnRequest;
import com.pos.sdkdemo.trade.interfac.OnRequestInputPIN;
import com.pos.sdkdemo.trade.interfac.OnRequestSinature;
import com.pos.sdkdemo.trade.interfac.OnSelectApplication;
import com.pos.sdkdemo.trade.interfac.OnTransactionResult;
import com.pos.sdkdemo.widgets.EnterDialog;

import java.util.List;

public class OnlinePBOClistener implements OnPBOCListener {

    private OnFindingCard onFindingCard = null;
    private OnSelectApplication onSelectApplication = null;
    private OnConfirmCardInfo onConfirmCardInfo = null;
    private OnRequestInputPIN onRequestInputPIN = null;
    private OnRequestSinature onRequestSinature = null;
    private OnConfirmCertInfo onConfirmCertInfo = null;
    private OnAARequestOnlineProcess onAARequestOnlineProcess = null;
    private OnTransactionResult onTransactionResult = null;
    private OnError onError = null;

    @Override
    public void onError(Intent intent) throws Exception {
        if (onError != null) {
            onError.onError(intent);
        }
    }

    @Override
    public void onFindingCard(int i, Intent intent) throws Exception {
        if(onFindingCard!=null){
            Log.e("OnlinePBOClistener", "onFindingCard: ");
            onFindingCard.findingCard(i,intent);
        }
    }

    @Override
    public void onContactlessCardType(int i) throws Exception {

    }

    @Override
    public void onStartPBOC() throws Exception {

    }

    @Override
    public void onSelectApplication(List<String> list) throws Exception {
        if (onSelectApplication != null) {
            onSelectApplication.selectApplication(list);
        }
    }

    @Override
    public void onConfirmCertInfo(String s, String s1) throws Exception {
        if (onConfirmCertInfo != null) {
            onConfirmCertInfo.confirmCertInfo(s, s1);
        }
    }

    @Override
    public void onConfirmCardInfo(Intent intent) throws Exception {
        if (onConfirmCardInfo != null) {
            onConfirmCardInfo.confirmCardInfo(intent);
        }
    }

    @Override
    public void onRequestInputPIN(boolean b, int i) throws Exception {
        if (onRequestInputPIN != null) {
            onRequestInputPIN.requestInputPIN(b, i);
        }
    }

    @Override
    public void onAARequestOnlineProcess(Intent intent) throws Exception {
        if (onAARequestOnlineProcess != null) {
            onAARequestOnlineProcess.AARequestOnlineProcess(intent);
        }
    }

    @Override
    public void onTransactionResult(int i, Intent intent) throws Exception {
        if (onTransactionResult != null) {
            onTransactionResult.transactionResult(i, intent);
        }
    }

    @Override
    public void onRequestAmount() throws Exception {

    }

    @Override
    public void onReadECBalance(Intent intent) throws Exception {

    }

    @Override
    public void onReadCardOfflineRecord(Intent intent) throws Exception {

    }

    @Override
    public void onRupayContactlessSecondTapCard() throws Exception {

    }

    @Override
    public void onRequestSinature() throws Exception {
        if(onRequestSinature!=null){
            onRequestSinature.requestSinature();
        }
    }

    @Override
    public void onFindSelectAid(String s) throws Exception {

    }

    public OnFindingCard getOnFindingCard() {
        return onFindingCard;
    }

    public void setOnFindingCard(OnFindingCard onFindingCard) {
        this.onFindingCard = onFindingCard;
    }

    public OnRequestInputPIN getOnRequestInputPIN() {
        return onRequestInputPIN;
    }

    public void setOnRequestInputPIN(OnRequestInputPIN onRequestInputPIN) {
        this.onRequestInputPIN = onRequestInputPIN;
    }

    public OnConfirmCardInfo getOnConfirmCardInfo() {
        return onConfirmCardInfo;
    }

    public void setOnConfirmCardInfo(OnConfirmCardInfo onConfirmCardInfo) {
        this.onConfirmCardInfo = onConfirmCardInfo;
    }

    public OnConfirmCertInfo getOnConfirmCertInfo() {
        return onConfirmCertInfo;
    }

    public void setOnConfirmCertInfo(OnConfirmCertInfo onConfirmCertInfo) {
        this.onConfirmCertInfo = onConfirmCertInfo;
    }

    public OnSelectApplication getOnSelectApplication() {
        return onSelectApplication;
    }

    public void setOnSelectApplication(OnSelectApplication onSelectApplication) {
        this.onSelectApplication = onSelectApplication;
    }

    public OnAARequestOnlineProcess getOnAARequestOnlineProcess() {
        return onAARequestOnlineProcess;
    }

    public OnRequestSinature getOnRequestSinature() {
        return onRequestSinature;
    }

    public void setOnRequestSinature(OnRequestSinature onRequestSinature) {
        this.onRequestSinature = onRequestSinature;
    }

    public void setOnAARequestOnlineProcess(OnAARequestOnlineProcess onAARequestOnlineProcess) {
        this.onAARequestOnlineProcess = onAARequestOnlineProcess;
    }

    public OnTransactionResult getOnTransactionResult() {
        return onTransactionResult;
    }

    public void setOnTransactionResult(OnTransactionResult onTransactionResult) {
        this.onTransactionResult = onTransactionResult;
    }

    public OnError getOnError() {
        return onError;
    }

    public void setOnError(OnError onError) {
        this.onError = onError;
    }
}
