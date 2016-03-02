/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2015
 *
 * Name: Khai Nguyen, Khoi Le & Alice Shen
 * Date: Nov 14, 2015
 * Time: 3:17:36 AM
 *
 * Project: csci205_final_project
 * Package: Networking
 * File: PlayerLocationPacket
 * Description:
 *
 * ****************************************
 */
package Networking.Packet;

/**
 * The packet which is used to transfer the location and direction of a player
 * through the network.
 *
 * @author khainguyen
 */
public class PlayerLocationPacket {
    public short ID;
    public short x;
    public short y;
    public short direction;

    public PlayerLocationPacket(short ID, short x, short y, short direction) {
        this.ID = ID;
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    public PlayerLocationPacket() {
    }
}
