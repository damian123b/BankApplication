package com.luxoft.bankapp.userinteractiontools;

import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.service.BankServiceImpl;

public class FindClientCommand implements Command{

	private String nameEnteredByUser;
	BankServiceImpl bsi = new BankServiceImpl();
	//Scanner scanner = new Scanner(System.in);

	public Client findClient(Bank bank, String nameSearched)
	{
		return bsi.findClient(BankCommander.currentBank,/*BankApplication.getBank1()*/ nameEnteredByUser);
	}
	
	@Override
	public void execute() { 	
		
		System.out.print("Which client are you searching? - enter name:");
		nameEnteredByUser = BankCommander.scanner.nextLine();
		System.out.println("You entered: "+nameEnteredByUser);
		
		if (findClient(BankCommander.currentBank, nameEnteredByUser) != null) 			 
		{
			      System.out.println("Found! Method findClient() works fine.");
			   
			      return;
		}
		 else 
		 {
		  System.out.println("Client with name "+nameEnteredByUser+" not found");
		  
		 }
	}
		
//		for ( int i=0; i<=bank.getClients().size(); i++)
//		{
//			if (bank.getClients().get(i).getName().equals(nameEnteredByUser)  )
//			{
//				BankCommander.currentClient = bank.getClients().get(i);
//				System.out.println("Client "+bank.getClients().get(i).getName()+ "found");
//				BankCommander.scanner.nextLine();
//				BankCommander.scanner.close();
//				break;				
//			}
//			System.out.println("Client not found");
//			BankCommander.scanner.nextLine();
//			BankCommander.scanner.close();
//		}
	@Override
	public void printCommandInfo() {
		System.out.println("find and set client as current client");
		
	}

}
