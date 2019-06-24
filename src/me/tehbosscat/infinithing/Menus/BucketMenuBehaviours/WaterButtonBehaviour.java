package me.tehbosscat.infinithing.Menus.BucketMenuBehaviours;

import me.tehbosscat.infinithing.Items.InfiniItemFactory;
import me.tehbosscat.infinithing.Menus.BaseMenu.I_OnClickBehaviour;
import org.bukkit.entity.Player;

public class WaterButtonBehaviour extends BucketButtonBehaviour implements I_OnClickBehaviour {

    public boolean Clicked(Player player) {
        super.f = InfiniItemFactory.GetInstance();
        super.bucket = f.CreateItem("water");
        return super.Clicked(player);
    }
}
