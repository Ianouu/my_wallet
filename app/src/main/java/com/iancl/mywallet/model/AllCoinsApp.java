package com.iancl.mywallet.model;

import com.iancl.mywallet.utility.URLUtility;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by ian on 25/01/18.
 */

public class AllCoinsApp implements Serializable {

    private ArrayList<Coin> coins;

    public AllCoinsApp(ArrayList<Coin> coins) {
        this.coins = coins;
    }
    public AllCoinsApp() {
        this.coins = new ArrayList<>();
    }

    public ArrayList<Coin> getCoins() {
        return coins;
    }

    public void setCoins(ArrayList<Coin> coins) {
        this.coins = coins;
    }

    public void refreshData() {
        /*for (Coin c :coins ) {
            URLUtility.fillCoin(c);
        }*/
        coins.clear();
    }
}
