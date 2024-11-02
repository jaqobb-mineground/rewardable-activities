package dev.jaqobb.rewardable_activities.listener.entity;

import com.cryptomorin.xseries.XEntityType;
import dev.jaqobb.rewardable_activities.RewardableActivitiesConstants;
import dev.jaqobb.rewardable_activities.RewardableActivitiesPlugin;
import dev.jaqobb.rewardable_activities.data.RewardableActivity;
import dev.jaqobb.rewardable_activities.data.RewardableActivityReward;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityBreedEvent;

public class EntityBreedListener implements Listener {
    
    private final RewardableActivitiesPlugin plugin;
    
    public EntityBreedListener(RewardableActivitiesPlugin plugin) {
        this.plugin = plugin;
    }
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void onEntityBreed(EntityBreedEvent event) {
        Entity breeder = event.getBreeder();
        if (breeder == null) {
            return;
        }
        if (!(breeder instanceof Player breederPlayer)) {
            return;
        }
        Entity child = event.getEntity();
        if (this.plugin.isEntityBreedOwnershipCheckEnabled()) {
            this.plugin.setMetadata(child, RewardableActivitiesConstants.ENTITY_BRED_BY_PLAYER_KEY, true);
        }
        RewardableActivity activity = this.plugin.getRepository().getEntityBreedActivity(XEntityType.of(child));
        if (activity == null) {
            return;
        }
        RewardableActivityReward reward = activity.getReward(breederPlayer);
        if (reward == null) {
            return;
        }
        reward.reward(this.plugin, breederPlayer);
    }
}
