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
@RequestMapping("/instruments")
public class InstrumentController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private final InstrumentService instrumentService;

    public InstrumentController(InstrumentService instrumentService) {
        this.instrumentService = instrumentService;
    }

    //create new instrument from employee portal
    @GetMapping("/new")
    public String showNewInstrumentPage(Model model) {
        Instrument instrument = new Instrument();
        model.addAttribute("instrument", instrument);
        return "new-instrument";
    }

    //save new instrument and redirect to employee portal
    @PostMapping
    public String saveInstrument(@ModelAttribute("instrument") Instrument instrument) {
        instrumentService.saveInstrument(instrument);
        return "redirect:/employeePortal";
    }

    @GetMapping("/edit/{id}")
    public ModelAndView showEditInstrumentPage(@PathVariable(name = "id") Long id) {
        ModelAndView mav = new ModelAndView("edit-instrument");
        Instrument instrument = instrumentService.getInstrument(id);
        mav.addObject("instrument", instrument);
        List<Customer> customerList = customerService.getAllAvailableCustomers();
        mav.addObject("customerList", customerList);
        return mav;
    }

    @PostMapping("/update/{id}")
    public String updateInstrument(@PathVariable(name = "id") Long id, @ModelAttribute("instrument") Instrument instrument, Model model) {
        if (!id.equals(instrument.getId())) {
            model.addAttribute("message",
                    "Cannot update, instrument id " + instrument.getId()
                            + " doesn't match id to be updated: " + id + ".");
            return "error-page";
        }
        instrumentService.saveInstrument(instrument);
        return "redirect:/employeePortal";
    }

    //Delete Instrument
    @RequestMapping("/delete/{id}")
    public String deleteInstrument(@PathVariable(name = "id") Long id, Model model) {
        if (instrumentService.getInstrument(id).getCustomerId() == null) {
            return "redirect:/errorpage";
        } else {

            try {
                instrumentService.deleteInstrument(id);
            } catch (Exception e) {
                model.addAttribute("error", e.getMessage());

            }
            final List<Customer> customerList = customerService.getAllCustomers();
            final List<Instrument> instrumentList = instrumentService.getAllInstruments();

            // Once the customers are retrieved, you can store them in model and return it to the view
            model.addAttribute("customerList", customerList);
            model.addAttribute(instrumentList);
            return "employeePortal";
        }
    }


}
