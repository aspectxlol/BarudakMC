package com.aspectxlol.barudakmc;

import com.aspectxlol.barudakmc.listener.onDeathListener;
import com.aspectxlol.barudakmc.listener.onJoinAndLeaveListener;
import com.aspectxlol.barudakmc.listener.onMessageListener;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public final class BarudakMC extends JavaPlugin {

    FileConfiguration config = getConfig();

    BarudakMC getInstance() {
        return this;
    }

    public String getUrl() {
        if (isDiscordWebhookUri(Objects.requireNonNull(config.getString("webhookUri")))) return config.getString("webhookUri");
        System.out.println("[BarudakMC] Invalid webhook uri / disabling Plugin");
        getServer().getPluginManager().disablePlugin(this);
        return "";
    }

    @Override
    public void onEnable() {
        System.out.println("[BarudakMC] Plugin Started");

        getServer().getPluginManager().registerEvents(new onMessageListener(getInstance()), this);
        getServer().getPluginManager().registerEvents(new onJoinAndLeaveListener(getInstance()), this);
        getServer().getPluginManager().registerEvents(new onDeathListener(getInstance()), this);

        config.addDefault("webhookUri", "Replace with your webhook url");
        config.options().copyDefaults(true);
        this.saveDefaultConfig();
        saveConfig();

        DiscordWebhook discordWebhook = new DiscordWebhook(getUrl());
        discordWebhook.setUsername("Server");

        DiscordWebhook.EmbedObject StartEmbed = new DiscordWebhook.EmbedObject();
        StartEmbed.setTitle("Server is Starting");
        StartEmbed.setColor(Color.GREEN);

        discordWebhook.addEmbed(StartEmbed);

        try {
            discordWebhook.execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onDisable() {
        System.out.println("[BarudakMC] Plugin Stopped");

        DiscordWebhook discordWebhook = new DiscordWebhook(getUrl());
        discordWebhook.setUsername("Server");

        DiscordWebhook.EmbedObject StopEmbed = new DiscordWebhook.EmbedObject();
        StopEmbed.setTitle("Server is Stopping");
        StopEmbed.setColor(Color.RED);

        discordWebhook.addEmbed(StopEmbed);

        try {
            discordWebhook.execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean isDiscordWebhookUri(String url) {
        // Basic structure of a Discord webhook URL:
        // https://discord.com/api/webhooks/<guild_id>/<webhook_token>
        String[] parts = url.split("/");
        if (parts.length != 6) {
            return false;
        }
        if (!parts[0].toLowerCase().equals("https:")) {
            return false;
        }
        if (!parts[1].equals("discord.com")) {
            return false;
        }
        if (!parts[2].equals("api")) {
            return false;
        }
        if (!parts[3].equals("webhooks")) {
            return false;
        }
        try {
            // Check if the guild ID and webhook token are integers
            Integer.parseInt(parts[4]);
            Integer.parseInt(parts[5]);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
}
