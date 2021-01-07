package containers;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.HashMap;

@XmlRootElement
public class DoorCombiner {
    @XmlElement
    private HashMap<String, String> combinedDoors = new HashMap<>();
    private Doors doors;

    public DoorCombiner(Doors doors){
        this.doors = doors;
    }

    public void combineDoors(Door door1, Door door2){
            combinedDoors.put(door1.getName(), door2.getName());
    }

    public Door getCombinedDoor(Door door){
        try {
            return doors.getDoor(combinedDoors.get(door.getName()));
        }
        catch(Exception exception){
            return null;
        }
    }
}
