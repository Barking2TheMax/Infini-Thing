package BarkerDevelopment.InfiniThing.Menus.InfiniMenuBehaviours;

import BarkerDevelopment.InfiniThing.Items.InfiniItem;
import BarkerDevelopment.InfiniThing.Items.InfiniItemFactory;
import BarkerDevelopment.MinecraftMenus.Behaviours.I_OnClickBehaviour;
import org.bukkit.entity.Player;

public class PearlSpawnButtonBehaviour implements I_OnClickBehaviour {

    public boolean Clicked(Player player) {
        InfiniItem.ItemSpawnQuery(player, InfiniItemFactory.GetInstance().GetItem("pearl"));
        player.closeInventory();

        return true;
    }
}
