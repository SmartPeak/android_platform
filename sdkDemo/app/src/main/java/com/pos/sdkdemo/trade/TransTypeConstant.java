package com.pos.sdkdemo.trade;

import android.content.Context;

import com.pos.sdkdemo.R;

public class TransTypeConstant {
    //消费
    public final static int ACTION_SALE = 1;
    //撤销
    public final static int ACTION_VOID = 2;
    //退货
    public final static int ACTION_REFUND = 3;
    //预授权
    public final static int ACTION_AUTH = 4;
    //预授权撤销
    public final static int ACTION_CANCEL = 5;
    //预授权完成通知
    public final static int ACTION_AUTH_SETTLEMENT = 6;
    //预授权完成请求
    public final static int ACTION_AUTH_COMPLETE = 7;
    //预授权完成撤销
    public final static int ACTION_COMPLETE_VOID = 8;
    //查询余额
    public final static int ACTION_QUERY_BALANCE = 15;
    //打印
    public final static int ACTION_PRINT_LAST = 16;
    //重打印任一笔
    public final static int ACTION_PRINT_RANDOM = 17;
    //打印交易明细
    public final static int ACTION_PRINT_DETAIL = 18;
    //打印交易汇总
    public final static int ACTION_PRINT_SUMMARY = 19;
    //打印结算单
    public final static int ACTION_PRINT_SETTLEMENT = 20;

    //管理
    public final static int MANAGER = 100;
    public final static int MENU_AUTH = 200;//预授权菜单
    public final static int SING_IN = 300;
    public final static int SETTLE = 400;
    public final static int MOUDLE = 500;
    public final static int NET_SETTING = 600;
    public static final int MERCHANT_CONFIG=700;


    public static boolean isSaveDB(int type) {
        boolean save = true;
        switch (type) {
            case ACTION_SALE:
            case ACTION_VOID:
            case ACTION_REFUND:
            case ACTION_AUTH:
            case ACTION_CANCEL:
            case ACTION_AUTH_SETTLEMENT:
            case ACTION_AUTH_COMPLETE:
            case ACTION_COMPLETE_VOID:
                save = true;
                break;
            default:
                save = false;

        }

        return save;
    }

    public static String getStatusByTransType(int transType) {
        String status = "";
        switch (transType) {
            case ACTION_VOID:
            case ACTION_REFUND:
            case ACTION_CANCEL:
            case ACTION_COMPLETE_VOID:
                status = "-";
                break;
            case ACTION_SALE:
            case ACTION_AUTH:
            case ACTION_AUTH_COMPLETE:
                status = "+";
                break;
        }
        return status;
    }

    public static String getSettleTypeByTransType(int transType) {
        String settleType = "";
        switch (transType) {
            case ACTION_VOID:
            case ACTION_REFUND:
            case ACTION_COMPLETE_VOID:
                settleType = "2";
                break;
            case ACTION_SALE:
            case ACTION_AUTH_COMPLETE:
                settleType = "1";
                break;
        }
        return settleType;
    }

    public static String getNameByTransType(Context context,int transType) {
        String name = "";
        switch (transType) {
            case ACTION_SALE://消费
                name = context.getString(R.string.menu_sale);
                break;
            case ACTION_VOID://撤销
                name = context.getString(R.string.menu_void);
                break;
            case ACTION_REFUND://退货
                name = "REFUND";
                break;
            case ACTION_AUTH://预授权
                name = context.getString(R.string.menu_auth);
                break;
            case ACTION_CANCEL://预授权撤销
                name = context.getString(R.string.menu_auth_cancel);
                break;
            case ACTION_AUTH_SETTLEMENT://预授权完成(通知)
                name = "AUTH SETTLEMENT";
                break;
            case ACTION_AUTH_COMPLETE://预授权完成(请求)
                name = context.getString(R.string.menu_auth_complete);
                break;
            case ACTION_COMPLETE_VOID://预授权完成撤销
                name = context.getString(R.string.menu_auth_complete_void);
                break;
            case ACTION_QUERY_BALANCE://查询余额
                name = "QUERY BALANCE";
                break;
            case ACTION_PRINT_LAST://重打印最后一笔
                name = "PRINT LAST";
                break;
            case ACTION_PRINT_RANDOM://重打印任意一笔
                name = "PRINT RANDOM";
                break;
            case ACTION_PRINT_DETAIL://打印交易明细
                name = "PRINT DETAIL";
                break;
            case ACTION_PRINT_SUMMARY://打印交易汇总
                name = "PRINT SUMMARY";
                break;
            case ACTION_PRINT_SETTLEMENT://重打印结算单
                name = "PRINT SETTLEMENT";
                break;
            case MANAGER://管理
                name = context.getString(R.string.menu_manage);
                break;
            case SING_IN://管理
                name = context.getString(R.string.menu_sign_in);
                break;
            case SETTLE://管理
                name = context.getString(R.string.menu_settle);
                break;
            case MOUDLE://管理
                name = context.getString(R.string.menu_reference);
                break;
            case NET_SETTING://管理
                name = context.getString(R.string.menu_net_setting);
                break;
            case MERCHANT_CONFIG://管理
                name = context.getString(R.string.menu_merchant_config);
                break;
            default:
                name = "";
                break;
        }
        return name;
    }

    /**
     * 是撤销交易
     * 如果其他的撤销交易，添加到int【】 即可
     *
     * @param action
     * @return
     */
    public static boolean hasOLDTRACE(int action) {
        int[] ints = {ACTION_VOID, ACTION_COMPLETE_VOID};
        return compare(action, ints);
    }

    /**
     * 是退货交易
     * 如果其他的撤销交易，添加到int【】 即可
     *
     * @param action
     * @return
     */
    public static boolean isREFUND(int action) {
        int[] ints = {ACTION_REFUND};
        return compare(action, ints);
    }

    /**
     * 是否有原授权码
     * 如果其他的撤销交易，添加到int【】 即可
     *
     * @param action
     * @return
     */
    public static boolean hasOLDAUTHCODE(int action) {
        int[] ints = {
                ACTION_AUTH_COMPLETE,
                ACTION_COMPLETE_VOID,
                ACTION_AUTH_SETTLEMENT,
                ACTION_CANCEL,
        };
        return compare(action, ints);
    }

    /**
     * 比较 action是否在匹配的数组里面
     *
     * @param action
     * @param actions
     * @return
     */
    public static boolean compare(int action, int[] actions) {
        if (actions == null || actions.length <= 0) {
            return false;
        }
        for (int i = 0; i < actions.length; i++) {
            if (action == actions[i]) {
                return true;
            }
        }
        return false;
    }
}
