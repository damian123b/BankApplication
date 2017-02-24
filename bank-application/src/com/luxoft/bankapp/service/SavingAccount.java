package com.luxoft.bankapp.service;

import java.util.Map;

public class SavingAccount extends AbstractAccount {
   static final long serialVersionUID = 2L;
	private String name;
	
//	public int getId() {
//		return id;
//	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public SavingAccount(int idik, String name, String typeOfAccount, int owner_id, int bank_id,
	                     boolean activeOrNot, float balance) {
		
	 if (!typeOfAccount.equalsIgnoreCase("s"))  {throw new IllegalArgumentException("Account type for this account must be \"s\"");}
	 super.setId(idik);
	 this.name = name;
	 super.setAccountType(typeOfAccount);
	 super.setOwner_Id(bank_id);
	 super.setBank_Id(bank_id);
	 super.setActive(activeOrNot);
	 super.setBalance(balance);
    }
	
	public SavingAccount(String name, String typeOfAccount, int owner_id, int bank_id, boolean activeOrNot,
			float balance) {

		if (!typeOfAccount.equalsIgnoreCase("s")) {
			throw new IllegalArgumentException("Account type for this account must be \"s\"");
		}
		// super.setID(idik);
		this.name = name;
		super.setAccountType(typeOfAccount);
		super.setOwner_Id(bank_id);
		super.setBank_Id(bank_id);
		super.setActive(activeOrNot);
		super.setBalance(balance);
	}
	
	public String toString() {
		return name + " (ID:) "+super.getId()  + " balance: "+super.getBalance();
	}

	public SavingAccount(float balance, String name) {
		//super.setID((int) Math.abs(new Date().getTime() - new Random().nextLong()));
		this.name = name;
		setBalance(balance);
	}
	public SavingAccount() {
		//super.setID((int)Math.abs(new Date().getTime() - new Random().nextLong()));
		this.name = null;
		setBalance(0F);		
	}
	
	public void printReport() {
		System.out.print(this.toString());
	}

	@Override
	public String getAccountType(){
		return "s";
	 }
	
	@Override
	public float getOverdraft() {
		System.out.println("Overdraft not available for this type of account!");
		return 0.0f;
	}

	@Override
	public void setOverdraft(float f) {
   System.out.println("Overdraft is not available for this type of account!!");
		
	}

	@Override
	public float decimalValue() {
		float unroundedAmount = super.getBalance();
		int temp = Math.round(unroundedAmount * 100);
		double endResult = temp / 100.0;
		return (float) endResult;
	}

	@Override
	public void parseFeed(Map<String, String> feed) {	
		this.setBalance(Float.valueOf(feed.get("balance")));
	}

	@Override
	public int getOwner_Id() {	
		return super.getOwner_Id();
	}

	@Override
	public void setId(int accountIdFromDB) {
		super.setId(accountIdFromDB);	
	}

	@Override
	public int getId() {
		
		return super.getId();
	}


}
