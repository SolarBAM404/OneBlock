package com.lunardev.oneblock.events;

import com.lunardev.oneblock.OneBlockPlugin;
import com.lunardev.oneblock.block.BlockDetails;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.mineacademy.fo.annotation.AutoRegister;
import org.mineacademy.fo.event.SimpleListener;
import org.mineacademy.fo.model.SimpleRunnable;

@AutoRegister
public final class BlockChangeEvent extends SimpleListener {

    private static final BlockChangeEvent instance = new BlockChangeEvent();

    private BlockChangeEvent() {
        super(EntityChangeBlockEvent.class);
    }


    @Override
    protected void execute(Event event) {

        if (event instanceof EntityChangeBlockEvent entityChangeBlockEvent) {
            if (entityChangeBlockEvent.getEntityType() == (EntityType.FALLING_BLOCK)) {
                Location location = entityChangeBlockEvent.getBlock().getLocation();
                BlockDetails blockDetails = BlockDetails.getBlockLocation(location);
                if (blockDetails != null && blockDetails.isBlockLocationWithY(location)) {
                    SimpleRunnable runnable = new SimpleRunnable() {
                        @Override
                        public void run() {
                            blockDetails.spawnBlock();
                        }
                    };

                    runnable.runTaskLater(OneBlockPlugin.getInstance(), 1L);
                    location.getNearbyEntities(0.5, 5, 0.5).forEach(entity -> {
                        if (entity.getType() == EntityType.PLAYER) {
                            entity.teleport(entity.getLocation().add(0, 1, 0));
                        }
                    });
                }
            }
        }
    }

}
