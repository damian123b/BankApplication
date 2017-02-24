package com.luxoft.bankapp.service;

import java.util.Comparator;
import com.luxoft.bankapp.model.Client;

public class ClientComparatorByName implements Comparator<Client> {

	@Override
	public int compare(Client c1, Client c2) {
		
		 if (c1.getName().equals(c2.getName())) return 0;
		 else if (c1.getName().compareTo(c2.getName()) > 0) return 1;
		 else return -1;
	}

}
