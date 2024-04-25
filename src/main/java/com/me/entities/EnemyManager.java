package com.me.entities;

import com.me.gamestates.Playing;
import com.me.utils.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static com.me.utils.Constants.EnemyConstants.CRABBY_DRAWOFFSET_Y;
import static com.me.utils.Constants.EnemyConstants.CRABBY_HEIGHT;
import static com.me.utils.Constants.EnemyConstants.CRABBY_HEIGHT_DEFAULT;
import static com.me.utils.Constants.EnemyConstants.CRABBY_WIDTH;
import static com.me.utils.Constants.EnemyConstants.CRABBY_WIDTH_DEFAULT;

public class EnemyManager {

    private Playing playing;
    private BufferedImage[][] crabbyArray;
    private ArrayList<Crabby> crabbies = new ArrayList<>();

    public EnemyManager(Playing playing) {
        this.playing = playing;
        loadEnemyImages();
        addEnemies();
    }

    private void addEnemies() {
        crabbies = LoadSave.getCrabs();
    }

    public void update(int[][] levelData, Player player) {
        for (Crabby crabby : crabbies) {
            crabby.update(levelData, player);
        }
    }

    public void draw(Graphics graphics, int xLevelOffset) {
        drawCrabs(graphics, xLevelOffset);
    }

    private void drawCrabs(Graphics graphics, int xLevelOffset) {
        for (Crabby crabby : crabbies) {
            graphics.drawImage(crabbyArray[crabby.getEnemyState()][crabby.getAnimationIndex()], (int) crabby.getHitBox().x - xLevelOffset, (int) crabby.getHitBox().y -CRABBY_DRAWOFFSET_Y, CRABBY_WIDTH, CRABBY_HEIGHT, null);
        }
    }

    private void loadEnemyImages() {
        crabbyArray = new BufferedImage[5][9];
        BufferedImage temp = LoadSave.getSpriteAtlas(LoadSave.CRABBY_SPRITE);

        for (int j = 0; j < crabbyArray.length; j++) {
            for (int i = 0; i < crabbyArray[j].length; i++) {
                crabbyArray[j][i] = temp.getSubimage(i * CRABBY_WIDTH_DEFAULT, j * CRABBY_HEIGHT_DEFAULT, CRABBY_WIDTH_DEFAULT, CRABBY_HEIGHT_DEFAULT);
            }
        }
    }
}
