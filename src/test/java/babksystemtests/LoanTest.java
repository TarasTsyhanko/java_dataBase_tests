package babksystemtests;

import com.epam.sql.banksystem.config.exception.InfoException;
import com.epam.sql.banksystem.entity.Bank;
import com.epam.sql.banksystem.entity.Loan;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

@Test(priority = 3)
public class LoanTest extends BaseTest {
    Loan loan = loanList.get(0);

    @Test(priority = 1)
    public void insertLoanTestCase() throws InfoException {
        loan.setIDBank(banksDB.get(0).getId());
        loan.setIDClient(personsDB.get(0).getId());
        loanService.insertLoan(loan);
        Assert.assertEquals(loan, loanService.getAllLoanInOneBank(banksDB.get(0)).get(0));
    }

    @Test(priority = 2,
            expectedExceptions = {InfoException.class},
            expectedExceptionsMessageRegExp = "This Client already has LOAN in this Bank")
    public void insertLoanWithSameClientInSameBankTestCase() throws InfoException {
        Loan newLoan = loanList.get(1);
        newLoan.setIDBank(banksDB.get(0).getId());
        newLoan.setIDClient(personsDB.get(0).getId());
        loanService.insertLoan(newLoan);
    }

    @Test(priority = 3)
    public void insertLoanIfClientBankTestCase() throws InfoException {
        Loan newLoan = loanList.get(1);
        newLoan.setIDBank(banksDB.get(0).getId());
        newLoan.setIDClient(banksDB.get(1).getId());
        loanService.insertLoan(newLoan);
        Assert.assertEquals(newLoan, loanService.getAllLoanIByClient(banksDB.get(1).getId()).get(0));
    }

    @Test(priority = 4)
    public void insertLoanIntoAnotherBankTestCase() throws InfoException {
        Loan newLoan = loanList.get(2);
        newLoan.setIDBank(banksDB.get(1).getId());
        newLoan.setIDClient(personsDB.get(0).getId());
        loanService.insertLoan(newLoan);
    }

    @Test(priority = 5)
    public void getAllLoanByBankTestCase() throws InfoException {
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
        Assert.assertEquals(2, list.size());
        Assert.assertNotEquals(list.get(0).getIDClient(), list.get(1).getIDClient());
        Assert.assertEquals(list.get(0).getIDBank(), list.get(1).getIDBank());
    }

    @Test(priority = 6)
    public void getAllLoanByClientTestCase() throws InfoException {
        List<Loan> list = loanService.getAllLoanIByClient(personsDB.get(0).getId());
        Assert.assertEquals(3, list.size());
        Assert.assertNotEquals(list.get(0).getIDBank(), list.get(1).getIDBank());
        Assert.assertNotEquals(list.get(0).getIDBank(), list.get(2).getIDBank());
        Assert.assertNotEquals(list.get(1).getIDBank(), list.get(2).getIDBank());
    }

    @Test(priority = 6,
            expectedExceptions = {InfoException.class},
            expectedExceptionsMessageRegExp = "This com.epam.sql.client has no any loan")
    public void getLoanByClientIfClientHasNoLoanTestCase() throws InfoException {
        List<Loan> list = loanService.getAllLoanIByClient(personsDB.get(personsDB.size() - 1).getId());

    }

    @Test(priority = 7)
    public void updateLoanTestCase() throws InfoException {
        Loan loan = loanService.getAllLoan().get(0);
        loan.setAmount(23000);
        loan.setCurrency("USD");
        Loan upLoan = loanService.updateLoan(loan);
        Assert.assertEquals(loan, upLoan);
    }

    @Test(priority = 7,
            expectedExceptions = {InfoException.class},
            expectedExceptionsMessageRegExp = "You can't change Bank or Client")
    public void tryUpdateLoanIfChangeBankTestCase() throws InfoException {
        Loan loan = loanService.getAllLoan().get(2);
        loan.setIDBank(banksDB.get(banksDB.size() - 1).getId());
        Loan upLoan = loanService.updateLoan(loan);
    }

    @Test(priority = 7,
            expectedExceptions = {InfoException.class},
            expectedExceptionsMessageRegExp = "This Loan absent")
    public void deleteLoanTestCase() throws InfoException {
        Loan loan = loanService.getAllLoan().get(2);
        loanService.deleteLoan(loan);
        Loan loan1 = loanService.getLoanByID(loan);
    }
}
