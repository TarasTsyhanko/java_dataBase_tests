package babksystemtests;

import com.epam.sql.banksystem.config.exception.InfoException;
import com.epam.sql.banksystem.entity.Bank;
import com.epam.sql.banksystem.entity.Location;
import org.junit.Assert;
import org.testng.annotations.Test;

import java.util.List;
@Test(priority = 1)
public class BankTest extends BaseTest {
    @Test(priority = 1)
    public void insertAllBankTestCase() {
        List<Location> locDB = locationService.getAllLocation();
        bankList.get(0).setIDLocation(locDB.get(0).getLocationID());
        bankList.get(1).setIDLocation(locDB.get(1).getLocationID());
        bankList.get(2).setIDLocation(locDB.get(2).getLocationID());
        bankList.get(3).setIDLocation(locDB.get(3).getLocationID());
        bankList.forEach(bank -> {
            try {
                bankService.insertBank(bank);
            } catch (InfoException e) {
                e.printStackTrace();
            }
        });
        Assert.assertEquals(bankList.size(), bankService.getAllBanks().size());
    }

    @Test(priority = 2)
    public void getAllBankTestCase() {
        List<Bank> listDB = bankService.getAllBanks();
        Assert.assertEquals(bankList, listDB);
    }

    @Test(priority = 3)
    public void getBankByNameTestCase() throws InfoException {
        Bank bank = bankList.get(0);
        Bank bankDB = bankService.getBankByName(bank.getName());
        Assert.assertEquals(bank, bankDB);
    }

    @Test(priority = 3,
            expectedExceptions = {InfoException.class},
            expectedExceptionsMessageRegExp = "This bank already exists")
    public void insertSameBank() throws InfoException {
        bankService.insertBank(bankList.get(1));
    }

    @Test(priority = 4)
    public void getBankByLocationTestCase() throws InfoException {
        Location location = locationList.get(1);
        Bank bank = bankService.getBankByLocation(location);
        Assert.assertEquals(location.getLocationID(), bank.getIDLocation());
    }

    @Test(priority = 5,
            expectedExceptions = {InfoException.class},
            expectedExceptionsMessageRegExp = "This Location already has Bank")
    public void insertBankWithSameLocationTestCase() throws InfoException {
        Location location = locationService.getAllLocation().get(0);
        Bank bank = new Bank("New Bank", location.getLocationID());
        bankService.insertBank(bank);
    }

    @Test(priority = 6)
    public void updateNameBankTestCase() throws InfoException {
        Bank bank = bankService.getAllBanks().get(1);
        bank.setName("Private Bank");
        bankService.updateBank(bank);
        Assert.assertEquals(bank, bankService.getBankByName(bank.getName()));
    }

    @Test(priority = 7)
    public void updateLocationBankTestCase() throws InfoException {
        Bank bank = bankService.getAllBanks().get(1);
        bank.setIDLocation(locationService.getAllLocation().get(4).getLocationID());
        bankService.updateBank(bank);
        Assert.assertEquals(bank, bankService.getBankByName(bank.getName()));
    }

    @Test(priority = 8,
            expectedExceptions = {InfoException.class},
            expectedExceptionsMessageRegExp = "This bank is absent")
    public void deleteBankTestCase() throws InfoException {
        Bank bank = bankService.getAllBanks().get(2);
        bankService.deleteBank(bank);
        bank = bankService.getBankByName(bank.getName());
    }

    @Test(priority = 9,
            expectedExceptions = {InfoException.class},
            expectedExceptionsMessageRegExp = "This bank is absent")
    public void deleteNotExistBankTestCase() throws InfoException {
        Bank bank = new Bank("Bank", 123);
        bankService.deleteBank(bank);
    }
}
