package com.lunardev.oneblock.block.entities;

import lombok.Getter;
import org.bukkit.Material;
import org.jetbrains.annotations.Nullable;
import org.mineacademy.fo.collection.SerializedMap;
import org.mineacademy.fo.model.ConfigSerializable;
import org.mineacademy.fo.remain.CompMaterial;

@Getter
public class Item implements ConfigSerializable {

    private @Nullable
    final String name;
    private final int amount;
    private final float chance;
    private final Material material;

    public Item(@Nullable String name, int amount, float chance, Material material) {
        this.name = name;
        this.amount = amount;
        this.chance = chance;
        this.material = material;
    }

    @Override
    public SerializedMap serialize() {
        SerializedMap map = new SerializedMap();

        map.put("Name", name == null ? "null" : name);
        map.put("Amount", amount);
        map.put("Chance", chance);
        map.put("Material", material);

        return map;
    }

    public static Item deserialize(SerializedMap map) {
        String name = map.getString("Name") == null ? null : map.getString("name");
        Integer amount = map.getInteger("Amount");
        Float chance = map.getFloat("Chance");
        CompMaterial material = map.getMaterial("Material");

        return new Item(name, amount, chance, material.getMaterial());
    }
}
