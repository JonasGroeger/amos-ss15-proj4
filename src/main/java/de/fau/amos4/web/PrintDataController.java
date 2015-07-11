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

import de.fau.amos4.model.Client;
import de.fau.amos4.model.Employee;
import de.fau.amos4.service.ClientService;
import de.fau.amos4.service.EmployeeRepository;
import de.fau.amos4.service.EmployeeService;
import de.fau.amos4.util.ZipGenerator;
import net.lingala.zip4j.exception.ZipException;
import org.apache.pdfbox.exceptions.COSVisitorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.Locale;

@Controller
public class PrintDataController
{
    private final EmployeeRepository employeeRepository;
    private final ClientService clientService;
    private final EmployeeService employeeService;

    @Autowired
    public PrintDataController(EmployeeRepository employeeRepository, ClientService clientService,
                               EmployeeService employeeService)
    {
        this.employeeRepository = employeeRepository;
        this.clientService = clientService;
        this.employeeService = employeeService;
    }

    // Employee file download - Download text file with Employee data
    @RequestMapping("/employee/download/text")
    public ModelAndView EmployeeDownloadText(HttpServletResponse response,
                                             @RequestParam(value = "id", required = true) long employeeId
    ) throws IOException
    {
        // Use the service to get the Employee in the LODAS format
        final String employeeAsLodas = employeeService.getLodasRepresentation(employeeId);

        if(employeeAsLodas == null)
        {
            return new ModelAndView("redirect:/client/dashboard");
        }

        // We want to have a txt file download
        response.setContentType("text/plain");
        response.setHeader("Content-Disposition", "attachment;filename=employee_as_lodas.txt");
        response.setCharacterEncoding("UTF-8");

        // Write the data out
        ServletOutputStream out = response.getOutputStream();
        out.write(employeeAsLodas.getBytes());
        out.flush();
        out.close();
        return null;
    }

    // Employee zip file download - Download zip file containing a text with Employee data
    @RequestMapping("/employee/download/zip")
    public void EmployeeDownloadZip(Principal principal, HttpServletResponse response, @RequestParam(value = "id", required = true) long employeeId) throws IOException, NoSuchMessageException, COSVisitorException, ZipException, CloneNotSupportedException
    {
    	final String currentUser = principal.getName();
        Client currentClient = clientService.getClientByEmail(currentUser);
        
        int fontSize=12;
        float height= 1;
        height = height*fontSize*1.05f;

        //Prepare textfile contents
        Employee employee = employeeRepository.findOne(employeeId);
        Locale locale = LocaleContextHolder.getLocale();
        response.setContentType("application/zip");
        response.setHeader("Content-Disposition", "attachment;filename=employee.zip");
        
        ZipGenerator zipGenerator = new ZipGenerator();
        
        zipGenerator.generate(response.getOutputStream(), locale, height, employee, fontSize, currentClient.getZipPassword());
    }
}
