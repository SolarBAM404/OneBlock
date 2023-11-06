package com.lunardev.oneblock.block;

import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Random;

public class LootPool {

    private final List<ItemStack[]> inventories;

    public LootPool(List<ItemStack[]> inventories) {
        this.inventories = inventories;
    }

    public ItemStack[] getRandomItemSet(Random rand) {
        return inventories.get(rand.nextInt(inventories.size()));
    }

}
