package com.luxoft.bankapp.databases;

import java.sql.*;
import com.luxoft.bankapp.exceptions.DAOException;

public class BaseDAOImpl implements BaseDAO {

	Connection conn;
	@Override
	public Connection openConnection() throws DAOException {
			  
       try {
		Class.forName("org.h2.Driver"); // this is driver for H2
		
        //conn = DriverManager.getConnection("jdbc:h2:~/bank", URL given by Vladimir
		conn = DriverManager.getConnection("jdbc:h2:tcp://localhost:9092/~/bank",
             "sa", // login
             "" // password
             );
   //  jdbc:h2:tcp://localhost:9092/~/test this enables us to test programm and have database running at same time
		return conn;
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			throw new DAOException();
		}
		
	}

	//preferably my code from jRunner should be used to create and populate DB
	public void initialiseDatabase() throws DAOException {
		openConnection();
		String createTableforBanks = "	create table Banks (id int AUTO_INCREMENT not null,"
				+ "name varchar(100) unique,"
				+ "primary key(id) );";
		String createTableForClients ="create table Clients(id int AUTO_INCREMENT,"
				+ "name varchar(200) unique not null,"
				+ "gender varchar(4), "
				+ "city varchar(100),"
				+ "email varchar(150),"
				+ " phone bigint,"
				+ "primary key(id));";
		String createManyToManyTable = "create table Banks_And_Clients (id int primary key, bank_id int, client_id int,"
				+ " foreign key (bank_id) references banks(id),"
				+ " foreign key (client_id) references clients(id) );";
		String createTableForAccounts = "create table Accounts (id int auto_increment,"
				+ "name  varchar(130) unique not null,"
				+ "type char(1) not null check type like 'c' or type like 's',"
				+ "owner_id int not null,"
				+ "bank_id int not null,"
				+ "is_active bool not null,"
				+ "balance decimal(7,2),"
				+ "overdraft decimal (5,2),"
				+ "primary key(id),"
				+ "foreign key (owner_id) references clients(id),"
				+ "foreign key (bank_id) references banks(id));";
		 try {
			PreparedStatement ps = conn.prepareStatement(createTableforBanks);
			 ps.executeQuery();
			 ps = conn.prepareStatement(createTableForClients);
			 ps.executeQuery();
			 ps = conn.prepareStatement(createManyToManyTable);
			 ps.executeQuery();
			 ps = conn.prepareStatement(createTableForAccounts);
			 ps.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		 finally {
			 closeConnection();
		 }
		
	}
	@Override
	public Connection closeConnection() {
		
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
