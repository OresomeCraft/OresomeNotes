package com.oresomecraft.oresomenotes;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class Utility {

    public static List<Player> getStaff() {
        List<Player> staff = new ArrayList<Player>();
        for (Player p : OresomeNotes.getInstance().getServer().getOnlinePlayers()) {
            if (p.hasPermission("oresomenotes.staff")) staff.add(p);
        }
        return staff;
    }

    public static List<String> getNotes(String p) {
        return OresomeNotes.getInstance().getConfig().getStringList(p.toLowerCase() + ".notes");
    }

    public static void addNote(String p, String note, String sender) {
        List<String> tmp1 = OresomeNotes.getInstance().getConfig().getStringList(p.toLowerCase() + ".notes");
        tmp1.add(note + " [ " + sender + " ] ");
        OresomeNotes.getInstance().getConfig().set(p + ".notes", tmp1);
        OresomeNotes.getInstance().saveConfig();
    }

    public static void addBlacklist(String p) {
        List<String> tmp1 = OresomeNotes.getInstance().getConfig().getStringList("blacklist");
        if (!tmp1.contains(p)) {
            tmp1.add(p);
            OresomeNotes.getInstance().getConfig().set("blacklist", tmp1);
            OresomeNotes.getInstance().saveConfig();
        }
    }

    public static void removeBlacklist(String p) {
        List<String> tmp1 = OresomeNotes.getInstance().getConfig().getStringList("blacklist");
        try {
            tmp1.remove(p);
        } catch (Exception e) {
            e.printStackTrace();
            //It might not exist, even though I don't think it'll throw an error.
        }
        OresomeNotes.getInstance().getConfig().set("blacklist", tmp1);
        OresomeNotes.getInstance().saveConfig();
    }

    public static boolean hasBlacklist(String p) {
        return OresomeNotes.getInstance().getConfig().getStringList("blacklist").contains(p);
    }

    public static void removeNote(String p, int entry, CommandSender sender) {
        List<String> notes = OresomeNotes.getInstance().getConfig().getStringList(p.toLowerCase() + ".notes");
        ListIterator<String> iter = notes.listIterator();
        int count = 0;
        boolean stopcheck = false;
        while (iter.hasNext() && stopcheck == false) {
            count++;
            if (count == entry - 1) {
                try {
                    sender.sendMessage(ChatColor.RED + "Removed message " + notes.get(count));
                    notes.remove(count);
                } catch (IndexOutOfBoundsException e) {
                    stopcheck = true;
                }
                break;
            }
        }
        if (notes.size() == 0) {
            OresomeNotes.getInstance().getConfig().set(p.toLowerCase() + ".notes", null);
            OresomeNotes.getInstance().saveConfig();
            sender.sendMessage(ChatColor.RED + "No more notes are tagged to this player!");
            return;
        }
        OresomeNotes.getInstance().getConfig().set(p.toLowerCase() + ".notes", notes);
        OresomeNotes.getInstance().saveConfig();
    }

    public static void removeNotes(String p) {
        List<String> notes = OresomeNotes.getInstance().getConfig().getStringList(p.toLowerCase() + ".notes");
        notes.clear();
        OresomeNotes.getInstance().getConfig().set(p.toLowerCase() + ".notes", null);
        OresomeNotes.getInstance().saveConfig();
    }

    public static boolean hasNotes(Player p) {
        List<String> notes = getNotes(p.getName());
        if (notes.size() > 0) {
            return true;
        }
        return false;
    }

    public static void sendStaffNotes(Player p) {
        List<Player> staff = getStaff();
        List<String> notes = OresomeNotes.getInstance().getConfig().getStringList(p.getName().toLowerCase() + ".notes");
        for (Player s : staff) {
            s.sendMessage(ChatColor.RED + "WARNING: Player " + ChatColor.GOLD + p.getName() + ChatColor.RED + " has attached notes!");
            for (String st : notes) {
                s.sendMessage(ChatColor.BLUE + st);
            }
        }
    }

}