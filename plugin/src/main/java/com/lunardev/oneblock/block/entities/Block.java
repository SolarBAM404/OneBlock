package com.lunardev.oneblock.block.entities;

import org.bukkit.Material;

public class Block {

    private final String name;
    private final Material material;

    public Block(String name, Material material) {
        this.name = name;
        this.material = material;
    }
}
