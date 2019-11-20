package com.pos.sdkdemo.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.pos.sdkdemo.pinpad.PinpadInterfaceVersion;

/**
 * @author liudy
 */
public class GlobalData {
    private static final String TAG = "GlobalData";
    private static final String PREFERENCES = "global_data";
    public Context mContext;
    private SharedPreferences mPrefs;
    private Editor mEditor;
    private static GlobalData instance = null;

    /**
     * keys define
     */
    private static final String PINPADVERSION = "pinpad_version";  /*version for pinpad*/
    private static final String AREA = "area"; /*area for pinpad version 3.0*/
    private static final String TMKID = "tmkid"; /*tmk index for pinpad version 2.0 and 3.0*/

    private static final String WELCOME_SHOWS = "welcome_show"; /*show guider or not*/
    private static final String GUIDER_SHOWS = "guider_show"; /*show guider or not*/

    private static final String LOGIN = "login"; /*login*/
    private static final String SETPINKEY = "setpinkey"; /*setpinkey*/
    private static final int DEFAULTAREA = 1;
    private static final int DEFAULTTMKID = 1;

    public static boolean ifEntransActivityExist = false;
    private GlobalData() {

    }

    public void init(Context context) {
        if (instance == null) {
            instance = new GlobalData();
        }
        mContext = context;
        mPrefs = mContext.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        mEditor = mPrefs.edit();
    }

    public static GlobalData getInstance() {
        if (instance == null) {
            instance = new GlobalData();
        }
        return instance;
    }

    /**
     * set the version which use for now
     * @param pinpadVersion
     */
    public void setPinpadVersion(int pinpadVersion) {
        mEditor.putInt(PINPADVERSION, pinpadVersion);
        mEditor.commit();
    }

    /**
     * get the version which use for now
     * @return
     */
    public int getPinpadVersion() {
        return mPrefs.getInt(PINPADVERSION, PinpadInterfaceVersion.PINPAD_INTERFACE_VERSION3);
    }

    /**
     * set the area which use for now
     * @param area
     */
    public void setArea(int area) {
        mEditor.putInt(AREA, area);
        mEditor.commit();
    }

    /**
     * get the area which use for now
     * @return
     */
    public int getArea() {
        return mPrefs.getInt(AREA, DEFAULTAREA);
    }

    /**
     * set the tmkid which use for now
     * @param tmkid
     */
    public void setTmkId(int tmkid) {
        mEditor.putInt(TMKID, tmkid);
        mEditor.commit();
    }

    /**
     * get the tmkid which use for now
     * @return
     */
    public int getTmkId() {
        return mPrefs.getInt(TMKID, DEFAULTTMKID);
    }

    /**
     * set the guider shows or not
     * @param ifShow
     */
    public void setGuiderShows(boolean ifShow) {
        mEditor.putBoolean(GUIDER_SHOWS, ifShow);
        mEditor.commit();
    }

    /**
     * get the guider status
     * @return
     */
    public boolean getGuiderShows() {
        return mPrefs.getBoolean(GUIDER_SHOWS, false);
    }
    
    /**
     * set the welcome shows or not
     * @param ifShow
     */
    public void setWelcomeShows(boolean ifShow) {
        mEditor.putBoolean(WELCOME_SHOWS, ifShow);
        mEditor.commit();
    }

    /**
     * get the welcome status
     * @return
     */
    public boolean getWelcomeShows() {
        return mPrefs.getBoolean(WELCOME_SHOWS, true);
    }

    
    /**
     * set the login
     * @param ifLogin
     */
    public void setLogin(boolean ifLogin) {
        mEditor.putBoolean(LOGIN, ifLogin);
        mEditor.commit();
    }

    /**
     * get the login status
     * @return
     */
    public boolean getLogin() {
        return mPrefs.getBoolean(LOGIN, false);
    }

    
    /**
     * set the pinkey flag
     * @param ifset
     */
    public void setPinkeyFlag(boolean ifset) {
        mEditor.putBoolean(SETPINKEY, ifset);
        mEditor.commit();
    }

    /**
     * get the pinkey set status
     * @return
     */
    public boolean getPinkeyFlag() {
        return mPrefs.getBoolean(SETPINKEY, false);
    }
}
