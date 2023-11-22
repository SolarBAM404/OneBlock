package com.lunardev.oneblock.events;

import com.lunardev.oneblock.block.BlockDetails;
import org.bukkit.Location;
import org.bukkit.event.Event;
import org.bukkit.event.block.LeavesDecayEvent;
import org.mineacademy.fo.annotation.AutoRegister;
import org.mineacademy.fo.event.SimpleListener;

@AutoRegister
public final class LeavesDecayListener extends SimpleListener {

    private static final LeavesDecayListener instance = new LeavesDecayListener();

    private LeavesDecayListener() {
        super(LeavesDecayEvent.class);
    }


    @Override
    protected void execute(Event event) {

        if (event instanceof LeavesDecayEvent leavesDecayEvent) {
            Location location = leavesDecayEvent.getBlock().getLocation();
            BlockDetails blockDetails = BlockDetails.getBlockLocation(location);

            if (blockDetails != null && blockDetails.isBlockLocationWithY(location)) {
                leavesDecayEvent.setCancelled(true);
            }
        }
    }

}
