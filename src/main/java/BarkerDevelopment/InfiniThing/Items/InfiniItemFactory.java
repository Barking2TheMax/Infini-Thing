package BarkerDevelopment.InfiniThing.Items;

import BarkerDevelopment.InfiniThing.Main;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

/**
 *
 */
public class InfiniItemFactory {
    // Variables.
    private static InfiniItemFactory INSTANCE;

    private InfiniItem empty;
    private InfiniItem lava;
    private InfiniItem milk;
    private ThrownInfiniItem pearl;
    private InfiniItem water;


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
        empty = new InfiniItem(
                ChatColor.WHITE + "Empty " + ChatColor.GOLD + "Infini" + ChatColor.WHITE + "-Bucket",
                Material.BUCKET,
                "infini.bucket",
                "empty"
        );
        empty.AddAdditionalLore(ChatColor.RESET + "Left click to change bucket type.");

        String changeLore = (ChatColor.RESET + "Throw on the floor to return it's potential.");

        lava = new InfiniItem(
                ChatColor.GOLD + "Infini" + ChatColor.WHITE + "-Lava",
                Material.LAVA_BUCKET,
                "infini.bucket",
                "lava"
        );
        lava.AddAdditionalLore(changeLore);

        milk = new InfiniItem(
                ChatColor.GOLD + "Infini" + ChatColor.WHITE + "-Milk",
                Material.MILK_BUCKET,
                "infini.bucket",
                "milk"
        );
        milk.AddAdditionalLore(changeLore);

        water = new InfiniItem(
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
     * Returns an InfiniItem based of the string passed in.
     * Current types are:
     *      - empty
     *      - lava
     *      - milk
     *      - pearl
     *      - water
     *
     * @param type
     * @return An InfiniItem object.
     */
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

    /**
     *
     * @param type
     * @return
     */
    public ItemStack CreateItem(String type){
        InfiniItem item = GetItem(type);

        return (item != null) ? item.CreateItem() : null;
    }

    /**
     *
     * @param type
     * @return
     */
    public ItemStack CreateItem(InfiniItem type){
        return (type != null) ? type.CreateItem() : null;
    }
}
