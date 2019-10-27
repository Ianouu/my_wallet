package com.iancl.mywallet.model;

import java.io.Serializable;

public class Devise implements Serializable {
	
	private String symbole;
	private String name;
	
	public Devise(String name, String symbole) {
		this.symbole = symbole;
		this.name = name;
	}

	public String getSymbole() {
		return symbole;
	}

	public String getName() {
		return name;
	}
	

}
