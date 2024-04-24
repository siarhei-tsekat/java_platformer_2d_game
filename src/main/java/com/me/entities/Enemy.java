package com.me.entities;

import com.me.Game;
import com.me.utils.Constants;
import com.me.utils.HelpMethods;

import static com.me.utils.Constants.Directions.LEFT;
import static com.me.utils.Constants.Directions.RIGHT;
import static com.me.utils.Constants.EnemyConstants.IDLE;
import static com.me.utils.Constants.EnemyConstants.RUNNING;

public abstract class Enemy extends Entity {

    private int animationIndex, enemyState, enemyType;
    private int animationTick, animationSpeed = 25;
    private boolean firstUpdate = true;
    private boolean inAir;
    private float fallSpeed;
    private float gravity = 0.04f * Game.SCALE;
    private float walkSpeed = 0.35f * Game.SCALE;
    private int walkDirection = LEFT;

    public Enemy(float x, float y, int width, int height, int enemyType) {
        super(x, y, width, height);
        this.enemyType = enemyType;
        initHitBox(x, y, width, height);
    }

    private void updateAnimationTick() {
        animationTick++;
        if (animationTick >= animationSpeed) {
            animationTick = 0;
            animationIndex++;
            if (animationIndex >= Constants.EnemyConstants.getSpriteAmount(enemyType, enemyState)) {
                animationIndex = 0;
            }
        }
    }

    private void updateMove(int[][] levelData) {
        if (firstUpdate) {
            if (!HelpMethods.isEntityOnFloor(hitBox, levelData)) {
                inAir = true;
            }
            firstUpdate = false;
        }

        if (inAir) {
            if (HelpMethods.canMoveHere(hitBox.x, hitBox.y + fallSpeed, hitBox.width, hitBox.height, levelData)) {
                hitBox.y += fallSpeed;
                fallSpeed += gravity;
            } else {
                inAir = false;
                hitBox.y = HelpMethods.getEntityYPosUnderRoofOrAboveFloor(hitBox, fallSpeed);
            }
        } else {
            switch (enemyState) {
                case IDLE:
                    enemyState = RUNNING;
                    break;
                case RUNNING:
                    float xSpeed = 0;
                    if (walkDirection == LEFT) {
                        xSpeed = -walkSpeed;
                    } else {
                        xSpeed = walkSpeed;
                    }

                    if (HelpMethods.canMoveHere(hitBox.x + xSpeed, hitBox.y, hitBox.width, hitBox.height, levelData)) {
                        if (HelpMethods.isFloor(hitBox, xSpeed, levelData)) {
                            hitBox.x += xSpeed;
                            return;
                        }
                    }

                    changeWalkDir();
                    break;
            }
        }
    }

    private void changeWalkDir() {
        if (walkDirection == LEFT) {
            walkDirection = RIGHT;
        } else {
            walkDirection = LEFT;
        }
    }

    public void update(int[][] levelData) {
        updateMove(levelData);
        updateAnimationTick();
    }

    public int getAnimationIndex() {
        return animationIndex;
    }

    public int getEnemyState() {
        return enemyState;
    }
}
