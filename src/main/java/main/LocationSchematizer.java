package main;

import org.bukkit.Location;
import org.bukkit.block.BlockFace;

import java.util.HashMap;
import java.util.Map;

public class LocationSchematizer {
    private static final Map<BlockFace, Float> facingsToYaw = new HashMap<>();

    static{
        facingsToYaw.put(BlockFace.NORTH, -180.0f);
        facingsToYaw.put(BlockFace.SOUTH, 0.0f);
        facingsToYaw.put(BlockFace.WEST, 90.0f);
        facingsToYaw.put(BlockFace.EAST, -90.0f);
    }

    public static Location schematize(Location location, BlockFace facing) {
        Location loc = location.clone();
        loc.setX( loc.getBlockX() + 0.5 );
        loc.setZ( loc.getBlockZ() + 0.5 );

        loc.setPitch(0f);
        loc.setYaw(facingsToYaw.get(facing));

        return loc;
    }
}
