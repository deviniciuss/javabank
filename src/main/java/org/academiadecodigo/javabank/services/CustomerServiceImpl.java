package org.academiadecodigo.javabank.services;

import org.academiadecodigo.javabank.persistence.model.AbstractModel;
import org.academiadecodigo.javabank.persistence.model.Customer;
import org.academiadecodigo.javabank.persistence.model.Recipient;
import org.academiadecodigo.javabank.persistence.model.account.Account;
import org.academiadecodigo.javabank.persistence.dao.CustomerDao;
import org.springframework.transaction.annotation.Transactional;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

/**
 * An {@link CustomerService} implementation
 */
public class CustomerServiceImpl implements CustomerService {

    private CustomerDao customerDao;

    /**
     * Sets the customer data access object
     *
     * @param customerDao the account DAO to set
     */
    @Transactional
    public void setCustomerDao(CustomerDao customerDao) {
        this.customerDao = customerDao;
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
     * @see CustomerService#get(Integer)
     */
    @Transactional

    @Override
    public Customer get(Integer id) {
<<<<<<< HEAD


        return customerDao.findById(id);

=======
        return customerDao.findById(id);
>>>>>>> 9d2388ea9e1e59c86ff6170772ac68772bd2c651
    }

    /**
     * @see CustomerService#getBalance(Integer)
     */
    @Transactional

    @Override
    public double getBalance(Integer id) {

<<<<<<< HEAD

=======
>>>>>>> 9d2388ea9e1e59c86ff6170772ac68772bd2c651
        Customer customer = Optional.ofNullable(customerDao.findById(id))
                .orElseThrow(() -> new IllegalArgumentException("Customer does not exist"));

        return customer.getAccounts().stream()
                .mapToDouble(Account::getBalance)
                .sum();
<<<<<<< HEAD

=======
>>>>>>> 9d2388ea9e1e59c86ff6170772ac68772bd2c651
    }

    /**
     * @see CustomerService#listCustomerAccountIds(Integer)
     */
    @Transactional

    @Override
    public Set<Integer> listCustomerAccountIds(Integer id) {

<<<<<<< HEAD

        Customer customer = Optional.ofNullable(customerDao.findById(id))
                .orElseThrow(() -> new IllegalArgumentException("Customer does not exist"));

        return customer.getAccounts().stream()
                .map(AbstractModel::getId)
                .collect(Collectors.toSet());


=======
        Customer customer = Optional.ofNullable(customerDao.findById(id))
                .orElseThrow(() -> new IllegalArgumentException("Customer does not exist"));

        return customer.getAccounts().stream()
                .map(AbstractModel::getId)
                .collect(Collectors.toSet());
>>>>>>> 9d2388ea9e1e59c86ff6170772ac68772bd2c651
    }

    /**
     * @see CustomerService#listRecipients(Integer)
     */
<<<<<<< HEAD
    @Transactional

    @Override
    public List<Recipient> listRecipients(Integer id) {


        Customer customer = Optional.ofNullable(customerDao.findById(id))
                .orElseThrow(() -> new IllegalArgumentException("Customer does not exist"));

        return new ArrayList<>(customer.getRecipients());

=======
    @Transactional(readOnly = true)
    @Override
    public List<Recipient> listRecipients(Integer id) {

        Customer customer = Optional.ofNullable(customerDao.findById(id))
                .orElseThrow(() -> new IllegalArgumentException("Customer does not exist"));

        return new ArrayList<>(customer.getRecipients());
>>>>>>> 9d2388ea9e1e59c86ff6170772ac68772bd2c651
    }
}
