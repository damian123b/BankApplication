package com.luxoft.bankapp.userinteractiontools;

import java.util.LinkedList;
import java.util.List;

import com.luxoft.bankapp.databases.AccountDAOImpl;
import com.luxoft.bankapp.databases.BankDAOImpl;
import com.luxoft.bankapp.databases.ClientDAOImpl;
import com.luxoft.bankapp.exceptions.DAOException;
import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;

public class DBSelectBankCommander implements Command {

	@Override
	public void execute() {
		System.out.println("Enter bank name:");
		String bankName = BankCommander.scanner.nextLine();
		Bank b = null;
		try {
			if ((b = new BankDAOImpl().getBankByName(bankName)) != null) {
				BankCommander.currentBank = b;
				System.out.println("Bank " + bankName + " found and set as current.");
				System.out.println("Ensuring, current bank name is " + BankCommander.currentBank.getName());

				List<Client> lista = new ClientDAOImpl().getAllClients(BankCommander.currentBank);

				for (Client c : lista) {
					BankCommander.currentBank.addClient(c);
				}
			}
		} catch (DAOException e) {
			e.printStackTrace();
		}

		for (Client c : BankCommander.currentBank.getClients()) {
			List<Account> listaKont = new LinkedList<>();
			try {
				listaKont = new AccountDAOImpl().getClientAccounts(c.getId());
			} catch (DAOException e) {
				e.printStackTrace();
			}
			for (Account a : listaKont) {
				c.addAccount(a);
				if (a.isActive()) {
					c.setActiveAccount(a);
				}
			}
		}
	}

	@Override
	public void printCommandInfo() {
		System.out.println("load a bank from database and set as active");
	}
}
