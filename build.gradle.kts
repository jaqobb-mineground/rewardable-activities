plugins {
    java
    id("net.minecrell.plugin-yml.bukkit") version "0.6.0"
    id("com.gradleup.shadow") version "8.3.4"
}

group = "dev.jaqobb"
version = "1.9.1-SNAPSHOT"
description = "Reward players upon performing certain activities"

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

bukkit {
    name = "RewardableActivities"
    main = "dev.jaqobb.rewardable_activities.RewardableActivitiesPlugin"
    version = project.version as String
    apiVersion = "1.13"
    softDepend = listOf("Vault", "PlaceholderAPI")
    description = project.description
    author = "jaqobb"
    website = "https://jaqobb.dev"
    commands {
        create("rewardable-activities") {
            description = "Rewardable Activities main command"
            aliases = listOf("rewardableactivities")
        }
    }
}

tasks {
    shadowJar {
        exclude("com/cryptomorin/xseries/messages/**")
        exclude("com/cryptomorin/xseries/particles/**")
        exclude("com/cryptomorin/xseries/unused/**")
        exclude("com/cryptomorin/xseries/NMSExtras*")
        exclude("com/cryptomorin/xseries/NoteBlockMusic*")
        exclude("com/cryptomorin/xseries/ReflectionUtils*")
        exclude("com/cryptomorin/xseries/SkullCacheListener*")
        exclude("com/cryptomorin/xseries/SkullUtils*")
        exclude("com/cryptomorin/xseries/XBiome*")
        exclude("com/cryptomorin/xseries/XBlock*")
        exclude("com/cryptomorin/xseries/XEnchantment*")
        exclude("com/cryptomorin/xseries/XItemStack*")
        exclude("com/cryptomorin/xseries/XPotion*")
        exclude("com/cryptomorin/xseries/XSound*")
        exclude("com/cryptomorin/xseries/XTag*")
        exclude("com/cryptomorin/xseries/XWorldBorder*")
        exclude("com/cryptomorin/xseries/abstractions/**")
        exclude("com/cryptomorin/xseries/profiles/**")
        exclude("com/cryptomorin/xseries/reflection/**")
        relocate("com.cryptomorin.xseries", "dev.jaqobb.rewardable_activities.library.xseries")
    }
}

repositories {
    mavenCentral()
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://oss.sonatype.org/content/repositories/snapshots/")
    maven("https://nexus.hc.to/content/repositories/pub_releases/")
    maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")
}

dependencies {
    compileOnly("org.spigotmc:spigot-api:1.21.3-R0.1-SNAPSHOT")
    compileOnly("net.milkbowl.vault:VaultAPI:1.7")
    compileOnly("me.clip:placeholderapi:2.11.6")
    implementation("com.github.cryptomorin:XSeries:11.3.0")
}
