package de.fau.amos4.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
public class Client
{
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @OneToMany(mappedBy="client")
    private List<Employee> employees;

    @Column
    String companyName;

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public List<Employee> getEmployees()
    {
        return employees;
    }

    public void setEmployees(List<Employee> employees)
    {
        this.employees = employees;
    }

    public String getCompanyName()
    {
        return companyName;
    }

    public void setCompanyName(String companyName)
    {
        this.companyName = companyName;
    }
}
