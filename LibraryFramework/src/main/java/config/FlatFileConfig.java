package config;

import lombok.Data;

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
}


