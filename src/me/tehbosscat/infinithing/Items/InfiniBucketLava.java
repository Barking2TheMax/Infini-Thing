package me.tehbosscat.infinithing.Items;

import me.tehbosscat.infinithing.Main;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class InfiniBucketLava extends InfiniBucket{
    private final String NAME = ChatColor.GOLD + "Infini" + ChatColor.WHITE +"-Lava";

    @Override
    public void giveItems(Player player){
        player.getInventory().addItem(createInstance());
    }

    @Override
    public ItemStack createInstance(){
        String description = Main.config.getString("bucket.lava.lore");
        double price = Main.config.getDouble("bucket.lava.use-cost");
        return createInfiniBucket(Material.LAVA_BUCKET, NAME, description, price);
    }
}
