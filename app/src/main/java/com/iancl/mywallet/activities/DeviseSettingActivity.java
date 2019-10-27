package com.iancl.mywallet.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.iancl.mywallet.R;
import com.iancl.mywallet.model.Devise;
import com.iancl.mywallet.model.PERCENT_CHANGED;
import com.iancl.mywallet.model.WalletApp;


public class DeviseSettingActivity extends AppCompatActivity {


    private RadioButton button_1H;
    private RadioButton button_24H;
    private RadioButton button_7D;
    private RadioButton button_NA;
    private RadioGroup radio_group;

    private ListView listView;
    private DeviseSettingActivity my;
    public static final String PREF_DEVISE = "DEVISE_PREF";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_devise_setting);
        my = this;
        this.listView = (ListView) findViewById(R.id.listView_devise);
        this.button_1H = (RadioButton) findViewById(R.id.radioButton_1H);
        this.button_24H = (RadioButton) findViewById(R.id.radioButton_24H);
        this.button_7D = (RadioButton) findViewById(R.id.radioButton_7D);
        this.button_NA = (RadioButton) findViewById(R.id.radioButton_NA);



        switch (Main2Activity.walletApp.getTime_to_refresh()) {
            case PERCENT_CHANGED.Hours_1:
                this.button_1H.setChecked(true);
                break;
            case PERCENT_CHANGED.Hours_24:
                this.button_24H.setChecked(true);
                break;
            case PERCENT_CHANGED.Days_7:
                this.button_7D.setChecked(true);
                break;
            case PERCENT_CHANGED.NA:
                this.button_NA.setChecked(true);
                break;
        }
    //TODO : Set a different color for the devise selected
    //TODO :  Add a back flèche

        this.radio_group = (RadioGroup) findViewById(R.id.radioGroup_refresh);
        this.radio_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {

                    case R.id.radioButton_7D:
                        setRefreshTime (getApplicationContext(),PERCENT_CHANGED.Days_7);
                        break;
                    case R.id.radioButton_24H:
                        setRefreshTime (getApplicationContext(),PERCENT_CHANGED.Hours_24);
                        break;
                    case R.id.radioButton_1H:
                        setRefreshTime (getApplicationContext(), PERCENT_CHANGED.Hours_1);
                        break;
                    case R.id.radioButton_NA:
                        setRefreshTime (getApplicationContext(), PERCENT_CHANGED.NA);
                        break;
                }
                my.finish();
            }
        });


        this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected devise
                String selectedFromList =(String) (listView.getItemAtPosition(position));

                // Write in the pref, the new value
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString(PREF_DEVISE, selectedFromList);
                editor.commit();

                Main2Activity.walletApp.setDevise(getNewDeviseSymbol(getApplicationContext()));

                Toast.makeText(DeviseSettingActivity.this, getResources().getString(R.string.devise_changed) + " " +selectedFromList, Toast.LENGTH_SHORT).show();
                my.finish();

            }
        });
    }


    public  static Devise getNewDeviseSymbol(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        String tmp_deviseName = preferences.getString(PREF_DEVISE, "EUR");

        if (tmp_deviseName.equals("USD")) {
            return new Devise("USD", "$");
        } else if ( tmp_deviseName.equals("EUR")) {
            return new Devise("EUR","€");
        } else if ( tmp_deviseName.equals("GPB")) {
            return new Devise("GPB", "£");
        } else if ( tmp_deviseName.equals("INR")) {
            return new Devise("INR", "₹");
        } else if (tmp_deviseName.equals("CNY")) {
            return new Devise("CNY", "¥");
        }  else if ( tmp_deviseName.equals("RUB")) {
            return new Devise("RUB", "\u20BD");
        } else {
            return new Devise("USD", "$");
        }
    }

    public  static void setRefreshTime(Context context, String value) {
        Main2Activity.walletApp.setTime_to_refresh(value);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(Main2Activity.walletApp.PREF_TIME_REFRESH, value);
        editor.apply();


    }
}
