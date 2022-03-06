package fr.yachan.database;

import fr.yachan.entity.SumUpEntity;

import java.io.IOException;
import java.sql.*;
import java.util.HashMap;

public class SoraDataBase extends basicConnection{


    public SoraDataBase() throws IOException {
        super("sora");
    }

    public HashMap<String,Integer> getAllDatabaseStat() throws SQLException {
        HashMap<String, Integer> countResult = new HashMap<String, Integer>();
        Connection connexion = this.connect();
        countResult.put("anime_info",getTableCount("anime_info"));
        countResult.put("sum_up",getTableCount("sum_up"));
        countResult.put("sum_up_links",getTableCount("sum_up_links"));
        return countResult;
    }

    public SumUpEntity getAnUnKnowSumUp(){
        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery("SELECT * FROM sum_up WHERE status LIKE 'UN';"))
        {
            rs.next();
            int id = rs.getInt("id");
            int animeId = rs.getInt("anime_id");
            String text = rs.getString("text");
            String status = rs.getString("status");
            int doneAt = rs.getInt("done_at");
            return new SumUpEntity(id,animeId,text,status,doneAt);

        }catch (Exception exception)
        {
            exception.printStackTrace();
        }finally {
            if (this.connect() != null) {
                try {
                    this.connect().close(); // <-- This is important
                } catch (SQLException e) {
                    /* handle exception */
                }
            }
        }
        return new SumUpEntity(-1,-1,"","",-1);
    }

    private int getTableCount(String table){
        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery("SELECT count(1) FROM"+table+";"))
        {
            rs.next();
            return rs.getInt(1);

        }catch (Exception exception)
        {
            exception.printStackTrace();
        }finally {
            if (this.connect() != null) {
                try {
                    this.connect().close(); // <-- This is important
                } catch (SQLException e) {
                    /* handle exception */
                }
            }
        }
        return -1;
    }


    public void changeStatus(int sumId, String stat) {
        try (Connection conn = this.connect();
             PreparedStatement stmt  = conn.prepareStatement("UPDATE sum_up SET status = ? WHERE id = ?")
        )
        {
            stmt.setString(1, stat);
            stmt.setInt(2, sumId);
            stmt.executeUpdate();

        }catch (Exception exception)
        {
            exception.printStackTrace();
        }finally {
            if (this.connect() != null) {
                try {
                    this.connect().close(); // <-- This is important
                } catch (SQLException e) {
                    /* handle exception */
                }
            }
        }
    }
}
