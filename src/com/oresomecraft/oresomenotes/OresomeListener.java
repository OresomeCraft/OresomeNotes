package com.oresomecraft.oresomenotes;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.ArrayList;

public class OresomeListener implements Listener {

    OresomeNotes plugin;

    public OresomeListener(OresomeNotes pl) {
        plugin = pl;
        pl.getServer().getPluginManager().registerEvents(this, pl);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        final Player p = e.getPlayer();
        Bukkit.getScheduler().runTaskLater(OresomeNotes.getInstance(), new Runnable() {
            public void run() {
                if (Utility.hasNotes(p)) {
                    Utility.sendStaffNotes(p);
                }
            }
        }, 2 * 20);
    }

    @EventHandler
    public void automaticNoteListener(PlayerCommandPreprocessEvent e) {
        if (!Utility.hasAutomatic(e.getPlayer().getName().toLowerCase())) return;
        if (e.getMessage().toString().startsWith("/mute") || e.getMessage().toString().startsWith("/tempmute") || e.getMessage().toString().startsWith("/ban") ||
                e.getMessage().toString().startsWith("/tempban") || e.getMessage().toString().startsWith("/kick")){
            e.getPlayer().sendMessage(ChatColor.RED + "A note was automatically added to the player!");
            Utility.addAnonymousNote(argToTarget(e.getMessage()), argBuilderReason(e.getMessage()));
        }
    }

    private String argToTarget(String arg) {
        ArrayList<String> string = new ArrayList<String>();
        for (String s : arg.split(" ")) string.add(s);
        return string.get(1);
    }

    private String argBuilderReason(String args) {
        ArrayList<String> string = new ArrayList<String>();
        for (String s : args.split(" ")) string.add(s);
        String reason = "";
        reason = "[BANS] " + string.get(0).toUpperCase().replace("/", "") + " -> " + string.get(1) + ":";
        for (int i = 0; i < string.size(); i++) {
            if (i != 0 && i != 1) {
                reason = reason + " " + string.get(i);
            }
        }
        return reason;
    }
}