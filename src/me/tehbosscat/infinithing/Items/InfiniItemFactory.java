package me.tehbosscat.infinithing.Items;

import me.tehbosscat.infinithing.Main;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

public class InfiniItemFactory {
    private static InfiniItemFactory INSTANCE;

    private InfiniItem empty;
    private InfiniItem lava;
    private InfiniItem milk;
    private ThrownInfiniItem pearl;
    private InfiniItem water;


    private InfiniItemFactory(){
        empty = new InfiniItem(
                ChatColor.GOLD + "Infini" + ChatColor.WHITE +"-Bucket",
                Material.BUCKET,
                "infini.bucket",
                "empty"
        );

        lava = new InfiniItem(
                ChatColor.GOLD + "Infini" + ChatColor.WHITE +"-Lava",
                Material.LAVA_BUCKET,
                "infini.bucket",
                "lava"
        );

        milk = new InfiniItem(
                ChatColor.GOLD + "Infini" + ChatColor.WHITE +"-Milk",
                Material.MILK_BUCKET,
                "infini.bucket",
                "milk"
        );

        pearl = new ThrownInfiniItem(
                ChatColor.GOLD + "Infini" + ChatColor.WHITE +"-Pearl",
                Material.ENDER_PEARL,
                EntityType.ENDER_PEARL,
                "infini.pearl",
                "pearl"
        );

        water = new InfiniItem(
                ChatColor.GOLD + "Infini" + ChatColor.WHITE +"-Water",
                Material.WATER_BUCKET,
                "infini.bucket",
                "water"
        );
    }

    public static InfiniItemFactory GetInstance(){
        if(INSTANCE == null){
            INSTANCE = new InfiniItemFactory();
        }

        return INSTANCE;
    }

    public InfiniItem GetItem(String type){
        switch (type){
            case "empty":
                return empty;

            case "lava":
                return lava;

            case "milk":
                return milk;

            case "pearl":
                return pearl;

            case "water":
                return water;

            default:
                Main.SendConsoleMessage(ChatColor.RED + "GetItem type not recognised.");
                return null;
        }
    }

    public ItemStack CreateItem(String type){
        InfiniItem item = GetItem(type);

        return (item != null) ? item.CreateItem() : null;
    }

    public ItemStack CreateItem(InfiniItem type){
        return (type != null) ? type.CreateItem() : null;
    }
}
