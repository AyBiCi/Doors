package containers;

import utils.LocationSchematizer;
import org.bukkit.Location;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Door implements Comparable<Door>{
    @XmlElement
    private final String name;
    @XmlElement
    private final LocationXML location;

    public Door(String name, Location location) {
        this.name = name;
        this.location = new LocationXML(LocationSchematizer.schematizeForDoors(location));
    }

    public String getName() {
        return name;
    }
    public Location getLocation() {
        return location.toBukkitLocation();
    }


    @Override
    public int compareTo(Door door) {
        return getName().compareTo(door.getName());
    }
}
