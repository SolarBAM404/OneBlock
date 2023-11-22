package com.lunardev.oneblock.commands.subcommands;

import com.lunardev.oneblock.block.BlockDetails;
import com.lunardev.oneblock.settings.Localization;
import org.mineacademy.fo.Common;
import org.mineacademy.fo.command.SimpleSubCommand;

public final class BlockTpSubCommand extends SimpleSubCommand {

    public BlockTpSubCommand() {
        super("teleport|tp|t");
        setDescription("Teleport to your block.");
    }

    @Override
    protected void onCommand() {
        checkConsole();

        BlockDetails block = BlockDetails.getBlockDetails(getPlayer());

        if (block == null) {
            Common.tell(getPlayer(), Localization.Blocks.BLOCK_DOES_NOT_EXIST);
            return;
        }

        block.teleportToBlock(getPlayer());
        Common.tell(getPlayer(), Localization.Blocks.TELEPORT);
    }
}
