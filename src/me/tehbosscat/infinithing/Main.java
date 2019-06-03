package me.tehbosscat.infinithing;

import me.tehbosscat.infinithing.Events.BucketEvents;
import me.tehbosscat.infinithing.Events.PearlEvents;
import me.tehbosscat.infinithing.Items.InfiniItem;
import me.tehbosscat.infinithing.Items.InfiniItemFactory;
import me.tehbosscat.infinithing.Menus.BaseMenu.I_OnClickBehaviour;
import me.tehbosscat.infinithing.Menus.BaseMenu.Menu;
import me.tehbosscat.infinithing.Menus.BaseMenu.MenuItem;
import me.tehbosscat.infinithing.Menus.BucketMenuBehaviours.LavaButtonBehaviour;
import me.tehbosscat.infinithing.Menus.BucketMenuBehaviours.WaterButtonBehaviour;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;

public class Main extends JavaPlugin {
    public static Server server;
    public static Plugin plugin;
    public static ConsoleCommandSender console;
    public static PluginManager pluginManager;
    public static FileConfiguration config;
    public static Economy economy;
    private Commands commands;

    public static Menu bucketMenu;

    public static HashMap<UUID, Long> cooldown = new HashMap<>();

    public void onEnable(){
        plugin = this;
        server = getServer();
        if(SetupEconomy()){
            try {
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
            }
            catch (Exception e){
                SendConsoleMessage(ChatColor.RED + "Error on start-up." + ChatColor.GRAY + " Please submit a bug report on the GitHub page.");
                e.printStackTrace();
                SendConsoleMessage(ChatColor.RED + "Offline.");
            }
        }else{
            SendConsoleMessage(ChatColor.RED + "Economy plugin not found.");
            SendConsoleMessage(ChatColor.RED + "Offline.");
        }
    }

    private boolean SetupEconomy() {
        RegisteredServiceProvider<Economy> economyProvider = server.getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null) {
            economy = economyProvider.getProvider();
            SendConsoleMessage(ChatColor.GREEN + "Economy plugin found.");
        }

        return (economy != null);
    }

    public static void SendConsoleMessage(String string){
       server.getConsoleSender().sendMessage("[" +  ChatColor.GOLD + "Infini" + ChatColor.GRAY + "Thing] " + string);
    }

    public static void SendPlayerMessage(Player player, String string){
        player.sendMessage("[" +  ChatColor.GOLD + "Infini" + ChatColor.WHITE + "Thing] " + string);
    }
}
