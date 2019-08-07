package config;

import lombok.Data;

import java.nio.file.Paths;

@Data
public class MysqlConfig implements  IConfig {

    private String con;

    public MysqlConfig(){
    }
}
