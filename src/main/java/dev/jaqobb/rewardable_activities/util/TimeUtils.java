package dev.jaqobb.rewardable_activities.util;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class TimeUtils {
    
    private static final Pattern TIME_PATTERN = Pattern.compile("(?:([0-9]+)\\s*d[a-z]*[,\\s]*)?(?:([0-9]+)\\s*h[a-z]*[,\\s]*)?(?:([0-9]+)\\s*m[a-z]*[,\\s]*)?(?:([0-9]+)\\s*(?:s[a-z]*)?)?", Pattern.CASE_INSENSITIVE);
    
    private TimeUtils() {
        throw new UnsupportedOperationException("Cannot create instance of this class");
    }
    
    public static Instant parse(String string) {
        Matcher matcher = TIME_PATTERN.matcher(string);
        long days = 0L;
        long hours = 0L;
        long minutes = 0L;
        long seconds = 0L;
        boolean found = false;
        while (matcher.find()) {
            if (matcher.group() == null || matcher.group().isEmpty()) {
                continue;
            }
            for (int index = 0; index < matcher.groupCount(); index++) {
                if (matcher.group(index) == null || matcher.group(index).isEmpty()) {
                    continue;
                }
                found = true;
                break;
            }
            if (!found) {
                continue;
            }
            if (matcher.group(1) != null && !matcher.group(1).isEmpty()) {
                days = Long.parseLong(matcher.group(1));
            }
            if (matcher.group(2) != null && !matcher.group(2).isEmpty()) {
                hours = Long.parseLong(matcher.group(2));
            }
            if (matcher.group(3) != null && !matcher.group(3).isEmpty()) {
                minutes = Long.parseLong(matcher.group(3));
            }
            if (matcher.group(4) != null && !matcher.group(4).isEmpty()) {
                seconds = Long.parseLong(matcher.group(4));
            }
            break;
        }
        if (!found) {
            return null;
        }
        return Instant.EPOCH
            .plus(days, ChronoUnit.DAYS)
            .plus(hours, ChronoUnit.HOURS)
            .plus(minutes, ChronoUnit.MINUTES)
            .plus(seconds, ChronoUnit.SECONDS);
    }
}
