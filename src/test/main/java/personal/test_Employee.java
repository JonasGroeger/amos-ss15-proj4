package personal;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class test_Employee {
	@Test
	public void generatedTokenIsInCorrectFormat() throws Exception {
		// Create Employee
		Employee emp = new Employee();
		EmployeeManager.getInstance().PersistEmployee(emp);
		// Generate Token
		EmployeeManager.getInstance().GenerateToken(emp);
		
		// Make sure that the Token is a 6 char long alphanumeric string.
		String GeneratedToken = emp.getToken();
		// Is the Token 6 char long?
		Assert.assertEquals(6, GeneratedToken.length());
		// Is the Token alphanumeric?
		Pattern p = Pattern.compile("([A-Z]|[0-9]){6}");
		Boolean IsTokenInCorrectFormat = p.matcher(GeneratedToken).matches();
		Assert.assertTrue(IsTokenInCorrectFormat);
	}
	
	@Test
	public void generatedTokensAreUnique() throws Exception {
		List<String> TokenList = new ArrayList<String>();
		
		// Generate 10 Tokens -> Each of them should be unique.
		for(int i = 0; i < 10; i++)
		{
   	       // Create Employee
		   Employee emp = new Employee();
		   EmployeeManager.getInstance().PersistEmployee(emp);
		   // Generate Token
		   EmployeeManager.getInstance().GenerateToken(emp);		   
		   String CurrentToken = emp.getToken();

		   // Make sure that the new Token is unique
		   for(String PreviousToken : TokenList)
		   {
			   if(CurrentToken.equals(PreviousToken))
			   {
				   // Fail: This token is not unique.
				   Assert.fail();
			   }
		   }
		   
		   TokenList.add(CurrentToken);
		}
	}
}
