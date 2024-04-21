package com.me.ui;

import com.me.utils.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

import static com.me.utils.Constants.UI.VolumeBtns.SLIDER_DEFAULT_WIDTH;
import static com.me.utils.Constants.UI.VolumeBtns.VOLUME_DEFAULT_HEIGHT;
import static com.me.utils.Constants.UI.VolumeBtns.VOLUME_DEFAULT_WIDTH;
import static com.me.utils.Constants.UI.VolumeBtns.VOLUME_WIDTH;

public class VolumeBtn extends PauseButton {
    private BufferedImage[] images;
    private BufferedImage slider;
    private int index = 0;
    private boolean mouseOver, mousePressed;
    private int buttonX, minX, maxX;

    public VolumeBtn(int x, int y, int width, int height) {
        super(x + width / 2, y, VOLUME_WIDTH, height);
        bounds.x -= VOLUME_WIDTH / 2;
        buttonX = x + width / 2;
        this.x = x;
        this.width = width;
        minX = x + VOLUME_WIDTH / 2;
        maxX = x + width - VOLUME_WIDTH / 2;
        loadImages();
    }

    private void loadImages() {
        BufferedImage tmp = LoadSave.getSpriteAtlas(LoadSave.VOLUME_BTNS);
        images = new BufferedImage[3];
        for (int i = 0; i < images.length; i++) {
            images[i] = tmp.getSubimage(i * VOLUME_DEFAULT_WIDTH, 0, VOLUME_DEFAULT_WIDTH, VOLUME_DEFAULT_HEIGHT);
        }

        slider = tmp.getSubimage(3 * VOLUME_DEFAULT_WIDTH, 0, SLIDER_DEFAULT_WIDTH, VOLUME_DEFAULT_HEIGHT);
    }

    public void update() {
        index = 0;
        if (mouseOver) {
            index = 1;
        }
        if (mousePressed) {
            index = 2;
        }
    }

    public void draw(Graphics graphics) {
        graphics.drawImage(slider, x, y, width, height, null);
        graphics.drawImage(images[index], buttonX - VOLUME_WIDTH / 2, y, VOLUME_WIDTH, height, null);
    }

    public void changeX(int x) {
        if (x < minX) {
            buttonX = minX;
        } else if (x > maxX) {
            buttonX = maxX;
        } else {
            buttonX = x;
        }
        bounds.x = buttonX - VOLUME_WIDTH / 2;
    }

    public void resetBooleans() {
        mouseOver = false;
        mousePressed = false;
    }

    public boolean isMouseOver() {
        return mouseOver;
    }

    public void setMouseOver(boolean mouseOver) {
        this.mouseOver = mouseOver;
    }

    public boolean isMousePressed() {
        return mousePressed;
    }

    public void setMousePressed(boolean mousePressed) {
        this.mousePressed = mousePressed;
    }
}
