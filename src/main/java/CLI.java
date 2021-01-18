import com.github.aybici.Subcommand;
import com.github.aybici.SubcommandExecutor;
import containers.Door;
import containers.Doors;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class CLI {
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

        executor.addCommandExecutor(new Subcommand(
                "combine",
                "<name1> <name2>",
                "combine doors by name",
                (commandSender, command, s, args) -> {
                    Door door1,door2;

                    try{
                        door1 = DoorsPlugin.getInstance().getDoors().getDoor(args[0]);
                        door2 = DoorsPlugin.getInstance().getDoors().getDoor(args[1]);
                    }
                    catch(Doors.DoorWithThatNameDoesntExist exception){
                        commandSender.sendMessage(exception.getMessage());
                        return false;
                    }

                    if(door1.getName().equals(door2.getName()) == false)
                        DoorsPlugin.getInstance().getDoors().combineDoors(door1, door2);

                    else
                    {
                        commandSender.sendMessage("Cannot combine doors with themselves!");
                        return false;
                    }
                    commandSender.sendMessage("Doors \""+door1.getName()+
                            "\" ---> \""+door2.getName()+"\" combined!");
                    return true;
                }
        ));

        executor.addCommandExecutor(new Subcommand(
                "save",
                "",
                "save all doors",
                (commandSender, command, s, args) -> {
                    DoorsPlugin.getInstance().getSaver().saveAllDoors(DoorsPlugin.getInstance().getDoors());
                    commandSender.sendMessage("All doors saved!");
                    return true;
                }
        ));




        return executor;
    }
}
