package me.fabsi23.infiniteblocks.externalApis;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;

public class Vault {
    private final Economy economy;

    public Vault(Plugin plugin) {
        economy = setupEconomy(plugin);
    }

    private Economy setupEconomy(Plugin plugin) {
        if (plugin.getServer().getPluginManager().getPlugin("Vault") == null) {
            return null;
        }
        RegisteredServiceProvider<Economy> rsp = plugin.getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return null;
        }
        return rsp.getProvider();
    }

    public boolean ignoresDeduction(Player player) {
        return player.hasPermission("infiniteblocks.ignoreCost");
    }

    public boolean deductBalance(Player player, double amount) {
        if (amount <= 0) return true;
        EconomyResponse response = economy.withdrawPlayer(player, amount);
        return response.transactionSuccess();
    }

    public boolean isAvailable() {
        return economy != null;
    }
}
