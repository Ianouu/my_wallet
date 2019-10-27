
package com.iancl.mywallet.model;

import com.iancl.mywallet.utility.NumberUtility;
import com.iancl.mywallet.utility.URLUtility;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by iancl on 05/12/2017.
 */

public class Client implements Serializable {


	private static final int MAX_SIZE_WALLET = 30;
	private double totalSolde;
    private List<Coin> coins = new ArrayList<>();


    public Client() {
    }

    public double getTotalSolde() {
        return totalSolde;
    }

	public String getFormattedTotalSolde() {
        String total ="";
        if ( this.totalSolde > 9999 ) {
            total = NumberUtility.format( this.totalSolde);
        }
        else {
            total = new DecimalFormat("###,###.##").format(Double.parseDouble(totalSolde+"")) ;
        }

        total = total + " " + WalletApp.SYMBOLE_DEVISE.getSymbole();
        return total;
	}

	public void setTotalSolde(double totalSolde) {
        this.totalSolde = totalSolde;
    }
    
    public void addCoinToWallet(Coin coin) {
    	if ( this.coins.size()< MAX_SIZE_WALLET) {
        	this.coins.add(coin);
        	coin.setId( this.coins.size());
        	this.totalSolde += coin.getSoldeValue();
    	}
    }
    
    public void removeCointoWallet(Coin coin) {
    	if ( coin.getId() > 0 ) {
        	System.out.println(coin);

    		Iterator<Coin> i = coins.iterator();
        	Coin tmp = null;
        	while (i.hasNext()) {
        	   tmp = i.next(); // must be called before you can call i.remove()
        	   if ( coin.getId() == tmp.getId()) {
            	   i.remove();
        	   }
        	}

        	coin.setId(-1);
        	//this.totalSolde -= coin.getSoldeValue();
            refreshData();
        }
    }
    
    public void refreshData() {
        totalSolde = 0.0;
        for (Coin c :coins ) {
            URLUtility.fillDataCoin(c);
            totalSolde += c.getSoldeValue();
        }
    }

    public List<Coin> getCoins() {
    	return this.coins;
	}

	@Override
	public String toString() {
		return "Client [totalSolde=" + totalSolde + ", \ncoins=" + coins + "]";
	}
    
    
}
