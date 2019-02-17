package com.projektg.wiuwiu1.gbot.utilities;

import com.projektg.wiuwiu1.gbot.Bot;
import com.projektg.wiuwiu1.gbot.commands.Command;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
        
public class CommandListender extends Thread{
    private Bot bot;
    private BufferedReader in;
    private Map commands;
    
    public CommandListender (Bot bot){
        this.bot = bot;
        this.in = new BufferedReader(new InputStreamReader(System.in));
        this.commands = new HashMap();
        
        //init command map
        initCommand();
    }
    
    @Override
    public void run() {
        System.out.println("commandListender started");
        String line = "";
        while (line.equalsIgnoreCase("quit") == false) {
            try {
                System.out.println("insert command: ");
                line = in.readLine();
            } catch(IOException e){
                
            }
            
            try {
                System.out.println("try to resolve command...");
                String className = (String) commands.get(line);
                Class commandclass = Class.forName(className);
                Constructor con = commandclass.getConstructor(Bot.class);
                Command command = (Command) con.newInstance(bot);
                System.out.println(command.toString());
                command.run();
            } catch(ClassNotFoundException e){
                
            } catch(InstantiationException e){
                
            } catch(IllegalAccessException e){
                
            } catch(NoSuchMethodException e){
                
            } catch(InvocationTargetException e){
                System.out.println("Error: couldnt find Class");
            }
        }
        try {
            in.close(); 
        } catch(IOException e){
               
        }
        
        bot.stop();
    }
    
    private void initCommand(){
        commands.put("printjson", "com.projektg.wiuwiu1.gbot.commands.PrintJson");
    }
    
}
