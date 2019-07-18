package BarkerDevelopment.InfiniThing.Menus.BucketMenuBehaviours;

import BarkerDevelopment.InfiniThing.Items.InfiniItemFactory;
import BarkerDevelopment.MinecraftMenus.Behaviours.I_OnClickBehaviour;
import org.bukkit.entity.Player;

/**
 * Behaviour for BucketMenu to spawn an Infini-Water.
 */
public class WaterButtonBehaviour extends BucketButtonBehaviour implements I_OnClickBehaviour {

    /**
     * Called when ItemStack is clicked in the Menu Inventory.
     *
     * @param player Target Player that triggered the action.
     * @return TRUE if the action closes the inventory afterwards. FALSE otherwise.
     */
    public boolean Clicked(Player player) {
        super.f = InfiniItemFactory.GetInstance();
        super.bucket = f.CreateItem("water");
        return super.Clicked(player);
    }
}
