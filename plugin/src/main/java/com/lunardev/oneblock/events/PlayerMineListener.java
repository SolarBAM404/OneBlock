package com.lunardev.oneblock.events;

import com.lunardev.oneblock.OneBlockPlugin;
import com.lunardev.oneblock.block.BlockDetails;
import org.bukkit.Location;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.mineacademy.fo.annotation.AutoRegister;
import org.mineacademy.fo.event.SimpleListener;
import org.mineacademy.fo.model.SimpleRunnable;

import java.util.Collection;

@AutoRegister
public final class PlayerMineListener extends SimpleListener {

    private static final PlayerMineListener instance = new PlayerMineListener();

    private PlayerMineListener() {
        super(BlockBreakEvent.class);
    }

    @Override
    protected void execute(Event event) {
        if (event instanceof BlockBreakEvent blockBreakEvent) {
            Location location = blockBreakEvent.getBlock().getLocation();
            BlockDetails blockDetails = BlockDetails.getBlockLocation(location);
            if (blockDetails != null && blockDetails.isBlockLocationWithY(location)) {
                Collection<ItemStack> drops = blockBreakEvent.getBlock().getDrops();
                for (ItemStack drop : drops) {
                    location.getWorld().dropItem(blockBreakEvent.getPlayer().getLocation(), drop);
                }
                blockBreakEvent.setDropItems(false);
                SimpleRunnable runnable = new SimpleRunnable() {
                    @Override
                    public void run() {
                        blockDetails.spawnBlock();
                    }
                };

                runnable.runTaskLater(OneBlockPlugin.getInstance(), 5L);

                Location playerLocation = blockBreakEvent.getPlayer().getLocation();
                if (playerLocation.getBlockX() == blockDetails.getLocation().getBlockX()
                        && playerLocation.getBlockZ() == blockDetails.getLocation().getBlockZ()) {
                    Location added = blockDetails.getLocation().clone().add(0.5, 1.75, 0.5);
                    added.setYaw(blockBreakEvent.getPlayer().getLocation().getYaw());
                    added.setPitch(blockBreakEvent.getPlayer().getLocation().getPitch());
                    if (blockBreakEvent.getPlayer().isFlying()) {
                        added.add(0, 1.5, 0);
                    }
                    blockBreakEvent.getPlayer().teleport(added);
                }

                blockDetails.addXp(1);
                blockDetails.addBlockMined();
            }
        }
    }
}
