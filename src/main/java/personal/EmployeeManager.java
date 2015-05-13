package personal;

import java.util.List;
import java.util.Random;
import java.math.BigInteger;
import java.security.SecureRandom;

import hibernate.HibernateUtil;

import org.hibernate.Session;
import org.hibernate.Transaction;

public class EmployeeManager {
	private static EmployeeManager instance = new EmployeeManager();
	private SecureRandom random = new SecureRandom();
	// List of characters allowed in tokens
	private static char[] TOKEN_CHARSET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();

	public static EmployeeManager getInstance() {
		return instance;
	}

	// Save employee
	public int PersistEmployee(Employee employee)
	{
		// Persist employee
		Session session = HibernateUtil.getSession();
		Transaction transaction = session.beginTransaction();
		session.saveOrUpdate(employee);
		transaction.commit();

		// Return id (primary key)
		return employee.getId();
	}

	// Load employee with given id.
	public Employee getEmployee(int id)
	{
		Session session = HibernateUtil.getSession();
		Employee loadedEmployee = (Employee) session.get(Employee.class, id);
		return loadedEmployee;
	}
	
	public List<Employee> getAllEmployees()
	{
		Session session = HibernateUtil.getSession();
		List<Employee> list = session.createCriteria(Employee.class).list();
		return list;
	}

	public void GenerateToken(Employee employee)
	{
		if(employee.id == 0)
		{
			throw new IllegalArgumentException("The passed employee instance has no id set. Please make sure that this employee instance is already persisted before generating Token");
		}
		
		synchronized(EmployeeManager.TokenGeneratorLock)
		{
		    employee.token = this.getNewToken();
		    this.PersistEmployee(employee);
		}
	}

	private String randomString(char[] characterSet, int length) {
	    char[] result = new char[length];
	    for (int i = 0; i < result.length; i++) {
	        int randomCharIndex = this.random.nextInt(characterSet.length);
	        result[i] = characterSet[randomCharIndex];
	    }
	    return new String(result);
	}
	
	private String getNewToken()
	{		
		String TokenCandidate;
		Boolean IsTokenUnique;
		do
		{
		   // Generate new token
           TokenCandidate = randomString(TOKEN_CHARSET, 6);
           IsTokenUnique = true;
           
		   // Make sure that it is unique
           for(Employee employee : this.getAllEmployees())
           {
        	   if (employee.token == TokenCandidate)
        	   {
        		   IsTokenUnique = false;
        	   }
           }        		   		
		}
		while(!IsTokenUnique);	   	   
	    
	    return TokenCandidate;	    		
	}
	
	private static Object TokenGeneratorLock = new Object();
	
}
