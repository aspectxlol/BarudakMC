package com.aspectxlol.barudakmc.listener;

import com.aspectxlol.barudakmc.BarudakMC;
import com.aspectxlol.barudakmc.DiscordWebhook;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import java.io.IOException;

public class onMessageListener implements Listener {
    private final BarudakMC barudakMC;

    public onMessageListener(BarudakMC barudakMC) {
        this.barudakMC = barudakMC;
    }

    @EventHandler
    public void onMessage(AsyncPlayerChatEvent event) {
        DiscordWebhook discordWebhook = new DiscordWebhook(this.barudakMC.getUrl());
        discordWebhook.setUsername(event.getPlayer().getDisplayName());
        discordWebhook.setAvatarUrl("https://api.tydiumcraft.net/v1/players/skin?uuid=" + event.getPlayer().getUniqueId() + "&type=avatar");
        discordWebhook.setContent(event.getMessage());

        try {
            discordWebhook.execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
