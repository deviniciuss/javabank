package org.academiadecodigo.javabank.services.mock;

import org.academiadecodigo.javabank.model.Customer;
import org.academiadecodigo.javabank.model.Model;
import org.academiadecodigo.javabank.model.account.Account;
import org.academiadecodigo.javabank.persistence.SessionManager;
import org.academiadecodigo.javabank.persistence.TransactionManager;
import org.academiadecodigo.javabank.services.CustomerService;

import java.util.*;
import java.util.stream.Collectors;

/**
 * A mock {@link CustomerService} implementation
 */
public class MockCustomerService implements CustomerService {

    private Map<Integer, Customer> customerMap = new HashMap<>();

    /**
     * Gets the next customer id
     *
     * @return the customer id
     */
    private Integer getNextId() {
        return customerMap.isEmpty() ? 1 : Collections.max(customerMap.keySet()) + 1;
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
        return new ArrayList<>(customerMap.values());
    }

    /**
     * @see CustomerService#get(Integer)
     */
    @Override
    public Customer get(Integer id) {
        return customerMap.get(id);
    }

    /**
     * @see CustomerService#save(Customer)
     */
    @Override
    public Customer save(Customer customer) {

        if (customer.getId() == null) {
            customer.setId(getNextId());
        }

        customerMap.put(customer.getId(), customer);

        return customer;

    }

    /**
     * @see CustomerService#delete(Integer)
     */
    @Override
    public void delete(Integer id) {
        customerMap.remove(id);
    }

    /**
     * @see CustomerService#getBalance(Integer)
     */
    @Override
    public double getBalance(Integer id) {

        List<Account> accounts = customerMap.get(id).getAccounts();

        return accounts.stream()
                .mapToDouble(Account::getBalance)
                .sum();
    }

    /**
     * @see CustomerService#listCustomerAccountIds(Integer)
     */
    @Override
    public Set<Integer> listCustomerAccountIds(Integer id) {

        List<Account> accounts = customerMap.get(id).getAccounts();

        return accounts.stream()
                .map(Model::getId)
                .collect(Collectors.toSet());
    }
}
