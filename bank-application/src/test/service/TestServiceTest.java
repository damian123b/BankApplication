package test.service;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertTrue;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;

import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;

public class TestServiceTest {
	
	 Bank bank1, bank2, bank3;
	 
	 @Before
     public void initBanks () {
           bank1 = new Bank(8,"My Bank from test class");
           Client client = new Client("Ivan Ivanov");
 
           client.setCity ("Kiev");
           client.setNickname("nickname1"); 
          // client.addAccount (new CheckingAccount ());
          // bank1.addClient (client);
           
           bank3 = new Bank(12,"My Bank from test class");
           
           bank2 = new Bank (9, "Second bank from test class");
           Client client2 = new Client ("Ivan Ivanov");
           client2.setCity ("Kiev");
          // client2.addAccount (new CheckingAccount ());
          // bank2.addClient(client2);
     }
	 
	   @Test
	      public void testEquals () throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, IntrospectionException {
		  assertTrue(TestService.isEqual(bank1, bank3));
	      }
}
