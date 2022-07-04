package org.academiadecodigo.javabank.service;

import org.academiadecodigo.javabank.factories.AccountFactory;
import org.academiadecodigo.javabank.model.Bank;
import org.academiadecodigo.javabank.model.account.Account;

import java.util.Map;

public class AccountManagerService implements AccountService{

    private AccountFactory accountFactory;
    private Map<Integer, Account> accountMap;
    public Bank bank;


    @Override
    public Account add(Account account) {
        Account newAccount = accountFactory.createAccount(account);
        accountMap.put(newAccount.getId(), newAccount);
        return newAccount;
    }

    @Override
    public void deposit(int id, double amount) {
        accountMap.get(id).credit(amount);


    }

    @Override
    public void withdraw(int id, double amount) {
        Account account = accountMap.get(id);
        //Account account = bank.getCustomers().get(id);

        if (!account.canWithdraw()) {
            return;
        }

        accountMap.get(id).debit(amount);
    }

    @Override
    public void transfer(int srcId, int dstId, double amount) {
        Account srcAccount = accountMap.get(srcId);
        Account dstAccount = accountMap.get(dstId);

        // make sure transaction can be performed
        if (srcAccount.canDebit(amount) && dstAccount.canCredit(amount)) {
            srcAccount.debit(amount);
            dstAccount.credit(amount);
        }
    }

}
