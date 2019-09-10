/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projektg.wiuwiu1.gbot.scripts.event;

import com.github.theholywaffle.teamspeak3.TS3Api;
import static com.github.theholywaffle.teamspeak3.api.PermissionGroupDatabaseType.REGULAR;
import com.github.theholywaffle.teamspeak3.api.event.ClientJoinEvent;
import com.github.theholywaffle.teamspeak3.api.wrapper.Client;
import com.github.theholywaffle.teamspeak3.api.wrapper.ServerGroup;
import java.util.Date;

public class WelcomeMessage extends BaseEventScript {

    private ClientJoinEvent event;
    private final String message1 = "Wilkommen auf dem Projekt G TeamSpeak!";
    private final String message2 = "Durch Betreten stimmst du unserem [url=https://cutt.ly/oVSVBG]Regelwerk[/url] zu!";
    private final String defaultServerGroup = "Guest";

    public WelcomeMessage(TS3Api api, ClientJoinEvent event) {
        super(api);
        this.event = event;
    }

    @Override
    public void run() {
        api.getServerGroups().stream().filter((group) -> (group.getName().equalsIgnoreCase(defaultServerGroup) && group.getType() == REGULAR)).forEachOrdered((item) -> {
            for (Client cl : api.getClients()) {
                if (cl.getId() == event.getClientId()) {
                    for (ServerGroup sg : api.getServerGroupsByClient(cl)) {
                        if (sg.getId() == item.getId() && cl.getCreatedDate().getTime() / 1000 / 60 == new Date().getTime() / 1000 / 60) {
                            super.api.pokeClient(event.getClientId(), message1);
                            super.api.pokeClient(event.getClientId(), message2);
                        }
                    }
                }
            }

        });

    }

}
