package babksystemtests;

import com.epam.sql.banksystem.config.exception.InfoException;
import com.epam.sql.banksystem.entity.Bank;
import com.epam.sql.banksystem.entity.Deposit;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

@Test(priority = 5)
public class DepositTest extends BaseTest {

    @Test(priority = 2,
            expectedExceptions = {InfoException.class},
            expectedExceptionsMessageRegExp = "Incorrect deposit")
    public void tryInsertDepositIfClientSameBankTestCase() throws InfoException {
        Deposit deposit = depositList.get(2);
        deposit.setIDClient(banksDB.get(0).getId());
        deposit.setIDBank(banksDB.get(0).getId());
        Deposit depositDB = depositService.insertDeposit(deposit);
    }
}
