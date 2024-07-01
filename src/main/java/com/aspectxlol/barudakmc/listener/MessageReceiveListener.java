package com.aspectxlol.barudakmc.listener;

import com.aspectxlol.barudakmc.BarudakMC;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.ChatColor;

public class MessageReceiveListener extends ListenerAdapter {
    BarudakMC barudakMC;

    public MessageReceiveListener(BarudakMC barudakMC) {
        this.barudakMC = barudakMC;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        System.out.printf("[%s] %#s: %s\n",
                event.getChannel(),
                event.getAuthor(),
                event.getMessage().getContentDisplay());

        if (event.getChannel().getId().equals(this.barudakMC.getServerChatChannel())) {
            this.barudakMC.getServer().broadcastMessage("[" + ChatColor.BLUE + "Discord" + ChatColor.WHITE + "]" + "[" + ChatColor.RED + event.getAuthor().getName() + ChatColor.WHITE + "] " + event.getMessage().getContentDisplay());
        }
    }
}