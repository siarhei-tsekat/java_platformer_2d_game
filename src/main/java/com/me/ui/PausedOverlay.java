package com.me.ui;

import com.me.Game;
import com.me.gamestates.GameState;
import com.me.gamestates.Playing;
import com.me.utils.LoadSave;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static com.me.utils.Constants.UI.PauseBtns.SOUND_SIZE;
import static com.me.utils.Constants.UI.UrmBtns.URM_SIZE;
import static com.me.utils.Constants.UI.VolumeBtns.SLIDER_WIDTH;
import static com.me.utils.Constants.UI.VolumeBtns.VOLUME_HEIGHT;

public class PausedOverlay {
    private BufferedImage bg;
    private int bgX, bgY, bgWidth, bgHeight;
    private SoundButton musicBtn, sfxBtn;
    private UrmButton menuB, replayB, unpauseB;
    private Playing playing;
    private VolumeBtn volumeBtn;

    public PausedOverlay(Playing playing) {
        this.playing = playing;
        loadBg();
        createSoundBtns();
        createUrmBtns();
        createVolumeBtn();
    }

    private void createVolumeBtn() {
        int vX = (int) (309 * Game.SCALE);
        int vY = (int) (278 * Game.SCALE);

        volumeBtn = new VolumeBtn(vX, vY, SLIDER_WIDTH, VOLUME_HEIGHT);
    }

    private void createUrmBtns() {
        int menuX = (int) (313 * Game.SCALE);
        int replayX = (int) (387 * Game.SCALE);
        int unpauseX = (int) (462 * Game.SCALE);
        int bY = (int) (325 * Game.SCALE);

        menuB = new UrmButton(menuX, bY, URM_SIZE, URM_SIZE, 2);
        replayB = new UrmButton(replayX, bY, URM_SIZE, URM_SIZE, 1);
        unpauseB = new UrmButton(unpauseX, bY, URM_SIZE, URM_SIZE, 0);
    }

    private void createSoundBtns() {
        int soundX = (int) (450 * Game.SCALE);
        int musicY = (int) (140 * Game.SCALE);
        int sfxY = (int) (186 * Game.SCALE);

        musicBtn = new SoundButton(soundX, musicY, SOUND_SIZE, SOUND_SIZE);
        sfxBtn = new SoundButton(soundX, sfxY, SOUND_SIZE, SOUND_SIZE);
    }

    private void loadBg() {
        bg = LoadSave.getSpriteAtlas(LoadSave.PAUSE_BG);
        bgWidth = (int) (bg.getWidth() * Game.SCALE);
        bgHeight = (int) (bg.getHeight() * Game.SCALE);
        bgX = Game.GAME_WIDTH / 2 - bgWidth / 2;
        bgY = (int) (25 * Game.SCALE);
    }

    public void update() {
        musicBtn.update();
        sfxBtn.update();
        menuB.update();
        replayB.update();
        unpauseB.update();
        volumeBtn.update();
    }

    public void draw(Graphics graphics) {
        graphics.drawImage(bg, bgX, bgY, bgWidth, bgHeight, null);
        musicBtn.draw(graphics);
        sfxBtn.draw(graphics);

        menuB.draw(graphics);
        replayB.draw(graphics);
        unpauseB.draw(graphics);

        volumeBtn.draw(graphics);
    }

    public void mousePressed(MouseEvent e) {
        if (isIn(e, musicBtn)) {
            musicBtn.setMousePressed(true);
        } else if (isIn(e, sfxBtn)) {
            sfxBtn.setMousePressed(true);
        } else if (isIn(e, menuB)) {
            menuB.setMousePressed(true);
        } else if (isIn(e, replayB)) {
            replayB.setMousePressed(true);
        } else if (isIn(e, unpauseB)) {
            unpauseB.setMousePressed(true);
        } else if (isIn(e, volumeBtn)) {
            volumeBtn.setMousePressed(true);
        }
    }

    public void mouseReleased(MouseEvent e) {
        if (isIn(e, musicBtn)) {
            if (musicBtn.isMousePressed()) {
                musicBtn.setMuted(!musicBtn.isMuted());
            }
        } else if (isIn(e, sfxBtn)) {
            if (sfxBtn.isMousePressed()) {
                sfxBtn.setMuted(!sfxBtn.isMuted());
            }
        } else if (isIn(e, menuB)) {
            if (menuB.isMousePressed()) {
                GameState.state = GameState.MENU;
                playing.unpause();
            }
        } else if (isIn(e, replayB)) {
            if (replayB.isMousePressed()) {

            }
        } else if (isIn(e, unpauseB)) {
            if (unpauseB.isMousePressed()) {
                playing.unpause();
            }
        }

        musicBtn.resetBooleans();
        sfxBtn.resetBooleans();
        menuB.resetBooleans();
        replayB.resetBooleans();
        unpauseB.resetBooleans();
        volumeBtn.resetBooleans();
    }

    public void mouseMoved(MouseEvent e) {
        musicBtn.setMouseOver(false);
        sfxBtn.setMouseOver(false);

        menuB.setMouseOver(false);
        replayB.setMouseOver(false);
        unpauseB.setMouseOver(false);
        volumeBtn.setMouseOver(false);

        if (isIn(e, musicBtn)) {
            musicBtn.setMouseOver(true);
        } else if (isIn(e, sfxBtn)) {
            sfxBtn.setMouseOver(true);
        } else if (isIn(e, menuB)) {
            menuB.setMouseOver(true);
        } else if (isIn(e, replayB)) {
            replayB.setMouseOver(true);
        } else if (isIn(e, unpauseB)) {
            unpauseB.setMouseOver(true);
        } else if (isIn(e, volumeBtn)) {
            volumeBtn.setMouseOver(true);
        }
    }

    public void mouseDragged(MouseEvent e) {
        if (volumeBtn.isMousePressed()) {
            volumeBtn.changeX(e.getX());
        }
    }

    private boolean isIn(MouseEvent e, PauseButton b) {
        return b.bounds.getBounds().contains(e.getX(), e.getY());
    }
}
