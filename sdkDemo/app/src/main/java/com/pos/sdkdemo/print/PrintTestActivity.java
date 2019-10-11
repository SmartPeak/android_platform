package com.pos.sdkdemo.print;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.basewin.aidl.OnPrinterListener;
import com.basewin.define.FontsType;
import com.basewin.define.GlobalDef;
import com.basewin.define.PrinterInfo;
import com.basewin.models.BitmapPrintLine;
import com.basewin.models.PrintLine;
import com.basewin.models.TextPrintLine;
import com.basewin.services.PrinterBinder;
import com.basewin.services.ServiceManager;
import com.basewin.zxing.utils.QRUtil;
import com.pos.sdkdemo.R;
import com.pos.sdkdemo.base.BaseActivity;
import com.pos.sdkdemo.interfaces.OnChoseListener;
import com.pos.sdkdemo.utils.TimerCountTools;
import com.pos.sdkdemo.widgets.EnterDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.List;

/**
 * Created by lyw on 2016/12/12. print function: print the
 * text,one-dimension,two-dimension or picture on pager,you can input text or
 * select a picture from you deivce to print,and you can set the type of
 * printing ,textSie,fontsType or positon.
 */

public class PrintTestActivity extends BaseActivity implements View.OnClickListener {
    private static final int REQUEST_CODE_GALLERY = 100;
    public static String Elemo = "****#1饿了么外卖订单****\n\n        卡萨披萨       \n\n       --已支付--      \n\n"
            + "      预计19:00送达     \n\n[时间]:2014-12-03 16:21\n\n  不吃辣 辣一点 多加米\n\n "
            + "[发票]这是一个发票抬头\n\n------------------------\n\n菜名          数量  "
            + "小计\n\n--------1号篮子---------\n\n测试美食一        X4   4\n\n"
            + "测试美食二        X6   6\n\n测试美食三        X2   2\n\n" + "--------2号篮子---------\n\n"
            + "测试1             X1   1\n\n测试2             X1   1\n\n"
            + "测试3             X1  23\n\n(+)测试西式甜点   X1   1\n\n" + "(+)测试酸辣       X1   1\n\n--------3号篮子---------\n\n"
            + "测试菜品名字很长很长很长\n\n测试              X1   1\n\n--------其它费用--------\n\n配送费\n\n\n";
    public static String Baidu = "本店留存\n************************\n      百度外卖\n      [货到付款]\n"
            + "************************\n期望送达时间：立即配送\n"
            + "订单备注:送到西门,不要辣\n发票信息:百度外卖\n************************\n下单编号: 14187186911689\n下单时间: "
            + "2014-12-16 16\n************************\n" + "菜品名称     数量  金额\n------------------------\n"
            + "香辣面套餐     1   40.00\n素食天线汉堡   1   38.00\n香辣面套餐     1   40.00\n"
            + "素食天线汉堡   1   38.00\n香辣面         1   43.00\n" + "素食天线       1   34.00\n" + "------------------------\n"
            + "************************\n姓名:百度测试\n" + "地址:泰然工贸园\n电话:18665248965\n"
            + "************************\n百度测试商户\n" + "18665248965\n#15 百度外卖 11月09号";
    private final String TAG = this.getClass().getSimpleName();
    JSONObject printJson = new JSONObject();
    private EditText et_printTextContent, et_printTextSize, et_printGray, et_lineSpace, et_printoneContent,
            et_printtwoContent;
    private Spinner sp_printTextSize, sp_printTextPosition, sp_fontsType, sp_printPosition1, sp_printPosition2,
            sp_printHeight1, sp_printSize1, sp_printSize2;
    private ImageView iv_testImage;
    private LinearLayout ll_selectprinter;
    private CheckBox cb_italic, cb_bold;
    private TextView tv_printerinfo;
    private String printTextContent, printTextSize, ePrintTextSize, printGray, printPosition, fontsType, italic, bold,
            lineSpace, printoneContent, printtwoContent, onePosition, twoPosition, oneHeight, oneSize, twoSize;
    private Button btn_print_demo, btn_print, btn_selectPic, btn_print_text, btn_print_one, btn_print_two,
            btn_print_pic, btn_print_derect;
    private PrinterListener printer_callback = new PrinterListener();
    private TimerCountTools timeTools;
    private Bitmap bm;
    private byte[] mContent;

    @Override
    protected View onCreateView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.activity_print, null);
    }

    @Override
    protected void onInitView() {
        initView();
    }

    private void initView() {
        iv_testImage = (ImageView) findViewById(R.id.iv_testImage);
        tv_printerinfo = (TextView) findViewById(R.id.tv_printerinfo);
        ll_selectprinter = (LinearLayout) findViewById(R.id.select_printer);
        et_printoneContent = (EditText) findViewById(R.id.et_printoneContent);
        et_printtwoContent = (EditText) findViewById(R.id.et_printtwoContent);
        btn_selectPic = (Button) findViewById(R.id.btn_selectPic);
        btn_selectPic.setOnClickListener(this);
        btn_print_text = (Button) findViewById(R.id.btn_print_text);
        btn_print_one = (Button) findViewById(R.id.btn_print_onedimension);
        btn_print_two = (Button) findViewById(R.id.btn_print_twodimension);
        btn_print_pic = (Button) findViewById(R.id.btn_print_pic);
        btn_print_demo = (Button) findViewById(R.id.btn_print_demo);
        btn_print_derect = (Button) findViewById(R.id.btn_print_derect);
        btn_print_text.setOnClickListener(this);
        btn_print_one.setOnClickListener(this);
        btn_print_pic.setOnClickListener(this);
        btn_print_two.setOnClickListener(this);
        btn_print_demo.setOnClickListener(this);
        btn_print_derect.setOnClickListener(this);
        ll_selectprinter.setOnClickListener(this);

        et_printTextContent = (EditText) findViewById(R.id.et_printTextContent);
        optimizSoftKeyBoard(et_printTextContent);
        et_printTextSize = (EditText) findViewById(R.id.et_printTextSize);
        et_printGray = (EditText) findViewById(R.id.et_printGray);
        et_lineSpace = (EditText) findViewById(R.id.et_lineSpace);
        btn_print = (Button) findViewById(R.id.btn_print);
        btn_print.setOnClickListener(this);

        onePosition = "center";
        // select the printing position of the one-dimesion,default position is
        // centet
        sp_printPosition1 = (Spinner) findViewById(R.id.sp_printPosition1);
        sp_printPosition1.setSelection(1);
        sp_printPosition1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        onePosition = "left";
                        break;
                    case 1:
                        onePosition = "center";
                        break;
                    case 2:
                        onePosition = "right";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                onePosition = "center";
            }
        });
        // select the printing height of the one-dimesion ,default height is 3.
        sp_printHeight1 = (Spinner) findViewById(R.id.sp_printHeight1);
        sp_printHeight1.setSelection(2);
        sp_printHeight1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        oneHeight = "1";
                        break;
                    case 1:
                        oneHeight = "2";
                        break;
                    case 2:
                        oneHeight = "3";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                oneHeight = "2";
            }
        });
        // select the printing size of the one-dimesion , default value is 3.
        sp_printSize1 = (Spinner) findViewById(R.id.sp_printSize1);
        sp_printSize1.setSelection(2);
        sp_printSize1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        oneSize = "1";
                        break;
                    case 1:
                        oneSize = "2";
                        break;
                    case 2:
                        oneSize = "3";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                oneSize = "2";
            }
        });

        // select the printing position of the two-dimesion,default position is
        // right
        sp_printPosition2 = (Spinner) findViewById(R.id.sp_printPosition2);
        sp_printPosition2.setSelection(2);
        sp_printPosition2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        twoPosition = "left";
                        break;
                    case 1:
                        twoPosition = "center";
                        break;
                    case 2:
                        twoPosition = "right";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                twoPosition = "center";
            }
        });
        // select the
        sp_printSize2 = (Spinner) findViewById(R.id.sp_printSize2);
        sp_printSize2.setSelection(7);
        sp_printSize2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        twoSize = "1";
                        break;
                    case 1:
                        twoSize = "2";
                        break;
                    case 2:
                        twoSize = "3";
                        break;
                    case 3:
                        twoSize = "4";
                        break;
                    case 4:
                        twoSize = "5";
                        break;
                    case 5:
                        twoSize = "7";
                        break;
                    case 6:
                        twoSize = "8";
                        break;
                    case 7:
                        twoSize = "6";
                        break;
                    default:
                        twoSize = "6";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        printPosition = "center";
        italic = "0";
        bold = "0";
        // Select Italic property
        cb_italic = (CheckBox) findViewById(R.id.cb_italic);
        cb_italic.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    italic = "1";
                } else {
                    italic = "0";
                }
            }
        });
        // Select Bold property
        cb_bold = (CheckBox) findViewById(R.id.cb_bold);
        cb_bold.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    bold = "1";
                } else {
                    bold = "0";
                }
            }
        });
        // Set TextSize property
        sp_printTextSize = (Spinner) findViewById(R.id.sp_printTextSize);
        sp_printTextSize.setSelection(2);
        sp_printTextSize.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                et_printTextSize.setText("");// if select a value ,make the
                // edit_input blank
                switch (position) {
                    case 0:
                        printTextSize = "1";// small
                        break;
                    case 1:
                        printTextSize = "2";// middle
                        break;
                    case 2:
                        printTextSize = "3";// big
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                if (ePrintTextSize.length() != 0) {
                    printTextSize = ePrintTextSize;
                } else {
                    printTextSize = "2";
                }
            }
        });
        // Set TextPosition property
        sp_printTextPosition = (Spinner) findViewById(R.id.sp_printTextPosition);
        sp_printTextPosition.setSelection(2);
        sp_printTextPosition.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        printPosition = "left";
                        break;
                    case 1:
                        printPosition = "center";
                        break;
                    case 2:
                        printPosition = "right";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                printPosition = "center";// default position is center
            }
        });
        // Set fontsType property
        sp_fontsType = (Spinner) findViewById(R.id.sp_fontsType);
        sp_fontsType.setSelection(0);
        sp_fontsType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        try {
                            fontsType = "";
                            ServiceManager.getInstence().getPrinter().setPrintFont(FontsType.simsun);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    case 1:
                        try {
                            ServiceManager.getInstence().getPrinter().setPrintFontByAsserts("songti.ttf");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    default:
                        fontsType = "";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // fresh printer info
        try {
            tv_printerinfo.setText(
                    "printer info fresh after select printer,make usre your system version is new or demo will crash!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("NewApi")
    private void getValue() {
        printTextContent = et_printTextContent.getText().toString().trim();
        if (printTextContent.length() == 0) {
            printTextContent = "ABC";// default content is "ABC"
        }
        ePrintTextSize = et_printTextSize.getText().toString().trim();
        if (!ePrintTextSize.isEmpty()) {
            printTextSize = ePrintTextSize;// when editInput is not blank ,using
            // the value to set the textSize
        }
        printGray = et_printGray.getText().toString().trim();
        if (printGray.length() == 0) {
            printGray = "2000";// the "printGray" default value is 2000
        }
        lineSpace = et_lineSpace.getText().toString().trim();
        if (lineSpace.length() == 0) {
            lineSpace = "2";
        }
        // validate
        printoneContent = et_printoneContent.getText().toString().trim();
        if (printoneContent.isEmpty()) {
            printoneContent = "12345678";
        }
        printtwoContent = et_printtwoContent.getText().toString().trim();
        if (printtwoContent.isEmpty()) {
            printtwoContent = "111111111";
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_print:
                printAll();
                break;
            case R.id.btn_selectPic:
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, REQUEST_CODE_GALLERY);
                break;
            case R.id.btn_print_text:
                printText();
                break;
            case R.id.btn_print_onedimension:
                printOne();
                break;
            case R.id.btn_print_twodimension:
                printTwo();
                break;
            case R.id.btn_print_pic:
                printPic();
                break;
            case R.id.btn_print_demo:
                printDemo();
                break;
            case R.id.select_printer:
                selectPrinter();
                break;

        }
    }

    /**
     * print all content
     */
    public void printAll() {
        getValue();
        LOGD("Print all");
        try {
            timeTools = new TimerCountTools();
            timeTools.start();
            //set Gray
            ServiceManager.getInstence().getPrinter().setPrintGray(Integer.valueOf(printGray));
            //set lineSpace
            ServiceManager.getInstence().getPrinter().setLineSpace(Integer.valueOf(lineSpace));
            ServiceManager.getInstence().getPrinter().setPrintTypesettingType(GlobalDef.PRINTERLAYOUT_TYPESETTING);
            ServiceManager.getInstence().getPrinter().cleanCache();
            TextPrintLine textPrintLine = new TextPrintLine();
            textPrintLine.setType(PrintLine.TEXT);
            textPrintLine.setSize(TextPrintLine.FONT_NORMAL);
            textPrintLine.setPosition(PrintLine.CENTER);
            textPrintLine.setContent(Baidu);
            ServiceManager.getInstence().getPrinter().addPrintLine(textPrintLine);
            Bitmap ywm = QRUtil.getBarcodeBMP(printoneContent,320,120);
            Bitmap ewm = QRUtil.getRQBMP(printtwoContent,220);
            BitmapPrintLine bitmapPrintLine = new BitmapPrintLine();
            bitmapPrintLine.setType(PrintLine.BITMAP);
            bitmapPrintLine.setPosition(PrintLine.CENTER);
            bitmapPrintLine.setBitmap(ywm);
            ServiceManager.getInstence().getPrinter().addPrintLine(bitmapPrintLine);
            textPrintLine.setType(PrintLine.TEXT);
            textPrintLine.setPosition(PrintLine.CENTER);
            textPrintLine.setContent("                                         ");
            ServiceManager.getInstence().getPrinter().addPrintLine(textPrintLine);
            bitmapPrintLine.setBitmap(ewm);
            ServiceManager.getInstence().getPrinter().addPrintLine(bitmapPrintLine);
            textPrintLine.setType(PrintLine.TEXT);
            textPrintLine.setPosition(PrintLine.CENTER);
            textPrintLine.setContent("                                         ");
            ServiceManager.getInstence().getPrinter().addPrintLine(textPrintLine);
            textPrintLine.setContent("                                         ");
            ServiceManager.getInstence().getPrinter().addPrintLine(textPrintLine);
            ServiceManager.getInstence().getPrinter().beginPrint(printer_callback);
            LOGD("Print success");
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * print text content
     */
    public void printText() {
        getValue();
        LOGD("Print text");
        try {
            timeTools = new TimerCountTools();
            timeTools.start();
            // set Gray
            ServiceManager.getInstence().getPrinter().setPrintGray(Integer.valueOf(printGray));
           //set lineSpace
            ServiceManager.getInstence().getPrinter().setLineSpace(Integer.valueOf(lineSpace));
            //set print type
            ServiceManager.getInstence().getPrinter().setPrintTypesettingType(GlobalDef.ANDROID_TYPESETTING);
            //clean print cache.
            ServiceManager.getInstence().getPrinter().cleanCache();
            TextPrintLine textPrintLine = new TextPrintLine();
            textPrintLine.setType(PrintLine.TEXT);
            textPrintLine.setPosition(PrintLine.CENTER);
            textPrintLine.setSize(TextPrintLine.FONT_LARGE);
            textPrintLine.setContent("asdasdasdad");
            ServiceManager.getInstence().getPrinter().addPrintLine(textPrintLine);
            textPrintLine.setContent("                                         ");
            ServiceManager.getInstence().getPrinter().addPrintLine(textPrintLine);
            textPrintLine.setContent("                                         ");
            ServiceManager.getInstence().getPrinter().addPrintLine(textPrintLine);
            ServiceManager.getInstence().getPrinter().beginPrint(printer_callback);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * print oneDimension content
     */
    public void printOne() {
        getValue();
        LOGD("Print one-dimension");
        try {
            timeTools = new TimerCountTools();
            timeTools.start();
            //set Gray
            ServiceManager.getInstence().getPrinter().setPrintGray(Integer.valueOf(printGray));
            // set lineSpace
            ServiceManager.getInstence().getPrinter().setLineSpace(Integer.valueOf(lineSpace));
            ServiceManager.getInstence().getPrinter().setPrintTypesettingType(GlobalDef.ANDROID_TYPESETTING);
            ServiceManager.getInstence().getPrinter().cleanCache();
            BitmapPrintLine bitmapPrintLine = new BitmapPrintLine();
            bitmapPrintLine.setType(PrintLine.BITMAP);
            bitmapPrintLine.setPosition(PrintLine.CENTER);
            //create QR code(max width and height is 384px)
            Bitmap bitmap = QRUtil.getBarcodeBMP(printoneContent, 320, 120);
            bitmapPrintLine.setBitmap(bitmap);
            ServiceManager.getInstence().getPrinter().addPrintLine(bitmapPrintLine);
            TextPrintLine textPrintLine = new TextPrintLine();
            textPrintLine.setType(PrintLine.TEXT);
            textPrintLine.setPosition(PrintLine.CENTER);
            textPrintLine.setSize(TextPrintLine.FONT_NORMAL);
            textPrintLine.setContent("                                         ");
            ServiceManager.getInstence().getPrinter().addPrintLine(textPrintLine);
            textPrintLine.setContent("                                         ");
            ServiceManager.getInstence().getPrinter().addPrintLine(textPrintLine);
            ServiceManager.getInstence().getPrinter().beginPrint(printer_callback);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * print twoDimension content
     */
    public void printTwo() {
        getValue();
        LOGD("Print two-dimension");
        try {
            timeTools = new TimerCountTools();
            timeTools.start();
            //set Gray
            ServiceManager.getInstence().getPrinter().setPrintGray(Integer.valueOf(printGray));
            // set lineSpace
            ServiceManager.getInstence().getPrinter().setLineSpace(Integer.valueOf(lineSpace));
            ServiceManager.getInstence().getPrinter().setPrintTypesettingType(GlobalDef.ANDROID_TYPESETTING);
            ServiceManager.getInstence().getPrinter().cleanCache();
            BitmapPrintLine bitmapPrintLine = new BitmapPrintLine();
            bitmapPrintLine.setType(PrintLine.BITMAP);
            bitmapPrintLine.setPosition(PrintLine.CENTER);
            //create QR code(max height is 384px)
            Bitmap bitmap = QRUtil.getRQBMP("7865162371263", 240);
            bitmapPrintLine.setBitmap(bitmap);
            ServiceManager.getInstence().getPrinter().addPrintLine(bitmapPrintLine);
            ServiceManager.getInstence().getPrinter().beginPrint(printer_callback);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * print picture content
     */
    public void printPic() {
        LOGD("Print picture");
        getValue();
        try {
            // add picture
            JSONObject json11 = new JSONObject();
            json11.put("content-type", "jpg");
            json11.put("position", "center");
            timeTools = new TimerCountTools();
            timeTools.start();
            //set Gray
            ServiceManager.getInstence().getPrinter().setPrintGray(Integer.valueOf(printGray));
            //set lineSpace
            ServiceManager.getInstence().getPrinter().setLineSpace(Integer.valueOf(lineSpace));
            //set print type
            ServiceManager.getInstence().getPrinter().setPrintTypesettingType(GlobalDef.ANDROID_TYPESETTING);
            //set bitmap print param
            BitmapPrintLine bitmapPrintLine = new BitmapPrintLine();
            bitmapPrintLine.setType(PrintLine.BITMAP);
            bitmapPrintLine.setPosition(PrintLine.CENTER);
            //select a picture in assets folder
            Bitmap bitmap = BitmapFactory.decodeStream(getAssets().open("test_print2.jpg"));
            bitmapPrintLine.setBitmap(PicUtils.switchColor(bitmap));
            ServiceManager.getInstence().getPrinter().addPrintLine(bitmapPrintLine);
            ServiceManager.getInstence().getPrinter().beginPrint(printer_callback);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private JSONObject getPrintObject(String test) {
        JSONObject json = new JSONObject();
        try {
            json.put("content-type", "txt");
            json.put("content", test);
            json.put("size", "2");
            json.put("position", "left");
            json.put("offset", "0");
            json.put("bold", "0");
            json.put("italic", "0");
            json.put("height", "-1");
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return json;
    }

    private JSONObject getPrintObject(String test, String size) {
        JSONObject json = new JSONObject();
        try {
            json.put("content-type", "txt");
            json.put("content", test);
            json.put("size", size);
            json.put("position", "left");
            json.put("offset", "0");
            json.put("bold", "0");
            json.put("italic", "0");
            json.put("height", "-1");
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return json;
    }

    /**
     * print text content
     */
    public void printDemo() {
        try {
            LOGD("print Demo");
            ServiceManager.getInstence().getPrinter().setPrintGray(1000);
            // 組打印json字符串
            //JSONArray printTest = new JSONArray();
            // 添加文本打印,正常
            timeTools = new TimerCountTools();
            timeTools.start();
            ServiceManager.getInstence().getPrinter().setPrintFont(FontsType.simsun);
            //set print type
            //ServiceManager.getInstence().getPrinter().setPrintTypesettingType(GlobalDef.CPP_TYPESETTING);
            ServiceManager.getInstence().getPrinter().setPrintTypesettingType(GlobalDef.ANDROID_TYPESETTING);
            //clean print cache.
            ServiceManager.getInstence().getPrinter().cleanCache();
            // ServiceManager.getInstence().getPrinter().setPrintFontByAsserts("songti.ttf");

            //print text content
            TextPrintLine textPrintLine = new TextPrintLine();
            textPrintLine.setType(PrintLine.TEXT);
            textPrintLine.setPosition(TextPrintLine.LEFT);
            textPrintLine.setSize(TextPrintLine.FONT_NORMAL);

            textPrintLine.setContent("------------------------------------------------");
            ServiceManager.getInstence().getPrinter().addPrintLine(textPrintLine);
            textPrintLine.setPosition(PrintLine.CENTER);
            textPrintLine.setContent("SmartPeak Print Test Center");
            ServiceManager.getInstence().getPrinter().addPrintLine(textPrintLine);
            textPrintLine.setPosition(PrintLine.LEFT);
            textPrintLine.setContent("SmartPeak Print Test Left");
            ServiceManager.getInstence().getPrinter().addPrintLine(textPrintLine);
            textPrintLine.setPosition(PrintLine.RIGHT);
            textPrintLine.setContent("SmartPeak Print Test Right");
            ServiceManager.getInstence().getPrinter().addPrintLine(textPrintLine);
            textPrintLine.setPosition(PrintLine.CENTER);
            textPrintLine.setSize(TextPrintLine.FONT_LARGE);
            textPrintLine.setContent("SmartPeak Print Test Center and Large");
            ServiceManager.getInstence().getPrinter().addPrintLine(textPrintLine);
            textPrintLine.setPosition(PrintLine.LEFT);
            textPrintLine.setSize(TextPrintLine.FONT_SMALL);
            textPrintLine.setContent("SmartPeak Print Test Left and Small");
            ServiceManager.getInstence().getPrinter().addPrintLine(textPrintLine);
            textPrintLine.setPosition(PrintLine.RIGHT);
            textPrintLine.setSize(TextPrintLine.FONT_NORMAL);
            textPrintLine.setContent("SmartPeak Print Test Right and Normal");
            ServiceManager.getInstence().getPrinter().addPrintLine(textPrintLine);
            textPrintLine.setPosition(TextPrintLine.LEFT);
            textPrintLine.setSize(TextPrintLine.FONT_NORMAL);
            textPrintLine.setContent("iiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii");
            ServiceManager.getInstence().getPrinter().addPrintLine(textPrintLine);
            textPrintLine.setContent("iiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii");
            ServiceManager.getInstence().getPrinter().addPrintLine(textPrintLine);
            textPrintLine.setContent("iiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii");
            ServiceManager.getInstence().getPrinter().addPrintLine(textPrintLine);
            textPrintLine.setContent("iiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii");
            ServiceManager.getInstence().getPrinter().addPrintLine(textPrintLine);
            textPrintLine.setContent("iiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii");
            ServiceManager.getInstence().getPrinter().addPrintLine(textPrintLine);
            textPrintLine.setContent("mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm");
            ServiceManager.getInstence().getPrinter().addPrintLine(textPrintLine);
            //print image(bitmap) content
            Bitmap qr = BitmapFactory.decodeResource(getResources(), R.drawable.qr);
            BitmapPrintLine bitmapPrintLine = new BitmapPrintLine();
            bitmapPrintLine.setType(PrintLine.BITMAP);
            bitmapPrintLine.setPosition(PrintLine.CENTER);
            bitmapPrintLine.setBitmap(PicUtils.switchColor(qr));
            ServiceManager.getInstence().getPrinter().addPrintLine(bitmapPrintLine);
            ServiceManager.getInstence().getPrinter().beginPrint(printer_callback);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void selectPrinter() {
        LOGD("get printer info....");
        try {
            int printerNum = ServiceManager.getInstence().getPrinter().getPrinterNum();
            if (printerNum > 0) {
                final List<PrinterInfo> infos = ServiceManager.getInstence().getPrinter().getPrinterInfo();
                String[] printerName = new String[infos.size()];
                for (int i = 0; i < infos.size(); i++)
                    printerName[i] = infos.get(i).getName();
                new EnterDialog(PrintTestActivity.this).showListChoseDialog("please chose a printer", printerName,
                        new OnChoseListener() {
                            @Override
                            public void Chose(int i) {
                                LOGD("select " + i + " printer");
                                try {
                                    ServiceManager.getInstence().getPrinter().selectPosPrinter(infos.get(i).getId());
                                    tv_printerinfo.setText(infos.get(i).toNormalString());
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }

        ContentResolver resolver = getContentResolver();
        if (requestCode == REQUEST_CODE_GALLERY) {
            try {
                // 获得图片的uri
                Uri originalUri = data.getData();
                LOGD("get the uri of the picture");
                // 将图片内容解析成字节数组
                mContent = PicUtils.getBytesFromInputStream(resolver.openInputStream(Uri.parse(originalUri.toString())),
                        3500000);
                LOGD("Parse the image into an array of bytes ");
                // Converts a byte array to a bitmap object that can be called
                // by a ImageView (将字节数组转换为ImageView可调用的Bitmap对象)
                bm = PicUtils.getPicFromBytes(mContent, null);
                Log.d(TAG, "Converts an image content resolution to an array of ImageView ");
                if (bm != null) {
                    // he bitmap object is cut, the width of 240, height
                    // according to the geometric proportion zoom
                    // (将bitmap对象进行裁剪，宽度为240,高度按等比比例缩放)
                    bm = PicUtils.zoomImage(bm, 384);
                    bm = PicUtils.switchColor(bm);// 将彩色图片转换成黑白图片
                    // show image on the activity
                    iv_testImage.setImageBitmap(bm);
                } else {
                    Toast.makeText(this, "This picture is not available. Please choose another one. ",
                            Toast.LENGTH_SHORT).show();
                }

            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }

    }

    class PrinterListener implements OnPrinterListener {
        private final String TAG = "Print";

        @Override
        public void onStart() {
            // TODO 打印开始
            // Print start
            LOGD("start print");
        }

        @Override
        public void onFinish() {
            // TODO 打印结束
            // End of the print
            LOGD("pint success");
            timeTools.stop();
            LOGD("time cost：" + timeTools.getProcessTime());
        }

        @Override
        public void onError(int errorCode, String detail) {
            // TODO 打印出错
            // print error
            LOGD("print error" + " errorcode = " + errorCode + " detail = " + detail);
            if (errorCode == PrinterBinder.PRINTER_ERROR_NO_PAPER) {
                Toast.makeText(PrintTestActivity.this, "paper runs out during printing", Toast.LENGTH_SHORT).show();
            }
            if (errorCode == PrinterBinder.PRINTER_ERROR_OVER_HEAT) {
                Toast.makeText(PrintTestActivity.this, "over heat during printing", Toast.LENGTH_SHORT).show();
            }
            if (errorCode == PrinterBinder.PRINTER_ERROR_OTHER) {
                Toast.makeText(PrintTestActivity.this, "other error happen during printing", Toast.LENGTH_SHORT).show();
            }

        }
    }


}
