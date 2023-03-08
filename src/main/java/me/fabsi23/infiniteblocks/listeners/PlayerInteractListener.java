package me.fabsi23.infiniteblocks.listeners;

import me.fabsi23.infiniteblocks.InfiniteBlocks;
import me.fabsi23.infiniteblocks.utils.infiniteblocks.InfiniteItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Result;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;

public class PlayerInteractListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerInteract(PlayerInteractEvent e) {
        if (e.getAction() != Action.RIGHT_CLICK_AIR && e.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if (e.useItemInHand() == Result.DENY) return;
        if (!e.hasItem()) return;
        Player player = e.getPlayer();

        Material type = e.getItem().getType();

        EntityEquipment equipment = player.getEquipment();
        ItemStack main = equipment.getItemInMainHand().clone();
        ItemStack off = equipment.getItemInOffHand().clone();

        if (InfiniteItem.isInfiniteItemOfType(main, type)) {
            setItemWithDelay(player, main, true);
        } else if (InfiniteItem.isInfiniteItemOfType(off, type)) {
            setItemWithDelay(player, off, false);
        }
    }

    public void setItemWithDelay(Player player, ItemStack stack, boolean mainhand) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(InfiniteBlocks.getReference(), () -> {
            if (!player.isValid()) return;
            if (mainhand) {
                player.getInventory().setItemInMainHand(stack);
            } else {
                player.getInventory().setItemInOffHand(stack);
            }
        }, 0L);
    }
}
