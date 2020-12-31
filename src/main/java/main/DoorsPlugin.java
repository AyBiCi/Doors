package main;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
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
        getCommand("dtp").setExecutor(Cli.createMainCommand());
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event){
        if(event.getAction() == Action.RIGHT_CLICK_BLOCK
        && event.getClickedBlock().getType().equals(Material.IRON_DOOR))
            onPlayerDoorsClick(event.getPlayer(),event.getClickedBlock().getLocation());
    }
    public void onPlayerDoorsClick(Player player, Location doorLocation){
        doors.getDoor(doorLocation);
    }

    //Constuctor for MockBukkit
    protected DoorsPlugin(JavaPluginLoader loader, PluginDescriptionFile description, File dataFolder, File file)
    {
        super(loader, description, dataFolder, file);
    }
}
