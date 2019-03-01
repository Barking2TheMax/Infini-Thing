package me.tehbosscat.infinithing.Items;

import me.tehbosscat.infinithing.Main;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class InfiniBucket implements Listener {
    private static final String NAME = ChatColor.WHITE + "Empty "+ ChatColor.GOLD + "Infini" + ChatColor.WHITE + "-Bucket";
    private static final String LORE_STRING = Main.config.getString("bucket.types.empty.lore");

    public void giveItems(Player player){
        player.getInventory().addItem(CreateInstance());
    }

    public ItemStack CreateInstance(){
        return CreateInfiniBucket(Material.BUCKET, NAME, LORE_STRING);

    }

    private static ItemStack CreateInfiniBucket(Material mat, String name, String lore){
        return InfiniBucket.AddMeta(new ItemStack(mat, 1), name, lore);
    }

    private static ItemStack AddMeta(ItemStack item, String name, String loreString){
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(name);
        ArrayList<String> lore = new ArrayList<>();
        lore.add(loreString);
        lore.add("Click the air to select your bucket type.");
        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        item.setItemMeta(meta);

        return item;
    }

    protected static ItemStack CreateInfiniBucket(Material mat, String name, String lore, double price){
        ItemStack item = new ItemStack(mat, 1);
        return InfiniBucket.AddMeta(item, name, lore, price);
    }

    protected static ItemStack AddMeta(ItemStack item, String name, String loreString, double price){
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

