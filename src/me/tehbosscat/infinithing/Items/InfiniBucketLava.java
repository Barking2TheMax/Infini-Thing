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
        player.getInventory().addItem(CreateInstance());
    }

    @Override
    public ItemStack CreateInstance(){
        String description = Main.config.getString("bucket.types.lava.lore");
        double price = Main.config.getDouble("bucket.types.lava.use-cost");
        return CreateInfiniBucket(Material.LAVA_BUCKET, NAME, description, price);
    }
}
