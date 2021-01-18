package saving;

import containers.Door;
import containers.Doors;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class DoorsToFilesSaver {
    private String savesPath;
    private File savesFile;

    public boolean loadSavesDirectory(){
        savesFile = new File(savesPath);
        return savesFile.mkdirs();
    }

    public DoorsToFilesSaver(String savesPath){
        this.savesPath = savesPath;
        loadSavesDirectory();
    }

    public ArrayList<Door> getSavedDoors(){
        ArrayList<File> files = new ArrayList<File>(Arrays.asList(savesFile.listFiles()));
        ArrayList<Door> doors = new ArrayList<>();
        for(File save : files){
            if(save.getName().endsWith(".json")){
                try {
                    Scanner fileReader = new Scanner(save);
                    String jsonData = "";

                    while (fileReader.hasNextLine()) {
                        jsonData += fileReader.nextLine() + "\n";;
                    }

                    fileReader.close();
                    Door door = DoorToJSON.convertFrom(jsonData);
                    if(door != null)
                        doors.add(door);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }

        return doors;
    }

    public void saveDoor(Door door){
        try {
            File save = new File(savesPath + File.separator + door.getName() + ".json");
            save.createNewFile();

            FileWriter writer = new FileWriter(save);
            writer.write(DoorToJSON.convertTo(door));
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void removeAllSaves() {
        ArrayList<File> files = new ArrayList<File>(Arrays.asList(savesFile.listFiles()));
        for(File save : files){
            if(save.getName().endsWith(".json"))
                save.delete();
        }
    }

    public void feedDoors(Doors doors) {
        ArrayList<Door> doorsList = getSavedDoors();

        for(Door door : doorsList)
            doors.addDoor(door);
    }

    public void saveAllDoors(Doors doors) {
        for(Door door : doors.getDoorList()){
            saveDoor(door);
        }
    }
}
