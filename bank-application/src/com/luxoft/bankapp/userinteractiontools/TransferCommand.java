package com.luxoft.bankapp.userinteractiontools;

import java.util.Scanner;

import com.luxoft.bankapp.databases.AccountDAOImpl;
import com.luxoft.bankapp.exceptions.BankException;
import com.luxoft.bankapp.exceptions.DAOException;
import com.luxoft.bankapp.exceptions.OverDraftLimitExceededException;
import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.service.BankServiceImpl;

public class TransferCommand implements Command{	

	BankServiceImpl bsi = new BankServiceImpl();   
	
	@Override
	public void execute() {
		 boolean withdrawSuccesful = false;
         String nameOfReceiver="";
		
		BankCommander.scanner = new Scanner(System.in);
		System.out.print("Enter amount you want to transfer:");
		float amountToWithdraw = BankCommander.scanner.nextFloat();
		BankCommander.scanner.nextLine();	
		System.out.print("To whom you want to transfer? - enter complete name:");
		nameOfReceiver = BankCommander.scanner.nextLine();	
		
		Account accountToBeChanged = null;
		
		 if (BankCommander.currentClient != null ) 
		 {	 
			 if(BankCommander.currentClient.getActiveAccount() != null)
			 {
				 try {
					 accountToBeChanged = BankCommander.currentClient.getActiveAccount();
					 System.out.println();
					 System.out.println("current account to be changed is "+accountToBeChanged.getName());
					 
					BankCommander.currentClient.getActiveAccount().withdraw(amountToWithdraw);
										
					new AccountDAOImpl().save(BankCommander.currentClient.getActiveAccount());
					
				    withdrawSuccesful = true;
				    
				} catch (OverDraftLimitExceededException e) {					
					e.getMessage();
				}
				 catch (BankException e) {
					 e.getMessage();
				 }
				 catch (DAOException daoe) {
					 daoe.printStackTrace();
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
			
		 if(withdrawSuccesful)
			{
			 System.out.println("Entering second phase: balance of current client(damian) is "+BankCommander.currentClient.getActiveAccount().getBalance());
			 Client receiverFound;
			 if ((receiverFound = bsi.findClient(BankCommander.currentBank, nameOfReceiver)) != null)
			 { 
		
				System.out.println("Has active account? "+(receiverFound.getActiveAccount()!=null));
				System.out.println("current client is now set to  "+BankCommander.currentClient.getName());
				
				receiverFound.getActiveAccount().deposit(amountToWithdraw);
				try {
					new AccountDAOImpl().save(receiverFound.getActiveAccount());
				} catch (DAOException e) {
					e.printStackTrace();
				}
			    System.out.println(amountToWithdraw + " succesfully deposited to account "+
				bsi.findClient(BankCommander.currentBank, nameOfReceiver).getActiveAccount());	 
			 }
			 else {
				 System.out.println("Operation failed. Make sure you selected correct receiver");
				 accountToBeChanged.deposit(amountToWithdraw);
				 try {
					new AccountDAOImpl().save(accountToBeChanged);
				} catch (DAOException e) {
					e.printStackTrace();
				}
			 }
			 
			}
		 
	}	//end of method

	@Override
	public void printCommandInfo() {		
		System.out.println("Transfer money to another account");
	}

}
