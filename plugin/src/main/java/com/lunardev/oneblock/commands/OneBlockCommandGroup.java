package com.lunardev.oneblock.commands;

import com.lunardev.oneblock.commands.subcommands.BlockTpSubCommand;
import com.lunardev.oneblock.commands.subcommands.CreateBlockSubCommand;
import com.lunardev.oneblock.commands.subcommands.DeleteBlockSubCommand;
import com.lunardev.oneblock.commands.subcommands.InfoBlockSubCommand;
import org.mineacademy.fo.annotation.AutoRegister;
import org.mineacademy.fo.command.ReloadCommand;
import org.mineacademy.fo.command.SimpleCommandGroup;

@AutoRegister
public final class OneBlockCommandGroup extends SimpleCommandGroup {

    public OneBlockCommandGroup() {
        super("oneblock|ob");
    }

    @Override
    protected void registerSubcommands() {
        registerSubcommand(new CreateBlockSubCommand());
        registerSubcommand(new ReloadCommand());
        registerSubcommand(new InfoBlockSubCommand());
        registerSubcommand(new BlockTpSubCommand());
        registerSubcommand(new DeleteBlockSubCommand());
    }
}
