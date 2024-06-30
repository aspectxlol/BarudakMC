package com.aspectxlol.barudakmc.listener;

import com.aspectxlol.barudakmc.BarudakMC;
import com.aspectxlol.barudakmc.DiscordWebhook;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.io.IOException;

public class onDeathListener implements Listener {

    private BarudakMC barudakMC;

    onDeathListener(BarudakMC barudakMC) {
        this.barudakMC = barudakMC;
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        DiscordWebhook discordWebhook = new DiscordWebhook(this.barudakMC.getUrl());
        discordWebhook.setUsername(event.getEntity().getDisplayName());
        discordWebhook.setAvatarUrl("https://api.tydiumcraft.net/v1/players/skin?uuid=" + event.getEntity().getUniqueId() + "&type=avatar");

        DiscordWebhook.EmbedObject DeathEmbed = new DiscordWebhook.EmbedObject();
        DeathEmbed.setTitle(event.getDeathMessage());

        discordWebhook.addEmbed(DeathEmbed);

        try {
            discordWebhook.execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
