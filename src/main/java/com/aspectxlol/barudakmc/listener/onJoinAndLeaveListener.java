package com.aspectxlol.barudakmc.listener;

import com.aspectxlol.barudakmc.DiscordWebhook;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.awt.*;
import java.io.IOException;

public class onJoinAndLeaveListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        DiscordWebhook discordWebhook = new DiscordWebhook("https://discord.com/api/webhooks/1256894119876362261/3wtZqGIU7w69j-7vgzrP_o-jg724LeGgdHMSYaq6R74zujtBlKprg4G6E4T5kifguNH3");
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
        DiscordWebhook discordWebhook = new DiscordWebhook("https://discord.com/api/webhooks/1256894119876362261/3wtZqGIU7w69j-7vgzrP_o-jg724LeGgdHMSYaq6R74zujtBlKprg4G6E4T5kifguNH3");
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
