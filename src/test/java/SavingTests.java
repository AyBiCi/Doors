import containers.Door;
import org.bukkit.Location;
import org.junit.jupiter.api.Test;
import saving.DoorToJSON;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SavingTests {

    @Test
    public void doorToJSONTest(){
        Door door = new Door("marec", new Location(null, 1,2,3));
        String doorJson = DoorToJSON.convertTo(door);
        assertEquals(door.toString(),DoorToJSON.convertFrom(doorJson).toString());
    }
}
