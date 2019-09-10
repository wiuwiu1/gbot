package com.projektg.wiuwiu1.gbot;

import com.google.gson.Gson;
import com.projektg.wiuwiu1.gbot.utilities.Logger;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.Writer;
import java.io.IOException;

public class Init {

    private static Bot bot;
    private static FileReader reader;

    public static void main(String[] args) {
        try {
            reader = new FileReader("conf.json");
            Gson gson = new Gson();
            bot = gson.fromJson(reader, Bot.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!(bot instanceof Bot)) {
            try {
                if(reader instanceof FileReader){
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            initJson();
        }
        bot.init();
    }

    private static void initJson() {
        Logger.log("System", "conf.json doesnt exists. building missing conf.json...");
        try {
            Writer writer = new FileWriter("conf.json");
            Gson gson = new Gson();
            bot = new Bot("127.0.0.1", "G-Bot", "serveradmin", "XfVarJcz", "1"); //eigentlich von der standart json erstellen lassen
            gson.toJson(bot, writer);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            Logger.log("System", "ERROR: coud not build config json");
            Logger.exportLog();
            System.exit(0);
        }
    }
}
