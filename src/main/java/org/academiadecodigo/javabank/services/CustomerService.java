package org.academiadecodigo.javabank.services;

import org.academiadecodigo.javabank.model.Customer;

import java.util.List;
import java.util.Set;

/**
 * Common interface for customer services, provides methods to manage customers
 */
public interface CustomerService {

    /**
     * Gets a list of the customers
     *
     * @return the customer list
     */
    List<Customer> list();

    /**
     * Gets the customer
     *
     * @param id the customer id
     * @return the customer
     */
    Customer get(Integer id);

    /**
     * Saves the customer
     *
     * @param customer to be saved
     * @return the saved customer
     */
    Customer save(Customer customer);

    /**
     * Deletes a customer
     *
     * @param id the id of the customer to be deleted
     */
    void delete(Integer id);

    /**
     * Gets the balance of the customer
     *
     * @param id the customer id
     * @return the balance of the customer with the given id
     */
    double getBalance(Integer id);

    /**
     * Gets the set of customer account ids
     *
     * @param id the customer id
     * @return the accounts of the given customer id
     */
    Set<Integer> listCustomerAccountIds(Integer id);
}
