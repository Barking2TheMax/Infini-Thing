package me.tehbosscat.infinithing.Menus;

import me.tehbosscat.infinithing.Main;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;

public class BucketMenu implements Listener {

    public void newBucketMenu(Player player){
        Inventory i = Main.server.createInventory(null, 9, ChatColor.GOLD + "Infini" + ChatColor.WHITE + "Bucket Menu");
        player.openInventory(i);
    }
}
