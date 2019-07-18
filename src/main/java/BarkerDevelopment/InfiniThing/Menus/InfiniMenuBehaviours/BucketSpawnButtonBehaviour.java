package BarkerDevelopment.InfiniThing.Menus.InfiniMenuBehaviours;

import BarkerDevelopment.InfiniThing.Items.InfiniItemFactory;
import BarkerDevelopment.MinecraftMenus.Behaviours.I_OnClickBehaviour;
import org.bukkit.entity.Player;

/**
 * Behaviour for InfiniMenu to spawn an Infini-Bucket.
 */
public class BucketSpawnButtonBehaviour implements I_OnClickBehaviour {

    /**
     * Called when ItemStack is clicked in the Menu Inventory.
     *
     * @param player Target Player that triggered the action.
     * @return TRUE if the action closes the inventory afterwards. FALSE otherwise.
     */
    public boolean Clicked(Player player) {
        InfiniItemFactory.GetInstance().GetItem("empty").Give(player);
        player.closeInventory();

        return true;
    }
}
