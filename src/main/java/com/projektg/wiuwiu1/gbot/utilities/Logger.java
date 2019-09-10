package com.projektg.wiuwiu1.gbot.utilities;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Logger {
    private static List<String> output = new ArrayList();
    
    public static void log(String unit, String message){
        String log = "[" + unit + "] " + new SimpleDateFormat("dd.MM.yyyy-HH:mm:ss").format(new Date()) + " # " + message;
        output.add(log + "\r\n");
        if(output.size() > 1000)
            exportLog();
        System.out.println(log);
    }
    
    public static void exportLog(){
        try {
            File file = new File(System.getProperty("user.dir") + "/log-" + new SimpleDateFormat("dd.MM.yyyy-HH.mm.ss").format(new Date()) + ".txt");
            file.mkdirs();
            FileWriter fileWriter = new FileWriter(file.getPath(), true);
            output.clear();
        } catch (IOException e) {
            e.printStackTrace();
            log("Logger", "ERROR: cant write log");
        }

    }
}
