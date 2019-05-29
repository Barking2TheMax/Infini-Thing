package me.tehbosscat.infinithing.Items;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;

public class ThrownInfiniItem extends InfiniItem {
    private EntityType ENTITY_TYPE;

    public ThrownInfiniItem(String name, Material material, EntityType entityType, String permissionPath, String configPath){
        super(name, material, permissionPath, configPath);
        ENTITY_TYPE = entityType;
    }

    public EntityType GetEntityType(){
        return ENTITY_TYPE;
    }
}
