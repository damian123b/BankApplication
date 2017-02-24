package com.luxoft.bankapp.exceptions;

import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.Client;

public class NotEnoughFundsException extends BankException {
	
	static final long serialVersionUID = 3;
	private float amountAttempted; 
	Client c;
	Account a;
	
    public float getAmountAttempted() {    
          return amountAttempted;
    }
    
    public NotEnoughFundsException(Client client, Account account, float amount)
    {
    	
    	c   = client;
        a   = account;
        amountAttempted = amount;
    }

     public String toString()
     {
    	 return "You tried to withdraw too large amount of " + amountAttempted + " . Operation failed."+"\n"+
 		  "Maximal amount to withdraw is "+(amountAttempted - a.getOverdraft() - a.getBalance() );
     }
}