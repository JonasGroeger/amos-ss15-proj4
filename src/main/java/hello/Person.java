package hello;

import javax.persistence.*;


@Entity
@Table(name="Person")
public class Person {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	int id;
	String Name;
	int Age;

    public int getId() {
        return id;
    } 
    public void setId(int id) {
        this.id = id;
    }    
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public int getAge() {
		return Age;
	}
	public void setAge(int age) {
		Age = age;
	}
	
	public Person()
	{
		
	}
}
