package me.tehbosscat.infinithing.Items.Other;

import me.tehbosscat.infinithing.Items.InfiniItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;

public class InfiniPearl extends InfiniItem {
    private InfiniPearl(){
        super(
            ChatColor.GOLD + "Infini" + ChatColor.WHITE +"-Lava",
            Material.LAVA_BUCKET,
            "bucket.types.lava.lore",
            "bucket.types.lava.use-cost"
        );
    }

    public static InfiniItem GetInstance(){
        if(INSTANCE == null){
            INSTANCE = new InfiniPearl();
        }

        return INSTANCE;
    }
}
