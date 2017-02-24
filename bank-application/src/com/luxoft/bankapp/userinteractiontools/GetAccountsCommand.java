package com.luxoft.bankapp.userinteractiontools;
import com.luxoft.bankapp.model.*;
import com.luxoft.bankapp.service.*;

public class GetAccountsCommand implements Command {

	BankServiceImpl bsi = new BankServiceImpl();
	
	@Override
	public void execute() {
      	
      	for (Account a: BankCommander.currentClient.getAccounts())
      	{
      		System.out.println(a);
      		System.out.println();
      		System.out.println("Another accounts - if any: ");
      	}
//		for (Account account : BankCommander.currentClient.getAccounts())
//		{
//			System.out.println("Accounts are: ");
//			System.out.println("Account are: "+account+" "+account.getBalance());
//		}	
	}

	@Override
	public void printCommandInfo() {
		System.out.println("display accounts of current client");	
	}
}
