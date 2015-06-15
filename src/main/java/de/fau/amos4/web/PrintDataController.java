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
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
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
        Map<String,String> fields = employee.getFields();
        Locale locale = LocaleContextHolder.getLocale();

        out.println(AppContext.getApplicationContext().getMessage("EmployeeForm.header", null, locale));
        out.println();
        out.println(AppContext.getApplicationContext().getMessage("print.section.personalData", null, locale));
        out.println();
        Iterator it = fields.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            out.println(pair.getKey() + ": " + pair.getValue());
            it.remove(); // avoids a ConcurrentModificationException
        }

        out.close();

    }

    // Employee zip file download - Download zip file containing a text with Employee data
    @RequestMapping("/employee/download/zip")
    public void EmployeeDownloadZip(HttpServletResponse response, @RequestParam(value = "id", required = true) long employeeId) throws IOException
    {
        int fontSize=12;
        float height= 1;
        height = height*fontSize*1.05f;

        //Prepare textfile contents
        Employee employee = employeeRepository.findOne(employeeId);
        Locale locale = LocaleContextHolder.getLocale();
        Map<String,String> fields = employee.getFields();

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
                /*
                List<String> list = StringUtils.splitEqually(fileContent, 90);
                for (String e : list) {
                    contentStream.moveTextPositionByAmount(0, -15);
                    contentStream.drawString(e);
                }
                */

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
        } catch (ZipException e) {
            e.printStackTrace();
        }
    }
}
