package org.academiadecodigo.javabank.services.mock;

import org.academiadecodigo.javabank.model.account.Account;
import org.academiadecodigo.javabank.persistence.JpaSessionManager;
import org.academiadecodigo.javabank.persistence.JpaTransactionManager;
import org.academiadecodigo.javabank.services.AccountService;

import java.util.*;

/**
 * A mock {@link AccountService} implementation
 */
public class MockAccountService implements AccountService {

    protected Map<Integer, Account> accountMap = new HashMap<>();

    /**
     * Gets the next account id
     *
     * @return the account id
     */
    private Integer getNextId() {
        return accountMap.isEmpty() ? 1 : Collections.max(accountMap.keySet()) + 1;
    }


    public void setTm(JpaTransactionManager tm) {

    }

    public void setSm(JpaSessionManager sm) {

    }

    /**
     * @see AccountService#list()
     */
    @Override
    public List<Account> list() {
        return new ArrayList<>(accountMap.values());
    }

    /**
     * @see AccountService#get(Integer)
     */
    @Override
    public Account get(Integer id) {
        return accountMap.get(id);
    }

    /**
     * @see AccountService#save(Account)
     */
    @Override
    public Account save(Account account) {

        if (account.getId() == null) {
            account.setId(getNextId());
        }

        accountMap.put(account.getId(), account);

        return account;

    }

    /**
     * @see AccountService#delete(Integer)
     */
    @Override
    public void delete(Integer id) {
        accountMap.remove(id);
    }

    /**
     * @see AccountService#deposit(Integer, double)
     */
    public void deposit(Integer id, double amount) {
        accountMap.get(id).credit(amount);
    }

    /**
     * @see AccountService#withdraw(Integer, double)
     */
    public void withdraw(Integer id, double amount) {

        Account account = accountMap.get(id);

        if (!account.canWithdraw()) {
            return;
        }

        accountMap.get(id).debit(amount);
    }

    /**
     * @see AccountService#transfer(Integer, Integer, double)
     */
    public void transfer(Integer srcId, Integer dstId, double amount) {

        Account srcAccount = accountMap.get(srcId);
        Account dstAccount = accountMap.get(dstId);

        // make sure transaction can be performed
        if (srcAccount.canDebit(amount) && dstAccount.canCredit(amount)) {
            srcAccount.debit(amount);
            dstAccount.credit(amount);
        }
    }

    @Override
    public void setTM(JpaTransactionManager jtm) {

    }
}
