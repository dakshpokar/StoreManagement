package com.dakshpokar.storemanager;
import java.io.Serializable;
import java.sql.ResultSet;
import java.util.Vector;

public class SerializedVector implements Serializable{
	private Vector<Vector<Object>> x;
	private static final long serialVersionUID = -8071914112278139481L;
	public Vector<Vector<Object>> getVector() {
		return x;
	}
	public SerializedVector(Vector<Vector<Object>> x){
		this.x = x;
	}
}
