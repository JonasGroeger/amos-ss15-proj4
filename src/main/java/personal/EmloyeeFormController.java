package personal;

import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.io.ZipOutputStream;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import personal.fields.Disabled;
import personal.fields.MartialStatus;
import personal.fields.Sex;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class EmloyeeFormController
{
    // Employee data form - Enter Employee data
    @RequestMapping({"/", "/EmployeeForm"})
    public String EmloyeeForm(Model model)
    {
        model.addAttribute("employee", new Employee());
        model.addAttribute("allDisabled", Disabled.values());
        model.addAttribute("allMartial", MartialStatus.values());
        model.addAttribute("allSex", Sex.values());
        return "EmployeeForm";
    }

    @InitBinder
    public void initBinder(WebDataBinder binder)
    {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("dd/MM/yyyy"), true, 10));
    }

    // Employee data review - Review Employee data
    @RequestMapping(value = "/EmployeePreview", method = RequestMethod.POST)
    public String EmloyeeReview(@ModelAttribute("employee") Employee employee, BindingResult result, Model model)
    {
        model.addAttribute("allDisabled", Disabled.values());
        model.addAttribute("allMartial", MartialStatus.values());
        model.addAttribute("allSex", Sex.values());
        return "EmployeeReview";
    }

    // Employee data submit - Submit Employee data
    @RequestMapping("/EmployeeSubmit")
    public String EmloyeeSubmit(@ModelAttribute("employee") Employee employee,
                                BindingResult result, Model model)
    {
        EmployeeManager employeeManager = EmployeeManager.getInstance();
        int EmployeeId = employeeManager.PersistEmployee(employee);

        model.addAttribute("EmployeeId", EmployeeId + "");

        return "EmployeeSubmit";
    }

    // Exception handling - Display exception information
    @ExceptionHandler(Exception.class)
    public ModelAndView handleError(HttpServletRequest req, Exception exception)
    {
        // TODO: Add logging!
        ModelAndView mav = new ModelAndView();
        mav.addObject("url", req.getRequestURL());
        mav.addObject("message", exception.getMessage());
        // TODO: Implement stacktrace display function.

        return mav;
    }

    // Employee file download - Download text file with Employee data
    @RequestMapping("/EmployeeTextFileDownload")
    public void EmployeeTextFileDownload(HttpServletResponse response, @RequestParam(value = "id", required = true) int employeeId) throws IOException
    {
        //Prepare textfile contents
        Employee employee = EmployeeManager.getInstance().getEmployee(employeeId);
        String fileContent = employee.dump();

        // Send file contents
        response.setContentType("text/plain");
        response.setHeader("Content-Disposition", "attachment;filename=myFile.txt");
        ServletOutputStream out = response.getOutputStream();
        out.println(fileContent);
        out.close();
    }

    // Employee zip file download - Download zip file containing a text with Employee data
    @RequestMapping("/EmployeeZipFileDownload")
    public void EmployeeZipFileDownload(HttpServletResponse response, @RequestParam(value = "id", required = true) int employeeId) throws IOException
    {
        //Prepare textfile contents
        Employee employee = EmployeeManager.getInstance().getEmployee(employeeId);
        String fileContent = employee.dump();

        response.setContentType("application/zip");
        response.setHeader("Content-Disposition", "attachment;filename=employee.zip");

        final ZipOutputStream zout = new ZipOutputStream(response.getOutputStream());

        try {
            ZipParameters params = new ZipParameters();
            params.setFileNameInZip("employee.txt");
            params.setCompressionLevel(Zip4jConstants.COMP_DEFLATE);
            params.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_ULTRA);
            params.setEncryptFiles(true);
            params.setReadHiddenFiles(false);
            params.setEncryptionMethod(Zip4jConstants.ENC_METHOD_AES);
            params.setAesKeyStrength(Zip4jConstants.AES_STRENGTH_256);
            params.setPassword("AMOS");

            // Create temporary file for shitty API
            File temp = File.createTempFile("employee", ".txt");
            FileOutputStream fOut = new FileOutputStream(temp);
            fOut.write(fileContent.getBytes(StandardCharsets.UTF_8));
            fOut.flush();

            // Write the zip to client
            zout.putNextEntry(temp, params); // Why do you need a File, Mister API?
            zout.write(fileContent.getBytes(StandardCharsets.UTF_8));
            zout.closeEntry();
            zout.finish();
            zout.flush();
            zout.close();
        } catch (ZipException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/EmployeeList")
    public ModelAndView EmployeeList()
    {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("EmployeeList");

        List<Employee> AllEmployees = EmployeeManager.getInstance().getAllEmployees();
        mav.addObject("Employees", AllEmployees);

        return mav;
    }
}
