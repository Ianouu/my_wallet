package com.iancl.mywallet.activities;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.iancl.mywallet.R;
import com.iancl.mywallet.model.Coin;
import com.iancl.mywallet.model.WalletApp;
import com.iancl.mywallet.utility.FileUtility;
import com.iancl.mywallet.utility.URLUtility;

import java.util.ArrayList;

public class AddingCoinActivity extends AppCompatActivity {

    private ArrayList<String> data = new ArrayList<>();
    private ArrayAdapter<String> adapterSetting;
    private AutoCompleteTextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        textView = (AutoCompleteTextView) findViewById(R.id.editText_currency_currency);


        Bundle loadInfo = getIntent().getExtras();
        data = (ArrayList) loadInfo.getSerializable("allCoins");
        //data = Main2Activity.SettingCoinList;
        if (data != null && data.size()>0) {
            adapterSetting = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, data);
            textView.setAdapter(adapterSetting);
        }
    }




    public void addCurrency(View view) {
        Button button = (Button) findViewById(R.id.button_addCurrency);
        button.setEnabled(false);

        TextView textView = (AutoCompleteTextView) findViewById(R.id.editText_currency_currency);
        TextView textViewAM = (EditText) findViewById(R.id.editText2_amount);

        String tmp[] = textView.getText().toString().split(" : ");
        String name =  tmp[0].toUpperCase().trim();
        String amount =  textViewAM.getText().toString();

        if ( amount.equals("")) {
            amount = "0";
        }
        String strTowrite = name + ":" + amount;
        FileUtility.addToFileData(strTowrite, MODE_APPEND, this.getResources().getString(R.string.FILE_DATA_CURRENCY));


        Coin c = new Coin();
        c.setName(name);
        /*URLUtility.fillDataCoin(c);*/
        c.setAmount(amount);

        Main2Activity.walletApp.addCoinToClient(c);
        Toast.makeText(this, this.getResources().getString(R.string.toast_add),
                Toast.LENGTH_SHORT).show();
        this.finish();


    }






}
