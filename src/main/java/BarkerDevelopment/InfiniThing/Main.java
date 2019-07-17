package BarkerDevelopment.InfiniThing;

import BarkerDevelopment.InfiniThing.Events.BucketEvents;
import BarkerDevelopment.InfiniThing.Events.PearlEvents;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Main for the plugin: Main
 */
public class Main extends JavaPlugin {
    public static Server server;
    public static Plugin plugin;
    public static ConsoleCommandSender console;
    public static PluginManager pluginManager;
    public static FileConfiguration config;
    public static Economy economy;
    private Commands commands;

    // Events.
    /**
     * Called when the plugin is enabled.
     *
     * Will not enable the plugin if an Economy plugin is not found.
     */
    public void onEnable(){
        try{
            plugin = this;
            server = getServer();
            if(SetupEconomy()){
                SendConsoleMessage(ChatColor.GREEN + "Economy plugin found.");

                console = server.getConsoleSender();
                pluginManager = server.getPluginManager();
                config = getConfig();

                config.options().copyDefaults(true);
                saveConfig();

                // Register events.
                pluginManager.registerEvents(PearlEvents.GetInstance(), this);
                pluginManager.registerEvents(BucketEvents.GetInstance(), this);

                // Register commands.
                commands = new Commands();
                getCommand(commands.baseCmd).setExecutor(commands);

                SendConsoleMessage(ChatColor.GREEN + "Online!");

            }else{
                SendConsoleMessage(ChatColor.RED + "Economy plugin not found.");
                SendConsoleMessage(ChatColor.RED + "Offline.");
            }

        }catch (Exception e){
            SendConsoleMessage(ChatColor.RED + "Error on start-up." + ChatColor.GRAY +
                    " Please submit a bug report on the GitHub page.");
            e.printStackTrace();
            SendConsoleMessage(ChatColor.RED + "Offline.");
        }
    }


    // Methods.
    /**
     * Checks if the server has an economy plugin. If so sets its reference up.
     *
     * @return TRUE if an Economy plugin is found. FALSE f an Economy plugin is not found.
     */
    private boolean SetupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }

        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }

        economy = rsp.getProvider();
        return economy != null;
    }

    /**
     * Prints a formatted string to the server console.
     *
     * @param string String to send to the console.
     */
    public static void SendConsoleMessage(String string){
       server.getConsoleSender().sendMessage("[" +  ChatColor.GOLD + "Infini" + ChatColor.GRAY + "Thing] " + string);
    }

    /**
     * Prints a formatted string to the player.
     *
     * @param player The player to send the message to.
     * @param string String to send to the player.
     */
    public static void SendPlayerMessage(Player player, String string){
        player.sendMessage("[" +  ChatColor.GOLD + "Infini" + ChatColor.WHITE + "Thing] " + string);
    }
}
