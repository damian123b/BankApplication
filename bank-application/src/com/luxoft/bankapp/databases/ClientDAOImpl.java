package com.luxoft.bankapp.databases;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import com.luxoft.bankapp.exceptions.DAOException;
import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.model.Gender;
import com.luxoft.bankapp.userinteractiontools.BankCommander;

public class ClientDAOImpl extends BaseDAOImpl implements ClientDAO {

	Connection conn;

	@Override
	public Client findClientByName(Bank bank, String name) throws DAOException {

		Client client;

		String sql = "SELECT clients.id,clients.name,clients.gender FROM CLIENTS join Banks_And_Clients "
				+ "on Banks_And_Clients.client_id=clients.id where clients.name = ? and Banks_And_Clients.bank_id=?";

		try {

			PreparedStatement ps = openConnection().prepareStatement(sql);
			ps.setString(1, name);
			ps.setInt(2, bank.getId());
			ResultSet rs = ps.executeQuery();			

			while (rs.next()) {
				List<Account> accountsList = new LinkedList<>();

				if (rs.getString("gender").equalsIgnoreCase("Mr.") || rs.getString("gender").equalsIgnoreCase("Mr")) {
					client = new Client(rs.getInt("id"), rs.getString("name"), Gender.MALE,
							"",
							"", // empty strings in attempt to avoid null pointer exceptions at testing of isEqual() method
							"");

					try {
						accountsList = new AccountDAOImpl().getClientAccounts(client.getId());
					} catch (DAOException e) {
						e.printStackTrace();
					}
					for (Account a : accountsList) {
						client.addAccount(a);
						if (a.isActive()) {
							client.setActiveAccount(a);
						}
					}
					BankCommander.currentClient = client;
					return client;
				} else if (rs.getString(3).equalsIgnoreCase("Mrs.") | rs.getString(3).equalsIgnoreCase("Mrs")) {
					client = new Client(rs.getInt(1), rs.getString(2), Gender.FEMALE,
							"",
							"" ,
							"");

					try {
						accountsList = new AccountDAOImpl().getClientAccounts(client.getId());
					} catch (DAOException e) {
						e.printStackTrace();
					}
					for (Account a : accountsList) {
						client.addAccount(a);
						if (a.isActive()) {
							client.setActiveAccount(a);
						}

						BankCommander.currentClient = client;
						return client;
					}
				}

				else { System.err.println("WRONG GENDER");
					return null;
				}
			} // end of try
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException();
		} finally {
			closeConnection();
		}

		return null;

	}

	@Override
	public List<Client> getAllClients(Bank bank) throws DAOException {
		String sql = "SELECT * from clients join Banks_And_Clients" + " on clients.id=Banks_And_Clients.client_id "
				+ "where Banks_And_Clients.bank_id=?;";
		PreparedStatement ps;
		List<Client> lista = new LinkedList<>();
		try {

			ps = openConnection().prepareStatement(sql);
			ps.setInt(1, bank.getId());

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Client c;
				if (rs.getString(3).equalsIgnoreCase("Mr") | rs.getString(3).equalsIgnoreCase("Mr.")) {
					c = new Client(rs.getInt(9), rs.getString(2), Gender.MALE, rs.getString(4), rs.getString(5),
							rs.getString(6));
				} else {
					c = new Client(rs.getInt(9), rs.getString(2), Gender.FEMALE, rs.getString(4), rs.getString(5),
							rs.getString(6));
				}
				lista.add(c);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection();
		}
		return lista;
	}

	@Override
	public void save(Client client) throws DAOException {

		String sqlCommand = "insert into clients (name, gender,city,email,phone) values(?,?,?,?,?)";

		try {
			PreparedStatement ps = openConnection().prepareStatement(sqlCommand);

			ps.setString(1, client.getName());
			if (client.getClientSalutation().equalsIgnoreCase("Mr") ||
					 client.getClientSalutation().equalsIgnoreCase("Mr.")) {
				ps.setString(2, "Mr");
			} else if (client.getClientSalutation().equalsIgnoreCase("Mrs") ||
					 client.getClientSalutation().equalsIgnoreCase("Mrs.")) {
				ps.setString(2, "Mrs");
			}
			ps.setString(3, client.getCity());
			ps.setString(4, client.getEmailAddress());
			ps.setLong(5, Long.parseLong(client.getPhoneNumber()));

			ps.execute();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection();
		}

	}

	public void assignClientToCurrentBank(Client client) throws DAOException {

		String sqlCommand;
		PreparedStatement ps;

		try {
			sqlCommand = "insert into Banks_And_Clients (bank_id, client_id) values (?,?)";

			ps = openConnection().prepareStatement(sqlCommand);
			ps.setInt(1, BankCommander.currentBank.getId());
			ps.setInt(2, client.getId());
			ps.execute();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection();
		}

	}

	public int getClientIdFromDB(Client client) {
		String sqlCommand = "select id from clients where name=?;";
		int idOfClientTakenFromDB = -1;
		try {
			PreparedStatement ps = openConnection().prepareStatement(sqlCommand);
			ps.setString(1, client.getName());
			System.out.println("select id from...query text should be printed here: " + ps.toString());
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				idOfClientTakenFromDB = rs.getInt(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection();
		}
		return idOfClientTakenFromDB;
	}

	public void updateAccount(int account_id, float amount) throws DAOException {
		String sqlCommand = "update Accounts set balance=? where id=? ";
		try {
			PreparedStatement ps = openConnection().prepareStatement(sqlCommand);
			ps.setFloat(1, amount);
			ps.setInt(2, account_id);
			ps.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection();
		}
	}

	@Override
	public void remove(Client client) throws DAOException {
		openConnection();
		String sqlCommand = "delete from clients where id=?";
		try {
			PreparedStatement ps = conn.prepareStatement(sqlCommand);
			ps.setInt(1, client.getId());
			ps.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection();
		}

	}

}
