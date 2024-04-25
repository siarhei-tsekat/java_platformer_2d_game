package com.me.entities;

import com.me.Game;
import com.me.utils.Constants;
import com.me.utils.HelpMethods;

import static com.me.utils.Constants.Directions.LEFT;
import static com.me.utils.Constants.Directions.RIGHT;
import static com.me.utils.Constants.EnemyConstants.ATTACK;
import static com.me.utils.Constants.EnemyConstants.IDLE;

public abstract class Enemy extends Entity {

    protected int animationIndex, enemyState, enemyType;
    protected int animationTick, animationSpeed = 25;
    protected boolean firstUpdate = true;
    protected boolean inAir;
    protected float fallSpeed;
    protected float gravity = 0.04f * Game.SCALE;
    protected float walkSpeed = 0.35f * Game.SCALE;
    protected int walkDirection = LEFT;
    protected int tileY;
    protected float attackDistance = Game.TILES_SIZE;

    public Enemy(float x, float y, int width, int height, int enemyType) {
        super(x, y, width, height);
        this.enemyType = enemyType;
        initHitBox(x, y, width, height);
    }

    protected void updateAnimationTick() {
        animationTick++;
        if (animationTick >= animationSpeed) {
            animationTick = 0;
            animationIndex++;
            if (animationIndex >= Constants.EnemyConstants.getSpriteAmount(enemyType, enemyState)) {
                animationIndex = 0;
                if (enemyState == ATTACK) {
                    enemyState = IDLE;
                }
            }
        }
    }

    protected void changeWalkDir() {
        if (walkDirection == LEFT) {
            walkDirection = RIGHT;
        } else {
            walkDirection = LEFT;
        }
    }

    protected void firstUpdateCheck(int[][] levelData) {
        if (!HelpMethods.isEntityOnFloor(hitBox, levelData)) {
            inAir = true;
        }
        firstUpdate = false;
    }

    protected void updateInAir(int[][] levelData) {
        if (HelpMethods.canMoveHere(hitBox.x, hitBox.y + fallSpeed, hitBox.width, hitBox.height, levelData)) {
            hitBox.y += fallSpeed;
            fallSpeed += gravity;
        } else {
            inAir = false;
            hitBox.y = HelpMethods.getEntityYPosUnderRoofOrAboveFloor(hitBox, fallSpeed);
            tileY = (int) (hitBox.y / Game.TILES_SIZE);
        }
    }

    protected void move(int[][] levelData) {
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
    }

    protected boolean isPlayerInRange(Player player) {
        int abs = (int) Math.abs(player.hitBox.x - hitBox.x);

        return abs <= attackDistance * 5;
    }

    protected void newState(int enemyState) {
        this.enemyState = enemyState;
        animationTick = 0;
        animationIndex = 0;
    }

    public int getAnimationIndex() {
        return animationIndex;
    }

    public int getEnemyState() {
        return enemyState;
    }

    protected void turnTowardsPlayer(Player player) {
        if (player.hitBox.x > hitBox.x) {
            walkDirection = RIGHT;
        } else {
            walkDirection = LEFT;
        }
    }

    protected boolean isPlayerCloseForAttack(Player player) {
        int abs = (int) Math.abs(player.hitBox.x - hitBox.x);

        return abs <= attackDistance;
    }
}
