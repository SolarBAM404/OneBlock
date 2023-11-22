package com.lunardev.oneblock.commands.subcommands;

import com.lunardev.oneblock.block.BlockDetails;
import com.lunardev.oneblock.settings.Localization;
import org.mineacademy.fo.Common;
import org.mineacademy.fo.command.SimpleSubCommand;

public final class CreateBlockSubCommand extends SimpleSubCommand {

    public CreateBlockSubCommand() {
        super("create|c");
        setDescription("Creates a new block.");
    }

    @Override
    protected void onCommand() {
        checkConsole();

        BlockDetails block = BlockDetails.createNewBlock(getPlayer());

        if (block == null) {
            Common.tell(getPlayer(), Localization.Blocks.BLOCK_ALREADY_EXISTS);
            return;
        }

        Common.tell(getPlayer(), Localization.Blocks.BLOCK_CREATED);
        block.teleportToBlock(getPlayer());
    }
}
