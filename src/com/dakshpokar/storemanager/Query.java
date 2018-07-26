package com.dakshpokar.storemanager;

import java.io.*;

public class Query implements Serializable{
	
	private static final long serialVersionUID = 1533489866497387180L;
	private String Query;
	private int type;
	private int db;
	
	public Query(String query, int type, int db) {
		Query = query;
		this.type = type;
		this.db = db;
	}
	public String getQuery() {
		return Query;
	}
	public int getType() {
		return type;
	}
	public int getDb() {
		return db;
	}
}
