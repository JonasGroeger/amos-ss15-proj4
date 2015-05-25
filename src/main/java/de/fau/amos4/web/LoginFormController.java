package de.fau.amos4.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

public class LoginFormController {

    @RequestMapping("/Login")
    public String Login(@RequestParam(value = "username", required = true) String userName, @RequestParam(value = "password", required = true) String password)
    {
    	// TODO: implement
    	
    	return "";
    }
}
