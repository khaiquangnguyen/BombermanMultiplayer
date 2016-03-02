/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2015
 *
 * Name: Khai Nguyen, Khoi Le & Alice Shen
 * Date: Nov 15, 2015
 * Time: 10:39:25 PM
 *
 * Project: csci205_final_project
 * Package: Networking
 * File: PlayerCreationPacket
 * Description:
 *
 * ****************************************
 */
package Networking.Packet;

/**
 * A packet which is used to signal the creation of a player in the multi-player
 * game.
 *
 * @author khainguyen
 */
public class PlayerCreationPacket {
    public short ID;

    public PlayerCreationPacket() {
    }

    public PlayerCreationPacket(short ID) {
        this.ID = ID;
    }
}
