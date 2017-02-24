package test.service;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.luxoft.bankapp.databases.AccountDAO;
import com.luxoft.bankapp.databases.AccountDAOImpl;
import com.luxoft.bankapp.databases.BankDAOImpl;
import com.luxoft.bankapp.databases.ClientDAO;
import com.luxoft.bankapp.databases.ClientDAOImpl;
import com.luxoft.bankapp.exceptions.DAOException;
import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.service.CheckingAccount;

public class SaveAccountTest {
	
	
	@Test
	public void takeBankFromDB() throws DAOException {

		Bank bank = new BankDAOImpl().getBankByName("PKO");
		
		assertEquals("PKO", bank.getName());

	}
	
	@Test
	public void loadClientByNameTest() throws DAOException {

		Bank tempBank = new Bank(1,"doj");
		String name = "andrzej duda";
		Client client = new ClientDAOImpl().findClientByName(tempBank, name);
        assertEquals(name, client.getName());        
	}
	
	@Test
    public void saveAccountTest() {
        ClientDAO clientDAO = new ClientDAOImpl();
        Client client = null;
        Bank tempBank = new Bank(1,"doj");
        
        try {
            client = clientDAO.findClientByName(tempBank, "andrzej duda");

            System.out.print("Loaded client: ");
            System.out.println(client);

        } catch (DAOException e) {
            e.printStackTrace();
        }

        int clientsAccountsCounter = client.getAccounts().size();

        float initialBalance = 999;
        float initialOverdraft = 90;
        Account account = new CheckingAccount("kontoDoTestow4", "c", 44,
        		                              1, false, initialBalance, initialOverdraft);
       // account.setBankId(1);
       // account.setClientId(client.getId());
       // int expectedAccNumber = new AccountDAO(). 
       
		

        AccountDAO accountDAO = new AccountDAOImpl();
        try {
            accountDAO.add(account);
        } catch (DAOException e) {
            e.printStackTrace();
        }

        Client clientLoadedFromDB = new Client(null);
        try {
            clientLoadedFromDB = clientDAO.findClientByName(tempBank, "andrzej duda");

            System.out.print("Loaded client:");
            System.out.println(clientLoadedFromDB);

        } catch (DAOException e) {
            e.printStackTrace();
        }

//        int accountsCountAfterSavingNewAccount = client.getAccounts().size();
//        assertEquals(clientsAccountsCount, accountsCountAfterSavingNewAccount-1);

           
        assertEquals(clientsAccountsCounter + 1, clientLoadedFromDB.getAccounts().size());
    }
}
