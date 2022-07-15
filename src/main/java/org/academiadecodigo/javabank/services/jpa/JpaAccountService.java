package org.academiadecodigo.javabank.services.jpa;

import org.academiadecodigo.javabank.model.account.AbstractAccount;
import org.academiadecodigo.javabank.model.account.Account;
import org.academiadecodigo.javabank.persistence.JpaSessionManager;
import org.academiadecodigo.javabank.persistence.JpaTransactionManager;
import org.academiadecodigo.javabank.services.AccountService;

import javax.persistence.RollbackException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

/**
 * A JPA {@link AccountService} implementation
 */
public class JpaAccountService implements AccountService {


    private JpaTransactionManager tm ;

    private Class<Account> accountClass;

    public JpaAccountService(  ) {


        accountClass = Account.class;
    }




    /**
     * @see AccountService#list()
     */
    public List<Account> list() {


        try {
            tm.beginRead();

            CriteriaQuery<Account> criteriaQuery = tm.getEm().getCriteriaBuilder().createQuery(accountClass);
            Root<Account> root = criteriaQuery.from(accountClass);
            return tm.getEm().createQuery(criteriaQuery).getResultList();

        } finally {
            if (tm.getEm() != null) {
                tm.getEm().close();
            }
        }
    }

    /**
     * @see AccountService#get(Integer)
     */
    public Account get(Integer id) {


        try {

            return tm.getEm().find(accountClass, id);


        } finally {
            if (tm.getEm() != null) {
                tm.getEm().close();
            }
        }
    }

    /**
     * @see AccountService#save(Account)
     */
    public Account save(Account account) {


        try {

            tm.getEm().getTransaction().begin();
            Account savedObject = tm.getEm().merge(account);
            tm.getEm().getTransaction().commit();

            return savedObject;

        } catch (RollbackException ex) {

            tm.getEm().getTransaction().rollback();
            return null;

        } finally {
            if (tm.getEm()  != null) {
                tm.getEm().close();
            }
        }
    }

    /**
     * @see AccountService#delete(Integer)
     */
    public void delete(Integer id) {


        try {

            tm.getEm().getTransaction().begin();
            tm.getEm() .remove(tm.getEm().find(accountClass, id));
            tm.getEm().getTransaction().commit();

        } catch (RollbackException ex) {

            tm.getEm().getTransaction().rollback();

        } finally {
            if (tm.getEm()  != null) {
                tm.getEm().close();
            }
        }
    }

    /**
     * @see AccountService#deposit(Integer, double)
     */
    public void deposit(Integer id, double amount) {


        try {

            tm.getEm().getTransaction().begin();

            Optional<AbstractAccount> account = Optional.ofNullable(tm.getEm() .find(AbstractAccount.class, id));

            if (!account.isPresent()) {
                tm.getEm().getTransaction().rollback();
            }

            account.orElseThrow(() -> new IllegalArgumentException("invalid account id")).credit(amount);

            tm.getEm().getTransaction().commit();

        } catch (RollbackException ex) {

            tm.getEm().getTransaction().rollback();

        } finally {

            if (tm.getEm() != null) {
                tm.getEm().close();
            }
        }
    }

    /**
     * @see AccountService#withdraw(Integer, double)
     */
    @Override
    public void withdraw(Integer id, double amount) {


        try {

            tm.getEm().getTransaction().begin();

            Optional<AbstractAccount> account = Optional.ofNullable(tm.getEm().find(AbstractAccount.class, id));

            if (!account.isPresent()) {
                tm.getEm().getTransaction().rollback();
            }

            account.orElseThrow(() -> new IllegalArgumentException("invalid account id")).debit(amount);

            tm.getEm().getTransaction().commit();

        } catch (RollbackException ex) {

            tm.getEm().getTransaction().rollback();

        } finally {

            if (tm.getEm() != null) {
                tm.getEm().close();
            }
        }
    }

    /**
     * @see AccountService#transfer(Integer, Integer, double)
     */
    @Override
    public void transfer(Integer srcId, Integer dstId, double amount) {


        try {

            tm.getEm().getTransaction().begin();

            Optional<AbstractAccount> srcAccount = Optional.ofNullable(tm.getEm().find(AbstractAccount.class,srcId ));
            Optional<AbstractAccount> dstAccount = Optional.ofNullable(tm.getEm().find(AbstractAccount.class,dstId ));

            if (!srcAccount.isPresent() || !dstAccount.isPresent()) {
                tm.getEm().getTransaction().rollback();
            }

            srcAccount.orElseThrow(() -> new IllegalArgumentException("invalid account id"));
            dstAccount.orElseThrow(() -> new IllegalArgumentException("invalid account id"));

            // make sure transaction can be performed
            if (srcAccount.get().canDebit(amount) && dstAccount.get().canCredit(amount)) {
                srcAccount.get().debit(amount);
                dstAccount.get().credit(amount);
            }

            tm.getEm().getTransaction().commit();

        } catch (RollbackException ex) {

            tm.getEm().getTransaction().rollback();

        } finally {

            if (tm.getEm() != null) {
                tm.getEm().close();
            }
        }
    }

    @Override
    public void setTM(JpaTransactionManager jtm) {

    }
}
