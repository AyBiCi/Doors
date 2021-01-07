import utils.LocationSchematizer;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LocationSchematizerTest {

    @Test
    public void schematizerTestNorth(){
        BlockFace facing = BlockFace.NORTH;

        Location location = new Location(null,
                361.481,
                4.00,
                42.512,
                -153.2f,
                85.9f);

        Location expectedLocation = new Location(null,
                361.5,
                4.00,
                42.5,
                -180f,
                0f);

        Location output = LocationSchematizer.schematize(location, facing);

        assertEquals(expectedLocation.toString(), output.toString());
    }

    @Test
    public void schematizerTestSouth(){
        BlockFace facing = BlockFace.SOUTH;

        Location location = new Location(null,
                358.490,
                4.00,
                42.552,
                178.6f,
                89.1f);

        Location expectedLocation = new Location(null,
                358.5,
                4.00,
                42.5,
                0f,
                0f);

        Location output = LocationSchematizer.schematize(location, facing);

        assertEquals(expectedLocation.toString(), output.toString());
    }

    @Test
    public void schematizerTestWest(){
        BlockFace facing = BlockFace.WEST;

        Location location = new Location(null,
                -358.490,
                4.00,
                -42.552,
                178.6f,
                89.1f);

        Location expectedLocation = new Location(null,
                -358.5,
                4.00,
                -42.5,
                90.0f,
                0f);

        Location output = LocationSchematizer.schematize(location, facing);

        assertEquals(expectedLocation.toString(), output.toString());
    }

    @Test
    public void schematizerTestEast(){
        BlockFace facing = BlockFace.EAST;

        Location location = new Location(null,
                -358.490,
                4.00,
                42.552,
                178.6f,
                89.1f);

        Location expectedLocation = new Location(null,
                -358.5,
                4.00,
                42.5,
                -90.0f,
                0f);

        Location output = LocationSchematizer.schematize(location, facing);

        assertEquals(expectedLocation.toString(), output.toString());
    }
}
