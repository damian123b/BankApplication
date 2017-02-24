package com.luxoft.bankapp.exceptions;

public class ClientExistsException extends BankException {
   
	private static final long serialVersionUID = 5022848483534836065L;

	@Override
    public String getMessage() {
    	return "Client with such name already exists";
    }
}
