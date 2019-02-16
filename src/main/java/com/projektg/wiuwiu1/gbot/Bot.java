package com.projektg.wiuwiu1.gbot;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.TS3Config;
import com.github.theholywaffle.teamspeak3.TS3Query;
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

    public void init(){
        final TS3Config config = new TS3Config();
        config.setHost(this.ip);

        final TS3Query query = new TS3Query(config);
        query.connect();

        final TS3Api api = query.getApi();
        api.login(username, pw);
        api.selectVirtualServerById(1); //vsid anstatt 1
        api.setNickname(this.nickname);
        api.sendChannelMessage(this.nickname + " is online!");
        
        CommandListender cl = new CommandListender(this);
        cl.start();
    }
    
    public void stop(){
        System.exit(0);
    }
    
    

    @Override
    public String toString() {
        return "ip: " + ip + "nickname: " + nickname + "username: " + username + "pw: " + pw + "vsId: " + vsId;
    }
}
