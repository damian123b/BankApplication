package com.luxoft.bankapp.exceptions;

public class BankException extends Exception{

	private static final long serialVersionUID = -3558819945114504225L;
 
	public BankException(){
		super();
		;
		
	}
	
	public BankException (String message)
	{
		super(message);
	}
}
