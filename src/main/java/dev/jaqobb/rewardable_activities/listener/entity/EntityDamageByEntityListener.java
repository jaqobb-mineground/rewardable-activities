package dev.jaqobb.rewardable_activities.listener.entity;

import com.cryptomorin.xseries.XEntityType;
import dev.jaqobb.rewardable_activities.RewardableActivitiesConstants;
import dev.jaqobb.rewardable_activities.RewardableActivitiesPlugin;
import dev.jaqobb.rewardable_activities.data.RewardableActivity;
import dev.jaqobb.rewardable_activities.data.RewardableActivityReward;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.projectiles.ProjectileSource;

public class EntityDamageByEntityListener implements Listener {
    
    private final RewardableActivitiesPlugin plugin;
    
    public EntityDamageByEntityListener(RewardableActivitiesPlugin plugin) {
        this.plugin = plugin;
    }
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof LivingEntity victim)) {
            return;
        }
        Player attacker = this.getPlayer(event.getDamager());
        if (attacker == null) {
            return;
        }
        if (victim.getHealth() - event.getFinalDamage() > 0.0D) {
            return;
        }
        if (this.plugin.isEntityBreedOwnershipCheckEnabled() && this.plugin.hasMetadata(victim, RewardableActivitiesConstants.ENTITY_BRED_BY_PLAYER_KEY)) {
            return;
        }
        if (this.plugin.isEntitySpawnerOwnershipCheckEnabled() && this.plugin.hasMetadata(victim, RewardableActivitiesConstants.ENTITY_SPAWNED_BY_SPAWNER_KEY)) {
            return;
        }
        RewardableActivity activity = this.plugin.getRepository().getEntityKillActivity(XEntityType.of(victim));
        if (activity == null) {
            return;
        }
        RewardableActivityReward reward = activity.getReward(attacker);
        if (reward == null) {
            return;
        }
        reward.reward(this.plugin, attacker);
    }
    
    private Player getPlayer(Entity entity) {
        if (entity instanceof Player) {
            return (Player) entity;
        }
        if (entity instanceof Projectile projectile) {
            ProjectileSource projectileSource = projectile.getShooter();
            if (!(projectileSource instanceof Player)) {
                return null;
            }
            return (Player) projectileSource;
        }
        return null;
    }
}
