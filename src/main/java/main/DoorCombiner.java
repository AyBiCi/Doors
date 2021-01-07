package main;

import java.util.HashMap;

public class DoorCombiner {
    private HashMap<String, String> combinedDoors = new HashMap<>();

    public void combineDoors(Door door1, Door door2){
            combinedDoors.put(door1.getName(), door2.getName());
    }

    public Door getCombinedDoor(Door door){
        try {
            return DoorsPlugin.getInstance().getDoors().getDoor(combinedDoors.get(door.getName()));
        }
        catch(Exception exception){
            return null;
        }
    }
}
