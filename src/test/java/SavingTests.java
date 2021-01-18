import containers.Door;
import org.bukkit.Location;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import saving.DoorToJSON;
import saving.DoorsToFilesSaver;

import java.io.File;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SavingTests {

    DoorsToFilesSaver saver;

    @BeforeEach
    public void setUp(){
        saver = new DoorsToFilesSaver("."+ File.separator + "testSaves");
        saver.removeAllSaves();
    }

    @Test
    public void doorToJSONTest(){
        Door door = new Door("marec", new Location(null, 1,2,3));
        String doorJson = DoorToJSON.convertTo(door);
        assertEquals(door.toString(),DoorToJSON.convertFrom(doorJson).toString());
    }

    @Test
    public void testDoorsToFilesSaver(){
        Door door = new Door("marec", new Location(null, 1,2,3));
        saver.saveDoor(door);
        ArrayList<Door> doors = saver.getSavedDoors();
        assertEquals(1, doors.size());
        assertTrue(doors.get(0).toString().equals(door.toString()));
    }

    @Test
    public void testMultipleDoors(){
        assertEquals(0, saver.getSavedDoors().size());

        Door door1 = new Door("marec1", new Location(null, 1,2,3));
        Door door2 = new Door("marec2", new Location(null, 4,5,6));
        Door door3 = new Door("marec3", new Location(null, 7,8,9));

        saver.saveDoor(door1);
        saver.saveDoor(door2);
        saver.saveDoor(door3);

        assertEquals(3, saver.getSavedDoors().size());
    }

    @Test
    public void resaveDoor(){
        Door door = new Door("marec", new Location(null, 1,2,3));
        saver.saveDoor(door);
        saver.saveDoor(door);
        assertEquals(1, saver.getSavedDoors().size());
    }

    @Test
    public void saveDoorWithCombined(){
        Door door = new Door("marec", new Location(null, 1,2,3));
        door.combineTo("marec3");
        saver.saveDoor(door);
        assertEquals(1, saver.getSavedDoors().size());
        assertEquals(door.toString(), saver.getSavedDoors().get(0).toString());
    }
}
