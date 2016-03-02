/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2015
 *
 * Name: Khai Nguyen, Khoi Le & Alice Shen
 * Date: Nov 5, 2015
 * Time: 9:39:45 AM
 *
 * Project: csci205_final_project
 * Package: GameObject
 * File: Player
 * Description:
 *
 * ****************************************
 */
package GameObject;

import GameManager.GameManager;
import GameUtility.GameKeyboardListener;
import Networking.Network;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * The player object for the game.
 *
 * @author khainguyen
 */
public class Player extends GameObject {
    /**
     * the currently active bomb that the player has planted
     *
     */
    private Queue<Bomb> bombQueue;
    /**
     * the currently power up that the player has.
     *
     */
    private Queue<PowerUpType> powerUps;
    /**
     * the number of bomb the player can plant at the same time.
     *
     */
    private int bombLimit;
    /**
     * the number of bomb the player has planted at the same time.
     *
     */
    private int bombPlanted;
    /**
     * the speed of the player, counted by pixel per frame.
     *
     */
    private int speed;
    /**
     * the current direction the player is faceing. Using an oclock as the
     * foundation. Up is 12, down is 6, left is 9, right is 3.
     *
     */
    private int direction;
    /**
     * the size of the bombQueue
     *
     */
    private int bombSize;
    /**
     * if the player can be harmed or not
     *
     */
    private boolean invulnerable = false;
    /**
     * the game map that the player belong to
     *
     */

    /**
     * The ID of the player. This ID matches the ID of the game.
     */
    short ID;

    /**
     * The list of all animations.
     */
    BufferedImage[] imagesFront = new BufferedImage[8];
    BufferedImage[] imagesLeft = new BufferedImage[8];
    BufferedImage[] imagesRight = new BufferedImage[8];
    BufferedImage[] imagesBack = new BufferedImage[8];
    /**
     * The animation index.
     */
    int front = 0;
    int left = 0;
    int right = 0;
    int back = 0;
    /**
     * Animation render rate.
     */
    int count = 0;
    /**
     * The maximum render rate.
     */
    static final int MAX_COUNT = 7;
    /**
     * Vanish animation
     */
    int vanishIndex;
    static final int VANISH_COUNT = 10;
    int vanishCount;
    BufferedImage[] vanishImages = new BufferedImage[5];

    /**
     * Initiate a player at a specific location in the map with a specific ID.
     *
     * @param x - the x location in the frame-based coordinate.
     * @param y - the y location in the frame-based coordinate.
     * @param ID - the ID assigned to the player. Match that of the game.
     * @author khainguyen
     */
    public Player(int x, int y, short ID) {
        this.bombLimit = 2;
        this.bombQueue = new LinkedBlockingDeque<>();
        this.bombSize = 1;
        this.bombPlanted = 0;
        this.invulnerable = false;
        this.direction = 6;
        this.speed = 4;
        this.setX(x);
        this.setY(y);
        this.setCollisionGroup(CollisionGroup.PLAYER_COLLISION);
        this.setType(GameObjectType.PLAYER);
        GameManager.addToObjectList(this);
        this.ID = ID;
        GameManager.setNumPlayer(GameManager.getNumPlayer() + 1);
        loadAnimations();
        sendPlayerDataToNetwork();
        this.vanishImages = GameManager.getResources().getPlayerVanishImages();
    }

    /**
     * Load all the necessary animations for the current player.
     *
     * @author khainguyen
     */
    private void loadAnimations() {
        loadImages(imagesFront, "F");
        loadImages(imagesLeft, "L");
        loadImages(imagesRight, "R");
        loadImages(imagesBack, "B");
    }

    /**
     * Load all the necessary images to form a single animation.
     *
     * @param images -the image buffer to be loaded to.
     * @param direction - the direction that the animation represents.
     */
    private void loadImages(BufferedImage[] images, String direction) {
        switch (this.ID) {
            case 0:
                for (int i = 0; i < images.length; i++) {
                    String fileName = direction + (i + 1);
                    images[i] = GameManager.getResources().getPlayerImages().get(
                            fileName);
                }
                break;
            case 1:
                for (int i = 0; i < images.length; i++) {
                    String fileName = direction + (i + 1);
                    images[i] = GameManager.getResources().getPlayer1Images().get(
                            fileName);
                }
                break;
            case 2:
                for (int i = 0; i < images.length; i++) {
                    String fileName = direction + (i + 1);
                    images[i] = GameManager.getResources().getPlayer2Images().get(
                            fileName);
                }
                break;
            case 3:
                for (int i = 0; i < images.length; i++) {
                    String fileName = direction + (i + 1);
                    images[i] = GameManager.getResources().getPlayer3Images().get(
                            fileName);
                }
                break;
        }

    }

    @Override
    public void render(Graphics2D g) {
        if (isIsAlive()) {
            //go right
            if (getDirection() == 3 && GameKeyboardListener.RIGHT == true) {
                if (right < imagesRight.length) {
                    g.drawImage(imagesRight[right], null, getX(), getY());
                    count++;
                    if (count == MAX_COUNT) {
                        right++;
                        count = 0;
                    }
                } else {
                    right = 0;
                    g.drawImage(imagesRight[right], null, getX(), getY());
                }
            } else if (getDirection() == 3 && GameKeyboardListener.RIGHT == false) {
                g.drawImage(imagesRight[0], null, getX(), getY());

                //go left
            } else if (getDirection() == 9 && GameKeyboardListener.LEFT == true) {
                if (left < imagesRight.length) {
                    g.drawImage(imagesLeft[left], null, getX(), getY());
                    count++;
                    if (count == MAX_COUNT) {
                        left++;
                        count = 0;
                    }
                } else {
                    left = 0;
                    g.drawImage(imagesLeft[left], null, getX(), getY());
                }
            } else if (getDirection() == 9 && GameKeyboardListener.LEFT == false) {
                g.drawImage(imagesLeft[0], null, getX(), getY());

                //go up
            } else if (getDirection() == 12 && GameKeyboardListener.UP == true) {
                if (back < imagesRight.length) {
                    g.drawImage(imagesBack[back], null, getX(), getY());
                    count++;
                    if (count == MAX_COUNT) {
                        back++;
                        count = 0;
                    }
                } else {
                    back = 0;
                    g.drawImage(imagesBack[back], null, getX(), getY());
                }
            } else if (getDirection() == 12 && GameKeyboardListener.UP == false) {
                g.drawImage(imagesBack[0], null, getX(), getY());
                //go down
            } else if (getDirection() == 6 && GameKeyboardListener.DOWN == true) {
                if (front < imagesFront.length) {
                    g.drawImage(imagesFront[front], null, getX(), getY());
                    count++;
                    if (count == MAX_COUNT) {
                        front++;
                        count = 0;
                    }
                } else {
                    front = 0;
                    g.drawImage(imagesFront[front], null, getX(), getY());
                }
            } else if (getDirection() == 6 && GameKeyboardListener.DOWN == false) {
                g.drawImage(imagesFront[0], null, getX(), getY());
            }
        } else {
            g.drawImage(vanishImages[vanishIndex], null, getX(), getY());
            vanishCount++;
            if (vanishCount == VANISH_COUNT) {
                vanishIndex++;
                vanishCount = 0;
            }
        }
    }

    /**
     * make the player vanish from the game completely. Activate after the die
     * animation finishes.
     */
    private void vanishCompletely() {
        GameManager.setNumPlayer(GameManager.getNumPlayer() - 1);
        if (GameManager.isMultiplayer()) {
            GameManager.remove(this);
            GameManager.setIsWin(false);
            if (GameManager.getNumPlayer() == 1) {
                GameManager.endGame();
            }
        } else {
            GameManager.setIsWin(false);
            GameManager.endGame();
        }
    }

    @Override
    public void vanish() {
        setIsAlive(false);
        GameManager.getResources().reloadSound();
        GameManager.getResources().getSoundEffects().get("die").play();
        setType(GameObjectType.NULL);
    }

    @Override
    public void update() {
        if (isIsAlive() == false) {
            setDeadTimer(getDeadTimer() - 1);
            if (getDeadTimer() == 0) {
                vanishCompletely();
            }
            return;
        }
        checkMonsterCollision();
        if (GameKeyboardListener.UP == true) {
            sendPlayerDataToNetwork();
            move(12);
        } else if (GameKeyboardListener.DOWN == true) {
            sendPlayerDataToNetwork();
            move(6);
        } else if (GameKeyboardListener.LEFT == true) {
            sendPlayerDataToNetwork();
            move(9);
        } else if (GameKeyboardListener.RIGHT == true) {
            sendPlayerDataToNetwork();
            move(3);
        }
        if (GameKeyboardListener.PLANT_BOMB == true) {
            plantBomb();
            GameKeyboardListener.stopRegisterNewPlantBomb();
        }
    }

    /**
     * Signal the creation of the player to other games in the network.
     *
     * @author khainguyen
     */
    private void sendPlayerDataToNetwork() {
        if (GameManager.isMultiplayer()) {
            Network.sendPlayerLocation(ID, this.getLocation(), (short) direction);
        }
    }

    /**
     * Plant the bomb in the map according to the player's current position and
     * facing. If a bomb is planted, add it to the bomb array and return true.
     * Otherwise, return false
     *
     * @return - boolean, true of can plant a bomb, false otherwise
     * @author khainguyen
     */
    public boolean plantBomb() {
        if (this.bombPlanted >= this.bombLimit) {
            return false;
        } else {
            //get the current grid coordinate of the player
            Point currGrid = GameUtility.GameUtility.toGridCordinate(
                    this.getLocation());
            if (GameManager.getGameMap().getFromMap(currGrid) != null) {
                return false;
            }
            //initiate a new bomb
            Bomb aBomb = new Bomb(bombSize, this);
            GameManager.getGameMap().addToMap(aBomb, currGrid);
            this.bombQueue.add(aBomb);
            this.bombPlanted += 1;
            GameManager.getResources().reloadSound();
            GameManager.getResources().getSoundEffects().get("placeBomb").play();
            if (GameManager.isMultiplayer()) {
                Network.signalPlantBomb(getID());
            }
            return true;
        }
    }

    /**
     * Check if the current player has reached the border or not
     *
     * @return - true if reach the border, false otherwise
     * @author khainguyen
     */
    public boolean reachBorder() {
        // check up
        if (direction == 12) {
            if (this.getY() < speed) {
                return true;
            }
        }
        //check down
        if (direction == 6) {
            if (this.getY() + this.getHeight() + speed > GameUtility.GameUtility.MAP_HEIGHT) {
                return true;
            }
        }
        //check left
        if (direction == 9) {
            if (this.getX() < speed) {
                return true;
            }
        }
        //check right
        if (direction == 3) {
            if (this.getX()
                + this.getWidth() + speed > GameUtility.GameUtility.MAP_WIDTH) {
                return true;
            }
        }
        return false;

    }

    /**
     * Check if the player can move in the current direction or not. That
     * include colliding with anything or reach the border of the game
     *
     * @return - true of the player can move in the current direction, false
     * otherwise
     * @author khainguyen
     */
    public boolean moveable() {
        //check reach border
        if (reachBorder()) {
            return false;
        }
        //get the location of the next spot to move to
        //move up
        Point nextLocation = this.getCenterLocation();
        if (direction == 12) {
            nextLocation = new Point(this.getCenterX(),
                                     this.getCenterY() - speed);
        }
        //move right
        if (direction == 3) {
            nextLocation = new Point(this.getCenterX() + speed,
                                     this.getCenterY());

        }
        //move down
        if (direction == 6) {
            nextLocation = new Point(this.getCenterX(),
                                     this.getCenterY() + speed);

        }
        //move left
        if (direction == 9) {
            nextLocation = new Point(this.getCenterX() - speed,
                                     this.getCenterY());
        }

        // get all objects in a circle of radius tileSize * 2 around the players
        //these objects are those which can possibly colide with the players
        int checkRadius = GameUtility.GameUtility.TILE_SIZE * 2;
        for (GameObject gameObject : GameManager.getGameObjectList()) {
            if (GameUtility.GameUtility.distance(gameObject.getCenterLocation(),
                                                 this.getCenterLocation()) < checkRadius) {
                if ((GameUtility.GameUtility.dectectCollision(
                     gameObject.getCenterLocation(),
                     nextLocation)) && !(GameUtility.GameUtility.dectectCollision(
                                         gameObject.getCenterLocation(),
                                         this.getCenterLocation())) && gameObject.getType() != GameObjectType.POWER_UP && gameObject.getType() != GameObjectType.MONSTER) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Check every update if the player collides with any monster. If true, kill
     * the player.
     */
    public void checkMonsterCollision() {
        for (GameObject gameObject : GameManager.getGameObjectList()) {
            if (GameUtility.GameUtility.dectectCollision(
                    gameObject.getCenterLocation(), getCenterLocation()) && gameObject.getType() == GameObjectType.MONSTER) {
                vanish();
            }
        }
    }

    /**
     * Move the player according to the key pressed.
     *
     * @param moveDirection - the direction of moving as inputed by the player
     * @author khainguyen
     */
    public void move(int moveDirection) {

        //if same or opposite direction, check if movable
        if ((direction - moveDirection) % 6 == 0) {
            this.direction = moveDirection;
            if (moveable()) {
                if (direction == 12) {
                    this.setLocation(new Point(this.getX(), this.getY() - speed));
                }
                //move right
                if (direction == 3) {
                    this.setLocation(new Point(this.getX() + speed, this.getY()));

                }
                //move down
                if (direction == 6) {
                    this.setLocation(new Point(this.getX(), this.getY() + speed));
                }
                //move left
                if (direction == 9) {
                    this.setLocation(new Point(this.getX() - speed, this.getY()));
                }
            }
        } // if it is turning, check if can turn or not. If can turn then turn and move according to the direction before turn, otherwise, just leave the player there
        else {
            Point snapToGridPoint = GameUtility.GameUtility.snapToGrid(
                    this.getLocation());
            Point gridPoint = GameUtility.GameUtility.toGridCordinate(
                    this.getLocation());
            Point nextPoint = new Point(gridPoint.x, gridPoint.y);
            //if the distance is acceptable
            if (GameUtility.GameUtility.distance(snapToGridPoint,
                                                 this.getLocation()) < GameUtility.GameUtility.TILE_SIZE / 2.5) {

                if (moveDirection == 3) {
                    int x = Math.max(0, gridPoint.x + 1);
                    nextPoint = new Point(x, gridPoint.y);
                } else if (moveDirection == 6) {
                    int y = Math.max(0, gridPoint.y + 1);
                    nextPoint = new Point(gridPoint.x, y);
                } else if (moveDirection == 9) {
                    int x = Math.min(19, gridPoint.x - 1);
                    nextPoint = new Point(x, gridPoint.y);
                } else if (moveDirection == 12) {
                    int y = Math.min(19, gridPoint.y - 1);
                    nextPoint = new Point(gridPoint.x, y);
                }
                // if the turn is empty, then snap the player to the grid location
                if (!(GameManager.getGameMap().getFromMap(nextPoint) instanceof Wall) && !(GameManager.getGameMap().getFromMap(
                                                                                           nextPoint) instanceof Bomb)) {
                    if (GameUtility.GameUtility.distance(snapToGridPoint,
                                                         this.getLocation()) < GameUtility.GameUtility.TILE_SIZE / 10) {
                        this.setLocation(snapToGridPoint);
                        this.direction = moveDirection;
                    } else {
                        if (direction == 9 || direction == 3) {
                            int directionOfMovement = (snapToGridPoint.x - getX());
                            directionOfMovement = directionOfMovement / Math.abs(
                                    directionOfMovement);
                            this.setLocation(new Point(
                                    this.getX() + directionOfMovement * speed,
                                    this.getY()));
                        } else if (direction == 12 || direction == 6) {
                            int directionOfMovement = (snapToGridPoint.y - getY());
                            directionOfMovement = directionOfMovement / Math.abs(
                                    directionOfMovement);
                            this.setLocation(new Point(
                                    this.getX(),
                                    this.getY() + directionOfMovement * speed));
                        }
                    }
                }
            }
        }
    }

    //
    //getters and setters
    //
    //
    public boolean isInvulnerable() {
        return invulnerable;
    }

    public short getID() {
        return ID;
    }

    public void setID(short ID) {
        this.ID = ID;
    }

    public void setInvulnerable(boolean invulnerable) {
        this.invulnerable = invulnerable;
    }

    public int getBombSize() {
        return bombSize;
    }

    public void setBombSize(int bombSize) {
        this.bombSize = bombSize;
    }

    public Queue<Bomb> getBombs() {
        return bombQueue;
    }

    public int getBombLimit() {
        return bombLimit;
    }

    public void setBombLimit(int bombLimit) {
        this.bombLimit = bombLimit;
    }

    public int getBombPlanted() {
        return bombPlanted;
    }

    public void setBombPlanted(int bombPlanted) {
        this.bombPlanted = bombPlanted;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

}
