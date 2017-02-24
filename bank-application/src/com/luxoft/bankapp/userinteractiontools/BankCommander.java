package com.luxoft.bankapp.userinteractiontools;

import java.io.IOException;
import java.util.*;
import com.luxoft.bankapp.model.*;
import com.luxoft.bankapp.service.BankServiceImpl;
import com.luxoft.bankapp.service.CheckingAccount;

public class BankCommander {

	public static Bank[] allBanks = new Bank[20];    // in case we needed to create
													// more banks from scratch programatically
													 
	public static Bank currentBank = new Bank(10, "My Bank");

	public static Client currentClient;
	public static Scanner scanner;
	static Map<String, Command> mapOfCommands = new LinkedHashMap<>();

	public static void initialiseBankWithClients() {

		currentBank = new Bank(10, "My Bank");
		Client exampleClient1 = new Client(39, "adam malysz", Gender.MALE, "wisla", "malysz@wp.pl", "774512654");
		Client exampleClient2 = new Client(40, "steffi graf", Gender.FEMALE, "Los Angeles", "steffi@wp.pl",
				"7546452312");

		currentBank.addClient(exampleClient1);
		currentBank.addClient(exampleClient2);

		currentClient = exampleClient1;

		Account accForTesting = new CheckingAccount(99, "checking account for Adam Malysz", "c", 39, 10, true, 55.4F,
				14F);
		currentClient.addAccount(accForTesting);
		currentClient.setActiveAccount(accForTesting);
		System.out.println("Creating example data finished.");
	}

	public static void registerCommand(String name, Command command) {
		mapOfCommands.put(name, command);
	}

	public static void removeCommand(String name) {
		mapOfCommands.remove(name);
	}

	public static void main(String args[]) throws IOException, InterruptedException {
		initialiseBankWithClients();

		if (args.length > 0 && args[0].equals("-report")) {

			allBanks[0] = BankCommander.currentBank;

			// ********************************************

			// other banks to be created and added to array here
			// if this is what authors of the exercise meant

			// ********************************************

			int choice = 0;
			BankServiceImpl bsi = new BankServiceImpl();
			while (true) {
				System.out.println("1 - getNumberOfClients");
				System.out.println("2 - getAccountsNumber");
				System.out.println("3 - getClientsSorted");
				System.out.println("4 - getBankCreditSum");
				System.out.println("5 - getClientsByCity");
				System.out.println("Your choice:");
				scanner = new Scanner(System.in);
				choice = scanner.nextInt();
				switch (choice) {
				case 1:
					bsi.getNumberOfClients(currentBank);
					break;
				case 2:
					bsi.getAccountsNumber(currentBank);
					break;
				case 3:
					bsi.getClientsSorted(currentBank);
					break;
				case 4:
					bsi.getBankCreditSum(currentBank);
					break;
				case 5:
					bsi.getClientsByCity(currentBank);
					break;
				default:
					System.out.println("No such option!!!");
				}
				scanner.nextLine();
				new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
			}
		} // end of if
		mapOfCommands.put("0", new Command() { // anonymous class

			@Override
			public void execute() {
				System.exit(0);
			}

			@Override
			public void printCommandInfo() {
				System.out.println("End program");
			}
		});
	 // mapOfCommands.put("1", new FindClientCommand());
		mapOfCommands.put("2", new DepositCommand());
		mapOfCommands.put("3", new WithdrawCommand());
		mapOfCommands.put("4", new GetAccountsCommand());
		mapOfCommands.put("5", new TransferCommand());
		mapOfCommands.put("6", new AddClientCommand());
		mapOfCommands.put("7", new AddAccountsCommands());
     // registerCommand("8", new ReadFromFileCommand());
		registerCommand("9", new DBSelectBankCommander());
		registerCommand("10", new DBSelectClientCommander());
		registerCommand("11", new DBRemoveClientCommander());
		registerCommand("12", new PrintBankReportCommander());

		while (true) {
			Set<String> set = mapOfCommands.keySet();
			System.out.println();
			for (String s : set) {
				System.out.print(s + " ");
				mapOfCommands.get(s).printCommandInfo();
				System.out.println();
			}
			System.out.print("Your choice (enter a respective integer):");
			scanner = new Scanner(System.in);
			String commandString = scanner.nextLine();

			mapOfCommands.get(commandString).execute();
		}
	}
}
// while (true) {
// for (int i=0;i<commands.length;i++) { // show menu
// System.out.print(i+") ");
// commands[i].printCommandInfo();
// }
// System.out.print("Your choice (enter a respective integer):");
// String commandString = System.console().readLine(); gives
// NullPointerException
// scanner = new Scanner(System.in);
// String commandString = scanner.nextLine();
// int command = Integer.valueOf(commandString); // initialize command with
// commandString
// scannerFromMainMethod.nextLine();
// scannerFromMainMethod.close();
// commands[command].execute();
