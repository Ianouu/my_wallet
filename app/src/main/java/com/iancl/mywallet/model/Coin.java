package com.iancl.mywallet.model;

import com.iancl.mywallet.utility.NumberUtility;

import java.io.Serializable;
import java.text.DecimalFormat;


/**
 * Created by iancl on 02/12/2017.
 */

public class Coin implements Serializable{
    public static String BASE_URL;

    private String name;
    private String fullName;
    private String price;
    private String amount;
    private Double sold;
    private String imagePath;
    private String nameId;
    private String percentChanged;
    private int rang;
    
    private int id = -1;

    public Coin() {
        this.nameId ="";
    }

    public String getFormattedPrice() {
		return new DecimalFormat("###,###.##").format(Double.parseDouble(this.getPrice())) + " " + WalletApp.SYMBOLE_DEVISE.getSymbole();
    }
    
    public String getFormattedSold() {
    	String formatedSold ="";
    	if (getSoldeValue() > 9999 ) {
    		formatedSold = NumberUtility.format(getSoldeValue());
    	} else {
    		formatedSold = new DecimalFormat("###,###.##").format(getSoldeValue());   
    	}
    	return formatedSold  + " " + WalletApp.SYMBOLE_DEVISE.getSymbole();
    }
    
    

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.imagePath = "ic_"+name.toLowerCase();
        this.name = name;
    }

    public String getPrice() {
        if ( this.price == null) {
            return "0";
        } else {
            return price;
        }
    }

    public void setPrice(String price) {
        this.price = price;
        SoldeValueChanged();
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
        SoldeValueChanged();
    }

    public void setSold(Double sold) {
        this.sold = sold;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Double getSoldeValue() {
    	if ( this.sold == null ){
    		this.sold = (Double.parseDouble(getAmount()) * Double.parseDouble(getPrice()));
    	}
    	return this.sold;
    }

    public void SoldeValueChanged(){
    	if ( this.sold != null && this.price != "") {
    		this.sold = (Double.parseDouble(getAmount()) * Double.parseDouble(getPrice()));
    	}
    }
    
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

    public String getNameId() {
        return nameId;
    }

    public void setNameId(String nameId) {
        this.nameId = nameId;
    }

    @Override
	public String toString() {
		return "Coin [name=" + name +  "]";
	}

    public String getPercentChanged() {
        return percentChanged;
    }
    public boolean isSoldUp() {
        if ( this.percentChanged == null) {
            return true;
        } else {
            if ( Double.parseDouble(this.percentChanged) > 0) {
                return true;
            } else {
                return false;
            }
        }

    }

    public void setPercentChanged(String percentChanged) {
        if ( percentChanged == null) {
            this.percentChanged = "0";
        } else {
            this.percentChanged = percentChanged;
        }
    }

    public int getRang() {
        return rang;
    }

    public void setRang(int rang) {
        this.rang = rang;
    }
}
