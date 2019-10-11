package com.pos.sdkdemo.test;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.util.Pair;

import com.basewin.aidl.OnPrinterListener;
import com.basewin.define.FontsType;
import com.basewin.services.PrinterBinder;
import com.pos.sdk.printer.PosPrinter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CheckPrinter {

    private static final String TAG = CheckPrinter.class.getCanonicalName();

    private enum State {
        NORMAL,
        BOLD,
        IMAGE
    }

    private static final int TEXT_SIZE = 20;

    private static final int MAX_LINES = 64;

    private PrinterBinder printerBinder;
    private Context context;

    private boolean printingFinished;

    CheckPrinter(PrinterBinder printerBinder, Context context) {
        this.printerBinder = printerBinder;
        this.context = context;
    }

    public void print() {
        String testReceipt =
                "================================\r\n" +
                "!       КОНТРОЛЬНАЯ ЛЕНТА        \r\n" +
                "Номер пакета:                001\r\n" +
                "Номер смены:                 001\r\n" +
                "Дата:        19/09/2018 12:23:25\r\n" +
                "ID ТЕРМИНАЛА:           25468768\r\n" +
                "          ВСЕ ЭЛЕМЕНТЫ          \r\n" +
                "================================\r\n" +
                "EUR\r\n" +
                "Чек:000003     25/07    17.56.11\r\n" +
                "            ВОЗВРАТ             \r\n" +
                "Сумма(EUR)               2222.22\r\n" +
                "VISA\r\n" +
                "************2685\r\n" +
                "Код ответа:000 Код автор.:084077\r\n" +
                "================================\r\n" +
                "Руб\r\n" +
                "Чек:000018     26/07    20.48.38\r\n" +
                "             ОПЛАТА             \r\n" +
                "Сумма(Руб)                 25.55\r\n" +
                "VISA\r\n" +
                "************9532\r\n" +
                "Код ответа:000 Код автор.:455539\r\n" +
                "================================\r\n" +
                "Руб\r\n" +
                "Чек:000019     26/07    20.50.06\r\n" +
                "             ОПЛАТА             \r\n" +
                "Сумма(Руб)                  5.55\r\n" +
                "VISA\r\n" +
                "************7423\r\n" +
                "Код ответа:000 Код автор.:688494\r\n" +
                "================================\r\n" +
                "Руб\r\n" +
                "Чек:000020     26/07    21.00.59\r\n" +
                "             ОПЛАТА             \r\n" +
                "Сумма(Руб)                 25.55\r\n" +
                "VISA\r\n" +
                "************2219\r\n" +
                "Код ответа:000 Код автор.:008445\r\n" +
                "================================\r\n" +
                "Руб\r\n" +
                "Чек:000024     27/07    16.58.30\r\n" +
                "             ОПЛАТА             \r\n" +
                "Сумма(Руб)                  7.77\r\n" +
                "VISA\r\n" +
                "************2685\r\n" +
                "Код ответа:000 Код автор.:057324\r\n" +
                "================================\r\n" +
                "Руб\r\n" +
                "Чек:000026     27/07    17.30.21\r\n" +
                "             ОПЛАТА             \r\n" +
                "Сумма(Руб)                100.00\r\n" +
                "MasterCard\r\n" +
                "************0045\r\n" +
                "Код ответа:000 Код автор.:304302\r\n" +
                "================================\r\n" +
                "Руб\r\n" +
                "Чек:000027     27/07    17.33.29\r\n" +
                "             ОПЛАТА             \r\n" +
                "Сумма(Руб)                100.00\r\n" +
                "MasterCard\r\n" +
                "************0045\r\n" +
                "Код ответа:000 Код автор.:634308\r\n" +
                "================================\r\n" +
                "Руб\r\n" +
                "Чек:000028     27/07    17.40.30\r\n" +
                "             ОПЛАТА             \r\n" +
                "Сумма(Руб)                100.00\r\n" +
                "MasterCard\r\n" +
                "************0045\r\n" +
                "Код ответа:000 Код автор.:171031\r\n" +
                "================================\r\n" +
                "Руб\r\n" +
                "Чек:000029     27/07    17.41.29\r\n" +
                "             ОПЛАТА             \r\n" +
                "Сумма(Руб)                100.00\r\n" +
                "MasterCard\r\n" +
                "************0045\r\n" +
                "Код ответа:000 Код автор.:273843\r\n" +
                "================================\r\n" +
                "Руб\r\n" +
                "Чек:000030     30/07    13.24.45\r\n" +
                "             ОПЛАТА             \r\n" +
                "Сумма(Руб)                  0.25\r\n" +
                "VISA\r\n" +
                "************9532\r\n" +
                "Код ответа:000 Код автор.:353509\r\n" +
                "================================\r\n" +
                "Руб\r\n" +
                "Чек:000031     30/07    13.30.43\r\n" +
                "             ОПЛАТА             \r\n" +
                "Сумма(Руб)                 10.00\r\n" +
                "VISA\r\n" +
                "************9532\r\n" +
                "Код ответа:000 Код автор.:563988\r\n" +
                "================================\r\n" +
                "Руб\r\n" +
                "Чек:000032     30/07    13.35.49\r\n" +
                "             ОПЛАТА             \r\n" +
                "Сумма(Руб)                 25.55\r\n" +
                "VISA\r\n" +
                "************9532\r\n" +
                "Код ответа:000 Код автор.:919013\r\n" +
                "================================\r\n" +
                "Руб\r\n" +
                "Чек:000033     30/07    13.38.01\r\n" +
                "             ОПЛАТА             \r\n" +
                "Сумма(Руб)                 25.55\r\n" +
                "VISA\r\n" +
                "************9532\r\n" +
                "Код ответа:000 Код автор.:379528\r\n" +
                "================================\r\n" +
                "Руб\r\n" +
                "Чек:000034     30/07    13.38.49\r\n" +
                "            ВОЗВРАТ             \r\n" +
                "Сумма(Руб)                  8.55\r\n" +
                "VISA\r\n" +
                "************9532\r\n" +
                "Код ответа:000 Код автор.:049571\r\n" +
                "================================\r\n" +
                "Руб\r\n" +
                "Чек:000035     30/07    13.39.14\r\n" +
                "             ОПЛАТА             \r\n" +
                "Сумма(Руб)                  2.55\r\n" +
                "VISA\r\n" +
                "************9532\r\n" +
                "Код ответа:000 Код автор.:488264\r\n" +
                "================================\r\n" +
                "Руб\r\n" +
                "Чек:000036     30/07    13.39.49\r\n" +
                "             ОПЛАТА             \r\n" +
                "Сумма(Руб)                255.55\r\n" +
                "VISA\r\n" +
                "************2219\r\n" +
                "Код ответа:000 Код автор.:254414\r\n" +
                "================================\r\n" +
                "Руб\r\n" +
                "Чек:000037     30/07    13.42.44\r\n" +
                "             ОПЛАТА             \r\n" +
                "Сумма(Руб)                  0.36\r\n" +
                "VISA\r\n" +
                "************9532\r\n" +
                "Код ответа:000 Код автор.:776692\r\n" +
                "================================\r\n" +
                "Руб\r\n" +
                "Чек:000038     30/07    14.17.25\r\n" +
                "             ОПЛАТА             \r\n" +
                "Сумма(Руб)                  7.77\r\n" +
                "MasterCard\r\n" +
                "************0045\r\n";



        printingFinished = false;

        printerBinder.cleanCache();

        List<String> stringsList = Arrays.asList(testReceipt.split("\r\n", -1));

        if (stringsList.isEmpty())
            return;

        State prevState = State.NORMAL;

        List<Pair<State, StringBuilder> > strList = new ArrayList<>();
        List<Bitmap> bitmapList = new ArrayList<>();

        try {
            printerBinder.setPrintFont(FontsType.DroidSansMono);

            PosPrinter.Parameters parameters = printerBinder.getParameters();
            parameters.setLineSpace(2);

            printerBinder.setParameters(parameters);
        } catch (Exception e) {
            e.printStackTrace();
        }


        int stepSize = 0;
        int i = 0;

        for (String stringForPrint : stringsList) {
            i++;

            State currState = State.NORMAL;

            String imageFile = "";

            if (!stringForPrint.isEmpty()) {
                if (stringForPrint.charAt(0) == '!') {
                    stringForPrint = stringForPrint.substring(1, stringForPrint.length());
                    currState = State.BOLD;
                }

                if (stringForPrint.charAt(0) == '#') {
                    stringForPrint = stringForPrint.substring(1, stringForPrint.length());
                    currState = State.BOLD;
                }

                if (stringForPrint.charAt(0) == '&') {
                    stringForPrint = stringForPrint.substring(1, stringForPrint.length());
                    currState = State.BOLD;
                }

                if (stringForPrint.charAt(0) == '~') {
                    String imageName = stringForPrint.substring(1, stringForPrint.length()).replace("~", "").trim();

                    stringForPrint = "";
                    imageFile = context.getFilesDir().getAbsolutePath() + "/conffiles/" + imageName;
                    currState = State.IMAGE;

                    if (new File(imageFile).exists()) {
                        Bitmap bitmap = BitmapFactory.decodeFile(imageFile);
                        bitmapList.add(bitmap);
                    } else {
                        imageFile = null;
                    }
                }
            }

            if (currState == State.IMAGE && imageFile != null) {
                strList.add(new Pair<>(currState, new StringBuilder()));
            } else if (currState != prevState) {
                strList.add(new Pair<>(currState, new StringBuilder()));
            }

            if (strList.size() == 0) {
                strList.add(new Pair<>(currState, new StringBuilder()));
            }

            if (currState != State.IMAGE) {
                strList.get(strList.size() - 1).second.append(stringForPrint);
                if (stringForPrint.length() < 32)
                    strList.get(strList.size() - 1).second.append("\n");
            }

            stepSize ++;

            prevState = currState;

            if (stepSize == MAX_LINES || (stepSize < MAX_LINES && i == stringsList.size() - 1)) {
                JSONObject root = new JSONObject();
                JSONArray sposArray = new JSONArray();

                for (Pair<State, StringBuilder> pair : strList) {
                    JSONObject jsonObject = getPrintObject(pair.second, pair.first);

                    sposArray.put(jsonObject);
                }

                try {
                    root.put("spos", sposArray);
                } catch (JSONException e) {
                    Log.e(TAG, "generate check error", e);
                }

                try {
                    Bitmap[] stockArr = new Bitmap[bitmapList.size()];
                    stockArr = bitmapList.toArray(stockArr);

                    printerBinder.printNoFeed(root.toString(), stockArr, new OnPrinterListener() {
                        @Override
                        public void onError(int i, String s) {
                            printingFinished = true;
                        }

                        @Override
                        public void onFinish() {
                            printingFinished = true;
                        }

                        @Override
                        public void onStart() {
                        }
                    });
                } catch (Exception e) {
                    Log.e(TAG, "print error", e);
                }

                while (!printingFinished) {
                    continue;
                }

                printingFinished = false;

                strList.clear();

                for (Bitmap bitmap: bitmapList) {
                    bitmap.recycle();
                }

                bitmapList.clear();

                stepSize = 0;
            }
        }
    }

    private JSONObject getPrintObject( StringBuilder stringBuilder, State state) {
        JSONObject json = new JSONObject();

        try {
            if (state != State.IMAGE) {
                json.put("content", stringBuilder.toString());
                json.put("content-type", "txt");
            } else {
                json.put("content-type","jpg");
            }

            json.put("position", "left");
            json.put("size", TEXT_SIZE + "");
            json.put("offset", "0");
            json.put("bold", state == State.BOLD ? "1" : "0");
            json.put("italic", "0");
        } catch (JSONException e) {
            Log.e(TAG, "generate check error", e);
        }

        return json;
    }

    public boolean printInProgress() {
        return !printingFinished;
    }

    public boolean isPaperMissing() {
        return !printerBinder.queryIfHavePaper();
    }

    public void open() {
    }

    public void close() {
    }
}
