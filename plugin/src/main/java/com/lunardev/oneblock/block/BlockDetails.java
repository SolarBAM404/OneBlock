package com.lunardev.oneblock.block;

import com.lunardev.oneblock.OneBlockPlugin;
import com.lunardev.oneblock.block.entities.BlockMob;
import com.lunardev.oneblock.block.entities.Item;
import com.lunardev.oneblock.misc.Cuboid;
import com.lunardev.oneblock.settings.BlocksConfig;
import com.lunardev.oneblock.settings.Localization;
import com.lunardev.oneblock.settings.Settings;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.*;
import org.bukkit.block.Barrel;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.mineacademy.fo.ItemUtil;
import org.mineacademy.fo.collection.SerializedMap;
import org.mineacademy.fo.menu.model.ItemCreator;
import org.mineacademy.fo.model.ConfigSerializable;
import org.mineacademy.fo.remain.CompMaterial;

import java.util.*;

public class BlockDetails implements ConfigSerializable {

    public static final OneBlockPlugin plugin = OneBlockPlugin.getInstance();

    @Getter
    private static final List<BlockDetails> blocks = new ArrayList<>();
    private static float[][] blockMap = new float[513][513];
    @Getter
    private Level currentLevel;
    @Getter
    private Location location;
    @Getter
    private OfflinePlayer owner;
    @Getter
    @Setter
    private Material currentBlock;
    @Getter
    private Cuboid cuboid;
    @Getter
    @Setter
    private int currentXp;
    @Getter
    @Setter(AccessLevel.PRIVATE)
    private int blocksMined;
    private final Random rand;

    public BlockDetails(Level currentLevel, Location location, OfflinePlayer owner, int currentXp) {
        if (doesPlayerHaveBlock(owner)) {
            throw new IllegalArgumentException("Player already has a block");
        }

        this.currentLevel = currentLevel;
        this.location = location;
        this.owner = owner;
        this.currentXp = currentXp;
        rand = new Random((long) location.getBlockX() * location.getBlockZ());

        // Set region
        int radius = Settings.BLOCK_SIZE / 2;
        Location bottomCorner = location.clone().add(radius, 0, radius);
        bottomCorner.setY(-64);
        Location topCorner = location.clone().add(-radius, 0, -radius);
        topCorner.setY(255);
        cuboid = new Cuboid(bottomCorner, topCorner);

        addBlock(this);
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

    public static boolean isLocationTaken(Location location) {
        int x = (location.getBlockX() / Settings.BLOCK_DISTANCE) + 256;
        int z = (location.getBlockZ() / Settings.BLOCK_DISTANCE) + 256;
        return blockMap[x][z] == 1;
    }

    public static void setLocationTaken(Location location) {
        int x = (location.getBlockX() / Settings.BLOCK_DISTANCE) + 256;
        int z = (location.getBlockZ() / Settings.BLOCK_DISTANCE) + 256;
        blockMap[x][z] = 1;
    }

    public static BlockDetails createNewBlock(OfflinePlayer owner) {
        Random rand = new Random();

        int x = rand.nextInt(blockMap.length);
        int z = rand.nextInt(blockMap.length);
        World blockWorld = OneBlockPlugin.getInstance().getBlockWorld();
        int rawX = (x - 256) * Settings.BLOCK_DISTANCE;
        int rawZ = (z - 256) * Settings.BLOCK_DISTANCE;

        Location location = new Location(blockWorld, rawX, 96, rawZ);

        while (isLocationTaken(location)) {
            x = rand.nextInt(blockMap.length);
            z = rand.nextInt(blockMap.length);
            rawX = (x - 256) * Settings.BLOCK_DISTANCE;
            rawZ = (z - 256) * Settings.BLOCK_DISTANCE;
            location = new Location(blockWorld, rawX, 96, rawZ);
        }

        blockMap[x][z] = 1;
        BlockDetails blockDetails;

        try {
            blockDetails = new BlockDetails(Level.getDefaultLevel(), location, owner, 0);
        } catch (IllegalArgumentException e) {
            return null;
        }

        blockDetails.spawnBlock(Material.GRASS_BLOCK);

        BlocksConfig blocksConfig = plugin.getBlockDetailsConfig();
        blocksConfig.save();

        return blockDetails;
    }

    public static boolean deleteBreakBlock(Player player) {
        BlockDetails block = getBlockDetails(player);
        if (block == null) {
            return false;
        }
        block.cuboid.fillCuboid(Material.AIR);
        return blocks.remove(block);
    }

    public void spawnBlock(Material block) {
        loadChunk();
        location.getBlock().setType(block);
        currentBlock = block;
    }

    public void spawnBlock(Chest chest) {
        loadChunk();
        location.getBlock().setType(chest.getType());
        currentBlock = chest.getType();
        Chest chestBlock = (Chest) location.getBlock().getState();
        chestBlock.getInventory().setContents(chest.getInventory().getContents());
    }

    public void spawnBlock() {
        loadChunk();
        if (currentLevel == null) {
            currentLevel = Level.getDefaultLevel();
        }

        Material material;

        float chestChance = rand.nextFloat() % 1;
        if (chestChance < 0.04) {
            Block block = location.getBlock();
            if (rand.nextBoolean()) {
                material = Material.BARREL;
            } else {
                material = Material.CHEST;
            }
            block.setType(material);

            Inventory inventory;
            if (block.getState() instanceof Chest chest) {
                inventory = chest.getInventory();
            } else {
                inventory = ((Barrel) block.getState()).getInventory();
            }

            int amt = rand.nextInt(2,10);
            ItemStack[] contents = inventory.getContents();

            for (int i = 0; i < amt; i++) {
                Item item = currentLevel.getRandomItem(rand);
                ItemCreator creator = ItemCreator.of(CompMaterial.fromMaterial(item.getMaterial()), item.getName(), "");
                creator.amount(item.getAmount());
                int slot = rand.nextInt(contents.length);
                while (contents[slot] != null) {
                    slot = rand.nextInt(contents.length);
                }
                contents[slot] = creator.make();
            }
            inventory.setContents(contents);

            // TODO add a way to set the chest name
            // TODO add a way to set the chest lore
            //TODO add a way to set the chest enchantments
            //TODO add a way to set the chest item flags

        } else {
            material = currentLevel.getRandomMaterial(rand);
            location.getBlock().setType(material, true);
        }
        currentBlock = material;

        if (blocksMined < Settings.MINIMUM_BLOCKS_BEFORE_MOBS_SPAWN) {
            return;
        }

        float mobChance = rand.nextFloat() % 1;
        if (mobChance < currentLevel.getMobSpawnChance()) {
            BlockMob mob = currentLevel.getRandomMobSet(rand);

            for (int i = 0; i < mob.getAmount(); i++) {
                double x = rand.nextDouble() % 1 + 0.5d;
                double z = rand.nextDouble() % 1 + 0.5d;
                Location loc = location.clone().add(x, 1d,
                        z);
                Entity entity = Objects.requireNonNull(loc.getWorld()).spawnEntity(loc, mob.getEntityType(), true);
                if (mob.getName() != null) {
                    entity.setCustomName(mob.getName());
                    entity.setCustomNameVisible(true);
                }
            }

            // TODO add a way to set the mob health
            // TODO add a way to set the mob armor
            // TODO add a way to set the mob weapon
        }
    }

    private void loadChunk() {
        location.getChunk().load();
    }

    public boolean isBlockLocation(Location location) {
        return location.getBlockX() == this.location.getBlockX() && location.getBlockZ() == this.location.getBlockZ();
    }

    public boolean isBlockLocationWithY(Location location) {
        return location.getBlockX() == this.location.getBlockX()
                && location.getBlockY() == this.location.getBlockY()
                && location.getBlockZ() == this.location.getBlockZ();
    }

    public void teleportToBlock(Player player) {
        Location added = location.clone().add(0.5, 1, 0.5);
        player.teleport(added);

        if (player.getBedSpawnLocation() == null || !cuboid.isInCuboid(player.getBedSpawnLocation())) {
            player.setBedSpawnLocation(added, true);
        }
    }

    public void addBlockMined() {
        blocksMined++;
    }

    public void addXp(int i) {
        currentXp += i;

        if (currentXp >= currentLevel.getLevelExperience()) {
            if (currentLevel.getLevelNumber() >= Level.getFinalLevel().getLevelNumber()) {
                return;
            }

            int maxXp = currentLevel.getLevelExperience();
            currentLevel = Level.getLevel(currentLevel.getLevelNumber() + 1);

            if (owner.isOnline()) {
                Player player = owner.getPlayer();
                player.sendMessage(Localization.Blocks.LEVEL_UP.replace("{level}", currentLevel.getLevelNumber() + ""));
                player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1f, 1f);
            }

            currentXp = currentXp - maxXp;
        }

    }

    public static BlockDetails getBlockDetails(OfflinePlayer player) {
        for (BlockDetails block : blocks) {
            if (block.getOwner().equals(player)) {
                return block;
            }
        }
        return null;
    }

    public static void addBlock(BlockDetails block) {
        for (BlockDetails blockDetails : getBlocks()) {
            if (blockDetails.getOwner().equals(block.getOwner())
                    && blockDetails.getLocation().equals(block.getLocation())) {
                return;
            }
        }

        blocks.add(block);
    }

    public static boolean doesPlayerHaveBlock(OfflinePlayer player) {
        for (BlockDetails block : blocks) {
            if (block.getOwner().equals(player)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public SerializedMap serialize() {
        SerializedMap map = new SerializedMap();
        map.put("Owner_Uuid", owner.getUniqueId());
        map.put("Current_Level", currentLevel.getLevelNumber());
        map.put("Current_Xp", currentXp);
        map.put("Blocks_Mined", blocksMined);
        map.put("Location", location);
        map.put("Cube", cuboid);

        return map;
    }

    public static BlockDetails deserialize(SerializedMap map) {
        UUID ownerUuid = map.getUUID("Owner_Uuid");
        OfflinePlayer owner = Bukkit.getOfflinePlayer(ownerUuid);

        int currentLevel = map.getInteger("Current_Level");
        Level level = Level.getLevel(currentLevel) == null ? Level.getDefaultLevel() : Level.getLevel(currentLevel);

        int currentXp = map.getInteger("Current_Xp");
        int blocksMined = map.getInteger("Blocks_Mined");
        Location location = map.getLocation("Location");
        Cuboid cuboid = map.get("Cube", Cuboid.class);
        BlockDetails blockDetails;

        try {
            blockDetails = new BlockDetails(level, location, owner, currentXp);
        } catch (IllegalArgumentException e) {
            return null;
        }

        blockDetails.blocksMined = blocksMined;
        blockDetails.cuboid = cuboid;
        return blockDetails;
    }
}
