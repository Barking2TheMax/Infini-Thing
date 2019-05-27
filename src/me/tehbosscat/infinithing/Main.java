package me.tehbosscat.infinithing;

import me.tehbosscat.infinithing.Events.BucketEvents;
import me.tehbosscat.infinithing.Events.PearlEvents;
import me.tehbosscat.infinithing.Items.Buckets.InfiniBucket;
import me.tehbosscat.infinithing.Items.Buckets.InfiniLava;
import me.tehbosscat.infinithing.Items.Buckets.InfiniWater;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;

public class Main extends JavaPlugin {
    public static HashMap<UUID, Long> cooldown = new HashMap<>();

    public static Server server;
    public static ConsoleCommandSender console;
    public static PluginManager pluginManager;
    public static FileConfiguration config;
    public static Economy economy;
    private Commands commands = new Commands();


    public void onEnable(){
        server = getServer();
        if(SetupEconomy()){
            console = server.getConsoleSender();
            pluginManager = server.getPluginManager();
            config = getConfig();

            config.options().copyDefaults(true);
            saveConfig();

            pluginManager.registerEvents(PearlEvents.GetInstance(), this);
            pluginManager.registerEvents(BucketEvents.GetInstance(), this);
            pluginManager.registerEvents(new BucketMenu(), this);

            // Register Commands.
            getCommand(commands.baseCmd).setExecutor(commands);

            SendConsoleMessage(ChatColor.GREEN + "Online!");
        }else{
            SendConsoleMessage("Economy plugin not found.");
            SendConsoleMessage(ChatColor.RED + "Offline.");
        }
    }

    private boolean SetupEconomy(){
        RegisteredServiceProvider<Economy> economyProvider = server.getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if(economyProvider != null){
            economy = economyProvider.getProvider();
            SendConsoleMessage("Economy plugin found.");
        }

        return (economy != null);
    }

    public static void SendConsoleMessage(String string){
       server.getConsoleSender().sendMessage("[" +  ChatColor.GOLD + "Infini" + ChatColor.GRAY + "Thing] " + string);
    }

    public static void SendPlayerMessage(Player player, String string){
        player.sendMessage("[" +  ChatColor.GOLD + "Infini" + ChatColor.WHITE + "Bucket] " + string);
    }
}
