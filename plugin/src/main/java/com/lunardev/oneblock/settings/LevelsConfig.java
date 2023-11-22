package com.lunardev.oneblock.settings;

import com.lunardev.oneblock.block.Level;
import lombok.Getter;
import org.mineacademy.fo.settings.YamlConfig;

import java.util.List;

public class LevelsConfig extends YamlConfig {

    @Getter
    private List<Level> levels;

    public LevelsConfig() {
        loadConfiguration("levels.yml");
    }

    @Override
    protected void onLoad() {
        levels = getList("levels", Level.class);
    }
}
