package com.lunardev.oneblock.block;

import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Mob;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.Random;

public class BlockDetails {

    @Getter
    private final Level currentLevel;
    @Getter
    private Location location;
    private Random rand;

    public BlockDetails(Level currentLevel, Location location) {
        this.currentLevel = currentLevel;
        this.location = location;
        rand = new Random((long) location.getBlockX() * location.getBlockZ());
    }

    public void spawnBlock(Material block) {
        location.getBlock().setType(block);
    }

    public void spawnBlock(Chest chest) {
        location.getBlock().setType(chest.getType());
        Chest chestBlock = (Chest) location.getBlock().getState();
        chestBlock.getInventory().setContents(chest.getInventory().getContents());
    }

    public void spawnBlock() {
        if (rand.nextFloat(1) < 0.04) {
            ItemStack[] itemSet = currentLevel.getLootPool().getRandomItemSet(rand);
            Block block = location.getBlock();
            if (rand.nextBoolean()) {
                block.setType(Material.BARREL);
            } else {
                block.setType(Material.CHEST);
            }

            InventoryHolder inventoryHolder = (InventoryHolder) block.getBlockData();
            inventoryHolder.getInventory().setContents(itemSet);

        } else {
            Material material = currentLevel.getRandomMaterial(rand);
            location.getBlock().setType(material);
        }

        if (rand.nextFloat(1) < 0.02) {
            Mob[] mobSet = currentLevel.getMobPool().getRandomMobSet(rand);
            for (Mob mob : mobSet) {
                Location loc = location.clone().add(rand.nextDouble(0.5d, 3), 0d,
                        rand.nextDouble(0.5d, 3));
                mob.spawnAt(loc);
            }
        }
    }
}
