package fr.yachan;

import fr.yachan.commands.god.statusCommand;
import fr.yachan.commands.sora.StartSoraClassifier;
import fr.yachan.usefull.Logs;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.User;
import org.ini4j.*;

import javax.security.auth.login.LoginException;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class Main {

    private static JDA _JDA ;
    public static String _TOKEN;
    public static String _PREFIX;
    public static String _VERSION;
    private static String _ENV;
    public static String _LOGPATH;
    private static JDABuilder _BUILDER;
    public static User _YASUKO;
    public static HashMap<String,String> _LIB_V;

    public static void main(String[] args) {
        initialisation();
        Logs.logConfig("Initialisation complete");
        Logs.logConfig("Version "+_VERSION);
        Logs.logConfig("Création du bot");
        botCreation();
    }

    private static void botCreation(){
        _BUILDER = JDABuilder   .createDefault(_TOKEN)
                                .setActivity(Activity.playing("type y>help"));
        initCommand();

        try {
            Logs.logConfig("Building du bot");
            _JDA = _BUILDER.build();
        } catch (LoginException e) {
            Logs.logFatal("Yasubot#2605","CONFIG",
                    "CONFIG",e.getMessage());
        }
        _YASUKO = _JDA.getUserById("553207678059544589");
    }

    private static void initialisation(){
        try {
            Wini initFile = new Wini(new File("src/fr/yachan/ya-chan.ini"));
            _LOGPATH = initFile.get("Path","logs_path");
            Logs.logConfig("Récupération du fichier .ini");
            _TOKEN = initFile.get("Base","token");
            _PREFIX =  initFile.get("Base","prefix");
            String[] version = initFile.get("Base","version").split("\\.");
            _VERSION =  version[0]+"."+version[1]+"."+ Integer.toString(Integer.parseInt(version[2]) +1);
            initFile.put("Base","version",_VERSION);
            initFile.store();
            _ENV = initFile.get("Base","mode");
            _LOGPATH = initFile.get("Path","logs_path");

            _LIB_V = new HashMap<>();
            _LIB_V.put("jda",initFile.get("Librairies","jda_version"));
            _LIB_V.put("ini4j",initFile.get("Librairies","ini4j_version"));
            _LIB_V.put("psql",initFile.get("Librairies","psql_version"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void initCommand(){
        _BUILDER.addEventListeners(new statusCommand())
                .addEventListeners(new StartSoraClassifier())
            ;
    }
}
