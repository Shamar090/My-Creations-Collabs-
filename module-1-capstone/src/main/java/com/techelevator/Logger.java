package com.techelevator;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.io.FileOutputStream;
import java.io.PrintWriter;

public class Logger {

    public static void log(String type, String money, String userMoney){
        try (PrintWriter dataOutput = new PrintWriter(new FileOutputStream("log/Log.txt"), true)) {
            String time;
            time = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss a").format(new Date());
            dataOutput.println(time + " " + type + " $" + money + " $" + userMoney);
        } catch (Exception e) {
            System.err.println("File cannot be opened for writing.");
        }
    }

}
