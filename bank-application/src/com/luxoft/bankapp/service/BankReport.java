package com.luxoft.bankapp.service;

import com.luxoft.bankapp.model.*;

public interface BankReport {

int getNumberOfClients(Bank bank);
void getAccountsNumber(Bank bank);
void getClientsSorted(Bank bank);
float getBankCreditSum(Bank bank);
void getClientsByCity(Bank bank);
}
