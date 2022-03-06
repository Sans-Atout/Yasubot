package fr.yachan.database;

import org.ini4j.Wini;
import fr.yachan.usefull.Logs;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

import static fr.yachan.usefull.Logs.*;


public class basicConnection {
    private final ArrayList<String> connexionInfo;
    private String url;
    private Connection db;


    public basicConnection(String dbName) throws IOException {
        this.connexionInfo = this.createUrl(dbName);
        this.db = connect();
    }

    private ArrayList<String> createUrl(String dbName) throws IOException {
        Wini initFile = new Wini(new File("src/fr/yachan/ya-chan.ini"));
        ArrayList<String> returnArray = new ArrayList<>();
        String login = initFile.get("database","login");
        String password = initFile.get("database","password");
        String url = initFile.get("database","url");
        String port = initFile.get("database","port");
        String connUrl = "jdbc:postgresql://"+url+":"+port+"/"+dbName;
        returnArray.add(connUrl);
        returnArray.add(login);
        returnArray.add(password);
        return  returnArray;
    }

    protected Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(this.connexionInfo.get(0),this.connexionInfo.get(1),this.connexionInfo.get(2));

        } catch (SQLException e) {
            logCritical("Yasubot#2605","CONFIG",
                    "CONFIG",e.getMessage());
        }
        return conn;
    }
}
