import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import main.Doors;
import main.DoorsPlugin;
import org.bukkit.Server;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DoorsPluginTest {
    //MockBukkit mocking
    DoorsPlugin plugin;
    ServerMock server;

    @BeforeEach
    public void setUp()
    {
        server = MockBukkit.mock();
        plugin = (DoorsPlugin) MockBukkit.load(DoorsPlugin.class);
    }
    @AfterEach
    public void tearDown()
    {
        MockBukkit.unmock();
    }


    @Test
    public void getDoorsTest(){
        assertNotEquals(null, plugin.getDoors());
        assertTrue(plugin.getDoors() instanceof Doors);
    }

    @Test
    public void getInstanceTest(){
        assertNotEquals(null, plugin.getInstance());
        assertTrue(plugin.getInstance() instanceof DoorsPlugin);
    }

    @Test
    public void testFirstCommand(){
        PlayerMock player = server.addPlayer();
        player.performCommand("dtp");
        String message = player.nextMessage();
        assertEquals("Lista komend - /dtp help.", message);
    }

    @Test
    public void testHelpCommand(){
        PlayerMock player = server.addPlayer();
        player.performCommand("dtp help");
        String message = player.nextMessage();
        assertEquals("Lista subkomend dla komendy dtp", message);
    }
}
