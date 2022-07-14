package org.academiadecodigo.javabank.services.jpa;

import org.academiadecodigo.bootcamp.InputScanner;
import org.academiadecodigo.javabank.model.Customer;
import org.academiadecodigo.javabank.model.account.AbstractAccount;
import org.academiadecodigo.javabank.model.account.Account;
import org.academiadecodigo.javabank.persistence.ConnectionManager;
import org.academiadecodigo.javabank.services.AccountService;
import org.academiadecodigo.javabank.services.CustomerService;

import javax.persistence.EntityManager;
import javax.persistence.RollbackException;
import javax.persistence.TypedQuery;
import java.util.*;

public class JpaCustomerService implements CustomerService {
    private AccountService accountService;

    private ConnectionManager connectionManager;


    public JpaCustomerService() {

    }

    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public Customer get(Integer id) {

        EntityManager em = connectionManager.getConnection();

        try {
            Customer customer =  em.find(Customer.class, id);
            if(customer == null){
                return buildCustomer();
            }
            return customer;


        } finally {
            if (em != null){
                em.close();
            }
        }
    }

    @Override
    public List<Customer> list() {
        EntityManager em = connectionManager.getConnection();

        try{
            TypedQuery<Customer> query = em.createQuery("SELECT '*' from Customer customer", Customer.class);
            return  query.getResultList();
        }  finally {
            if (em!= null){
                em.close();
            }
        }

    }

    @Override
    public Set<Integer> listCustomerAccountIds(Integer id) {

        Customer customer = get(id);

        if (customer == null) {
            throw new IllegalArgumentException("Customer does not exist");
        }

        List<AbstractAccount> accounts = customer.getAccounts();

        if (accounts.size() == 0) {
            return Collections.emptySet();
        }

        Set<Integer> customerAccountIds = new HashSet<>();

        for (Account account : accounts) {
            customerAccountIds.add(account.getId());
        }

        return customerAccountIds;
    }

    @Override
    public double getBalance(int id) {

        Customer customer = get(id);

        if (customer == null) {
            throw new IllegalArgumentException("Customer does not exist");
        }

        List<AbstractAccount> accounts = customer.getAccounts();

        double balance = 0;
        for (Account account : accounts) {
            balance += account.getBalance();
        }

        return balance;
    }

    @Override
    public Customer add(Customer customer) {
        EntityManager em = connectionManager.getConnection();

        try{
            em.getTransaction().begin();
            Customer savedCustomer = em.merge(customer);
            em.getTransaction().commit();
            return savedCustomer;


        } catch (RollbackException ex){
            em.getTransaction().rollback();
            return null;


        } finally {
            if (em != null){
                em.close();
            }
        }

    }
    private Customer buildCustomer()   {
        EntityManager em = connectionManager.getConnection();
        Scanner scanner = new Scanner(System.in);
        Customer customer = new Customer();


        System.out.println("First Name: " ); customer.setFirstName(scanner.next());
        System.out.println("Last Name: " );customer.setLastName(scanner.next());
        System.out.println("Phone: " );customer.setPhone(scanner.next());
        System.out.println("Email: " ); customer.setEmail(scanner.next());

        em.getTransaction().begin();
        em.persist(customer);
        em.getTransaction().commit();

        return customer;
    }



}
