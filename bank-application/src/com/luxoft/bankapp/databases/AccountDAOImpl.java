package com.luxoft.bankapp.databases;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import com.luxoft.bankapp.exceptions.DAOException;
import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.service.CheckingAccount;
import com.luxoft.bankapp.service.SavingAccount;
import com.luxoft.bankapp.userinteractiontools.BankCommander;

public class AccountDAOImpl extends BaseDAOImpl implements AccountDAO{

	@Override
	public void save(Account account) throws DAOException {              
		String sqlCommand = "update accounts set balance=? where id=?";
		PreparedStatement ps;
		try {
			ps = openConnection().prepareStatement(sqlCommand);
			ps.setFloat(1, account.getBalance());
			ps.setInt(2, account.getId());
			System.out.println("ps.tostring()= "+ps.toString()); //dynamic testing :P
			ps.executeUpdate();
		}
		catch(SQLException  e) {
			e.printStackTrace();
		}
		finally {
			closeConnection();
		}
		
	}

	@Override
	public void add(Account account) throws DAOException {
		
		try {
			
			String	sqlCommand = "insert into accounts (name, type, owner_id, bank_id,is_active,balance,overdraft) "
					+ "values(?,?,?,?,?,?,?);";
			PreparedStatement ps = openConnection().prepareStatement(sqlCommand);
			ps.setString(1, account.getName());
				
			if (account.getClass().getSimpleName().equalsIgnoreCase("SavingAccount")) {
				ps.setString(2,"s");
				ps.setFloat(7, 0F);
			}
			else if (account.getClass().getSimpleName().equalsIgnoreCase("CheckingAccount")) {
				ps.setString(2,"c");
				ps.setFloat(7, account.getOverdraft());
			}	
			else { 
				System.err.println("Type of account is neither saving nor checking"); 
				return;
			}
			ps.setInt(3, account.getOwner_Id());                                  //owner_id (liczba int)
			ps.setInt(4, account.getBank_Id());                                   // bank_id
			ps.setBoolean(5, account.isActive());                                  
			ps.setFloat(6, account.getBalance());                                 // balance
			System.out.println(ps.toString());
			ps.execute();
			
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			throw new DAOException();
		}
		finally {
			closeConnection();
		}
	}

	@Override
	public void removeByClientId(int idClient) throws DAOException {
		String sqlCommand = "delete from accounts where owner_id=?";
		try {
			PreparedStatement ps = openConnection().prepareStatement(sqlCommand);
			ps.setInt(1, idClient);
			ps.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			closeConnection();
		}
		
	}

	@Override
	public List<Account> getClientAccounts(int idClient) throws DAOException {
		List <Account> lista = new LinkedList<>(); 
		Account a = null;
		try {
			
			String sqlCommand ="select * from accounts where owner_id =?";
			PreparedStatement ps = openConnection().prepareStatement(sqlCommand);
			ps.setInt(1, idClient);
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				
				if(rs.getString(3).equalsIgnoreCase("s"))
			    { a = new SavingAccount(rs.getInt("ID"), rs.getString(2), rs.getString(3), rs.getInt(4), 
			    		                rs.getInt(5), rs.getBoolean(6), rs.getFloat(7));  }
				
				else if (rs.getString(3).equalsIgnoreCase("c"))
				{ a = new CheckingAccount(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), 
		                                  rs.getInt(5), rs.getBoolean(6), rs.getFloat(7), rs.getFloat(8));  }
				lista.add(a);
			}
			return Collections.unmodifiableList(lista);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		finally {
			closeConnection();
		}
		return lista;		
	}

	public int getAccountIdFromDB(Account account) {
		String sqlCommand = "select id from accounts where name=?;";
		int idOfAccountTakenFromDB = -1;
		try {
			PreparedStatement ps = openConnection().prepareStatement(sqlCommand);
			ps.setString(1, account.getName());
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				idOfAccountTakenFromDB = rs.getInt(1);
				System.out.println("selecting id from DB... should be printed here: "+idOfAccountTakenFromDB);

			}				
		}
		catch(SQLException e ) {
			e.printStackTrace();
		}
		finally {
			closeConnection();
		}
		return idOfAccountTakenFromDB;
	}
	
	public void markAccountAsActiveInDB(Account account) throws DAOException {   
		String sqlCommand = "update accounts set is_active=true where id=?";
		PreparedStatement ps;
		try {
			ps = openConnection().prepareStatement(sqlCommand);
			ps.setInt(1, (int) BankCommander.currentClient.getActiveAccount().getId());
			ps.execute();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			closeConnection();
		}
		
	}
}
