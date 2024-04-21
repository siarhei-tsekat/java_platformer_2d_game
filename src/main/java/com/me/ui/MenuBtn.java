package com.me.ui;

import com.me.gamestates.GameState;
import com.me.utils.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

import static com.me.utils.Constants.UI.Buttons.B_HEIGHT;
import static com.me.utils.Constants.UI.Buttons.B_HEIGHT_DEFAULT;
import static com.me.utils.Constants.UI.Buttons.B_WIDTH;
import static com.me.utils.Constants.UI.Buttons.B_WIDTH_DEFAULT;

public class MenuBtn {

    private int xPos;
    private int yPos;
    private int rowIndex;
    private int index;
    private int xOffsetCenter = B_WIDTH / 2;
    private GameState state;
    private BufferedImage[] images;
    private boolean mouseOver, mousePressed;
    private Rectangle bounds;

    public MenuBtn(int xPos, int yPos, int rowIndex, GameState state) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.rowIndex = rowIndex;
        this.state = state;

        loadImages();
        initBounds();
    }

    private void initBounds() {
        bounds = new Rectangle(xPos - xOffsetCenter, yPos, B_WIDTH, B_HEIGHT);
    }

    private void loadImages() {
        images = new BufferedImage[3];
        BufferedImage tmp = LoadSave.getSpriteAtlas(LoadSave.MENU_BTNS);

        for (int i = 0; i < images.length; i++) {
            images[i] = tmp.getSubimage(i * B_WIDTH_DEFAULT, rowIndex * B_HEIGHT_DEFAULT, B_WIDTH_DEFAULT, B_HEIGHT_DEFAULT);
        }
    }

    public void draw(Graphics graphics) {
        graphics.drawImage(images[index], xPos - xOffsetCenter, yPos, B_WIDTH, B_HEIGHT, null);
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

    public void applyGameState() {
        GameState.state = state;
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

    public Rectangle getBounds() {
        return bounds;
    }
}
