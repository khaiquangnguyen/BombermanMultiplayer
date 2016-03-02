/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2015
 *
 * Name: Khai Nguyen, Khoi Le & Alice Shen
 * Date: Nov 5, 2015
 * Time: 10:09:37 AM
 *
 * Project: csci205_final_project
 * Package: GameObject
 * File: Wall
 * Description:
 *
 * ****************************************
 */
package GameObject;

import GameManager.GameManager;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * The wall object, which represents all the wall in the game. There are two
 * types of wall, destructible and indestructible. Each destructible wall has a
 * chance to carry a hidden power up. This is assigned when the wall is created.
 *
 * @author khainguyen
 */
public class Wall extends GameObject {

    /**
     * If the wall can be destroyed or not
     *
     */
    private boolean isDestroyable;
    /**
     * the power up the wall contains. If the wall doesn't contain any power up
     * then the default is null.
     *
     */
    private PowerUpType hiddenPowerUp = null;
    /**
     * The time from when the wall is destroyed until the wall completely
     * vanish.
     *
     */
    private int timeTillVanish = 50;
    /**
     * If begin to vanish.
     *
     */
    private boolean beginVanish = false;
    /**
     * Vanish animation
     */
    private int vanishIndex;
    private static final int VANISH_COUNT = 10;
    private int count;
    private static BufferedImage[] vanishImages = GameManager.getResources().getWallVanishImages();

    /**
     * Initiate a wall at the location x and y according to frame-based
     * coordinate
     *
     * @param isDestroyable - if the wall can be destroyed or not
     * @param x - the x location in the frame-based coordinate
     * @param y - the y location in the frame-based coordinate
     * @author khainguyen
     *
     */
    public Wall(boolean isDestroyable, int x, int y) {
        this.isDestroyable = isDestroyable;
        this.setX(x);
        this.setY(y);
        this.setType(GameObjectType.WALL);
        GameManager.addToObjectList(this);
        this.vanishIndex = 0;
        this.count = 0;
    }

    /**
     * Initiate a wall at the location x and y according to the frame-based
     * coordinate, and the hidden powerup that the wall stores.
     *
     * @param isDestroyable - if the wall can be destroyed
     * @param x - the x location in the frame-based coordinate
     * @param y - the y location in the frame-based coordinate
     * @param powerUp - the power up that the wall stores.
     * @author khainguyen
     */
    public Wall(boolean isDestroyable, int x, int y, PowerUpType powerUp) {
        this.isDestroyable = isDestroyable;
        this.setX(x);
        this.setY(y);
        this.hiddenPowerUp = powerUp;
        this.setType(GameObjectType.WALL);
        GameManager.addToObjectList(this);
        this.vanishIndex = 0;
        this.count = 0;
    }

    @Override
    public void render(Graphics2D g) {
        if (this.isDestroyable) {
            if (!beginVanish) {
                g.drawImage(GameManager.getResources().getWallImages().get(
                        "brick"),
                            null, getX(), getY());
            } else {
                g.drawImage(vanishImages[vanishIndex], null, getX(), getY());
                count++;
                if (count == VANISH_COUNT) {
                    vanishIndex++;
                    count = 0;
                }
            }
        } else {
            g.drawImage(GameManager.getResources().getWallImages().get("wall"),
                        null, getX(), getY());
        }
    }

    @Override
    public void vanish() {
        if (isDestroyable) {
            beginVanish = true;
        }
    }

    public void vanishCompletely() {
        GameManager.remove(this);
        if (hiddenPowerUp != null) {
            GameManager.getGameMap().addToMap(new PowerUp(hiddenPowerUp),
                                              GameUtility.GameUtility.toGridCordinate(
                                                      this.getLocation()));
        }
    }

    @Override
    public void update() {
        if (beginVanish) {
            timeTillVanish -= 1;
            if (timeTillVanish == 0) {
                vanishCompletely();
            }
        }
    }

    //
    //getters and setters
    //
    //
    public boolean isIsDestroyable() {
        return isDestroyable;
    }

    public void setIsDestroyable(boolean isDestroyable) {
        this.isDestroyable = isDestroyable;
    }

    public PowerUpType getHiddenPowerUp() {
        return hiddenPowerUp;
    }

    public void setHiddenPowerUp(PowerUpType hiddenPowerUp) {
        this.hiddenPowerUp = hiddenPowerUp;
    }

}
