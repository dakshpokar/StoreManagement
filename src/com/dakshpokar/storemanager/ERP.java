package com.dakshpokar.storemanager;

public class ERP {
	public static LoginForm loginForm;
	public static void main(String args[])
	{
		loginForm = new LoginForm();
		loginForm.returnFrame().setVisible(true);
	}

}
