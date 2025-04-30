package ModNav.ModNavUtils;

import ModNav.ModNavStructure.Path;
import ModNav.ModNavStructure.Place;

import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
                this.stmt.execute("CREATE TABLE map (" +
                        "id VARCHAR(10) PRIMARY KEY," +
                        "names TEXT NOT NULL" +
                        "paths TEXT NOT NULL," +
                        ")"
                );
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

    public Map<Place, List<Path>> loadMapFromDB(){
        Map<Place, List<Path>> m = new HashMap<>();

        try (ResultSet r = this.executeQuery("SELECT * FROM map;")) {
            while (r.next()){
                String pId = r.getString("id");
                Place p = new Place(pId);

                List<String> names = JsonOperations.modNavNameListParse((r.getString("names")));
                List<Path> pth = JsonOperations.modNavMapParse(r.getString("paths"));

                p.setNames(names);

                m.put(p, pth);
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return m;
    }

    public void saveMapToDB(){

    }
}
