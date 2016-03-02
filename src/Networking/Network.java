/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2015
 *
 * Name: Khai Nguyen, Khoi Le & Alice Shen
 * Date: Nov 14, 2015
 * Time: 3:01:18 AM
 *
 * Project: csci205_final_project
 * Package: Networking
 * File: Network
 * Description:
 *
 * ****************************************
 */
package Networking;

import GameManager.GameManager;
import GameObject.MPMonster;
import GameObject.MPPlayer;
import GameObject.Map;
import GameObject.Monster;
import Networking.Client.MPClient;
import Networking.Packet.ClientIDPacket;
import Networking.Packet.GameMapPacket;
import Networking.Packet.GameStartPacket;
import Networking.Packet.MonsterCreationPacket;
import Networking.Packet.MonsterLocationPacket;
import Networking.Packet.NumPlayerPacket;
import Networking.Packet.PlantBombPacket;
import Networking.Packet.PlayerCreationPacket;
import Networking.Packet.PlayerLocationPacket;
import Networking.Server.MPServer;
import com.esotericsoftware.kryonet.Connection;
import java.awt.Point;
import java.util.ArrayList;

/**
 * The network class. Specifically designed as a special extension of
 * GameManager, used only for multi-player.
 *
 * This class is crucial for a multi-player game. The network class acts as the
 * bridge between the main game and other games on the network. The network
 * class will temporary store the updates from other game instances for the
 * multi-player objects in current game instance to update whenever the update
 * function is called.
 *
 * All necessary information sent from the server will be temporary stored here
 * and then access later by the game.
 *
 *
 * @author khainguyen
 */
public class Network {
    /**
     * is server or client, true of server, false if client \
     */
    private static boolean isServer;

    /**
     * the server
     */
    private static MPServer server;
    /**
     * the client
     */
    private static MPClient client;
    /**
     * the map of the game. Will be updated when received the map from server.
     *
     */
    private static Map gameMap;
    /**
     * if current game successufully connect with another game
     *
     */
    private static boolean isConnected;
    /**
     * The location of all monsters in the game.
     *
     */
    private static ArrayList<Point> mpMonsterLocations = new ArrayList<>();
    /**
     * The location of all players from other games which are connecting to the
     * current game.
     *
     */
    private static Point[] mpPlayerLocations = new Point[4];
    /**
     * The direction of all players from other games which are connecting to the
     * current game.
     *
     */
    private static short[] mpPlayerDirection = new short[4];

    /**
     * The direction of all monsters in the game.
     *
     */
    private static ArrayList<Short> mpMonsterDirections = new ArrayList<>();
    /**
     * Store whether the players from other games plant a bomb or not.
     *
     */
    private static boolean[] isPlantBombs = new boolean[4];

    /**
     * The names of all players in the current game.
     */
    private static ArrayList<String> playerNames = new ArrayList<>();
    /**
     * The number of players in the current game.
     */
    private static short numPlayer;

    /**
     * Send the number of players to the clients in the network
     */
    private static void sendNumPlayer(short numPlayer) {
        if (Network.isServer) {
            server.server.sendToAllTCP(new NumPlayerPacket(numPlayer));
        }
    }

    private static void initNetworkData() {
        playerNames = new ArrayList<>();
        numPlayer = 1;
        isPlantBombs = new boolean[]{false, false, false, false};

        mpPlayerDirection = new short[]{0, 0, 0, 0};
        mpPlayerLocations = new Point[]{new Point(0, 0), new Point(
                                        0,
                                        0), new Point(
                                        0, 0), new Point(0, 0)};
        mpMonsterDirections = new ArrayList<>();
        mpMonsterLocations = new ArrayList<>();
        isConnected = false;
        gameMap = null;
        client = null;
        server = null;
    }

    /**
     * Close the server and the client when the network is done.
     */
    public static void closeNetwork() {
        if (server != null) {
            server.server.close();
        }
        if (client != null) {
            client.client.close();
        }
        //clean all the network data, work as a precaution
        initNetworkData();
    }

    /**
     * Initiate the network as the server.
     *
     */
    public static void initNetworkServer() {
        initNetworkData();
        Network.isServer = true;
        server = new MPServer();
    }

    /**
     * initiate the network as the client
     *
     * @param serverIPAddress - the IP address of the server that the client
     * will connect to
     */
    public static void initNetworkClient(String serverIPAddress) {
        initNetworkData();
        Network.isServer = false;
        client = new MPClient(serverIPAddress);
    }

    /**
     * Start the multi-player game. Used only by clients when receive the start
     * game signal from server.
     *
     * @author khainguyen
     */
    public static void startMultiPlayerGame() {
        GameManager.startMultiplayerGame();
    }

    /**
     * Signal the start of a multi-player game. Used only by server to send out
     * the signal to start the game.
     *
     * @author khainguyen
     */
    public static void signalStartMultiplayerGame() {
        server.server.sendToAllTCP(new GameStartPacket());
    }

    /**
     * Give each client which are connecting to the current server an ID. This
     * ID will be used to identify the player in a multi-player game.
     *
     * @author khainguyen
     */
    public static void sendCLientID() {
        short clientID = 1;
        for (Connection c : server.server.getConnections()) {
            server.server.sendToTCP(c.getID(), new ClientIDPacket(clientID));
            clientID += 1;
        }
    }

    //
    //Monster-related functions
    //
    /**
     * create a monster in the current game. The monster is a clone of the
     * primary one from the server, and will be updated according to the primary
     * one
     *
     * @param x - the x location of the monster
     * @param y - the y location of the monster
     * @param ID - the ID of the monster
     * @param type - the type of the monster
     * @author khainguyen
     */
    public static void receiveMonsterCreation(int x, int y, int ID, int type) {
        MPMonster monster = new MPMonster(x, y, type, ID);
        mpMonsterLocations.add(new Point(x, y));
        mpMonsterDirections.add((short) 0);
    }

    /**
     * Signal the creation of a monster to other game instances in the network.
     *
     * @param monster - the monster which is created
     * @author khainguyen
     */
    public static void sendMonsterCreation(Monster monster) {
        //only server can signal spawn monster to client
        if (Network.isServer == true) {
            server.server.sendToAllTCP(
                    new MonsterCreationPacket(monster.getMonsterType(),
                                              monster.getID(), monster.getX(),
                                              monster.getY()));
        }
    }

    /**
     * send the location of the monsters to clients to update the clients'
     * monsters location
     *
     * @param ID - the ID of the monster
     * @param location - the location of the monster
     * @param direction
     * @author khainguyen
     */
    public static void sendMonsterLocationToClients(short ID, Point location,
                                                    short direction) {
        if (Network.isServer == true) {
            server.server.sendToAllTCP(new MonsterLocationPacket(ID,
                                                                 (short) location.x,
                                                                 (short) location.y,
                                                                 direction));
        }
    }

    /**
     * Store a monster location to sync with the server's monsters.
     *
     * @param ID - the ID of the monster
     * @param x - the x location of the monster
     * @param y - the y location of the monster
     * @param direction
     * @author khainguyen
     */
    public static void storeMonsterLocation(int ID, int x, int y,
                                            short direction) {
        mpMonsterLocations.set(ID, new Point(x, y));
        mpMonsterDirections.set(ID, direction);

    }

    /**
     * Get the location of a monster.
     *
     * @param ID - the ID of the monster.
     * @return -a point represents the location of the monster
     * @author khainguyen
     */
    public static Point getMPMonsterLocation(int ID) {
        return mpMonsterLocations.get(ID);
    }

    /**
     * Get the direction of a monster.
     *
     * @param ID - the Id of the monster.
     * @return - the direction the monster is facing.
     * @author khainguyen
     */
    public static short returnMPMonsterDirection(int ID) {
        return mpMonsterDirections.get(ID);
    }

    //
    //Player-related functions
    //
    /**
     * Create a MPPlayer object, which represents a real player in the network
     * in the current game intance. This object will get data of the original
     * one and then duplicates the actions of the original.
     *
     * @param ID - the ID of the original player
     * @author khainguyen
     */
    public static void createMPPlayer(short ID) {
        if (ID != GameManager.getGameID()) {
            MPPlayer mPPlayer = new MPPlayer(Network.returnPlayerLocation(ID).x,
                                             Network.returnPlayerLocation(ID).y,
                                             ID);
        }
    }

    /**
     * Signal to other games in the network that a new player has been created.
     *
     * @param ID - the ID of the player.
     * @author khainguyen
     */
    public static void sendPlayerCreation(short ID) {
        if (Network.isServer == true) {
            server.server.sendToAllTCP(new PlayerCreationPacket(ID));
        } else {
            client.client.sendTCP(new PlayerCreationPacket(ID));
        }
    }

    /**
     * create a new player in the current game.
     *
     * @param ID - the ID of the new player
     * @author khainguyen
     */
    public static void receivePlayerCreation(short ID) {
        createMPPlayer(ID);
    }

    /**
     * Send the location of the current player to other games in the networks
     *
     * @param ID - the ID of the current player
     * @param location - the location of the player
     * @author khainguyen
     */
    public static void sendPlayerLocation(short ID, Point location,
                                          short direction) {
        short x = (short) location.x;
        short y = (short) location.y;
        if (Network.isServer == true) {
            server.server.sendToAllTCP(new PlayerLocationPacket(ID, x, y,
                                                                direction));
        } else {
            client.client.sendTCP(new PlayerLocationPacket(ID, x, y, direction));
        }
    }

    /**
     * update the location of another player in the network.
     *
     * @param ID - the ID of the player
     * @param location - the location of the player with id ID
     * @param direction
     * @author khainguyen
     */
    public static void storeMPPlayerLocation(short ID, Point location,
                                             short direction) {
        Network.mpPlayerLocations[ID] = location;
        Network.mpPlayerDirection[ID] = direction;
    }

    /**
     * return the location of a player with a specific ID in the network.
     *
     * @param ID - the ID of the player
     * @return - the location of the player with id ID
     * @author khainguyen
     */
    public static Point returnPlayerLocation(short ID) {
        return Network.mpPlayerLocations[ID];
    }

    /**
     * Return the current direction of a player with a specific ID in the
     * network.
     *
     * @param ID - the ID of the player
     * @return - the direction of the player.
     * @author khainguyen
     */
    public static short returnPlayerDirection(short ID) {
        return Network.mpPlayerDirection[ID];
    }

    /**
     * Signal the GameManger to go back to the client menu. This only happens
     * when the server rejects connection from clients.
     *
     * @author khainguyen
     */
    public static void backToClientMenu() {
        GameManager.toClientMenu();
    }

    //
    //Map-replated functions
    //
    /**
     * Send the server's game map to the clients' games so that all games use
     * the same map created by the server.
     *
     *
     * @param map -the map of the server, which will be sent to the clients
     * @author khainguyen
     */
    public static void sendMap(Map map) {
        if (Network.isServer == true) {
            server.server.sendToAllTCP(new GameMapPacket(map.getMapString()));
        } else {
            client.client.sendTCP(new GameMapPacket(map.getMapString()));
        }
    }

    /**
     * Update the map to match the map sent by the server.
     *
     * @param mapString - a string, which represents the map
     * @author khainguyen
     */
    public static void updateMap(String mapString) {
        Network.gameMap = new Map(mapString);
    }

    /**
     * Update the list of all players' names in the network.
     *
     * @param name
     */
    public static void receiveClientNamePacket(String name) {
        playerNames.add(name);
        System.out.println(playerNames.toString());
    }

    //
    // Bomb-related activites
    //
    //
    /**
     * Update the plantBomb array when receive signal from another player in the
     * network.
     *
     * @param ID -the ID of the bomber who plants the bomb
     * @param plantBomb - whether a player plants a bomb or not
     * @author khainguyen
     */
    public static void setPlantBomb(short ID, boolean plantBomb) {
        isPlantBombs[ID] = plantBomb;
    }

    /**
     * Check if the player has planted a bomb or not
     *
     * @param ID - the ID of the player
     * @return - true if they player plants a bomb, false otherwise
     * @author khainguyen
     */
    public static boolean isPlantBomb(short ID) {
        return isPlantBombs[ID];
    }

    /**
     * Send a signal over the network that a bomb has been planted
     *
     * @param ID - the ID of the player who plant the bomb and send the signal
     * @author khainguyen
     */
    public static void signalPlantBomb(short ID) {
        if (Network.isServer == true) {
            server.server.sendToAllTCP(new PlantBombPacket(ID));
        } else {
            client.client.sendTCP(new PlantBombPacket(ID));
        }
    }

    //
    //Getters and setters
    //
    public static boolean isIsConnected() {
        return isConnected;
    }

    public static void setIsConnected(boolean isConnected) {
        Network.isConnected = isConnected;
    }

    public static boolean isServer() {
        return isServer;
    }

    public static void setPlayerID(short ID) {
        GameManager.setGameID(ID);
    }

    public static Map getMap() {
        return gameMap;
    }

    public static short getNumPlayer() {
        return numPlayer;
    }

    public static void setNumPlayer(short numPlayer) {
        Network.numPlayer = numPlayer;
        sendNumPlayer(numPlayer);
    }

}
