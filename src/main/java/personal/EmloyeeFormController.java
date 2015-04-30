package personal;

import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	public String EmloyeeSubmit(Model model, HttpServletRequest request){
		Employee employee = new Employee();
		employee.setFamilyName(request.getParameter("lastname"));
		employee.setFirstName(request.getParameter("firstname"));
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
		// TO DO: fill the file Content to make sure it contains actual Employee personal data.
		Employee employee = EmployeeManager.getInstance().getEmployee(employeeId);
		
		String fileContent = "[EMPLOYEE DATA] ID: " + employee.Id + " Name:" + employee.FirstName + " " + employee.FamilyName;

		response.setContentType("text/plain");
		response.setHeader("Content-Disposition",
				"attachment;filename=myFile.txt");
		ServletOutputStream out = response.getOutputStream();
		out.println(fileContent);
		out.flush();
		out.close();
	}
}
