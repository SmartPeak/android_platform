package com.pos.sdkdemo.trade.interfac;

import android.content.Intent;

public interface OnRequest {
    public void AARequestOnlineProcess(Intent intent);
    public void onTransactionResult(int i, Intent intent);
}
