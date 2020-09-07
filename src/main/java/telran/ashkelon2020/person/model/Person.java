package telran.ashkelon2020.person.model;

import java.time.LocalDate;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = {"id"})
@Entity   // for JPA
//@Table(name = "persons")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)  // all inherited classes - into single tbl
//@Inheritance(strategy = InheritanceType.JOINED) //3 tbls - with fields of classes + ID-Foreign key to Person
//@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)  //all fields from parent and child classes in each table
public class Person {
	@Id   // for JPA
	Integer id;
	String name;
	LocalDate birthDate;
	
	@Embedded   // сущность персона, сама по себе быть не может - включается в состав тбл person
	Address address;

}
