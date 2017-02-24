package com.luxoft.bankapp.model;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.luxoft.bankapp.exceptions.FeedException;
import com.luxoft.bankapp.service.CheckingAccount;
import com.luxoft.bankapp.service.Report;
import com.luxoft.bankapp.service.SavingAccount;

public class Client implements Report, Comparable<Client>, java.io.Serializable {

	static final long serialVersionUID = 2;
	@NoDB private int id;
	private String name;
    private Set<Account> accounts = new HashSet<>(); 
	private Account activeAccount;
	private Gender gender;
	private String emailAddress;
	private String phoneNumber;
	private String city;
	@NoDB private String nickname;
	
	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	@Override
	public int compareTo(Client client) {
		if (this.name.equals(client.getName()) && this.gender.equals(client.getGender()) ) return 0;
		else if (this.name.compareTo(client.getName()) > 0) return 1;
		else return -1;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId (int id){
		this.id = id;	
	}
	
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((gender == null) ? 0 : gender.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Client other = (Client) obj;
		if (gender != other.gender)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	public Gender getGender() {
		return gender;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Account> getAccounts() {
		return accounts;
	}

	public void addAccount(Account account) {
		System.out.println("account added succesfully? "+accounts.add(account)) ;
	}

	public Account getActiveAccount() {
		return activeAccount;
	}

	public void setActiveAccount(Account activeAccount) { // cannot be directly
															// replaced by method from  BankServiceImpl
		this.activeAccount = activeAccount;
	}

	public Client(String name, Gender gender, Set<Account> listOfAccounts, Account a) {
		this.name = name;
		this.gender = gender;
		accounts = listOfAccounts;
		activeAccount = a;	
	}
	
	public Client(String name, Gender gender) {
		this.name = name;
		this.gender = gender;	
	}
	public Client(String name) {
		this.name = name;
	}
	
	public Client(String name, Gender gender, String city, String email, String phoneNumber) {
		    //* constructor without id - database should autoincrement
		this.name = name;
		this.gender = gender;
		emailAddress = email;	
		this.city = city;
		this.phoneNumber = phoneNumber;
		
	}
	
	public Client(int id,String name, Gender gender, String city, String email, String phoneNumber) {
		this.id = id;
		this.name = name;
		this.gender = gender;
		emailAddress = email;	
		this.city = city;
		this.phoneNumber = phoneNumber;
	}
	
	public String getClientSalutation() {
		String salut = this.gender.getSalutation().equals("Mr.") ? "Mr" : "Mrs";
		return salut;
	}

	@Override 
	public String toString()
	{
		StringBuilder sb = new StringBuilder("");
		sb.append("Client is ").append(this.getGender().getSalutation()).append(" "+this.name);
		sb.append("\n");
		sb.append("mail: " + emailAddress);
		sb.append(" phone: "+ phoneNumber);
		sb.append(" from "+city);
		sb.append("\n"+" has following accounts: "+Arrays.toString(accounts.toArray()) );
		if (this.getActiveAccount() != null)  {
			sb.append("\n"+"Active account:"+getActiveAccount().getName()+", balance: "+getActiveAccount().getBalance());}
		else   {
			sb.append("\n"+"Active accounts: none");}
		
		return sb.toString();
	}
	
	@Override
	public void printReport() {
		System.out.println("Client " + getClientSalutation()+" " + getName() + " has following accounts: ");

       for(Account a : getAccounts()) {
    	   a.printReport();
    	   if(a.isActive()) {
    		   System.out.println();
    		   System.out.print("This one is active.");
    		   System.out.println();
    	   }
       }
       
		if (this.getActiveAccount() != null) {
			System.out.println("Active account (if any): id " + this.getActiveAccount().getId() + ", balance: "
					+ this.getActiveAccount().getBalance());
		}
		System.out.println();
	}

    private Account createAccount(String accountType) {
        Account acc;
         if ("s".equals(accountType)) {
               acc = new SavingAccount();
         } else if ("c".equals(accountType)) {
               acc = new CheckingAccount();
         } else {
               throw new FeedException("Account type not found "+accountType);
         }
         accounts.add(acc);
         return acc;
    }
	
	private Account getAccount(String accountType) {
        for (Account acc: accounts) {
              if (acc.getAccountType().equals(accountType)) {
                   return acc;
              }
        }
        return createAccount(accountType);      
   }
	
	   public void parseFeed(Map<String, String> feed) {
           String accountType = feed.get("accounttype");
           Account acc = getAccount(accountType);
           /**
            * This method should read all account info from the feed.
            * There will be different implementations for
            * CheckingAccount and SavingAccount.
            */
           acc.parseFeed(feed);
           setActiveAccount(acc);
      }
}
