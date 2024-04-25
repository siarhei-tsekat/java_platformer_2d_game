package com.me.entities;

import com.me.Game;
import com.me.utils.Constants;
import com.me.utils.HelpMethods;

import static com.me.utils.Constants.EnemyConstants.ATTACK;
import static com.me.utils.Constants.EnemyConstants.IDLE;
import static com.me.utils.Constants.EnemyConstants.RUNNING;

public class Crabby extends Enemy {

    public Crabby(float x, float y) {
        super(x, y, Constants.EnemyConstants.CRABBY_WIDTH, Constants.EnemyConstants.CRABBY_HEIGHT, Constants.EnemyConstants.CRABBY);
        initHitBox(x, y, (int) (22 * Game.SCALE), (int) (19 * Game.SCALE));
    }

    public void update(int[][] levelData, Player player) {
        updateMove(levelData, player);
        updateAnimationTick();
    }

    private void updateMove(int[][] levelData, Player player) {
        if (firstUpdate) {
            firstUpdateCheck(levelData);
        }

        if (inAir) {
            updateInAir(levelData);
        } else {
            switch (enemyState) {
                case IDLE:
                    newState(RUNNING);
                    break;
                case RUNNING:
                    if (canSeePlayer(levelData, player)) {
                        turnTowardsPlayer(player);
                    }

                    if (isPlayerCloseForAttack(player)) {
                        newState(ATTACK);
                    }

                    move(levelData);
                    break;
            }
        }
    }

    private boolean canSeePlayer(int[][] levelData, Player player) {
        int playerTileY = (int) (player.hitBox.y / Game.TILES_SIZE);
        if (playerTileY == tileY) {
            if (isPlayerInRange(player)) {
                if (HelpMethods.isSightClear(levelData, hitBox, player.hitBox, tileY)) {
                    return true;
                }
            }
        }

        return false;
    }
}
