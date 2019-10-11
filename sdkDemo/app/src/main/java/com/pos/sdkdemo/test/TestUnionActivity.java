package com.pos.sdkdemo.test;

import java.io.BufferedInputStream;
import java.io.Console;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.security.KeyStore;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CountDownLatch;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

import com.basewin.aidl.OnPrinterListener;
import com.basewin.define.TransactionType;
import com.basewin.log.LogUtil;
import com.basewin.packet8583.exception.Packet8583Exception;
import com.basewin.packet8583.factory.Iso8583Manager;
import com.basewin.print.DataCollecter;
import com.basewin.printService.PrintParams;
import com.basewin.printService.PrintService;
import com.basewin.services.ServiceManager;
import com.basewin.utils.AppUtil;
import com.basewin.utils.BCDHelper;
import com.basewin.utils.BytesUtil;
import com.basewin.utils.TimerCountTools;
import com.basewin.zxing.utils.QRUtil;
import com.pos.sdk.accessory.PosAccessoryManager;
import com.pos.sdk.emvcore.PosEmvCoreManager;
import com.pos.sdk.emvcore.PosEmvParam;
import com.pos.sdk.emvcore.PosTermInfo;
import com.pos.sdkdemo.R;

import android.R.integer;
import android.app.Activity;
import android.graphics.Bitmap;
import android.view.View;

/**
 * activity for union test only
 *
 * @author liudy
 *
 */
public class TestUnionActivity extends Activity {

    TimerCountTools timerCountTools = null;

    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

    };

    /**
     * for test
     *
     * @param view
     * @throws IOException
     */
    public void test(View view){
        try {
//			ServiceManager.getInstence().getPinpad().GenerateRsaKeyPair(1);
//	        byte[] encryptedData = ServiceManager.getInstence().getPinpad().encrypByRsaPub(1, new byte[]{0x00, 0x02, 0x03, 0x00, 0x00, 0x01, 0x02, 0x03});
//	        byte[] decryptedData = ServiceManager.getInstence().getPinpad().decryptByRsaPri(1, encryptedData);
//	        ServiceManager.getInstence().getPinpad().GenerateRsaKeyPair(1);
            byte[] encryptedData = ServiceManager.getInstence().getPinpad().encrypByRsaPub(1, new byte[]{0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00});
            byte[] decryptedData = ServiceManager.getInstence().getPinpad().decryptByRsaPri(1, encryptedData);
            LogUtil.si(getClass(), "encryptedData.length="+encryptedData.length);
            LogUtil.si(getClass(), "decryptedData.length="+decryptedData.length);
            LogUtil.si(getClass(), "encryptedData="+BCDHelper.bcdToString(encryptedData, 0, encryptedData.length));
            LogUtil.si(getClass(), "decryptedData="+BCDHelper.bcdToString(decryptedData, 0, decryptedData.length));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }




    }

    private void synchSpTime() throws Exception {
        LogUtil.si(getClass(), "获取SP时间");
        LogUtil.si(getClass(), "Acquisition of SP time");
        byte[] time = PosAccessoryManager.getDefault().getDateTime();;
        LogUtil.si(getClass(), "获取SP时间:" + BCDHelper.hex2DebugHexString(time, time.length));
        LogUtil.si(getClass(), "Acquisition of SP time:" + BCDHelper.hex2DebugHexString(time, time.length));
        DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        String date = df.format(new Date());
        LogUtil.si(getClass(), "获取系统时间:" + date);
        LogUtil.si(getClass(), "Acquisition system time:" + date);
        byte[] system_time = BCDHelper.stringToBcd(date, date.length());
        // 比对system时间与SP时间，年月日时分需要对应，不然就同步
        if (!BCDHelper.memcmp(time, 0, system_time, 1, 5)) {
            LogUtil.si(getClass(), "sp时间与系统时间不匹配，同步！");
            LogUtil.si(getClass(), "Sp time does not match system time，synchronizing！");
            System.arraycopy(system_time, 1, time, 0, 5);
            PosAccessoryManager.getDefault().setDateTime(time);
        }
    }
    String auth() {
        StringBuilder result = new StringBuilder();
        InputStream trustCertIS = null;
        InputStream localCertIS = null;
        InputStream remoteIS = null;
        HttpsURLConnection connection = null;
        try {
            TrustManager[] tm = null;
            trustCertIS = new BufferedInputStream(getAssets().open("stunnel_s.bks"));
            KeyStore ts = KeyStore.getInstance("BKS");
            ts.load(trustCertIS, null);
            TrustManagerFactory tmf = TrustManagerFactory.getInstance("X509");
            tmf.init(ts);
            tm = tmf.getTrustManagers();

            // KeyManager[] km = null;
            // localCertIS = new
            // BufferedInputStream(getAssets().open("stunnel_s.bks"));
            // KeyStore kks = KeyStore.getInstance("BKS");
            // kks.load(localCertIS, null);

            // KeyManagerFactory kmf = KeyManagerFactory.getInstance("X509");
            // kmf.init(null, null);
            // km = kmf.getKeyManagers();

            SSLContext sslCtx = SSLContext.getInstance("TLSv1.2");
            sslCtx.init(null, tm, null);

            SSLSocketFactory factory = sslCtx.getSocketFactory();
            SSLSocket sslSocket = (SSLSocket) factory.createSocket("196.038.095.008", 443);
            sslSocket.setSoTimeout(1000 * 10);
            ObjectOutputStream out = new ObjectOutputStream(sslSocket.getOutputStream());
            out.writeUTF("1234");
            out.flush();

            result.append("Verify OK!");

            sslSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
            result = new StringBuilder("Response error!\n" + e.getMessage());
        } finally {
            if (trustCertIS != null) {
                try {
                    trustCertIS.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                trustCertIS = null;
            }

            if (localCertIS != null) {
                try {
                    localCertIS.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                localCertIS = null;
            }

            if (remoteIS != null) {
                try {
                    remoteIS.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                remoteIS = null;
            }

            if (connection != null) {
                connection.disconnect();
                connection = null;
            }
        }

        if (result.length() <= 0) {
            result.append("Response unknown!");
        }
        return result.toString();
    }
}
