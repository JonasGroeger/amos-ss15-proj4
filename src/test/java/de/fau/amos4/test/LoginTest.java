package de.fau.amos4.test;

import org.junit.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.fau.amos4.test.configuration.TestConfiguration;
import de.fau.amos4.web.LoginFormController;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TestConfiguration.class)
public class LoginTest {

    @Test
    public void Login_InvalidLoginAndPassword() throws Exception
    {
    	LoginFormController loginFormController = new LoginFormController();
    	
    	String userName = "WrongUser";
    	String password = "WrongPassword";
    	
    	String result = loginFormController.Login(userName, password);

    	Boolean LoggedIn = result.contains("EmployeeList");
    	Assert.assertFalse(LoggedIn);
    }
    
    @Test
    public void Login_ValidUserAndInvalidPassword() throws Exception
    {
    	LoginFormController loginFormController = new LoginFormController();
    	
    	String userName = "Client15";
    	String password = "Password16";
    	
    	String result = loginFormController.Login(userName, password);
    	
    	Boolean LoggedIn = result.contains("EmployeeList");
    	Assert.assertFalse(LoggedIn);
    }
    
    @Test
    public void Login_ValidLogind() throws Exception
    {
    	LoginFormController loginFormController = new LoginFormController();
    	
    	String userName = "Client1932";
    	String password = "Password1932";
    	
    	String result = loginFormController.Login(userName, password);
    	
    	Boolean LoggedIn = result.contains("EmployeeList");
    	Assert.assertTrue(LoggedIn);
    }
}
