package BarkerDevelopment.InfiniThing.Menus.BucketMenuBehaviours;

import BarkerDevelopment.InfiniThing.Items.InfiniItemFactory;
import BarkerDevelopment.MinecraftMenus.Behaviours.I_OnClickBehaviour;
import org.bukkit.entity.Player;

public class LavaButtonBehaviour extends BucketButtonBehaviour implements I_OnClickBehaviour {

    public boolean Clicked(Player player) {
        super.f = InfiniItemFactory.GetInstance();
        super.bucket = f.CreateItem("lava");
        return super.Clicked(player);
    }
}
