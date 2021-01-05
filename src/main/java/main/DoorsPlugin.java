package main;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;

import java.io.File;

public class DoorsPlugin extends JavaPlugin implements Listener {

    private final Doors doors = new Doors();
    public Doors getDoors() { return doors; }

    private static DoorsPlugin instance;
    public static DoorsPlugin getInstance() { return instance; }

    @Override
    public void onEnable(){
        instance = this;
        getCommand("dtp").setExecutor(Cli.createMainCommand());
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {

    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event){
        if(event.getAction() == Action.RIGHT_CLICK_BLOCK
        && event.getClickedBlock().getType().equals(Material.IRON_DOOR))
            onPlayerDoorsClick(event.getPlayer(),event.getClickedBlock().getLocation());
    }
    public void onPlayerDoorsClick(Player player, Location doorLocation){
        Door door;
        try {
            door = doors.getDoor(doorLocation);
        }
        catch(Doors.DoorWithThatLocationDoesntExist exception){
            player.sendMessage(exception.getMessage());
            return;
        }

        if(door.isPassable()){
            player.teleport(door.getEndLocation());
        }
        else{
            player.sendMessage("Doors doesn't go anywhere!");
        }
    }

    //For bukkit
    public DoorsPlugin(){

    }

    //Constuctor for MockBukkit
    protected DoorsPlugin(JavaPluginLoader loader, PluginDescriptionFile description, File dataFolder, File file)
    {
        super(loader, description, dataFolder, file);
    }
}
