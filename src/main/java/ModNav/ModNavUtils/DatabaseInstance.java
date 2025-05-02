package ModNav.ModNavUtils;

import ModNav.ModNavStructure.ModNavGraph;

import java.sql.*;

public class DatabaseInstance implements AutoCloseable {
    private Connection conn = null;
    private Statement stmt = null;

    public DatabaseInstance(){
        try {
            String dbFileName = "data.db";
            this.conn = DriverManager.getConnection("jdbc:sqlite:"+ dbFileName);
            this.stmt = conn.createStatement();
            this.stmt.execute("CREATE TABLE IF NOT EXISTS map ( id VARCHAR(10) PRIMARY KEY, names TEXT NOT NULL, paths TEXT NOT NULL);");
        }
        catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void close() {
        try {
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
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
            try {
                String selS = "SELECT * FROM map WHERE id = ?;";
                PreparedStatement selp = conn.prepareStatement(selS);
                selp.setString(1, n.getId());

                ResultSet r = selp.executeQuery();

                String ps = JsonOperations.modNavPlaceArrayStringify(e);
                String nls = JsonOperations.modNavNameListStringify(n.getNames());

                if (!r.next()){
                    String insertS = "INSERT INTO map (id, names, paths) VALUES (?, ?, ?);";
                    PreparedStatement insp = conn.prepareStatement(insertS);
                    insp.setString(1, n.getId());
                    insp.setString(2, JsonOperations.modNavNameListStringify(n.getNames()));
                    insp.setString(3, ps);

                    insp.execute();
                }
                else {
                    if (!r.getString("paths").equals(ps)){
                        String updateS = "UPDATE map SET paths = ? WHERE id = ?;";
                        PreparedStatement updp = conn.prepareStatement(updateS);
                        updp.setString(1, ps);
                        updp.setString(2, n.getId());

                        updp.execute();
                    }

                    if (!r.getString("names").equals(nls)){
                        String updateS = "UPDATE map SET names = ? WHERE id = ?;";
                        PreparedStatement updp = conn.prepareStatement(updateS);
                        updp.setString(1, ps);
                        updp.setString(2, n.getId());

                        updp.execute();
                    }
                }
            }
            catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

        });
    }
}
