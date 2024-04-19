package com.me.entities;

import com.me.utils.Constants;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class Player extends Entity {

    private BufferedImage[][] animations;

    private int animationTick, animationIndex, animationSpeed = 15; // 120 frames / 8 times per second
    private int playerAction = Constants.PlayerConstants.IDLE;
    private boolean moving = false, attacking = false;
    private boolean left, up, right, down;

    private float playerSpeed = 2.0f;

    public Player(float x, float y) {
        super(x, y);
        loadAnimations();
    }

    public void update() {
        updatePosition();
        updateAnimationTick();
        setAnimation();
    }

    public void render(Graphics graphics) {
        int scale_width = 256;
        int scale_height = 160;
        graphics.drawImage(animations[playerAction][animationIndex], (int) x, (int) y, scale_width, scale_height, null);
    }

    private void loadAnimations() {

        try (InputStream is = getClass().getResourceAsStream("/player_sprites.png")) {

            BufferedImage image = ImageIO.read(is);
            animations = new BufferedImage[9][6];

            for (int i = 0; i < animations.length; i++) {
                for (int j = 0; j < animations[i].length; j++) {
                    animations[i][j] = image.getSubimage(j * 64, i * 40, 64, 40);
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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

        if (left && !right) {
            x += playerSpeed;
            moving = true;
        } else if (right && !left) {
            x -= playerSpeed;
            moving = true;
        }

        if (up && !down) {
            y -= playerSpeed;
            moving = true;
        } else if (down && !up) {
            y += playerSpeed;
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
