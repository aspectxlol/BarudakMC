package com.aspectxlol.barudakmc.listener;

import com.aspectxlol.barudakmc.BarudakMC;
import com.aspectxlol.barudakmc.DiscordWebhook;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.awt.*;
import java.io.IOException;

public class onJoinAndLeaveListener implements Listener {

    private BarudakMC barudakMC;

    onJoinAndLeaveListener(BarudakMC barudakMC) {
        this.barudakMC = barudakMC;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        DiscordWebhook discordWebhook = new DiscordWebhook(this.barudakMC.getUrl());
        discordWebhook.setUsername("Server");

        DiscordWebhook.EmbedObject JoinEmbed = new DiscordWebhook.EmbedObject();
        JoinEmbed.setTitle(event.getPlayer().getDisplayName() + " Joined the Server");

        discordWebhook.addEmbed(JoinEmbed);

        try {
            discordWebhook.execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @EventHandler
    public void onJoin(PlayerQuitEvent event) {
        DiscordWebhook discordWebhook = new DiscordWebhook(this.barudakMC.getUrl());
        discordWebhook.setUsername("Server");

        DiscordWebhook.EmbedObject JoinEmbed = new DiscordWebhook.EmbedObject();
        JoinEmbed.setTitle(event.getPlayer().getDisplayName() + " Left the Server");

        discordWebhook.addEmbed(JoinEmbed);

        try {
            discordWebhook.execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
