package personal;

import hello.Person;
import hibernate.HibernateUtil;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.Transaction;

public class EmployeeManager {
	
	private Map<Integer, Employee> storage = new HashMap<Integer, Employee>();
	
	private static EmployeeManager instance = new EmployeeManager();  
	public static EmployeeManager getInstance() {
		return instance;
	}
	
	int PersistEmployee(Employee employee)
	{	
		// persist employee
		Session session = HibernateUtil.getSession();
		Transaction transaction = session.beginTransaction();
		session.save(employee);
		transaction.commit();
				
		// return id (primary key)
		return employee.getId();
	}
	
	Employee getEmployee(int id)
	{
		Session session = HibernateUtil.getSession();
		Employee loadedEmployee = (Employee) session.get(Employee.class, id);
		return loadedEmployee;
	}
}
