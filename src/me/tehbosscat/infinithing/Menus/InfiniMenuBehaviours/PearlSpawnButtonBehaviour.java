package me.tehbosscat.infinithing.Menus.InfiniMenuBehaviours;

import me.tehbosscat.infinithing.Items.InfiniItem;
import me.tehbosscat.infinithing.Items.InfiniItemFactory;
import me.tehbosscat.infinithing.Menus.BaseMenu.I_OnClickBehaviour;
import org.bukkit.entity.Player;

public class PearlSpawnButtonBehaviour implements I_OnClickBehaviour {

    public boolean Clicked(Player player) {
        InfiniItem.ItemSpawnQuery(player, InfiniItemFactory.GetInstance().GetItem("pearl"));
        player.closeInventory();

        return true;
    }
}
