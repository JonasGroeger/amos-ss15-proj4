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
