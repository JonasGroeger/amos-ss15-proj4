package de.fau.amos4.web;

import de.fau.amos4.model.Employee;
import de.fau.amos4.service.EmployeeRepository;
import de.fau.amos4.model.fields.Disabled;
import de.fau.amos4.model.fields.MaritalStatus;
import de.fau.amos4.model.fields.Sex;
import de.fau.amos4.util.StringUtils;
import de.fau.amos4.util.TokenGenerator;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.io.ZipOutputStream;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class EmployeeFormController
{
    @Resource
    EmployeeRepository employeeRepository;

    // Login form
    @RequestMapping("/")
    public String LoginForm(Model model) throws Exception
    {
        return "EmployeeLogin";
    }
    
    // Employee data form - Enter Employee data
    @RequestMapping("/EmployeeForm")
    public String EmployeeForm(Model model) throws Exception
    {
        model.addAttribute("employee", new Employee());
        model.addAttribute("allDisabled", Disabled.values());
        model.addAttribute("allMarital", MaritalStatus.values());
        model.addAttribute("allSex", Sex.values());
        System.out.println("EmployeeForm");
        return "EmployeeForm";
    }

    @InitBinder
    public void initBinder(WebDataBinder binder)
    {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("dd/MM/yyyy"), true, 10));
    }

    // Employee data review - Review Employee data
    @RequestMapping(value = "/EmployeePreview", method = {RequestMethod.POST, RequestMethod.GET})
    public String EmployeeReview(@ModelAttribute("employee") Employee employee, BindingResult result, Model model)
    {
        model.addAttribute("allDisabled", Disabled.values());
        model.addAttribute("allMarital", MaritalStatus.values());
        model.addAttribute("allSex", Sex.values());
        return "EmployeeReview";
    }

    // Employee data submit - Submit Employee data
    @RequestMapping("/EmployeeSubmit")
    public String EmployeeSubmit(@ModelAttribute("employee") Employee employee,
                                 BindingResult result, Model model) throws Exception
    {
        // TODO: Move this to a service layer later.
        String token = TokenGenerator.getInstance().createUniqueToken(employeeRepository);
        employee.setToken(token);

        // If the employee is new: Create
        // If the employee already has a primary key: Update
        Employee newOrUpdatedEmployee = employeeRepository.save(employee);

        // Setup modell and return view
        model.addAttribute("EmployeeId", newOrUpdatedEmployee.getId());
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
    public void EmployeeTextFileDownload(HttpServletResponse response, @RequestParam(value = "id", required = true) long employeeId) throws IOException
    {
        Employee employee = employeeRepository.findOne(employeeId);
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
    public void EmployeeZipFileDownload(HttpServletResponse response, @RequestParam(value = "id", required = true) long employeeId) throws IOException
    {
        //Prepare textfile contents
        Employee employee = employeeRepository.findOne(employeeId);
        String fileContent = employee.dump();

        response.setContentType("application/zip");
        response.setHeader("Content-Disposition", "attachment;filename=employee.zip");
        
        final StringBuilder sb = new StringBuilder(fileContent);
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
            params.setSourceExternalStream(true);
            
            zout.putNextEntry(null, params);
            byte[] data = sb.toString().getBytes();
            zout.write(data, 0, data.length);
            zout.closeEntry();

            // Create temporary file for shitty API
            //File temp = File.createTempFile("employee", ".txt");
            //FileOutputStream fOut = new FileOutputStream(temp);
            //fOut.write(fileContent.getBytes(StandardCharsets.UTF_8));
            //fOut.flush();


            try {
                // Create a document and add a page to it
                PDDocument document = new PDDocument();
                PDPage page = new PDPage();
                document.addPage(page);

                // Create a new font object selecting one of the PDF base fonts
                PDFont font = PDType1Font.COURIER;

                // Start a new content stream which will "hold" the to be created content
                PDPageContentStream contentStream = new PDPageContentStream(document, page);

                // Define a text content stream using the selected font, moving the cursor and drawing the text "Hello World"
                contentStream.beginText();
                contentStream.setFont(font, 10);
                contentStream.moveTextPositionByAmount(10, 700);

                List<String> list = StringUtils.splitEqually(fileContent, 90);
                for (String e : list) {
                    contentStream.moveTextPositionByAmount(0, -15);
                    contentStream.drawString(e);
                }
                contentStream.endText();

                // Make sure that the content stream is closed:
                contentStream.close();

                // Save the results and ensure that the document is properly closed:
                ByteArrayOutputStream pdfout = new ByteArrayOutputStream();
                document.save(pdfout);
                document.close();


                ZipParameters params2 = (ZipParameters) params.clone();
                params2.setFileNameInZip("employee.pdf");

                zout.putNextEntry(null, params2);
                zout.write(pdfout.toByteArray());
                zout.closeEntry();
            } catch (CloneNotSupportedException | COSVisitorException e) {
                e.printStackTrace();
            }

            // Write the zip to client
            zout.finish();
            zout.flush();
            zout.close();
        } catch (ZipException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/EmployeeDelete")
    public String EmployeeDelete(@RequestParam(value = "id", required = true) long employeeId)
    {
         // Remove employee with passed id
         this.employeeRepository.delete(employeeId);
     
         // Redirect to EmployeeList page
         return "redirect:/EmployeeList";
    }

    @RequestMapping("/NewEmployee")
    public String NewEmployee()
    {
    	// Create a new employee with default name
    	Employee employee = new Employee();
    	employee.setFamilyName("New Employee");
    	
    	employeeRepository.save(employee);
    	
         // Redirect to EmployeeList page
         return "redirect:/EmployeeList";
    }
    
    @RequestMapping("/EmployeeList")
    public ModelAndView EmployeeList()
    {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("EmployeeList");

        Iterable<Employee> allEmployees = employeeRepository.findAll();
        mav.addObject("Employees", allEmployees);

        return mav;
    }
    @RequestMapping("/EmployeeLogin")
    public ModelAndView EmployeeLogin()
    {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("EmployeeLogin");
        return mav;
    }
    
}
