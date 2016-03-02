/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2015
 *
 * Name: Khai Nguyen, Khoi Le & Alice Shen
 * Date: Nov 5, 2015
 * Time: 9:39:56 AM
 *
 * Project: csci205_final_project
 * Package: GameObject
 * File: Bomb
 * Description:
 *
 * ****************************************
 */
package GameObject;

import GameManager.GameManager;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;

/**
 * The bomb class, which manages a bomb instance.
 *
 * @author khainguyen
 */
public class Bomb extends GameObject {
    /**
     * the size of the explosion
     *
     */
    private int size;
    /**
     * the number of frame until the bomb explode
     *
     */
    private int timer = 125;
    /**
     * the player which plants the bomb
     *
     */
    private Player bomber;
    //images
    private BufferedImage[] images = GameManager.getResources().getBomb();
    private int count;
    private static final int MAX_COUNT = 7;
    private int index;

    /**
     * Initiate a bomb.
     *
     * @param size -the size of the bomb
     * @param bomber - the playe who plants the bomb
     */
    public Bomb(int size, Player bomber) {
        this.size = size;
        this.bomber = bomber;
        this.setType(GameObjectType.BOMB);
        GameManager.addToObjectList(this);
        this.count = 0;
        this.index = 0;
    }

    @Override
    public void render(Graphics2D g) {
        //draw the bomb
        if (index < images.length) {
            g.drawImage(images[index], null, getX(), getY());
            count++;
            if (count == MAX_COUNT) {
                index++;
                count = 0;
            }
        } else {
            index = 0;
            g.drawImage(images[index], null, getX(), getY());
        }
    }

    @Override
    public void vanish() {
        bomber.setBombPlanted(bomber.getBombPlanted() - 1);
        bomber.getBombs().remove(this);
        GameManager.remove(this);
    }

    @Override
    public void update() {
        this.timer -= 1;
        //if timer reach 0 , explode the bomb
        if (timer == 30) {
            exploding();
        }
        if (timer == 0) {
            vanish();
        }
    }

    /**
     * The bomb explodes. Begin the create explosions the bomb, according to the
     * size. The explosions are only horizontal and vertical.
     *
     * @author khainguyen
     */
    public void exploding() {
        int x = this.getCenterLocation().x;
        int y = this.getCenterLocation().y;
        GameManager.getResources().reloadSound();
        GameManager.getResources().getSoundEffects().get("explode").play();
        //check if collide with the bomb
        Explosion explosion = new Explosion(x, y, ExplosionType.CENTER);
        explosion.collided();
        for (int i = 1; i <= size; i++) {
            int range = i * GameUtility.GameUtility.TILE_SIZE;
            Point explosionLocation = new Point(x + range, y);
            if (i < size) {
                explosion = new Explosion(explosionLocation.x,
                                          explosionLocation.y,
                                          ExplosionType.HORIZONTAL);
            } else {
                explosion = new Explosion(explosionLocation.x,
                                          explosionLocation.y,
                                          ExplosionType.RIGHT);
            }
            if (explosion.collided()) {
                break;
            }
        }
        for (int i = -1; i >= -size; i--) {
            int range = i * GameUtility.GameUtility.TILE_SIZE;
            Point explosionLocation = new Point(x + range, y);
            if (i > -size) {
                explosion = new Explosion(explosionLocation.x,
                                          explosionLocation.y,
                                          ExplosionType.HORIZONTAL);
            } else {
                explosion = new Explosion(explosionLocation.x,
                                          explosionLocation.y,
                                          ExplosionType.LEFT);
            }
            if (explosion.collided()) {
                break;
            }
        }
        for (int i = -1; i >= -size; i--) {
            int range = i * GameUtility.GameUtility.TILE_SIZE;
            Point explosionLocation = new Point(x, y + range);
            if (i > -size) {
                explosion = new Explosion(explosionLocation.x,
                                          explosionLocation.y,
                                          ExplosionType.VERTICAL);
            } else {
                explosion = new Explosion(explosionLocation.x,
                                          explosionLocation.y,
                                          ExplosionType.UP);
            }
            if (explosion.collided()) {
                break;
            }
        }
        for (int i = 1; i <= size; i++) {
            int range = i * GameUtility.GameUtility.TILE_SIZE;
            Point explosionLocation = new Point(x, y + range);
            if (i < size) {
                explosion = new Explosion(explosionLocation.x,
                                          explosionLocation.y,
                                          ExplosionType.VERTICAL);
            } else {
                explosion = new Explosion(explosionLocation.x,
                                          explosionLocation.y,
                                          ExplosionType.DOWN);
            }
            if (explosion.collided()) {
                break;
            }
        }
    }
}
