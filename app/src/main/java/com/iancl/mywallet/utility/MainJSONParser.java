package com.iancl.mywallet.utility;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.iancl.mywallet.model.Coin;
import com.iancl.mywallet.model.WalletApp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static com.iancl.mywallet.model.WalletApp.PREF_TIME_REFRESH;

/**
 * Created by iancl on 21/12/2017.
 */

public class MainJSONParser {

    public static JSONArray all_coins = new JSONArray();
    public static ArrayList<Coin> all_coins_list = new ArrayList();


    public static JSONObject getCoin(String symbol) {
        JSONObject o = new JSONObject();
        JSONObject tmp;
        try {
            int i = 0;
            while (i < all_coins.length()) {
                tmp = all_coins.getJSONObject(i);
                if (tmp.get("symbol").equals(symbol)) {
                    o = tmp;
                    i = all_coins.length() + 1;
                }
                i++;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return o;
    }

    public static Coin getCoinBySymbol(String symbol) {
        Coin o = null;
        Coin tmp;
        try {
            int i = 0;
            while (i < all_coins_list.size()) {
                tmp = all_coins_list.get(i);
                if (tmp.getName().equals(symbol)) {
                    o = tmp;
                    i = all_coins_list.size() + 1;
                }
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return o;
    }

    public static String getCoinId(String symbol) {
        /*String o = "";
        JSONObject tmp = null;
        if ( all_coins.length() < 1) {
            all_coins = URLUtility.getAllCOins();
        }
        try {
            int i = 0;
            while (i < all_coins.length()) {
                tmp = all_coins.getJSONObject(i);
                if (tmp.get("symbol").equals(symbol)) {
                    o = tmp.getString("id").toString();
                    i = all_coins.length() + 1;
                }
                i++;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }*/
        String o = "";
        int i = 0;
        Coin tmp;
        while (i < all_coins_list.size()) {
            tmp = all_coins_list.get(i);
            if (tmp.getName().equals(symbol)  ) {
                o = tmp.getId() +"";
                i = all_coins_list.size() + 1;
            }
            i++;
        }
        return o;
    }



    /*public static ArrayList<Coin> getAllCoins(Context context) {
        ArrayList<Coin> list = new ArrayList<>();
        JSONObject tmp = null;
        if ( all_coins.length() < 1) {
            all_coins = URLUtility.getAllCOins();
        }
        try {
            int i = 0;
            while (i < all_coins.length()) {
                tmp = all_coins.getJSONObject(i);
                Coin c = new Coin();
                c.setName(tmp.getString("symbol"));
                c.setPrice(tmp.getString("price_"+ WalletApp.SYMBOLE_DEVISE.getName().toLowerCase()));
                c.setNameId(tmp.getString("id"));
                c.setFullName(tmp.getString("name"));
                c.setRang(tmp.getInt("rank"));
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
                String percentChanged = tmp.getString("percent_change_"+ preferences.getString(PREF_TIME_REFRESH, "1H"));
                c.setPercentChanged(percentChanged);
                list.add(c);
                i++;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }*/


    public static ArrayList<Coin> getAllCoins(Context context, JSONArray all_coins_param) {
        ArrayList<Coin> list = new ArrayList<>();
        JSONObject tmp = null;
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String time_change = "percent_change_" + preferences.getString(PREF_TIME_REFRESH, "1h");
        try {
            int i = 0;
            while (i < all_coins_param.length()) {
                tmp = all_coins_param.getJSONObject(i);
                Coin c = new Coin();
                c.setName(tmp.getString("symbol"));
                c.setPrice(tmp.getString("price_"+ WalletApp.SYMBOLE_DEVISE.getName().toLowerCase()));
                c.setNameId(tmp.getString("id"));
                c.setFullName(tmp.getString("name"));
                c.setRang(tmp.getInt("rank"));
                String percentChanged = tmp.getString( time_change);
                c.setPercentChanged(percentChanged);
                c.setId(i);
                list.add(c);
                i++;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        all_coins_list = list;
        return list;
    }
}
