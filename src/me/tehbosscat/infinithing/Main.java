package me.tehbosscat.infinithing;

import me.tehbosscat.infinithing.Events.BucketEvents;
import me.tehbosscat.infinithing.Items.InfiniBucket;
import me.tehbosscat.infinithing.Items.InfiniBucketLava;
import me.tehbosscat.infinithing.Items.InfiniBucketWater;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    public static Server server;
    public static ConsoleCommandSender console;
    public static PluginManager pluginManager;
    public static FileConfiguration config;
    public static Economy economy = null;

    private Commands commands = new Commands();

    public static InfiniBucket empty;
    public static InfiniBucket water;
    public static InfiniBucket lava;

    private boolean SetupEconomy(){
        RegisteredServiceProvider<Economy> economyProvider = server.getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if(economyProvider != null){
            economy = economyProvider.getProvider();
            SendConsoleMessage("Economy plugin found.");
        }

        return (economy != null);
    }

    public void onEnable(){
        server = getServer();
        if(SetupEconomy()){
            console = server.getConsoleSender();
            pluginManager = server.getPluginManager();
            config = getConfig();

            config.options().copyDefaults(true);
            saveConfig();

            pluginManager.registerEvents(new BucketEvents(), this);

            getCommand(commands.baseCmd).setExecutor(commands);

            empty = new InfiniBucket();
            water = new InfiniBucketWater();
            lava = new InfiniBucketLava();

            SendConsoleMessage(ChatColor.GREEN + "Online!");
        }else{
            SendConsoleMessage("Economy plugin not found.");
            SendConsoleMessage(ChatColor.RED + "Offline!");
        }
    }

    public static void SendConsoleMessage(String string){
       server.getConsoleSender().sendMessage("[" +  ChatColor.GOLD + "Infini" + ChatColor.GRAY + "Thing] " + string);
    }

    public static void SendPlayerMessage(Player player, String string){
        player.sendMessage("[" +  ChatColor.GOLD + "Infini" + ChatColor.WHITE + "Bucket] " + string);
    }
}
