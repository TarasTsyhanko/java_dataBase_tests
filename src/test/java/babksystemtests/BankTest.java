package babksystemtests;

import com.epam.sql.banksystem.entity.Bank;
import com.epam.sql.banksystem.entity.Location;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;
import java.util.stream.Collectors;

@Test(priority = 2)
public class BankTest extends BaseTest {
    @Test(priority = 1)
    public void insertAllBankTestCase() {
        List<Location> locDB = locationService.getAllLocation();
        bankList.get(0).setIDLocation(locDB.get(0).getLocationID());
        bankList.get(1).setIDLocation(locDB.get(1).getLocationID());
        bankList.get(2).setIDLocation(locDB.get(2).getLocationID());
        bankList.get(3).setIDLocation(locDB.get(3).getLocationID());
        bankList.forEach(bank -> bankService.insertBank(bank));
        Assert.assertEquals(bankList, bankService.getAllBanks());
    }

    @Test(priority = 3)
    public void getBankByNameTestCase(){
        Bank bank = bankList.get(0);
        Bank bankDB = bankService.getBankByName(bank.getName());
        Assert.assertEquals(bankDB.getName(), bank);
    }

    @Test(priority = 3)
    public void insertSameBank(){
        Bank newBank = bankList.get(1);
        bankService.insertBank(newBank);
        List<Bank> banks = bankService.getAllBanks()
                .stream().filter(bank -> bank.equals(newBank))
                .collect(Collectors.toList());
        Assert.assertEquals(banks.size(),1);
    }

    @Test(priority = 4)
    public void getBankByLocationTestCase(){
        Location location = locationList.get(1);
        Bank bank = bankService.getBankByLocation(location);
        Assert.assertEquals(location.getLocationID(), bank.getIDLocation());
    }

    @Test(priority = 5)
    public void insertBankWithSameLocationTestCase() {
        Location location = locationService.getAllLocation().get(0);
        Bank bank = new Bank("New Bank", location.getLocationID());
        bankService.insertBank(bank);
        List<Bank> banks = bankService.getAllBanks()
                .stream().filter(bank1 -> bank1.getIDLocation()==location.getLocationID())
                .collect(Collectors.toList());
        Assert.assertEquals(banks.size(),1);
    }

    @Test(priority = 6)
    public void updateNameBankTestCase(){
        Bank bank = bankService.getAllBanks().get(1);
        bank.setName("Private Bank");
        bankService.updateBank(bank);
        Assert.assertEquals(bank, bankService.getBankByName(bank.getName()));
    }

    @Test(priority = 7)
    public void updateLocationBankTestCase(){
        Bank bank = bankService.getAllBanks().get(1);
        bank.setIDLocation(locationService.getAllLocation().get(4).getLocationID());
        bankService.updateBank(bank);
        Assert.assertEquals(bank, bankService.getBankByName(bank.getName()));
    }

    @Test(priority = 8)
    public void deleteBankTestCase(){
        Bank bank = bankService.getAllBanks().get(2);
        bankService.deleteBank(bank);
        bank = bankService.getBankByName(bank.getName());
        Assert.assertNull(bank);
    }
}
