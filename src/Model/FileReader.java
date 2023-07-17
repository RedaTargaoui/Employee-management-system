/**
 * @author Reda TARGAOUI
 * @version 1.0
 * @since 18/05/2023
 */
package Model;

import java.io.*;
import java.util.ArrayList;

public class FileReader<Type> {
    // Attributes :
    private final String filePath;

    /**
     * Initialize object
     * @param filePath path to file
     */
    public FileReader(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Pull file's content
     * @return List that contains file content
     */
    public ArrayList<Type> pullFromFile() {
        ArrayList<Type> fileContent = new ArrayList<>();

        try {
            File file = new File(this.filePath);
            // If the file exist :
            if (file.exists()) {
                // Creating an ObjectInputStream to deserialize file content :
                ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));

                fileContent = (ArrayList<Type>) in.readObject();

                in.close();
            }

        } catch (Exception EXCObject) {
            EXCObject.printStackTrace();
        }

        return fileContent;
    }

    /**
     * Push data to file
     * @param contentToPush content to push
     */
    public void pushToFile(ArrayList<Type> contentToPush) {
        try {
            // If data folder doesn't exist we create it :
            File dataFolder = new File("./data");
            if (!dataFolder.exists()) {
                dataFolder.mkdirs();
            }
            // Creating an ObjectOutputStream to serialize object and add them to file :
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(this.filePath));
            // Write Object to the file using serialization :
            out.writeObject(contentToPush);
            // Close the outputStream :
            out.close();
        } catch (Exception EXCObject) {
            EXCObject.printStackTrace();
        }
    }
}
