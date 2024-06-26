package com.me.utils;

import com.me.Game;

import java.awt.geom.Rectangle2D;

public class HelpMethods {
    public static boolean canMoveHere(float x, float y, float width, float height, int[][] levelData) {
        if (!isSolid(x, y, levelData)) {
            if (!isSolid(x + width, y + height, levelData)) {
                if (!isSolid(x + width, y, levelData)) {
                    if (!isSolid(x, y + height, levelData)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public static float getEntityXPosNextToWall(Rectangle2D.Float hitBox, float xSpeed) {
        int currentTile = (int) (hitBox.x / Game.TILES_SIZE);

        if (xSpeed > 0) {
            int tileX = currentTile * Game.TILES_SIZE;
            int xOffset = (int) (Game.TILES_SIZE - hitBox.width);
            return tileX + xOffset - 1;
        } else {
            return currentTile * Game.TILES_SIZE;
        }
    }

    public static float getEntityYPosUnderRoofOrAboveFloor(Rectangle2D.Float hitBox, float airSpeed) {
        int currentTile = (int) (hitBox.y / Game.TILES_SIZE);

        if (airSpeed > 0) {
            int tileYPos = currentTile * Game.TILES_SIZE;
            int yOffset = (int) (Game.TILES_SIZE - hitBox.height);
            return tileYPos + yOffset - 1;
        } else {
            return currentTile * Game.TILES_SIZE;
        }
    }

    public static boolean isEntityOnFloor(Rectangle2D.Float hitBox, int[][] levelData) {
        if (!isSolid(hitBox.x, hitBox.y + hitBox.height + 1, levelData)) {
            if (!isSolid(hitBox.x + hitBox.width, hitBox.y + hitBox.height + 1, levelData)) {
                return false;
            }
        }
        return true;
    }

    private static boolean isSolid(float x, float y, int[][] levelData) {

        int maxWidth = levelData[0].length * Game.TILES_SIZE;

        if (x < 0 || x >= maxWidth) {
            return true;
        }
        if (y < 0 || y >= Game.GAME_HEIGHT) {
            return true;
        }
        float xInd = x / Game.TILES_SIZE;
        float yInd = y / Game.TILES_SIZE;

        return isTileSolid((int) xInd, (int) yInd, levelData);
    }

    public static boolean isFloor(Rectangle2D.Float hitBox, float xSpeed, int[][] levelData) {
        return isSolid(hitBox.x + xSpeed, hitBox.y + hitBox.height + 1, levelData);
    }

    public static boolean isTileSolid(int xTile, int yTile, int[][] levelData) {
        int value = levelData[yTile][xTile];

        if (value >= 48 || value < 0 || value != 11) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isSightClear(int[][] levelData, Rectangle2D.Float f_hitBox, Rectangle2D.Float s_hitBox, int tileY) {
        int firstXTile = (int) (f_hitBox.x / Game.TILES_SIZE);
        int secondXTile = (int) (s_hitBox.x / Game.TILES_SIZE);

        if (firstXTile > secondXTile) {
            return isAllTileWalkable(secondXTile, firstXTile, tileY, levelData);
        } else {
            return isAllTileWalkable(firstXTile, secondXTile, tileY, levelData);
        }
    }

    public static boolean isAllTileWalkable(int xStart, int xEnd, int y, int[][] levelData) {
        for (int i = 0; i < xEnd - xStart; i++) {
            if (isTileSolid(xStart + i, y, levelData)) {
                return false;
            }

            if (!isTileSolid(xStart + i, y + 1, levelData)) {
                return false;
            }
        }
        return true;
    }
}
