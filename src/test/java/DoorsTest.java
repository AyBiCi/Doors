import main.Door;
import main.Doors;
import org.bukkit.Location;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class DoorsTest {

    public static Doors doors;

    @BeforeEach
    public void setUp(){
        doors = new Doors();
    }

    @Test
    public void addNewTeleportTest(){
        doors.addDoor("newDoor", new Location(null, 0,0,0));

        Door door = doors.getDoor("newDoor");

        assertEquals("newDoor",door.getName());
    }

    @Test
    public void addTwoTeleportsTest(){
        doors.addDoor("newDoor", null);
        doors.addDoor("newDoor2", null);

        assertEquals("newDoor",doors.getDoor("newDoor").getName());
        assertEquals("newDoor2",doors.getDoor("newDoor2").getName());
    }

    @Test
    public void checkCountOfDoorsWhenAdding(){
        assertEquals(0,doors.getCount());
        doors.addDoor("newDoor", null);
        assertEquals(1,doors.getCount());
    }

    @Test
    public void checkCountOfDoorsWhenAddingAndRemoving(){
        doors.addDoor("newDoor1", null);
        doors.addDoor("newDoor2", null);
        doors.addDoor("newDoor3", null);

        assertEquals(3,doors.getCount());

        doors.removeDoor("newDoor1");
        doors.removeDoor("newDoor2");

        assertEquals(1,doors.getCount());
    }

    @Test
    public void checkThrowingOnAddingDoorsWithTheSameNames(){
        createRedundantNamesProblem("newDoor1");
        createRedundantNamesProblem("newDoor2");
    }

    public void createRedundantNamesProblem(String name){
        assertThrows(Doors.DoorWithThatNameAlreadyExists.class, () -> {
            doors.addDoor(name, null);
            doors.addDoor(name, null);
        });
    }

    @Test
    public void checkDoorList(){
        doors.addDoor("newDoor1", null);
        ArrayList<Door> doorsList = doors.getDoorList();
        assertEquals(1, doorsList.size());
        assertEquals("newDoor1", doorsList.get(0).getName());
    }

    @Test
    public void getDoorListTest(){
        ArrayList<String> testNames = new ArrayList<>();
        testNames.add("sdafasdg");
        testNames.add("ksefygjksdhkj");
        testNames.add("SADFGKJh");

        for(String name : testNames) {
            doors.addDoor(name, null);
        }

        ArrayList<Door> doorsList = doors.getDoorList();

        assertEquals(testNames.size(), doorsList.size());

        for(Door door : doorsList){
            testNames.remove(door.getName());
        }

        assertEquals(0, testNames.size());
    }

    @Test
    public void checkGetDoorThrowDoesntExist(){
        assertThrows(Doors.DoorWithThatNameDoesntExist.class, () -> {
            doors.getDoor("notExistentDoors");
        });
    }

    @Test
    public void checkRemoveDoorThrowDoesntExist(){
        assertThrows(Doors.DoorWithThatNameDoesntExist.class, () -> {
            doors.removeDoor("notExistentDoors");
        });
    }

    @Test
    public void checkLocationSaving(){
        addDoorAndTestLocation("newDoor1", new Location(null, 0,0,0));
        addDoorAndTestLocation("newDoor2", new Location(null, 10,0,0));
    }

    public void addDoorAndTestLocation(String name, Location location){
        doors.addDoor(name, location);
        Door door = doors.getDoor(name);
        assertEquals(location, door.getLocation());
    }

    @Test
    public void checkDoorWithThatNameDoesntExistMessage(){
        Doors.DoorWithThatNameDoesntExist t = new Doors.DoorWithThatNameDoesntExist("name");
        assertEquals("main.Door with name \"name\" doesn't exist!", t.getMessage());
    }
}
