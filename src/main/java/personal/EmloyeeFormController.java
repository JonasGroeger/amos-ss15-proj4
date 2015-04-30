package personal;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class EmloyeeFormController {
	// Employee data form - Enter Employee data
	@RequestMapping("/EmployeeForm")
	public String EmloyeeForm(){
		return "EmployeeForm";
	}
	
	// Exception handling - Display exception information
	@ExceptionHandler(Exception.class)
	public ModelAndView handleError(HttpServletRequest req, Exception exception) {
		// TODO: Add logging!
	    ModelAndView mav = new ModelAndView();
	    mav.addObject("url", req.getRequestURL());
	    mav.addObject("message", exception.getMessage());	   
	    // TODO: Implement stacktrace display function.
	    
	    return mav;
	  }
}
