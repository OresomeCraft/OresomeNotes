package com.huskehhh.oresomenotes;


import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OresomeListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if (Utility.hasNotes(p)) {
            Utility.sendStaffNotes(p);
        }
        YamlConfiguration config = YamlConfiguration.loadConfiguration(new File("plugins/OresomeNotes/config.yml"));
        if (!config.getStringList(p.getName() + ".notes").contains("dataManage--noedit")) {
            List<String> ls = new ArrayList<String>();
            ls.add("dataManage--noedit");
            config.set(p.getName() + ".notes", ls);
            try {
                config.save(new File("plugins/OresomeNotes/config.yml"));
            } catch (IOException ee) {
                ee.printStackTrace();
            }
        }

    }

}