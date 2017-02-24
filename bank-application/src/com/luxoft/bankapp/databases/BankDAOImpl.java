package com.luxoft.bankapp.databases;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.luxoft.bankapp.exceptions.BankNotFoundException;
import com.luxoft.bankapp.exceptions.DAOException;
import com.luxoft.bankapp.model.*;
import com.luxoft.bankapp.userinteractiontools.BankCommander;

public class BankDAOImpl extends BaseDAOImpl implements BankDAO {

	Connection conn;

	public Bank getBankByName(String name) throws DAOException { // aka loadBank() :)
																	

		Bank bankFound = null;
		String sql = "SELECT ID, NAME FROM BANKS WHERE name=?";
		PreparedStatement stmt;
		try {
			stmt = openConnection().prepareStatement(sql);
			stmt.setString(1, name);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {

				bankFound = new Bank(rs.getInt(1), rs.getString(2));
				BankCommander.currentBank = bankFound;
				System.out.println("found bank " + name + " in DB");

				// List<Client> listOfClientsTakenFromDB = new
				// ClientDAOImpl().getAllClients(BankCommander.currentBank);
				// System.out.println("Downloading list of clients in this banks
				// from DB - done!");

				// for (Client c : listOfClientsTakenFromDB) {
				// BankCommander.currentBank.addClient(c);
				// System.out.println("Added client "+c.getName()+" to bank");
				// List<Account> listOfAccountsTakenFromDB = new
				// AccountDAOImpl().getClientAccounts(c.getId());
				// System.out.println("downloading list of cleints from db -
				// done!");
				// for(Account a : listOfAccountsTakenFromDB) {
				// c.addAccount(a); System.out.println("Adding ccunts to
				// client");
				// }
				// }

				return bankFound;
			} else {
				System.out.println("Bank not found.");
				throw new BankNotFoundException(name);
			}
		} // end of try
		catch (BankNotFoundException e) {
			e.getMessage();
		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			closeConnection();
		}
		return BankCommander.currentBank;
	}

	@Override
	public void save(Bank bank) throws DAOException {

		String sql = "insert into banks values(?,?)";
		try {
			PreparedStatement ps;
			ps = openConnection().prepareStatement(sql);
			ps.setInt(1, bank.getId());
			ps.setString(2, bank.getName());
			ps.executeUpdate();
		} 
		  catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection();
		}

	}

	@Override
	public void remove(Bank bank) throws DAOException {
	
		String sql = "delete from banks where id=?";
		try {
			PreparedStatement ps;
			ps = openConnection().prepareStatement(sql);
			ps.setInt(1, bank.getId());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection();
		}
	}

}
