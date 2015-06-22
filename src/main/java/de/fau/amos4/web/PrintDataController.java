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

import de.fau.amos4.configuration.AppContext;
import de.fau.amos4.model.Employee;
import de.fau.amos4.service.ClientRepository;
import de.fau.amos4.service.EmployeeRepository;
import de.fau.amos4.util.StringUtils;
import de.fau.amos4.util.ZipGenerator;
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
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

/**
 * Created by Yao Bochao on 06/06/2015.
 */

@Controller
public class PrintDataController {

    private final EmployeeRepository employeeRepository;
    private final ClientRepository clientRepository;

    @Autowired
    public PrintDataController(EmployeeRepository employeeRepository, ClientRepository clientRepository)
    {
        this.employeeRepository = employeeRepository;
        this.clientRepository = clientRepository;
    }

    // Employee file download - Download text file with Employee data
    @RequestMapping("/employee/download/text")
    public void EmployeeDownloadText(HttpServletResponse response, @RequestParam(value = "id", required = true) long employeeId) throws IOException
    {

        Employee employee = employeeRepository.findOne(employeeId);
        // Send file contents
        response.setContentType("text/plain");
        response.setHeader("Content-Disposition", "attachment;filename=myFile.txt");

        ServletOutputStream out = response.getOutputStream();
        Map<String,String> fields = employee.getPersonalDataFields();
        Locale locale = LocaleContextHolder.getLocale();

        out.println(AppContext.getApplicationContext().getMessage("HEADER", null, locale));
        out.println();
        out.println(AppContext.getApplicationContext().getMessage("employeeEdit.personalDataSection", null, locale));
        out.println();
        Iterator it = fields.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            out.println(pair.getKey() + ": " + pair.getValue());
            it.remove(); // avoids a ConcurrentModificationException
        }

        fields = employee.getTaxesFields();
        it = fields.entrySet().iterator();
        out.println();
        out.println(AppContext.getApplicationContext().getMessage("employeeEdit.taxesSection", null, locale));
        out.println();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            out.println(pair.getKey() + ": " + pair.getValue());
            it.remove(); // avoids a ConcurrentModificationException
        }


        out.close();

    }

    // Employee zip file download - Download zip file containing a text with Employee data
    @RequestMapping("/employee/download/zip")
    public void EmployeeDownloadZip(HttpServletResponse response, @RequestParam(value = "id", required = true) long employeeId) throws IOException, NoSuchMessageException, COSVisitorException, ZipException, CloneNotSupportedException
    {
        int fontSize=12;
        float height= 1;
        height = height*fontSize*1.05f;

        //Prepare textfile contents
        Employee employee = employeeRepository.findOne(employeeId);
        Locale locale = LocaleContextHolder.getLocale();
        response.setContentType("application/zip");
        response.setHeader("Content-Disposition", "attachment;filename=employee.zip");
        
        ZipGenerator zipGenerator = new ZipGenerator();
        zipGenerator.generate(response.getOutputStream(), locale, height, employee, fontSize);
    }
}
