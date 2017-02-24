package test.service;

import static org.junit.Assert.assertTrue;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;

import org.junit.Before;
import org.junit.Test;

import com.luxoft.bankapp.databases.BankDAOImpl;
import com.luxoft.bankapp.databases.ClientDAOImpl;
import com.luxoft.bankapp.exceptions.DAOException;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.model.Gender;
import com.luxoft.bankapp.service.CheckingAccount;
import com.luxoft.bankapp.service.SavingAccount;
import com.luxoft.bankapp.userinteractiontools.BankCommander;

public class DatabaseOperationsTest {

	Bank bank1, bank2, bank8;
  @Before
  public void initBanks () {
	
    bank1 = new Bank (74, "name of thebank");
   Client c1 = new Client("Ivan Ivanov");
   c1.setGender(Gender.MALE);
   c1.setCity("Kiev");
   c1.addAccount(new CheckingAccount());
   bank1.addClient(c1);  
  
   //bank1.printReport();	   
  
}

  @Test          //PASSED!
  
  public void testInsert () throws DAOException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IntrospectionException {
     
	 // BankDAOImpl().remove(bank1);
	  
	 // new BankDAOImpl().save(bank1);
      Bank bank7 = new Bank(81,"bank number 7");
      new BankDAOImpl().save(bank7);
      bank7.printReport();
       
      Bank bank12 = new BankDAOImpl().getBankByName("bank number 7");
     
      System.out.println("********");
      bank12.printReport();
      assertTrue(TestService.isEqual(bank7, bank12));
   }

  @Test  //this one crashes with NullPointerException
  public void testUpdate() throws DAOException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IntrospectionException{
	  Bank bank38 = new Bank (38, "bank 38");
	  new BankDAOImpl().save(bank38);
	
	Client client2 = new Client ("Ivana Petrova");
	client2.setGender(Gender.FEMALE);
    client2.setCity ("New York");
    client2.setEmailAddress("k@k.k");
    client2.setPhoneNumber("34545556");
    client2.addAccount(new SavingAccount ());
    bank38.addClient(client2);
   new ClientDAOImpl().save(client2);
    BankCommander.currentBank = bank38;
    
    client2.setId(new ClientDAOImpl().getClientIdFromDB(client2));
    new ClientDAOImpl().assignClientToCurrentBank(client2);
 //   Bank bank2 = new BankDAOImpl().getBankByName("name of the bank");
   
  Client client3 = new ClientDAOImpl().findClientByName(bank38, "Ivana Petrova");
  
  System.out.println("************");
  System.out.println("is he null? "+(client3==null));
 System.out.println("***********");
 System.out.println(client2.toString());
 System.out.println("**********");
 System.out.println(client3.toString());
  assertTrue(TestService.isEqual(client2, client3));
   }

}
