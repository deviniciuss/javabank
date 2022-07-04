package org.academiadecodigo.javabank.service;

import org.academiadecodigo.javabank.model.Customer;
import java.util.List;
import java.util.Set;


public interface CustomerService {
    public Customer get(Integer id);
    public List<Customer> list();
    Set<Integer> listCustomerAccountIds(Integer id);
    public double getBalance(int customerId);
    public void add(Customer customer);
}
