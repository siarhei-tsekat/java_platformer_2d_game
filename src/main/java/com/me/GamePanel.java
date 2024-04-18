package com.me;

import com.me.inputs.KeyboardInputs;
import com.me.inputs.MouseInputs;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class GamePanel extends JPanel {

    private final MouseInputs mouseInputs;
    private float xDelta = 100, yDelta = 100;
    private BufferedImage image;

    public GamePanel() {
        mouseInputs = new MouseInputs(this);
        importImage();
        setPanelSize();
        addKeyListener(new KeyboardInputs(this));
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
    }

    private void importImage() {
        InputStream is = getClass().getResourceAsStream("/player_sprites.png");
        try {
            image = ImageIO.read(is);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void setPanelSize() {
        Dimension size = new Dimension(1280, 800); // image size 32 so 1280 / 32 = 40 and 800 / 32 = 25
        setMinimumSize(size);
        setPreferredSize(size);
        setMaximumSize(size);
    }

    public void changeXDelta(int v) {
        xDelta += v;
    }

    public void changeYDelta(int v) {
        yDelta += v;
    }

    public void setRectPos(int x, int y) {
        xDelta = x;
        yDelta = y;
    }

    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        graphics.drawImage(image.getSubimage(0, 0, 64, 40), 0, 0, null);
    }

}
