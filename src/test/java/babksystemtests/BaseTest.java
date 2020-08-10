package babksystemtests;

import com.epam.sql.banksystem.config.parser.ParserJSon;
import com.epam.sql.banksystem.dao.databasedao.PersonDataBaseDAO;
import com.epam.sql.banksystem.entity.Bank;
import com.epam.sql.banksystem.entity.Loan;
import com.epam.sql.banksystem.entity.Location;
import com.epam.sql.banksystem.entity.Person;
import com.epam.sql.banksystem.modules.DAOModule;
import com.epam.sql.banksystem.modules.ServiceModule;
import com.epam.sql.banksystem.service.BankService;
import com.epam.sql.banksystem.service.LocationService;
import com.epam.sql.banksystem.service.PersonService;
import com.google.inject.Inject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.Guice;

import java.util.List;

@Guice(modules = {DAOModule.class, ServiceModule.class})
public abstract class BaseTest {
    private static final Logger log = LogManager.getLogger(PersonDataBaseDAO.class);
    @Inject
    protected PersonService personService;
    @Inject
    protected BankService bankService;
    @Inject
    protected LocationService locationService;

    protected List<Person> personList = new ParserJSon().getPersons();
    protected List<Bank> bankList = new ParserJSon().getBanks();
    protected List<Location> locationList = new ParserJSon().getLocations();
    protected List<Loan> loanList = new ParserJSon().getLoans();

    @AfterSuite
    public void clearDataBase() {
        personService.getAllPerson().forEach(person ->personService.deletePerson(person));
        bankService.getAllBanks().forEach(bank -> bankService.deleteBank(bank));
        locationService.getAllLocation().forEach(location ->locationService.deleteLocation(location));
        String s = System.getProperty("example");
        log.info(s);
    }
}
