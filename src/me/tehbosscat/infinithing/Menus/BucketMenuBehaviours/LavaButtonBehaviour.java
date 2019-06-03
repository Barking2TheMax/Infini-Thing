package me.tehbosscat.infinithing.Menus.BucketMenuBehaviours;

import me.tehbosscat.infinithing.Items.InfiniItemFactory;
import me.tehbosscat.infinithing.Menus.BaseMenu.I_OnClickBehaviour;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class LavaButtonBehaviour implements I_OnClickBehaviour {

    public boolean Clicked(Player player) {
        InfiniItemFactory f = InfiniItemFactory.GetInstance();
        Inventory inventory = player.getInventory();

        ItemStack emptyBucket = f.CreateItem("empty");
        ItemStack lavaBucket = f.CreateItem("lava");

        for (int i = 0; i < inventory.getSize(); i++) {
            ItemStack itemStack = inventory.getItem(i);

            if (itemStack == null) continue;

            if (itemStack.isSimilar(emptyBucket)){
                itemStack.setAmount(itemStack.getAmount() - 1);
                inventory.setItem(i , itemStack);
                inventory.addItem(lavaBucket);
                return true;
            }
        }

        return false;
    }
}
