package me.fabsi23.infiniteblocks.config;

import me.fabsi23.infiniteblocks.utils.Logging;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import me.fabsi23.infiniteblocks.InfiniteBlocks;

import java.util.HashMap;
import java.util.Map;

public class InfiniteBlocksConfig {

    private static Map<Material, Double> materialWithCost;

    private static final String CONFIG = "Config.";
    private static final String PREFIX = CONFIG + "Prefix.";
    private static final String MESSAGE = CONFIG + "Messages.";
    private static final String SETTINGS = CONFIG + "Settings.";

    private static final JavaPlugin plugin = InfiniteBlocks.getReference();
    private static FileConfiguration cfg = plugin.getConfig();

    public static String getPluginPrefix() {
        return cfg.getString(PREFIX + "plugin-prefix");
    }

    public static String getNoPermission() {
        return cfg.getString(MESSAGE + "no-permission");
    }

    public static String getConfigReloaded() {
        return cfg.getString(MESSAGE + "reloaded-config");
    }

    public static String getInvalidItem() {
        return cfg.getString(MESSAGE + "invalid-item");
    }

    public static String getInvalidPlayer() {
        return cfg.getString(MESSAGE + "invalid-player");
    }

    public static String getObtainedInfiniteBlock(Material mat, String name) {
        String message = cfg.getString(MESSAGE + "obtained-infinite-item");
        String item = mat.toString().toLowerCase();
        message = message.replace("%ITEM%", item);
        if (name == null)
            name = item;
        message = message.replace("%ITEMNAME%", name);
        return message;
    }

    public static String getHandedOutInfiniteBlock(Material mat, Player player, String name) {
        String message = cfg.getString(MESSAGE + "handed-out-infinite-item");
        String item = mat.toString().toLowerCase();
        message = message.replace("%ITEM%", item);
        if (name == null)
            name = item;
        message = message.replace("%ITEMNAME%", name);
        message = message.replace("%PLAYER%", player.getName());
        message = message.replace("%DISPLAY%", player.getDisplayName());
        return message;
    }

    public static String getPlacingFailedMissingBalance(double balance) {
        String message = cfg.getString(MESSAGE + "placing-failed-missing-balance");
        message = message.replace("%AMOUNT%", "" + balance);
        return message;
    }

    public static String getInfiniteBlockHelp() {
        return cfg.getString(MESSAGE + "infiniteblock-help");
    }

    public static double getMaterialCost(Material mat) {
        Double amount = materialWithCost.get(mat);
        if (amount == null) {
            amount = getDefaultCost();
        }
        return amount;
    }

    private static double getDefaultCost() {
        return cfg.getDouble(SETTINGS + "blockUsageCost.defaultUsageCost");
    }

    private static Map<Material, Double> getMaterialsWithCost() {
        String configKey = SETTINGS + "blockUsageCost.usageCostPerMaterial";
        Map<Material, Double> usageCostPerMaterial = new HashMap<>();
        for (String material : cfg.getConfigurationSection(configKey).getKeys(false)) {
            double cost = cfg.getDouble(configKey + "." + material);
            Material mat = Material.getMaterial(material);
            if (mat != null) {
                usageCostPerMaterial.put(mat, cost);
            } else {
                Logging.logWarning("Configured cost for material " + material + " which is not a valid material.");
            }
        }
        return usageCostPerMaterial;
    }

    public static void reload() {
        plugin.reloadConfig();
        cfg = plugin.getConfig();
        materialWithCost = getMaterialsWithCost();
    }
}
