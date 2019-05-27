package me.tehbosscat.infinithing.Items.Buckets;

import me.tehbosscat.infinithing.Items.InfiniItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;

public class InfiniWater extends InfiniItem {
    private InfiniWater(){
        super(
            ChatColor.GOLD + "Infini" + ChatColor.WHITE +"-Lava",
             Material.LAVA_BUCKET,
            "bucket.types.lava.lore",
            "bucket.types.lava.use-cost"
        );
    }

    public static InfiniItem GetInstance(){
        if(INSTANCE == null){
            INSTANCE = new InfiniWater();
        }

        return INSTANCE;
    }
}
