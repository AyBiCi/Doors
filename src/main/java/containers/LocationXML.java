package containers;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class LocationXML {

    public LocationXML(Location location){
        worldName = location.getWorld().getName();
        X = location.getX();
        Y = location.getY();
        Z = location.getZ();
        yaw = location.getYaw();
        pitch = location.getPitch();
    }

    @XmlElement
    private String worldName;
    @XmlElement
    private double X,Y,Z;
    @XmlElement
    private float yaw, pitch;

    public Location toBukkitLocation(){
        return new Location(Bukkit.getWorld(worldName), X, Y, Z, yaw, pitch);
    }
}
