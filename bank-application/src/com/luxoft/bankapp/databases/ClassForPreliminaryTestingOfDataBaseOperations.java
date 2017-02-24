package com.luxoft.bankapp.databases;

import java.util.*;
import java.sql.*;

import com.luxoft.bankapp.exceptions.DAOException;
import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.model.Gender;
import com.luxoft.bankapp.userinteractiontools.BankCommander;

public class ClassForPreliminaryTestingOfDataBaseOperations extends BaseDAOImpl {
	
	public static class Banks_And_ClientsFiller {
		int id;
        int bank_id;
        int client_id;
        
        public Banks_And_ClientsFiller(int id, int bank_id, int client_id) {
            this.id = id;
            this.bank_id = bank_id;
            this.client_id = client_id;
        }
    }
	
	public void fillBanks_And_ClientsTable(List <Banks_And_ClientsFiller> values) throws SQLException {
        final String sql = "INSERT INTO Banks_And_Clients values (?, ?, ?)";

        final PreparedStatement stmt = openConnection().prepareStatement(sql);
        try {
            for (Banks_And_ClientsFiller n: values) {
                stmt.setInt(1, n.id);
                stmt.setInt(2, n.bank_id);
                stmt.setInt(3, n.client_id);
                stmt.addBatch();
            }

            final int[] results = stmt.executeBatch();
            for (int i = 0; i < results.length; i++) {
                if (results[i] == Statement.EXECUTE_FAILED) {
                    System.out.println("Batch #" + i + " - Failed.");
                } else if (results[i] == Statement.SUCCESS_NO_INFO) {
                    System.out.println("Batch #" + i + " - Succeded with no results.");
                } else {
                    System.out.println("Batch #" + i + " - Affected " + results[i] + " rows.");
                }
            }

        } finally {
            stmt.close();
        }
    }
	
	//daoObject.save(BankCommander.currentBank);
	
    public void insertAllClientsToDataBase() throws Exception {
	ClientDAOImpl clientDaoObject = new ClientDAOImpl();
	
	for(Client client : BankCommander.currentBank.getClients())
	{
	//	if (client.getName().equalsIgnoreCase("adam malysz")) {continue;}   //pamiêtaj ch³oie ¿eby usun¹æ tê liniê!
	   clientDaoObject.save(client);
//		String sqlCommand = "insert into clients values(?,?,?,?,?,?)";
//
//		PreparedStatement ps = openConnection().prepareStatement(sqlCommand);
//		// 	public Client(1int id,2String name, 3 Gender gender, 4 String email,5String phoneNumber, String city) {
//		ps.setInt(1, client.getId());
//		ps.setString(2, client.getName());
//
//		if (client.getClientSalutation().equalsIgnoreCase("Mr")) { ps.setString(3, "Mr");}
//		else if (client.getClientSalutation().equalsIgnoreCase("Mrs")) { ps.setString(3, "Mrs");}
//
//		ps.setString(4, client.getEmailAddress());
//		ps.setLong(5, Long.parseLong(client.getPhoneNumber()));
//		ps.setString(6, client.getCity());
//		ps.executeQuery();
//		closeConnection();
	}

} //end of function
    
    public void insertAllAccountsToDataBase() throws Exception {
    	for (Client c : BankCommander.currentBank.getClients())
    	{
    		for (Account a : c.getAccounts())
    		{
    			new AccountDAOImpl().add(a);
    		}
    	}
    }
    
    
public List<Client> getAllClients(Bank bank) throws DAOException {
		
		String sql = "SELECT * from clients join Banks_And_Clients"
				+ " on clients.id=Banks_And_Clients.client_id "
				+ "where Banks_And_Clients.bank_id=1;";
		List<Client> lista = new LinkedList<>();
		try {
			bank.setId(1);
			PreparedStatement ps = openConnection().prepareStatement(sql);
			ps.setInt(1, bank.getId());
			
			ResultSet rs = ps.executeQuery();
			System.out.println("executed query");
			while (rs.next()) {
				Client c;
				System.out.println("**************"+rs.getString(3));
				if (rs.getString(3).equalsIgnoreCase("Mr") | rs.getString(3).equalsIgnoreCase("Mr.")) {
					c = new Client(rs.getInt(9), rs.getString(2), Gender.MALE, rs.getString(4), rs.getString(5),
							rs.getString("phone"));
				} else {
					c = new Client(rs.getInt(9), rs.getString(2), Gender.FEMALE, rs.getString(4), rs.getString(5),
							rs.getString(6));
				}
				System.out.println(lista.add(c));System.out.println("read client from base and inserted to list on fly");
			}
		
		} catch (SQLException e) {
		} finally {
			closeConnection();
		}
		return lista;
	}
    
    
    
    public static void main(String[] args)  throws Exception {
    	//new BankDAOImpl().save(BankCommander.currentBank);
    	System.out.println("zaczynamy");
    	Bank  a = new BankDAOImpl().getBankByName("My Bank");
    	System.out.println(a.getId());
    	a.printReport();
//    	List<Client> listaDoSprawdzeniaCzyDzia³a = new KlasaDoTestówBazDanych().getAllClients(BankCommander.currentBank);
//    	System.out.println("Lista ma wilkeosc "+listaDoSprawdzeniaCzyDzia³a.size());
//    	for (Client k : listaDoSprawdzeniaCzyDzia³a) {
//    		System.out.println(k.getName());
//    	}
    	
//    	BankCommander.initialiseBankWithClients();
//    	System.out.println(BankCommander.currentClient.getActiveAccount().getOwner_Id());
//    	System.out.println("current client is "+BankCommander.currentClient.getName());
    	//daoObject.save(BankCommander.currentBank);                      ******works  odkomentuj tê liniê ch³opie
    	
    	//new KlasaDoTestówBazDanych().insertAllClientsToDataBase();   *********works odkomentuj tê liniê ch³opie
    	
    	
    	
    	//new KlasaDoTestówBazDanych().insertAllAccountsToDataBase();   *********works odkomentuj tê liniê ch³opie
    	
    	Banks_And_ClientsFiller[] names = {
        
//    	new Banks_And_ClientsFiller(1,2,1),
//    	new Banks_And_ClientsFiller(2,3,1),
//    	new Banks_And_ClientsFiller(3,1,2),
//    	new Banks_And_ClientsFiller(4,3,3),
//    	new Banks_And_ClientsFiller(5,1,4),
//    	new Banks_And_ClientsFiller(6,3,4),
//    	new Banks_And_ClientsFiller(7,3,5),
//    	new Banks_And_ClientsFiller(8,1,6),
//    	new Banks_And_ClientsFiller(9,2,7),
//    	new Banks_And_ClientsFiller(10,3,8),
//    	new Banks_And_ClientsFiller(11,3,9),
//    	new Banks_And_ClientsFiller(12,2,9),
    	new Banks_And_ClientsFiller(20,10,39),
    	};          
      // new KlasaDoTestówBazDanych().fillBanks_And_ClientsTable(Arrays.asList(names));  **************works odkomentuj tê liniê ch³opie
	}
}
