package com.dakshpokar.storemanager;

import java.io.Serializable;
import java.util.Vector;

public class VectorWrapper implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8570671345639634326L;
	private Vector<Vector<Object>> data;
	private Vector<String> columnNames;
	
	VectorWrapper(Vector<Vector<Object>> data, Vector<String> columnNames){
		this.data = data;
		this.columnNames = columnNames;
	}
	public Vector<String> getColumnNames() {
		return columnNames;
	}
	public Vector<Vector<Object>> getData() {
		return data;
	}
}
