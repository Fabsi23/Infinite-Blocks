package me.fabsi23.infiniteblocks.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import me.fabsi23.infiniteblocks.InfiniteBlocks;

public class BlockPlaceListener implements Listener {

	@EventHandler(priority = EventPriority.LOWEST)
	public void onBlockPlace(BlockPlaceEvent e) {
		if (e.isCancelled())
			return;
		Player player = e.getPlayer();
		Material type = e.getBlock().getType();

		EntityEquipment equipment = player.getEquipment();
		ItemStack main = equipment.getItemInMainHand();
		ItemStack off = equipment.getItemInOffHand();

		if (isInfiniteItemOfType(main, type)) {
			player.getInventory().setItemInMainHand(main);
		} else if (isInfiniteItemOfType(off, type)) {
			player.getInventory().setItemInOffHand(off);
		}
	}

	private boolean isInfiniteItemOfType(ItemStack stack, Material type) {
		if (stack.getType() != type)
			return false;
		InfiniteBlocks.getReference();
		Short infinite = stack.getItemMeta().getPersistentDataContainer().get(InfiniteBlocks.getNamespacedKey(),
				PersistentDataType.SHORT);
		if (infinite == null || infinite == 0)
			return false;
		return true;
	}
}
