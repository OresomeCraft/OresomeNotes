package com.huskehhh.oresomenotes;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class OresomeNotes extends JavaPlugin {

    private static OresomeNotes plugin;
    public YamlConfiguration config = YamlConfiguration.loadConfiguration(new File("plugins/OresomeNotes/config.yml"));
    public final Logger logger = Logger.getLogger("Minecraft");

    @Override
    public void onEnable() {
        createConfig();

        manageListeners();

        registerCommands();

        plugin = this;

        PluginDescriptionFile pdfFile = getDescription();
        this.logger.info(pdfFile.getName() + " version " + pdfFile.getVersion() + "is now enabled");
    }

    @Override
    public void onDisable() {

        PluginDescriptionFile pdfFile = getDescription();
        this.logger.info(pdfFile.getName() + " is now disabled");
    }

    private void createConfig() {
        boolean exists = new File("plugins/OresomeNotes/config.yml").exists();
        if (!exists) {
            new File("plugins/OresomeNotes").mkdir();
            config.options().header("OresomeNotes, made by Husky!");
            try {
                config.save("plugins/OresomeNotes/config.yml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void manageListeners() {
        this.getServer().getPluginManager().registerEvents(new OresomeListener(), this);
    }

    private void registerCommands() {
        getCommand("notes").setExecutor(new OresomeCommands());
    }

    public static OresomeNotes getInstance() {
        return plugin;
    }

}