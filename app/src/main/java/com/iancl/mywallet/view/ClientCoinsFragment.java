package com.iancl.mywallet.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.iancl.mywallet.activities.DeviseSettingActivity;
import com.iancl.mywallet.activities.Main2Activity;
import com.iancl.mywallet.R;
import com.iancl.mywallet.model.AllCoinsApp;
import com.iancl.mywallet.utility.FileUtility;
import com.iancl.mywallet.utility.MainJSONParser;
import com.iancl.mywallet.utility.URLUtility;

import static com.iancl.mywallet.activities.Main2Activity.walletApp;
import static com.iancl.mywallet.utility.FileUtility.context;

/**
 * Created by ian on 20/01/18.
 */

public class ClientCoinsFragment extends Fragment {

    /**
     * A placeholder fragment containing a simple view.
     */

    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    private static ListView mListView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    public static CoinAdapter adapter;
    private AdView mAdView;

    public ClientCoinsFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static ClientCoinsFragment newInstance() {
        ClientCoinsFragment fragment = new ClientCoinsFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.content_main, container, false);

        //------------------- ADS CODE
        MobileAds.initialize(rootView.getContext(), "ca-app-pub-1301811565917813~3593670476");
        mAdView = rootView.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        mListView = (ListView) rootView.findViewById(R.id.listCoins);
        mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swiperefresh);
        // Header view
        ViewGroup headerView = (ViewGroup)getLayoutInflater().inflate(R.layout.header_list_view, mListView,false);
        mListView.addHeaderView(headerView);

        //----------------- LISTENER

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
                mSwipeRefreshLayout.setRefreshing(false);
            };
        });
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View view, int pos, long id) {

                CoinAdapter.CoinViewHolder  c = (CoinAdapter.CoinViewHolder)(mListView.getChildAt(pos).getTag());
                FileUtility.deleteCurrency(c);

                removeCoin( c, getContext());
                Toast.makeText(getContext(), ""+c.name.getText()+ " "+getResources().getString(R.string.toast_delete), Toast.LENGTH_LONG).show();

                return true;
            }
        });


        loadDataFirstStart();


        /*TextView textView = (TextView) rootView.findViewById(R.id.section_label);
        textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));*/

        return rootView;
    }

    public static void removeCoin(CoinAdapter.CoinViewHolder coin, Context context) {

        walletApp.removeCoinToClient(coin.getAssociateCoin());
        adapter.notifyDataSetChanged();
    }



    public static void loadData() {

        // Get all coins, and from this new list get tickers in app -> Only one fecth
        MainJSONParser.getAllCoins(context, URLUtility.getAllCOins());
        walletApp.setAllcoinsApp(new AllCoinsApp(MainJSONParser.all_coins_list));
        adapter.notifyDataSetChanged();
        AllCoinsFragment.loadData_back();
    }

    public static void loadData_back() {

        // Get all coins, and from this new list get tickers in app -> Only one fecth
        adapter.notifyDataSetChanged();
    }

    private void loadDataFirstStart() {
        DeviseSettingActivity.getNewDeviseSymbol(getActivity().getApplicationContext());
        adapter = new CoinAdapter(getActivity().getApplicationContext(), walletApp.getClientInstance());
        mListView.setAdapter(adapter);
        adapter.notifyDataSetChangedFirstLoad();
    }
}

