package org.academiadecodigo.javabank.services;

import org.academiadecodigo.javabank.persistence.model.account.Account;
import org.academiadecodigo.javabank.persistence.TransactionException;
import org.academiadecodigo.javabank.persistence.dao.AccountDao;

import javax.transaction.Transactional;
import java.util.Optional;

/**
 * An {@link AccountService} implementation
 */
public class AccountServiceImpl implements AccountService {

    private AccountDao accountDao;

    /**
     * Sets the account data access object
     *
     * @param accountDao the account DAO to set
     */
    @Transactional
    public void setAccountDao(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    /**
     * Sets the transaction manager
     *
     * @param tx the transaction manager to set
     */


    /**
     * @see AccountService#get(Integer)
     */
    @Transactional

    @Override
    public Account get(Integer id) {

        return accountDao.findById(id);

    }

    /**
     * @see AccountService#add(Account)
     */
    @Transactional

    @Override
    public Integer add(Account account) {

        Integer id = null;
        id = accountDao.saveOrUpdate(account).getId();
        return id;
    }

    /**
     * @see AccountService#deposit(Integer, double)
     */
    @Transactional

    @Override
    public void deposit(Integer id, double amount) {


        Optional<Account> accountOptional = Optional.ofNullable(accountDao.findById(id));

        accountOptional.orElseThrow(() -> new IllegalArgumentException("invalid account id"))
                .credit(amount);

        accountDao.saveOrUpdate(accountOptional.get());

    }

    /**
     * @see AccountService#withdraw(Integer, double)
     */
    @Transactional

    @Override
    public void withdraw(Integer id, double amount) {


        Optional<Account> accountOptional = Optional.ofNullable(accountDao.findById(id));


        accountOptional.orElseThrow(() -> new IllegalArgumentException("invalid account id"))
                .debit(amount);

        accountDao.saveOrUpdate(accountOptional.get());

    }

    /**
     * @see AccountService#transfer(Integer, Integer, double)
     */
    @Transactional

    @Override
    public void transfer(Integer srcId, Integer dstId, double amount) {


        Optional<Account> srcAccount = Optional.ofNullable(accountDao.findById(srcId));
        Optional<Account> dstAccount = Optional.ofNullable(accountDao.findById(dstId));


        srcAccount.orElseThrow(() -> new IllegalArgumentException("invalid account id"));
        dstAccount.orElseThrow(() -> new IllegalArgumentException("invalid account id"));

        if (srcAccount.get().canDebit(amount) && dstAccount.get().canCredit(amount)) {
            srcAccount.get().debit(amount);
            dstAccount.get().credit(amount);
        }

        accountDao.saveOrUpdate(srcAccount.get());
        accountDao.saveOrUpdate(dstAccount.get());


    }
}


