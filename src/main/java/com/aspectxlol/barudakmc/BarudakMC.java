package com.aspectxlol.barudakmc;

import com.aspectxlol.barudakmc.listener.onDeathListener;
import com.aspectxlol.barudakmc.listener.onJoinAndLeaveListener;
import com.aspectxlol.barudakmc.listener.onMessageListener;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;

public final class BarudakMC extends JavaPlugin {

    FileConfiguration config = getConfig();

    public String getUrl() {
        if (isDiscordWebhookUri(Objects.requireNonNull(config.getString("webhookUri"))))
            return config.getString("webhookUri");
        System.out.println("[BarudakMC] Invalid webhook uri");
//        getServer().getPluginManager().disablePlugin(this);
//        this.setEnabled(false);
        System.out.println("[BarudakMC]" + " " + config.getString("webhookUri"));
        return "";
    }

    @Override
    public void onEnable() {
        System.out.println("[BarudakMC] Plugin Started");

        getServer().getPluginManager().registerEvents(new onMessageListener(this), this);
        getServer().getPluginManager().registerEvents(new onJoinAndLeaveListener(this), this);
        getServer().getPluginManager().registerEvents(new onDeathListener(this), this);

        config.addDefault("webhookUri", "Replace with your webhook url");
        config.options().copyDefaults(true);
//        this.saveDefaultConfig();
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
        try {
            new URI(url);
            return true;
        } catch (URISyntaxException e) {
            return false;
        }
    }
}
