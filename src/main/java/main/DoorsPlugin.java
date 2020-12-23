package main;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;

import java.io.File;

public class DoorsPlugin extends JavaPlugin {

    private Doors doors = new Doors();
    public Doors getDoors() { return doors; }

    private static DoorsPlugin instance;
    public static DoorsPlugin getInstance() { return instance; }

    public void onEnable(){
        instance = this;
        String standardCommandName = "dtp";
        getCommand(standardCommandName).setExecutor(createSubcommandExecutor(standardCommandName));
    }

    private SubcommandExecutor createSubcommandExecutor(String commandName){
        SubcommandExecutor commandExecutor = new SubcommandExecutor(commandName);

        //When player write bad or none subcommand
        commandExecutor.setDefaultExecutor(new CommandExecutor() {
            @Override
            public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
                commandSender.sendMessage("Lista komend - /dtp help.");
                return false;
            }
        });

        return commandExecutor;
    }

    //Constuctors for MockBukkit
    public DoorsPlugin()
    {
        super();
    }
    protected DoorsPlugin(JavaPluginLoader loader, PluginDescriptionFile description, File dataFolder, File file)
    {
        super(loader, description, dataFolder, file);
    }
}
