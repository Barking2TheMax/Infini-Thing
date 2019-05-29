package me.tehbosscat.infinithing.Menus.BucketMenuBehaviours;

import me.tehbosscat.infinithing.Items.InfiniItemFactory;
import me.tehbosscat.infinithing.Menus.BaseMenu.I_OnClickBehaviour;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class WaterButtonBehaviour implements I_OnClickBehaviour {

    public boolean Clicked(Player player) {
        InfiniItemFactory f = InfiniItemFactory.GetInstance();
        Inventory inventory = player.getInventory();

        for (int i = 0; i < inventory.getSize(); i++) {
            if (inventory.getContents()[i].equals(f.CreateItem("empty"))){
                inventory.setItem(i , f.CreateItem("water"));
                break;
            }
        }
        return true;
    }
}
