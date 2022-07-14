package org.academiadecodigo.javabank.services.jpa;

import org.academiadecodigo.javabank.factories.AccountFactory;
import org.academiadecodigo.javabank.model.account.AbstractAccount;
import org.academiadecodigo.javabank.model.account.Account;
import org.academiadecodigo.javabank.persistence.ConnectionManager;
import org.academiadecodigo.javabank.services.AccountService;

import javax.persistence.EntityManager;
import javax.persistence.RollbackException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.sql.SQLException;

public class JpaAccountService implements AccountService {
    private ConnectionManager connectionManager;
    private AccountFactory accountFactory;

    public JpaAccountService() {

    }

    @Override
    public Account get(Integer id) {

        EntityManager em = connectionManager.getConnection();
        try {
            // 1 - get a CriteriaBuilder object from the EntityManager
            CriteriaBuilder builder = em.getCriteriaBuilder();

            // 2 - create a new CriteriaQuery instance for the Customer entity
            CriteriaQuery<AbstractAccount> criteriaQuery = builder.createQuery(AbstractAccount.class);

            // 3 - get the root of the query, from where all navigation starts
            Root<AbstractAccount> root = criteriaQuery.from(AbstractAccount.class);

            // 4 - specify the item that is to be returned in the query result
            criteriaQuery.select(root);

            // 5 - add query restrictions
            criteriaQuery.where(builder.equal(root.get("id"), id));

            // 6 - create and execute a query using the criteria
            return em.createQuery(criteriaQuery).getSingleResult();


        } finally {
            if (em != null) {
                em.close();
            }
        }


    }

    @Override
    public AbstractAccount add(AbstractAccount account) {

        EntityManager em = connectionManager.getConnection();

        try {
            em.getTransaction().begin();
            AbstractAccount savedAccount = em.merge(account);
            em.getTransaction().commit();
            return savedAccount;


        } catch (RollbackException ex) {
            em.getTransaction().rollback();
            return null;


        } finally {
            if (em != null) {
                em.close();
            }
        }

    }

    @Override
    public void deposit(int id, double amount) {

        Account account = get(id);

        if (account == null) {
            throw new IllegalArgumentException("invalid account id");
        }

        try {

            account.credit(amount);
            updateBalance(account.getId(), account.getBalance());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void withdraw(int id, double amount) {

        Account account = get(id);

        if (account == null) {
            throw new IllegalArgumentException("invalid account id");
        }

        try {

            account.debit(amount);
            updateBalance(account.getId(), account.getBalance());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void transfer(int srcId, int dstId, double amount) {

        Account srcAccount = get(srcId);
        Account dstAccount = get(dstId);

        if (srcAccount == null || dstAccount == null) {
            throw new IllegalArgumentException("invalid account id");
        }

        try {
            if (srcAccount.canDebit(amount)) {
                srcAccount.debit(amount);
                dstAccount.credit(amount);

                updateBalance(srcAccount.getId(), srcAccount.getBalance());
                updateBalance(dstAccount.getId(), dstAccount.getBalance());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateBalance(int id, double totalBalance) throws SQLException {
        EntityManager em = connectionManager.getConnection();

        try {
            String query =  "UPDATE account SET balance = ? WHERE id = ?";


        } finally {
            if (em != null){
                em.close();
            }
        }

    }
}
