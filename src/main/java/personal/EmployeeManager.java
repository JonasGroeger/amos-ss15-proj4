package personal;

import java.util.HashMap;
import java.util.Map;

public class EmployeeManager {
	
	// TODO: implement persistance using Hibernate
	private Map<Integer, Employee> storage = new HashMap<Integer, Employee>();
	
	private static EmployeeManager instance = new EmployeeManager();  
	public static EmployeeManager getInstance() {
		return instance;
	}
	
	int PersistEmployee(Employee employee)
	{		
		// TODO: Add exception when already in stored
		int nextKey = 0;
	    synchronized(this) {
		nextKey = storage.size() + 1;
		storage.put(nextKey, employee);
		employee.setId(nextKey);
	    }
		return nextKey;
	}
	
	Employee getEmployee(int id)
	{
	    return storage.get(id);	
	}
}
