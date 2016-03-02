/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2015
 *
 * Name: Khai Nguyen, Khoi Le & Alice Shen
 * Date: Nov 5, 2015
 * Time: 10:11:19 AM
 *
 * Project: csci205_final_project
 * Package: GameObject
 * File: Monster
 * Description:
 *
 * ****************************************
 */
package GameObject;

import GameManager.GameManager;
import Networking.Network;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;

/**
 * The monster object for the game.
 *
 * @author khainguyen
 */
public class Monster extends GameObject {
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
    private int vanishCount;
    private static BufferedImage[] vanishImages = GameManager.getResources().getMonsterVanishImages();
    /**
     * The speed of the monster.
     */
    private int speed;
    /**
     * The direction of the monster.
     */
    private int direction;

    /**
     * The ID of the monster. Used to differentiate between monsters to
     * correctly handle each in a multiplayer game.
     */
    short ID = 0;
    /**
     * Time until a monster can change direction.
     */
    private int timeTillChanngeDirection = 150;
    /**
     * If can change direction.
     *
     */
    private boolean canChangeDirection = true;
    /**
     * The timer
     *
     */
    int timer;
    /**
     * The previous location before moving to a new location.
     *
     */
    Point lastLocation = new Point(0, 0);
    /**
     * The type of monster. Stored as an integer for easier network management.
     * default is 0.
     *
     */
    private int monsterType = 0;

    /**
     * the list of animations
     *
     */
    BufferedImage[] imagesFront = new BufferedImage[4];
    BufferedImage[] imagesLeft = new BufferedImage[4];
    BufferedImage[] imagesRight = new BufferedImage[4];
    BufferedImage[] imagesBack = new BufferedImage[4];
    /**
     * The animation index.
     */
    int front = 0;
    int left = 0;
    int right = 0;
    int back = 0;
    /**
     * The animation render rate.
     */
    int count = 0;
    /**
     * The maximum render rate.
     */
    static final int MAX_COUNT = 7;

    /**
     * Initiate a monster.
     *
     * @param x - the x location in the frame-based coordinate.
     * @param y - the y location in the frame-baed coordinate.
     * @param type - the type of monster.
     * @author khainguyen
     */
    public Monster(int x, int y, int type) {
        this.speed = 2;
        this.direction = 3;
        this.setType(GameObjectType.MONSTER);
        this.setX(x);
        this.setY(y);
        this.ID = (short) GameManager.getNumMonster();
        GameManager.setNumMonster(GameManager.getNumMonster() + 1);
        GameManager.getGameObjectList().add(this);
        loadAnimations();
        if (GameManager.isMultiplayer() && Network.isServer() == true) {
            Network.sendMonsterCreation(this);
        }
    }

    /**
     * Load all the necessary animation for the current monster.
     *
     * @author khainguyen
     *
     */
    private void loadAnimations() {
        loadImages(imagesFront, "F");
        loadImages(imagesLeft, "L");
        loadImages(imagesRight, "R");
        loadImages(imagesBack, "B");
    }

    /**
     * Load all the necessary image for a single animation.
     *
     * @param images - the image buffer to be loaded to.
     * @param direction - the direction that the animation represents.
     */
    private void loadImages(BufferedImage[] images, String direction) {
        for (int i = 0; i < images.length; i++) {
            String fileName = direction + (i + 1);
            images[i] = GameManager.getResources().getMonsterImages().get(
                    fileName);
        }

    }

    /**
     * Make the monster walk randomly around the map.
     *
     * @author khainguyen
     *
     */
    public void randomWalk() {
        if (getX() % GameUtility.GameUtility.TILE_SIZE < 5 && getY() % GameUtility.GameUtility.TILE_SIZE < 5) {
            if (canChangeDirection) {
                direction = (int) (Math.random() * 3);
                direction += 1;
                direction *= 3;
                canChangeDirection = false;
            }
        }
        move(direction);
        if (timer >= timeTillChanngeDirection) {
            canChangeDirection = true;
            timer = 0;
        }

        if (lastLocation.x == this.getLocation().x && lastLocation.y == this.getLocation().y) {
            direction = (direction + 3) % 15;
            canChangeDirection = false;

        }
        lastLocation = this.getLocation();

    }

    @Override
    public void vanish() {
        beginVanish = true;
    }

    public void vanishCompletely() {
        GameManager.remove(this);
        GameManager.setNumMonster(GameManager.getNumMonster() - 1);
        if (GameManager.getNumMonster() == 0 && GameManager.isMultiplayer() == false) {
            GameManager.setIsWin(true);
            GameManager.endGame();
        }

    }

    @Override
    public void update() {
        timer += 1;
        if (beginVanish) {
            timeTillVanish -= 1;
            if (timeTillVanish == 0) {
                vanishCompletely();
            }
        } else {
            randomWalk();
            if (!GameManager.isMultiplayer() == false && Network.isServer() == true) {
                Network.sendMonsterLocationToClients(ID,
                                                     getLocation(),
                                                     (short) direction);
            }
        }
    }

    @Override
    public void render(Graphics2D g) {
        if (!beginVanish) {
            //go right
            if (getDirection() == 3) {
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

                //go left
            } else if (getDirection() == 9) {
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

                //go up
            } else if (getDirection() == 12) {
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

                //go down
            } else {
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
     * Check if the current monster. has reached the border or not
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
     * Check if the monster can move in the current direction or not. That
     * include colliding with anything or reach the border of the game
     *
     * @return - true of the player can move in the current direction, false
     * otherwise
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
                                         this.getCenterLocation())) && gameObject.getType() != GameObjectType.POWER_UP && gameObject.getType() != GameObjectType.MONSTER && gameObject.getType() != GameObjectType.PLAYER) {
                    return false;
                }
            }
        }
        return true;

    }

    /**
     * Move the monster in a certain direction.
     *
     * @param moveDirection - the direction to move.
     */
    public void move(int moveDirection) {
        //if same or opposite direction, check if movable
        if ((direction - moveDirection) % 6 == 0) {
            this.direction = moveDirection;
            if (moveable()) {
                if (direction == 12) {
                    this.setLocation(new Point(this.getX(),
                                               this.getY() - speed));
                }
                //move right
                if (direction == 3) {
                    this.setLocation(new Point(this.getX() + speed,
                                               this.getY()));

                }
                //move down
                if (direction == 6) {
                    this.setLocation(new Point(this.getX(),
                                               this.getY() + speed));
                }
                //move left
                if (direction == 9) {
                    this.setLocation(new Point(this.getX() - speed,
                                               this.getY()));
                }

            }
        } // if it is turning, check if can turn or not. If can turn then turn and move according to the direction before turn, otherwise, just leave the monster there
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
                // if the turn is empty, then snap the monster to the grid location
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

    public short getID() {
        return ID;
    }

    public void setID(short ID) {
        this.ID = ID;
    }

    public int getTimeTillChanngeDirection() {
        return timeTillChanngeDirection;
    }

    public void setTimeTillChanngeDirection(int timeTillChanngeDirection) {
        this.timeTillChanngeDirection = timeTillChanngeDirection;
    }

    public boolean isCanChangeDirection() {
        return canChangeDirection;
    }

    public void setCanChangeDirection(boolean canChangeDirection) {
        this.canChangeDirection = canChangeDirection;
    }

    public int getTimer() {
        return timer;
    }

    public void setTimer(int timer) {
        this.timer = timer;
    }

    public Point getLastLocation() {
        return lastLocation;
    }

    public void setLastLocation(Point lastLocation) {
        this.lastLocation = lastLocation;
    }

    public int getMonsterType() {
        return monsterType;
    }

    public void setMonsterType(int monsterType) {
        this.monsterType = monsterType;
    }

}
