package babksystemtests;

import com.epam.sql.banksystem.entity.Bank;
import com.epam.sql.banksystem.entity.Loan;
import com.epam.sql.banksystem.entity.Person;
import com.epam.sql.banksystem.service.LoanService;
import com.google.inject.Inject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

@Test(priority = 4)
public class LoanTest extends BaseTest {
    @Inject
    private LoanService loanService;

    protected List<Person> personsDB ;
    protected List<Bank> banksDB;
    private Loan loan = loanList.get(0);

    @BeforeClass
    public void setUp(){
        personsDB = personService.getAllPerson();
        banksDB = bankService.getAllBanks();
    }

    @Test(priority = 4)
    public void insertLoanIntoAnotherBankTestCase(){
        Loan newLoan = loanList.get(2);
        newLoan.setIDBank(banksDB.get(1).getId());
        newLoan.setIDClient(personsDB.get(0).getId());
        loanService.insertLoan(newLoan);
    }

    @Test(priority = 5)
    public void getAllLoanByBankTestCase(){
        Bank bank = banksDB.get(2);
        Loan loan1 = loanList.get(1);
        loan1.setIDBank(bank.getId());
        loan1.setIDClient(personsDB.get(0).getId());
        Loan loan2 = loanList.get(2);
        loan2.setIDBank(bank.getId());
        loan2.setIDClient(personsDB.get(1).getId());
        loanService.insertLoan(loan1);
        loanService.insertLoan(loan2);
        List<Loan> list = loanService.getAllLoanInOneBank(bank);
        Assert.assertEquals(list.size(), 2);
        Assert.assertNotEquals(list.get(0).getIDClient(), list.get(1).getIDClient());
        Assert.assertEquals(list.get(0).getIDBank(), list.get(1).getIDBank());
    }

    @Test(priority = 6)
    public void getAllLoanByClientTestCase() {
        List<Loan> list = loanService.getAllLoanIByClient(personsDB.get(0).getId());
        Assert.assertEquals(2, list.size());
        Assert.assertNotEquals(list.get(0).getIDBank(), list.get(1).getIDBank());
    }

    @Test(priority = 6)
    public void getLoanByClientIfClientHasNoLoanTestCase() {
        List<Loan> list = loanService.getAllLoanIByClient(personsDB.get(personsDB.size() - 1).getId());
        Assert.assertEquals(list.size(),0);

    }

    @Test(priority = 7)
    public void updateLoanTestCase(){
        Loan loan = loanService.getAllLoan().get(0);
        loan.setAmount(23000);
        loan.setCurrency("USD");
        Loan upLoan = loanService.updateLoan(loan);
        Assert.assertEquals(loan, upLoan);
    }

    @Test(priority = 7)
    public void deleteLoanTestCase() {
        Loan loan = loanService.getAllLoan().get(2);
        loanService.deleteLoan(loan);
        Assert.assertNull(loanService.getLoanByID(loan));
    }
}
