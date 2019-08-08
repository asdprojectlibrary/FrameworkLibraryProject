package config;

import lombok.Data;

import java.nio.file.Paths;

@Data
public class MysqlConfig implements  IConfig {

    private String dbUrl;
    private  String password;
    private  String username;

    public MysqlConfig(){

    }

    @Override
    public void initStructure() {

    }
}
