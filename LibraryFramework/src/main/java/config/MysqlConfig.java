package config;

import dao.rdb.dataaccessadapter.DBAdapter;
import dao.rdb.dataaccessadapter.DBTarget;
import lombok.Data;

@Data
public class MysqlConfig implements  IConfig {

    private String dbUrl;
    private  String password;
    private  String username;


    @Override
    public void initStructure() {

        DBTarget dbAdapter=new DBAdapter();
        dbAdapter.createTables();

    }
}
