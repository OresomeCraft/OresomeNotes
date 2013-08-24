package com.huskehhh.oresomenotes;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class OresomeListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        if (Utility.hasNotes(e.getPlayer())) {
            Utility.sendStaffNotes(e.getPlayer());
        }
        YamlConfiguration config = YamlConfiguration.loadConfiguration(new File("plugins/OresomeNotes/config.yml"));
        if (!config.getStringList(e.getPlayer().getName() + ".notes").contains("dataManage--noedit")) {
            List<String> ls = new ArrayList<String>();
            ls.add("dataManage--noedit");
            config.set(e.getPlayer().getName() + ".notes", ls);
            try {
                config.save(new File("plugins/OresomeNotes/config.yml"));
            } catch (IOException ee) {
                ee.printStackTrace();
            }
        }

    }

}