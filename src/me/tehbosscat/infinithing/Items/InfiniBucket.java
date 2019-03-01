package me.tehbosscat.infinithing.Items;

import me.tehbosscat.infinithing.Main;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;

public class InfiniBucket implements Listener {
    private static final String NAME = ChatColor.WHITE + "Empty "+ ChatColor.GOLD + "Infini" + ChatColor.WHITE + "-Bucket";
    private static final String LORE_STRING = Main.config.getString("bucket.lore");

    public void giveItems(Player player){
        player.getInventory().addItem(createInstance());
    }

    public ItemStack createInstance(){
        return createInfiniBucket(Material.BUCKET, NAME, LORE_STRING, 0);
    }

    protected static ItemStack createInfiniBucket(Material mat, String name, String lore, double price){
        ItemStack item = new ItemStack(mat, 1);

        return InfiniBucket.addMeta(item, name, lore, price);
    }

    protected static ItemStack addMeta(ItemStack item, String name, String loreString, double price){
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(name);
        ArrayList<String> lore = new ArrayList<>();
        lore.add(loreString);
        if (price > 0){
            lore.add("Costs "+ ChatColor.GREEN + "$" + price + ChatColor.DARK_PURPLE  + ChatColor.ITALIC + " per use.");
        }
        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        item.setItemMeta(meta);

        return item;
    }
}

