package de.fau.amos4.web;

import java.util.regex.*;

import org.hibernate.annotations.Target;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginFormController {

    @RequestMapping(value = "/Login", method = RequestMethod.POST)    
    public String Login(@RequestParam(value = "username", required = true) String userName, @RequestParam(value = "password", required = true) String password)
    {
    	boolean LoginValid = false;
    	
    	// Check username & password: "ClientID", "PassID" format should be accepted. Eg.: "Client32" and "Pass32" are OK. 
        String patternString = "Client([1-9]*)";
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(userName);
        boolean matches = matcher.matches();
        
        if(matches)
        {
        	// UserName is in correct format -> check password.
        	String ClientNumber = matcher.group(1);
        	
        	// TODO: Implement real username and password handling
        	boolean PasswordIsValid = password.equals("Pass" + ClientNumber);
        	if(PasswordIsValid)
        	{
        		LoginValid = true;
        	}
        	else
        	{
        		LoginValid = false;
        	}
        }
    	
        if(LoginValid)
        {
        	// Valid Login -> Redirect to EmployeeList
    	    return "redirect:/EmployeeList";
        }
        else
        {
        	// Valid Login -> Redirect to Login Page
    	    return "redirect:/";
        }
    }
}
