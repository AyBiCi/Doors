package main;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.block.BlockGrowEvent;

public class Door implements Comparable<Door>{
    private final String name;
    private final Location location;
    private Door combinedDoor;

    public Door(String name, Location location) {
        this.name = name;
        BlockFace facing;
        try {
            Block b = location.clone().add(0, 1, 0).getBlock();
        }
        catch(Exception exception){
            facing = BlockFace.NORTH;
        }

        this.location = LocationSchematizer.schematize(location, facing);
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
