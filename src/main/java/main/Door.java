package main;

import org.bukkit.Location;

public class Door implements Comparable<Door>{
    private final String name;
    private final Location location;
    private Door combinedDoor;

    public Door(String name, Location location) {
        this.name = name;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public Location getLocation() {
        return location.clone();
    }

    @Override
    public int compareTo(Door door) {
        return getName().compareTo(door.getName());
    }

    public boolean isPassable() {
        return combinedDoor != null;
    }
    public Location getEndLocation() {
        return combinedDoor.getLocation();
    }

    public void combine(Door door) {
        combinedDoor = door;
    }
}
