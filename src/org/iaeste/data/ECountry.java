/******************************************
 * AUTHOR: Martyn Ellison
 * LAST UPDATE: 16/12/2011
 ******************************************/
package org.iaeste.data;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum ECountry {
	BELGIUM ("Belgium"),
	DENMARK ("Denmark"),
	FINLAND ("Finland"),
	FRANCE ("France"),
	GERMANY ("Germany"),
	PORTUGAL("Portugal"),
	ICELAND ("Iceland"),
	NETHERLANDS("Netherlands"),
	POLAND("Poland"),
	IRELAND ("Ireland"),
	ITALY ("Italy"),
	NORWAY ("Norway"),
	SPAIN ("Spain"),
	SWEEDEN ("Sweden"),
	OTHER ("Other"),
	SWITZERLAND ("Switzerland"),
	UK_EN ("UK - England"),
	UK_NI ("UK - Northern Ireland"),
	UK_SC ("UK - Scotland"),
	UK_W("UK - Wales");
	
	private final String name;
	private static final Map<String,ECountry> lookup = new HashMap<String,ECountry> ();
	
	static {
		for(ECountry s : EnumSet.allOf(ECountry.class))
			lookup.put(s.getName(), s );
	}

	ECountry (String name) {
        this.name = name;

    }
	
	public String getName() {
		return name;
	}
	
	public static ECountry getFromName(String name)
	{
	    return lookup.get(name);
	}
}
