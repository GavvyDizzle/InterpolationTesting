package com.github.gavvydizzle.interpolationtesting;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class InterpolationTesting extends JavaPlugin {

    @Override
    public void onEnable() {
        Objects.requireNonNull(getServer().getPluginCommand("dit")).setExecutor(new TestCommand(this));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
