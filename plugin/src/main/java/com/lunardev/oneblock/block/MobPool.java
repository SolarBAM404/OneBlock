package com.lunardev.oneblock.block;

import org.bukkit.entity.Mob;

import java.util.List;
import java.util.Random;

public class MobPool {

    private final List<Mob[]> mobPool;

    public MobPool(List<Mob[]> mobPool) {
        this.mobPool = mobPool;
    }

    public Mob[] getRandomMobSet(Random rand) {
        return mobPool.get(rand.nextInt(mobPool.size()));
    }

}
