package com.pos.sdkdemo.widgets;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.EditText;
import android.widget.ImageView;

import com.pos.sdkdemo.interfaces.OnChoseListener;
import com.pos.sdkdemo.interfaces.OnConfirmListener;
import com.pos.sdkdemo.interfaces.OnEnterListener;

/**
 * Created by liudy on 2017/3/28.
 */

public class EnterDialog {
    private Context context = null;
    private AlertDialog.Builder builder = null;
    private EditText editText = null;
    private ImageView imageView = null;
	int sel = 0;
    public EnterDialog(Context context) {
        this.context = context;
        builder = new AlertDialog.Builder(context);
    }

    /**
     *
     * @param title
     * @param onEnterListener
     */
    public void showEnterDialog(String title, final OnEnterListener onEnterListener)
    {
        editText = new EditText(this.context);
        builder.setTitle(title)
                .setIcon(android.R.drawable.ic_dialog_info)
                .setView(editText)
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        onEnterListener.onEnter(editText.getText().toString());
                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .show();
    }

    /**
     *
     * @param title
     * @param message
     * @param onConfirmListener
     */
    public void showConfirmDialog(String title, String message,final OnConfirmListener onConfirmListener)
    {
        builder.setTitle(title)
                .setIcon(android.R.drawable.ic_dialog_info)
                .setMessage(message)
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        onConfirmListener.OK();
                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        onConfirmListener.Cancel();
                    }
                })
                .show();
    }

    /**
     *
     * @param title
     * @param message
     * @param OkBtnMsg
     * @param CancelBtnMsg
     * @param onConfirmListener
     */
    public void showConfirmDialog(String title, String message,String OkBtnMsg,String CancelBtnMsg,final OnConfirmListener onConfirmListener)
    {
        builder.setTitle(title)
                .setIcon(android.R.drawable.ic_dialog_info)
                .setMessage(message)
                .setPositiveButton(OkBtnMsg, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        onConfirmListener.OK();
                    }
                })
                .setNegativeButton(CancelBtnMsg, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        onConfirmListener.Cancel();
                    }
                })
                .show();
    }

    /**
     *
     * @param title
     * @param list
     * @param onChoseListener
     */
    public void showListChoseDialog(String title, String[] list,final OnChoseListener onChoseListener)
    {
        builder.setTitle(title)
                .setIcon(android.R.drawable.ic_dialog_info)
                .setSingleChoiceItems(list, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        sel = i;
                    }
                })
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        onChoseListener.Chose(sel);
                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .show();
    }

    /**
     *
     * @param title
     * @param list
     */
    public void showListDialog(String title, String[] list)
    {
        builder.setTitle(title)
                .setIcon(android.R.drawable.ic_dialog_info)
                .setItems(list, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .show();
    }

    /**
     *
     * @param title
     * @param imgid
     * @param onConfirmListener
     */
    public void showImgDialog(String title, int imgid,final OnConfirmListener onConfirmListener)
    {
        imageView = new ImageView(this.context);
        imageView.setBackgroundResource(imgid);
        builder.setTitle(title)
                .setIcon(android.R.drawable.ic_dialog_info)
                .setView(imageView)
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        onConfirmListener.OK();
                    }
                })
                .show();
    }

}
