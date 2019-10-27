package com.iancl.mywallet.model;

import android.os.Parcelable;

import com.iancl.mywallet.utility.FileUtility;
import com.iancl.mywallet.utility.NumberUtility;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;


public class WalletApp implements Serializable{
	
	private Client client;
	private Devise devise;
	public static transient Devise SYMBOLE_DEVISE = new Devise("USD", "$");
    private String time_to_refresh = "1h";
	public static transient String PREF_TIME_REFRESH = "PREF_REFRESH_T";
    private AllCoinsApp allcoinsApp;


	
	public WalletApp() {
		super();
		this.client = getClientInstance();
		this.allcoinsApp = new AllCoinsApp();
	}
	
	public WalletApp(Client client, Devise devise) {
		super();
		this.client = client;
		this.setDevise(devise);
		this.allcoinsApp = new AllCoinsApp();
	}

	public Client getClientInstance() {
		Client retour = null;
		if ( this.client == null) {
			retour = new Client();
		} else {
			retour = this.client;
		}
		return retour;
	}

	public Devise getDevise() {
		return devise;
	}
	
	public void setDevise(Devise devise) {
		this.devise = devise;
		SYMBOLE_DEVISE = this.devise;
	}

	public String getFormatedTotalClient() {
		String total ="";
		if ( this.client.getTotalSolde() > 9999 ) {
			total = NumberUtility.format( this.client.getTotalSolde()) + " " + this.devise.getSymbole();
		}
		else {
			total = this.client.getFormattedTotalSolde();
		}

		return total;
	}
	
	public Double getTotalClient() {
		return this.client.getTotalSolde();
	}
	
	public void removeCoinToClient(Coin coin) {
		this.client.removeCointoWallet(coin);
	}
	
	public void addCoinToClient(Coin coin) {
		this.client.addCoinToWallet(coin);
	}
	
	
	// Remplace le notifyDataSetChanged, de coinAdapter
	public void loadData() {
		List<Coin> fileCoins = FileUtility.getCurrenciesFile();
		for (Coin c : fileCoins) {
			addCoinToClient( c);
		}
	}
	
	public void refreshData() {

		this.client.refreshData();

	}

	public void refreshAllData() {

		this.allcoinsApp.refreshData();


	}

	public String getTime_to_refresh() {
		return time_to_refresh;
	}

	public void setTime_to_refresh(String time_to_refresh) {
		this.time_to_refresh = time_to_refresh;
	}

	public AllCoinsApp getAllcoinsApp() {
		return allcoinsApp;
	}

	public void setAllcoinsApp(AllCoinsApp allcoinsApp) {
		this.allcoinsApp = allcoinsApp;
	}
}
