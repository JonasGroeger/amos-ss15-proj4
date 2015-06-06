package de.fau.amos4.web;

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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

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
    @RequestMapping("/EmployeeTextFileDownload")
    public void EmployeeTextFileDownload(HttpServletResponse response, @RequestParam(value = "id", required = true) long employeeId) throws IOException
    {

        Employee employee = employeeRepository.findOne(employeeId);
        String fileContent = employee.toString();
        System.out.println(fileContent);
        String hallo = "Hallo World!";
        // Send file contents
        response.setContentType("text/plain");
        response.setHeader("Content-Disposition", "attachment;filename=myFile.txt");

        ServletOutputStream out = response.getOutputStream();
        out.println(hallo);

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
}
