package me.tehbosscat.infinithing.Items;

import me.tehbosscat.infinithing.Main;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class InfiniBucketWater extends InfiniBucket{
    private static final String NAME = ChatColor.GOLD + "Infini" + ChatColor.WHITE +"-Water";

    @Override
    public void giveItems(Player player){
        player.getInventory().addItem(CreateInstance());
    }

    @Override
    public ItemStack CreateInstance(){
        String description = Main.config.getString("bucket.types..water.lore");
        double price = Main.config.getDouble("bucket.types.water.use-cost");
        return CreateInfiniBucket(Material.WATER_BUCKET, NAME, description, price);
    }
}
