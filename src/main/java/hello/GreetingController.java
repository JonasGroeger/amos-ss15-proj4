package hello;

import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GreetingController {
	// Example for simple Hello Word site
	@RequestMapping("/greeting")
	public String greeting(
			@RequestParam(value = "name", required = false, defaultValue = "World") String name,
			Model model) {
		model.addAttribute("name", name);
		return "greeting";
	}

	// Example for text file download (http://localhost:8080/file)
	@RequestMapping("/file")
	public void export(HttpServletResponse response) throws IOException {

		String fileContent = "Hello";

		response.setContentType("text/plain");
		response.setHeader("Content-Disposition",
				"attachment;filename=myFile.txt");
		ServletOutputStream out = response.getOutputStream();
		out.println(fileContent);
		out.flush();
		out.close();
	}

	// Example for simple form (http://localhost:8080/form)
	@RequestMapping(value = "/form", method = RequestMethod.GET)
	public String displayName(Model model) {
		model.addAttribute("Name", "Anonymus");
		return "form";
	}

	@RequestMapping(value = "/form", method = RequestMethod.POST)
	public String submitForm(@RequestParam String Name, Model model) {
		model.addAttribute("Name", Name);
		return "form";
	}

	// Example for object mapping (http://localhost:8080/PersonEditor)
	@RequestMapping(value = "/PersonEditor", method = RequestMethod.GET)
	public String newPerson(Model model) {
		Person person = new Person();
		model.addAttribute("person", person);
		return "PersonEditor";
	}
		
	@RequestMapping(value = "/PersonEditor", method = RequestMethod.POST)
    public String editPerson(@ModelAttribute("person") Person person,
    		   BindingResult result) {
		  if(result.hasErrors())
		  {
			  System.out.println("Something went wrong.");			  
		  }
		  else
		  {
			  System.out.println("Person data loaded.");
			  System.out.println("Person name: " + person.Name);
			  System.out.println("Person age:" + person.Age);
		  }
		  
		  return "PersonEditor";
	}
	

}
