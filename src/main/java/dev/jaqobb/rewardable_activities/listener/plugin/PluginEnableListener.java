package dev.jaqobb.rewardable_activities.listener.plugin;

import dev.jaqobb.rewardable_activities.RewardableActivitiesPlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginEnableEvent;
import java.util.logging.Level;

public class PluginEnableListener implements Listener {
    
    private final RewardableActivitiesPlugin plugin;
    
    public PluginEnableListener(RewardableActivitiesPlugin plugin) {
        this.plugin = plugin;
    }
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPluginEnable(PluginEnableEvent event) {
        if (!event.getPlugin().getName().equals("PlaceholderAPI")) {
            return;
        }
        this.plugin.setPlaceholderApiPresent(true);
        this.plugin.getLogger().log(Level.INFO, "PlaceholderAPI integration has been enabled.");
    }
}
