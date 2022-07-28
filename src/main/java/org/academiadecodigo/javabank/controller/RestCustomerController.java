package org.academiadecodigo.javabank.controller;

import org.academiadecodigo.javabank.command.AccountDto;
import org.academiadecodigo.javabank.command.CustomerDto;
import org.academiadecodigo.javabank.converters.AccountToAccountDto;
import org.academiadecodigo.javabank.converters.CustomerDtoToCustomer;
import org.academiadecodigo.javabank.converters.CustomerToCustomerDto;
import org.academiadecodigo.javabank.converters.RecipientToRecipientDto;
import org.academiadecodigo.javabank.persistence.model.Customer;
import org.academiadecodigo.javabank.persistence.model.account.Account;
import org.academiadecodigo.javabank.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/customer")
public class RestCustomerController {
    private CustomerService customerService;
    private CustomerToCustomerDto customerToCustomerDto;
    private CustomerDtoToCustomer customerDtoToCustomer;
    private AccountToAccountDto accountToAccountDto;
    private RecipientToRecipientDto recipientToRecipientDto;

    @Autowired
    public void setCustomerService(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Autowired
    public void setCustomerToCustomerDto(CustomerToCustomerDto customerToCustomerDto) {
        this.customerToCustomerDto = customerToCustomerDto;
    }

    @Autowired
    public void setCustomerDtoToCustomer(CustomerDtoToCustomer customerDtoToCustomer) {
        this.customerDtoToCustomer = customerDtoToCustomer;
    }

    @Autowired
    public void setAccountToAccountDto(AccountToAccountDto accountToAccountDto) {
        this.accountToAccountDto = accountToAccountDto;
    }

    @Autowired
    public void setRecipientToRecipientDto(RecipientToRecipientDto recipientToRecipientDto) {
        this.recipientToRecipientDto = recipientToRecipientDto;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/api/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CustomerDto>> listCustomers() {
        return new ResponseEntity<>(customerToCustomerDto.convert(customerService.list()), HttpStatus.OK);

    }

    @RequestMapping(method = RequestMethod.GET, value = "/api/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomerDto> showCustomer(@PathVariable Integer id) {

        return new ResponseEntity<>(customerToCustomerDto.convert(customerService.get(id)), HttpStatus.OK);

    }

    @RequestMapping(method = RequestMethod.GET, value = "/api/{id}/accounts", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AccountDto>> showAccountsCustomer(@PathVariable Integer id) {

        return new ResponseEntity<>(accountToAccountDto.convert(customerService.get(id).getAccounts()), HttpStatus.OK);

    }

    @RequestMapping(method = RequestMethod.GET, value = "/api/{cid}/accounts/{aid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccountDto> showOneAccountCustomer(@PathVariable Integer cid, @PathVariable Integer aid) {

        return new ResponseEntity<>(accountToAccountDto.convert(customerService.get(cid).getAccounts().get(aid -1)), HttpStatus.OK);

    }

    @RequestMapping(method = RequestMethod.POST, value = "/api/addCustomer", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity addCustomer(@Valid @RequestBody CustomerDto customerDto) {

        customerService.save(customerDtoToCustomer.convert(customerDto));
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @RequestMapping(method = RequestMethod.PUT, value = "/api/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity editCustomer(@RequestBody CustomerDto customerDto, @PathVariable Integer id) {
       Customer customer = customerDtoToCustomer.convert(customerDto) ;
       customer.setId(id);
       customerService.save(customer);
       return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/api/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity deleteCustomer( @RequestBody @PathVariable Integer id) {

        customerService.get(id);
        customerService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);

    }
    @RequestMapping(method = RequestMethod.DELETE, value = "/api/{cid}/accounts/{aid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity deleteAccount( @RequestBody @PathVariable Integer cid, @PathVariable Integer aid) {
        customerService.deleteAccount(cid, aid);

        return new ResponseEntity<>(HttpStatus.OK);

    }

}



