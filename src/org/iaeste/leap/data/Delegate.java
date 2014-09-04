/******************************************
 * AUTHOR: Martyn Ellison
 * LAST UPDATE: 16/12/2011
 ******************************************/
package org.iaeste.leap.data;

public class Delegate {
	
	private String fullName;
	private String lc;
	private ECountry country;
	
	public Delegate (String fullName, String lc,ECountry country)
	{
		this.fullName = fullName;
		this.lc = lc;
		this.country = country;
	}
	
	public Delegate() {
		this.fullName = "";
		this.lc = "";
		this.country = null;
	}

	public ECountry getCountry() {
		return country;
	}
	
	public void setCountry(ECountry country) {
		this.country = country;
	}
	
	public String getLc() {
		return lc;
	}
	
	public void setLc(String lc) {
		this.lc = lc;
	}
	
	public String getFullName() {
		return fullName;
	}
	
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	

}
