package babksystemtests;

import com.epam.sql.banksystem.config.exception.InfoException;
import com.epam.sql.banksystem.config.parser.ParserJSon;
import com.epam.sql.banksystem.dao.databasedao.PersonDataBaseDAO;
import com.epam.sql.banksystem.entity.*;
import com.epam.sql.banksystem.service.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;

import java.util.List;

public abstract class BaseTest {
    private static Logger log = LogManager.getLogger(PersonDataBaseDAO.class);
    protected PersonService personService;
    protected BankService bankService;
    protected LocationService locationService;
    protected DepositService depositService;
    protected LoanService loanService;
    protected InvestmentService investmentService;

    protected List<Person> personList = new ParserJSon().getPersons();
    protected List<Bank> bankList = new ParserJSon().getBanks();
    protected List<Location> locationList = new ParserJSon().getLocations();
    protected List<Deposit> depositList = new ParserJSon().getDeposits();
    protected List<Loan> loanList = new ParserJSon().getLoans();
    protected List<Investment> investmentList = new ParserJSon().getInvestments();

    protected List<Person> personsDB;
    protected List<Bank> banksDB;

    @BeforeClass(groups = {"babksystemtests.BankTest","babksystemtests.LocationTest","babksystemtests.LoanTest"})
    public void reinitialize() {
        personService = new PersonService();
        bankService = new BankService();
        locationService = new LocationService();
        depositService = new DepositService();
        loanService = new LoanService();
        investmentService = new InvestmentService();

    }
    @BeforeClass(dependsOnMethods = {"reinitialize"},groups = {"babksystemtests.BankTest" })
    public void loadPersonsAndBanksToDB(){
         personList.forEach(person -> {
             try {
                 personService.insertPerson(person);
             } catch (InfoException e) {
                 log.error( e.getMessage());
             }
         });
        locationList.forEach(location -> {
            try {
                locationService.insertLocation(location);
            } catch (InfoException e) {
                log.error( e.getMessage());
            }
        });
    }
    @BeforeClass(dependsOnMethods = {"reinitialize"})
    public void method(){
        personsDB = personService.getAllPerson();
        banksDB = bankService.getAllBanks();
    }

    @AfterSuite
    public void clearDataBase() {
        personService.getAllPerson().forEach(person -> {
            try {
                personService.deletePerson(person);
            } catch (InfoException e) {
                log.error( e.getMessage());
            }
        });
        bankService.getAllBanks().forEach(bank -> {
            try {
                bankService.deleteBank(bank);
            } catch (InfoException e) {
                log.error( e.getMessage());
            }
        });
        locationService.getAllLocation().forEach(location -> {
            try {
                locationService.deleteLocation(location);
            } catch (InfoException e) {
                log.error( e.getMessage());
            }
        });
        String s = System.getProperty("example");
        log.info(s);
    }
}
