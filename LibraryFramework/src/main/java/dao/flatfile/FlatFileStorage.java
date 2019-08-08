package dao.flatfile;

import config.FlatFileConfig;
import config.LibraryManager;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

public class FlatFileStorage {

    public String OUTPUT_DIR;
    /*=

            Paths.get(
                    System.getProperties().getProperty("user.dir"),
                    "src", "resources"
                    ).toString();*/


     FlatFileStorage(){
         FlatFileConfig flatFileConfig = (FlatFileConfig) LibraryManager.getInstance().getConfig();
         OUTPUT_DIR = flatFileConfig.getDir();
     }

    public void saveToStorage(String type, Object ob) {
        ObjectOutputStream out = null;
        try {
            Path path = createPathIfNotExists(type);

            out = new ObjectOutputStream(Files.newOutputStream(path));
            out.writeObject(ob);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (Exception e) {
                }
            }
        }
    }

    public Object readFromStorage(String type) {
        ObjectInputStream in = null;
        Object retVal = null;
        try {

            Path path = createPathIfNotExists(type);

            in = new ObjectInputStream(Files.newInputStream(path));
            retVal = in.readObject();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (Exception e) {
                }
            }
        }
        return retVal;
    }

    protected   Path createPathIfNotExists(String type) throws IOException {

        File file  =  new File(OUTPUT_DIR, type);

        System.out.println(file.getPath());
        if(file.exists()==false) {
            file.createNewFile();
        }
        return FileSystems.getDefault().getPath(OUTPUT_DIR, type);
    }
}
