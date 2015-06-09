package de.fau.amos4.service;

import de.fau.amos4.model.Client;
import de.fau.amos4.model.Employee;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Long>
{
    List<Employee> findByClient(Client client);

    Employee findOneByToken(String token);
}
