package de.fau.amos4.util;
import java.util.Properties;

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
	
	public void SendEmail(String SendToEmailAddress, String Subject, String Content) throws AddressException, MessagingException
	   {	
		final String UserName = this.User;
		final String Password = this.Pass;

	      Session session = Session.getDefaultInstance(properties,
	  			new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(UserName, Password);
				}
			});
	      // Generate message
	      MimeMessage message = new MimeMessage(session);
	      message.setFrom(new InternetAddress(this.SenderEmail));
	      message.addRecipient(Message.RecipientType.TO, new InternetAddress(SendToEmailAddress));
	      message.setSubject(Subject);
	      message.setText(Content);
	      
	      // Send Email
	      Transport.send(message);
	   }
}
