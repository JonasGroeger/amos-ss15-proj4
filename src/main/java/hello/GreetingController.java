package hello;

import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GreetingController
{
    @RequestMapping("/greeting")
    public String greeting(@RequestParam(value = "name", required = false, defaultValue = "World") String name, Model model)
    {
        model.addAttribute("name", name);
        return "greeting";
    }
    
    // Example for text file download (http://localhost:8080/file)
	@RequestMapping("/file")
	public void export(HttpServletResponse response) throws IOException {

		String fileContent = "Hello";

		response.setContentType("text/plain");
		response.setHeader("Content-Disposition", "attachment;filename=myFile.txt");
		ServletOutputStream out = response.getOutputStream();
		out.println(fileContent);
		out.flush();
		out.close();
	}

}
