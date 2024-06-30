package com.aspectxlol.barudakmc.listener;

import com.aspectxlol.barudakmc.DiscordWebhook;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.io.IOException;

public class onDeathListener implements Listener {

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        DiscordWebhook discordWebhook = new DiscordWebhook("https://discord.com/api/webhooks/1256894119876362261/3wtZqGIU7w69j-7vgzrP_o-jg724LeGgdHMSYaq6R74zujtBlKprg4G6E4T5kifguNH3");
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
