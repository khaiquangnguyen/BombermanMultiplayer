/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2015
 *
 * Name: Khai Nguyen, Khoi Le & Alice Shen
 * Date: Nov 9, 2015
 * Time: 8:34:17 PM
 *
 * Project: csci205_final_project
 * Package: Frame
 * File: GameManager
 * Description:
 *
 * ****************************************
 */
package GameManager;

import GameObject.GameObject;
import GameObject.Map;
import GameUtility.GameKeyboardListener;
import GameUtility.Resources;
import Networking.Network;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.swing.JFrame;

/**
 *
 * @author Khoi Le & Khai Nguyen
 */
public class GameManager extends Canvas implements Runnable {

    /**
     * the main frame of the game
     */
    private JFrame frame;

    /**
     * the number of frame per second of the game. Standard is 60
     */
    private static final int FPS = 60;
    /**
     * the array of all currently active game objects in the game
     */
    private static CopyOnWriteArrayList<GameObject> gameObjectList = new CopyOnWriteArrayList<>();
    /**
     * The map of the current game.
     */
    private static Map gameMap;
    /**
     * The ID of the game. The ID is used only in multi-player game, where the
     * ID of the game will be assigned by the server
     */
    private static short gameID;

    /**
     * The necessary resources for the current game
     */
    private static Resources resources;
    /**
     * the current game state, either MENU or GAME.
     */
    private static GameState state = GameState.MENU;
    /**
     * The menu
     */
    private static Menu menu;
    /**
     * If the game is running
     */
    private boolean isRunning = false;
    /**
     * If the game is in multi-player mode
     */
    private static boolean isMultiplayer = false;
    /**
     * 8-bit font
     */
    private static Font font = new Font("Emulator", Font.PLAIN, 12);

    /**
     * The number of player in the current game
     */
    private static int numPlayer = 0;
    /**
     * If the player win or lose the game
     */
    private static boolean isWin = false;
    /**
     * The number of monster in the game
     */
    private static int numMonster = 0;

    private void initGameFrame() {
        frame = new JFrame("Bomberman");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //the size modification is because the frame also contains the left and right bounderies
        // and the top taskbar
        frame.setSize(
                GameUtility.GameUtility.MAP_WIDTH + GameUtility.GameUtility.TILE_SIZE * 2,
                GameUtility.GameUtility.MAP_HEIGHT + 35);
        frame.setLayout(new BorderLayout());
        frame.add(this, BorderLayout.CENTER);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    /**
     * Initiate the game. Initiates all necessary prerequisites for the game.
     *
     */
    public GameManager() {
        initGameFrame();
        resources = new Resources();
        menu = new Menu();
        addKeyListener(new GameKeyboardListener());
    }

    /**
     * Start a multi-player game
     */
    public static void startMultiplayerGame() {
        resources.reloadSound();
        resources.getSoundEffects().get("start").play();
        if (Network.isServer()) {
            Network.sendCLientID();
            if (Network.getNumPlayer() == 1) {
                return;
            }
            gameMap = new Map(Network.getNumPlayer());
            gameMap.spawnMonster();
            gameMap.spawnMonster();
            gameMap.spawnMonster();
            gameMap.spawnMonster();
            gameMap.spawnMonster();
            Network.sendMap(gameMap);
            Network.signalStartMultiplayerGame();
        } else {
            while (true) {
                gameMap = Network.getMap();
                if (gameMap != null) {
                    break;
                }
            }
        }
        state = GameState.GAME;
        gameMap.spawnNewPlayer(gameID);
        Network.sendPlayerCreation(gameID);
    }

    /**
     * start a single player game
     */
    private static void startSinglePlayerGame() {
        cleanGame();
        state = GameState.GAME;
        resources.reloadSound();
        resources.getSoundEffects().get("start").play();
        gameMap = new Map((short) 1);
        gameMap.spawnMonster();
//        gameMap.spawnMonster();
//        gameMap.spawnMonster();
        gameMap.spawnNewPlayer(gameID);
    }

    /**
     * To Select host menu, where the player chooses to be the host or the
     * client.
     *
     */
    public static void toSelectHostMenu() {
        cleanGame();
        state = GameState.MENU;
        menu.resetSelection();
        //get the IP Mddress
        try {
            System.out.println(Inet4Address.getLocalHost().getHostAddress());
        } catch (UnknownHostException ex) {
        }
        isMultiplayer = true;
        menu.setMenuState(MenuState.SELECT_HOST_MENU);
        GameKeyboardListener.input = "";
    }

    /**
     * Prepare the host and then changes directly to the pregame-menu. There is
     * no actual "host menu".
     */
    public static void toHostMenu() {
        state = GameState.MENU;
        Network.initNetworkServer();
        menu.resetSelection();
        toPreMultiGameMenu();
    }

    /**
     * Change to the client menu.
     */
    public static void toClientMenu() {
        state = GameState.MENU;
        menu.resetSelection();
        menu.setMenuState(MenuState.CLIENT_MENU);
    }

    /**
     * Change to the main menu.
     */
    private static void toMainMenu() {
        menu.resetSelection();
        state = GameState.MENU;
        isMultiplayer = false;
        menu.setMenuState(MenuState.MAIN_MENU);
    }

    /**
     * Change to the pregame menu, where players can see each others and the
     * host can initiate the game.
     */
    private static void toPreMultiGameMenu() {
        menu.resetSelection();
        menu.setMenuState(MenuState.PREGAME_MENU);
    }

    private static void toEndScreen() {
        menu.resetSelection();
        state = GameState.END;
        if (isWin) {
            resources.reloadSound();
            resources.getSoundEffects().get("win").play();
        } else {
            resources.reloadSound();
            resources.getSoundEffects().get("lose").play();
        }
    }

    /**
     * The main game loop which updates game logic and renders images 60 times
     * every second.
     */
    @Override
    public void run() {
        long lastTime = System.nanoTime();
        //the numberof nanosecond of each update. Each update is for the game to update every game oject's logic
        double timePerUpdate = 1E9 / (double) FPS;
        long lastTimer = System.currentTimeMillis();
        double delta = 0;
        resources.reloadSound();
        resources.getSoundEffects().get("background").loop();
        while (isRunning) {
            long now = System.nanoTime();
            delta += (now - lastTime) / timePerUpdate;
            lastTime = now;
            boolean shouldRender = false;
            boolean shouldUpdate = false;
            while (delta >= 1) {
                delta -= 1;
                shouldRender = true;
                shouldUpdate = true;
            }
            try {
                Thread.sleep(1);
            } catch (InterruptedException ex) {
            }
            if (shouldRender) {
                render();
            }
            if (shouldUpdate) {
                update();
            }
        }
    }

    /**
     * Start the game loop
     */
    public synchronized void start() {
        isRunning = true;
        new Thread(this).start();
    }

    /**
     * Stop the game loop
     */
    private synchronized void stop() {
        isRunning = false;
    }

    /**
     * the update logic for the menu.
     */
    private void menuUpdate() {
        //Normal game
        if (GameKeyboardListener.ENTER && menu.getSelection() == 0) {
            startSinglePlayerGame();
            //Multiplayer game
        } else if (menu.getSelection() == 1 && GameKeyboardListener.ENTER) {
            toSelectHostMenu();
        } //Exit
        else if (GameKeyboardListener.ENTER && menu.getSelection() == 2) {
            stop();
            frame.dispose();
        }
        //select host option
        if (menu.getHostSelection() == 0 && GameKeyboardListener.ENTER) {
            toHostMenu();
            //Client option
        } else if (GameKeyboardListener.ENTER && menu.getHostSelection() == 1) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
            }
            toClientMenu();

        } else if (GameKeyboardListener.ENTER && menu.getHostSelection() == 2) {
            toMainMenu();
        }
        //Enter IP
        if (GameKeyboardListener.ENTER && menu.getClientSelection() == 0) {
            Network.initNetworkClient(GameKeyboardListener.input);
            if (Network.isIsConnected()) {
                toPreMultiGameMenu();
            }
        } else if (GameKeyboardListener.ENTER && menu.getClientSelection() == 1) {
            toSelectHostMenu();
        }
        //
        //
        //
        if (GameKeyboardListener.ENTER && menu.getMenuState() == MenuState.PREGAME_MENU && Network.isServer()) {
            startMultiplayerGame();
        }
    }

    /**
     * the update logic for the game.
     */
    private void gameUpdate() {
        for (GameObject gameObject : gameObjectList) {
            gameObject.update();
        }
        gameMap.update();
    }

    /**
     * the update logic for the game. The update depends on the game state. If
     * the state is MENU, then update according to the menu update logic. If the
     * state is GAME, then update according to the game update logic.
     */
    private void update() {
        if (state == GameState.MENU) {
            menuUpdate();
        } else if (state == GameState.GAME) {
            gameUpdate();
        } else {
            if (GameKeyboardListener.ENTER) {
                cleanGame();
                toMainMenu();
            }
        }
    }

    /**
     * render the images of all objects in the game.
     */
    private void render() {
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
            return;
        }
        do {
            Graphics2D g = (Graphics2D) bs.getDrawGraphics();
            super.paint(g);
            if (state == GameState.GAME) {
                g.drawImage(resources.getBackground(), null, 0, 0);
                g.drawImage(resources.getIcons().get("monster"), null, 0, 0);
                g.setColor(Color.WHITE);
                g.setFont(font);
                g.drawString("" + numMonster, 910, 35);
                if (numPlayer == 1) {
                    g.drawImage(resources.getIcons().get("player0"), null, 0, 0);
                } else if (numPlayer == 2) {
                    g.drawImage(resources.getIcons().get("player0"), null, 0, 0);
                    g.drawImage(resources.getIcons().get("player1"), null, 0, 0);
                } else if (numPlayer == 3) {
                    g.drawImage(resources.getIcons().get("player0"), null, 0, 0);
                    g.drawImage(resources.getIcons().get("player1"), null, 0, 0);
                    g.drawImage(resources.getIcons().get("player2"), null, 0, 0);
                } else if (numPlayer == 4) {
                    g.drawImage(resources.getIcons().get("player0"), null, 0, 0);
                    g.drawImage(resources.getIcons().get("player1"), null, 0, 0);
                    g.drawImage(resources.getIcons().get("player2"), null, 0, 0);
                    g.drawImage(resources.getIcons().get("player3"), null, 0, 0);
                }

                for (GameObject gameObject : gameObjectList) {
                    gameObject.render(g);
                }

            } else if (state == GameState.MENU) {
                menu.render(g);
            } else {
                if (isWin) {
                    g.drawImage(resources.getMenu().get("win"), null, 0, 0);
                } else {
                    g.drawImage(resources.getMenu().get("lose"), null, 0, 0);
                }
            }
            g.dispose();
            bs.show();
        } while (bs.contentsLost());
    }

    /**
     * remove an object from the game by removing the objects from the game
     * object lists and from the map. If the object is a monster or player, then
     * it also checks if the player has won or lose the game
     *
     * @param gameObject - the game object to be removed.
     */
    public static void remove(GameObject gameObject) {
        if (gameObject != null) {
            gameMap.removeObject(gameObject);
            gameObjectList.remove(gameObject);
        }
    }

    /**
     * Activate when the the player wins or lose the game.
     *
     */
    public static void endGame() {
        //reset everything
        getGameObjectList().clear();
        gameID = 0;
        isMultiplayer = false;
        numPlayer = 0;
        numMonster = 0;
        toEndScreen();
    }

    /**
     * Clear the game of old data attributes after each game.
     */
    private static void cleanGame() {
        //reset all gameManager objects
        getGameObjectList().clear();
        gameID = 0;
        isMultiplayer = false;
        numPlayer = 0;
        numMonster = 0;
        Network.closeNetwork();
        setIsWin(true);
    }

    //
    //
    //all setters and getters of the game manager.
    //
    public static GameState getState() {
        return state;
    }

    public static boolean isMultiplayer() {
        return isMultiplayer;
    }

    public static void setIsMultiplayer(boolean isMultiplayer) {
        GameManager.isMultiplayer = isMultiplayer;
    }

    public static short getGameID() {
        return gameID;
    }

    public static void setGameID(short gameID) {
        GameManager.gameID = gameID;
    }

    public static void addToObjectList(GameObject gameObject) {
        GameManager.gameObjectList.add(gameObject);
    }

    public static CopyOnWriteArrayList<GameObject> getGameObjectList() {
        return gameObjectList;
    }

    public static Resources getResources() {
        return resources;
    }

    public static Map getGameMap() {
        return gameMap;
    }

    public static int getNumPlayer() {
        return numPlayer;
    }

    public static void setNumPlayer(int numPlayer) {
        GameManager.numPlayer = numPlayer;
    }

    public static void setIsWin(boolean isWin) {
        GameManager.isWin = isWin;
    }

    public static int getNumMonster() {
        return numMonster;
    }

    public static void setNumMonster(int numMonster) {
        GameManager.numMonster = numMonster;
    }

    public static void setGameMap(Map gameMap) {
        GameManager.gameMap = gameMap;
    }

    //
    //
    //Start the game
    //
    //
    public static void main(String[] args) throws InterruptedException {
        new GameManager().start();
    }

}
