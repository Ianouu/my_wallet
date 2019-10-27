package com.iancl.mywallet.utility;

import android.content.Context;

import com.iancl.mywallet.R;
import com.iancl.mywallet.model.WalletApp;


/**
 * Created by iancl on 21/12/2017.
 */

public class APIUtility {


    public static String getAllTickers(Context context) {
        return context.getResources().getString(R.string.API_COINS_LIST) + "?convert="+ WalletApp.SYMBOLE_DEVISE.getName();
    }

    public static String getOneTickers(Context context, String idName) {
        String str_ret = "";
        if ( !idName.isEmpty()) {
            str_ret = context.getResources().getString(R.string.API_COINS_LIST)+ idName + "/?convert="+ WalletApp.SYMBOLE_DEVISE.getName();
        }
        return str_ret;
    }
}
