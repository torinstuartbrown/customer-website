package com.example.demo.controllers;
import com.example.demo.models.Customer;
import com.example.demo.models.Instrument;
import com.example.demo.services.CustomerService;
import com.example.demo.services.InstrumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class CustomerController {

    @Autowired
    private final CustomerService customerService;

    @Autowired final InstrumentService instrumentService;

    //int added this
    public CustomerController(CustomerService customerService, InstrumentService instrumentService) {
        this.customerService = customerService;
        this.instrumentService = instrumentService;
    }

    @GetMapping("/")
    public String vewHomePage(Model model) {
        return "index";
    }

    //this endpoint returns both the customer and the instrument lists (employee portal)
    @GetMapping("/employeePortal")
    public String viewEmployeePortal(Model model) {
        // Here you call the service to retrieve all the customers and instruments
        final List<Customer> customerList = customerService.getAllCustomers();
        final List<Instrument> instrumentList = instrumentService.getAllInstruments();
        // Once the customers and instruments are retrieved, you can store them in model and return it to the view
        model.addAttribute("customerList", customerList);
        model.addAttribute(instrumentList);
        return "employeePortal";
    }

    //able to create a new customer from the employee portal
    @GetMapping("/new")
    public String showNewCustomerPage(Model model) {
        // Here a new (empty) Customer is created and then sent to the view
        Customer customer = new Customer();
        model.addAttribute("customer", customer);
        return "new-customer";
    }

    @PostMapping
    // As the Model is received back from the view, @ModelAttribute
    // creates a Customer based on the object you collected from
    // the HTML page above
    public String saveCustomer(@ModelAttribute("customer") Customer customer) {
        customerService.saveCustomer(customer);
        return "redirect:/employeePortal";
    }


    @GetMapping("/edit/{id}")
    // The path variable "id" is used to pull a customer from the database
    public ModelAndView showEditCustomerPage(@PathVariable(name = "id") Long id) {
        ModelAndView mav = new ModelAndView("edit-customer");
        Customer customer = customerService.getCustomer(id);
        mav.addObject("customer", customer);
        List<Instrument> instrumentList = instrumentService.getAllAvailableInstruments();
        mav.addObject("instrumentList", instrumentList);

        return mav;
    }

    @PostMapping("/update/{id}")
    public String updateCustomer(@PathVariable(name = "id") Long id, @ModelAttribute("customer") Customer customer, Model model) {
        if (!id.equals(customer.getId())) {
            model.addAttribute("message",
                    "Cannot update, customer id " + customer.getId()
                            + " doesn't match id to be updated: " + id + ".");
            return "error-page";
        }
        customerService.saveCustomer(customer);
        return "redirect:/employeePortal";
    }

    @GetMapping("/errorpage")
    public String errorPage(Model model) {
        return "error-page";
    }

    // Delete a customer
    @RequestMapping("/delete/{id}")
    public String deleteCustomer(@PathVariable(name = "id") Long id) {
        customerService.deleteCustomer(id);
        return "redirect:/employeePortal";
    }


}
