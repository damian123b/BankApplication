package com.luxoft.bankapp.databases;

import java.sql.*;
import com.luxoft.bankapp.exceptions.DAOException;

public interface BaseDAO {

	Connection openConnection() throws DAOException;
	
	Connection closeConnection();
}
