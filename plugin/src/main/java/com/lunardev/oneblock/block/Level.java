package com.lunardev.oneblock.block;

import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.Random;

@Getter
public class Level {

    private int levelNumber;
    private LootPool lootPool;
    private MobPool mobPool;
    private final Material[] blockTable;

    public Level(int levelNumber, Material[] blockTable, LootPool lootPool, MobPool mobPool) {
        this.levelNumber = levelNumber;
        this.blockTable = blockTable;
        this.lootPool = lootPool;
        this.mobPool = mobPool;
    }

    public Material getRandomMaterial(Random rand) {
        return blockTable[rand.nextInt(blockTable.length)];
    }

}
