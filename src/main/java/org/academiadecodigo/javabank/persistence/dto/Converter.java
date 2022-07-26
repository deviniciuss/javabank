package org.academiadecodigo.javabank.persistence.dto;

import org.academiadecodigo.javabank.persistence.model.Customer;
import org.academiadecodigo.javabank.persistence.model.account.Account;
import org.academiadecodigo.javabank.services.AccountServiceImpl;
import org.springframework.stereotype.Component;

@Component
public class Converter {
    private CustomerDto customerDto;
    private AccountDto accountDto;

    public void setCustomerDto(CustomerDto customerDto) {
        this.customerDto = customerDto;
    }

    public Customer convertDtoToCustomer(CustomerDto customerDto) {
        Customer customer = new Customer();
        customer.setFirstName(customerDto.getFirstName());
        customer.setLastName(customerDto.getLastName());
        return customer;


    }
    public CustomerDto convertCustomerToDto(Customer customer) {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setFirstName(customer.getFirstName());
        customerDto.setLastName(customer.getLastName());
        return customerDto;


    }

    /*public Account convertAccountToDto(AccountDto accountDto){
        Account account = new AccountServiceImpl();



    }

     */




    public CustomerDto getCustomerDto() {
        return customerDto;
    }
    public AccountDto getAccountDto() {
        return accountDto;
    }

    public void setAccountDto(AccountDto accountDto) {
        this.accountDto = accountDto;
    }





}
