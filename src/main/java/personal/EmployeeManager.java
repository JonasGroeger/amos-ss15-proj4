package personal;

import java.util.List;

import hibernate.HibernateUtil;

import org.hibernate.Session;
import org.hibernate.Transaction;

public class EmployeeManager {
	private static EmployeeManager instance = new EmployeeManager();
	public static EmployeeManager getInstance() {
		return instance;
	}

	// Save employee
	public int PersistEmployee(Employee employee)
	{
		// Persist employee
		Session session = HibernateUtil.getSession();
		Transaction transaction = session.beginTransaction();
		session.save(employee);
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
}
