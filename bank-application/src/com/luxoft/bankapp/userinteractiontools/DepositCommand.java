package com.luxoft.bankapp.userinteractiontools;

import java.util.Scanner;
import com.luxoft.bankapp.databases.AccountDAOImpl;
import com.luxoft.bankapp.exceptions.DAOException;


public class DepositCommand implements Command {

 
   private float amountToDeposit;
	
	@Override
	public void execute() {
		 
		BankCommander.scanner = new Scanner(System.in);
		System.out.print("Enter amount to deposit:");
		amountToDeposit = BankCommander.scanner.nextFloat();
		System.out.println("Please press Enter");
		BankCommander.scanner.nextLine();
		
		 if (BankCommander.currentClient != null ) 
		 {	 
			 if(BankCommander.currentClient.getActiveAccount() != null)
			 {
				 BankCommander.currentClient.getActiveAccount().deposit(amountToDeposit);
				 try {
					
					 new AccountDAOImpl().save(BankCommander.currentClient.getActiveAccount());
				} 
				 catch (DAOException e) {					
					e.printStackTrace();
				}
				 
			 }
			 else
			 {
				 System.out.println("This customer has no active account");
			 }
		 }
		 else
		 {
			 System.out.println("Current client has not been set");
		 }
	}

	@Override
	public void printCommandInfo() {
		System.out.println("deposit money to active account");
		
	}
}
