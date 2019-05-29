package me.tehbosscat.infinithing;

import me.tehbosscat.infinithing.Events.BucketEvents;
import me.tehbosscat.infinithing.Events.PearlEvents;
import me.tehbosscat.infinithing.Items.InfiniItem;
import me.tehbosscat.infinithing.Items.InfiniItemFactory;
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
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;

public class Main extends JavaPlugin {
    public static Server server;
    public static ConsoleCommandSender console;
    public static PluginManager pluginManager;
    public static FileConfiguration config;
    public static Economy economy;
    private Commands commands = new Commands();

    public Menu bucketMenu;

    public static HashMap<UUID, Long> cooldown = new HashMap<>();

    public void onEnable(){
        server = getServer();
        if(SetupEconomy()){
            try {
                console = server.getConsoleSender();
                pluginManager = server.getPluginManager();
                config = getConfig();

                config.options().copyDefaults(true);
                saveConfig();

                // Initialise menus.
                bucketMenu = InitialiseBucketMenu();

                // Register events.
                pluginManager.registerEvents(PearlEvents.GetInstance(), this);
                pluginManager.registerEvents(BucketEvents.GetInstance(), this);
                pluginManager.registerEvents(bucketMenu, this);

                // Register commands.
                getCommand(commands.baseCmd).setExecutor(commands);

                SendConsoleMessage(ChatColor.GREEN + "Online!");
            }
            catch (Exception e){
                SendConsoleMessage(ChatColor.RED + "Error on start-up." + ChatColor.GRAY + " Please submit a bug report on the GitHub page.");
                SendConsoleMessage(e.toString());
                SendConsoleMessage(ChatColor.RED + "Offline.");
            }
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

    private Menu InitialiseBucketMenu(){
        InfiniItemFactory f = InfiniItemFactory.GetInstance();

        Menu menu = new Menu(
                "Select an " + ChatColor.GOLD + "Infini" + ChatColor.WHITE + "Bucket",
                1,
                Material.STAINED_GLASS_PANE
        );

        InfiniItem lava = f.GetItem("lava");
        MenuItem lavaButton = new MenuItem(
                "Select " + lava.GetName(),
                lava.GetMaterial(),
                1
        );
        lavaButton.AddOnClick(new LavaButtonBehaviour());
        menu.UpdateMenuItem(3, lavaButton);

        InfiniItem water = f.GetItem("lava");
        MenuItem waterButton = new MenuItem(
                "Select " + water.GetName(),
                water.GetMaterial(),
                1
        );
        waterButton.AddOnClick(new WaterButtonBehaviour());
        menu.UpdateMenuItem(5, waterButton);

        return menu;
    }

    public static void SendConsoleMessage(String string){
       server.getConsoleSender().sendMessage("[" +  ChatColor.GOLD + "Infini" + ChatColor.GRAY + "Thing] " + string);
    }

    public static void SendPlayerMessage(Player player, String string){
        player.sendMessage("[" +  ChatColor.GOLD + "Infini" + ChatColor.WHITE + "Bucket] " + string);
    }
}
