package babksystemtests;

import com.epam.sql.banksystem.config.exception.InfoException;
import com.epam.sql.banksystem.entity.Bank;
import com.epam.sql.banksystem.entity.Location;
import org.testng.Assert;
import org.testng.annotations.Test;

@Test(priority = 2)
public class LocationTest extends BaseTest {
    private Location newLocation = new Location("England", "York", "Sheekspeer 3");

    @Test(priority = 1)
    public void getAllLocationTestCase() {
        Assert.assertEquals(locationList, locationService.getAllLocation());
    }

    @Test(priority = 1)
    public void insertLocationTestCase() throws InfoException {
        locationService.insertLocation(newLocation);
        Location locDB = locationService.getLocation(newLocation);
        Assert.assertEquals(newLocation, locDB);
        newLocation.setLocationID(locDB.getLocationID());
    }

    @Test(priority = 2,
            expectedExceptions = {InfoException.class},
            expectedExceptionsMessageRegExp = "This Location already exists")
    public void insertSameLocation() throws InfoException {
        locationService.insertLocation(newLocation);
    }

    @Test(priority = 3)
    public void getLocationByBank() throws InfoException {
        Bank bank = bankService.getAllBanks().get(1);
        Location location = locationService.getLocationByBank(bank);
        Assert.assertEquals(bank.getIDLocation(), location.getLocationID(), 0.0);
    }

    @Test(priority = 4)
    public void updateLocation() throws InfoException {
        newLocation.setCity("London");
        newLocation.setStreet("Baker Street 221B");
        locationService.updateLocation(newLocation);
        Assert.assertEquals(newLocation, locationService.getLocation(newLocation));
    }

    @Test(priority = 5,
            expectedExceptions = {InfoException.class},
            expectedExceptionsMessageRegExp = "This Location absent")
    public void deleteLocation() throws InfoException {
        bankService.insertBank(new Bank("Example", newLocation.getLocationID()));
        locationService.deleteLocation(newLocation);
        Location location = locationService.getLocation(newLocation);
    }
}
