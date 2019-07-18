package BarkerDevelopment.InfiniThing.Items;

import BarkerDevelopment.InfiniThing.Main;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

/**
 * InfiniItemType class holds information about an InfiniItem.
 */
public class InfiniItemType {
    // Variables.
    private String NAME;
    private String LORE;
    private ArrayList<String> _additionalLore;

    private Material MATERIAL;
    private String PERMISSION_PATH;
    private String CONFIG_PATH;


    // Instantiation.
    /**
     * Default constructor.
     *
     * @param name Name of Infini-Item.
     * @param material Material of Infini-Item.
     * @param permissionPath Path to permissions for the Infini-Item.
     * @param configPath Path of the Infini-Item in the config file.
     */
    public InfiniItemType(String name, Material material, String permissionPath, String configPath){
        Initialise(name, material, permissionPath, configPath, new ArrayList<>());
    }

    /**
     * Alternative constructor. Accepts extra lore for the Infini-Item ItemStack.
     *
     * @param name Name of Infini-Item.
     * @param material Material of Infini-Item.
     * @param permissionPath Path to permissions for the Infini-Item.
     * @param configPath Path of the Infini-Item in the config file.
     * @param additionalLore An arrayList of lore strings.
     */
    public InfiniItemType(String name, Material material, String permissionPath, String configPath,
                          ArrayList<String> additionalLore){
        Initialise(name, material, permissionPath, configPath, additionalLore);
    }

    /**
     * Master constructor. This is to reduce redundancy in the constructors.
     *
     * @param name Name of Infini-Item.
     * @param material Material of Infini-Item.
     * @param permissionPath Path to permissions for the Infini-Item.
     * @param configPath Path of the Infini-Item in the config file.
     * @param additionalLore An arrayList of lore strings.
     */
    private void Initialise(String name, Material material, String permissionPath, String configPath,
                            ArrayList<String> additionalLore){
        NAME = name;
        MATERIAL = material;
        PERMISSION_PATH = permissionPath;
        CONFIG_PATH = configPath;
        _additionalLore = additionalLore;
        LORE = Main.config.getString(CONFIG_PATH + ".lore");
    }

    // Getters and Setters.
    /**
     * Checks if the Infini-Item is enabled in the config.
     *
     * @return TRUE Infini-Item is enabled. FALSE Infini-Item is disabled.
     */
    public boolean IsEnabled(){
        try{
            return Main.config.getBoolean(CONFIG_PATH + ".options.enabled");

        }catch (Exception e){
            Main.SendConsoleMessage(ChatColor.RED + "Error: " + ChatColor.GRAY + CONFIG_PATH +
                    ".options.enabled in config.yml is wrong type, try float.");
            return false;
        }
    }

    /**
     * Getter for the name of the Infini-Item.
     *
     * @return Name of the Infini-Item.
     */
    public String GetName(){
        return NAME;
    }

    /**
     * Getter for the material of the Infini-Item.
     *
     * @return Material of the Infini-Item.
     */
    public Material GetMaterial(){
        return MATERIAL;
    }

    /**
     * Getter for the lore string of the Infini-Item.
     *
     * @return Lore of the Infini-Item.
     */
    public String GetLoreString(){
        return LORE;
    }

    /**
     * Getter for the price of the Infini-Item.
     *
     * @return Use cost of the Infini-Item.
     */
    public double GetPrice(){
        return GetDouble(CONFIG_PATH + ".use-cost");
    }

    /**
     * Getter for the spawn price of the Infini-Item.
     *
     * @return Give price of the Infini-Item.
     */
    public double GetSpawnPrice(){
        return GetDouble(CONFIG_PATH + ".options.spawning.spawn-cost");
    }

    /**
     * Getter for the permission path of the Infini-Item.
     *
     * @return Permission path of the Infini-Item.
     */
    public String GetPermissionPath(){
        return PERMISSION_PATH;
    }

    /**
     * Getter for the config path of the Infini-Item.
     *
     * @return Config path of the Infini-Item.
     */
    public String GetConfigPath(){
        return CONFIG_PATH;
    }

    /**
     * Getter for the tail additional lore string.
     *
     * @return An additional lore string.
     */
    public String GetAdditionalLore(){
        return _additionalLore.get(_additionalLore.size() - 1);
    }

    /**
     * Getter for the additional lore string at the index.
     *
     * @param index Index of lore string.
     * @return The tail additional lore string.
     * @throws IndexOutOfBoundsException If index out of bounds of additional lore array.
     */
    public String GetAdditionalLore(int index) throws IndexOutOfBoundsException{
        return _additionalLore.get(index);
    }

    /**
     * Add an additional lore string to the end of the array.
     *
     * @param lore An additional lore string.
     */
    public void AddAdditionalLore(String lore){
        _additionalLore.add(lore);
    }

    /**
     * Pop the tail additional lore string.
     *
     * @return The tail additional lore string.
     */
    public String RemoveAdditionalLore(){
        int index = _additionalLore.size() - 1;

        return RemoveAdditionalLore(index);
    }

    /**
     * Remove the indexed additional lore string.
     *
     * @param index Index of lore string.
     * @return An additional lore string.
     * @throws IndexOutOfBoundsException If index out of bounds of additional lore array.
     */
    public String RemoveAdditionalLore(int index){
        String lore = _additionalLore.get(index);
        _additionalLore.remove(index);

        return lore;
    }

    /**
     * Gets a double from the config.
     *
     * @param path Path in the config file.
     */
    private double GetDouble(String path){
        try{
            return Main.config.getDouble(path);

        }catch (Exception e){
            Main.SendConsoleMessage(ChatColor.RED + "Error: " + ChatColor.GRAY + path + " in config.yml is " +
                    "wrong type, try float.");
            return 0;
        }
    }


    // Methods
    /**
     * Initialises the spawn of the Infini-Item ItemStack. Ensures the player has permission to spawn an Infini-Bucket.
     *
     * @param player The player to give an Infini-Item to.
     */
    public void Give(Player player){
        InfiniItemFactory f = InfiniItemFactory.GetInstance();

        if(player.hasPermission(PERMISSION_PATH + ".spawn")){
            SpawnInfiniItem(player, f);

        }else{
            Main.SendPlayerMessage(player, ChatColor.RED + "You have insufficient permissions to spawn an "
                    + NAME);
        }
    }

    /**
     * Gives the item to the player. Ensures the item is spawnable.
     *
     * @param player The player to give an Infini-Item to.
     * @param f InfiniItemFactor.
     */
    private void SpawnInfiniItem(Player player, InfiniItemFactory f){
        double spawnCost;

        if(Main.config.getBoolean(CONFIG_PATH + ".options.spawning.spawnable")){
            spawnCost = GetSpawnPrice();

            if(Main.ChargePlayer(player, spawnCost)){
                Main.SendPlayerMessage(player,"Thank you for using " + NAME + ".");
                player.getInventory().addItem(CreateItem());
            }

        }else{
            Main.SendPlayerMessage(player,ChatColor.RED + NAME + " has been disabled.");
        }
    }

    /**
     * Create an ItemStack version of the Infini-Item.
     *
     * @return ItemStack of the Infini-Item.
     */
    public ItemStack CreateItem(){
        return AddMeta(new ItemStack(MATERIAL, 1));
    }

    /**
     * Adds meta data to the Infini-Item ItemStack.
     *
     * @param item Infini-Item ItemStack.
     * @return Infini-Item ItemStack with added meta.
     */
    private ItemStack AddMeta(ItemStack item){
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(NAME);
        ArrayList<String> lore = new ArrayList<>();
        lore.add(LORE);

        double price = GetPrice();

        if (price > 0){
            lore.add("Costs " + ChatColor.GREEN + "$" + price + ChatColor.DARK_PURPLE + ChatColor.ITALIC + " per use.");
        }

        lore.addAll(_additionalLore);

        meta.addEnchant(Enchantment.DURABILITY, 1, true);

        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(meta);

        return item;
    }
}
