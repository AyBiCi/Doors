package main;

import org.bukkit.Location;

public class Door implements Comparable<Door>{
    private String name;
    private Location location;

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
}
