import cli.SubcommandExecutor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CliTest {

    SubcommandExecutor executor;

    @BeforeEach
    public void setup(){
        executor = new SubcommandExecutor();
    }

    @Test
    public void realLifeTest(){
        final String[] testString = new String[1];

        executor.addCommandExecutor("create", (commandSender, command, s, strings) -> {
            testString[0] = "create";
            return false;
        });

        executor.addCommandExecutor("delete", (commandSender, command, s, strings) -> {
            testString[0] = "delete";
            return false;
        });

        executor.onCommand(null, null, null, new String[]{"create"});
        assertEquals("create", testString[0]);

        executor.onCommand(null, null, null, new String[]{"delete"});
        assertEquals("delete", testString[0]);
    }

    @Test
    public void firstCliSubcommandExecutorTest(){

        executor.addCommandExecutor("subcommand", (commandSender, command, s, strings) -> false);

        String[] args = new String[]{"subcommand"};
        executor.onCommand(null,null,null, args);
    }

    @Test
    public void checkIsExecutable(){

        assertFalse(executor.hasExecutor("subcommand"));

        executor.addCommandExecutor("subcommand", (commandSender, command, s, strings) -> false);

        assertTrue(executor.hasExecutor("subcommand"));
    }

    @Test
    public void checkExecution(){
        final Boolean[] executed = {false};

        executor.addCommandExecutor("subcommand", (commandSender, command, s, strings) -> {
            executed[0] = true;
            return false;
        });

        executor.onCommand(null,null,null, new String[]{"subcommand"});

        assertTrue(executed[0]);
    }

    @Test
    public void testThrowNoExecutor(){
        testThrowSubname("makrejsl");
        testThrowSubname("asdgs");
        testThrowSubname("makrsdfgsdfejsl");
        testThrowSubname("makrasdfsadfejsl");
    }

    private void testThrowSubname(String name){
        SubcommandExecutor.NoExecutorForCommand exception =
                assertThrows(SubcommandExecutor.NoExecutorForCommand.class, () -> {
                    executor.onCommand(null, null, null, new String[]{name});
                });

        assertEquals(name,exception.getSubcommandName());
        assertEquals("No executor for subcommand \""+name+"\"!", exception.getMessage());
    }






}
