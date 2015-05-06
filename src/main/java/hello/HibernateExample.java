package hello;

import hibernate.HibernateUtil;

import org.hibernate.*;
import org.hibernate.cfg.AnnotationConfiguration;

public class HibernateExample {

	// Example with hibernate ORM. How to persist a single instance of an annotated class.
	public static void main(String [] args)
	{
		System.out.println("Starting hibernate example");

		// Create object
		Person person = new Person();
		person.Age = 18;
		person.Name = "John Small";

		// Persist object
		Session session = HibernateUtil.getSession();
		Transaction transaction = session.beginTransaction();
		session.save(person);
		transaction.commit();

		// Load saved object
		int id = person.getId();
		Person loadedPerson = (Person) session.get(Person.class, id);

		// Print loaded object data
		String message = String.format("Loaded person with id: %s Name: '%s' and Age: %s",
				                       loadedPerson.getId(),
				                       loadedPerson.getName(),
				                       loadedPerson.getAge());
		System.out.println(message);
	}
}
