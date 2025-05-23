package ModNav.ModNavUtils;

import ModNav.ModNavStructure.ModNavGraph;
import ModNav.ModNavStructure.ModNavSubject;
import ModNav.ModNavStructure.Place;

import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class DatabaseInstance implements AutoCloseable {
    private Connection conn = null;
    private Statement stmt = null;

    public DatabaseInstance(){
        try {
            String dbFileName = "data.db";
            this.conn = DriverManager.getConnection("jdbc:sqlite:"+ dbFileName);
            this.stmt = conn.createStatement();
            this.stmt.execute("CREATE TABLE IF NOT EXISTS map ( id VARCHAR(10) PRIMARY KEY, names TEXT NOT NULL, paths TEXT NOT NULL);");
            this.stmt.execute("CREATE TABLE IF NOT EXISTS subject ( id VARCHAR(10) PRIMARY KEY, bid TEXT NOT NULL, sn TEXT NOT NULL);");
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

    public DBMapQueryResult loadMapFromDB(){
        DBMapQueryResult qRes = new DBMapQueryResult();
        Map<String, Place> pm = new HashMap<>();

        try (ResultSet r = this.executeQuery("SELECT id, names FROM map;")){
            while (r.next()){
                String id = r.getString("id");
                List<String> names = JsonOperations.modNavNameListParse(r.getString("names"));
                Place p = new Place(id);
                p.setNames(names);
                pm.put(id, p);
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try (ResultSet r = this.executeQuery("SELECT * FROM map;")) {
            while (r.next()){
                qRes.addRow(pm.get(r.getString("id")), r.getString("paths"), pm);
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return qRes;
    }

    public DBSubjectQueryResult loadSubjectDataFromDB(ModNavGraph g){
        DBSubjectQueryResult qRes = new DBSubjectQueryResult();
        try (ResultSet r = this.executeQuery("SELECT * FROM subject;");) {
            while (r.next()){
                String sid = r.getString("id");
                String bid = r.getString("bid");
                String sn = r.getString("sn");

                Optional<Place> p = g.getPlaceById(bid);

                if (p.isEmpty()){
                    throw new RuntimeException();
                }

                qRes.addRow(sid, sn, p.get());
            }
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }

        return qRes;
    }

    @Deprecated
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
                else  {
                    if (!r.getString("paths").equals(ps)){
                        String updateS = "UPDATE map SET paths = ? WHERE id = ?;";
                        PreparedStatement updp = conn.prepareStatement(updateS);
                        updp.setString(1, ps);
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

        g.getSubjectMap().forEach((sid, sub) -> {
            try {
                ResultSet r = this.executeQuery("SELECT * FROM subject");

                if (!r.next()){
                    String insertS = "INSERT INTO subject (id, bid, sn) VALUES (?, ?, ?);";
                    PreparedStatement insp = conn.prepareStatement(insertS);
                    insp.setString(1, sid);
                    insp.setString(2, sub.getBuilding().getId());
                    insp.setString(3, sub.getSubjectName());
                    insp.execute();
                }
                else {
                    if (!r.getString("bid").equals(sub.getBuilding().getId())){
                        String updateS = "UPDATE subject SET bid = ?, sn = ? WHERE id = ?;";
                        PreparedStatement updp = conn.prepareStatement(updateS);
                        updp.setString(1, sub.getBuilding().getId());
                        updp.setString(2, sub.getSubjectName());
                        updp.setString(3, sid);
                        updp.execute();
                    }
                }
            }
            catch (SQLException e){
                throw new RuntimeException(e);
            }
        });
    }

    public void savePlaceToDB(Place n, ModNavGraph g){
        try {
            String selS = "SELECT * FROM map WHERE id = ?;";
            PreparedStatement selp = conn.prepareStatement(selS);
            selp.setString(1, n.getId());

            ResultSet r = selp.executeQuery();

            String ps = JsonOperations.modNavPlaceArrayStringify(g.getPathsFromPlace(n));
            String nls = JsonOperations.modNavNameListStringify(n.getNames());

            if (!r.next()){
                String insertS = "INSERT INTO map (id, names, paths) VALUES (?, ?, ?);";
                PreparedStatement insp = conn.prepareStatement(insertS);
                insp.setString(1, n.getId());
                insp.setString(2, JsonOperations.modNavNameListStringify(n.getNames()));
                insp.setString(3, ps);

                insp.execute();
            }
            else  {
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
                    updp.setString(1, nls);
                    updp.setString(2, n.getId());

                    updp.execute();
                }
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removePlaceRow(Place p){
        try {
            String delS = "DELETE FROM map WHERE id = ?;";
            PreparedStatement delPs = conn.prepareStatement(delS);
            delPs.setString(1, p.getId());
            delPs.execute();
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveSubject(String sid, ModNavSubject sub){
        try {
            String selS = "SELECT * FROM subject WHERE id = ?";
            PreparedStatement selPs = conn.prepareStatement(selS);
            selPs.setString(1, sid);

            ResultSet r = selPs.executeQuery();
            if (!r.next()){
                String insertS = "INSERT INTO subject (id, bid, sn) VALUES (?, ?, ?);";
                PreparedStatement insp = conn.prepareStatement(insertS);
                insp.setString(1, sid);
                insp.setString(2, sub.getBuilding().getId());
                insp.setString(3, sub.getSubjectName());
                insp.execute();
            }
            else if (!r.getString("bid").equals(sub.getBuilding().getId())){
                String updateS = "UPDATE subject SET bid = ?, sn = ? WHERE id = ?;";
                PreparedStatement updp = conn.prepareStatement(updateS);
                updp.setString(1, sub.getBuilding().getId());
                updp.setString(2, sub.getSubjectName());
                updp.setString(3, sid);
                updp.execute();
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeSubjectRow(String sub){
        try {
            String delS = "DELETE FROM subject WHERE id = ?;";
            PreparedStatement delPs = conn.prepareStatement(delS);
            delPs.setString(1, sub);
            delPs.execute();
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
