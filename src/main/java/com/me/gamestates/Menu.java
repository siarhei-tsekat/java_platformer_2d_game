package com.me.gamestates;

import com.me.Game;
import com.me.ui.MenuBtn;
import com.me.utils.LoadSave;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class Menu extends State implements StateMethods {

    private MenuBtn[] btns = new MenuBtn[3];
    private BufferedImage bg;

    private int menuX, menuY, menuWidth, menuHeight;

    public Menu(Game game) {
        super(game);
        loadBtns();
        loadBg();
    }

    private void loadBg() {
        bg = LoadSave.getSpriteAtlas(LoadSave.MENU_BG);
        menuWidth = (int) (bg.getWidth() * Game.SCALE);
        menuHeight = (int) (bg.getHeight() * Game.SCALE);
        menuX = Game.GAME_WIDTH / 2 - menuWidth / 2;
        menuY = (int) (45 * Game.SCALE);

    }

    private void loadBtns() {
        btns[0] = new MenuBtn(Game.GAME_WIDTH / 2, (int) (150 * Game.SCALE), 0, GameState.PLAYING);
        btns[1] = new MenuBtn(Game.GAME_WIDTH / 2, (int) (220 * Game.SCALE), 1, GameState.OPTIONS);
        btns[2] = new MenuBtn(Game.GAME_WIDTH / 2, (int) (290 * Game.SCALE), 2, GameState.QUIT);
    }

    @Override
    public void update() {
        for (int i = 0; i < btns.length; i++) {
            btns[i].update();
        }
    }

    @Override
    public void draw(Graphics graphics) {
        graphics.drawImage(bg, menuX, menuY, menuWidth, menuHeight, null);
        for (int i = 0; i < btns.length; i++) {
            btns[i].draw(graphics);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        for (int i = 0; i < btns.length; i++) {
            if (isIn(e, btns[i])) {
                btns[i].setMousePressed(true);
                break;
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

        for (int i = 0; i < btns.length; i++) {
            if (isIn(e, btns[i])) {
                if (btns[i].isMousePressed()) {
                    btns[i].applyGameState();
                }
                break;
            }
        }
        resetButtons();
    }

    private void resetButtons() {
        for (MenuBtn btn : btns) {
            btn.resetBooleans();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        for (MenuBtn btn : btns) {
            btn.setMouseOver(false);
        }
        for (MenuBtn btn : btns) {
            if (isIn(e, btn)) {
                btn.setMouseOver(true);
                break;
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            GameState.state = GameState.PLAYING;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
