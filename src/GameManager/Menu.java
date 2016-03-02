/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2015
 *
 * Name: Khai Nguyen, Khoi Le & Alice Shen
 * Date: Nov 15, 2015
 * Time: 9:28:54 PM
 *
 * Project: csci205_final_project
 * Package: GameObject
 * File: Menu
 * Description:
 *
 * ****************************************
 */
package GameManager;

import GameUtility.GameKeyboardListener;
import Networking.Network;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.image.BufferedImage;
import java.util.HashMap;

/**
 *
 * @author Khoi Le
 */
public class Menu extends KeyAdapter {
    private HashMap<String, BufferedImage> resources;
    private int selection = 3;
    private int hostSelection = 3;
    private int clientSelection = 2;
    private MenuState menuState = MenuState.MAIN_MENU;
    private static final int MAX_SELECTION = 2;
    //so that the when you press enter the selections will not overlap
    private static final int A_PRETTY_LARGE_NUMBER = 1000;

    public Menu() {
        resources = GameManager.getResources().getMenu();
    }

    public void render(Graphics g) {
        //Main menu
        if (menuState == MenuState.MAIN_MENU) {
            g.drawImage(resources.get("background"), 0, 0, null);
            if (selection == 0) {
                g.drawImage(resources.get("normalGameSelected"), 0, 0, null);
                g.drawImage(resources.get("battleGame"), 0, 0, null);
                g.drawImage(resources.get("exit"), 0, 0, null);
            } else if (selection == 1) {
                g.drawImage(resources.get("normalGame"), 0, 0, null);
                g.drawImage(resources.get("battleGameSelected"), 0, 0, null);
                g.drawImage(resources.get("exit"), 0, 0, null);
            } else if (selection == 2) {
                g.drawImage(resources.get("normalGame"), 0, 0, null);
                g.drawImage(resources.get("battleGame"), 0, 0, null);
                g.drawImage(resources.get("exitSelected"), 0, 0, null);
            } else if (selection == 3) {
                g.drawImage(resources.get("normalGame"), 0, 0, null);
                g.drawImage(resources.get("battleGame"), 0, 0, null);
                g.drawImage(resources.get("exit"), 0, 0, null);
            }

            //Host menu
        } else if (menuState == MenuState.SELECT_HOST_MENU) {
            selection = A_PRETTY_LARGE_NUMBER;
            g.drawImage(resources.get("backgroundHost"), 0, 0, null);
            if (hostSelection == 0) {
                g.drawImage(resources.get("yesSelected"), 0, 0, null);
                g.drawImage(resources.get("no"), 0, 0, null);
                g.drawImage(resources.get("back"), 0, 0, null);
            } else if (hostSelection == 1) {
                g.drawImage(resources.get("noSelected"), 0, 0, null);
                g.drawImage(resources.get("yes"), 0, 0, null);
                g.drawImage(resources.get("back"), 0, 0, null);
            } else if (hostSelection == 2) {
                g.drawImage(resources.get("yes"), 0, 0, null);
                g.drawImage(resources.get("no"), 0, 0, null);
                g.drawImage(resources.get("backSelected"), 0, 0, null);
            } else if (hostSelection == 3) {
                g.drawImage(resources.get("yes"), 0, 0, null);
                g.drawImage(resources.get("no"), 0, 0, null);
                g.drawImage(resources.get("back"), 0, 0, null);
            }
            //Client menu
        } else if (menuState == MenuState.CLIENT_MENU) {
            hostSelection = A_PRETTY_LARGE_NUMBER;
            g.drawImage(resources.get("client"), 0, 0, null);
            Font font = new Font("Emulator", Font.PLAIN, 18);
            g.setFont(font);
            g.drawString(GameKeyboardListener.input,
                         475 - GameKeyboardListener.input.length() * 9, 425);
            if (clientSelection == 2) {
                g.drawImage(resources.get("clientBack"), 0, 0, null);
                g.drawImage(resources.get("clientEnter"), 0, 0, null);
            } else if (clientSelection == 0) {
                g.drawImage(resources.get("clientBack"), 0, 0, null);
                g.drawImage(resources.get("clientEnterSelected"), 0, 0, null);
            } else if (clientSelection == 1) {
                g.drawImage(resources.get("clientBackSelected"), 0, 0, null);
                g.drawImage(resources.get("clientEnter"), 0, 0, null);
            }
        } else if (menuState == MenuState.PREGAME_MENU) {
            clientSelection = A_PRETTY_LARGE_NUMBER;
            g.drawImage(resources.get("pregame"), 0, 0, null);
            g.drawImage(resources.get("player0"), 0, 0, null);
            if (Network.getNumPlayer() == 2) {
                g.drawImage(resources.get("player1"), 0, 0, null);
            } else if (Network.getNumPlayer() == 3) {
                g.drawImage(resources.get("player1"), 0, 0, null);
                g.drawImage(resources.get("player2"), 0, 0, null);
            } else if (Network.getNumPlayer() == 4) {
                g.drawImage(resources.get("player1"), 0, 0, null);
                g.drawImage(resources.get("player2"), 0, 0, null);
                g.drawImage(resources.get("player3"), 0, 0, null);
            }
        }
        listener();
    }

    private void listener() {
//        // If the game doesn't allow taking any new input yet
//        if (GameKeyboardListener.canRegisterNewKey == false) {
//            return;
//        }
        //The main menu events according to unput
        if (menuState == MenuState.MAIN_MENU) {
            if (selection < MAX_SELECTION && GameKeyboardListener.DOWN == true) {
                selection += 1;
                GameKeyboardListener.stopRegisterNewKey();
            } else if (selection > 0 && selection <= MAX_SELECTION && GameKeyboardListener.UP == true) {
                selection -= 1;
                GameKeyboardListener.stopRegisterNewKey();
            } else if (selection == 3 && GameKeyboardListener.DOWN == true) {
                selection = 0;
                GameKeyboardListener.stopRegisterNewKey();
            } else if (selection == 3 && GameKeyboardListener.UP == true) {
                selection = 2;
                GameKeyboardListener.stopRegisterNewKey();
            }
        }
        if (menuState == MenuState.SELECT_HOST_MENU) {
            if (hostSelection == 3 && GameKeyboardListener.RIGHT == true) {
                hostSelection = 2;
                GameKeyboardListener.stopRegisterNewKey();
            } else if (hostSelection == 3 && GameKeyboardListener.LEFT == true) {
                hostSelection = 0;
                GameKeyboardListener.stopRegisterNewKey();
            } else if (hostSelection < MAX_SELECTION && GameKeyboardListener.RIGHT == true) {
                hostSelection += 1;
                GameKeyboardListener.stopRegisterNewKey();
            } else if (hostSelection > 0 && hostSelection <= MAX_SELECTION && GameKeyboardListener.LEFT == true) {
                hostSelection -= 1;
                GameKeyboardListener.stopRegisterNewKey();
            }
        }
        if (menuState == MenuState.CLIENT_MENU) {
            if (clientSelection == 2 && GameKeyboardListener.RIGHT == true) {
                clientSelection = 1;
                GameKeyboardListener.stopRegisterNewKey();
            } else if (clientSelection == 2 && GameKeyboardListener.LEFT == true) {
                clientSelection = 0;
                GameKeyboardListener.stopRegisterNewKey();
            } else if (clientSelection == 0 && GameKeyboardListener.RIGHT == true) {
                clientSelection = 1;
                GameKeyboardListener.stopRegisterNewKey();
            } else if (clientSelection == 1 && GameKeyboardListener.LEFT == true) {
                clientSelection = 0;
                GameKeyboardListener.stopRegisterNewKey();
            }
        }
    }

    public MenuState getMenuState() {
        return menuState;
    }

    public void setMenuState(MenuState menuState) {
        this.menuState = menuState;
    }

    public int getSelection() {
        return selection;
    }

    public int getHostSelection() {
        return hostSelection;
    }

    public int getClientSelection() {
        return clientSelection;
    }

    public void setSelection(int selection) {
        this.selection = selection;
    }

    public void resetSelection() {
        this.hostSelection = 3;
        this.selection = 3;
        this.clientSelection = 2;
    }

}
