import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import main.DoorsPlugin;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CliTest {


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
    public void testCommands(){

    }

}
