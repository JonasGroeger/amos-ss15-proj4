package personal;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
	
	// Employee data review - Review Employee data
	@RequestMapping("/EmployeeReview")
	public String EmloyeeReview(Model model, HttpServletRequest request){
		java.util.Enumeration<String> reqEnum = request.getParameterNames();		
		while (reqEnum.hasMoreElements()) {
			String paramName = reqEnum.nextElement();
			String paramValue = request.getParameter(paramName).toString();
			System.out.println(paramName + "==" + paramValue); // TO DO: Add debug logging instead of println
			model.addAttribute(paramName, paramValue);
		}
 
		return "EmployeeReview";
	}
	
	// Employee data submit - Submit Employee data
	@RequestMapping("/EmployeeSubmit")
	public String EmloyeeSubmit(Model model){ 
		return "EmployeeSubmit";
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
