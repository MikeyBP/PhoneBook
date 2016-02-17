package com.bluespurs.phonebook.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bluespurs.phonebook.models.Customer;
import com.bluespurs.phonebook.models.helpers.CustomerHelper;

@Controller
@RequestMapping("/rest")
public class RestController {
    
    @RequestMapping(value = "/getCustomers", method = RequestMethod.GET)
    public @ResponseBody
    List<Customer> getCustomers() {
        return CustomerHelper.getCustomers();        
    }
    
    @RequestMapping(value = "/searchCustomers/{criteria}", method = RequestMethod.GET)
    public @ResponseBody
    List<Customer> searchCustomers(@PathVariable String criteria) {
        return CustomerHelper.searchCustomer(criteria);        
    }


}
