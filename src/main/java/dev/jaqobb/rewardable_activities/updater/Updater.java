package dev.jaqobb.rewardable_activities.updater;

import dev.jaqobb.rewardable_activities.RewardableActivitiesConstants;
import dev.jaqobb.rewardable_activities.RewardableActivitiesPlugin;
import net.md_5.bungee.api.ChatColor;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import javax.net.ssl.HttpsURLConnection;

public class Updater implements Runnable {
    
    private final RewardableActivitiesPlugin plugin;
    private final int pluginId;
    private final String currentVersion;
    private String latestVersion;
    private Integer versionDifference;
    
    public Updater(RewardableActivitiesPlugin plugin, int pluginId) {
        this.plugin = plugin;
        this.pluginId = pluginId;
        this.currentVersion = this.plugin.getDescription().getVersion();
        this.latestVersion = null;
        this.versionDifference = null;
    }
    
    public String getCurrentVersion() {
        return this.currentVersion;
    }
    
    public String getLatestVersion() {
        return this.latestVersion;
    }
    
    public Integer getVersionDifference() {
        return this.versionDifference;
    }
    
    public String getUpdateMessage() {
        if (this.currentVersion.contains("-SNAPSHOT")) {
            return RewardableActivitiesConstants.PREFIX + ChatColor.RED + "You are running a development version of " + ChatColor.WHITE + "Rewardable Activities" + ChatColor.RED + " (" + ChatColor.WHITE + this.currentVersion + ChatColor.RED + "). Development versions may be unstable. As such, please avoid running them on production servers.";
        }
        if (this.latestVersion == null || this.versionDifference == null) {
            return RewardableActivitiesConstants.PREFIX + ChatColor.RED + "Could not retrieve the latest version data of " + ChatColor.WHITE + "Rewardable Activities" + ChatColor.RED + ".";
        }
        if (this.versionDifference < 0) {
            return RewardableActivitiesConstants.PREFIX + ChatColor.WHITE + "You are running an outdated version of " + ChatColor.GRAY + "Rewardable Activities" + ChatColor.WHITE + " (" + ChatColor.GRAY + this.currentVersion + ChatColor.WHITE + " < " + ChatColor.GRAY + this.latestVersion + ChatColor.WHITE + "). Consider updating to receive new features, bug fixes, and more.";
        }
        if (this.versionDifference > 0) {
            return RewardableActivitiesConstants.PREFIX + ChatColor.WHITE + "You are running a future version of " + ChatColor.GRAY + "Rewardable Activities" + ChatColor.WHITE + " (" + ChatColor.GRAY + this.currentVersion + ChatColor.WHITE + " > " + ChatColor.GRAY + this.latestVersion + ChatColor.WHITE + "). I suppose you are a time traveler.";
        }
        return RewardableActivitiesConstants.PREFIX + ChatColor.WHITE + "You are running the latest version of " + ChatColor.GRAY + "Rewardable Activities" + ChatColor.WHITE + ".";
    }
    
    @Override
    public void run() {
        if (this.currentVersion.contains("-SNAPSHOT")) {
            return;
        }
        try {
            HttpsURLConnection connection = (HttpsURLConnection) URL.of(URI.create("https://api.spigotmc.org/legacy/update.php?resource=" + this.pluginId), null).openConnection();
            connection.setRequestMethod("GET");
            try (InputStream input = connection.getInputStream(); BufferedReader reader = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8))) {
                this.latestVersion = reader.readLine();
                String[] currentVersionData = this.currentVersion.split("\\.");
                String[] latestVersionData = this.latestVersion.split("\\.");
                if (currentVersionData.length != 3 || latestVersionData.length != 3) {
                    return;
                }
                int majorDifference = Integer.compare(Integer.parseInt(currentVersionData[0]), Integer.parseInt(latestVersionData[0]));
                if (majorDifference != 0) {
                    this.versionDifference = majorDifference;
                    return;
                }
                int minorDifference = Integer.compare(Integer.parseInt(currentVersionData[1]), Integer.parseInt(latestVersionData[1]));
                if (minorDifference != 0) {
                    this.versionDifference = minorDifference;
                    return;
                }
                this.versionDifference = Integer.compare(Integer.parseInt(currentVersionData[2]), Integer.parseInt(latestVersionData[2]));
            } finally {
                connection.disconnect();
            }
        } catch (Exception exception) {
            this.plugin.getLogger().log(Level.WARNING, "Could not retrieve the latest version data.", exception);
        }
    }
}
