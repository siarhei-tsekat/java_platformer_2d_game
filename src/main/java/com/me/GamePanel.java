package com.me;

import com.me.inputs.KeyboardInputs;
import com.me.inputs.MouseInputs;
import com.me.utils.Constants;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import static com.me.utils.Constants.Directions.LEFT;

public class GamePanel extends JPanel {

    private final MouseInputs mouseInputs;
    private float xDelta = 100, yDelta = 100;
    private BufferedImage image;
    private BufferedImage[][] animations;

    private int animationTick, animationIndex, animationSpeed = 15; // 120 frames / 8 times per second
    private int playerAction = Constants.PlayerConstants.IDLE;
    private int playerDirection = -1;
    private boolean moving = false;

    public GamePanel() {
        mouseInputs = new MouseInputs(this);
        importImage();
        loadAnimations();
        setPanelSize();
        addKeyListener(new KeyboardInputs(this));
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
    }

    public void setPlayerAction(int playerAction) {
        this.playerAction = playerAction;
    }

    public void setDirection(int direction) {
        playerDirection = direction;
        moving = true;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    private void loadAnimations() {
        animations = new BufferedImage[9][6];

        for (int i = 0; i < animations.length; i++) {
            for (int j = 0; j < animations[i].length; j++) {
                animations[i][j] = image.getSubimage(j * 64, i * 40, 64, 40);
            }
        }
    }

    private void importImage() {
        InputStream is = getClass().getResourceAsStream("/player_sprites.png");
        try {
            image = ImageIO.read(is);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void setPanelSize() {
        Dimension size = new Dimension(1280, 800); // image size 32 so 1280 / 32 = 40 and 800 / 32 = 25
        setMinimumSize(size);
        setPreferredSize(size);
        setMaximumSize(size);
    }

    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        updateAnimationTick();
        int scale_width = 256;
        int scale_height = 160;
        graphics.drawImage(animations[playerAction][animationIndex], (int) xDelta, (int) yDelta, scale_width, scale_height, null);

    }

    private void updateAnimationTick() {
        animationTick++;

        setAnimation();

        updatePosition();

        if (animationTick >= animationSpeed) {
            animationTick = 0;
            animationIndex++;
            if (animationIndex >= Constants.PlayerConstants.getSpriteAmount(playerAction)) {
                animationIndex = 0;
            }
        }
    }

    private void updatePosition() {
        if (moving) {
            switch (playerDirection) {
                case Constants.Directions.LEFT:
                    xDelta -= 5;
                    break;
                case Constants.Directions.UP:
                    yDelta -= 5;
                    break;
                case Constants.Directions.RIGHT:
                    xDelta += 5;
                    break;
                case Constants.Directions.DOWN:
                    yDelta += 5;
                    break;
            }
        }
    }

    private void setAnimation() {
        if (moving) {
            playerAction = Constants.PlayerConstants.RUNNING;
        } else {
            playerAction = Constants.PlayerConstants.IDLE;
        }
    }
}
