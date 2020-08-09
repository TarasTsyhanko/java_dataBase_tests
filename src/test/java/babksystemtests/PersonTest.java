package babksystemtests;

import com.epam.sql.banksystem.entity.Person;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;
import java.util.stream.Collectors;

@Test(priority = 3)
public class PersonTest extends BaseTest {
    Person newPerson = new Person("Joye", "Tribiany", "american", "UAS", "howYouDoing@gmail.com");


    @Test(priority = 1)
    public void insertAllPersonTestCase(){
        personList.forEach(person -> personService.insertPerson(person));
        Assert.assertEquals(personList, personService.getAllPerson());
    }

    @Test(priority = 2)
    public void insertPersonTestCase(){
        Person personDB = personService.insertPerson(newPerson);
        Assert.assertEquals(personDB, newPerson);
    }

    @Test(priority = 3)
    public void insertSamePersonTestCase(){
        personService.insertPerson(newPerson);
        List<Person> people = personService.getAllPerson()
                .stream().filter(person -> person.equals(newPerson))
                .collect(Collectors.toList());
        Assert.assertEquals(people.size(),1);
    }

    @Test(priority = 4)
    public void getPersonByNameTestCase(){
        Person personDB = personService.getPersonByName(newPerson.getFirstName(),newPerson.getLastName());
        Assert.assertEquals(newPerson,personDB);
    }

    @Test(priority = 4)
    public void getNotExistsPersonByNameTestCase(){
        Assert.assertNull(personService.getPersonByName("Oleh","Vylnuy"));
    }

    @Test(priority = 5)
    public void updatePersonTestCase(){
        Person upPerson = personService.getAllPerson().get(2);
        upPerson.setLastName("Wise");
        upPerson.setCountry("Australia");
        Person personDB = personService.updatePerson(upPerson);
        Assert.assertEquals(personDB, upPerson);
    }

    @Test(priority = 6)
    public void deletePersonTestCase(){
        Person person = personService.getAllPerson().get(3);
       personService.deletePerson(person);
       Assert.assertNull(personService.getPersonByName(person.getFirstName(),person.getLastName()));
    }



}
