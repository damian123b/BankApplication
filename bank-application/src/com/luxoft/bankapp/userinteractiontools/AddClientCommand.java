package com.luxoft.bankapp.userinteractiontools;

import com.luxoft.bankapp.databases.ClientDAOImpl;
import com.luxoft.bankapp.exceptions.DAOException;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.model.Gender;
import com.luxoft.bankapp.service.BankServiceImpl;

public class AddClientCommand implements Command{
	
	Client c;
	BankServiceImpl bsi = new BankServiceImpl();
	@Override
	public void execute() {

		System.out.println("Enter client's name:");
		String whichName = BankCommander.scanner.nextLine();
		System.out.println("Enter client's sex: \"1\" for male and \"2\" for female");
		int whichGender = BankCommander.scanner.nextInt();
	    BankCommander.scanner.nextLine();                    //discards input
	    
		System.out.println("Enter your email:");
		String whichEmail = BankCommander.scanner.nextLine();
		
		String cityName;
		System.out.println("Enter your city:");
		cityName = BankCommander.scanner.nextLine();
		
		String whichPhoneNumber;
		do {System.out.println("Enter your phone number(numbers only!):");
		whichPhoneNumber = BankCommander.scanner.nextLine();}
		
		while (! whichPhoneNumber.matches("[0-9]*"));
		System.out.println("Phone number"+whichPhoneNumber+" ok!");
		
		c = new Client(whichName, (whichGender==1) ? Gender.MALE : Gender.FEMALE, cityName, whichEmail,whichPhoneNumber);
		 
			bsi.addClient(BankCommander.currentBank, c);
			try {
				new ClientDAOImpl().save(c);
			} catch (DAOException e) {
				e.printStackTrace();
			}
			
			c.setId(new ClientDAOImpl().getClientIdFromDB(c));
			
			try {
				new ClientDAOImpl().assignClientToCurrentBank(c);
			} catch (DAOException e) {
				
				e.printStackTrace();
			}
				 
	}

	@Override
	public void printCommandInfo() {		
		System.out.println("add a client to current bank");	
	}

}
