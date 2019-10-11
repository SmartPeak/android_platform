package com.pos.sdkdemo.trade.net8583;

import com.basewin.packet8583.factory.Iso8583Manager;
import com.pos.sdkdemo.trade.bean.Card;

import java.io.UnsupportedEncodingException;
import java.util.Map;

public abstract class Base8583Package {

    public Base8583Package() {
    }

    public abstract Iso8583Manager init8583();

    public abstract Iso8583Manager package8583(Map data, Card card);
}
