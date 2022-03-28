package me.fabsi23.infiniteblocks.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import me.fabsi23.infiniteblocks.config.InfiniteBlocksConfig;
import me.fabsi23.infiniteblocks.utils.Message;

public class CMDinfiniteblocksreload implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender.hasPermission("infiniteblocks.reload")) {
			InfiniteBlocksConfig.reload();
			Message.sendIfNotVoid(sender, InfiniteBlocksConfig.getConfigReloaded());
		} else {
			Message.sendIfNotVoid(sender, InfiniteBlocksConfig.getNoPermission());
		}
		return true;
	}
}