package com.me.ui;

import com.me.utils.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

import static com.me.utils.Constants.UI.PauseBtns.SOUND_SIZE_DEFAULT;

public class SoundButton extends PauseButton {

    private BufferedImage[][] soundImages;
    private boolean mouseOver, mousePressed;
    private boolean muted;
    private int rowIndex, columIndex;

    public SoundButton(int x, int y, int width, int height) {
        super(x, y, width, height);
        loadImages();
    }

    private void loadImages() {
        BufferedImage tmp = LoadSave.getSpriteAtlas(LoadSave.SOUND_BTNS);
        soundImages = new BufferedImage[2][3];

        for (int j = 0; j < soundImages.length; j++) {
            for (int i = 0; i < soundImages[j].length; i++) {
                soundImages[j][i] = tmp.getSubimage(i * SOUND_SIZE_DEFAULT, j * SOUND_SIZE_DEFAULT, SOUND_SIZE_DEFAULT, SOUND_SIZE_DEFAULT);
            }
        }
    }

    public void update() {
        if (muted) {
            rowIndex = 1;
        } else {
            rowIndex = 0;
        }

        columIndex = 0;

        if (mouseOver) {
            columIndex = 1;
        }

        if (mousePressed) {
            columIndex = 2;
        }
    }

    public void resetBooleans() {
        mouseOver = false;
        mousePressed = false;
    }

    public void draw(Graphics graphics) {
        graphics.drawImage(soundImages[rowIndex][columIndex], x, y, width, height, null);
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

    public boolean isMuted() {
        return muted;
    }

    public void setMuted(boolean muted) {
        this.muted = muted;
    }
}
