package de.fau.amos4.test;

import org.junit.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.fau.amos4.test.configuration.TestConfiguration;
import de.fau.amos4.util.EmailSender;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TestConfiguration.class)
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
