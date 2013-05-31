package com.example.testt;

/** Klasa Predmet vo koja se cuvaat ime, sifra, krediti i semestar
 *  za eden predmet i soodvetnite get i set metodi */

public class Predmet {
	
	private String ime;
	private String sifra;
	private Double krediti;
	private int semestar;
	
	public void setIme(String ime)
	{
		this.ime=ime;
	}
	public String getIme()
	{
		return this.ime;
	}
	public void setSifra(String sifra)
	{
		this.sifra=sifra;
	}
	public String getSifra()
	{
		return this.sifra;
	}
	public void setKrediti(Double krediti)
	{
		this.krediti=krediti;
	}
	public Double getKrediti()
	{
		return this.krediti;
	}
	public void setSemestar(int semestar)
	{
		this.semestar=semestar;
	}
	public int getSemestar()
	{
		return this.semestar;
	}

}
