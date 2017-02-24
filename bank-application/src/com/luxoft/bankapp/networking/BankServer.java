package com.luxoft.bankapp.networking;

import static com.luxoft.bankapp.userinteractiontools.BankCommander.currentBank;
import static com.luxoft.bankapp.userinteractiontools.BankCommander.currentClient;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.model.Gender;
import com.luxoft.bankapp.service.BankServiceImpl;
import com.luxoft.bankapp.service.CheckingAccount;
import com.luxoft.bankapp.userinteractiontools.BankCommander;

public class BankServer {

	ServerSocket providerSocket;
	Socket connection = null;
	ObjectOutputStream oos;
	ObjectInputStream ois;
	DataInputStream dis;
	DataOutputStream dos;
	String message;
	static Logger loggerForExceptions = Logger.getLogger(BankServer.class.getName());
	static Logger loggerForConnections = Logger
			.getLogger("connections to " + BankServer.class.getSimpleName() + " logger");

	static void initialise() {
		Client exampleClient1 = new Client(39, "adam malysz", Gender.MALE, "wisla", "malysz@wp.pl", "774512654");
		Client exampleClient2 = new Client("steffi graf", Gender.FEMALE, "Los Angeles", "steffi@wp.pl", "98796452312");
		currentBank.addClient(exampleClient1);
		currentBank.addClient(exampleClient2);
		currentClient = exampleClient1;
		Account accForTesting = new CheckingAccount(1500f, "checking account for Adam Malysz", 800f);
		currentClient.addAccount(accForTesting);
		currentClient.setActiveAccount(accForTesting);
	}

	void run() throws IOException, ClassNotFoundException {
		long startTime = 0, endTime = 0;
		Handler fileHandlerForExceptions = null;
		Handler fileHandlerForConenctions = null;
		Handler fileHandlerGeneral = null;

		loggerForExceptions.setLevel(Level.ALL);
		loggerForConnections.setLevel(Level.ALL);

		try {

			initialise();
			System.out.println("Ilu klientow? = " + BankCommander.currentBank.getClients().size());
			int optionReceived = 0;
			providerSocket = new ServerSocket(2005);
			System.out.println("server socket initialized succesfully");

			while (true) {

				// fileHandlerForExceptions = new
				// FileHandler("logger_exceptions.properties");
				fileHandlerForExceptions = new FileHandler("C:/Windows/Temp/logger_exceptions.properties ", true);
				fileHandlerForConenctions = new FileHandler("C:/Windows/Temp/logger_clients.properties ", true);
				fileHandlerGeneral = new FileHandler("C:/Windows/Temp/logger_all.properties ", true);
				loggerForExceptions.addHandler(fileHandlerForExceptions);
				loggerForExceptions.addHandler(fileHandlerGeneral);
				loggerForConnections.addHandler(fileHandlerForConenctions);
				loggerForConnections.addHandler(fileHandlerGeneral);

				System.out.println("Waiting for connection");
				connection = providerSocket.accept();

				startTime = System.currentTimeMillis();
				loggerForConnections.log(Level.INFO, "Client connected to server on " + new Date().toString());

				System.out.println("Connection received from " + connection.getInetAddress().getHostName());

				dis = new DataInputStream(connection.getInputStream());
				oos = new ObjectOutputStream(connection.getOutputStream());
				// oos.flush();
				ois = new ObjectInputStream(connection.getInputStream());
				dos = new DataOutputStream(connection.getOutputStream());
				optionReceived = dis.readInt();

				switch (optionReceived) {
				case 1: // show statistics
					System.out.println("Server is processing option 1....");
					// oos.flush();
					oos.writeObject(BankCommander.currentBank.getClients());
					break;

				case 2: // find client
					String nameToSearch = dis.readUTF();
					nameToSearch = nameToSearch.trim();
					String newNameToSearch = "";
					// StringBuilder sb = new StringBuilder(nameToSearch);
					if (nameToSearch.startsWith("Mr") || nameToSearch.startsWith("mr")
							|| nameToSearch.startsWith("MR")) {
						newNameToSearch = nameToSearch.substring(2);
					} else {
						newNameToSearch = nameToSearch;
					}
					if (nameToSearch.startsWith("Mrs") || nameToSearch.startsWith("mrs")
							|| nameToSearch.startsWith("MRs")) {
						newNameToSearch = nameToSearch.substring(3);
					} else {
						newNameToSearch = nameToSearch;
					}
					if (newNameToSearch.startsWith("."))
						newNameToSearch = newNameToSearch.substring(1);
					if (newNameToSearch.startsWith(" "))
						newNameToSearch = newNameToSearch.substring(1);

					Client c = new BankServiceImpl().findClient(BankCommander.currentBank, newNameToSearch);
					oos.writeObject(c);
					break;

				case 3: // add client

					Client clientToBeAdded = (Client) ois.readObject();
					new BankServiceImpl().addClient(BankCommander.currentBank, clientToBeAdded);
					dos.writeUTF("Client " + clientToBeAdded.getName() + " has been added to current bank.");
					break;

				case 4:
					try {
						endTime = System.currentTimeMillis();
						loggerForExceptions.log(Level.INFO, "Cient disconencted from server on " + new Date().toString()
								+ " Connection lasted " + (endTime - startTime) / 1000.0 + "seconds.");

						dis.close();
						dos.close();
						ois.close();
						oos.close();
					} catch (IOException ioException) {
						ioException.printStackTrace();
						loggerForExceptions.log(Level.SEVERE, ioException.getMessage(), ioException);
					}
				}
			}
		} finally {
		}
	}
      
      public static void main(final String args[])  throws IOException, ClassNotFoundException{
    	  
    	  //LogManager.getLogManager().readConfiguration(new FileInputStream("C:/Windows/Temp/logger.properties"));
   
    	 BankServer server = new BankServer();

    	 //loggerForExceptions.setLevel(Level.ALL);  	 
         server.run();
     
    }
}