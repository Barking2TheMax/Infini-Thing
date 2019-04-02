package me.tehbosscat.infinithing.Items;

import me.tehbosscat.infinithing.Main;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class InfiniPearl {
    public static final String NAME = ChatColor.GOLD + "Infini" + ChatColor.WHITE + "-Pearl";
    public static final String LORE_STRING = getLoreString();
    public static final double PRICE = getPrice();

    public static String getLoreString(){
        return Main.config.getString("pearl.lore");
    }

    public static double getPrice(){
        try{
            return Main.config.getDouble("pearl.use-cost");
        }catch (Exception e){
            Main.SendConsoleMessage(ChatColor.RED + "Error: " + ChatColor.GRAY+ "pearl.use-cost in config.yml is wrong type, try float.");
            return 0;
        }
    }

    public void giveItems(Player player){
        player.getInventory().addItem(CreateInstance());
    }

    public ItemStack CreateInstance(){
        ItemStack item = new ItemStack(Material.ENDER_PEARL, 1);
        return InfiniBucket.AddMeta(item, NAME, LORE_STRING, PRICE);
    }
}
