/**
 * Personalfragebogen 2.0. Revolutionize form data entry for taxation and
 * other purposes.
 * Copyright (C) 2015 Attila Bujaki, Werner Sembach, Jonas Gröger, Oswaldo
 *     Bejarano, Ardhi Sutadi, Nikitha Mohan, Benedikt Rauh
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.fau.amos4.web;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import de.fau.amos4.configuration.AppContext;
import de.fau.amos4.model.Client;
import de.fau.amos4.model.Employee;
import de.fau.amos4.model.fields.MaritalStatus;
import de.fau.amos4.model.fields.Title;
import de.fau.amos4.service.ClientRepository;
import de.fau.amos4.service.ClientService;
import de.fau.amos4.service.EmployeeRepository;
import de.fau.amos4.util.EmailSender;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.io.ZipOutputStream;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * Handles client related requests.
 */
@Controller
public class ClientController
{
    private final ClientService clientService;
    private final EmployeeRepository employeeRepository;
    private final ClientRepository clientRepository;

    @Autowired
    public ClientController(ClientService clientService, EmployeeRepository employeeRepository, ClientRepository clientRepository)
    {
        this.clientService = clientService;
        this.clientRepository = clientRepository;
        this.employeeRepository = employeeRepository;
    }

    /**
     * Show the client login page.
     * @return
     * @throws Exception
     */
    @RequestMapping(value =  "/", method = RequestMethod.GET)
    public ModelAndView ClientLogin(@RequestParam(value = "m", required=false, defaultValue = "")String message) throws Exception
    {
        ModelAndView mav = new ModelAndView();
        // Display the default login screen
        mav.setViewName("client/login");
        // Set the message to be displayed (invalid login, confirm success, confirm fail, registration done)
        mav.addObject("message", message);
    	
        return mav;
    }

    @RequestMapping(value = "/client/dashboard")
    public ModelAndView ClientDashboard(Principal principal)
    {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("client/dashboard");
        
        final String currentUser = principal.getName();
        Client client = clientService.getClientByEmail(currentUser);
        Iterable<Employee> clientsEmployees = employeeRepository.findByClient(client);

        mav.addObject("Employees", clientsEmployees);
        return mav;
    }
    
    @RequestMapping(value = "/client/profile")
    public ModelAndView ClientProfile(Principal principal)
    {
        ModelAndView mav = new ModelAndView();
        
        final String currentUser = principal.getName();
        Client client = clientService.getClientByEmail(currentUser);
        mav.addObject("Client", client);
        
        mav.setViewName("client/profile");
        return mav;
    }
    
    @RequestMapping(value = "/client/edit")
    public ModelAndView ClientEdit(Principal principal)
    {
        ModelAndView mav = new ModelAndView();
        
        final String currentUser = principal.getName();
        Client client = clientService.getClientByEmail(currentUser);
        mav.addObject("Client", client);
        mav.addObject("allTitles", Title.values());
        
        mav.setViewName("client/edit");
        return mav;
    }
    
    @RequestMapping(value = "/client/forgotPassword")
    public ModelAndView ClientForgotPassword(@RequestParam(value="email", required= false, defaultValue = "")String email) throws AddressException, MessagingException
    {
        ModelAndView mav = new ModelAndView();
        
        if(!email.equals(""))
        {
            // Create new password for user
            Client client = clientService.getClientByEmail(email);
            String newRandomPassword = RandomStringUtils.random(8, "ABCDEFGHJKLMNPQRSTUVWXYZ23456789");
            String newPasswordHash = new BCryptPasswordEncoder(4).encode(newRandomPassword);
            client.setPasswordHash(newPasswordHash);
            
            EmailSender Sender = new EmailSender();
            Sender.SendEmail(client.getEmail(), "PersonalFragebogen 2.0", "Your new password is: " + newRandomPassword, null);
            clientRepository.save(client);
            mav.setViewName("/client/login");
        }
        else
        {
            mav.setViewName("/client/forgotPassword");
        }
        return mav;
    }
    
    @RequestMapping(value = "/employee/email/send")
    public ModelAndView EmployeeEmailSend(@RequestParam(value="id")long id, @RequestParam(value="to")String to) throws AddressException, MessagingException, NoSuchMessageException, IOException
    {
        ModelAndView mav = new ModelAndView();
        
        Employee employee = employeeRepository.findOne(id);
        
        int fontSize=12;
        float height= 1;
        height = height*fontSize*1.05f;

        //Prepare textfile contents
        Locale locale = LocaleContextHolder.getLocale();
        Map<String,String> fields = employee.getFields();
        
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        final ZipOutputStream zout = new ZipOutputStream(out);
        
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
            zout.write((AppContext.getApplicationContext().getMessage("EmployeeForm.header", null, locale) + "\n\n").getBytes());
            //zout.println();
            zout.write((AppContext.getApplicationContext().getMessage("print.section.personalData", null, locale) + "\n\n").getBytes());
            //zout.println();
            Iterator it = fields.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry)it.next();
                zout.write((pair.getKey() + ": " + pair.getValue() + '\n').getBytes());
                it.remove(); // avoids a ConcurrentModificationException
            }
            zout.closeEntry();
            
            try {
                // Create a document and add a page to it
                PDDocument document = new PDDocument();
                PDPage page = new PDPage();
                document.addPage(page);
                float y = -1;
                int margin = 100;
                float maxStringLength = page.getMediaBox().getWidth() - 2*margin;

                // Create a new font object selecting one of the PDF base fonts
                PDFont font = PDType1Font.TIMES_ROMAN;

                // Start a new content stream which will "hold" the to be created content
                PDPageContentStream contentStream = new PDPageContentStream(document, page);

                // Define a text content stream using the selected font, moving the cursor and drawing the text "Hello World"
                contentStream.beginText();

                y = page.getMediaBox().getHeight() - margin + height;
                contentStream.moveTextPositionByAmount(margin, y);
                
                fields = employee.getFields();
                
                contentStream.setFont(PDType1Font.TIMES_BOLD, 36);
                contentStream.drawString(AppContext.getApplicationContext().getMessage("EmployeeForm.header", null, locale));
                contentStream.setFont(PDType1Font.TIMES_BOLD, 14);
                contentStream.moveTextPositionByAmount(0, -4 * height);
                contentStream.drawString(AppContext.getApplicationContext().getMessage("print.section.personalData", null, locale));
                contentStream.moveTextPositionByAmount(0, -2 * height);
                contentStream.setFont(font, fontSize);
                it = fields.entrySet().iterator();
                while (it.hasNext()) {
                    StringBuffer nextLineToDraw = new StringBuffer();
                    Map.Entry pair = (Map.Entry)it.next();
                    nextLineToDraw.append( pair.getKey());
                    nextLineToDraw.append(": ");
                    nextLineToDraw.append(pair.getValue());
                    
                    contentStream.drawString( nextLineToDraw.toString() );
                    contentStream.moveTextPositionByAmount(0, -height);
                    it.remove(); // avoids a ConcurrentModificationException
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
        }
        catch (ZipException e) {
            e.printStackTrace();
        }
        
        EmailSender sender = new EmailSender();
        sender.SendEmail(to, "Employee Data", "test", new ByteArrayInputStream(out.toByteArray()));
        
        mav.setViewName("/client/dashboard");
        return mav;
    }
}
