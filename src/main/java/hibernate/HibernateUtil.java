package hibernate;

import hello.Person;

import org.hibernate.*;
import org.hibernate.cfg.*;

import personal.Employee;

@SuppressWarnings("deprecation")
public class HibernateUtil {

private static final SessionFactory sessionFactory;
    static {
        try {
        	sessionFactory = new AnnotationConfiguration()
            .addPackage("hello") //the fully qualified package name
            .addAnnotatedClass(Person.class)            
            .addAnnotatedClass(Employee.class)            
            //.addResource("test/animals/orm.xml")
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