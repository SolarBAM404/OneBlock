package com.lunardev.oneblock.settings;

import com.lunardev.oneblock.block.BlockDetails;
import org.mineacademy.fo.collection.SerializedMap;
import org.mineacademy.fo.settings.YamlConfig;

public class BlocksConfig extends YamlConfig {


    public BlocksConfig() {
        loadConfiguration(NO_DEFAULT, "blocks.yml");
    }

    @Override
    protected void onLoad() {
        getMapList("Blocks").forEach(BlockDetails::deserialize);
    }


    @Override
    public SerializedMap saveToMap() {
        SerializedMap map = new SerializedMap();

        map.put("Blocks", BlockDetails.getBlocks());

        return map;
    }
}
