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
import com.iancl.mywallet.R;
import com.iancl.mywallet.activities.Main2Activity;
import com.iancl.mywallet.model.AllCoinsApp;
import com.iancl.mywallet.model.Coin;
import com.iancl.mywallet.utility.FileUtility;
import com.iancl.mywallet.utility.MainJSONParser;
import com.iancl.mywallet.utility.URLUtility;

import org.json.JSONArray;

import java.util.Collection;

import static com.iancl.mywallet.activities.Main2Activity.walletApp;
import static com.iancl.mywallet.utility.FileUtility.context;

/**
 * Created by ian on 20/01/18.
 */

public class AllCoinsFragment extends Fragment {

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
    public static AllCoinAdapter adapter;
    private AdView mAdView;

    public AllCoinsFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static AllCoinsFragment newInstance() {
        AllCoinsFragment fragment = new AllCoinsFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.content_main_all_coins, container, false);

        //------------------- ADS CODE
        MobileAds.initialize(rootView.getContext(), "ca-app-pub-1301811565917813~3593670476");
        mAdView = rootView.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        mListView = (ListView) rootView.findViewById(R.id.list_all_Coins);
        mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swiperefresh);
/*        mSwipeRefreshLayout.setRefreshing(false);
        mSwipeRefreshLayout.setEnabled(false);*/
        // Header view
        ViewGroup headerView = (ViewGroup)getLayoutInflater().inflate(R.layout.header_list_view_all_coins, mListView,false);
        mListView.addHeaderView(headerView);
        //----------------- LISTENER

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
                mSwipeRefreshLayout.setRefreshing(false);
            };
        });

        loadDataFirstStart();




        return rootView;
    }

    public static void removeCoin(AllCoinAdapter.CoinViewHolder coin, Context context) {

        walletApp.removeCoinToClient(coin.getAssociateCoin());
        adapter.notifyDataSetChanged();
    }



    public void loadData() {
        // Get all coins, and from this new list get tickers in app -> Only one fecth
        MainJSONParser.getAllCoins(context, URLUtility.getAllCOins());
        walletApp.setAllcoinsApp(new AllCoinsApp(MainJSONParser.all_coins_list));
        refreshAdapter(context, mListView);

        adapter.notifyDataSetChanged();
        ClientCoinsFragment.loadData_back();

    }

    public static void loadData_back() {
        // Get all coins, and from this new list get tickers in app -> Only one fecth
        if ( MainJSONParser.all_coins_list.size() > 0) {
            refreshAdapter(context, mListView);
            adapter.notifyDataSetChanged();
        } else {
            MainJSONParser.getAllCoins(context, URLUtility.getAllCOins());
            refreshAdapter(context, mListView);
            adapter.notifyDataSetChanged();
        }

    }

    private void loadDataFirstStart() {

        adapter = new AllCoinAdapter(getActivity().getApplicationContext(), walletApp.getAllcoinsApp());
        mListView.setAdapter(adapter);
    }

    private static void refreshAdapter(Context context, ListView mListView) {
        if ( MainJSONParser.all_coins_list.size() > 0) {
            if ( adapter.getCount() <= 0) {
                adapter = new AllCoinAdapter(context, walletApp.getAllcoinsApp());
                mListView.setAdapter(adapter);

            } else {
                for (int i=0;i<adapter.getCount();i++){
                    Coin c = adapter.getItem(i);
                    c.setPrice(MainJSONParser.all_coins_list.get(i).getPrice());
                    c.setPercentChanged(MainJSONParser.all_coins_list.get(i).getPercentChanged());
                    c.setRang(MainJSONParser.all_coins_list.get(i).getRang());
                    c.setFullName(MainJSONParser.all_coins_list.get(i).getFullName());
                    c.setNameId(MainJSONParser.all_coins_list.get(i).getNameId());
                    c.setId(MainJSONParser.all_coins_list.get(i).getId());
                    c.setName(MainJSONParser.all_coins_list.get(i).getName());
                }
            }

        }

    }
}