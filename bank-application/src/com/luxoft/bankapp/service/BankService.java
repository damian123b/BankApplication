package com.luxoft.bankapp.service;

import java.io.FileNotFoundException;
import java.io.IOException;

import com.luxoft.bankapp.model.*;


public interface BankService {
	
	 void addClient(Bank bank,Client client);
	 
     void removeClient(Bank bank,Client client);
     
     void addAccount(Client client, Account account);
     
     void setActiveAccount(Client client, Account account);
     
     Client findClient (Bank bank, String nameSearched);
     
     void saveClient(Client c) throws FileNotFoundException, IOException;
     
     Client loadClient() throws IOException,ClassNotFoundException;
}
