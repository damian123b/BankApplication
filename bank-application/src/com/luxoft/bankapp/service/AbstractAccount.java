package com.luxoft.bankapp.service;
import com.luxoft.bankapp.model.*;
import com.luxoft.bankapp.exceptions.*;

public abstract class AbstractAccount implements Account,java.io.Serializable {
  final static long serialVersionUID = 2L;
  private float balance;
  @NoDB
  private int id;
  private int owner_Id;
  private  int bank_Id;
  private boolean active;
  private String accountType;
  

public String getAccountType() {
	return accountType;
}

public void setAccountType(String accountType) {
	this.accountType = accountType;
}

public boolean isActive() {
	return active;
}

public void setActive(boolean active) {
	this.active = active;
}

public int getOwner_Id() {
	return owner_Id;
}

public void setOwner_Id(int owner_id) {
	this.owner_Id = owner_id;
}

public int getBank_Id() {
	return bank_Id;
}

public void setBank_Id(int bank_Id) {
	this.bank_Id = bank_Id;
}

@Override
public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + (int) (id ^ (id >>> 32));
	return result;
}

@Override
public boolean equals(Object obj) {
	if (this == obj)
		return true;
	if (obj == null)
		return false;
	if (getClass() != obj.getClass())
		return false;
	AbstractAccount other = (AbstractAccount) obj;
	if (id != other.id)
		return false;
	return true;
}

public int getId() {
	return id;
}

public void setId(int id) {
	this.id = id;
}

public float getBalance() {
	return balance;
}

public void setBalance(float balance) {
	if (balance < 0)
	{ throw new IllegalArgumentException();}
	this.balance = balance;
}

@Override
public void withdraw(float x) throws NotEnoughFundsException{
	System.out.println("Balance before = " + getBalance());
	System.out.println("Executing withdraw method from Abstract Account");
	
	if (getBalance() >= x) {
		balance -= x;
		System.out.println("Balance after = "+ getBalance());
	} 
	else {
		System.out.println("Too less money to withdraw such amount");
	}    
}
  
  public void deposit(float x)
  {
	  balance += x;
  }
}

