package com.luxoft.bankapp.model;
import com.luxoft.bankapp.service.*;

import java.util.Map;
import com.luxoft.bankapp.exceptions.*;

public interface Account extends Report, java.io.Serializable {

	public float getBalance();
	public void deposit (float x);
	public void withdraw (float x)throws BankException; 
	
	public float getOverdraft();                  
	public void setOverdraft(float f);
	public float decimalValue();
	public String getAccountType();
	public void parseFeed(Map<String, String> feed);
	public String getName();
	public int getId();
	public int getOwner_Id();
	public int getBank_Id();
	public boolean isActive();
	public void setId(int accountIdFromDB);
	
}


