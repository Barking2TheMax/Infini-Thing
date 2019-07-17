package BarkerDevelopment.InfiniThing.Menus.BucketMenuBehaviours;

import BarkerDevelopment.InfiniThing.Items.InfiniItemFactory;
import BarkerDevelopment.MinecraftMenus.Behaviours.I_OnClickBehaviour;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class BucketButtonBehaviour implements I_OnClickBehaviour {
    protected InfiniItemFactory f = InfiniItemFactory.GetInstance();
    protected ItemStack bucket = f.CreateItem("lava");

    public boolean Clicked(Player player) {
        Inventory inventory = player.getInventory();

        ItemStack emptyBucket = f.CreateItem("empty");

        for (int i = 0; i < inventory.getSize(); i++) {
            ItemStack itemStack = inventory.getItem(i);

            if (itemStack == null) continue;

            if (itemStack.isSimilar(emptyBucket)) {
                itemStack.setAmount(itemStack.getAmount() - 1);
                inventory.setItem(i, itemStack);
                inventory.addItem(bucket);
                return true;
            }
        }

        return true;
    }
}
