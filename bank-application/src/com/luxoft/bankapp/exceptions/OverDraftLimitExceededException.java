package com.luxoft.bankapp.exceptions;

import com.luxoft.bankapp.model.*;
import com.luxoft.bankapp.service.*;

public class OverDraftLimitExceededException extends NotEnoughFundsException {
 
	static final long serialVersionUID = 1;                              
	
	private Client client;
	private Account account;
	//private float maximumAllowedAmount;
	private float amountAttempted;
	public String errorMessage;
	public Client getClient() {
		return client;
	}
	public Account getAccount() {
		return account;
	}
	
	public float getAmountAttempted() {
		return amountAttempted;
	}
	
	public OverDraftLimitExceededException(float amount,Client client, CheckingAccount checkingAccount)   //TO-DO   full names !!!
	{
		super(client,checkingAccount,amount);
		amountAttempted = amount;
		this.client = client;
		account=checkingAccount;
		
		System.out.println("You tried to withdraw too large amount = "+amount);
	}
	
	@Override
	public String getMessage()   //co to robi i po co
	{
		errorMessage = "Client "+" tried to withdraw too large amount = "+amountAttempted+" from account "+account;
		float temp = amountAttempted - account.getOverdraft() - account.getBalance();
		errorMessage += "Maximum allowed amount is " +temp;
		return errorMessage;//to add client info
	}	
}
