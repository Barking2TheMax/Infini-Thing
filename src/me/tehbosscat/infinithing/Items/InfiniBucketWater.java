package me.tehbosscat.infinithing.Items;

import me.tehbosscat.infinithing.Main;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class InfiniBucketWater extends InfiniBucket{
    public static final String NAME = ChatColor.GOLD + "Infini" + ChatColor.WHITE +"-Water";

    public static String getLoreString(){
        return Main.config.getString("bucket.types.water.lore");
    }

    public static double getPrice(){
        return Main.config.getDouble("bucket.types.water.use-cost");
    }

    @Override
    public void giveItems(Player player){
        player.getInventory().addItem(CreateInstance());
    }

    @Override
    public ItemStack CreateInstance(){
        String description = getLoreString();
        double price = getPrice();
        return CreateInfiniBucket(Material.WATER_BUCKET, NAME, description, price);
    }
}
