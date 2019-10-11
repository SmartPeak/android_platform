package com.pos.sdkdemo.utils;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.pos.sdkdemo.R;

public class DialogUtil {

    public static void showDialog(Context context,String msg, OnClickListener onClickListener){
        MyDialog dialog = new MyDialog(context);
        dialog.setMsg(msg).setOnClickListener(onClickListener);
        dialog.show();
    }

    public static void showDialog(Context context,String title,String msg, OnClickListener onClickListener){
        MyDialog dialog = new MyDialog(context);
        dialog.setTitle(title).setMsg(msg).setOnClickListener(onClickListener);
        dialog.show();
    }

    public static LoadingDialog showLoadingDialog(Context context,String msg){
        LoadingDialog dialog = new LoadingDialog(context);
        dialog.setMsg(msg);
        dialog.show();
        return dialog;
    }

    private static class MyDialog extends Dialog implements View.OnClickListener {

        private String msg="";
        private String title="";
        private Context context;
        private TextView tvMsg,tvTitle,confirm,cancel;
        private DialogUtil.OnClickListener onClickListener;

        public MyDialog(@NonNull Context context) {
            this(context,R.style.myDialog);
        }

        public MyDialog(@NonNull Context context, int themeResId) {
            super(context, themeResId);
            this.context=context;
            setCanceledOnTouchOutside(false);
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.dialog_confirm_cancel_layout);
            initView();
        }

        private void initView(){
            tvMsg = (TextView) findViewById(R.id.msg);
            tvTitle = (TextView) findViewById(R.id.title);
            confirm = (TextView) findViewById(R.id.confirm);
            cancel = (TextView) findViewById(R.id.cancel);
            confirm.setOnClickListener(this);
            cancel.setOnClickListener(this);
            if(TextUtils.isEmpty(title)){
                tvTitle.setVisibility(View.GONE);
            }
            tvMsg.setText(msg);
            tvTitle.setText(title);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.confirm:
                    if(onClickListener!=null){
                        onClickListener.onConfirm();
                    }
                    break;
                case R.id.cancel:
                    if(onClickListener!=null){
                        onClickListener.onCancel();
                    }
                    break;
                    default:
                        break;
            }
            dismiss();
        }

        public MyDialog setTitle(String title){
            this.title=title;
            return this;
        }

        public MyDialog setMsg(String msg){
            this.msg=msg;
            return this;
        }

        public DialogUtil.OnClickListener getOnClickListener() {
            return onClickListener;
        }

        public MyDialog setOnClickListener(DialogUtil.OnClickListener onClickListener) {
            this.onClickListener = onClickListener;
            return this;
        }
    }


    private static class LoadingDialog extends Dialog{

        private String msg="";
        private Context context;
        private TextView tvMsg;

        public LoadingDialog(@NonNull Context context) {
            this(context,R.style.loading_dialog_style);
        }

        public LoadingDialog(@NonNull Context context, int themeResId) {
            super(context, themeResId);
            this.context=context;
            setCanceledOnTouchOutside(false);
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.dialog_loading_layout);
            initView();
        }

        private void initView(){
            tvMsg = (TextView) findViewById(R.id.msg);
            tvMsg.setText(msg);
        }

        public LoadingDialog setMsg(String msg){
            this.msg=msg;
            return this;
        }
    }



    public static interface OnClickListener{
        public void onConfirm();
        public void onCancel();
    }
}
