package de.fau.amos4.service;

import de.fau.amos4.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Yao Bochao on 07/06/2015.
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository)
    {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Employee getEmployeeByToken(String token)
    {
        return this.employeeRepository.findOneByToken(token);
    }

    @Override
    public Iterable<Employee> getAllClients()
    {
        return this.employeeRepository.findAll();
    }

    @Override
    public Employee create(Employee employee)
    {
        return employeeRepository.save(employee);
    }
}
