package de.fau.amos4.web;

import de.fau.amos4.configuration.AppContext;
import de.fau.amos4.model.Client;
import de.fau.amos4.model.Employee;
import de.fau.amos4.model.fields.Disabled;
import de.fau.amos4.model.fields.MaritalStatus;
import de.fau.amos4.model.fields.Sex;
import de.fau.amos4.model.fields.Title;
import de.fau.amos4.service.ClientRepository;
import de.fau.amos4.service.EmployeeRepository;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Controller
public class EmployeeFormController
{
    private final EmployeeRepository employeeRepository;
    private final ClientRepository clientRepository;

    @Autowired
    public EmployeeFormController(EmployeeRepository employeeRepository, ClientRepository clientRepository)
    {
        this.employeeRepository = employeeRepository;
        this.clientRepository = clientRepository;
    }

    // Employee data form - Enter Employee data
    @RequestMapping("/EmployeeForm")
    public String EmployeeForm(Model model) throws Exception
    {
        model.addAttribute("employee", new Employee());
        model.addAttribute("allDisabled", Disabled.values());
        model.addAttribute("allMarital", MaritalStatus.values());
        model.addAttribute("allSex", Sex.values());
        return "EmployeeForm";
    }

    @RequestMapping("/EmployeeEdit")
    public ModelAndView EmployeeEdit(HttpServletResponse response, @RequestParam(value = "id") long employeeId, Model model) throws IOException
    {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("EmployeeEdit");
        Employee employee = employeeRepository.findOne(employeeId);
        mav.addObject("id", employeeId);
        mav.addObject("employee", employee);
        mav.addObject("allDisabled", Disabled.values());
        mav.addObject("allMarital", MaritalStatus.values());
        mav.addObject("allSex", Sex.values());
        //employeeRepository.save(employee);
        //model.addAttribute("EmployeeId", employeeId);
        return mav;
    }

    @RequestMapping("/EditSubmit")
    public String EditSubmit(Employee employee, Model model)
    {
        System.out.println(employee.getId());
        Client client = clientRepository.findOne(1l);
        employee.setClient(client);
        client.getEmployees().add(employee);

        employeeRepository.save(employee);
        clientRepository.save(client);

        // Redirect to EmployeeList page
        return "redirect:/EmployeeList";
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
        String fileContent = employee.toString();

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
        String fileContent = employee.toString();

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
        Client client = clientRepository.findOne(1l);

        Locale locale = LocaleContextHolder.getLocale();
        String newEmployee = AppContext.getApplicationContext().getMessage("EmployeeFormController.newEmployee", null, locale);
        employee.setFamilyName(newEmployee);
        employee.setClient(client);
        String token = TokenGenerator.getInstance().createUniqueToken(employeeRepository);
        employee.setToken(token);
        client.getEmployees().add(employee);

        employeeRepository.save(employee);
        clientRepository.save(client);


        // Redirect to EmployeeList page
        return "redirect:/EmployeeList";
    }

    @RequestMapping("/EmployeeList")
    public ModelAndView EmployeeList(@RequestParam(value = "id", defaultValue = "1") long clientId)
    {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("EmployeeList");

        Client client = clientRepository.findOne(clientId);
        Iterable<Employee> clientsEmployees = employeeRepository.findByClient(client);

        mav.addObject("Employees", clientsEmployees);
        return mav;
    }

    @RequestMapping("/ClientLogin")
    public ModelAndView ClientLogin(Model model) throws Exception
    {

        ModelAndView mav = new ModelAndView();
        return mav;
    }

    @RequestMapping("/FrontPage")
    public String FrontPage(Model model) throws Exception
    {

        return "FrontPage";
    }

    @RequestMapping("/RegistrationPage")
    public ModelAndView RegistrationPage(Model model) throws Exception
    {
        ModelAndView mav = new ModelAndView();

        mav.addObject("allTitle", Title.values());
        return mav;
    }
}
