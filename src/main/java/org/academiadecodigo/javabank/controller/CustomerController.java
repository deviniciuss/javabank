package org.academiadecodigo.javabank.controller;

import org.academiadecodigo.javabank.persistence.dto.Converter;
import org.academiadecodigo.javabank.persistence.dto.CustomerDto;
import org.academiadecodigo.javabank.persistence.model.Customer;
import org.academiadecodigo.javabank.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Controller responsible for rendering {@link Customer} related views
 */
@Controller
@RequestMapping("/customer")
public class CustomerController {

    private CustomerService customerService;
    private Converter converter;

    @Autowired
    public void setConverter(Converter converter) {
        this.converter = converter;
    }

    /**
     * Sets the customer service
     *
     * @param customerService the customer service to set
     */

    @Autowired
    public void setCustomerService(CustomerService customerService) {
        this.customerService = customerService;
    }

    /**
     * Renders a view with a list of customers
     *
     * @param model the model object
     * @return the view to render
     */
    @RequestMapping(method = RequestMethod.GET, path = {"/list", "/", ""})
    public String listCustomers(Model model) {
        model.addAttribute("customers", customerService.list());
        return "customer/list";
    }

    /**
     * Renders a view with customer details
     *
     * @param id    the customer id
     * @param model the model object
     * @return the view to render
     */
    @RequestMapping(method = RequestMethod.GET, path = "/{id}")
    public String showCustomer(@PathVariable Integer id, Model model) {
        Customer customer = customerService.get(id);
        model.addAttribute("customer", converter.convertCustomerToDto(customer));
        model.addAttribute("recipients", customerService.listRecipients(id));
        return "customer/show";
    }

    /**
     * Deletes a customer
     *
     * @param id the customer id
     * @return the view to render
     */
    @RequestMapping(method = RequestMethod.GET, path = "{id}/delete")
    public String deleteCustomer(@PathVariable Integer id) {
        customerService.delete(id);
        return "redirect:/customer/list";
    }

    /**
     * Deletes a recipient from a customer
     *
     * @param cid the customer id
     * @param rid the recipient id
     * @return the view to render
     */
    @RequestMapping(method = RequestMethod.GET, path = "/{cid}/recipient/{rid}/delete/")
    public String deleteRecipient(@PathVariable Integer cid, @PathVariable Integer rid) {
        customerService.removeRecipient(cid, rid);
        return "redirect:/customer/" + cid;
    }
    @RequestMapping(method = RequestMethod.POST, value = "/add")
    public String addCustomer(@ModelAttribute CustomerDto customerDto) {

        Customer savedCustomer = customerService.save(converter.convertDtoToCustomer(customerDto));
        customerService.save(savedCustomer);

// Instead of returning a rendered view to the browser,
// a 302 redirect is sent to the browser, forcing showCustomer()
// to execute after adding a new customer
        return "redirect:/customer/list";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/add")
    public String ShowNewCustomer (Model model) {
        model.addAttribute("customer",new CustomerDto());
        return "customer/add";
    }

}