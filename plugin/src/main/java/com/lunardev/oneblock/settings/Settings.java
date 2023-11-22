package com.lunardev.oneblock.settings;

import com.lunardev.oneblock.settings.enums.LevelView;
import lombok.Getter;
import org.mineacademy.fo.settings.SimpleSettings;

@Getter
public final class Settings extends SimpleSettings {

    // Version is 1 unless major changes happen
    private static final int configVersion = 1;

    public static String BLOCK_WORLD_NAME = "blocksWorld";

    public static String LOBBY_WORLD_NAME = "world";

    public static Boolean LEVEL_UP_PLAY_SOUND = true;
    public static String LEVEL_UP_SOUND = "ENTITY_PLAYER_LEVELUP";
    public static Boolean LEVEL_UP_SHOW_TITLE = true;
    public static LevelView LEVEL_VIEW = LevelView.Action_Bar;

    public static Integer MINIMUM_BLOCKS_BEFORE_MOBS_SPAWN = 10;
    public static Integer BLOCK_SIZE = 256;
    public static Integer BLOCK_DISTANCE = BLOCK_SIZE / 2;

    @Override
    protected int getConfigVersion() {
        return Settings.configVersion;
    }


}
