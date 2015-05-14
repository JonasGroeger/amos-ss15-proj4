package personal;

import hibernate.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.security.SecureRandom;
import java.util.List;

public class EmployeeManager
{
    private static EmployeeManager instance = new EmployeeManager();
    private static char[] TOKEN_CHARSET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
    private static final Object TokenGeneratorLock = new Object();
    private SecureRandom random = new SecureRandom();

    public static EmployeeManager getInstance()
    {
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
        return (Employee) session.get(Employee.class, id);
    }

    public List<Employee> getAllEmployees()
    {
        Session session = HibernateUtil.getSession();
        return session.createCriteria(Employee.class).list();
    }

    public void GenerateToken(Employee employee) throws Exception
    {
        if (employee.id == 0) {
            throw new IllegalArgumentException(
                    "The passed employee instance has no id set. Please make sure that this employee instance is already persisted before generating Token");
        }

        synchronized (EmployeeManager.TokenGeneratorLock) {
            employee.token = this.getNewToken();
            this.PersistEmployee(employee);
        }
    }

    private String randomString(char[] characterSet, int length)
    {
        char[] result = new char[length];
        for (int i = 0; i < result.length; i++) {
            int randomCharIndex = this.random.nextInt(characterSet.length);
            result[i] = characterSet[randomCharIndex];
        }
        return new String(result);
    }

    private String getNewToken() throws Exception
    {
        String TokenCandidate;
        Boolean IsTokenUnique;
        int i = 0;

        do {
            i++;
            // Generate new token
            TokenCandidate = randomString(TOKEN_CHARSET, 6);
            IsTokenUnique = true;

            // Make sure that it is unique
            for (Employee employee : this.getAllEmployees()) {
                if (employee.token.equals(TokenCandidate)) {
                    IsTokenUnique = false;
                }

                // Stop after many tries
                if (i > 10000) {
                    throw new Exception("Can't create new unique token.");
                }

            }
        } while (!IsTokenUnique);

        return TokenCandidate;
    }
}
