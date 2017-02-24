package com.luxoft.bankapp.databases;

import java.util.List;
import com.luxoft.bankapp.exceptions.DAOException;
import com.luxoft.bankapp.model.Account;

public interface AccountDAO {
	
	   public void save(Account account) throws DAOException;
	   
	   public void add(Account account) throws DAOException;
	    
	   public void removeByClientId(int idClient) throws DAOException;
	    
	   public List<Account> getClientAccounts(int idClient) throws DAOException;
}
