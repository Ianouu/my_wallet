package com.iancl.mywallet.utility;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.iancl.mywallet.R;
import com.iancl.mywallet.model.Coin;
import com.iancl.mywallet.model.WalletApp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static android.content.ContentValues.TAG;
import static com.iancl.mywallet.model.WalletApp.PREF_TIME_REFRESH;


/**
 * Created by iancl on 05/12/2017.
 */

public class URLUtility {

    public static Context context;
    public static String PRICE = "price_";
    public static String ID = "id";
    public static String SYMBOL = "symbol";
    public static String NAME = "price_";
    public static String RANK = "rank";

     /**
     * Currencies informations in the API
     * @return the value of the selected currency
     */
    public static void fillCoin(Coin coin) {
        String id = coin.getNameId();
        String CUR = coin.getName();
        try {
            String api;
            if ( id.equals("")){
                api = APIUtility.getOneTickers(context, CUR);
            } else {
                api = APIUtility.getOneTickers(context, id);
            }

            URL url = new URL(api);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                bufferedReader.close();
                JSONArray jsonResult = new JSONArray(stringBuilder.toString());
                JSONObject tmp = jsonResult.getJSONObject(0);

                coin.setPrice(tmp.getString( PRICE + WalletApp.SYMBOLE_DEVISE.getName().toLowerCase()));
                coin.setNameId(tmp.getString(ID));

                coin.setName(tmp.getString(SYMBOL));
                coin.setFullName(tmp.getString("name"));
                coin.setRang(tmp.getInt("rank"));

                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
                String percentChanged = tmp.getString("percent_change_"+ preferences.getString(PREF_TIME_REFRESH, "1h"));
                coin.setPercentChanged(percentChanged);

            } catch(Exception e) {
                Toast.makeText(context, context.getResources().getString(R.string.connectionError), Toast.LENGTH_LONG).show();
            }
            finally{
                urlConnection.disconnect();
            }
        }
        catch(Exception e) {
            coin.setPrice("0");
        }
    }

    /**
     * Get the list of all the currencies
     * @return JSONObject of all the currencies
     */
    public static JSONArray getAllCOins() {
        JSONArray jsonResult = new JSONArray();
        try {
            String api = APIUtility.getAllTickers(context);

            URL url = new URL(api);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                bufferedReader.close();
                jsonResult = new JSONArray(stringBuilder.toString());

            }
            finally{
                urlConnection.disconnect();
            }
        }
        catch(Exception e) {
            //Toast.makeText(context, "Impossible de se connecter...", Toast.LENGTH_SHORT).show();
        }
        return jsonResult;

    }



    public static void fillDataCoin(Coin coin) {
        Coin tmp = MainJSONParser.getCoinBySymbol(coin.getName());

        try {
            coin.setPrice(tmp.getPrice());
            coin.setNameId(tmp.getNameId());
            coin.setRang(tmp.getRang());
            coin.setPercentChanged(tmp.getPercentChanged());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }




}
