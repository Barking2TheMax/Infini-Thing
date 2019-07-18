package BarkerDevelopment.InfiniThing.Items;

import BarkerDevelopment.InfiniThing.Main;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

/**
 * InfiniItemFactory implements the Factory design pattern.
 */
public class InfiniItemFactory {
    // Variables.
    private static InfiniItemFactory INSTANCE;

    private InfiniItemType empty;
    private InfiniItemType lava;
    private InfiniItemType milk;
    private ThrownInfiniItem pearl;
    private InfiniItemType water;


    // Instantiation.
    /**
     * InfiniItemFactory implements the Singleton design pattern.
     *
     * @return The Singleton instance of InfiniItemFactory.
     */
    public static InfiniItemFactory GetInstance(){
        if(INSTANCE == null){
            INSTANCE = new InfiniItemFactory();
        }

        return INSTANCE;
    }

    /**
     * Constructor implementing Singleton design pattern.
     */
    private InfiniItemFactory(){
        empty = new InfiniItemType(
                ChatColor.WHITE + "Empty " + ChatColor.GOLD + "Infini" + ChatColor.WHITE + "-Bucket",
                Material.BUCKET,
                "infini.bucket",
                "empty"
        );
        empty.AddAdditionalLore(ChatColor.RESET + "" + ChatColor.GRAY + "Left click to change bucket type.");

        String changeLore = (ChatColor.RESET + "" + ChatColor.GRAY + "Throw on the floor to restore it's potential.");

        lava = new InfiniItemType(
                ChatColor.GOLD + "Infini" + ChatColor.WHITE + "-Lava",
                Material.LAVA_BUCKET,
                "infini.bucket",
                "lava"
        );
        lava.AddAdditionalLore(changeLore);

        milk = new InfiniItemType(
                ChatColor.GOLD + "Infini" + ChatColor.WHITE + "-Milk",
                Material.MILK_BUCKET,
                "infini.bucket",
                "milk"
        );
        milk.AddAdditionalLore(changeLore);

        water = new InfiniItemType(
                ChatColor.GOLD + "Infini" + ChatColor.WHITE + "-Water",
                Material.WATER_BUCKET,
                "infini.bucket",
                "water"
        );
        water.AddAdditionalLore(changeLore);

        pearl = new ThrownInfiniItem(
                ChatColor.GOLD + "Infini" + ChatColor.WHITE + "-Pearl",
                Material.ENDER_PEARL,
                EntityType.ENDER_PEARL,
                "infini.pearl",
                "pearl"
        );
    }


    // Methods.
    /**
     * Returns an InfiniItemType based of the string passed in.
     * Current types are:
     *      - empty
     *      - lava
     *      - milk
     *      - pearl
     *      - water
     *
     * @param type
     * @return An InfiniItemType object.
     */
    public InfiniItemType GetItem(String type){
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

    /**
     * Creates an ItemStack version of an InfiniItemType.
     * Current types are:
     *      - empty
     *      - lava
     *      - milk
     *      - pearl
     *      - water
     *
     * @param type String type.
     * @return ItemStack of InfiniItemType.
     */
    public ItemStack CreateItem(String type){
        InfiniItemType item = GetItem(type);

        return (item != null) ? item.CreateItem() : null;
    }

    /**
     * Creates an ItemStack version of an InfiniItemType.
     *
     * @param type InfiniItemType type.
     * @return ItemStack of InfiniItemType.
     */
    public ItemStack CreateItem(InfiniItemType type){
        return (type != null) ? type.CreateItem() : null;
    }
}
