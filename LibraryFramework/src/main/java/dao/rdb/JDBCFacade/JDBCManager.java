package dao.rdb.JDBCFacade;

import com.ibatis.common.jdbc.ScriptRunner;
import config.LibraryManager;
import config.MysqlConfig;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class JDBCManager {

    private Connection conn;


    private JDBCManager() {
        try {
            loadDriver();
        }catch(Exception slException){}
    }

    private static class InnerJDBCManager {
        private static JDBCManager instance=new JDBCManager();
    }

    public  static JDBCManager getInstance() {
        return InnerJDBCManager.instance;
    }



    private void loadDriver(){
        System.out.println("Loading");
        try {
            // **********Loading driver **********
            Class.forName("com.mysql.jdbc.Driver");
        }catch (ClassNotFoundException ex){System.out.println("Class : "+ex.getMessage());}


        // ********** Opening Database connection **********
        try{

            MysqlConfig mysqlConfig = (MysqlConfig) LibraryManager.getInstance().getConfig();

            String DBurl =mysqlConfig.getDbUrl();// "jdbc:mysql://localhost:3306/library";
            conn = DriverManager.getConnection(DBurl,
                    mysqlConfig.getUsername(),
                    mysqlConfig.getPassword()

            );
        }catch (SQLException ex){System.out.println("Driver : "+ex.getMessage());}

    }



    public List<HashMap<String,Object>>  selection(String sqlQuery){
        Statement stm=null;
        List<HashMap<String,Object>> result=new ArrayList<>();
        ResultSet resultSet=null;
        try{
            stm=conn.createStatement();
            resultSet=stm.executeQuery(sqlQuery);

            //----get the result set metaData that will help us get number of column-------
            //---- and their name so that we can retrieve the data-----------
            ResultSetMetaData rsMetaData=resultSet.getMetaData();
            int nbrCol=rsMetaData.getColumnCount();

            while(resultSet.next()){
                HashMap<String,Object> li=new HashMap<>();
                for(int i=1;i<=nbrCol;i++){
                    String colName=rsMetaData.getColumnName(i);
                    li.put(colName,resultSet.getObject(colName));
                }
                result.add(li);
            }
        }catch(SQLException ex){System.out.println(ex.getMessage());}

        return result;
    }

    public Integer insertData(String sqlQuery){
        System.out.println(sqlQuery);
        sqlQuery.replace("'","''");
        System.out.println(sqlQuery);
        boolean isExecuted=true;
        Integer result=0;
        try{
            Statement stm = conn.createStatement();
            stm.executeUpdate(sqlQuery,
                    Statement.RETURN_GENERATED_KEYS);
            ResultSet rs=stm.getGeneratedKeys();
            if(rs.next()){
                result=rs.getInt(1);
            }


            stm.close();
        }catch(SQLException ex){
            isExecuted=false;
            System.out.println("Insert : "+ex.getMessage());
        }
        return result;
    }


    public boolean updateData(String sqlQuery)  {
        sqlQuery.replace("'","''");
        boolean isExecuted=false;
        try{
            Statement stm = conn.createStatement();
            stm.executeUpdate(sqlQuery);
            stm.close();
            isExecuted=true;
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        return isExecuted;

    }

    public boolean deleteData(String sqlQuery) {
        sqlQuery.replace("'","''");
        boolean isDeleted=true;
        try{
            Statement stm = conn.createStatement();
            stm.executeUpdate(sqlQuery);
            stm.close();

        }catch(SQLException ex){
            isDeleted=false;
            System.out.println(ex.getMessage());
        }
        return isDeleted;

    }



    public void closeConnection() {
        try{
            conn.close();
        }catch(SQLException ex){}

    }

    public boolean runScript(String SQLScriptFilePath){
        try {
            // Initialize object for ScripRunner
            ScriptRunner sr = new ScriptRunner(conn, false, false);

            // Give the input file to Reader
            Reader reader = new BufferedReader(
                    new FileReader(SQLScriptFilePath));

            // Exctute script
            sr.runScript(reader);

        } catch (Exception e) {
            System.err.println("Failed to Execute" + SQLScriptFilePath
                    + " The error is " + e.getMessage());
        }

        return true;
    }

    public boolean runScript2(String script){
        try{
            String s            = new String();
            StringBuffer sb = new StringBuffer();
            /*BufferedReader br = new BufferedReader(new FileReader(script));

            while((s = br.readLine()) != null)
            {
                sb.append(s);
            }
            br.close();*/

            sb.append(script);

            String[] inst = sb.toString().split(";");
            for(int i = 0; i<inst.length; i++){
                if(!inst[i].trim().equals(""))                {
                    Statement stm = conn.createStatement();
                    stm.executeUpdate(inst[i]);
                    stm.close();
                    System.out.println(">>"+inst[i]);
                }
            }
        } catch (Exception e) {
        System.err.println("Failed to Execute" + script
                + " The error is " + e.getMessage());
        }

        return true;
    }
}