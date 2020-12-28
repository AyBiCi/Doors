package main;

import com.github.aybici.Subcommand;
import com.github.aybici.SubcommandExecutor;
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
                "<name>",
                "removes door by the name",
                (commandSender, command, s, args) -> {
                    commandSender.sendMessage("List of doors names:");

                    for(Door door : DoorsPlugin.getInstance().getDoors().getDoorList()){
                        commandSender.sendMessage(door.getName());
                    }
                    return false;
                }
        ));

        return executor;
    }
}
