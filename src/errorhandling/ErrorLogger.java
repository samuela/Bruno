package errorhandling;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: jonathan
 * Date: 5/6/13
 * Time: 10:35 PM
 * To change this template use File | Settings | File Templates.
 */
public class ErrorLogger {

    private static PrintWriter errorLog;
    private static boolean set = false;
    private static void init(){
        try {
            errorLog = new PrintWriter(new BufferedWriter(new FileWriter(
                    System.getProperty("user.home") + "/bruno.log", true)),
                    true);
        } catch (IOException e) {
            errorLog = new PrintWriter(System.err, true);
            e.printStackTrace(); // To change body of catch statement use File |
            // Settings | File Templates.
        }
        set = true;
    }

    public static void log(String message){
        if(!set){
            init();
        }
        errorLog.print(new Timestamp((new Date()).getTime()));
        errorLog.println(" " + message);
    }
}
