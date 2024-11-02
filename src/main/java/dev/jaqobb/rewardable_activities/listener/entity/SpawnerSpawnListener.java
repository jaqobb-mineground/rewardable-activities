package dev.jaqobb.rewardable_activities.listener.entity;

import dev.jaqobb.rewardable_activities.RewardableActivitiesConstants;
import dev.jaqobb.rewardable_activities.RewardableActivitiesPlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.SpawnerSpawnEvent;

public class SpawnerSpawnListener implements Listener {
    
    private final RewardableActivitiesPlugin plugin;
    
    public SpawnerSpawnListener(RewardableActivitiesPlugin plugin) {
        this.plugin = plugin;
    }
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void onEntitySpawn(SpawnerSpawnEvent event) {
        if (!this.plugin.isEntitySpawnerOwnershipCheckEnabled()) {
            return;
        }
        this.plugin.setMetadata(event.getEntity(), RewardableActivitiesConstants.ENTITY_SPAWNED_BY_SPAWNER_KEY, true);
    }
}
