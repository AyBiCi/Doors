package main;

import com.github.aybici.Subcommand;
import com.github.aybici.SubcommandExecutor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Cli {
    public static SubcommandExecutor createMainCommand(){
        SubcommandExecutor executor = new SubcommandExecutor("dtp");

        executor.addCommandExecutor(new Subcommand(
                "create",
                "<name>",
                "create new door on the place you stay",
                (commandSender, command, s, args) -> {
                    Player player = (Player) commandSender;
                    try {
                        DoorsPlugin.getInstance().getDoors().addDoor(new Door(args[0], player.getLocation()));
                        commandSender.sendMessage("Doors with name \""+args[0]+"\" has been added!");
                    }
                    catch(Doors.DoorWithThatNameAlreadyExists exception){
                        commandSender.sendMessage(exception.getMessage());
                    }
                    return false;
                }
        ));

        executor.addCommandExecutor(new Subcommand(
                "remove",
                "<name>",
                "removes door by the name",
                (commandSender, command, s, args) -> {
                    try {
                        DoorsPlugin.getInstance().getDoors().removeDoor(args[0]);
                        commandSender.sendMessage("Doors with name \""+args[0]+"\" has been removed!");
                    }
                    catch(Doors.DoorWithThatNameDoesntExist exception){
                        commandSender.sendMessage(exception.getMessage());
                    }
                    return false;
                }
        ));

        executor.addCommandExecutor(new Subcommand(
                "list",
                "",
                "gives list of doors",
                (commandSender, command, s, args) -> {
                    int c = DoorsPlugin.getInstance().getDoors().getCount();
                    commandSender.sendMessage("List of doors names ("+c+"):");

                    for(Door door : DoorsPlugin.getInstance().getDoors().getDoorList()){
                        commandSender.sendMessage(door.getName());
                    }
                    return false;
                }
        ));

        executor.addCommandExecutor(new Subcommand(
                "info",
                "[name]",
                "create new door on the place you stay",
                (commandSender, command, s, args) -> {
                    Door door;
                    if(args.length == 0) {
                        Location doorLocation = ((Player) commandSender).getLocation();
                        try {
                            door = DoorsPlugin.getInstance().getDoors().getDoor(doorLocation);
                        } catch (Doors.DoorWithThatLocationDoesntExist exception) {
                            commandSender.sendMessage(exception.getMessage());
                            return false;
                        }
                    }
                    else{
                        try {
                            door = DoorsPlugin.getInstance().getDoors().getDoor(args[0]);
                        } catch (Doors.DoorWithThatNameDoesntExist exception) {
                            commandSender.sendMessage(exception.getMessage());
                            return false;
                        }
                    }
                    commandSender.sendMessage("Name: " + door.getName());
                    return false;
                }
        ));

        return executor;
    }
}
