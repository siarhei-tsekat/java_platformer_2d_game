package com.me.gamestates;

import com.me.Game;
import com.me.entities.Player;
import com.me.levels.LevelManager;
import com.me.ui.PausedOverlay;
import com.me.utils.LoadSave;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class Playing extends State implements StateMethods {

    private Player player;
    private LevelManager levelManager;
    private boolean paused = false;
    private PausedOverlay pausedOverlay;
    private int xLevelOffset;
    private int leftBorder = (int) (0.2 * Game.GAME_WIDTH);
    private int rightBorder = (int) (0.8 * Game.GAME_WIDTH);
    private int levelTilesWide = LoadSave.getLevelData()[0].length;
    private int maxTilesOffset = levelTilesWide - Game.TILES_IN_WIDTH;
    private int maxLevelOffsetX = maxTilesOffset * Game.TILES_SIZE;

    public Playing(Game game) {
        super(game);
        initClasses();
    }

    private void initClasses() {
        levelManager = new LevelManager(getGame());
        player = new Player(200, 200, (int) (64 * Game.SCALE), (int) (40 * Game.SCALE));
        player.loadLevelData(levelManager.getCurrentLevel().getLevelData());
        pausedOverlay = new PausedOverlay(this);
    }

    public Player getPlayer() {
        return player;
    }

    public void windowFocusLost() {
        player.resetDirBooleans();
    }

    @Override
    public void update() {
        if (!paused) {
            levelManager.update();
            player.update();
            checkCloseToBorder();
        } else {
            pausedOverlay.update();
        }
    }

    private void checkCloseToBorder() {
        int playerX = (int) (player.getHitBox().x);
        int diff = playerX - xLevelOffset;

        if (diff > rightBorder) {
            xLevelOffset += diff - rightBorder;
        } else if (diff < leftBorder) {
            xLevelOffset += diff - leftBorder;
        }

        if (xLevelOffset > maxLevelOffsetX) {
            xLevelOffset = maxLevelOffsetX;
        } else if (xLevelOffset < 0) {
            xLevelOffset = 0;
        }
    }

    @Override
    public void draw(Graphics graphics) {
        levelManager.draw(graphics, xLevelOffset);
        player.render(graphics, xLevelOffset);

        if (paused) {
            graphics.setColor(new Color(0, 0, 0, 150));
            graphics.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);
            pausedOverlay.draw(graphics);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            player.setAttacking(true);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (paused) {
            pausedOverlay.mousePressed(e);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (paused) {
            pausedOverlay.mouseReleased(e);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (paused) {
            pausedOverlay.mouseMoved(e);
        }
    }

    public void mouseDragged(MouseEvent e) {
        if (paused) {
            pausedOverlay.mouseDragged(e);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                player.setUp(true);
                break;
            case KeyEvent.VK_A:
                player.setLeft(true);
                break;
            case KeyEvent.VK_S:
                player.setDown(true);
                break;
            case KeyEvent.VK_D:
                player.setRight(true);
                break;
            case KeyEvent.VK_SPACE:
                player.setJump(true);
                break;
            case KeyEvent.VK_ESCAPE:
                paused = !paused;
                break;
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                player.setUp(false);
                break;
            case KeyEvent.VK_A:
                player.setLeft(false);
                break;
            case KeyEvent.VK_S:
                player.setDown(false);
                break;
            case KeyEvent.VK_D:
                player.setRight(false);
                break;
            case KeyEvent.VK_SPACE:
                player.setJump(false);
                break;
        }
    }

    public void unpause() {
        paused = false;
    }
}
