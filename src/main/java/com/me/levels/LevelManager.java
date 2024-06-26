package com.me.levels;

import com.me.Game;
import com.me.utils.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

public class LevelManager {

    private Game game;
    private BufferedImage[] levelSprite;
    private Level levelOne;

    public LevelManager(Game game) {
        this.game = game;
        importOutsideSprites();
        levelOne = new Level(LoadSave.getLevelData());
    }

    public void draw(Graphics graphics, int xLevelOffset) {

        for (int j = 0; j < Game.TILES_IN_HEIGHT; j++) {
            for (int i = 0; i < levelOne.getLevelData()[0].length; i++) {
                int index = levelOne.getSpriteIndex(i, j);
                graphics.drawImage(levelSprite[index], i * Game.TILES_SIZE - xLevelOffset, j * Game.TILES_SIZE, Game.TILES_SIZE, Game.TILES_SIZE, null);
            }
        }
        graphics.drawImage(levelSprite[2], 0, 0, null);
    }

    public void update() {

    }

    public Level getCurrentLevel() {
        return levelOne;
    }

    private void importOutsideSprites() {
        BufferedImage img = LoadSave.getSpriteAtlas(LoadSave.LEVEL_ATLAS);

        levelSprite = new BufferedImage[48];

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 12; j++) {
                int index = i * 12 + j;
                levelSprite[index] = img.getSubimage(j * 32, i * 32, 32, 32);
            }
        }
    }
}
