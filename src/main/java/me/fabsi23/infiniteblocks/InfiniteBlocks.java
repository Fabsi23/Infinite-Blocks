package me.fabsi23.infiniteblocks;

import me.fabsi23.infiniteblocks.externalApis.Vault;
import org.bstats.bukkit.Metrics;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import me.fabsi23.infiniteblocks.commands.CMDinfiniteblock;
import me.fabsi23.infiniteblocks.commands.CMDinfiniteblocksreload;
import me.fabsi23.infiniteblocks.config.InfiniteBlocksConfig;
import me.fabsi23.infiniteblocks.listeners.BlockPlaceListener;
import me.fabsi23.infiniteblocks.utils.Logging;

public class InfiniteBlocks extends JavaPlugin {

    /**
     * author: Fabsi23 Date: 28.03.2022 - 28.03.2022 (DMY) Last edited: 15.03.2024
     */

    private static InfiniteBlocks instance;
    private static NamespacedKey namespacedKey;

    // External APIs
    private Vault vault;

    @Override
    public void onEnable() {
        long current = System.currentTimeMillis();
        InfiniteBlocks.instance = this;
        namespacedKey = new NamespacedKey(InfiniteBlocks.getReference(), "infinite");
        enableBStats();
        loadConfigurations();
        prepareExternalApis();
        registerCommands();
        registerEvents();
        Logging.logInfo("Plugin activated! Starting took " + (System.currentTimeMillis() - current) + " ms.");
    }

    @Override
    public void onDisable() {
        Logging.logInfo("Plugin deactivated!");
    }

    private void loadConfigurations() {
        saveDefaultConfig();
        InfiniteBlocksConfig.reload();
    }

    private void enableBStats() {
        int pluginId = 14786;
        new Metrics(this, pluginId);
    }

    private void registerCommands() {
        this.getCommand("infiniteblocksreload").setExecutor(new CMDinfiniteblocksreload());
        this.getCommand("infiniteblock").setExecutor(new CMDinfiniteblock());
    }

    private void registerEvents() {
        PluginManager pm = this.getServer().getPluginManager();
        pm.registerEvents(new BlockPlaceListener(vault), this);
    }

    private void prepareExternalApis() {
        vault = new Vault(this);
        if(vault.isAvailable()) {
            Logging.logInfo("Vault & Economy successfully linked. Item usage costs as per configuration will apply.");
        } else {
            Logging.logWarning("Vault and/or Economy not available. Item usage costs not available.");
        }
    }

    public static NamespacedKey getNamespacedKey() {
        return namespacedKey;
    }

    public static InfiniteBlocks getReference() {
        return instance;
    }
}