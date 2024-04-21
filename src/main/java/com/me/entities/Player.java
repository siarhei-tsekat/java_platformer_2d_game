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
    private boolean left, up, right, down, jump;

    private float playerSpeed = 1.0f * Game.SCALE;

    private int[][] levelData;
    private float xDrawOffset = 21 * Game.SCALE;
    private float yDrawOffset = 4 * Game.SCALE;

    // jumping / Gravity
    private float airSpeed = 0f;
    private float gravity = 0.04f * Game.SCALE;
    private float jumpSpeed = -2.25f * Game.SCALE;
    private float fallSpeedAfterCollision = 0.5f * Game.SCALE;
    private boolean inAir = false;

    public Player(float x, float y, int width, int height) {
        super(x, y, width, height);
        loadAnimations();
        initHitBox(x, y, (int) (20 * Game.SCALE), (int) (27 * Game.SCALE));

    }

    public void update() {
        updatePosition();
        updateAnimationTick();
        setAnimation();
    }

    public void render(Graphics graphics) {
        graphics.drawImage(animations[playerAction][animationIndex], (int) (hitBox.x - xDrawOffset), (int) (hitBox.y - yDrawOffset), width, height, null);
//        drawHitBox(graphics);
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
        if (!HelpMethods.isEntityOnFloor(hitBox, levelData)) {
            inAir = true;
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
        if (jump) {
            jump();
        }
        if (!left && !right && !inAir) {
            return;
        }

        float xSpeed = 0;

        if (left) {
            xSpeed -= playerSpeed;
        }
        if (right) {
            xSpeed += playerSpeed;
        }

        if (!inAir) {
            if (!HelpMethods.isEntityOnFloor(hitBox, levelData)) {
                inAir = true;
            }
        }

        if (inAir) {
            if (HelpMethods.canMoveHere(hitBox.x, hitBox.y + airSpeed, hitBox.width, hitBox.height, levelData)) {
                hitBox.y += airSpeed;
                airSpeed += gravity;
                updateXPos(xSpeed);
            } else {
                hitBox.y = HelpMethods.getEntityYPosUnderRoofOrAboveFloor(hitBox, airSpeed);
                if (airSpeed > 0) {
                    resetInAir();
                } else {
                    airSpeed = fallSpeedAfterCollision;
                }
                updateXPos(xSpeed);
            }
        } else {
            updateXPos(xSpeed);
        }

        moving = true;
    }

    private void jump() {
        if (inAir) {
            return;
        }
        inAir = true;
        airSpeed = jumpSpeed;
    }

    private void resetInAir() {
        inAir = false;
        airSpeed = 0;
    }

    private void updateXPos(float xSpeed) {
        if (HelpMethods.canMoveHere(hitBox.x + xSpeed, hitBox.y, hitBox.width, hitBox.height, levelData)) {
            hitBox.x += xSpeed;
        } else {
            hitBox.x = HelpMethods.getEntityXPosNextToWall(hitBox, xSpeed);
        }
    }

    private void setAnimation() {
        int startAnimation = playerAction;

        if (moving) {
            playerAction = Constants.PlayerConstants.RUNNING;
        } else {
            playerAction = Constants.PlayerConstants.IDLE;
        }

        if (inAir) {
            if (airSpeed < 0) {
                playerAction = Constants.PlayerConstants.JUMPING;
            } else {
                playerAction = Constants.PlayerConstants.FALLING;
            }
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

    public void setJump(boolean v) {
        this.jump = v;
    }
}
