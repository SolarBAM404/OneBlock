package com.lunardev.oneblock.block;

import com.lunardev.oneblock.block.entities.BlockMob;
import com.lunardev.oneblock.block.entities.Item;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.mineacademy.fo.collection.SerializedMap;
import org.mineacademy.fo.model.ConfigSerializable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Getter
public class Level implements ConfigSerializable {

    @Getter
    @Setter(AccessLevel.PRIVATE)
    private static Level defaultLevel;

    @Getter
    @Setter(AccessLevel.PRIVATE)
    private static Level finalLevel;

    @Getter
    private static List<Level> levels = new ArrayList<>();

    private int levelNumber;
    private int levelExperience = 25;
    private float mobSpawnChance = 0.01f;
    private boolean isDefault;
    private final Item[] lootPool;
    private final BlockMob[] mobPool;
    private final Material[] blockTable;

    public Level(int levelNumber, int levelExperience, float maxMobSpawnChance, boolean isDefault,
                 Material[] blockTable, Item[] lootPool, BlockMob[] mobPool) {
        this.levelNumber = levelNumber;
        this.levelExperience = levelExperience;
        mobSpawnChance = maxMobSpawnChance;
        this.isDefault = isDefault;
        this.blockTable = blockTable;
        this.lootPool = lootPool;
        this.mobPool = mobPool;

        if (isDefault) {
            Level.setDefaultLevel(this);
        }

        if (Level.getFinalLevel() == null || levelNumber > Level.getFinalLevel().getLevelNumber()) {
            Level.setFinalLevel(this);
        }

        Level.addLevel(this);
    }

    public Material getRandomMaterial(Random rand) {
        return blockTable[rand.nextInt(blockTable.length)];
    }

    public Material getRandomItemSet(Random rand) {
        return lootPool[(rand.nextInt(lootPool.length))].getMaterial(); // TODO this should return the item class
    }

    public Item getRandomItem(Random rand) {
        return lootPool[rand.nextInt(lootPool.length)];
    }

    public BlockMob getRandomMobSet(Random rand) {
        double totalChances = Arrays.stream(mobPool).mapToDouble(BlockMob::getChance).sum();
        double chance = rand.nextDouble() * totalChances;
        double cumulativeChance = 0.0;

        double normalizedChance = chance / totalChances;

        for (BlockMob mob : mobPool) {
            cumulativeChance += mob.getChance();
            if (normalizedChance <= cumulativeChance) {
                return mob;
            }
        }

        return mobPool[rand.nextInt(mobPool.length)];
    }

    public static Level getLevel(int levelNumber) {
        for (Level level : levels) {
            if (level.getLevelNumber() == levelNumber) {
                return level;
            }
        }
        return null;
    }

    public static void resetLevels() {
        levels.clear();
    }

    private static void addLevel(Level level) {
        for (Level lv : levels) {
            if (lv.getLevelNumber() == level.getLevelNumber()) {
                throw new IllegalArgumentException("Level number already exists");
            }
        }

        levels.add(level);
    }

    @Override
    public SerializedMap serialize() {
        SerializedMap map = new SerializedMap();

        map.put("Level", levelNumber);
        map.put("Level_Experience", levelExperience);
        map.put("Mob_Spawn_Chance", mobSpawnChance);
        map.put("Block_Table", blockTable);
        map.put("Loot_Pool", lootPool);
        map.put("Mob_Pool", mobPool);


        return map;
    }

    public static Level deserialize(SerializedMap map) {
        Integer levelNumber = map.getInteger("Level");
        Integer levelExperience = map.getInteger("Level_Experience");
        Float maxMobSpawnChance = map.getFloat("Mob_Spawn_Chance");
        Boolean isDefault = map.getBoolean("Is_Default");
        List<Material> blockTable = map.getList("Blocks", Material.class);
        List<Item> lootPoolList = map.getList("Loot", Item.class);
        List<BlockMob> mobPoolList = map.getList("Mobs", BlockMob.class);

        Item[] lootPool = new Item[lootPoolList.size()];
        lootPoolList.toArray(lootPool);

        BlockMob[] mobPool = new BlockMob[mobPoolList.size()];
        mobPoolList.toArray(mobPool);

        return new Level(levelNumber, levelExperience, maxMobSpawnChance,
                isDefault, blockTable.toArray(new Material[0]), lootPool, mobPool);
    }
}
