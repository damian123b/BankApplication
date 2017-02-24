package com.luxoft.bankapp.userinteractiontools;

import java.util.Scanner;

import com.luxoft.bankapp.databases.AccountDAOImpl;
import com.luxoft.bankapp.exceptions.BankException;
import com.luxoft.bankapp.exceptions.DAOException;
import com.luxoft.bankapp.exceptions.OverDraftLimitExceededException;
import com.luxoft.bankapp.model.Bank;

public class WithdrawCommand implements Command {

	 private float amountToWithdraw;
	 
	@Override
	public void execute() {
		BankCommander.scanner = new Scanner(System.in);
		System.out.print("Enter amount to withdraw");
		amountToWithdraw = BankCommander.scanner.nextFloat();
		BankCommander.scanner.nextLine();
		
		 if (BankCommander.currentClient == null ) 
		 {	 
			 System.out.println("Current client has not been set");
			 return;
		 }
		 else 
		 {
			 if(BankCommander.currentClient.getActiveAccount() != null)
			 {
				 try {

					 
					float startAmount = BankCommander.currentClient.getActiveAccount().getBalance();
					BankCommander.currentClient.getActiveAccount().withdraw(amountToWithdraw);
					
					new AccountDAOImpl().save(BankCommander.currentClient.getActiveAccount());
					
					System.out.println("Withdrawal succesful.\n"
							+ "balance should be "+(startAmount - amountToWithdraw));
					System.out.println("Real balance = "+BankCommander.currentClient.getActiveAccount().getBalance());
					
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
			 else {System.out.println("This client has no active account.");}
		 }	
		
	}

	@Override
	public void printCommandInfo() {
		System.out.println("withdraw money from account");		
	}

}
