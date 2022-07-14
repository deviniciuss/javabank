package org.academiadecodigo.javabank.services.jpa;

import org.academiadecodigo.javabank.model.Customer;
import org.academiadecodigo.javabank.model.Model;
import org.academiadecodigo.javabank.model.account.Account;
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

    private EntityManagerFactory emf;
    private Class<Customer> customerClass;

    public JpaCustomerService(EntityManagerFactory emf) {
        this.emf = emf;
        customerClass = Customer.class;
    }

    /**
     * @see CustomerService#list()
     */
    @Override
    public List<Customer> list() {

        EntityManager em = emf.createEntityManager();

        try {

            CriteriaQuery<Customer> criteriaQuery = em.getCriteriaBuilder().createQuery(customerClass);
            Root<Customer> root = criteriaQuery.from(customerClass);
            return em.createQuery(criteriaQuery).getResultList();

        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /**
     * @see CustomerService#get(Integer)
     */
    @Override
    public Customer get(Integer id) {

        EntityManager em = emf.createEntityManager();

        try {

            return em.find(customerClass, id);

        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /**
     * @see CustomerService#save(Customer)
     */
    @Override
    public Customer save(Customer customer) {

        EntityManager em = emf.createEntityManager();

        try {

            em.getTransaction().begin();
            Customer savedObject = em.merge(customer);
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
     * @see CustomerService#delete(Integer)
     */
    @Override
    public void delete(Integer id) {

        EntityManager em = emf.createEntityManager();

        try {

            em.getTransaction().begin();
            em.remove(em.find(customerClass, id));
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
     * @see CustomerService#getBalance(Integer)
     */
    @Override
    public double getBalance(Integer id) {

        EntityManager em = emf.createEntityManager();

        try {

            Customer customer = Optional.ofNullable(em.find(customerClass, id))
                .orElseThrow(() -> new IllegalArgumentException("Customer does not exist"));

            return customer.getAccounts().stream()
                    .mapToDouble(Account::getBalance)
                    .sum();

        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /**
     * @see CustomerService#listCustomerAccountIds(Integer)
     */
    @Override
    public Set<Integer> listCustomerAccountIds(Integer id) {

        EntityManager em = emf.createEntityManager();

        try {

            Customer customer = Optional.ofNullable(em.find(customerClass, id))
                    .orElseThrow(() -> new IllegalArgumentException("Customer does not exist"));

            return customer.getAccounts().stream()
                    .map(Model::getId)
                    .collect(Collectors.toSet());

        } finally {
            if (em != null) {
                em.close();
            }
        }

    }
}
