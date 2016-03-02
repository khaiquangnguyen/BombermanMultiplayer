/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2015
 *
 * Name: Khai Nguyen, Khoi Le & Alice Shen
 * Date: Nov 5, 2015
 * Time: 9:07:27 AM
 *
 * Project: csci205_final_project
 * Package: GameObject
 * File: GameObject
 * Description:
 *
 * ****************************************
 */
package GameObject;

import java.awt.Graphics2D;
import java.awt.Point;

/**
 * An abstract object. This object is the blueprint for all other game objects.
 * It specifies the basic attributes and functions that a game object must have.
 *
 * @author khainguyen
 */
public abstract class GameObject {
    /**
     * the type of the object. Default is the wall
     *
     */
    private GameObjectType type = GameObjectType.WALL;
    /**
     * the collision group of the object. Default is that of wall
     *
     */
    private CollisionGroup collisionGroup = CollisionGroup.WALL_COLLISION;
    /**
     * the x coordinate of the object
     *
     */
    private int x;
    /**
     * the y coordintae of the object
     *
     */
    private int y;
    /**
     * the width of the object. Always equal to the with of a tile
     *
     */
    private int width = GameUtility.GameUtility.TILE_SIZE;
    /**
     * the height of the object. Always equal to the height of a tile
     *
     */
    private int height = GameUtility.GameUtility.TILE_SIZE;

    /**
     * Time until the object vanish from the game completely.
     */
    private int deadTimer = 50;
    /**
     * If the player is alive or not
     */
    private boolean isAlive = true;

    /**
     * Initiate the frame for the game.
     */
    /**
     * Update the animation of the current object
     *
     * @param g - the graphic object which handle drawing
     * @author khainguyen
     */
    public abstract void render(Graphics2D g);

    /**
     * Actions when the object is killed.
     *
     * @author khainguyen
     */
    public abstract void vanish();

    /**
     * Update the current logic of the game with each game loop.
     *
     * @author khainguyen
     */
    public abstract void update();

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Point getLocation() {
        return new Point(x, y);
    }

    public void setLocation(Point newLoction) {
        this.x = newLoction.x;
        this.y = newLoction.y;

    }

    public void setLoction(int newX, int newY) {
        this.x = newX;
        this.y = newY;
    }

    public int getCenterX() {
        return getX() + this.getWidth() / 2;
    }

    public void setType(GameObjectType type) {
        this.type = type;
    }

    public void setCollisionGroup(CollisionGroup collisionGroup) {
        this.collisionGroup = collisionGroup;
    }

    public int getCenterY() {
        return getY() + this.getWidth() / 2;
    }

    public Point getCenterLocation() {
        return new Point(getCenterX(), getCenterY());
    }

    public GameObjectType getType() {
        return type;
    }

    public CollisionGroup getCollisionGroup() {
        return collisionGroup;
    }

    public int getDeadTimer() {
        return deadTimer;
    }

    public void setDeadTimer(int deadTimer) {
        this.deadTimer = deadTimer;
    }

    public boolean isIsAlive() {
        return isAlive;
    }

    public void setIsAlive(boolean isAlive) {
        this.isAlive = isAlive;
    }

}
