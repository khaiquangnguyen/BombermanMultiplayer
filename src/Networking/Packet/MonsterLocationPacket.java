/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2015
 *
 * Name: Khai Nguyen, Khoi Le & Alice Shen
 * Date: Nov 14, 2015
 * Time: 2:10:47 PM
 *
 * Project: csci205_final_project
 * Package: Networking
 * File: MonsterLocationPacket
 * Description:
 *
 * ****************************************
 */
package Networking.Packet;

/**
 * A packet which is used to transfer the locations and directions of all
 * monsters in the game.
 *
 * @author khainguyen
 */
public class MonsterLocationPacket {
    public short ID;
    public short x;
    public short y;
    public short direction;

    public MonsterLocationPacket(short ID, short x, short y, short direction) {
        this.ID = ID;
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    public MonsterLocationPacket() {
    }
}
