package babksystemtests;

import com.epam.sql.banksystem.config.exception.InfoException;
import com.epam.sql.banksystem.entity.Bank;
import com.epam.sql.banksystem.entity.Investment;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

@Test(priority = 4)
public class InvestmentTest extends BaseTest {

    @Test(priority = 2,
            expectedExceptions = {InfoException.class},
            expectedExceptionsMessageRegExp = "Incorrect investment")
    public void tryInsertInvestmentIfClientSameBankTestCase() throws InfoException {
        Investment investment = investmentList.get(2);
        investment.setIDClient(banksDB.get(0).getId());
        investment.setIDBank(banksDB.get(0).getId());
        Investment investmentDB = investmentService.insertInvestment(investment);
    }
}
