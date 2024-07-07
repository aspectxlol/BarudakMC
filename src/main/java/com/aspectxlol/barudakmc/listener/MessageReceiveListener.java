package com.aspectxlol.barudakmc.listener;

import com.aspectxlol.barudakmc.BarudakMC;
import net.dv8tion.jda.api.events.message.GenericMessageEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.ChatColor;
import org.jetbrains.annotations.NotNull;

public class MessageReceiveListener extends ListenerAdapter {
    BarudakMC barudakMC;

    public MessageReceiveListener(BarudakMC barudakMC) {
        this.barudakMC = barudakMC;
    }

    @Override
    public void onGenericMessage(@NotNull GenericMessageEvent event) {
        super.onGenericMessage(event);
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getChannel().getId().equals(this.barudakMC.getServerChatChannel()) && !(event.getAuthor().isBot())) {
            this.barudakMC.getServer().broadcastMessage("[" + ChatColor.BLUE + "Discord" + ChatColor.WHITE + "]" + "[" + ChatColor.RED + event.getAuthor().getName() + ChatColor.WHITE + "] " + event.getMessage().getContentDisplay());
        }
    }
}