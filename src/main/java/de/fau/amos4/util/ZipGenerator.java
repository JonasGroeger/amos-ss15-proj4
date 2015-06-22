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
package de.fau.amos4.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

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
import org.springframework.context.NoSuchMessageException;

import de.fau.amos4.configuration.AppContext;
import de.fau.amos4.model.Employee;

public class ZipGenerator {
	public void generate(OutputStream out, Locale locale, Map<String,String> fields, float height, Employee employee, int fontSize) throws ZipException, NoSuchMessageException, IOException, COSVisitorException, CloneNotSupportedException {
		final ZipOutputStream zout = new ZipOutputStream(out);

        
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

            // Write the zip to client
            zout.finish();
            zout.flush();
            zout.close();
	}
}
