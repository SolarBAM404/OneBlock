package com.lunardev.oneblock.tasks;

import com.lunardev.oneblock.OneBlockPlugin;
import org.mineacademy.fo.model.SimpleRunnable;

public class BlockSaverTask extends SimpleRunnable {
    @Override
    public void run() {
        OneBlockPlugin.getInstance().getBlockDetailsConfig().save();
    }
}
