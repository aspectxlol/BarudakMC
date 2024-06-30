package com.aspectxlol.barudakmc;

import com.aspectxlol.barudakmc.listener.onMessageListener;
import org.bukkit.plugin.java.JavaPlugin;

import java.awt.*;
import java.io.IOException;

public final class BarudakMC extends JavaPlugin {

    @Override
    public void onEnable() {
        System.out.println("[BarudakMC] Plugin Started");
        getServer().getPluginManager().registerEvents(new onMessageListener(), this);

        DiscordWebhook discordWebhook = new DiscordWebhook("https://discord.com/api/webhooks/1256894119876362261/3wtZqGIU7w69j-7vgzrP_o-jg724LeGgdHMSYaq6R74zujtBlKprg4G6E4T5kifguNH3");
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

        DiscordWebhook discordWebhook = new DiscordWebhook("https://discord.com/api/webhooks/1256894119876362261/3wtZqGIU7w69j-7vgzrP_o-jg724LeGgdHMSYaq6R74zujtBlKprg4G6E4T5kifguNH3");
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
}
