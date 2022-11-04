package com.example.demo.controllers;

import com.example.demo.models.Customer;
import com.example.demo.models.User;
import com.example.demo.services.CustomerService;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {

    @Autowired
    private final UserService userService;
    public UserController(UserService userService, CustomerService customerService) {
        this.userService = userService;
        this.customerService = customerService;
    }

    @Autowired final CustomerService customerService;



    //Registration page for new user
    @GetMapping("/register")
    public String viewRegistrationPage(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "registerUser";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user) {
        userService.saveUser(user);
        return "redirect:/";
    }

    @GetMapping("/edit/user")
    public ModelAndView viewEditUserPage(Authentication authentication) {
        ModelAndView mav = new ModelAndView("edit-user");
        User user = userService.getUser(authentication.getName());
        mav.addObject("user", user);
        return mav;
    }

    //return session user data
    @ResponseBody
    @GetMapping("/userinfo")
    public Authentication viewUserInfo(Authentication authentication) {
        return authentication;
    }


    @PostMapping("/edit/user")
    public String updateCustomer(Authentication authentication, @ModelAttribute("user") User user) {
        userService.updateUser(authentication.getName(), user);
        return "redirect:/";
    }


    //working on displaying the instrument that the customer with a matching email is renting
    @GetMapping("/myrental")
    public ModelAndView viewMyRental(Authentication authentication) {
        ModelAndView mav = new ModelAndView("myrental");
//        User user = userService.getUser(authentication.getName());
//        mav.addObject("user", user);
        Customer customer = customerService.getCustomerByEmail(authentication.getName());

        mav.addObject("customer", customer);
        return mav;
    }


}



