package dev.jaqobb.rewardable_activities.data;

import dev.jaqobb.rewardable_activities.RewardableActivitiesPlugin;
import dev.jaqobb.rewardable_activities.util.RandomUtils;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import java.time.Instant;
import java.util.Collection;
import java.util.Collections;

public record RewardableActivityReward(String group, double chance, double minimumEconomy, double maximumEconomy, Collection<String> commands) {
    
    public RewardableActivityReward {
        commands = Collections.unmodifiableCollection(commands);
    }
    
    public boolean testChance() {
        return RandomUtils.chance(this.chance);
    }
    
    public double getRandomEconomy() {
        return RandomUtils.getRandomDouble(this.minimumEconomy, this.maximumEconomy);
    }
    
    public void depositEconomy(Economy economy, Player player, double amount) {
        economy.depositPlayer(player, amount);
    }
    
    @Override
    public Collection<String> commands() {
        return Collections.unmodifiableCollection(this.commands);
    }
    
    public void executeCommands(RewardableActivitiesPlugin plugin, Player player) {
        this.commands.forEach(command -> {
            command = command.replace("{player}", player.getName()).replace("{group}", this.group);
            if (plugin.isPlaceholderApiPresent()) {
                command = me.clip.placeholderapi.PlaceholderAPI.setPlaceholders(player, command);
            }
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
        });
    }
    
    public void reward(RewardableActivitiesPlugin plugin, Player player) {
        if (plugin.isRewardLimiterEnabled() && !player.hasPermission("rewardableactivities.rewardlimiter.bypass")) {
            RewardLimiterData data = plugin.getRewardLimiterData(player.getUniqueId());
            if (data == null) {
                data = new RewardLimiterData();
                plugin.setRewardLimiterData(player.getUniqueId(), data);
            }
            if (data.getRewardsReceived() >= plugin.getRewardLimiterLimit() && data.getRewardsReceivedResetTime() != null && data.getRewardsReceivedResetTime().isAfter(Instant.now())) {
                if (!data.isMessageSent()) {
                    player.sendMessage(plugin.getRewardLimiterLimitReachedMessage());
                    data.setMessageSent(true);
                }
                return;
            }
            if (data.getRewardsReceivedResetTime() == null || data.getRewardsReceivedResetTime().isBefore(Instant.now())) {
                data.setRewardsReceived(0);
                data.setRewardsReceivedResetTime(Instant.now().plusMillis(plugin.getRewardLimiterCooldown().toEpochMilli()));
                data.setMessageSent(false);
            }
            data.setRewardsReceived(data.getRewardsReceived() + 1);
        }
        if (plugin.getEconomy() != null && this.minimumEconomy >= 0.0D && this.maximumEconomy > 0.0D && this.minimumEconomy <= this.maximumEconomy) {
            double economy = this.getRandomEconomy();
            if (economy > 0.0D) {
                this.depositEconomy(plugin.getEconomy(), player, economy);
            }
        }
        this.executeCommands(plugin, player);
    }
}
