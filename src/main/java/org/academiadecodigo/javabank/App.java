package org.academiadecodigo.javabank;

import org.academiadecodigo.javabank.controller.Controller;
import org.academiadecodigo.javabank.factories.AccountFactory;
import org.academiadecodigo.javabank.model.Customer;
import org.academiadecodigo.javabank.persistence.ConnectionManager;
import org.academiadecodigo.javabank.services.jdbc.JdbcAccountService;
import org.academiadecodigo.javabank.services.jdbc.JdbcCustomerService;
import org.academiadecodigo.javabank.services.AuthServiceImpl;
import org.academiadecodigo.javabank.services.jpa.JpaAccountService;
import org.academiadecodigo.javabank.services.jpa.JpaCustomerService;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.RollbackException;

public class App {



    public static void main(String[] args) {

        App app = new App();
        app.bootStrap();
    }

    private void bootStrap() {


        AccountFactory accountFactory = new AccountFactory();
        JpaAccountService accountService = new JpaAccountService();
        JpaCustomerService customerService = new JpaCustomerService();
        customerService.setAccountService(accountService);

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.setAuthService(new AuthServiceImpl());
        bootstrap.setAccountService(accountService);
        bootstrap.setCustomerService(customerService);
        bootstrap.setAccountFactory(accountFactory);

        Controller controller = bootstrap.wireObjects();

        // start application
        controller.init();

    }





}
