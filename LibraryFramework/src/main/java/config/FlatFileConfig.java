package config;

import com.sun.org.apache.xml.internal.security.Init;
import dao.flatfile.FlatFileStorage;
import lombok.Data;
import model.*;

import java.io.File;
import java.nio.file.Paths;

@Data
public class FlatFileConfig implements  IConfig {
    private String dir;

    public FlatFileConfig(){
        dir = Paths.get(
                System.getProperties().getProperty("user.dir"),
                "src", "resources"
        ).toString();
    }

    @Override
    public void initStructure() {
        init(Book.class);
        init(Author.class);
        init(CheckoutEntry.class);
        init(Member.class);
        init(User.class);
    }

    private <T> void init(Class<T> type){
        File file  =  new File(dir, type.getName());

        try {
            if (file.exists() == false) {
                file.createNewFile();
            }
        }catch (Exception ex){
         System.out.println(ex.getMessage());
        }
    }
}


