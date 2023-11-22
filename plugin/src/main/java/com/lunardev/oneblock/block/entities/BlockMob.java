package com.lunardev.oneblock.block.entities;

import lombok.Getter;
import org.bukkit.entity.EntityType;
import org.jetbrains.annotations.Nullable;
import org.mineacademy.fo.collection.SerializedMap;
import org.mineacademy.fo.model.ConfigSerializable;

import java.util.Objects;

@Getter
public class BlockMob implements ConfigSerializable {

    private @Nullable
    final String name;
    private final int amount;
    private final float chance;
    private final EntityType entityType;

    public BlockMob(@Nullable String name, int amount, float chance, EntityType entityType) {
        this.name = name;
        this.amount = amount;
        this.chance = chance;
        this.entityType = entityType;
    }

    @Override
    public SerializedMap serialize() {
        SerializedMap map = new SerializedMap();
        map.put("Name", name == null ? "null" : name);
        map.put("Amount", amount);
        map.put("Chance", chance);
        map.put("Entity_Type", entityType);

        return map;
    }

    public static BlockMob deserialize(SerializedMap map) {
        String name = Objects.equals(map.getString("Name"), "null") ? null : map.getString("name");
        Integer amount = map.getInteger("Amount");
        Float chance = map.getFloat("Chance");
        EntityType entityType = map.get("Entity_Type", EntityType.class);

        return new BlockMob(name, amount, chance, entityType);
    }
}
