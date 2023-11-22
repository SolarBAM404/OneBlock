package com.lunardev.oneblock.misc;

import org.bukkit.Location;
import org.bukkit.Material;
import org.mineacademy.fo.BlockUtil;
import org.mineacademy.fo.Common;
import org.mineacademy.fo.collection.SerializedMap;
import org.mineacademy.fo.model.ConfigSerializable;

public class Cuboid implements ConfigSerializable {

    private final Location loc1;
    private final Location loc2;

    public Cuboid(Location loc1, Location loc2) {
        this.loc1 = loc1;
        this.loc2 = loc2;
    }

    public boolean isInCuboid(Location loc) {
        return BlockUtil.isWithinCuboid(loc, loc1, loc2);
    }

    public void fillCuboid(Material material) {

        Runnable task = () -> {
            Location loc;
            for (int x = loc1.getBlockX(); x <= loc2.getBlockX(); x++) {
                for (int y = loc1.getBlockY(); y <= loc2.getBlockY(); y++) {
                    for (int z = loc1.getBlockZ(); z <= loc2.getBlockZ(); z++) {
                        loc = new Location(loc1.getWorld(), x, y, z);
                        loc.getBlock().setType(material);
                    }
                }
            }
        };

        Common.runAsync(task);

    }

    @Override
    public SerializedMap serialize() {
        SerializedMap map = new SerializedMap();
        map.put("Loc1", loc1);
        map.put("Loc2", loc2);
        return map;
    }

    public static Cuboid deserialize(SerializedMap map) {
        return new Cuboid(map.getLocation("Loc1"), map.getLocation("Loc2"));
    }
}
