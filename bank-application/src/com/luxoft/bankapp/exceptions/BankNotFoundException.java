package com.luxoft.bankapp.exceptions;

public class BankNotFoundException extends BankException{

	private static final long serialVersionUID = 1L;

	@Override
	public String getMessage() {
		
		return "Sorry friend - such bank not found";
	}
	public BankNotFoundException (String name) {
	
	   	super("Sorry friend - bank "+name+" not found");
	}
  
}
