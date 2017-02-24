package com.luxoft.bankapp.userinteractiontools;

import java.util.Scanner;

import com.luxoft.bankapp.databases.AccountDAOImpl;
import com.luxoft.bankapp.exceptions.DAOException;
import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.service.BankServiceImpl;

public class AddAccountsCommands implements Command {

	BankServiceImpl bsi = new BankServiceImpl();
	
	@Override
	public void execute() {
		
		Account acc = bsi.gatherDataForAccountCreation();
		System.out.println("gatherdata method returned "+acc.getName()+" balance:"+acc.getBalance());
	//	BankCommander.currentClient.addAccount(ca)		
		new BankServiceImpl().addAccount(BankCommander.currentClient, acc);
		
			try {
				new AccountDAOImpl().add(acc);
			} catch (DAOException e) {e.printStackTrace();}
			
			acc.setId(new AccountDAOImpl().getAccountIdFromDB(acc));
			
			System.out.println("created new acc and stored in database, Id assigned to account = "+acc.getId());
		
			
			System.out.println("Do you want to set it as active account? \"1\" for YES,another number for NO");
			
			
			BankCommander.scanner = new Scanner(System.in);
			 if (BankCommander.scanner.nextInt() == 1) {
				bsi.setActiveAccount(BankCommander.currentClient, acc);
				
				try {
					new AccountDAOImpl().markAccountAsActiveInDB(acc);
				} catch (DAOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
			
	@Override
	public void printCommandInfo() {
		System.out.println("add account to current client"); 		
	}

}
