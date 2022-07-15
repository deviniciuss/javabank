package org.academiadecodigo.javabank.services.jpa;

import org.academiadecodigo.javabank.model.Customer;
import org.academiadecodigo.javabank.model.Model;
import org.academiadecodigo.javabank.model.account.Account;
import org.academiadecodigo.javabank.persistence.JpaSessionManager;
import org.academiadecodigo.javabank.persistence.JpaTransactionManager;
import org.academiadecodigo.javabank.services.CustomerService;

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

    private JpaTransactionManager jtm;
    private Class<Customer> customerClass;


    public JpaCustomerService() {
        customerClass = Customer.class;
    }


    /**
     * @see CustomerService#list()
     */
    @Override
    public List<Customer> list() {
        jtm.beginRead();

        CriteriaQuery<Customer> criteriaQuery = jtm.getEm().getCriteriaBuilder().createQuery(customerClass);
        Root<Customer> root = criteriaQuery.from(customerClass);
        return jtm.getEm().createQuery(criteriaQuery).getResultList();


    }

    /**
     * @see CustomerService#get(Integer)
     */
    @Override
    public Customer get(Integer id) {

        jtm.beginRead();
        jtm.commit();
        return jtm.getEm().find(customerClass, id);


    }

    /**
     * @see CustomerService#save(Customer)
     */
    @Override
    public Customer save(Customer customer) {


        jtm.beginRead();

        try {
            jtm.beginWrite();
            Customer savedCustomer = jtm.getEm().merge(customer);
            jtm.commit();
            return savedCustomer;
        } catch (RollbackException ex) {
            jtm.rollback();
            return null;
        }

    }


    /**
     * @see CustomerService#delete(Integer)
     */
    @Override
    public void delete(Integer id) {


        jtm.beginRead();
        try {
            jtm.beginWrite();
            jtm.getEm().remove(jtm.getEm().find(customerClass, id));
            jtm.commit();
        } catch (RollbackException ex) {

            jtm.rollback();

        }


    }

    /**
     * @see CustomerService#getBalance(Integer)
     */
    @Override
    public double getBalance(Integer id) {
        jtm.beginRead();

        Customer customer = Optional.ofNullable(jtm.getEm().find(customerClass, id))
                .orElseThrow(() -> new IllegalArgumentException("Customer does not exist"));

        return customer.getAccounts().stream()
                .mapToDouble(Account::getBalance)
                .sum();


    }

    /**
     * @see CustomerService#listCustomerAccountIds(Integer)
     */
    @Override
    public Set<Integer> listCustomerAccountIds(Integer id) {

        jtm.beginRead();

        Customer customer = Optional.ofNullable(jtm.getEm().find(customerClass, id))
                .orElseThrow(() -> new IllegalArgumentException("Customer does not exist"));

        return customer.getAccounts().stream()
                .map(Model::getId)
                .collect(Collectors.toSet());


    }

    @Override
    public void setTM(JpaTransactionManager jtm) {

    }

    public void setJtm(JpaTransactionManager jtm) {
        this.jtm = jtm;
    }
}
