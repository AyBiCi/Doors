package saving;

import main.DoorsPlugin;
import org.bukkit.Location;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.StringWriter;

public class DoorXMLManager {

    @XmlRootElement
    private static class Door{

        @XmlAttribute
        public String name;

        @XmlElement
        public String worldName;
        @XmlElement
        public double X;
        @XmlElement
        public double Y;
        @XmlElement
        public double Z;
        @XmlElement
        public float yaw;
        @XmlElement
        public float pitch;

        @XmlElement
        public String combinedDoorsName;
    }

    public static String generate(main.Door door){
        Marshaller marshallerObj = null;
        
        DoorXMLManager.Door doorXML = new DoorXMLManager.Door();
        doorXML.name = door.getName();

        Location location = door.getLocation();

        try {
            doorXML.worldName = location.getWorld().getName();
        }
        catch(Exception exception){}

        doorXML.X = location.getX();
        doorXML.Y = location.getY();
        doorXML.Z = location.getZ();
        doorXML.yaw = location.getYaw();
        doorXML.pitch = location.getPitch();

        try {
            doorXML.combinedDoorsName = DoorsPlugin.getInstance().getDoorCombiner().getCombinedDoor(door).getName();
        }
        catch(Exception exception){}

        StringWriter strWriter = new StringWriter();

        try {
            JAXBContext contextObj = JAXBContext.newInstance(DoorXMLManager.Door.class);
            marshallerObj = contextObj.createMarshaller();
            marshallerObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshallerObj.marshal(doorXML, strWriter);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return strWriter.toString();
    }

    public static main.Door loadFromXML(String doorInXML) {
        main.Door newDoor = new main.Door("drzwi",new Location(null, 0,0,0));
        return newDoor;
    }
}
