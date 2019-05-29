package me.tehbosscat.infinithing.Menus.BaseMenu;

import org.bukkit.entity.Player;

public class NullClick implements I_OnClickBehaviour {
    public boolean Clicked(Player player){
        return true;
    }
}
