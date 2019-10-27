package com.iancl.mywallet.activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.StrictMode;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerTabStrip;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;

import android.widget.TextView;

import com.iancl.mywallet.R;
import com.iancl.mywallet.model.WalletApp;
import com.iancl.mywallet.view.AllCoinsFragment;
import com.iancl.mywallet.view.ClientCoinsFragment;

import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    public static TextView totalCoins;
    public static WalletApp walletApp;
    public static ArrayList<String> SettingCoinList;
    static final int CHANGED_DEVISE = 1;
    static final int ADDED_DEVISE = 2;

    static final int ID_CLIENT_COINS = 0;
    static final int ID_ALL_COINS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("");

        Bundle loadInfo = getIntent().getExtras();
        if ( walletApp == null) {
            walletApp = (WalletApp) loadInfo.getSerializable("walletApp");
        }
        if ( SettingCoinList == null) {
            SettingCoinList = (ArrayList<String>)  loadInfo.getSerializable("coinList");
        }


        totalCoins = (TextView) findViewById(R.id.toolbar_title);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Style of the tabs
        TabLayout tabs = (TabLayout) findViewById(R.id.layout_tabs);
        mViewPager = (ViewPager) findViewById(R.id.container);
        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager.setAdapter(adapter);
        tabs.setupWithViewPager(mViewPager);
        tabs.getTabAt(0).setIcon(R.drawable.ic_all_coins);
        tabs.getTabAt(1).setIcon(R.drawable.ic_client_coins);
        mViewPager.setCurrentItem(1);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, AddingCoinActivity.class);
            Bundle loadInfo = new Bundle();
            loadInfo.putSerializable("allCoins", SettingCoinList);
            intent.putExtras(loadInfo);

            startActivityForResult(intent, ADDED_DEVISE);

            return true;
        } else if (id == R.id.action_changeDevise) {
            Intent intent = new Intent(this, DeviseSettingActivity.class);
            startActivityForResult(intent, CHANGED_DEVISE);
            return true;
               } else if (id == R.id.action_show_help) {

            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setMessage(getResources().getString(R.string.help_text))
                    .setTitle(getResources().getString(R.string.help_title))
                    .setIcon(R.drawable.ic_info_black_24dp);
            dialog.show();

            return true;
        } else if (id == R.id.action_show_legal) {
            Intent intent = new Intent(this, AboutActivity.class);
            startActivity(intent);

           /* Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.devise_dialog);

            TextView text = (TextView) dialog.findViewById(R.id.textView_text);
            text.setText(Html.fromHtml(getResources().getString(R.string.legal_text)));


            dialog.show();*/

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == CHANGED_DEVISE) {
            mSectionsPagerAdapter.refreshAll();
        } else if (requestCode == ADDED_DEVISE) {
            mSectionsPagerAdapter.refreshAll();

        }
    }



    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public List<Fragment> fragmentList;

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
            fragmentList = new ArrayList<>();
        }

        @Override
        public Fragment getItem(int position) {
            Fragment c = null;

            switch (position) {
                case 0:
                    c = AllCoinsFragment.newInstance();
                    fragmentList.add(c);
                    break;
                case 1:
                    c =ClientCoinsFragment.newInstance();
                    fragmentList.add(c);
                    break;
            }
            return c;
        }

        public void refreshAll() {
            ((AllCoinsFragment)getItem(0)).loadData();
        }


        @Override
        public int getCount() {
            // Show 3 total pages.
            return 2;
        }
    }
}
