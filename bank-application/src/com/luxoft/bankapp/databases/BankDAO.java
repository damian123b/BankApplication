package com.luxoft.bankapp.databases;

import com.luxoft.bankapp.exceptions.BankNotFoundException;
import com.luxoft.bankapp.exceptions.DAOException;
import com.luxoft.bankapp.model.Bank;

public interface BankDAO extends BaseDAO {

	Bank getBankByName (String name) throws DAOException, BankNotFoundException;
	 
	void save(Bank bank) throws DAOException;  
	
	void remove(Bank bank) throws DAOException;
}
