package me.tehbosscat.infinithing.Items;

import me.tehbosscat.infinithing.Main;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class InfiniItem {
    protected final String NAME;
    protected String LORE;
    protected double PRICE;

    protected final Material MATERIAL;
    protected final String PERMISSION_PATH;
    protected final String CONFIG_PATH;

    public InfiniItem(String name, Material material, String permissionPath, String configPath){
        NAME = name;
        MATERIAL = material;
        PERMISSION_PATH = permissionPath;
        CONFIG_PATH = configPath;

        UpdateLoreString();
        UpdatePrice();
    }


    public String GetName(){
        return NAME;
    }

    public Material GetMaterial(){
        return MATERIAL;
    }

    public String GetLoreString(){
        return LORE;
    }

    public double GetPrice(){
        return PRICE;
    }

    public String GetPermissionPath(){
        return PERMISSION_PATH;
    }

    public String GetConfigPath(){
        return CONFIG_PATH;
    }


    public void UpdateLoreString(){
        String path = CONFIG_PATH + ".lore";

        LORE = Main.config.getString(path);
    }

    public void UpdatePrice(){
        String path = CONFIG_PATH + ".use-cost";

        try{
            PRICE =  Main.config.getDouble(path);

        }catch (Exception e){
            Main.SendConsoleMessage(ChatColor.RED + "Error: " + ChatColor.GRAY + path + " in config.yml is wrong type, try float.");
            PRICE = 0;
        }
    }


    public ItemStack CreateItem(){
        ItemStack item = new ItemStack(MATERIAL, 1);
        return AddMeta(item);
    }

    private ItemStack AddMeta(ItemStack item){
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(NAME);
        ArrayList<String> lore = new ArrayList<>();
        lore.add(LORE);

        if (PRICE > 0){
            lore.add("Costs " + ChatColor.ITALIC + ChatColor.GREEN + "$" + PRICE + ChatColor.DARK_PURPLE + " per use.");
        }

        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        item.setItemMeta(meta);

        return item;
    }
}
