package com.oresomecraft.oresomenotes;

import com.sk89q.minecraft.util.commands.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.ListIterator;

public class OresomeCommands {
    OresomeNotes plugin;

    public OresomeCommands(OresomeNotes pl) {
        plugin = pl;
    }

    @Command(aliases = {"notes", "oresomenotes"},
            desc = "OresomeNotes base command",
            usage = "<add/remove/removeall/read/blacklist>",
            min = 0,
            max = 16)
    @CommandPermissions({"oresomenotes.staff"})
    public void notes(CommandContext args, CommandSender sender) {
        if (args.argsLength() > 1) {
            if (args.getString(0).equalsIgnoreCase("add")) {
                if (Utility.hasBlacklist(args.getString(1).toLowerCase()) == false) {
                    if (args.argsLength() > 2) {
                        Utility.addNote(args.getString(1).toLowerCase(), args.getJoinedStrings(2));
                        sender.sendMessage(ChatColor.GREEN + "Success fully added note to " + ChatColor.RED + args.getString(1));
                        //Just in case we want to link it to something else.
                        Bukkit.getPluginManager().callEvent(new PlayerTagEvent(args.getString(1), args.getJoinedStrings(2), sender.getName()));
                    } else {
                        sender.sendMessage(ChatColor.RED + "Usage: /notes add <player> <note...>");
                    }
                } else {
                    sender.sendMessage(ChatColor.RED + "That person is exempt from being tagged!");
                }
            } else if (args.getString(0).equalsIgnoreCase("remove")) {
                if (args.argsLength() == 3) {
                    //We don't need a try/catch method because this is controlled by the framework.
                    int i = Integer.parseInt(args.getString(2));
                    if (OresomeNotes.getInstance().getConfig().contains(args.getString(1).toLowerCase())) {
                        Utility.removeNote(args.getString(1).toLowerCase(), i, sender);
                        sender.sendMessage(ChatColor.GREEN + "Note removed!");
                    } else {
                        sender.sendMessage(ChatColor.RED + "That person does not exist!");
                    }
                } else {
                    sender.sendMessage(ChatColor.RED + "Usage: /notes remove <player> <index>");
                }
            } else if (args.getString(0).equalsIgnoreCase("removeall")) {
                if (args.argsLength() == 2) {
                    if (OresomeNotes.getInstance().getConfig().contains(args.getString(1).toLowerCase())) {
                        Utility.removeNotes(args.getString(1).toLowerCase());
                        sender.sendMessage(ChatColor.GREEN + "All notes removed!");
                    } else {
                        sender.sendMessage(ChatColor.RED + "That person does not exist!");
                    }
                } else {
                    sender.sendMessage(ChatColor.RED + "Usage: /notes removeall <player>");
                }
            } else if (args.getString(0).equalsIgnoreCase("read")) {
                if (args.argsLength() == 2) {
                    if (OresomeNotes.getInstance().getConfig().contains(args.getString(1).toLowerCase())) {
                        List<String> notes = OresomeNotes.getInstance().getConfig().getStringList(args.getString(1).toLowerCase() + ".notes");
                        if(notes.size() > 0){
                        sender.sendMessage(ChatColor.RED + "WARNING: Player " + ChatColor.GOLD + args.getString(1) + ChatColor.RED + " has attached notes!");
                        for (String st : notes) {
                            sender.sendMessage(ChatColor.BLUE + st);
                        }
                        }else{
                            sender.sendMessage(ChatColor.GREEN + "This person's record is clean!");
                        }
                    } else {
                        sender.sendMessage(ChatColor.RED + "That player doesn't exist!");
                    }
                } else {
                    sender.sendMessage(ChatColor.RED + "Usage: /notes read <player>");
                }
            } else if (args.getString(0).equalsIgnoreCase("blacklist")) {
                if (args.argsLength() > 1) {
                    if (args.getString(1).equalsIgnoreCase("add")) {
                        if (args.argsLength() == 3) {
                            Utility.addBlacklist(args.getString(2).toLowerCase());
                            sender.sendMessage(ChatColor.GREEN + "Player " + ChatColor.RED + args.getString(2) + ChatColor.GREEN + " is now exempt from being tagged!");
                        } else {
                            sender.sendMessage(ChatColor.RED + "Usage: /notes blacklist add <player>");
                        }
                    } else if (args.getString(1).equalsIgnoreCase("remove")) {
                        if (args.argsLength() == 3) {
                            Utility.removeBlacklist(args.getString(2).toLowerCase());
                            sender.sendMessage(ChatColor.GREEN + "Player " + ChatColor.RED + args.getString(2) + ChatColor.GREEN + " is no longer exempt from being tagged!");
                        } else {
                            sender.sendMessage(ChatColor.RED + "Usage: /notes blacklist remove <player>");
                        }
                    } else {
                        sender.sendMessage(ChatColor.RED + "Usage: /notes blacklist <remove/add>");
                    }
                } else {
                    sender.sendMessage(ChatColor.RED + "Usage: /notes blacklist <remove/add>");
                }
            } else {
                sender.sendMessage(ChatColor.RED + "Usage: /notes <add/remove/removeall/read/blacklist>");
            }
        } else {
            sender.sendMessage(ChatColor.RED + "Usage: /notes <add/remove/removeall/read/blacklist>");
        }
    }
}