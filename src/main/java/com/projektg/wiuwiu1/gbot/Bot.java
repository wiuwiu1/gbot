package com.projektg.wiuwiu1.gbot;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.TS3Config;
import com.github.theholywaffle.teamspeak3.TS3Query;
import com.github.theholywaffle.teamspeak3.api.event.ChannelCreateEvent;
import com.github.theholywaffle.teamspeak3.api.event.ChannelDeletedEvent;
import com.github.theholywaffle.teamspeak3.api.event.ChannelDescriptionEditedEvent;
import com.github.theholywaffle.teamspeak3.api.event.ChannelEditedEvent;
import com.github.theholywaffle.teamspeak3.api.event.ChannelMovedEvent;
import com.github.theholywaffle.teamspeak3.api.event.ChannelPasswordChangedEvent;
import com.github.theholywaffle.teamspeak3.api.event.ClientJoinEvent;
import com.github.theholywaffle.teamspeak3.api.event.ClientLeaveEvent;
import com.github.theholywaffle.teamspeak3.api.event.ClientMovedEvent;
import com.github.theholywaffle.teamspeak3.api.event.PrivilegeKeyUsedEvent;
import com.github.theholywaffle.teamspeak3.api.event.ServerEditedEvent;
import com.github.theholywaffle.teamspeak3.api.event.TS3Listener;
import com.github.theholywaffle.teamspeak3.api.event.TextMessageEvent;
import com.github.theholywaffle.teamspeak3.api.exception.TS3ConnectionFailedException;
import com.projektg.wiuwiu1.gbot.scripts.event.WelcomeMessage;
import com.projektg.wiuwiu1.gbot.utilities.CommandListender;



public class Bot {

    private String ip;
    private String nickname;
    private String username;
    private String pw;
    private String vsId;

    public Bot(String ip, String nickname, String username, String pw, String vsId){
        this.ip = ip;
        this.nickname = nickname;
        this.username = username;
        this.pw = pw;
        this.vsId = vsId;
    }

    public void init() {
        final TS3Config config = new TS3Config();
        config.setHost(this.ip);

        final TS3Query query = new TS3Query(config);
        try{
        query.connect();
        } catch(TS3ConnectionFailedException e){
           System.out.println("Failed to connect to Teamspeak Server");
           System.out.println("Shutdown");
           stop();
        }
        System.out.println("Bot has connected to Query");

        final TS3Api api = query.getApi();
        api.login(username, pw);
        api.selectVirtualServerById(1); //vsid anstatt 1
        api.setNickname(this.nickname);
        api.sendChannelMessage(this.nickname + " is online!");
        
        CommandListender cl = new CommandListender(this);
        cl.start();
        
        //load all Event scripts
        initEventScripts(api);
        
    }
    
    public void stop(){
        System.exit(0);
    }
    
    

    @Override
    public String toString() {
        return "ip: " + ip + " nickname: " + nickname + " username: " + username + " pw: " + pw + " vsId: " + vsId;
    }
    
    private void initEventScripts(final TS3Api api){
         api.registerAllEvents(); // nur nötige events sollen importet werden
         //importieren aller event scripts
         
         TS3Listener listeners = new TS3Listener() {
             public void onTextMessage(TextMessageEvent tme) {
                 throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
             }

             public void onClientJoin(ClientJoinEvent cje) {
                 new WelcomeMessage(api, cje).run();
             }

             public void onClientLeave(ClientLeaveEvent cle) {
                 
             }

             public void onServerEdit(ServerEditedEvent see) {
                
             }

             public void onChannelEdit(ChannelEditedEvent cee) {
                 
             }

             public void onChannelDescriptionChanged(ChannelDescriptionEditedEvent cdee) {
                 
             }

             public void onClientMoved(ClientMovedEvent cme) {
                 
             }

             public void onChannelCreate(ChannelCreateEvent cce) {
                 
             }

             public void onChannelDeleted(ChannelDeletedEvent cde) {
                 
             }

             public void onChannelMoved(ChannelMovedEvent cme) {
                 
             }

             public void onChannelPasswordChanged(ChannelPasswordChangedEvent cpce) {
                 
             }

             public void onPrivilegeKeyUsed(PrivilegeKeyUsedEvent pkue) {
                 
             }
         };
    
         api.addTS3Listeners(listeners);
    }
}
