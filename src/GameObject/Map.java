/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2015
 *
 * Name: Khai Nguyen, Khoi Le & Alice Shen
 * Date: Nov 9, 2015
 * Time: 5:31:02 PM
 *
 * Project: csci205_final_project
 * Package: GameObject
 * File: Map
 * Description:
 *
 * ****************************************
 */
package GameObject;

import java.awt.Point;
import java.util.LinkedList;

/**
 * A map object, which stores objects in the grid-based map. The grid-based map
 * is a 2D array, with each item specify a "tile" of the game map
 *
 * The map only contains objects which are stationary, and act as objects which
 * block the player's movement, such as wall and bomb
 *
 * @author khainguyen
 */
public class Map {
    /**
     * The 2-D array which represents the map
     */
    private GameObject[][] gameMap;
    /**
     * The size of the map
     */
    private int mapSize = GameUtility.GameUtility.NUMBER_OF_TILE;
    /**
     * A string, represents the map. This string is used to send the map over
     * the network
     */
    private StringBuilder mapString = new StringBuilder();
    /**
     * All the power ups that will be used in the map
     */
    private LinkedList<PowerUpType> powerUps = new LinkedList<>();
    /**
     * The timer
     */
    private int timer = 120000;
    /**
     * The maximum number that timer can be
     */
    private int maxTimer = 120000;

    /**
     * Initiate the map from scratch.
     *
     * @author khainguyen
     * @param numPlayer - the number of players this map will include. default
     * is 1.
     */
    public Map(short numPlayer) {
        this.gameMap = new GameObject[mapSize][mapSize];
        this.spawnWall();
        if (numPlayer > 0) {
            powerUps.add(PowerUpType.BOMB_LIMIT);
            powerUps.add(PowerUpType.BOMB_LIMIT);
            powerUps.add(PowerUpType.BOMB_RANGE);
            powerUps.add(PowerUpType.BOMB_RANGE);
            powerUps.add(PowerUpType.SPEED);
        }
        if (numPlayer > 1) {
            powerUps.add(PowerUpType.BOMB_LIMIT);
            powerUps.add(PowerUpType.BOMB_LIMIT);
            powerUps.add(PowerUpType.BOMB_RANGE);
            powerUps.add(PowerUpType.BOMB_RANGE);
        }
        if (numPlayer > 2) {
            powerUps.add(PowerUpType.BOMB_LIMIT);
            powerUps.add(PowerUpType.BOMB_LIMIT);
            powerUps.add(PowerUpType.BOMB_RANGE);
            powerUps.add(PowerUpType.SPEED);
        }
        if (numPlayer > 3) {
            powerUps.add(PowerUpType.BOMB_LIMIT);
            powerUps.add(PowerUpType.BOMB_RANGE);
            powerUps.add(PowerUpType.BOMB_RANGE);
            powerUps.add(PowerUpType.SPEED);
        }
        this.addPowerUp();
        this.encodeMap();

    }

    /**
     * Initiate the map from a string. THe string is in a pre-defined format to
     * decode and create the map.
     *
     * @param mapString - the string which represents the map
     * @author khainguyen
     */
    public Map(String mapString) {
        int size = GameUtility.GameUtility.TILE_SIZE;
        this.gameMap = new GameObject[mapSize][mapSize];
        int index = 0;
        int i;
        int j;
        int wall;
        while (true) {
            if (index >= mapString.length() - 1) {
                break;
            }
            j = Integer.parseInt(mapString.substring(index, index + 2));
            index += 2;
            i = Integer.parseInt(mapString.substring(index, index + 2));
            index += 2;
            wall = Integer.parseInt(mapString.substring(index, index + 2));
            index += 2;
            if (wall == 2) {
                gameMap[j][i] = new Wall(false, i * size, j * size);
            } else if (wall == 1) {
                gameMap[j][i] = new Wall(true, i * size, j * size);
            } else if (wall == 11) {
                gameMap[j][i] = new Wall(true, i * size, j * size,
                                         PowerUpType.BOMB_LIMIT);
            } else if (wall == 21) {
                gameMap[j][i] = new Wall(true, i * size, j * size,
                                         PowerUpType.BOMB_RANGE);
            } else if (wall == 31) {
                gameMap[j][i] = new Wall(true, i * size, j * size,
                                         PowerUpType.SPEED);
            }
        }
    }

    /**
     * Add an object to the current map so that the map can also store it. If
     * the location is invalid, return false
     *
     * @param object - the object to be added to the current
     * @param location - the location in the map to be added
     * @return - true of the object can be added successfully, false otherwise
     * @author khainguyen
     */
    public boolean addToMap(GameObject object, Point location) {
        // if the location has already been occupied
        if (gameMap[location.y][location.x] != null) {
            return false;
        }
        //if the location is valid
        if (location.x >= 0 && location.x < mapSize && location.y >= 0 && location.y < mapSize) {
            this.gameMap[location.y][location.x] = object;
            object.setLocation(
                    GameUtility.GameUtility.toFrameCordinate(new Point(
                                    location.x,
                                    location.y)));
            return true;
        } else {
            return false;

        }
    }

    /**
     * get the item at the location from the map and return the item. If there
     * is nothing there or the location is invalid, return null.
     *
     * @param location - the location in the map to get the item
     * @return - the item at the location, null if there is nothing there
     * @author khainguyen
     */
    public GameObject getFromMap(Point location) {
        if (location.x >= 0 && location.x < mapSize && location.y >= 0 && location.y < mapSize) {
            return this.gameMap[location.y][location.x];
        } else {
            return null;
        }
    }

    /**
     * Spawn walls across the map to set up the map. The wall is not spawn in
     * the corners of the map
     *
     *
     * @author khainguyen
     */
    private void spawnWall() {
        int size = GameUtility.GameUtility.TILE_SIZE;

        for (int i = 0; i < mapSize; i++) {
            for (int j = 0; j < mapSize; j++) {
                if (i == 0 && (j == 1 || j == 0 || j == mapSize - 2 || j == mapSize - 1)) {
                } else if (j == 0 && (i == 1 || i == mapSize - 1 || i == mapSize - 2 || i == 0)) {
                } else if (i == mapSize - 1 && (j == 1 || j == 0 || j == mapSize - 1 || j == mapSize - 2)) {
                } else if (j == mapSize - 1 && (i == 1 || i == 0 || i == mapSize - 1 || i == mapSize - 2)) {
                } else if (i % 2 == 1 && j % 2 == 1) {
                    gameMap[j][i] = new Wall(false, i * size, j * size);
                } else if ((int) (Math.random() * 5) % 3 == 0) {
                    gameMap[j][i] = new Wall(true, i * size, j * size);

                }
            }
        }
    }

    /**
     * Encode the map into a string in the form aabbcc aa indicates the x
     * coordinate bb indicates the y coordinate cc indicates what is at [x,y] cc
     * = 00 -> nothing cc = 01 > a normal wall cc = 11 -> a wall with bomb_limit
     * power up cc = 21 -> a wall with bomb_range power up cc = 31 -> a wall
     * with speed power up cc = 02 -> an indestructible wall.
     *
     * @author khainguyen
     */
    private void encodeMap() {
        int size = GameUtility.GameUtility.TILE_SIZE;
        for (int i = 0; i < mapSize; i++) {
            for (int j = 0; j < mapSize; j++) {
                mapString.append(String.format("%02d", j));
                mapString.append(String.format("%02d", i));
                if (i % 2 == 1 && j % 2 == 1) {
                    mapString.append(String.format("%02d", 02));
                } else if (gameMap[j][i] instanceof Wall) {
                    Wall wall = (Wall) gameMap[j][i];
                    if (wall.getHiddenPowerUp() == null) {
                        mapString.append(String.format("%02d", 01));
                    } else if (wall.getHiddenPowerUp() == PowerUpType.BOMB_LIMIT) {
                        mapString.append(String.format("%02d", 11));
                    } else if (wall.getHiddenPowerUp() == PowerUpType.BOMB_RANGE) {
                        mapString.append(String.format("%02d", 21));
                    } else if (wall.getHiddenPowerUp() == PowerUpType.SPEED) {
                        mapString.append(String.format("%02d", 31));
                    }
                } else {
                    mapString.append(String.format("%02d", 00));
                }
            }
        }
    }

    /**
     * Add power up to the map by assign them to random walls. This function
     * must be used after the walls in the map are successfully spawn.
     *
     * @author khainguyen
     */
    private void addPowerUp() {
        for (PowerUpType powerUp : powerUps) {
            while (true) {
                short x = (short) (Math.random() * GameUtility.GameUtility.NUMBER_OF_TILE);
                short y = (short) (Math.random() * GameUtility.GameUtility.NUMBER_OF_TILE);
                if (gameMap[x][y] instanceof Wall) {
                    Wall wall = (Wall) gameMap[x][y];
                    if (wall.isIsDestroyable()) {
                        if (wall.getHiddenPowerUp() == null) {
                            wall.setHiddenPowerUp(powerUp);
                            break;
                        }
                    }
                }
            }
        }
    }

    /**
     * Spawn a new player at the location of the map. The location of the player
     * depends on its ID.
     *
     * @param ID - the ID of the player.
     * @author khainguyen
     */
    public void spawnNewPlayer(short ID) {
//        if (!GameManager.isMultiplayer() || Network.isServer() == true) {
        if (ID == 0) {
            int size = GameUtility.GameUtility.TILE_SIZE;
            for (int i = 0; i < mapSize; i++) {
                for (int j = 0; j < mapSize; j++) {
                    if (gameMap[j][i] == null) {
                        Player player = new Player(i * size, j * size, ID);
                        return;
                    }
                }
            }
        } else if (ID == 1) {
            int size = GameUtility.GameUtility.TILE_SIZE;
            for (int i = mapSize - 1; i > 0; i--) {
                for (int j = mapSize - 1; j > 0; j--) {
                    if (gameMap[j][i] == null) {
                        Player player = new Player(i * size, j * size, ID);
                        return;
                    }
                }
            }
        } else if (ID == 2) {
            int size = GameUtility.GameUtility.TILE_SIZE;
            for (int i = mapSize - 1; i > 0; i--) {
                for (int j = 0; j < mapSize; j++) {
                    if (gameMap[j][i] == null) {
                        Player player = new Player(i * size, j * size, ID);
                        return;
                    }
                }
            }
        } else if (ID == 3) {
            int size = GameUtility.GameUtility.TILE_SIZE;
            for (int i = 0; i < mapSize; i++) {
                for (int j = mapSize - 1; j > 0; j--) {
                    if (gameMap[j][i] == null) {
                        Player player = new Player(i * size, j * size, ID);
                        return;
                    }
                }
            }
        }
    }

    /**
     * Spawn a monster at a random available location of the map.
     *
     * @author khainguyen
     */
    public void spawnMonster() {
        int size = GameUtility.GameUtility.TILE_SIZE;
        for (int i = 1; i < mapSize - 1; i++) {
            for (int j = 1; j < mapSize - 1; j++) {
                if ((int) (Math.random() * 5) % 9 == 0 && gameMap[j][i] == null) {
                    Monster monster = new Monster(i * size, j * size, 0);
                    return;
                }
            }
        }
    }

    /**
     * Remove an object from the map.
     *
     * @param gameObject - the object to be removed
     * @author khainguyen
     */
    public void removeObject(GameObject gameObject) {
        for (int i = 0; i < mapSize; i++) {
            for (int j = 0; j < mapSize; j++) {
                if (gameMap[i][j] == gameObject) {
                    gameMap[i][j] = null;
                }
            }
        }
    }

    /**
     * Update the map. This function mostly is to spawn new monsters every time
     * distance.
     *
     * @author khainguyen
     */
    public void update() {
        if (GameManager.GameManager.isMultiplayer() && Networking.Network.isServer() == false) {
            return;
        }
        timer -= 1;
        if (timer == 0) {
            spawnMonster();
            timer = maxTimer;
            if (GameManager.GameManager.isMultiplayer()) {
                maxTimer -= 4000;
            } else {
                maxTimer -= 2000;
            }
            if (GameManager.GameManager.isMultiplayer()) {
                maxTimer = Math.max(60000, maxTimer);
            } else {
                maxTimer = Math.max(40000, maxTimer);
            }
        }
    }

    public String getMapString() {
        return mapString.toString();
    }

}
