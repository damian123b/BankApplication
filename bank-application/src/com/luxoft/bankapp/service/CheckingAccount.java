package com.luxoft.bankapp.service;
import java.util.*;

import com.luxoft.bankapp.exceptions.*;
import com.luxoft.bankapp.userinteractiontools.BankCommander;

public class CheckingAccount extends AbstractAccount implements java.io.Serializable {
	static final long serialVersionUID = 1L;
	private float overdraft;
	private String name;
//	private int owner_id;
//    private int id;
//    
//    public int getId() {
//		return id;
//    }
    
	public String getName() {
		return name;
	}
	public CheckingAccount(float overdraft, String name, int owner_id) {
		super();
		this.overdraft = overdraft;
		this.name = name;
		super.setOwner_Id(owner_id);
	}
	@Override
	public float getOverdraft() {
		return overdraft;
	}

	public void setOverdraft (float o)
	{
		if (o < 0){
			throw new IllegalArgumentException();}
		overdraft = o;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String toString() {
		return name + " (ID:) "+super.getId()  + " balance: "+super.getBalance();
	}

	public CheckingAccount() {
		super.setId((int) Math.abs(new Date().getTime() - new Random().nextLong()));
		name = null;
		setBalance(0F);
	}
	public CheckingAccount(float balance, String name) {
		super.setId((int) Math.abs(new Date().getTime() - new Random().nextLong()));
		this.name = name;
		overdraft = 10F;
		setBalance(balance);
	}
	
	public CheckingAccount(float balance, String name, float overdraftParam) {
		super.setId((int) (new Date().getTime() - new Random().nextLong()));
		this.name = name;
		super.setBalance(balance);
		this.setOverdraft(overdraftParam);
	}
	
	public CheckingAccount(int idik, String name, String typeOfAccount, int owner_id, int bank_id,
			boolean activeOrNot, float balance, float overdraft)
	{
		if (!typeOfAccount.equalsIgnoreCase("c"))  {throw new IllegalArgumentException("Account type for this account must be \"c\"");}
		super.setId(idik);
		this.name = name;
		super.setAccountType(typeOfAccount);
		super.setOwner_Id(owner_id);
		super.setBank_Id(bank_id);
		super.setActive(activeOrNot);
		super.setBalance(balance);
		this.overdraft = overdraft;
}
	/* constructor to insert account to database*/
	public CheckingAccount(String name, String typeOfAccount, int owner_id, int bank_id,
			boolean activeOrNot, float balance, float overdraft)
	{
		if (!typeOfAccount.equalsIgnoreCase("c"))  {throw new IllegalArgumentException("Account type for this account must be \"c\"");}
		//super.setID(idik);
		this.name = name;
		super.setAccountType(typeOfAccount);
		super.setOwner_Id(owner_id);
		super.setBank_Id(bank_id);
		super.setActive(activeOrNot);
		super.setBalance(balance);
		this.overdraft = overdraft;
}
	

	public void printReport() {
		System.out.println("Name: "+this.getName()+"MOST IMPORTANT FIELD id ejst równe "+getId()+", balance: "+this.getBalance());
	}

	@Override
	public String getAccountType(){
		return "c";
	 }

	@Override
	public void withdraw(float amountParam) throws OverDraftLimitExceededException{
		if (amountParam <= getBalance() + overdraft)
		{
			setBalance(getBalance() - amountParam);
			System.out.println("Wirhdrwa method from CheckinaAccount executed succefully");

		} else {
			throw new OverDraftLimitExceededException(amountParam,BankCommander.currentClient /*needed
			to be changed after modifications of CheckingAccount for databases connectivity*/, this);
	}
	
	}
	@Override
	public float decimalValue() {
		float unroundedAmount= super.getBalance();	    
		int temp = Math.round(unroundedAmount*100);
		double endResult =  temp/100.0;
	   return  (float) endResult;
	}
	@Override
	public void parseFeed(Map<String, String> feed) {		
		this.setBalance(Float.valueOf(feed.get("balance")));		
	}
//	@Override
//	public int getOwner_Id() {
//		return super.getOwner_Id();
//	}
	@Override
	public void setId(int accountIdFromDB) {
		super.setId(accountIdFromDB);
		
	}
	
    
}

