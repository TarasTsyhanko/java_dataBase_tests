package babksystemtests;

import com.epam.sql.banksystem.config.exception.InfoException;
import com.epam.sql.banksystem.entity.Bank;
import com.epam.sql.banksystem.entity.Deposit;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

@Test
public class DepositTest extends BaseTest {

    @Test(priority = 1)
    public void insertDepositIfClientPersonTestCase() throws InfoException {
        Deposit deposit = depositList.get(0);
        deposit.setIDClient(personsDB.get(4).getId());
        deposit.setIDBank(banksDB.get(2).getId());
        Deposit depositDB = depositService.insertDeposit(deposit);
        Assert.assertEquals(deposit, depositDB);
    }

    @Test(priority = 2)
    public void insertDepositIfClientBankTestCase() throws InfoException {
        Deposit deposit = depositList.get(0);
        deposit.setIDClient(banksDB.get(0).getId());
        deposit.setIDBank(banksDB.get(2).getId());
        Deposit depositDB = depositService.insertDeposit(deposit);
        Assert.assertEquals(deposit, depositDB);
    }

    @Test(priority = 2,
            expectedExceptions = {InfoException.class},
            expectedExceptionsMessageRegExp = "Incorrect deposit")
    public void tryInsertDepositIfClientSameBankTestCase() throws InfoException {
        Deposit deposit = depositList.get(2);
        deposit.setIDClient(banksDB.get(0).getId());
        deposit.setIDBank(banksDB.get(0).getId());
        Deposit depositDB = depositService.insertDeposit(deposit);
    }

    @Test(priority = 3)
    public void getAllDepositInOneBankTestCase() {
        Bank bank = banksDB.get(1);
        Deposit deposit1 = depositList.get(0);
        Deposit deposit2 = depositList.get(1);
        Deposit deposit3 = depositList.get(2);
        deposit1.setIDBank(bank.getId());
        deposit2.setIDBank(bank.getId());
        deposit3.setIDBank(bank.getId());
        deposit1.setIDClient(personsDB.get(2).getId());
        deposit2.setIDClient(personsDB.get(3).getId());
        deposit3.setIDClient(personsDB.get(4).getId());
        List<Deposit> list = Arrays.asList(deposit1, deposit2, deposit3);
        list.forEach(deposit -> {
            try {
                depositService.insertDeposit(deposit);
            } catch (InfoException e) {
                e.printStackTrace();
            }
        });
        List<Deposit> listDB = depositService.getAllDepositInOneBank(bank);
        Assert.assertEquals(listDB, list);
    }

    @Test(priority = 4)
    public void updateDepositTestCase() throws InfoException {
        Deposit myDeposit = depositService.getAllDepositInOneBank(banksDB.get(1)).get(1);
        myDeposit.setCurrency("UAN");
        myDeposit.setAmount(33000);
        Deposit upDeposit = depositService.updateDeposit(myDeposit);
        Assert.assertEquals(upDeposit, myDeposit);
    }

    @Test(priority = 5,
            expectedExceptions = {InfoException.class},
            expectedExceptionsMessageRegExp = "You can't change Bank or Client")
    public void updateDepositTryChangeBankTestCase() throws InfoException {
        Deposit myDeposit = depositService.getAllDepositInOneBank(banksDB.get(1)).get(2);
        myDeposit.setIDBank(banksDB.get(2).getId());
        Deposit depositDb = depositService.updateDeposit(myDeposit);
    }

    @Test(priority = 6)
    public void deleteDepositTestCase() {
        Deposit myDeposit = depositService.getAllDepositInOneBank(banksDB.get(1)).get(2);
        depositService.deleteDeposit(myDeposit);
        List<Deposit> list = depositService.getAllDeposit();
        for (Deposit d : list) {
            Assert.assertNotEquals(myDeposit, d);
        }
    }
}
