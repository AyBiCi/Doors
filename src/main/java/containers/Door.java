package containers;

import utils.LocationSchematizer;
import org.bukkit.Location;

public class Door implements Comparable<Door>{
    private final String name;
    private final Location location;
    private String combinedDoorName;

    public Door(String name, Location location) {
        this.name = name;
        this.location = LocationSchematizer.schematizeForDoors(location);
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

    public void combineTo(String doorToCombineTo) {
        combinedDoorName = doorToCombineTo;
    }

    public String getCombinedDoorName() {
        return combinedDoorName;
    }

    @Override
    public String toString(){
        return  name + " " +
                location.getX() + " " +
                location.getY() + " " +
                location.getZ() + " " +
                location.getYaw() + " " +
                location.getPitch() + " " +
                (location.getWorld() == null ? "null" : location.getWorld().getName()) + " " +
                combinedDoorName;
    }
}
