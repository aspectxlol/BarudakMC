package com.aspectxlol.barudakmc.commands;

import com.aspectxlol.barudakmc.ImageMapRenderer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.map.MapView;
import org.jetbrains.annotations.NotNull;

public class MapCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String string, @NotNull String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;

            String imageURL;
            if(args.length > 0){
                imageURL = args[0];
            }else{
                player.sendMessage("An image URL was not provided, using the default Mr. Dewey image.");
                //if not, use the default image
                imageURL = "https://media.discordapp.net/attachments/562679683364159488/929026632092647434/20220107_085603.jpg";
            }
            ItemStack itemStack = new ItemStack(Material.FILLED_MAP);
            MapMeta mapMeta = (MapMeta) itemStack.getItemMeta();
            MapView mapView = Bukkit.createMap(player.getWorld());
            mapView.getRenderers().clear();
            ImageMapRenderer renderer = new ImageMapRenderer(imageURL);
            mapView.addRenderer(renderer);
            assert mapMeta != null;
            mapMeta.setMapView(mapView);
            itemStack.setItemMeta(mapMeta);
            player.getInventory().addItem(itemStack);

            player.sendMessage("Map created and given!");
        }
        return true;
    }
}
