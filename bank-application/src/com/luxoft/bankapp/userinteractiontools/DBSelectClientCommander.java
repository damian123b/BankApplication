package com.luxoft.bankapp.userinteractiontools;

import java.util.Scanner;

import com.luxoft.bankapp.databases.ClientDAOImpl;
import com.luxoft.bankapp.exceptions.DAOException;
import com.luxoft.bankapp.model.Client;

public class DBSelectClientCommander implements Command {

	@Override
	public void execute() {
		Scanner scanner;
		if (BankCommander.currentBank == null) {
			System.out.println("Current bank not set. Pls follow the steps below");
			new DBSelectBankCommander().execute();
			return;
		}

		System.out.println("current bank is " + BankCommander.currentBank.getName());
		System.out.print("Please enter client's name to search:");
		scanner = new Scanner(System.in);
		String clientNameToSearch = scanner.nextLine();
		try {
			
			
			Client c = new ClientDAOImpl().findClientByName(BankCommander.currentBank,
					clientNameToSearch);
			
			BankCommander.currentBank.removeClient(c);
			BankCommander.currentBank.addClient(c);
			
			
			if (BankCommander.currentClient != null) {
				System.out.println("Client " + clientNameToSearch + " found and set as active");
				System.out.println("Confirmation: currentclient is "+BankCommander.currentClient.getClientSalutation()+
						" "+BankCommander.currentClient.getName());
			} else {
				System.out.println("Client " + clientNameToSearch + " not found in current bank");
			}
		} catch (DAOException e) {
			scanner.close();
			e.printStackTrace();
		}
	}

	@Override
	public void printCommandInfo() {
		System.out.println("find client in active bank and set as active");
	}
}
