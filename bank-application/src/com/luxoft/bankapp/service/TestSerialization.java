package com.luxoft.bankapp.service;

import java.io.IOException;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.model.Gender;


public class TestSerialization {
	static BankServiceImpl bsi = new BankServiceImpl();
	
	public static void main (String[] args) {
		
	Client klient = new Client(-1,"james bond", Gender.MALE, "Londyn", "jbond@wp.pl", "35683");
	klient.addAccount(new SavingAccount(20, "my saving accounts"));
	klient.addAccount(new CheckingAccount(40, "czeking akount", 30));
	klient.addAccount(new SavingAccount(85, "drugie saving account"));
	
	System.out.println(klient);
	
	try {
		bsi.saveClient(klient);
	}   catch (IOException ioe) {
		ioe.printStackTrace();
	    }
	System.out.println("if no errors appeared, client is saved.");
	Client clientFromFile=null;
	try {
    clientFromFile =  bsi.loadClient();
	} catch (IOException | ClassNotFoundException e) {
		e.printStackTrace();
	    }
	System.out.println(clientFromFile);
	} //end of main
}

