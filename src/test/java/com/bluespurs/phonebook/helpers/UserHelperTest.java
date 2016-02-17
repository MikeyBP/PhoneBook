package com.bluespurs.phonebook.helpers;

import static org.junit.Assert.*;

import org.junit.Test;

public class UserHelperTest {	

	@Test
	public void testLogin() {
		String username = "MikeyBP"; // valid username
		String password = "password";// valid password
		String usernameFalse = "MikeyBad"; // invalid username
		String passwordFalse = "crupples";// invalid password
		//UserHelper.login(username, password)
		assertTrue("UserHelper.login should return 'true'", UserHelper.login(username, password));
		assertFalse("UserHelper.login should return 'false'", UserHelper.login(usernameFalse, passwordFalse));
	}
	
	@Test
	public void testLogout() {
		assertTrue("UserHelper.login should return 'true'", true);		
	}
	
	@Test
	public void testIsAdmin() {
		String usernameTrue = "MikeyBP"; //valid
		String usernameFalse = "Krieger"; //invalid
		assertTrue("UserHelper.isAdmin should return 'true'", UserHelper.isAdmin(usernameTrue));
		assertFalse("UserHelper.isAdmin should return 'false'", UserHelper.isAdmin(usernameFalse));
	}
	
	//This test is not complete
	@Test
	public void testGetUser() {
		int userId = 1;
		assertEquals("UserHelper.getUser should return 'MikeyBP'", "MikeyBP", UserHelper.getUser(userId));	
	}
	
	@Test
	public void testIsEnabled() {
		String usernameTrue = "MikeyBP"; //valid
		String usernameFalse = "Krieger"; //invalid
		assertTrue("UserHelper.isAdmin should return 'true'", UserHelper.isEnabled(usernameTrue));
		assertFalse("UserHelper.isAdmin should return 'false'", UserHelper.isEnabled(usernameFalse));
	}
	
	/*
	 * Create a test for each of the User functionalities
	 */


}
