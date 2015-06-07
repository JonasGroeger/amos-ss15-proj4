package de.fau.amos4.service;

import de.fau.amos4.model.Employee;

/**
 * Created by Yao Bochao on 07/06/2015.
 */
public interface EmployeeService {
    Employee getEmployeeByToken(String token);

    Iterable<Employee> getAllClients();

    Employee create(Employee employee);
}
