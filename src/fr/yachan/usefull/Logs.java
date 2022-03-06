package fr.yachan.usefull;

import fr.yachan.Main;

import java.io.File;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logs {

    public static void logTrace(String userName, String salon, String guild, String message)
    {
        writeLog(LogLevel.TRACE,userName,salon,guild,message);
    }
    public static void logInfo(String userName, String salon, String guild, String message)
    {
        writeLog(LogLevel.INFO,userName,salon,guild,message);
    }
    public static void logCritical(String userName, String salon, String guild, String message){
        writeLog(LogLevel.CRITICAL,userName,salon,guild,message);
    }

    public static void logFatal(String userName, String salon, String guild, String message){
        writeLog(LogLevel.FATAL,userName,salon,guild,message);
    }

    public static void logError(String userName, String salon, String guild, String message){
        writeLog(LogLevel.ERROR,userName,salon,guild,message);
    }

    public static void logDebug(String message){
        writeLog(LogLevel.DEBUG,"Yasubot#2605","CONFIG","CONFIG",message);
    }

    public static void logWarning(String userName, String salon, String guild, String message){
        writeLog(LogLevel.WARNING,userName,salon,guild,message);
    }
    public static void logNotice(String userName, String salon, String guild, String message) {
        writeLog(LogLevel.NOTICE,userName,salon,guild,message);

    }

    public static void logConfig(String message){
        writeLog(LogLevel.NOTICE,"Yasubot#0000","CONFIG","CONFIG",message);
    }

    private static String getLogName()
    {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return Main._LOGPATH+ "log-"+formatter.format(now)+".memory";
    }

    private static String formatLog(LogLevel level, String userName, String salon, String guild, String message)
    {
        LocalDateTime now = LocalDateTime.now();
        message = message.replace("\n"," ");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        return "["+formatter.format(now)+"];["+level.toString()+"];["+guild+"];["+salon+"];["+userName+"];"+message+"\r\n";
    }

    private static void writeLog(LogLevel l,String userName, String salon, String guild,String message)
    {
        try
        {
            File f = new File(getLogName());
            FileWriter fw = new FileWriter(f,true);
            fw.write(formatLog(l,userName,salon,guild,message));
            fw.close();
        }catch (Exception e){
            System.out.println(formatLog(LogLevel.CRITICAL,"Yasubot#2605","CONFIG","CONFIG",e.getMessage()));
        }
    }

}
