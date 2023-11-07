package com.lunardev.oneblock.events;

import org.bukkit.Location;
import org.bukkit.entity.FallingBlock;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import com.lunardev.oneblock.block.BlockDetails;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;

public class BlockEvent implements Listener {

    @EventHandler
    public void onBlockChange(EntityChangeBlockEvent event) {
        if (event.getEntity() instanceof FallingBlock fallingBlock) {
            Location location = fallingBlock.getLocation();
            BlockDetails blockDetails = BlockDetails.getBlockLocation(location);
            if (fallingBlock.getBlockData().getMaterial() == (blockDetails != null ? blockDetails.getCurrentBlock() : null)){
                fallingBlock.remove();
                blockDetails.spawnBlock();
            }
        }
    }

    @EventHandler
    public void onPlayerMine(BlockBreakEvent event) {
        Location location = event.getBlock().getLocation();
        BlockDetails blockDetails = BlockDetails.getBlockLocation(location);
        if (blockDetails != null && blockDetails.isBlockLocation(location)) {
            blockDetails.spawnBlock();
            Collection<ItemStack> drops = event.getBlock().getDrops();
            for (ItemStack drop : drops) {
                location.getWorld().dropItem(event.getPlayer().getLocation(), drop);
            }
            event.setDropItems(false);
        }
    }

}
