package me.fabsi23.infiniteblocks.listeners;

import me.fabsi23.infiniteblocks.InfiniteBlocks;
import me.fabsi23.infiniteblocks.config.InfiniteBlocksConfig;
import me.fabsi23.infiniteblocks.externalApis.Vault;
import me.fabsi23.infiniteblocks.utils.Message;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class BlockPlaceListener implements Listener {

    private final Vault vault;

    public BlockPlaceListener(Vault vault) {
        this.vault = vault;
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onBlockPlace(BlockPlaceEvent e) {
        Player player = e.getPlayer();
        if (player.getGameMode() == GameMode.CREATIVE) return;
        ItemStack inHand = e.getItemInHand();
        ItemMeta meta = inHand.getItemMeta();
        if (meta == null) return;

        Short infinite = meta.getPersistentDataContainer().get(InfiniteBlocks.getNamespacedKey(), PersistentDataType.SHORT);
        if (infinite != null && infinite != 0) {
            double cost = InfiniteBlocksConfig.getMaterialCost(inHand.getType());
            if (successfullyPlaced(player, cost)) {
                if (e.getHand() == EquipmentSlot.HAND) {
                    player.getInventory().setItemInMainHand(inHand);
                } else if (e.getHand() == EquipmentSlot.OFF_HAND) {
                    player.getInventory().setItemInOffHand(inHand);
                }
            } else {
                e.setCancelled(true);
                Message.sendIfNotVoid(player, InfiniteBlocksConfig.getPlacingFailedMissingBalance(cost));
            }
        }
    }

    private boolean successfullyPlaced(Player player, double amount) {
        if (!vault.isAvailable()) return true;
        if (vault.ignoresDeduction(player)) return true;
        return vault.deductBalance(player, amount);
    }
}
