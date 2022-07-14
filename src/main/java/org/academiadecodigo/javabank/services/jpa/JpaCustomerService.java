package org.academiadecodigo.javabank.services.jpa;

import org.academiadecodigo.javabank.App;
import org.academiadecodigo.javabank.model.Customer;
import org.academiadecodigo.javabank.model.Model;
import org.academiadecodigo.javabank.model.account.Account;
import org.academiadecodigo.javabank.persistence.SessionManager;
import org.academiadecodigo.javabank.persistence.TransactionManager;
import org.academiadecodigo.javabank.services.CustomerService;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.RollbackException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * A JPA {@link CustomerService} implementation
 */
public class JpaCustomerService implements CustomerService {

    private TransactionManager tm;
    private Class<Customer> customerClass;
    private EntityManagerFactory emf;


    public JpaCustomerService(EntityManagerFactory emf1) {

        this.emf = emf1;
        customerClass = Customer.class;
    }

    @Override
    public void setTm(TransactionManager tm) {

    }

    @Override
    public void setSm(SessionManager sm) {

    }

    /**
     * @see CustomerService#list()
     */
    @Override
    public List<Customer> list() {


        try {

            CriteriaQuery<Customer> criteriaQuery = tm.getSm().getCurrentSession().getCriteriaBuilder().createQuery(customerClass);
            Root<Customer> root = criteriaQuery.from(customerClass);
            return tm.getSm().getCurrentSession().createQuery(criteriaQuery).getResultList();

        } finally {
            if (tm.getSm().getCurrentSession() != null) {
                tm.getSm().getCurrentSession().close();
            }
        }
    }

    /**
     * @see CustomerService#get(Integer)
     */
    @Override
    public Customer get(Integer id) {

        tm.beginRead();
        return tm.getEm().find(customerClass, id);


    }

    /**
     * @see CustomerService#save(Customer)
     */
    @Override
    public Customer save(Customer customer) {


        try {

            tm.getSm().getCurrentSession().getTransaction().begin();
            Customer savedObject = tm.getSm().getCurrentSession().merge(customer);
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
     * @see CustomerService#delete(Integer)
     */
    @Override
    public void delete(Integer id) {


        try {

            tm.getSm().getCurrentSession().getTransaction().begin();
            tm.getSm().getCurrentSession().remove(tm.getSm().getCurrentSession().find(customerClass, id));
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
     * @see CustomerService#getBalance(Integer)
     */
    @Override
    public double getBalance(Integer id) {


        try {

            Customer customer = Optional.ofNullable(tm.getSm().getCurrentSession().find(customerClass, id))
                    .orElseThrow(() -> new IllegalArgumentException("Customer does not exist"));

            return customer.getAccounts().stream()
                    .mapToDouble(Account::getBalance)
                    .sum();

        } finally {
            if (tm.getSm().getCurrentSession() != null) {
                tm.getSm().getCurrentSession().close();
            }
        }
    }

    /**
     * @see CustomerService#listCustomerAccountIds(Integer)
     */
    @Override
    public Set<Integer> listCustomerAccountIds(Integer id) {


        try {

            Customer customer = Optional.ofNullable(tm.getSm().getCurrentSession().find(customerClass, id))
                    .orElseThrow(() -> new IllegalArgumentException("Customer does not exist"));

            return customer.getAccounts().stream()
                    .map(Model::getId)
                    .collect(Collectors.toSet());

        } finally {
            if (tm.getSm().getCurrentSession() != null) {
                tm.getSm().getCurrentSession().close();
            }
        }

    }
}
