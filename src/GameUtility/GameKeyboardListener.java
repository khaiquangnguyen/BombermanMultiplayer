/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2015
 *
 * Name: Alice Shen
 * Date: Nov 10, 2015
 * Time: 11:45:36 AM *
 * Project: csci205
 * Package: Bomberman
 * File: GameKeyboardListener
 * Description:
 *
 * ****************************************
 */
package GameUtility;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 *
 * @author alice
 */
public class GameKeyboardListener extends KeyAdapter {
    public static boolean UP = false;
    public static boolean DOWN = false;
    public static boolean LEFT = false;
    public static boolean RIGHT = false;
    public static boolean PLANT_BOMB = false;
    public static boolean ENTER = false;
    public static boolean BACK_SPACE = false;
    public static boolean canRegisterNewKey = true;
    public static boolean canRegisterNewPlantBomb = true;
    public static String input = "";

    @Override
    public void keyPressed(KeyEvent e) {
        if (!canRegisterNewKey) {
            return;
        }
        int keys = e.getKeyCode();
        switch (keys) {
            case (KeyEvent.VK_LEFT):
                resetMovementKey();
                LEFT = true;
                break;
            case (KeyEvent.VK_RIGHT):
                resetMovementKey();
                RIGHT = true;
                break;
            case (KeyEvent.VK_UP):
                resetMovementKey();
                UP = true;
                break;
            case (KeyEvent.VK_DOWN):
                resetMovementKey();
                DOWN = true;
                break;
            case (KeyEvent.VK_SPACE):
                if (canRegisterNewPlantBomb) {
                    PLANT_BOMB = true;
                }
                break;
            case (KeyEvent.VK_ENTER):
                ENTER = true;
                break;
            case (KeyEvent.VK_BACK_SPACE):
                if (input.length() > 0) {
                    input = input.substring(0, input.length() - 1);
                }
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keys = e.getKeyCode();
        canRegisterNewKey = true;
        if (keys == KeyEvent.VK_LEFT) {
            LEFT = false;
        } else if (keys == KeyEvent.VK_RIGHT) {
            RIGHT = false;
        } else if (keys == KeyEvent.VK_UP) {
            UP = false;
        } else if (keys == KeyEvent.VK_DOWN) {
            DOWN = false;
        } else if (keys == KeyEvent.VK_SPACE) {
            canRegisterNewPlantBomb = true;
            PLANT_BOMB = false;
        } else if (keys == KeyEvent.VK_ENTER) {
            ENTER = false;
        } else if (keys == KeyEvent.VK_BACK_SPACE) {
            BACK_SPACE = false;

        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (Character.isDigit(e.getKeyChar()) || e.getKeyChar() == '.' || e.getKeyChar() == '/') {
            input += Character.toString(e.getKeyChar());
        }
    }

    /**
     * reset all movement key before each update key input. This is to ensure
     * that only one movement is detected at one time
     */
    private static void resetMovementKey() {
        UP = false;
        DOWN = false;
        LEFT = false;
        RIGHT = false;
    }

    /**
     * Stop the registering of new key until the key is released.
     */
    public static void stopRegisterNewKey() {
        GameKeyboardListener.canRegisterNewKey = false;
        resetMovementKey();
    }

    /**
     * Stop the registering of new plant bomb key until the key is released
     */
    public static void stopRegisterNewPlantBomb() {
        canRegisterNewPlantBomb = false;
        PLANT_BOMB = false;
    }
}
