package com.aspectxlol.barudakmc;

import com.aspectxlol.barudakmc.commands.MapCommand;
import com.aspectxlol.barudakmc.listener.*;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;

public final class BarudakMC extends JavaPlugin {

    FileConfiguration config = getConfig();
    private JDA DiscordBot;

    public String getUrl() {
        if (isDiscordWebhookUri(Objects.requireNonNull(config.getString("webhookUri"))))
            return config.getString("webhookUri");
        System.out.println("[BarudakMC] Invalid webhook uri");
        return "";
    }

    public String getToken() {
        return config.getString("token");
    }

    public String getServerChatChannel() {
        return config.getString("serverChatID");
    }

    @Override
    public void onEnable() {
        System.out.println("[BarudakMC] Plugin Started");

        getServer().getPluginManager().registerEvents(new onMessageListener(this), this);
        getServer().getPluginManager().registerEvents(new onJoinAndLeaveListener(this), this);
        getServer().getPluginManager().registerEvents(new onDeathListener(this), this);

        config.addDefault("webhookUri", "Replace with your webhook url");
        config.addDefault("token", "Replace with your bot token");
        config.addDefault("serverChatID", "Replace with your Server Chat Channel ID");
        config.options().copyDefaults(true);
        saveConfig();

        DiscordWebhook discordWebhook = new DiscordWebhook(getUrl());
        discordWebhook.setUsername("Server");

        DiscordWebhook.EmbedObject StartEmbed = new DiscordWebhook.EmbedObject();
        StartEmbed.setTitle("Server is Starting");
        StartEmbed.setColor(Color.GREEN);

        this.getCommand("map").setExecutor(new MapCommand());

        discordWebhook.addEmbed(StartEmbed);

        try {
            discordWebhook.execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        this.DiscordBot = JDABuilder.createLight(getToken(), GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_MESSAGES, GatewayIntent.MESSAGE_CONTENT)
                .addEventListeners(new MessageReceiveListener(this))
                .addEventListeners(new ListenerAdapter() {
                    @Override
                    public void onReady(@NotNull ReadyEvent event) {
                        super.onReady(event);
                        System.out.println("[BarudakMC] Discord Bot is Ready");
                    }
                })
                .build();
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

        this.DiscordBot.shutdown();

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
