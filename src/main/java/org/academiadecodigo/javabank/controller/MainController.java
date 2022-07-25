package org.academiadecodigo.javabank.controller;

import org.academiadecodigo.javabank.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MainController {
    CustomerService customer;

    @RequestMapping(method = RequestMethod.GET, value = "")
    public String listCustomer(Model model) {

        model.addAttribute("customers", customer.getAllCustomers());

        return "index";

    }

    @RequestMapping(method = RequestMethod.POST, value = "")
    public String createCustomer(Model model) {

        model.addAttribute("customers", customer.getAllCustomers());

        return "index";

    }


    @RequestMapping(method = RequestMethod.GET, value = "/customerDetails/{id}")
    public String showCustomer(Model model, @PathVariable Integer id) {


        model.addAttribute("customers",  customer.get(id) );

        return "customerDetails";

    }

    @RequestMapping(method = RequestMethod.GET, value ="/delete/{id}")
    public String delete(@PathVariable Integer id) {
        customer.delete(id);

        return "redirect:/";
    }

    @Autowired
    public void setCustomer(CustomerService customer) {
        this.customer = customer;
    }




}
