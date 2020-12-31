import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import main.DoorsPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

public class DoorsPluginTest {
    //MockBukkit mocking
    ServerMock server;
    PlayerMock player;
    World world;

    @BeforeEach
    public void setUp()
    {
        server = MockBukkit.mock();
        MockBukkit.load(DoorsPlugin.class);
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

    @Test
    public void testOnPlayerClicksDoors(){
        rightClick(fLoc(1));
        assertEquals("There's no door on this location!", player.nextMessage());
    }

    @Test
    public void createTwoDoorsAndClickOnFirstOnes(){
        createDoor("door1", fLoc(1));
        createDoor("door2", fLoc(2));
        clearPlayerMessages();
        rightClick(fLoc(1));
        assertEquals("Doors doesn't go anywhere!", player.nextMessage());
    }
    @Test
    public void createTwoDoorsCombineThemAndClickOnFirstOnes(){
        Location firstDoor = new Location(world, -200, 5, 0);
        placeDoor(firstDoor);
        createDoor("door1", firstDoor);

        Location secondDoor = new Location(world, 200, 5, 0);
        placeDoor(secondDoor);
        createDoor("door2", secondDoor);

        player.performCommand("dtp combine door1 door2");
        assertEquals("Doors \"door1\" and \"door2\" combined!", player.nextMessage());

        //We click first doors
        rightClick(firstDoor);
        player.assertTeleported(secondDoor, 2);
    }

    @Test
    public void testCombineMethodWithBadNames(){
        player.performCommand("dtp combine no yes");
        assertEquals("Door with name \"no\" doesn't exist!", player.nextMessage());

        player.performCommand("dtp create no");
        clearPlayerMessages();

        player.performCommand("dtp combine no yes");
        assertEquals("Door with name \"yes\" doesn't exist!", player.nextMessage());

        player.performCommand("dtp combine no no");
        assertEquals("Cannot combine doors with themselves!", player.nextMessage());

        player.performCommand("dtp create yes");
        clearPlayerMessages();

        player.performCommand("dtp combine no yes");
        assertEquals("Doors \"no\" and \"yes\" combined!", player.nextMessage());
    }


    private void rightClick(Location location){
        Block block = world.getBlockAt(location);
        block.setType(Material.IRON_DOOR);

        PlayerInteractEvent event =
                new PlayerInteractEvent(
                        player,
                        Action.RIGHT_CLICK_BLOCK,
                        new ItemStack(Material.IRON_DOOR),
                        block,
                        BlockFace.NORTH
                );

        Bukkit.getPluginManager().callEvent(event);
    }

    private void placeDoor(Location location){
        world.getBlockAt(location).setType(Material.IRON_DOOR);
        world.getBlockAt(location.clone().add(0,1,0)).setType(Material.IRON_DOOR);
    }

    private void createRedundantNamesProblem(String name){
            player.performCommand("dtp create "+name);
            clearPlayerMessages();
            player.performCommand("dtp create "+name);
            assertEquals("Door with name \""+name+"\" already exists!", player.nextMessage());
    }


    private Location fLoc(int number){
        return new Location(world, number, 1,1);
    }

    private void createDoor(String name, Location location){
        player.setLocation(location);
        player.performCommand("dtp create "+name);
        player.nextMessage();
    }

    private void removeDoor(String name){
        player.performCommand("dtp remove "+name);
        player.nextMessage();
    }

    private int getDoorCount(){
        player.performCommand("dtp list");
        int count = readDoorCountFromMessage(player.nextMessage());
        clearPlayerMessages();
        return count;
    }

    private static final Pattern doorCountPattern = Pattern.compile("[0-9]+");
    private int readDoorCountFromMessage(String message){
        Matcher matcher = doorCountPattern.matcher(message);
        matcher.find();
        return Integer.parseInt(matcher.group());
    }

    private void clearPlayerMessages(){
        while(player.nextMessage() != null){}
    }
}
