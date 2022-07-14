package org.academiadecodigo.javabank.services;

import org.academiadecodigo.javabank.model.account.AbstractAccount;
import org.academiadecodigo.javabank.model.account.Account;
import org.academiadecodigo.javabank.persistence.SessionManager;
import org.academiadecodigo.javabank.persistence.TransactionManager;

import java.util.List;

/**
 * Common interface for account services, provides methods to manage accounts and perform account transactions
 */
public interface AccountService {


     void setTm(TransactionManager tm);

     void setSm(SessionManager sm);

    /**
     * Gets a list of accounts
     *
     * @return the account list
     */
    List<Account> list();

    /**
     * Gets the account
     *
     * @param id the account id
     * @return an account
     */
    Account get(Integer id);

    /**
     * Saves the account
     *
     * @param account to be saved
     * @return the saved account
     */
    Account save(Account account);

    /**
     * Deletes as account
     *
     * @param id the id of the account to be deleted
     */
    void delete(Integer id);

    /**
     * Perform an {@link AbstractAccount} deposit
     *
     * @param id     the id of the account
     * @param amount the amount to deposit
     */
    void deposit(Integer id, double amount);

    /**
     * Perform an {@link AbstractAccount} withdrawal
     *
     * @param id     the id of the account
     * @param amount the amount to withdraw
     */
    void withdraw(Integer id, double amount);

    /**
     * Performs a transfer between two {@link AbstractAccount} if possible
     *
     * @param srcId  the source account id
     * @param dstId  the destination account id
     * @param amount the amount to transfer
     */
    void transfer(Integer srcId, Integer dstId, double amount);

}
