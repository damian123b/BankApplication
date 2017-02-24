package com.luxoft.bankapp.model;

public enum Gender implements java.io.Serializable{
  MALE("Mr."), FEMALE("Mrs.");
	
	String salutation;
	private Gender (String salutation)	{
		this.salutation = salutation;		
	}
	
	public String getSalutation() {
    return salutation;		
	}
}

