package personal;

import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.io.ZipOutputStream;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;
import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.io.IOUtils;
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

import personal.fields.Disabled;
import personal.fields.MaritalStatus;
import personal.fields.Sex;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class EmployeeFormController
{

    public static List<String> splitEqually(String text, int size)
    {
        // Give the list the right capacity to start with. You could use an array
        // instead if you wanted.
        List<String> ret = new ArrayList<String>((text.length() + size - 1) / size);

        for (int start = 0; start < text.length(); start += size) {
            ret.add(text.substring(start, Math.min(text.length(), start + size)));
        }
        return ret;
    }


    // Employee data form - Enter Employee data
    @RequestMapping({"/", "/EmployeeForm"})
    public String EmployeeForm(
    		 Model model) throws Exception
    {
    	//LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
    	//localeResolver.setLocale(request, response, StringUtils.parseLocaleString("de"));
    	
        //WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
        //ctx.setVariable("today", Calendar.getInstance());
        

    	
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

    @RequestMapping(value = "/EmployeePreview", method = RequestMethod.POST)
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


            try {
                File t = File.createTempFile("employee", ".pdf");

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

                List<String> list = splitEqually(fileContent, 90);
                for (String e : list) {
                    contentStream.moveTextPositionByAmount(0, -15);
                    contentStream.drawString(e);
                }
                contentStream.endText();

                // Make sure that the content stream is closed:
                contentStream.close();

                // Save the results and ensure that the document is properly closed:
                document.save(t);
                document.close();

                ZipParameters params2 = (ZipParameters) params.clone();
                params2.setFileNameInZip("employee.pdf");

                zout.putNextEntry(t, params2);
                zout.write(IOUtils.toByteArray(new FileInputStream(t)));
                zout.closeEntry();
            } catch (CloneNotSupportedException | COSVisitorException e) {
                e.printStackTrace();
            }


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
