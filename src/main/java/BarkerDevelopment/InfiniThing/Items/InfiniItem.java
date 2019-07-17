package BarkerDevelopment.InfiniThing.Items;

import BarkerDevelopment.InfiniThing.Main;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

/**
 *
 */
public class InfiniItem {
    // Variables.
    private final String NAME;
    private String LORE;
    private double PRICE;
    private ArrayList<String> _additionalLore;

    private final Material MATERIAL;
    private final String PERMISSION_PATH;
    private final String CONFIG_PATH;


    // Instantiation.
    /**
     *
     * @param name
     * @param material
     * @param permissionPath
     * @param configPath
     */
    public InfiniItem(String name, Material material, String permissionPath, String configPath){
        NAME = name;
        MATERIAL = material;
        PERMISSION_PATH = permissionPath;
        CONFIG_PATH = configPath;

        _additionalLore = new ArrayList<>();
        UpdateLoreString();
        UpdatePrice();
    }

    /**
     *
     * @param name
     * @param material
     * @param permissionPath
     * @param configPath
     * @param additionalLore
     */
    public InfiniItem(String name, Material material, String permissionPath, String configPath, ArrayList<String> additionalLore){
        NAME = name;
        MATERIAL = material;
        PERMISSION_PATH = permissionPath;
        CONFIG_PATH = configPath;

        _additionalLore = additionalLore;
        UpdateLoreString();
        UpdatePrice();
    }


    // Getters and Setters.
    /**
     *
     * @return
     */
    public boolean IsEnabled(){
        try{
            return Main.config.getBoolean(CONFIG_PATH + ".options.spawning.spawnable");

        }catch (Exception e){
            Main.SendConsoleMessage(ChatColor.RED + "Error: " + ChatColor.GRAY + CONFIG_PATH + ".options.spawning.spawn-cost in config.yml is wrong type, try float.");
            return false;
        }
    }

    /**
     *
     * @return
     */
    public String GetName(){
        return NAME;
    }

    /**
     *
     * @return
     */
    public Material GetMaterial(){
        return MATERIAL;
    }

    /**
     *
     * @return
     */
    public String GetLoreString(){
        return LORE;
    }

    /**
     *
     * @return
     */
    public double GetPrice(){
        return PRICE;
    }

    /**
     *
     * @return
     */
    public double GetSpawnPrice(){
        boolean enabled;
        try{
            enabled = Main.config.getBoolean(CONFIG_PATH + ".options.spawning.spawn-cost-enabled");

        }catch (Exception e){
            Main.SendConsoleMessage(ChatColor.RED + "Error: " + ChatColor.GRAY + CONFIG_PATH + ".options.spawning.spawn-cost in config.yml is wrong type, try float.");
            enabled = false;
        }

        double spawnCost;
        if (enabled){
            try{
                spawnCost = Main.config.getDouble(CONFIG_PATH + ".options.spawning.spawn-cost");

            }catch (Exception e){
                Main.SendConsoleMessage(ChatColor.RED + "Error: " + ChatColor.GRAY + CONFIG_PATH + ".options.spawning.spawn-cost in config.yml is wrong type, try float.");
                spawnCost  = 0;
            }
        }
        else {
            spawnCost = 0;
        }

        return spawnCost;
    }

    /**
     *
     * @return
     */
    public String GetPermissionPath(){
        return PERMISSION_PATH;
    }

    /**
     *
     * @return
     */
    public String GetConfigPath(){
        return CONFIG_PATH;
    }

    /**
     *
     * @return
     */
    public String GetAdditionalLore(){
        return _additionalLore.get(_additionalLore.size() - 1);
    }

    /**
     *
     * @param index
     * @return
     */
    public String GetAdditionalLore(int index){
        return _additionalLore.get(index);
    }

    /**
     *
     * @param lore
     */
    public void AddAdditionalLore(String lore){
        _additionalLore.add(lore);
    }

    /**
     *
     * @return
     */
    public String RemoveAdditionalLore(){
        int index = _additionalLore.size() - 1;

        return RemoveAdditionalLore(index);
    }

    /**
     *
     * @param index
     * @return
     */
    public String RemoveAdditionalLore(int index){
        String lore = _additionalLore.get(index);
        _additionalLore.remove(index);

        return lore;
    }

    /**
     *
     */
    public void UpdateLoreString(){
        String path = CONFIG_PATH + ".lore";

        LORE = Main.config.getString(path);
    }

    /**
     *
     */
    public void UpdatePrice(){
        String path = CONFIG_PATH + ".use-cost";

        try{
            PRICE =  Main.config.getDouble(path);

        }catch (Exception e){
            Main.SendConsoleMessage(ChatColor.RED + "Error: " + ChatColor.GRAY + path + " in config.yml is wrong type, try float.");
            PRICE = 0;
        }
    }


    // Methods
    /**
     *
     * @param player
     * @param item
     */
    public static void ItemSpawnQuery(Player player, InfiniItem item){
        InfiniItemFactory f = InfiniItemFactory.GetInstance();

        if(player.hasPermission(item.GetPermissionPath() + ".spawn")){
            SpawnInfiniItem(player, f, item);

        }else{
            Main.SendPlayerMessage(player, ChatColor.RED + "You have insufficient permissions to spawn an " + item.GetName());
        }
    }

    /**
     *
     * @param player
     * @param f
     * @param item
     */
    private static void SpawnInfiniItem(Player player, InfiniItemFactory f, InfiniItem item){
        String configPath = item.GetConfigPath();
        double spawnCost;

        if(Main.config.getBoolean(configPath + ".options.spawning.spawnable")){
            if(Main.config.getBoolean(configPath + ".options.spawning.spawn-cost-enabled")){
                spawnCost = item.GetSpawnPrice();

            }else{
                spawnCost  = 0;
            }

            if(spawnCost == 0){
                GiveInfiniItem(player, f, item);

            } else {
                if (Main.economy.has(player, spawnCost)){
                    Main.economy.withdrawPlayer(player, spawnCost);
                    Main.SendPlayerMessage(player,"Your account has been charged " + ChatColor.GREEN + "$" + spawnCost + ChatColor.WHITE + ".");
                    GiveInfiniItem(player, f, item);

                }else {
                    Main.SendPlayerMessage(player,ChatColor.RED + "You have insufficient funds.");
                }
            }

        }else{
            Main.SendPlayerMessage(player,ChatColor.RED + item.GetName() + " has been disabled in the config.");
        }
    }

    /**
     *
     * @param player
     * @param f
     * @param item
     */
    private static void GiveInfiniItem(Player player, InfiniItemFactory f, InfiniItem item){
        Main.SendPlayerMessage(player,"Thank you for using " + item.GetName() + ".");
        player.getInventory().addItem(f.CreateItem(item));
    }

    /**
     *
     * @return
     */
    public ItemStack CreateItem(){
        ItemStack item = new ItemStack(MATERIAL, 1);
        return AddMeta(item);
    }

    /**
     *
     * @param item
     * @return
     */
    private ItemStack AddMeta(ItemStack item){
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(NAME);
        ArrayList<String> lore = new ArrayList<>();
        lore.add(LORE);

        if (PRICE > 0){
            lore.add("Costs " + ChatColor.GREEN + "$" + PRICE + ChatColor.DARK_PURPLE + ChatColor.ITALIC + " per use.");
        }

        lore.addAll(_additionalLore);

        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        item.setItemMeta(meta);

        return item;
    }
}
