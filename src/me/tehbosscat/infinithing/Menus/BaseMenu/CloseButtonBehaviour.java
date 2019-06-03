package me.tehbosscat.infinithing.Menus.BaseMenu;

import org.bukkit.entity.Player;

public class CloseButtonBehaviour implements I_OnClickBehaviour {

    public boolean Clicked(Player player) {
        player.closeInventory();

        return true;
    }
}
