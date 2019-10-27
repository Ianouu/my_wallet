package com.iancl.mywallet.activities;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.iancl.mywallet.R;

import org.w3c.dom.Text;

public class AboutActivity extends AppCompatActivity {

    private Button button_emailMe;
    private Button button_rateMe;
    private TextView versionView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        versionView = (TextView) findViewById(R.id.textView_version);
        try {
            PackageInfo pInfo = this.getPackageManager().getPackageInfo(getPackageName(), 0);
            versionView.setText(""+ pInfo.versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        TextView text = (TextView) findViewById(R.id.text_about);
        text.setText(Html.fromHtml(getResources().getString(R.string.about_text)));

        TextView text_legal = (TextView) findViewById(R.id.legal_text);
        text_legal.setText(Html.fromHtml(getResources().getString(R.string.legal_text)));

        button_emailMe = (Button) findViewById(R.id.button_emailMe);
        button_rateMe = (Button) findViewById(R.id.button_rateApp);

        button_rateMe.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Uri uri = Uri.parse("market://details?id=" + getApplicationContext().getPackageName());
                Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                // To count with Play market backstack, After pressing back button,
                // to taken back to our application, we need to add following flags to intent.
                goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                        Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                        Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                try {
                    startActivity(goToMarket);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName())));
                }
            }
        });

        button_emailMe.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("*/*");
                intent.putExtra(Intent.EXTRA_EMAIL, "clementian067@gmail.com");
                intent.putExtra(Intent.EXTRA_SUBJECT, "CryptoWallet Idea !");
                intent.putExtra(Intent.EXTRA_STREAM, "Hi Ian, I have something to tell you.");
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });

    }
}
