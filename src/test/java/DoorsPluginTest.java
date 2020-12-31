import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import main.DoorsPlugin;
import org.bukkit.Location;
import org.bukkit.World;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

public class DoorsPluginTest {
    //MockBukkit mocking
    DoorsPlugin plugin;
    ServerMock server;
    PlayerMock player;
    World world;

    @BeforeEach
    public void setUp()
    {
        server = MockBukkit.mock();
        plugin = (DoorsPlugin) MockBukkit.load(DoorsPlugin.class);
        world = server.addSimpleWorld("world");
        player = server.addPlayer();
    }
    @AfterEach
    public void tearDown()
    {
        MockBukkit.unmock();
    }

    @Test
    public void testDoorsGetByLocation(){
        createDoor("1", fLoc(1));
        createDoor("2", fLoc(2));

        player.setLocation(fLoc(1));
        player.performCommand("dtp info");
        assertEquals("Name: 1", player.nextMessage());

        player.setLocation(fLoc(2));
        player.performCommand("dtp info");
        assertEquals("Name: 2", player.nextMessage());
    }

    @Test
    public void checkCountOfDoorsWhenAddingAndRemoving(){
        createDoor("newDoor1", fLoc(1));
        createDoor("newDoor2", fLoc(2));
        createDoor("newDoor3", fLoc(3));

        assertEquals(3,getDoorCount());

        removeDoor("newDoor1");
        removeDoor("newDoor2");

        assertEquals(1,getDoorCount());
    }

    @Test
    public void checkDoorList(){
        for(int i=1;i<=4;i++)
            createDoor("newDoor"+i, fLoc(i));

        player.performCommand("dtp list");
        int count = readDoorCountFromMessage(player.nextMessage());
        assertEquals(4, count);

        for(int i=1;i<=4;i++)
            assertEquals("newDoor"+i, player.nextMessage());
    }

    @Test
    public void checkThrowingOnAddingDoorsWithTheSameNames(){
        createRedundantNamesProblem("newDoor1");
        createRedundantNamesProblem("newDoor2");
    }

    @Test
    public void testRemovingNotExistingDoor(){
        player.performCommand("dtp remove mark");
        assertEquals("Door with name \"mark\" doesn't exist!",
                player.nextMessage());
    }

    @Test
    public void executeInfoCommandWhenNoDoorsAreHere(){
        player.performCommand("dtp info");
        assertEquals("There's no door on this location!", player.nextMessage());
    }

    @Test
    public void getInfoAboutNamedTeleport(){
        player.setLocation(fLoc(1));
        player.performCommand("dtp create mark");
        clearPlayerMessages();
        player.setLocation(fLoc(2));
        player.performCommand("dtp info mark");
        assertEquals("Name: mark", player.nextMessage());
    }

    @Test
    public void executeInfoCommandOnNonExistantNamedTeleport(){
        player.performCommand("dtp info mark");
        assertEquals("Door with name \"mark\" doesn't exist!", player.nextMessage());
    }

    public void createRedundantNamesProblem(String name){
            player.performCommand("dtp create "+name);
            clearPlayerMessages();
            player.performCommand("dtp create "+name);
            assertEquals("Door with name \""+name+"\" already exists!", player.nextMessage());
    }


    public Location fLoc(int number){
        return new Location(world, number, 1,1);
    }

    public String createDoor(String name, Location location){
        player.setLocation(location);
        player.performCommand("dtp create "+name);
        return player.nextMessage();
    }

    public String removeDoor(String name){
        player.performCommand("dtp remove "+name);
        return player.nextMessage();
    }

    public int getDoorCount(){
        player.performCommand("dtp list");
        int count = readDoorCountFromMessage(player.nextMessage());
        clearPlayerMessages();
        return count;
    }

    private static Pattern doorCountPattern = Pattern.compile("[0-9]+");
    public int readDoorCountFromMessage(String message){
        Matcher matcher = doorCountPattern.matcher(message);
        matcher.find();
        return Integer.parseInt(matcher.group());
    }

    private void clearPlayerMessages(){
        while(player.nextMessage() != null){}
    }
}
