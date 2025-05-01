package ModNav.ModNavUtils;

import ModNav.ModNavStructure.ModNavGraph;
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

    public DBQueryResult loadMapFromDB(){
        DBQueryResult qRes = new DBQueryResult();

        try (ResultSet r = this.executeQuery("SELECT * FROM map;")) {
            while (r.next()){
                qRes.addRow(r.getString("id"), r.getString("paths"), r.getString("names"));
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return qRes;
    }

    public void saveMapToDB(ModNavGraph g){
        g.getAdjacencyList().forEach((n, e) -> {
            try (ResultSet r = this.executeQuery("SELECT * FROM map WHERE id = " + n.getId() + ";")) {
                String ps = JsonOperations.modNavPlaceArrayStringify(e);
                if (!r.getString("paths").equals(ps)){  
                    this.executeUpdate("UPDATE map SET paths = " + ps + ";");
                }
            }
            catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

        });
    }
}
