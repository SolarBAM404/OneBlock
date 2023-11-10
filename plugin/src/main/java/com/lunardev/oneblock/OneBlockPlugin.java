package com.lunardev.oneblock;

import com.lunardev.oneblock.events.BlockEvent;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;

public final class OneBlockPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        setupListeners(BlockEvent.class);

        if (!getServer().getPluginManager().isPluginEnabled(this)) {
            return;
        }

        MainCommand.setupCommand(this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().log(Level.INFO, "Shutting down plugin...");
    }

    private void setupListeners(Class<? extends Listener> listenerClass) {
        try {
            getServer().getPluginManager().registerEvents(listenerClass.getDeclaredConstructor().newInstance(), this);
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            getServer().getPluginManager().disablePlugin(this);
            getLogger().log(Level.SEVERE, String.format("Could not load %s listener", listenerClass.getName()));
        }
    }

}
