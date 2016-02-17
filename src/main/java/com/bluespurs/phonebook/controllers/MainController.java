package com.bluespurs.phonebook.controllers;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import javax.servlet.http.HttpSession;
import com.bluespurs.phonebook.helpers.UserHelper;
import com.bluespurs.phonebook.models.Customer;
import com.bluespurs.phonebook.models.User;
import com.bluespurs.phonebook.models.helpers.CustomerHelper;

@Controller
@SessionAttributes({"username", "userId", "isAdmin", "customerId", "roles"})
public class MainController {
	
    /*
     * Common actions
     */
	
	@RequestMapping(method=RequestMethod.GET, value={"/"})
    public String index(Model model) {
        return "index";
    }
    
    @RequestMapping(method=RequestMethod.POST, value={"/login"})
    public String login(@RequestParam(value="user") String user, @RequestParam(value="password") String password, Model model) {    	
    	if(UserHelper.login(user,password)){
    		model.addAttribute("isAdmin", UserHelper.isAdmin(user));
    	   	model.addAttribute("username", user);
    	   	if(!UserHelper.isEnabled(user)){
         	    model.addAttribute("message", "User " + user + " is disabled");
         	    return index(model);        		
    	   	}
    	   	return "redirect:getCustomers";
    	}else{
    	   	model.addAttribute("message", "Username or password for user " + user + " is incorrect");
    	   	return index(model);
    	}  

    }

    @RequestMapping(method = RequestMethod.GET, value = { "/logout" })
    	public String logout(HttpSession session, Model model) {
    		model.asMap().clear();
    		session.invalidate();
    		return "index";
    }
    
    /*
     * Admin actions
     */
    
    @RequestMapping(method=RequestMethod.GET, value={"/getUsers"})
    public String getUsers(Model model, @RequestParam(required=false) String q) {
    	//Make sure the user accessing to this URL is logged in to the system.
    	if(!isLoggedIn(model)){
    		return "index";	
    	}
    	List<User> users;
    	model.addAttribute("roles",UserHelper.getRoles());
    	if(q != null){
    		users = UserHelper.searchUser(q);
	    	model.addAttribute("message","Showing search results for: " + q);
    	}else{
        	users = UserHelper.getUsers();        	    		
    	}
    	model.addAttribute("users", users);
        return "users";
    }
    
    private String getUsers(Model model) {
    	return getUsers(model, null);
    }
    
    @RequestMapping(method = RequestMethod.GET, value = { "/getUser" })
	public String getUser(
			@RequestParam(value = "userId") int userId, Model model) {
		model.addAttribute("user", UserHelper.getUser(userId));
    	model.addAttribute("roles",UserHelper.getRoles());
		model.addAttribute("userId", userId);
		return "modifyUser";
    }
    
    @RequestMapping(method = RequestMethod.POST, value = { "/addUser" })
	public String addUser(
			@RequestParam(value = "firstName") String firstName,
			@RequestParam(value = "lastName") String lastName,
			@RequestParam(value = "userName") String userName,
			@RequestParam(value = "password") String password,
			@RequestParam(required=false)  Integer roleId,
			@RequestParam(required=false) Boolean enabled, Model model) throws NoSuchAlgorithmException {
		model.addAttribute("firstName", firstName);
		model.addAttribute("lastName", lastName);
		model.addAttribute("userName", userName);
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(password.getBytes());
		byte[] digest = md.digest();
		StringBuffer sb = new StringBuffer();
		for (byte b : digest) {
			sb.append(String.format("%02x", b & 0xff));
		}
		model.addAttribute("password", sb.toString());
		if(roleId == null){
			model.addAttribute("message", "Please select a role for user " + firstName + " " + lastName +" ("+userName+")");
			return getUsers(model);    		
		}
		model.addAttribute("roleId", roleId);
		model.addAttribute("enabled", enabled != null);
		UserHelper.addUser(model);
		model.addAttribute("message", "User " + userName + " has been added successfully.");
		return getUsers(model);
    }
    
    @RequestMapping(method = RequestMethod.GET, value = { "/removeUser" })
	public String removeUser(@RequestParam(value = "userId") int userId, Model model) {
		UserHelper.removeUser(userId);
		return getUsers(model);
    }
    
    @RequestMapping(method = RequestMethod.POST, value = { "/modifyUser" })
	public String modifyUser(
			@RequestParam(value = "firstName") String firstName,
			@RequestParam(value = "lastName") String lastName,
			@RequestParam(value = "userName") String userName,
			@RequestParam(value = "password") String password,
			@RequestParam(required=false)  Integer roleId,
			@RequestParam(required=false) Boolean enabled, Model model) throws NoSuchAlgorithmException {
		int userId = (int) model.asMap().get("userId");
		model.addAttribute("firstName", firstName);
		model.addAttribute("lastName", lastName);
		model.addAttribute("userName", userName);
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(password.getBytes());
		byte[] digest = md.digest();
		StringBuffer sb = new StringBuffer();
		for (byte b : digest) {
			sb.append(String.format("%02x", b & 0xff));
		}
		model.addAttribute("password", sb.toString());
		if(roleId == null){
			model.addAttribute("message", "Please select a role for user " + firstName + " " + lastName +" ("+userName+")");
			return getUsers(model);    		
		}
		model.addAttribute("roleId", roleId);
		model.addAttribute("enabled", enabled != null);
		UserHelper.modifyUser(model, userId);
		model.addAttribute("message", "User " + userName + " has been successfully modified.");

		return "redirect:getUsers";
}
    
    @RequestMapping(method=RequestMethod.GET, value={"/enableUser"})
    public String enableUser(@RequestParam(value="userId") int userId, 
    						@RequestParam(value="enabled") boolean enabled, Model model) {
      	enabled = !enabled;
		model.addAttribute("enabled", enabled);
        return getUsers(model);
    }
    
    
    
    /*
     * Operator actions
     */
    
    
    @RequestMapping(method=RequestMethod.GET, value={"/getCustomers"})
        public String getCustomers(Model model, @RequestParam(required=false) String q) {
        	//Make sure the user accessing to this URL is logged in to the system.
        	if(!isLoggedIn(model)){
        		return "index";	
        	}
        	List<Customer> customers;
        	if(q != null){
    	    	customers = CustomerHelper.searchCustomer(q);
    	    	model.addAttribute("message","Showing search results for: " + q);
        	}else{
            	customers = CustomerHelper.getCustomers();        	    		
        	}
        	model.addAttribute("customers", customers);
            return "customers";
    }
    
    private boolean isLoggedIn(Model model) {
    	return model.asMap().get("username") != null;
    }

    @RequestMapping(method = RequestMethod.GET, value = { "/getCustomer" })
    	public String getCustomer(
    			@RequestParam(value = "customerId") int customerId, Model model) {
    		model.addAttribute("customer", CustomerHelper.getCustomer(customerId));
    		model.addAttribute("customerId", customerId);
    		return "modifyCustomer";
    }
    
    private String getCustomers(Model model) {
    	return getCustomers(model, null);
    }
    
    @RequestMapping(method = RequestMethod.GET, value = { "/removeCustomer" })
    	public String removeCustomer(@RequestParam(value = "customerId") int customerId, Model model) {
    		CustomerHelper.removeCustomer(customerId);
    		return getCustomers(model);
    }  
    
    @RequestMapping(method = RequestMethod.POST, value = { "/addCustomer" })
    	public String addCustomer(
    			@RequestParam(value = "firstName") String firstName,
    			@RequestParam(value = "lastName") String lastName,
    			@RequestParam(value = "email") String email,
    			@RequestParam(value = "phone") String phone,
    			@RequestParam(value = "company") String company, Model model) {
    		model.addAttribute("firstName", firstName);
    		model.addAttribute("lastName", lastName);
    		model.addAttribute("email", email);
    		model.addAttribute("phone", phone);
    		model.addAttribute("company", company);
    		CustomerHelper.addCustomer(model);
    		model.addAttribute("message", "Customer " + firstName + " " + lastName
    				+ " has been added successfully.");
    		return getCustomers(model);
    }
    
    @RequestMapping(method = RequestMethod.POST, value = { "/modifyCustomer" })
    	public String modifyCustomer(
    			@RequestParam(value = "firstName") String firstName,
    			@RequestParam(value = "lastName") String lastName,
    			@RequestParam(value = "email") String email,
    			@RequestParam(value = "phone") String phone,
    			@RequestParam(value = "company") String company, Model model) {
    		int customerId = (int) model.asMap().get("customerId");
    		model.addAttribute("firstName", firstName);
    		model.addAttribute("lastName", lastName);
    		model.addAttribute("email", email);
    		model.addAttribute("phone", phone);
    		model.addAttribute("company", company);
    		CustomerHelper.modifyCustomer(model, customerId);
    		model.addAttribute("message", "Customer " + firstName + " " + lastName
    				+ " has been successfully modified.");
    
    		return "redirect:getCustomers";
    }

}
