import main.Door;
import org.bukkit.Location;
import org.junit.jupiter.api.Test;
import saving.DoorXMLManager;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SavingTest {
    @Test
    public void testDoorXMLGenerator(){
        Door door = new Door("Marek", new Location(null, 0,0,0));
        String doorInXML = DoorXMLManager.generate(door);
        Door loadedDoor = DoorXMLManager.loadFromXML(doorInXML);
        assertEquals(true, loadedDoor.equals(loadedDoor));
    }
}
