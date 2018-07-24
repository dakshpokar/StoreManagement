package com.dakshpokar.storemanager;

import java.io.*;

public class Query implements Serializable{
	
	private static final long serialVersionUID = 1533489866497387180L;
	private String Query;
	private int type;
	
	public Query(String query, int type) {
		Query = query;
		this.type = type;
	}
	public String getQuery() {
		return Query;
	}
	public int getType() {
		return type;
	}
	
}
