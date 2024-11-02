package dev.jaqobb.rewardable_activities.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import java.util.LinkedList;
import java.util.List;

public class RewardableActivitiesCommandTabCompleter implements TabCompleter {
    
    @Override
    public List<String> onTabComplete(CommandSender sender, @NotNull Command command, @NotNull String label, String[] arguments) {
        if (!sender.hasPermission("rewardableactivities.command.rewardableactivities")) {
            return null;
        }
        List<String> completions = new LinkedList<>();
        if (arguments.length != 1) {
            return completions;
        }
        String argument = arguments[0].toLowerCase();
        if ("reload".startsWith(argument)) {
            completions.add("reload");
        }
        return completions;
    }
}
