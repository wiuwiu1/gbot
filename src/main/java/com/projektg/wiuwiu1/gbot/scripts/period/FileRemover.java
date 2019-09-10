/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projektg.wiuwiu1.gbot.scripts.period;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.api.wrapper.FileListEntry;
import com.projektg.wiuwiu1.gbot.utilities.Logger;
import java.util.Date;
import java.util.List;

public class FileRemover extends BasePeriodScript {
    
    private final String CHANNEL_NAME = "Dateien";
    private final long ALLOWED_TIME = 86400000;

    public FileRemover(TS3Api api, int period) {
        super(api, period);
    }

    @Override
    void execute() {
        int channelID = api.getChannelByNameExact(CHANNEL_NAME, false).getId();
        List<FileListEntry> files = api.getFileList("/", channelID);
        for(FileListEntry file : files){
            if((new Date().getTime() - file.getLastModifiedDate().getTime()) > ALLOWED_TIME){
                api.deleteFile(file.getPath(), channelID);
                Logger.log("FileRemover", "Deleted File " + file.getName());
            }
        }
    }

}
