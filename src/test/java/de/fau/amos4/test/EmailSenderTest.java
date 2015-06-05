package de.fau.amos4.test;

import org.junit.Assert;

import org.junit.Test;
import de.fau.amos4.util.EmailSender;

public class EmailSenderTest {

    @Test
    public void SendDummyEmail_NoExceptionsThrown() throws Exception
    {
    	 Boolean NoExceptions = true; 
    	
    	 EmailSender sender = new EmailSender();
    	 try
    	 {
    	    sender.SendEmail("Test@gmail.com", "Test", "Test Content");    	 
    	 }
    	 catch(Exception ex)
    	 {
    		 NoExceptions = false;
    	 }
    	 
    	 Assert.assertTrue(NoExceptions);
    }
    
}
