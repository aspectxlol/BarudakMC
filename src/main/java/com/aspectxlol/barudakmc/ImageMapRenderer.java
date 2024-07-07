package com.aspectxlol.barudakmc;

import org.bukkit.entity.Player;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapPalette;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;
import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class ImageMapRenderer extends MapRenderer {

    private final String url;

    public ImageMapRenderer(String url) {
        this.url = url;
    }

    @Override
    public void render(@NotNull MapView mapView, @NotNull MapCanvas mapCanvas, @NotNull Player player) {
        try {
            URL url = new URL(this.url);
            BufferedImage image = ImageIO.read(url);
            mapCanvas.drawImage(0, 0, MapPalette.resizeImage(image));
        } catch (IOException e) {
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
        }
    }
}
