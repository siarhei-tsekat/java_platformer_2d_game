package com.me.entities;

import com.me.Game;
import com.me.utils.Constants;

public class Crabby extends Enemy {

    public Crabby(float x, float y) {
        super(x, y, Constants.EnemyConstants.CRABBY_WIDTH, Constants.EnemyConstants.CRABBY_HEIGHT, Constants.EnemyConstants.CRABBY);
        initHitBox(x, y, (int) (22 * Game.SCALE), (int) (19 * Game.SCALE));
    }
}
