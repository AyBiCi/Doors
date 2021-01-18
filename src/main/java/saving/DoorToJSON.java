package saving;

import containers.Door;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class DoorToJSON {
    public static String convertTo(Door door){
        JSONObject json = new JSONObject();
        json.put("name", door.getName());
        json.put("X", door.getLocation().getX());
        json.put("Y", door.getLocation().getY());
        json.put("Z", door.getLocation().getZ());
        json.put("yaw", door.getLocation().getYaw());
        json.put("pitch", door.getLocation().getPitch());

        if(door.getLocation().getWorld() != null)
            json.put("world", door.getLocation().getWorld().getName());

        if(door.getCombinedDoorName() !=  null)
            json.put("combinedTo", door.getCombinedDoorName());

        return json.toJSONString();
    }

    public static Door convertFrom(String jsonString){
        JSONObject json = (JSONObject) JSONValue.parse(jsonString);

        Location location = new Location(
                ((json.get("world")) != null ?
                        Bukkit.getWorld( (String) json.get("world") ) :
                        null
                ),
                (Double) json.get("X"),
                (Double) json.get("Y"),
                (Double) json.get("Z"),
                new Float( (Double) json.get("yaw") ),
                new Float( (Double) json.get("pitch") )
        );

        Door door = new Door((String) json.get("name"),location);
        door.combineTo((json.get("combinedTo")) != null ?
                (String) json.get("combinedTo") :
                null
        );

        return door;
    }
}
