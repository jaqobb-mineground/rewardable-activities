package dev.jaqobb.rewardable_activities.listener.player;

import dev.jaqobb.rewardable_activities.RewardableActivitiesPlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {
    
    private final RewardableActivitiesPlugin plugin;
    
    public PlayerJoinListener(RewardableActivitiesPlugin plugin) {
        this.plugin = plugin;
    }
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (!player.hasPermission("rewardableactivities.updater.notify")) {
            return;
        }
        player.sendMessage(this.plugin.getUpdater().getUpdateMessage());
    }
}
