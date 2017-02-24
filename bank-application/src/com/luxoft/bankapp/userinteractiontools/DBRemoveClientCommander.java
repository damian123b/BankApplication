package com.luxoft.bankapp.userinteractiontools;

import java.util.Scanner;

import com.luxoft.bankapp.databases.ClientDAOImpl;
import com.luxoft.bankapp.exceptions.DAOException;
import com.luxoft.bankapp.service.BankServiceImpl;

public class DBRemoveClientCommander implements Command {

	@Override
	public void execute() {
		if (BankCommander.currentClient != null) {
			System.out.println("No active client found. Pls set him first");
			try {
				Thread.sleep(1800);
				return;
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			} 
			
		}//end of if
        System.out.println("Enter name of the client to be removed:");
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();
		try {
			new ClientDAOImpl().remove(new BankServiceImpl().findClient(BankCommander.currentBank, name));
		} catch (DAOException e) {
			
			e.printStackTrace();
		}
		finally {
			scanner.close();
		}
		
	}

	@Override
	public void printCommandInfo() {
		System.out.println("remove active client from database");
		
	}

}
