package com.lunardev.oneblock.block;

import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Mob;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BlockDetails {

    @Getter
    private static final List<BlockDetails> blocks = new ArrayList<>();
    @Getter
    private final Level currentLevel;
    @Getter
    private Location location;
    @Getter
    private OfflinePlayer owner;
    @Getter
    private Material currentBlock;
    private Random rand;

    public BlockDetails(Level currentLevel, Location location, OfflinePlayer owner) {
        this.currentLevel = currentLevel;
        this.location = location;
        this.owner = owner;
        rand = new Random((long) location.getBlockX() * location.getBlockZ());

        BlockDetails.getBlocks().add(this);

    }
    
    public static @Nullable BlockDetails getBlockLocation(Location location) {
        for (BlockDetails block : blocks) {
            if (location.getBlockZ() == block.location.getBlockZ()
                    && location.getBlockX() == block.location.getBlockX()) {
                return block;
            }
        }
        return null;
    }

    public void spawnBlock(Material block) {
        location.getBlock().setType(block);
        currentBlock = block;
    }

    public void spawnBlock(Chest chest) {
        location.getBlock().setType(chest.getType());
        currentBlock = chest.getType();
        Chest chestBlock = (Chest) location.getBlock().getState();
        chestBlock.getInventory().setContents(chest.getInventory().getContents());
    }

    public void spawnBlock() {
        Material material;

        if (rand.nextFloat(1) < 0.04) {
            ItemStack[] itemSet = currentLevel.getLootPool().getRandomItemSet(rand);
            Block block = location.getBlock();
            if (rand.nextBoolean()) {
                material = Material.BARREL;
            } else {
                material = Material.CHEST;
            }
            block.setType(material);

            InventoryHolder inventoryHolder = (InventoryHolder) block.getBlockData();
            inventoryHolder.getInventory().setContents(itemSet);

        } else {
            material = currentLevel.getRandomMaterial(rand);
            location.getBlock().setType(material);
        }
        currentBlock = material;

        if (rand.nextFloat(1) < 0.02) {
            Mob[] mobSet = currentLevel.getMobPool().getRandomMobSet(rand);
            for (Mob mob : mobSet) {
                Location loc = location.clone().add(rand.nextDouble(0.5d, 3), 0d,
                        rand.nextDouble(0.5d, 3));
                mob.spawnAt(loc);
            }
        }
    }

    public boolean isBlockLocation(Location location) {
        return location.getBlockX() == this.location.getBlockX() && location.getBlockZ() == this.location.getBlockZ();
    }
}
