package babksystemtests;

import com.epam.sql.banksystem.entity.Location;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;
import java.util.stream.Collectors;

@Test(priority = 1)
public class LocationTest extends BaseTest {
    private Location newLocation = new Location("England", "York", "Sheekspeer 3");
    private Location exampleLocation = new Location("Franche", "Lion", "Sheekspeer 3");

    @Test(priority = 1, description = "add all location from file to DB")
    public void insertAllLocationTestCase() {
        locationList.forEach(location -> locationService.insertLocation(location));
        Assert.assertEquals(locationList, locationService.getAllLocation());
    }

    @Test(priority = 1, description = "insert new location to DB")
    public void insertLocationTestCase(){
        locationService.insertLocation(newLocation);
        Location locDB = locationService.getLocation(newLocation);
        newLocation.setLocationID(locDB.getLocationID());
        Assert.assertEquals(newLocation, locDB);

    }

    @Test(priority = 2,description = "add already exist location to DB")
    public void insertSameLocation(){
        locationService.insertLocation(newLocation);
        List<Location> locations = locationService.getAllLocation()
                .stream().filter(location -> location.equals(newLocation))
                .collect(Collectors.toList());
        Assert.assertEquals(locations.size(),1);
    }

    @Test(priority = 4,description = "update location in adb")
    public void updateLocation(){
        newLocation.setCity("London");
        newLocation.setStreet("Baker Street 221B");
        locationService.updateLocation(newLocation);
        Assert.assertEquals(exampleLocation, locationService.getLocation(newLocation));
    }

    @Test(priority = 5,description = "delete location from DB")
    public void deleteLocation(){
        locationService.deleteLocation(newLocation);
        Location location = locationService.getLocation(newLocation);
        Assert.assertNull(location);
    }
}
