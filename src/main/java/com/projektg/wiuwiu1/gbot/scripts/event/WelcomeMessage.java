/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projektg.wiuwiu1.gbot.scripts.event;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.api.event.ClientJoinEvent;
import java.util.Arrays;
import java.util.Date;

public class WelcomeMessage extends BaseEventScript {
    
    private ClientJoinEvent event;
    private String message = "Wilkommen auf dem Projekt G TeamSpeak! Durch Betreten sitmmst du unserem [url=https://docs.google.com/document/d/1XMnawykwF7UgVZsDWSYqlKdBZLz3JiBcDNCK7CBTASc/edit?usp=sharing]Regelwerk[/url] zu!";
    
    public WelcomeMessage(TS3Api api, ClientJoinEvent event){
        super(api);
        this.event = event;
    }
    
    @Override
    public void run(){
        System.out.println(api.getServerGroups().get(0).getName()); //?????
        if (Arrays.asList(api.getClientByUId(event.getUniqueClientIdentifier()).getServerGroups()).contains(0)){
            if (api.getClientByUId(event.getUniqueClientIdentifier()).getCreatedDate().getTime() / 1000 / 60 == new Date().getTime() / 1000 / 60){
                super.api.pokeClient(event.getClientId(), message);
            }
        }    
    }
            
    
}
