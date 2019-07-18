package BarkerDevelopment.InfiniThing.Menus.BucketMenuBehaviours;

import BarkerDevelopment.InfiniThing.Items.InfiniItemFactory;
import BarkerDevelopment.MinecraftMenus.Behaviours.I_OnClickBehaviour;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * Abstract behaviour for BucketMenu to spawn an Infini-Bucket.
 */
public abstract class BucketButtonBehaviour implements I_OnClickBehaviour {
    protected InfiniItemFactory f = InfiniItemFactory.GetInstance();
    protected ItemStack bucket;

    /**
     * Called when ItemStack is clicked in the Menu Inventory.
     *
     * @param player Target Player that triggered the action.
     * @return TRUE if the action closes the inventory afterwards. FALSE otherwise.
     */
    public boolean Clicked(Player player) {
        Inventory inventory = player.getInventory();

        for (int i = 0; i < inventory.getSize(); i++) {
            ItemStack itemStack = inventory.getItem(i);

            if (itemStack == null) continue;

            if (itemStack.isSimilar(f.CreateItem("empty"))) {
                itemStack.setAmount(itemStack.getAmount() - 1);
                inventory.setItem(i, itemStack);
                inventory.addItem(bucket);
                return true;
            }
        }

        return true;
    }
}
