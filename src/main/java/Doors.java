import org.bukkit.Location;

import java.util.ArrayList;
import java.util.HashMap;

public class Doors {

    public final HashMap<String, Door> doorsByName;

    public Doors(){
        doorsByName = new HashMap<>();
    }

    public void addDoor(String name, Location location) {
        if(doorsByName.containsKey(name)) throw new DoorWithThatNameAlreadyExists(name);
        doorsByName.put(name, new Door(name, location));
    }

    public void checkDoorExistanceAndThrowIfNotExists(String name){
        if(doorsByName.containsKey(name) == false) throw new DoorWithThatNameDoesntExist(name);
    }

    public Door getDoor(String name) {
        checkDoorExistanceAndThrowIfNotExists(name);
        return doorsByName.get(name);
    }

    public int getCount() {
        return doorsByName.size();
    }

    public void removeDoor(String name) {
        checkDoorExistanceAndThrowIfNotExists(name);
        doorsByName.remove(name);
    }

    public ArrayList<Door> getDoorList() {
        return new ArrayList<>(doorsByName.values());
    }

    public static class DoorWithThatNameAlreadyExists extends RuntimeException{
        final private String doorName;

        public DoorWithThatNameAlreadyExists(String name){
            this.doorName = name;
        }

        @Override
        public String getMessage(){
            return "Door with name \""+doorName+"\" already exists!";
        }
    }

    public static class DoorWithThatNameDoesntExist extends RuntimeException{
        final private String doorName;

        public DoorWithThatNameDoesntExist(String name){
            this.doorName = name;
        }

        @Override
        public String getMessage(){
            return "Door with name \""+doorName+"\" doesn't exist!";
        }
    }
}
