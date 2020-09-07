package telran.ashkelon2020.person.dao;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import telran.ashkelon2020.person.dto.CityPopulationDto;
import telran.ashkelon2020.person.model.Child;
import telran.ashkelon2020.person.model.Employee;
import telran.ashkelon2020.person.model.Person;

@Service
public interface PersonRepository extends JpaRepository<Person, Integer> {

	//numeration FROM 1
	//@Query(value = "SELECT * FROM PERSONS  WHERE NAME=?1", nativeQuery = true)
	//Stream<Person> findByName(String name);
	
	//Stream<Person> findByName(String name);
	
	@Query(value = "SELECT p FROM Person p WHERE p.name=?1") // JPQL - by default
								// Person - name of Class/Entity (which is Entity for Database)
	Stream<Person> findByName(String name);
		
	List<Person> findByBirthDateBetween(LocalDate date1, LocalDate date2);
	
  //@Query("select p from Person p where p.address.city=?1")
	@Query("select p from Person p where p.address.city=:city")
	Stream<Person> findByAddressCity(@Param("city") String city);
	
	//Stream<Person> findByAddressCity(String city);
	
	@Query("select new telran.ashkelon2020.person.dto.CityPopulationDto(p.address.city, count(p)) "
			+ "from Person p group by p.address.city order by count(p) desc")
	List<CityPopulationDto> getCityPopulation();
	
	@Query("select p from Person p where p.salary IS NOT NULL and p.salary between ?1 and ?2")
	List<Employee> findBySalaryNotNullAndBetween(Integer min, Integer max);
		
	List<Child> findByKindergartenNotNull();

}
