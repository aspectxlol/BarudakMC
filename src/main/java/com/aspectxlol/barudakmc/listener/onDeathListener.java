package com.aspectxlol.barudakmc.listener;

import com.aspectxlol.barudakmc.BarudakMC;
import com.aspectxlol.barudakmc.DiscordWebhook;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class onDeathListener implements Listener {

    private final BarudakMC barudakMC;

    public onDeathListener(BarudakMC barudakMC) {
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


        assert event.getEntity().getPlayer() != null;

        ItemStack head = getHead(event.getEntity().getPlayer());
        ItemMeta headMeta = head.getItemMeta();
        assert headMeta != null;
        headMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&7" + event.getEntity().getPlayer().getName()));

        Entity e = event.getEntity().getKiller();
        if(e != null) {
            List<String> Lore = new ArrayList<>();
            Lore.add(ChatColor.translateAlternateColorCodes('&', "Was Killed By &6" + event.getEntity().getKiller().getDisplayName()));
            headMeta.setLore(Lore);
        }
        head.setItemMeta(headMeta);

        event.getDrops().add(head);
    }

    private ItemStack getHead(Player player) {
        ItemStack item = new ItemStack(Material.PLAYER_HEAD, 1);
        SkullMeta skull = (SkullMeta) item.getItemMeta();
        assert skull != null;
        skull.setDisplayName(player.getName());
        skull.setOwningPlayer(Bukkit.getOfflinePlayer(player.getUniqueId()));
        item.setItemMeta(skull);
        return item;
    }

}
