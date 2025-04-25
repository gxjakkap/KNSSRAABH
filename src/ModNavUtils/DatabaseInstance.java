package ModNavUtils;

import java.sql.*;

public class DatabaseInstance {
    private Connection conn = null;
    private Statement stmt = null;

    public DatabaseInstance(){
        try {
            String dbFileName = "data.db";
            this.conn = DriverManager.getConnection("jdbc:sqlite:"+ dbFileName);
            this.stmt = conn.createStatement();

            if (!FileUtil.checkFileExistence(dbFileName)){
                // TODO: Table creation statement
                // this.stmt.execute("CREATE TABLE ")
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void close(){
        try {
            this.conn.close();
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void executeUpdate(String sql){
        try {
            this.stmt.executeUpdate(sql);
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ResultSet executeQuery(String sql){
        try {
            return this.stmt.executeQuery(sql);
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
