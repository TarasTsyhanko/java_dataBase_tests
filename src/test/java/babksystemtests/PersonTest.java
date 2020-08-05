package babksystemtests;

import com.epam.sql.banksystem.config.exception.InfoException;
import com.epam.sql.banksystem.entity.Person;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

@Test
public class PersonTest extends BaseTest {
    Person newPerson = new Person("Joye", "Tribiany", "american", "UAS", "howYouDoing@gmail.com");


    @Test(priority = 1)
    public void getAllPersonTestCase() throws InfoException {
        List<Person> personListDB = personService.getAllPerson();
        Assert.assertEquals(personListDB, personsDB);
    }

    @Test(priority = 2)
    public void insertPersonTestCase() throws InfoException {
        Person personDB = personService.insertPerson(newPerson);
        Assert.assertEquals(personDB, newPerson);
    }

    @Test(priority = 3,
            expectedExceptions = {InfoException.class},
            expectedExceptionsMessageRegExp = "This person already exists")
    public void insertSamePersonTestCase() throws InfoException {
        Person personDB = personService.insertPerson(newPerson);
    }

    @Test(priority = 4)
    public void getPersonByNameTestCase() throws InfoException {
        Person personDB = personService.getPersonByName(newPerson.getFirstName(),newPerson.getLastName());
        Assert.assertEquals(newPerson,personDB);
    }

    @Test(priority = 4,
    expectedExceptions = {InfoException.class},
    expectedExceptionsMessageRegExp = "This person is absent")
    public void getNotExistsPersonByNameTestCase() throws InfoException {
        Person personDB = personService.getPersonByName("Oleh","Vylnuy");
    }

    @Test(priority = 5)
    public void updatePersonTestCase() throws InfoException {
        Person upPerson = personService.getAllPerson().get(2);
        upPerson.setLastName("Wise");
        upPerson.setCountry("Australia");
        Person personDB = personService.updatePerson(upPerson);
        Assert.assertEquals(personDB, upPerson);
    }

    @Test(priority = 6,
            expectedExceptions = {InfoException.class},
            expectedExceptionsMessageRegExp = "This person is absent")
    public void deletePersonTestCase() throws InfoException {
        Person person = personService.getAllPerson().get(3);
       personService.deletePerson(person);
       Person personDB = personService.getPersonByName(person.getFirstName(),person.getLastName());
    }



}
