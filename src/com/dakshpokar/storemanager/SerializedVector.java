package com.dakshpokar.storemanager;
import java.io.Serializable;
import java.sql.ResultSet;
import java.util.Vector;

public class SerializedVector implements Serializable{
	private VectorWrapper x;
	private static final long serialVersionUID = -8071914112278139481L;
	public VectorWrapper getVector() {
		return x;
	}
	public SerializedVector(VectorWrapper x){
		this.x = x;
	}
}
