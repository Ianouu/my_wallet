package com.iancl.mywallet.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.iancl.mywallet.model.AllCoinsApp;
import com.iancl.mywallet.utility.MainJSONParser;
import com.iancl.mywallet.R;
import com.iancl.mywallet.model.Client;
import com.iancl.mywallet.model.Coin;
import com.iancl.mywallet.model.Devise;
import com.iancl.mywallet.model.WalletApp;
import com.iancl.mywallet.utility.FileUtility;
import com.iancl.mywallet.utility.URLUtility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.iancl.mywallet.activities.DeviseSettingActivity.PREF_DEVISE;
import static com.iancl.mywallet.model.WalletApp.PREF_TIME_REFRESH;
import static com.iancl.mywallet.utility.FileUtility.context;

public class SplashScreenActivity extends AppCompatActivity {


    protected ArrayList<Coin> client_coins;
    protected JSONArray all_coins;
    protected ArrayList<String> coinList = new ArrayList<String>();
    private WalletApp walletApp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        /**
         * Showing splashscreen while making network calls to download necessary
         * data before launching the app Will use AsyncTask to make http call
         */
        new PrefetchData().execute();
    }

    /**
     * Async Task to make http call
     */
    private class PrefetchData extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // before making http calls

        }

        @Override
        protected Void doInBackground(Void... arg0) {

            // initialize the context for the url and file utility
            context = getApplicationContext();
            URLUtility.context = getApplicationContext();

            // Instanciate the app with the client and his coins
            Client clt = new Client();
            Devise devise = DeviseSettingActivity.getNewDeviseSymbol(getApplicationContext());



            walletApp = new WalletApp(clt, devise);

            // Load preferences
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplication().getApplicationContext());
            String tmp = preferences.getString(PREF_TIME_REFRESH, "1h");
            walletApp.setTime_to_refresh(tmp);

            // Load the json with all the coins
            all_coins = URLUtility.getAllCOins();
            MainJSONParser.getAllCoins(context, all_coins);
            walletApp.setAllcoinsApp(new AllCoinsApp(MainJSONParser.all_coins_list));
            getCoinsName();

            // Load the data for the clients coins
            client_coins = new ArrayList<>();
            client_coins.addAll(FileUtility.getCurrenciesFile());



            for (Coin c : client_coins ) {
                clt.addCoinToWallet(c);
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);


            Intent i = new Intent(SplashScreenActivity.this, Main2Activity.class);
            Bundle loadInfo = new Bundle();
            loadInfo.putSerializable("walletApp", walletApp);
            loadInfo.putSerializable("coinList", coinList);
            i.putExtras(loadInfo);
            startActivity(i);

            // close this activity
            finish();
        }

        protected void getCoinsName() {
            try {
                JSONObject oTMP = null;
                for (int i = 0; i < all_coins.length(); i++) {
                    oTMP = all_coins.getJSONObject(i);
                    coinList.add((oTMP.get("symbol").toString() + " : " + oTMP.get("name").toString()));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }



    }

}