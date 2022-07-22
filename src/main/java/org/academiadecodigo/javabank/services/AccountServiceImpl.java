package org.academiadecodigo.javabank.services;

import org.academiadecodigo.javabank.persistence.model.account.Account;
<<<<<<< HEAD
import org.academiadecodigo.javabank.persistence.TransactionException;
=======
>>>>>>> 9d2388ea9e1e59c86ff6170772ac68772bd2c651
import org.academiadecodigo.javabank.persistence.dao.AccountDao;
import org.springframework.transaction.annotation.Transactional;

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
<<<<<<< HEAD
     * Sets the transaction manager
     *
     * @param tx the transaction manager to set
     */


    /**
=======
>>>>>>> 9d2388ea9e1e59c86ff6170772ac68772bd2c651
     * @see AccountService#get(Integer)
     */
    @Transactional

    @Override
    public Account get(Integer id) {
<<<<<<< HEAD

        return accountDao.findById(id);

=======
        return accountDao.findById(id);
>>>>>>> 9d2388ea9e1e59c86ff6170772ac68772bd2c651
    }

    /**
     * @see AccountService#add(Account)
     */
    @Transactional
<<<<<<< HEAD

    @Override
    public Integer add(Account account) {

        Integer id = null;
        id = accountDao.saveOrUpdate(account).getId();
        return id;
=======
    @Override
    public Integer add(Account account) {
        return accountDao.saveOrUpdate(account).getId();
>>>>>>> 9d2388ea9e1e59c86ff6170772ac68772bd2c651
    }

    /**
     * @see AccountService#deposit(Integer, double)
     */
    @Transactional
<<<<<<< HEAD

    @Override
    public void deposit(Integer id, double amount) {


        Optional<Account> accountOptional = Optional.ofNullable(accountDao.findById(id));

        accountOptional.orElseThrow(() -> new IllegalArgumentException("invalid account id"))
                .credit(amount);

        accountDao.saveOrUpdate(accountOptional.get());

=======
    @Override
    public void deposit(Integer id, double amount) {

        Optional<Account> accountOptional = Optional.ofNullable(accountDao.findById(id));

        accountOptional.orElseThrow(() -> new IllegalArgumentException("invalid account id")).credit(amount);

        accountDao.saveOrUpdate(accountOptional.get());
>>>>>>> 9d2388ea9e1e59c86ff6170772ac68772bd2c651
    }

    /**
     * @see AccountService#withdraw(Integer, double)
     */
    @Transactional
<<<<<<< HEAD

    @Override
    public void withdraw(Integer id, double amount) {


        Optional<Account> accountOptional = Optional.ofNullable(accountDao.findById(id));


        accountOptional.orElseThrow(() -> new IllegalArgumentException("invalid account id"))
                .debit(amount);

        accountDao.saveOrUpdate(accountOptional.get());

=======
    @Override
    public void withdraw(Integer id, double amount) {

        Account account = Optional.ofNullable(accountDao.findById(id))
                .orElseThrow(() -> new IllegalArgumentException("invalid account id"));

        if (!account.canWithdraw()) {
            throw new IllegalArgumentException("invalid account type");
        }

        account.debit(amount);

        accountDao.saveOrUpdate(account);
>>>>>>> 9d2388ea9e1e59c86ff6170772ac68772bd2c651
    }

    /**
     * @see AccountService#transfer(Integer, Integer, double)
     */
    @Transactional
<<<<<<< HEAD

    @Override
    public void transfer(Integer srcId, Integer dstId, double amount) {


        Optional<Account> srcAccount = Optional.ofNullable(accountDao.findById(srcId));
        Optional<Account> dstAccount = Optional.ofNullable(accountDao.findById(dstId));

=======
    @Override
    public void transfer(Integer srcId, Integer dstId, double amount) {

        Optional<Account> srcAccount = Optional.ofNullable(accountDao.findById(srcId));
        Optional<Account> dstAccount = Optional.ofNullable(accountDao.findById(dstId));
>>>>>>> 9d2388ea9e1e59c86ff6170772ac68772bd2c651

        srcAccount.orElseThrow(() -> new IllegalArgumentException("invalid account id"));
        dstAccount.orElseThrow(() -> new IllegalArgumentException("invalid account id"));

<<<<<<< HEAD
=======
        // make sure transaction can be performed
>>>>>>> 9d2388ea9e1e59c86ff6170772ac68772bd2c651
        if (srcAccount.get().canDebit(amount) && dstAccount.get().canCredit(amount)) {
            srcAccount.get().debit(amount);
            dstAccount.get().credit(amount);
        }

        accountDao.saveOrUpdate(srcAccount.get());
        accountDao.saveOrUpdate(dstAccount.get());
<<<<<<< HEAD


=======
>>>>>>> 9d2388ea9e1e59c86ff6170772ac68772bd2c651
    }
}


