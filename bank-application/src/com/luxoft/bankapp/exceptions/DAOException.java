package com.luxoft.bankapp.exceptions;

import java.sql.SQLException;

@SuppressWarnings("serial")
public class DAOException extends SQLException {
	
	private static long serialVersionUID = 1L;
	
	@Override
    public String getMessage() {
		return "Sthg went wrong when it comes to database handling";
	}
}
