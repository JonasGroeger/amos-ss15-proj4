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
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.*;
import javax.mail.internet.*;

public class EmailSender {
	// Implements basic email sending functionality
	
	// The "from" Email address in the sent mesasges
	// TO DO: Configuration settings for Sender, MailServerHost and Credentials
	private String SenderEmail = "PersonalFragebogen2.0@gmail.com";	
	private String MailServerHost = "smtp.gmail.com";	
	private String MailServerPort = "465";
	
	// TO DO: Should be removed from source
	private String User = "PersonalFragebogen2.0";
	private String Pass = "dqwi23je34AS456Dasu";
	
	private Properties properties = System.getProperties();
	private Session session;
	
	public EmailSender()
	{
	      // Setup mail server
	      properties.setProperty("mail.smtp.host", this.MailServerHost);	
	      properties.setProperty("mail.user", this.User);
	      properties.setProperty("mail.password", this.Pass);
	      properties.put("mail.smtp.socketFactory.port", this.MailServerPort);
	      properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
	      properties.put("mail.smtp.auth", "true");
	      properties.put("mail.smtp.port", "465");
	}
	
	public void SendEmail(String SendToEmailAddress, String Subject, String HTMLContent, byte[] attachment) throws AddressException, MessagingException
	   {	
		final String UserName = this.User;
		final String Password = this.Pass;

	      Session session = Session.getDefaultInstance(properties,
	  			new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(UserName, Password);
				}
			});
	      
	      // Define message
	      MimeMessage message = new MimeMessage(session);
	      message.setFrom(new InternetAddress(this.SenderEmail));
	      message.addRecipient(Message.RecipientType.TO, new InternetAddress(SendToEmailAddress));
	      message.setSubject(Subject);

	      // create the message part 
	      MimeBodyPart messageBodyPart = new MimeBodyPart();

	      //fill message
	      messageBodyPart.setContent(HTMLContent, "text/html; charset=utf-8");;

	      Multipart multipart = new MimeMultipart();
	      multipart.addBodyPart(messageBodyPart);
	      
	      if (attachment != null) {
		      //TODO Add for loop for multiple attachments
		      // Part two is attachment
		      messageBodyPart = new MimeBodyPart();
		      messageBodyPart.setContent(attachment, "application/zip");
		      messageBodyPart.setFileName("Data.zip");
		      multipart.addBodyPart(messageBodyPart);
	      }

	      // Put parts in message
	      message.setContent(multipart);
	      
	      // Send Email
	      Transport.send(message);
	   }
}
