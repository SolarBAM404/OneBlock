package com.lunardev.oneblock;

import com.lunardev.oneblock.block.BlockDetails;
import com.lunardev.oneblock.block.Level;
import com.lunardev.oneblock.misc.VoidWorldGenerator;
import com.lunardev.oneblock.settings.BlocksConfig;
import com.lunardev.oneblock.settings.LevelsConfig;
import com.lunardev.oneblock.settings.Settings;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.mineacademy.fo.Common;
import org.mineacademy.fo.plugin.SimplePlugin;

import java.util.List;


public final class OneBlockPlugin extends SimplePlugin {

    @Getter
    @Setter(AccessLevel.PRIVATE)
    private static OneBlockPlugin instance;

    @Getter
    private List<BlockDetails> blocks;

    @Getter
    private LevelsConfig levelsConfig;
    @Getter
    private BlocksConfig blockDetailsConfig;

    private World blockWorld;

    @Override
    protected void onPluginStart() {
        setInstance(this);
        if (!getServer().getPluginManager().isPluginEnabled(this)) {
            return;
        }

        Common.runLater(() -> {
            blockWorld = getBlockWorld();
            levelsConfig = new LevelsConfig();
            blockDetailsConfig = new BlocksConfig();
        });
        Common.runTimerAsync(5 * 20, 5 * 20, () -> blockDetailsConfig.save());

    }

    @Override
    protected void onPluginReload() {
        Common.cancelTasks();
        Level.resetLevels();
        levelsConfig.reload();
        BlockDetails.getBlocks().clear();
        blockDetailsConfig.reload();

        Common.runTimerAsync(5 * 20, 5 * 20, () -> blockDetailsConfig.save());
    }

    public World getBlockWorld() {
        World world = Bukkit.getWorld(Settings.BLOCK_WORLD_NAME);

        if (world == null) {
            world = Bukkit.createWorld(new WorldCreator(Settings.BLOCK_WORLD_NAME).generator(new VoidWorldGenerator()));
        }

        return world;
    }

}
