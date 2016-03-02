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
import Networking.Network;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * A multiplayer-player. This player acts as the avatar of the real player
 * controlled by another person in the network in the current game instance. It
 * receives data from the network and acts accordingly to duplicate the acti5on
 * of the original.
 *
 * @author khainguyen
 */
public class MPPlayer extends Player {

    /**
     * Check if the MPPlayer is moving to assign animation correctly.
     */
    private boolean moving = false;

    /**
     * Initiate a MPplayer.
     *
     * @param x - the x location in the frame-based coordinate.
     * @param y - the y location in the frame-based coordinate.
     * @param ID - the ID of the player.
     */
    public MPPlayer(int x, int y, short ID) {
        super(x, y, ID);
        loadImages(imagesFront, "F");
        loadImages(imagesLeft, "L");
        loadImages(imagesRight, "R");
        loadImages(imagesBack, "B");
    }

    private void loadImages(BufferedImage[] images, String direction) {
        switch (this.ID) {
            case 0:
                for (int i = 0; i < images.length; i++) {
                    String fileName = direction + (i + 1);
                    images[i] = GameManager.getResources().getPlayerImages().get(
                            fileName);
                }
                vanishImages = GameManager.getResources().getPlayerVanishImages();
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
    public void update() {
        if (isIsAlive() == false) {
            setDeadTimer(getDeadTimer() - 1);
            if (getDeadTimer() == 0) {
                vanishCompletely();
            }
            return;
        }
        checkMonsterCollision();
        if (getX() == Network.returnPlayerLocation(getID()).x && getY() == Network.returnPlayerLocation(
                getID()).y) {
            moving = false;
        } else {
            moving = true;
        }
        this.setLocation(Network.returnPlayerLocation(
                getID()));
        this.setDirection(Network.returnPlayerDirection(getID()));

        if (Network.isPlantBomb(getID())) {
            plantBomb();
            Network.setPlantBomb(getID(), false);
        }
    }

    @Override
    public void render(Graphics2D g) {
        if (isIsAlive()) {
            //go right
            if (getDirection() == 3 && moving == true) {
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
            } else if (getDirection() == 3 && moving == false) {
                g.drawImage(imagesRight[0], null, getX(), getY());

                //go left
            } else if (getDirection() == 9 && moving) {
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
            } else if (getDirection() == 9 && !moving) {
                g.drawImage(imagesLeft[0], null, getX(), getY());

                //go up
            } else if (getDirection() == 12 && moving) {
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
            } else if (getDirection() == 12 && !moving) {
                g.drawImage(imagesBack[0], null, getX(), getY());

                //go down
            } else if (getDirection() == 6 && moving == true) {
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
            } else if (getDirection() == 6 && moving == false) {
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
        GameManager.remove(this);
        if (GameManager.getNumPlayer() == 1) {
//            GameManager.setIsWin(true);
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
}
