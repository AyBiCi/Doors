import be.seeseemelk.mockbukkit.MockBukkit;
import main.Doors;
import main.DoorsPlugin;
import org.bukkit.Server;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DoorsPluginTest {
    //MockBukkit mocking
    DoorsPlugin plugin;
    Server server;

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

}
