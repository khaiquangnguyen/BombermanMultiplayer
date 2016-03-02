/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2015
 *
 * Name: Khai Nguyen, Khoi Le & Alice Shen
 * Date: Nov 17, 2015
 * Time: 4:16:58 PM
 *
 * Project: csci205_final_project
 * Package: GameObject
 * File: Explosion
 * Description:
 *
 * ****************************************
 */
package GameObject;

import GameManager.GameManager;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * An explosion object. The explosion object is created in any place that an
 * explosion can be created when a bomb explode.
 *
 * @author khainguyen
 */
public class Explosion extends GameObject {

    /**
     * Timer. Count until the explosion disappear.
     */
    private short timer = 30;
    private ExplosionType type;
    private BufferedImage[] imagesCenter = new BufferedImage[5];
    private BufferedImage[] imagesHorizontal = new BufferedImage[5];
    private BufferedImage[] imagesVertical = new BufferedImage[5];
    private BufferedImage[] imagesUp = new BufferedImage[5];
    private BufferedImage[] imagesDown = new BufferedImage[5];
    private BufferedImage[] imagesLeft = new BufferedImage[5];
    private BufferedImage[] imagesRight = new BufferedImage[5];
    private int count;
    private static final int MAX_COUNT = 6;
    private int index;

    /**
     * Initiate an explosion.
     *
     * @param x - the x location of the frame-based coordinate.
     * @param y - the y location of the frame-based coordinate.
     */
    public Explosion(int x, int y, ExplosionType type) {
        setX(x - GameUtility.GameUtility.TILE_SIZE / 2);
        setY(y - GameUtility.GameUtility.TILE_SIZE / 2);
        GameManager.addToObjectList(this);
        this.type = type;
        loadAnimations();
        index = 0;
        count = 0;
    }

    /**
     * Load all the necessary animations for the current explosion.
     */
    private void loadAnimations() {
        loadImages(imagesCenter, "C");
        loadImages(imagesHorizontal, "H");
        loadImages(imagesVertical, "V");
        loadImages(imagesUp, "U");
        loadImages(imagesDown, "D");
        loadImages(imagesRight, "R");
        loadImages(imagesLeft, "L");
    }

    /**
     * Load all the images for a single animation.
     *
     * @param images - the images of the animation.
     * @param direction - the direct that the animation is associated with.
     */
    private void loadImages(BufferedImage[] images, String direction) {
        for (int i = 0; i < images.length; i++) {
            String fileName = direction + (i + 1);
            images[i] = GameManager.getResources().getExplosionImages().get(
                    fileName);
        }

    }

    @Override
    public void render(Graphics2D g) {
        if (type == ExplosionType.CENTER) {
            g.drawImage(imagesCenter[index], null, getX(), getY());
            count++;
            if (count == MAX_COUNT) {
                index++;
                count = 0;
            }
        } else if (type == ExplosionType.HORIZONTAL) {
            g.drawImage(imagesHorizontal[index], null, getX(), getY());
            count++;
            if (count == MAX_COUNT) {
                index++;
                count = 0;
            }
        } else if (type == ExplosionType.VERTICAL) {
            g.drawImage(imagesVertical[index], null, getX(), getY());
            count++;
            if (count == MAX_COUNT) {
                index++;
                count = 0;
            }
        } else if (type == ExplosionType.UP) {
            g.drawImage(imagesUp[index], null, getX(), getY());
            count++;
            if (count == MAX_COUNT) {
                index++;
                count = 0;
            }
        } else if (type == ExplosionType.DOWN) {
            g.drawImage(imagesDown[index], null, getX(), getY());
            count++;
            if (count == MAX_COUNT) {
                index++;
                count = 0;
            }
        } else if (type == ExplosionType.LEFT) {
            g.drawImage(imagesLeft[index], null, getX(), getY());
            count++;
            if (count == MAX_COUNT) {
                index++;
                count = 0;
            }
        } else if (type == ExplosionType.RIGHT) {
            g.drawImage(imagesRight[index], null, getX(), getY());
            count++;
            if (count == MAX_COUNT) {
                index++;
                count = 0;
            }
        }

    }

    @Override
    public void vanish() {
        GameManager.remove(this);

    }

    @Override
    public void update() {
        timer -= 1;
        if (timer <= 0) {
            vanish();
        }
    }

    /**
     * Check if the explosion collide with anything in its blast radius.
     *
     * @return - true of collide with anything, false otherwise.
     * @author khainguyen
     */
    public boolean collided() {
        int checkRadius = (int) (GameUtility.GameUtility.TILE_SIZE * 1.5);
        boolean collided = false;
        for (GameObject gameObject : GameManager.getGameObjectList()) {
            if (GameUtility.GameUtility.distance(
                    gameObject.getCenterLocation(),
                    this.getCenterLocation()) < checkRadius && !(gameObject instanceof Explosion) && !(gameObject.getType() == GameObjectType.BOMB)) {
                if (GameUtility.GameUtility.dectectCollision(
                        gameObject.getCenterLocation(),
                        this.getCenterLocation())) {
                    gameObject.vanish();
                    if (gameObject.getType() == GameObjectType.WALL) {
                        vanish();
                    }
                    if (gameObject.getType() != GameObjectType.MONSTER) {
                        collided = true;
                    }
                }
            }
        }
        return collided;
    }
}
