package org.academiadecodigo.javabank.service;

import org.academiadecodigo.javabank.model.account.Account;
import org.academiadecodigo.javabank.model.account.AccountType;

public interface AccountService {

    public Account add(AccountType account);
    public void deposit(int id, double amount);
    public void withdraw(int id, double amount);
    public void transfer(int srcId, int dstId, double amount);
}