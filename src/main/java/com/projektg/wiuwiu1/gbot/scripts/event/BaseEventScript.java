/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projektg.wiuwiu1.gbot.scripts.event;

import com.github.theholywaffle.teamspeak3.TS3Api;

/**
 *
 * @author wiuwiu1
 */
public class BaseEventScript {
    
    protected TS3Api api;
    
    public BaseEventScript(TS3Api api){
        this.api = api;
    }
    
    public void run(){
        
    }
    
}
