package me.fabsi23.infiniteblocks.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import me.fabsi23.infiniteblocks.InfiniteBlocks;
import me.fabsi23.infiniteblocks.config.InfiniteBlocksConfig;
import me.fabsi23.infiniteblocks.utils.Message;

public class CMDinfiniteblock implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("infiniteblocks.infiniteblock")) {
            Message.sendIfNotVoid(sender, InfiniteBlocksConfig.getNoPermission());
            return true;
        }

        if (args.length == 0) {
            Message.sendIfNotVoid(sender, InfiniteBlocksConfig.getInfiniteBlockHelp());
            return true;
        } else if (args.length == 1) {
            if (!(sender instanceof Player)) {
                Message.sendIfNotVoid(sender, InfiniteBlocksConfig.getInfiniteBlockHelp());
                return true;
            } else {
                Material mat = Material.getMaterial(args[0].toUpperCase());
                if (mat == null) {
                    Message.sendIfNotVoid(sender, InfiniteBlocksConfig.getInvalidItem());
                    return true;
                }
                addInfiniteItemOfType((Player) sender, mat, null);
                return true;
            }
        } else if (args.length == 2) {
            Material mat = Material.getMaterial(args[0].toUpperCase());
            if (mat == null) {
                Message.sendIfNotVoid(sender, InfiniteBlocksConfig.getInvalidItem());
                return true;
            }
            Player player = Bukkit.getPlayer(args[1]);
            if (player == null) {
                Message.sendIfNotVoid(sender, InfiniteBlocksConfig.getInvalidPlayer());
                return true;
            }
            addInfiniteItemOfType(player, mat, null);
            if (!player.equals(sender))
                Message.sendIfNotVoid(sender, InfiniteBlocksConfig.getHandedOutInfiniteBlock(mat, player, null));
            return true;
        } else {
            Material mat = Material.getMaterial(args[0].toUpperCase());
            if (mat == null) {
                Message.sendIfNotVoid(sender, InfiniteBlocksConfig.getInvalidItem());
                return true;
            }
            Player player = Bukkit.getPlayer(args[1]);
            if (player == null) {
                Message.sendIfNotVoid(sender, InfiniteBlocksConfig.getInvalidPlayer());
                return true;
            }
            StringBuilder itemname = new StringBuilder();
            for (int i = 2; i < args.length; i++)
                itemname.append(" ").append(args[i]);
            itemname = new StringBuilder(itemname.substring(1));
            itemname = new StringBuilder(ChatColor.translateAlternateColorCodes('&', itemname.toString()));
            addInfiniteItemOfType(player, mat, itemname.toString());
            if (!player.equals(sender))
                Message.sendIfNotVoid(sender, InfiniteBlocksConfig.getHandedOutInfiniteBlock(mat, player, itemname.toString()));
        }
        return true;
    }

    private void addInfiniteItemOfType(Player player, Material type, String name) {
        ItemStack stack = new ItemStack(type);
        ItemMeta meta = stack.getItemMeta();
        meta.getPersistentDataContainer().set(InfiniteBlocks.getNamespacedKey(), PersistentDataType.SHORT, (short) 1);
        if (name != null) {
            meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
        }
        meta.setLore(InfiniteBlocksConfig.getLoreList(type));
        stack.setItemMeta(meta);
        if (!player.getInventory().addItem(stack).isEmpty())
            player.getWorld().dropItem(player.getLocation(), stack);
        Message.sendIfNotVoid(player, InfiniteBlocksConfig.getObtainedInfiniteBlock(type, name));
    }
}