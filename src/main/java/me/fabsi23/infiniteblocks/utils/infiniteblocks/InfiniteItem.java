package me.fabsi23.infiniteblocks.utils.infiniteblocks;

import me.fabsi23.infiniteblocks.InfiniteBlocks;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public class InfiniteItem {

    public static boolean isInfiniteItemOfType(ItemStack stack, Material type) {
        if (stack.getType() != type)
            return false;
        Short infinite = stack.getItemMeta().getPersistentDataContainer().get(InfiniteBlocks.getNamespacedKey(),
                PersistentDataType.SHORT);
        return infinite != null && infinite != 0;
    }
}
