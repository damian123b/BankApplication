package com.luxoft.bankapp.model;
import java.util.*;
import com.luxoft.bankapp.service.*;
import com.luxoft.bankapp.userinteractiontools.BankCommander;

public class Bank implements Report {
	
	@NoDB private int id;
	private String name;
	
	@NoDB private Set<Client> clients = new TreeSet<>(); 
	@NoDB private List<ClientRegistrationListener> listeners = new ArrayList<>();	
	
	@NoDB
	private Map<String, Client> mapOfClientsInBankClass = new HashMap<>(clients.size()); 
		
	//**************************
	public Map<String, Client> getMapOfClientsInBankClass() {
		return Collections.unmodifiableMap(mapOfClientsInBankClass);
	}

	public void parseFeed(Map<String, String> feed) {
	       String nameFromFile = feed.get("name"); 
//	        // try to find client by his name
	      
	       Client client = mapOfClientsInBankClass.get(nameFromFile);
	      if (client==null) { // if no client then create it
	    	     Gender g;
	    	   
	    	if(feed.get("gender") != null)  {    
	    	     
	    	     if (feed.get("gender").equalsIgnoreCase("m"))      {g = Gender.MALE;}
	    	     else if (feed.get("gender").equalsIgnoreCase("f")) {g = Gender.FEMALE;}
	    	     else                                               {g = null;}
	              client = new Client(-1,nameFromFile,g,feed.get("email"), feed.get("city"), feed.get("phone number"));
	             //and add him:
	              new BankServiceImpl().addClient(BankCommander.currentBank, client);
//	              mapOfClientsInBankClass.put(name, client);
//	              clients.add(client);         
	      }
	      }
	        /**
	         * This method should read all info
	         * about the client from the feed map
	         */
	      client.parseFeed(feed);
	//   }
	}
		
	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Bank(int idParam, String name) {
		class PrintClientListener implements ClientRegistrationListener
		{
            @Override
			public void onClientAdded(Client c) {
				System.out.println("Adding new client: " + c.getClientSalutation() +" "+c.getName());
			}
	
		}
		class ClientMapFillerListener implements ClientRegistrationListener
		{	@Override
			public void onClientAdded(Client c) {
			mapOfClientsInBankClass.put(c.getName(), c);
			System.out.println("Added new entry to mapOfClientsInBankClass collection");
			}
		}
		class EmailNotificationListener implements ClientRegistrationListener
		{
			@Override
			public void onClientAdded(Client c)
			{
			 System.out.println("Notification email for client "+c.getName()+" to be sent");	
			}
		}   
		    this.id=idParam;
		    this.name=name;
			this.setListeners(new PrintClientListener());
			this.setListeners(new EmailNotificationListener());
			this.setListeners(new ClientMapFillerListener());
	}
	

	public List<ClientRegistrationListener> getListeners() {
		return Collections.unmodifiableList(listeners);   
	}

	public void setListeners(ClientRegistrationListener clientRegListener) {	
		this.listeners.add(clientRegListener);}
	
	public Set<Client> getClients() {
		return Collections.unmodifiableSet(clients);
	}

	public void addClient(Client client) 
	{   		 
		if (clients.add(client))
		{
			            
            for (ClientRegistrationListener crl : listeners )
                {crl.onClientAdded(client);}
            System.out.println("Client "+client.getClientSalutation()+" "+client.getName()+ " added succesfully.\n");
		}
		else {
			System.out.println("Client already exists!");
		}
		
	}
    public int getId() {
		return id;
	}

	public void removeClient(Client client) {
		clients.remove(client);
	}
	
	@Override
	public void printReport() {
	
		if (!clients.isEmpty()) {
			for (Client client : clients) {
				client.printReport();
			} 
		}
		else {
			System.out.println("This bank has no active clients");
		}
	}

	
}

