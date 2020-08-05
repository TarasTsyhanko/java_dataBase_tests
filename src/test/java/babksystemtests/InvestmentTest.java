package babksystemtests;

import com.epam.sql.banksystem.config.exception.InfoException;
import com.epam.sql.banksystem.entity.Bank;
import com.epam.sql.banksystem.entity.Investment;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

@Test
public class InvestmentTest extends BaseTest {

    @Test(priority = 1)
    public void insertInvestmentIfClientPersonTestCase() throws InfoException {
        Investment investment = investmentList.get(0);
        investment.setIDClient(personsDB.get(4).getId());
        investment.setIDBank(banksDB.get(2).getId());
        Investment investmentDB = investmentService.insertInvestment(investment);
        Assert.assertEquals(investment, investmentDB);
    }

    @Test(priority = 2)
    public void insertInvestmentIfClientBankTestCase() throws InfoException {
        Investment investment = investmentList.get(0);
        investment.setIDClient(banksDB.get(0).getId());
        investment.setIDBank(banksDB.get(2).getId());
        Investment investmentDB = investmentService.insertInvestment(investment);
        Assert.assertEquals(investment, investmentDB);
    }

    @Test(priority = 2,
            expectedExceptions = {InfoException.class},
            expectedExceptionsMessageRegExp = "Incorrect investment")
    public void tryInsertInvestmentIfClientSameBankTestCase() throws InfoException {
        Investment investment = investmentList.get(2);
        investment.setIDClient(banksDB.get(0).getId());
        investment.setIDBank(banksDB.get(0).getId());
        Investment investmentDB = investmentService.insertInvestment(investment);
    }

    @Test(priority = 3)
    public void getAllInvestmentInOneBankTestCase() {
        Bank bank = banksDB.get(1);
        Investment investment1 = investmentList.get(0);
        Investment investment2 = investmentList.get(1);
        Investment investment3 = investmentList.get(2);
        investment1.setIDBank(bank.getId());
        investment2.setIDBank(bank.getId());
        investment3.setIDBank(bank.getId());
        investment1.setIDClient(personsDB.get(2).getId());
        investment2.setIDClient(personsDB.get(4).getId());
        investment3.setIDClient(personsDB.get(1).getId());
        List<Investment> list = Arrays.asList(investment1,investment2,investment3);
        list.forEach(investment -> {
            try {
                investmentService.insertInvestment(investment);
            } catch (InfoException e) {
                e.printStackTrace();
            }
        });
        List<Investment> listDB = investmentService.getAllInvestmentInOneBank(bank);
        Assert.assertEquals(listDB, list);
    }

    @Test(priority = 4)
    public void updateInvestmentTestCase() throws InfoException {
        Investment myInvestment = investmentService.getAllInvestmentInOneBank(banksDB.get(1)).get(1);
        myInvestment.setCurrency("USD");
        myInvestment.setAmount(450000);
        Investment upInvestment = investmentService.updateInvestment(myInvestment);
        Assert.assertEquals(upInvestment, myInvestment);
    }

    @Test(priority = 5,
            expectedExceptions = {InfoException.class},
            expectedExceptionsMessageRegExp = "You can't change Bank or Client")
    public void updateInvestmentTryChangeBankTestCase() throws InfoException {
        Investment myInvestment = investmentService.getAllInvestmentInOneBank(banksDB.get(1)).get(2);
        myInvestment.setIDBank(banksDB.get(2).getId());
        Investment investmentDb = investmentService.updateInvestment(myInvestment);
    }

    @Test(priority = 6)
    public void deleteInvestmentTestCase() {
        Investment myInvestment = investmentService.getAllInvestmentInOneBank(banksDB.get(1)).get(2);
        investmentService.deleteInvestment(myInvestment);
        List<Investment> list = investmentService.getAllInvestment();
        for (Investment investment : list) {
            Assert.assertNotEquals(myInvestment, investment);
        }
    }
}
