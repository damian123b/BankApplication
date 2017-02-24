package com.luxoft.bankapp.service;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.*;
import java.util.*;

import com.luxoft.bankapp.model.*;
import com.luxoft.bankapp.userinteractiontools.*;


public class BankServiceImpl implements BankService, BankReport{

	@Override
	public void getClientsByCity(Bank bank) {
		Map<String, List<Client>> mapOfClients = new TreeMap <>(); /*(new Comparator<String>()
				{
			public int compare(String s1, String s2)
			{
				//return c1.getCity().compareTo(c2.getCity()); error ...:(
				return s1.compareTo(s2);
			}
				});*/
		Set<String> setOfCities = new TreeSet<>();
		List <Client> listOfClientsFromSameCity = new ArrayList<>();
		
		for (Client c: bank.getClients())
				{ setOfCities.add(c.getCity()); }

		for (String city:setOfCities)
		{
		for (Client c: bank.getClients())
		      {
		              if (c.getCity().equals(city))
		              {listOfClientsFromSameCity.add(c);}
		      }
		    mapOfClients.put(city, listOfClientsFromSameCity);
		    listOfClientsFromSameCity.clear();
		}
	
		for(String nameOfCity: mapOfClients.keySet())
				{
			    System.out.print(nameOfCity+":");
			      for (Client c: mapOfClients.get(nameOfCity))
			         {
			    	  System.out.println(c.getName());
			         }
				}
	} 
	
	@Override
	public float getBankCreditSum(Bank bank)
	{
		float suma = 0.0f;
		for (Client c : bank.getClients())
		{
			for(Account a : c.getAccounts())
			{
				if(a.getBalance() < 0) 
				{ 
					suma += a.getBalance(); 
				}
			}
		}
		return suma;
	}

	@Override
	public int getNumberOfClients(Bank bank){
		return bank.getClients().size();		
	}
	
	@Override
	public void getAccountsNumber(Bank bank)
	{
		for(Client c : bank.getClients())
		{
			System.out.print("Client "+c.getName()+" : number of accounts: ");
		    System.out.println(c.getAccounts().size());
		}
	}

	public void getClientsSorted(Bank bank)
	{
		Set <Client> clientsSortedByName = new TreeSet<>(new ClientComparatorByName());
		System.out.println(clientsSortedByName);
	}
	
	@Override
	public Client findClient(Bank bank, String nameSearched)
	{
//	   for ( Client c : bank.getClients() )
//	    {
//		   if (c.getName().equals(nameSearched)  )
//				{
//					BankCommander.currentClient = c;
//					System.out.println("Client "+c+ "found");
//					return c;				
//				}	
//	    }       in exercise 5 changed to: 
		
		if (bank.getMapOfClientsInBankClass().containsKey(nameSearched)) 
		{
			Client clientFound = BankCommander.currentBank.getMapOfClientsInBankClass().get(nameSearched);
			
			System.out.println(clientFound.toString());
			
			BankCommander.currentClient = clientFound;
			System.out.println("Client "+ clientFound+ " found!");
			return clientFound; 
		}
     return null;
	}
	
	@Override
	public void addClient(Bank bank, Client clientParam) 
	{	
		bank.addClient(clientParam);
	}
	
	@Override
	public void removeClient(Bank bank, Client client) {
		 bank.removeClient(client);
	}

	@Override
	public void addAccount(Client client, Account account) {
		System.out.println("++++++++++++++");
		client.addAccount(account);
		System.out.println("++++++++++++++");
	}

	@Override
	public void setActiveAccount(Client client, Account account) {
		client.setActiveAccount(account);	
	}

	@Override
	public void saveClient(Client c) throws FileNotFoundException, IOException {
		try 
	(   FileOutputStream fos = new FileOutputStream("C:/users/dbober/desktop/clientInfo.txt");
		ObjectOutputStream oos = new ObjectOutputStream(fos); )
	   {
			oos.writeObject(c);
	   }		
	}

	@Override
	public Client loadClient() throws IOException, ClassNotFoundException {
		Client client;
		try 
		( FileInputStream fis = new FileInputStream("C:/users/dbober/desktop/clientInfo.txt");
		ObjectInputStream ois = new ObjectInputStream(fis);)
		{
		 client = (Client) ois.readObject();
		}
		return client;
	}

	public Account gatherDataForAccountCreation() {

		BankCommander.scanner = new Scanner(System.in);
		System.out.println("Which account would you like to add for current client? "
				+ "\"1\" for CheckingAccount, another number for SavingAccount");
		int choice = BankCommander.scanner.nextInt();
		float whatBalance = 0.0F;
		boolean isInputGood = false;

		do {
			try {
				System.out.println("What is initial balance?");
				whatBalance = BankCommander.scanner.nextFloat();
				System.out.println("You entered " + whatBalance
						+ " .If you are prompted for input again, make sure that amount is  correct and positive. "
						+ "\n" + " If you see \"Input succesful\" message, pls disregard this warning");
				isInputGood = true;
			} catch (InputMismatchException ime) {
				System.out.println("You entered " + whatBalance
						+ " .If you are prompted for input again, make sure that amount is  correct and positive.");
				BankCommander.scanner.nextLine();
			}
		} while (isInputGood == false | whatBalance < 0);
		System.out.println();
		System.out.println("Input succesful");

		BankCommander.scanner.nextLine();
		isInputGood = false; 
		
		System.out.println("What is name of the account?");
		String whatName = BankCommander.scanner.nextLine();

		if (choice == 1) {

			System.out.println("What is overdraft allowed?");
			float whatOverdraft = BankCommander.scanner.nextFloat();

			// create and add account:
			CheckingAccount ca = new CheckingAccount(whatName, "c", BankCommander.currentClient.getId(),
					BankCommander.currentBank.getId(), false, whatBalance, whatOverdraft);
			//BankCommander.currentClient.addAccount(ca);
			return ca;
		}

		else {
			
			SavingAccount sa = new SavingAccount(whatName, "s", BankCommander.currentClient.getId(),
					BankCommander.currentBank.getId(), false, whatBalance);
			//BankCommander.currentClient.addAccount(sa);
			return sa;
		}
	}
}
