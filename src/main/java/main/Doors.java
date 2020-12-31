package main;

import org.bukkit.Location;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.stream.Collectors;

public class Doors {

    public final HashMap<String, Door> doorsByName;
    public final HashMap<String, Door> doorsByLocationString = new HashMap<>();
    public Doors(){
        doorsByName = new HashMap<>();
    }

    public void addDoor(Door door) {
        if(doorsByName.containsKey(door.getName())) throw new DoorWithThatNameAlreadyExists(door.getName());
        doorsByName.put(door.getName(), door);

        doorsByLocationString.put(stringifyLocation(door.getLocation()),door);
        doorsByLocationString.put(stringifyLocation(door.getLocation().clone().add(new Vector(0,1,0))),door);
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
        ArrayList<Door> d = new ArrayList<Door>(doorsByName.values());
        Collections.sort(d);
        return d;
    }

    public Door getDoor(Location doorLocation) {
        String doorString = stringifyLocation(doorLocation);
        if(doorsByLocationString.containsKey(doorString) == false)
            throw new DoorWithThatLocationDoesntExist(doorLocation);
        return doorsByLocationString.get(doorString);
    }

    private String stringifyLocation(final Location location){
        return location.getWorld().getName()+"|"+location.getBlockX()+"|"+location.getBlockY()+"|"+location.getBlockZ();
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

    public static class DoorWithThatLocationDoesntExist extends RuntimeException{
        Location location;

        public DoorWithThatLocationDoesntExist(Location location){
            this.location = location;
        }

        @Override
        public String getMessage(){
            return "There's no door on this location!";
        }
    }
}
