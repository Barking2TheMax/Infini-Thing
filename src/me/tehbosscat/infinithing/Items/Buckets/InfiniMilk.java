package me.tehbosscat.infinithing.Items.Buckets;

import me.tehbosscat.infinithing.Items.InfiniItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;

public class InfiniMilk extends InfiniItem {
    private InfiniMilk(){
        super(
            ChatColor.GOLD + "Infini" + ChatColor.WHITE +"-Milk",
             Material.MILK_BUCKET,
            "bucket.types.milk.lore",
            "bucket.types.milk.use-cost"
        );
    }

    public static InfiniItem GetInstance(){
        if(INSTANCE == null){
            INSTANCE = new InfiniMilk();
        }

        return INSTANCE;
    }
}
