package com.me.entities;

import com.me.Game;
import com.me.utils.Constants;
import com.me.utils.HelpMethods;
import com.me.utils.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Entity {

    private BufferedImage[][] animations;

    private int animationTick, animationIndex, animationSpeed = 15; // 120 frames / 8 times per second
    private int playerAction = Constants.PlayerConstants.IDLE;
    private boolean moving = false, attacking = false;
    private boolean left, up, right, down;

    private float playerSpeed = 2.0f;

    private int[][] levelData;
    private float xDrawOffset = 21 * Game.SCALE;
    private float yDrawOffset = 4 * Game.SCALE;

    public Player(float x, float y, int width, int height) {
        super(x, y, width, height);
        loadAnimations();
        initHitBox(x, y, 20 * Game.SCALE, 28 * Game.SCALE);
    }

    public void update() {
        updatePosition();
        updateAnimationTick();
        setAnimation();
    }

    public void render(Graphics graphics) {
        graphics.drawImage(animations[playerAction][animationIndex], (int) (hitBox.x - xDrawOffset), (int)(hitBox.y - yDrawOffset), width, height, null);
        drawHitBox(graphics);
    }

    private void loadAnimations() {
        BufferedImage image = LoadSave.getSpriteAtlas(LoadSave.PLAYER_ATLAS);
        animations = new BufferedImage[9][6];

        for (int i = 0; i < animations.length; i++) {
            for (int j = 0; j < animations[i].length; j++) {
                animations[i][j] = image.getSubimage(j * 64, i * 40, 64, 40);
            }
        }
    }

    public void loadLevelData(int[][] levelData) {
        this.levelData = levelData;

    }

    private void updateAnimationTick() {
        animationTick++;

        if (animationTick >= animationSpeed) {
            animationTick = 0;
            animationIndex++;
            if (animationIndex >= Constants.PlayerConstants.getSpriteAmount(playerAction)) {
                animationIndex = 0;
                attacking = false;
            }
        }
    }

    private void updatePosition() {

        moving = false;
        if (!left && !right && !up && !down) {
            return;
        }

        float xSpeed = 0;
        float ySpeed = 0;


        if (left && !right) {
            xSpeed = -playerSpeed;
        } else if (right && !left) {
            xSpeed = playerSpeed;
        }

        if (up && !down) {
            ySpeed = -playerSpeed;
        } else if (down && !up) {
            ySpeed = playerSpeed;
        }

        if (HelpMethods.canMoveHere(hitBox.x + xSpeed, hitBox.y + ySpeed, hitBox.width, hitBox.height, levelData)) {
            hitBox.x += xSpeed;
            hitBox.y += ySpeed;
            moving = true;
        }
    }

    private void setAnimation() {
        int startAnimation = playerAction;

        if (moving) {
            playerAction = Constants.PlayerConstants.RUNNING;
        } else {
            playerAction = Constants.PlayerConstants.IDLE;
        }

        if (attacking) {
            playerAction = Constants.PlayerConstants.ATTACK_1;
        }

        if (startAnimation != playerAction) {
            resetAnyTick();
        }
    }

    private void resetAnyTick() {
        animationTick = 0;
        animationIndex = 0;
    }

    public void resetDirBooleans() {
        left = false;
        right = false;
        up = false;
        down = false;
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public void setAttacking(boolean attacking) {
        this.attacking = attacking;
    }
}
