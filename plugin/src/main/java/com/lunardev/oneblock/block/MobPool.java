package com.lunardev.oneblock.block;

import org.bukkit.entity.EntityType;

import java.util.List;
import java.util.Random;

public class MobPool {

    private final List<EntityType[]> pool;

    public MobPool(List<EntityType[]> mobPool) {
        this.pool = mobPool;
    }

    public EntityType[] getRandomMobSet(Random rand) {
        return pool.get(rand.nextInt(pool.size()));
    }

}
