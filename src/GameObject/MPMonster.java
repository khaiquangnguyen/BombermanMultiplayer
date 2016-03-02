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
 * File: MPMonster
 * Description:
 *
 * ****************************************
 */
package GameObject;

import GameManager.GameManager;
import Networking.Network;
import java.awt.Graphics2D;

/**
 * A multi-player monster. This object acts as the avatar of the original
 * monster from the server. It receives data from the network and then acts
 * accordingly to duplicate the actions of the original.
 *
 * @author khainguyen
 */
public class MPMonster extends Monster {
    //the speed of the current monster
    /**
     * Initiate a MPMonster..
     *
     * @param x - the x location in the frame-based coordinate.
     * @param y - the y location in the frame-based coordinate.
     * @param ID - the ID of the monster.
     */
    public MPMonster(int x, int y, int type, int ID) {
        super(x, y, type);
    }

    @Override
    public void vanish() {
        GameManager.setNumMonster(GameManager.getNumMonster() - 1);
        GameManager.remove(this);
    }

    @Override
    public void update() {
        timer += 1;
        setLocation(Network.getMPMonsterLocation(ID));
        setDirection(Network.returnMPMonsterDirection(ID));
    }

    @Override
    public void render(Graphics2D g) {
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
    }

}
