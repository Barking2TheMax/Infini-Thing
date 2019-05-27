package me.tehbosscat.infinithing.Items;

import me.tehbosscat.infinithing.Items.Buckets.InfiniBucket;
import me.tehbosscat.infinithing.Items.Buckets.InfiniLava;
import me.tehbosscat.infinithing.Items.Buckets.InfiniMilk;
import me.tehbosscat.infinithing.Items.Buckets.InfiniWater;
import me.tehbosscat.infinithing.Items.Other.InfiniPearl;

import org.bukkit.inventory.ItemStack;

public class InfiniItemFactory {
    private static InfiniItemFactory INSTANCE;

    private InfiniItem EMPTY;
    private InfiniItem LAVA;
    private InfiniItem MILK;
    private InfiniItem WATER;

    private InfiniItem PEARL;


    private InfiniItemFactory(){
        // InfiniBucket items.
        EMPTY = InfiniBucket.GetInstance();
        LAVA = InfiniLava.GetInstance();
        MILK = InfiniMilk.GetInstance();
        WATER = InfiniWater.GetInstance();

        // Other items.
        PEARL = InfiniPearl.GetInstance();
    }

    public static InfiniItemFactory GetInstance(){
        if(INSTANCE == null){
            INSTANCE = new InfiniItemFactory();
        }

        return INSTANCE;
    }

    public ItemStack CreateItem(String type){
        type = type.toLowerCase();

        switch (type){
            case "empty":
                return EMPTY.CreateItem();

            case "lava":
                return LAVA.CreateItem();

            case "milk":
                return MILK.CreateItem();

            case "pearl":
                return PEARL.CreateItem();

            case "water":
                return WATER.CreateItem();

            default:
                return null;
        }
    }

    public ItemStack CreateItem(InfiniItem type){
        return type.CreateItem();
    }


}
