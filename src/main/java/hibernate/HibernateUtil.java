package hibernate;

import org.hibernate.*;
import org.hibernate.cfg.*;

import personal.Employee;

public class HibernateUtil {

private static final SessionFactory sessionFactory;
    static {
        try {
        	sessionFactory = new AnnotationConfiguration()
            .addAnnotatedClass(Employee.class)
            .configure()
            .buildSessionFactory();
        } catch (Throwable ex) {
            // Log exception!
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static Session getSession()
            throws HibernateException {
        return sessionFactory.openSession();
    }
}
