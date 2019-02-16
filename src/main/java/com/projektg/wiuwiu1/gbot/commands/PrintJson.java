/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projektg.wiuwiu1.gbot.commands;

import com.projektg.wiuwiu1.gbot.Bot;

/**
 *
 * @author wiuwiu1
 */
public class PrintJson extends Command{
   
   public PrintJson(Bot bot){
       super(bot);
   } 
    
   @Override
   public void run(){
       System.out.println(super.bot.toString());
   }
}
