/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2015
 *
 * Name: Khai Nguyen, Khoi Le & Alice Shen
 * Date: Nov 14, 2015
 * Time: 7:46:17 AM
 *
 * Project: csci205_final_project
 * Package: Networking
 * File: GameMapPacket
 * Description:
 *
 * ****************************************
 */
package Networking.Packet;

/**
 * A packet which is used to transfer the game map through the network.
 *
 * @author khainguyen
 */
public class GameMapPacket {
    public String map;

    public GameMapPacket(String map) {
        this.map = map;
    }

    public GameMapPacket() {
    }

}
