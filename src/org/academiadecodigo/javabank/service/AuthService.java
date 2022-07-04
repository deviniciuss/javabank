package org.academiadecodigo.javabank.service;

import org.academiadecodigo.javabank.model.Customer;

public interface AuthService {
    public boolean authenticate(Integer id);
    public Customer getAccessingCustomer();
}
