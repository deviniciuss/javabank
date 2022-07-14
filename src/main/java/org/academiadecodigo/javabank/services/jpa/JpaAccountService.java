package org.academiadecodigo.javabank.services.jpa;

import org.academiadecodigo.javabank.App;
import org.academiadecodigo.javabank.model.account.AbstractAccount;
import org.academiadecodigo.javabank.model.account.Account;
import org.academiadecodigo.javabank.persistence.SessionManager;
import org.academiadecodigo.javabank.persistence.TransactionManager;
import org.academiadecodigo.javabank.services.AccountService;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.RollbackException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

/**
 * A JPA {@link AccountService} implementation
 */
public class JpaAccountService implements AccountService {


    private TransactionManager tm ;

    private Class<Account> accountClass;

    public JpaAccountService(  ) {


        accountClass = Account.class;
    }

    @Override
    public void setTm(TransactionManager tm) {

    }

    @Override
    public void setSm(SessionManager sm) {

    }

    /**
     * @see AccountService#list()
     */
    public List<Account> list() {


        try {
            tm.beginRead();

            CriteriaQuery<Account> criteriaQuery = tm.getSm().getCurrentSession().getCriteriaBuilder().createQuery(accountClass);
            Root<Account> root = criteriaQuery.from(accountClass);
            return tm.getSm().getCurrentSession().createQuery(criteriaQuery).getResultList();

        } finally {
            if (tm.getSm().getCurrentSession() != null) {
                tm.getSm().getCurrentSession().close();
            }
        }
    }

    /**
     * @see AccountService#get(Integer)
     */
    public Account get(Integer id) {


        try {

            return tm.getSm().getCurrentSession().find(accountClass, id);


        } finally {
            if (tm.getSm().getCurrentSession() != null) {
                tm.getSm().getCurrentSession().close();
            }
        }
    }

    /**
     * @see AccountService#save(Account)
     */
    public Account save(Account account) {


        try {

            tm.getSm().getCurrentSession().getTransaction().begin();
            Account savedObject = tm.getSm().getCurrentSession().merge(account);
            tm.getSm().getCurrentSession().getTransaction().commit();

            return savedObject;

        } catch (RollbackException ex) {

            tm.getSm().getCurrentSession().getTransaction().rollback();
            return null;

        } finally {
            if (tm.getSm().getCurrentSession() != null) {
                tm.getSm().getCurrentSession().close();
            }
        }
    }

    /**
     * @see AccountService#delete(Integer)
     */
    public void delete(Integer id) {


        try {

            tm.getSm().getCurrentSession().getTransaction().begin();
            tm.getSm().getCurrentSession().remove(tm.getSm().getCurrentSession().find(accountClass, id));
            tm.getSm().getCurrentSession().getTransaction().commit();

        } catch (RollbackException ex) {

            tm.getSm().getCurrentSession().getTransaction().rollback();

        } finally {
            if (tm.getSm().getCurrentSession() != null) {
                tm.getSm().getCurrentSession().close();
            }
        }
    }

    /**
     * @see AccountService#deposit(Integer, double)
     */
    public void deposit(Integer id, double amount) {


        try {

            tm.getSm().getCurrentSession().getTransaction().begin();

            Optional<AbstractAccount> account = Optional.ofNullable(tm.getSm().getCurrentSession().find(AbstractAccount.class, id));

            if (!account.isPresent()) {
                tm.getSm().getCurrentSession().getTransaction().rollback();
            }

            account.orElseThrow(() -> new IllegalArgumentException("invalid account id")).credit(amount);

            tm.getSm().getCurrentSession().getTransaction().commit();

        } catch (RollbackException ex) {

            tm.getSm().getCurrentSession().getTransaction().rollback();

        } finally {

            if (tm.getSm().getCurrentSession() != null) {
                tm.getSm().getCurrentSession().close();
            }
        }
    }

    /**
     * @see AccountService#withdraw(Integer, double)
     */
    @Override
    public void withdraw(Integer id, double amount) {


        try {

            tm.getSm().getCurrentSession().getTransaction().begin();

            Optional<AbstractAccount> account = Optional.ofNullable(tm.getSm().getCurrentSession().find(AbstractAccount.class, id));

            if (!account.isPresent()) {
                tm.getSm().getCurrentSession().getTransaction().rollback();
            }

            account.orElseThrow(() -> new IllegalArgumentException("invalid account id")).debit(amount);

            tm.getSm().getCurrentSession().getTransaction().commit();

        } catch (RollbackException ex) {

            tm.getSm().getCurrentSession().getTransaction().rollback();

        } finally {

            if (tm.getSm().getCurrentSession() != null) {
                tm.getSm().getCurrentSession().close();
            }
        }
    }

    /**
     * @see AccountService#transfer(Integer, Integer, double)
     */
    @Override
    public void transfer(Integer srcId, Integer dstId, double amount) {


        try {

            tm.getSm().getCurrentSession().getTransaction().begin();

            Optional<AbstractAccount> srcAccount = Optional.ofNullable(tm.getSm().getCurrentSession().find(AbstractAccount.class,srcId ));
            Optional<AbstractAccount> dstAccount = Optional.ofNullable(tm.getSm().getCurrentSession().find(AbstractAccount.class,dstId ));

            if (!srcAccount.isPresent() || !dstAccount.isPresent()) {
                tm.getSm().getCurrentSession().getTransaction().rollback();
            }

            srcAccount.orElseThrow(() -> new IllegalArgumentException("invalid account id"));
            dstAccount.orElseThrow(() -> new IllegalArgumentException("invalid account id"));

            // make sure transaction can be performed
            if (srcAccount.get().canDebit(amount) && dstAccount.get().canCredit(amount)) {
                srcAccount.get().debit(amount);
                dstAccount.get().credit(amount);
            }

            tm.getSm().getCurrentSession().getTransaction().commit();

        } catch (RollbackException ex) {

            tm.getSm().getCurrentSession().getTransaction().rollback();

        } finally {

            if (tm.getSm().getCurrentSession() != null) {
                tm.getSm().getCurrentSession().close();
            }
        }
    }
}
