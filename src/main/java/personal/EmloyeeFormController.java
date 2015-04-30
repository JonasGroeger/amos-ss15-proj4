package personal;

import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class EmloyeeFormController {
	// Employee data form - Enter Employee data
	@RequestMapping("/EmployeeForm")
	public String EmloyeeForm(Model model){
		Employee employee = new Employee();
		model.addAttribute("employee", employee);
		return "EmployeeForm";
	}
	
	// Employee data review - Review Employee data
	@RequestMapping(value = "/EmployeePreview", method = RequestMethod.POST)
	public String EmloyeeReview(@ModelAttribute("employee") Employee employee,
	 		   BindingResult result, Model model){
		
		return "EmployeeReview";
	}
	
	// Employee data submit - Submit Employee data
	@RequestMapping("/EmployeeSubmit")
	public String EmloyeeSubmit(@ModelAttribute("employee") Employee employee,
 		   BindingResult result, Model model){
		EmployeeManager employeeManager = EmployeeManager.getInstance();
		int EmployeeId = employeeManager.PersistEmployee(employee);
		
		model.addAttribute("EmployeeId", EmployeeId + "");
		
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
	
	// Employee file download - Download text file with Employee data
	@RequestMapping("/EmployeeTextFileDownload")
	public void EmployeeTextFileDownload(HttpServletResponse response, @RequestParam(value = "id", required = true) int employeeId) throws IOException {
		//Prepare textfile contents
		Employee employee = EmployeeManager.getInstance().getEmployee(employeeId);		
		String fileContent = employee.getText();

		// Send file contents
		response.setContentType("text/plain");
		response.setHeader("Content-Disposition",
				"attachment;filename=myFile.txt");
		ServletOutputStream out = response.getOutputStream();
		out.println(fileContent);
		out.flush();
		out.close();
	}
}
