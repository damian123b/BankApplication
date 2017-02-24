package com.luxoft.bankapp.userinteractiontools;

import com.luxoft.bankapp.model.Client;

public class PrintBankReportCommander implements Command {

	@Override
	public void execute() {
		if (BankCommander.currentBank != null) {
			
			BankCommander.currentBank.printReport();
			try {
				Thread.sleep(2500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		else {
			System.out.println("Current bank is null. Did you set it?");
		}

	}

	@Override
	public void printCommandInfo() {
		System.out.println("print info about current bank and its clients");
	}
}
