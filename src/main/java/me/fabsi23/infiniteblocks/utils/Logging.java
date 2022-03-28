package me.fabsi23.infiniteblocks.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import me.fabsi23.infiniteblocks.config.InfiniteBlocksConfig;

public class Logging {

	private static final String CONSOLE_PREFIX = ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', InfiniteBlocksConfig.getPluginPrefix())) + " ";
	
	public static void logWarning(String message) {
		Bukkit.getLogger().warning(CONSOLE_PREFIX + message);
	}

	public static void logInfo(String message) {
		Bukkit.getLogger().info(CONSOLE_PREFIX +  message);
	}
}
