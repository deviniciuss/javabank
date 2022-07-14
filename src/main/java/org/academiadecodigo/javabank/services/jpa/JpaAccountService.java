package org.academiadecodigo.javabank.services.jpa;

import org.academiadecodigo.javabank.model.account.AbstractAccount;
import org.academiadecodigo.javabank.model.account.Account;
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

    private EntityManagerFactory emf;
    private Class<Account> accountClass;

    public JpaAccountService(EntityManagerFactory emf) {
        this.emf = emf;
        accountClass = Account.class;
    }

    /**
     * @see AccountService#list()
     */
    public List<Account> list() {

        EntityManager em = emf.createEntityManager();

        try {

            CriteriaQuery<Account> criteriaQuery = em.getCriteriaBuilder().createQuery(accountClass);
            Root<Account> root = criteriaQuery.from(accountClass);
            return em.createQuery(criteriaQuery).getResultList();

        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /**
     * @see AccountService#get(Integer)
     */
    public Account get(Integer id) {

        EntityManager em = emf.createEntityManager();

        try {

            return em.find(accountClass, id);

        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /**
     * @see AccountService#save(Account)
     */
    public Account save(Account account) {

        EntityManager em = emf.createEntityManager();

        try {

            em.getTransaction().begin();
            Account savedObject = em.merge(account);
            em.getTransaction().commit();

            return savedObject;

        } catch (RollbackException ex) {

            em.getTransaction().rollback();
            return null;

        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /**
     * @see AccountService#delete(Integer)
     */
    public void delete(Integer id) {

        EntityManager em = emf.createEntityManager();

        try {

            em.getTransaction().begin();
            em.remove(em.find(accountClass, id));
            em.getTransaction().commit();

        } catch (RollbackException ex) {

            em.getTransaction().rollback();

        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /**
     * @see AccountService#deposit(Integer, double)
     */
    public void deposit(Integer id, double amount) {

        EntityManager em = emf.createEntityManager();

        try {

            em.getTransaction().begin();

            Optional<AbstractAccount> account = Optional.ofNullable(em.find(AbstractAccount.class, id));

            if (!account.isPresent()) {
                em.getTransaction().rollback();
            }

            account.orElseThrow(() -> new IllegalArgumentException("invalid account id")).credit(amount);

            em.getTransaction().commit();

        } catch (RollbackException ex) {

            em.getTransaction().rollback();

        } finally {

            if (em != null) {
                em.close();
            }
        }
    }

    /**
     * @see AccountService#withdraw(Integer, double)
     */
    @Override
    public void withdraw(Integer id, double amount) {

        EntityManager em = emf.createEntityManager();

        try {

            em.getTransaction().begin();

            Optional<AbstractAccount> account = Optional.ofNullable(em.find(AbstractAccount.class, id));

            if (!account.isPresent()) {
                em.getTransaction().rollback();
            }

            account.orElseThrow(() -> new IllegalArgumentException("invalid account id")).debit(amount);

            em.getTransaction().commit();

        } catch (RollbackException ex) {

            em.getTransaction().rollback();

        } finally {

            if (em != null) {
                em.close();
            }
        }
    }

    /**
     * @see AccountService#transfer(Integer, Integer, double)
     */
    @Override
    public void transfer(Integer srcId, Integer dstId, double amount) {

        EntityManager em = emf.createEntityManager();

        try {

            em.getTransaction().begin();

            Optional<AbstractAccount> srcAccount = Optional.ofNullable(em.find(AbstractAccount.class,srcId ));
            Optional<AbstractAccount> dstAccount = Optional.ofNullable(em.find(AbstractAccount.class,dstId ));

            if (!srcAccount.isPresent() || !dstAccount.isPresent()) {
                em.getTransaction().rollback();
            }

            srcAccount.orElseThrow(() -> new IllegalArgumentException("invalid account id"));
            dstAccount.orElseThrow(() -> new IllegalArgumentException("invalid account id"));

            // make sure transaction can be performed
            if (srcAccount.get().canDebit(amount) && dstAccount.get().canCredit(amount)) {
                srcAccount.get().debit(amount);
                dstAccount.get().credit(amount);
            }

            em.getTransaction().commit();

        } catch (RollbackException ex) {

            em.getTransaction().rollback();

        } finally {

            if (em != null) {
                em.close();
            }
        }
    }
}
