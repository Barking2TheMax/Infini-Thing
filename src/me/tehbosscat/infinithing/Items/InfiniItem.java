package me.tehbosscat.infinithing.Items;

import me.tehbosscat.infinithing.Main;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public abstract class InfiniItem {
    protected static InfiniItem INSTANCE;

    private String NAME;
    private String LORE;
    private double PRICE;

    private Material MATERIAL;
    private String LORE_PATH;
    private String PRICE_PATH;

    protected InfiniItem(String name, Material material, String lorePath, String pricePath){
        NAME = name;
        MATERIAL = material;
        LORE_PATH = lorePath;
        PRICE_PATH = pricePath;
    }


    public String GetName(){
        return NAME;
    }

    public String GetLoreString(){
        return LORE;
    }

    public double GetPrice(){
        return PRICE;
    }


    public void UpdateLoreString(){
        LORE = Main.config.getString(LORE_PATH);
    }

    public void UpdatePrice(){
        try{
            PRICE =  Main.config.getDouble(PRICE_PATH);
        }catch (Exception e){
            Main.SendConsoleMessage(ChatColor.RED + "Error: " + ChatColor.GRAY + PRICE_PATH + " in config.yml is wrong type, try float.");
            PRICE = 0;
        }
    }


    protected ItemStack CreateItem(){
        ItemStack item = new ItemStack(MATERIAL, 1);
        return AddMeta(item, NAME, LORE, PRICE);
    }

    private ItemStack AddMeta(ItemStack item, String name, String loreString, double price){
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(name);
        ArrayList<String> lore = new ArrayList<>();
        lore.add(loreString);

        if (price > 0){
            lore.add("Costs " + ChatColor.ITALIC + ChatColor.GREEN + "$" + price + ChatColor.DARK_PURPLE + " per use.");
        }

        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        item.setItemMeta(meta);

        return item;
    }

}
