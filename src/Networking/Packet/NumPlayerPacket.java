/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2015
 *
 * Name: Khai Nguyen, Khoi Le & Alice Shen
 * Date: Nov 17, 2015
 * Time: 10:31:31 PM
 *
 * Project: csci205_final_project
 * Package: Networking.Packet
 * File: NumPlayerPacket
 * Description:
 *
 * ****************************************
 */
package Networking.Packet;

/**
 * Transfer the names of players in the network.
 *
 * @author khainguyen
 */
public class NumPlayerPacket {
    public short numPlayer;

    public NumPlayerPacket(short numPlayer) {
        this.numPlayer = numPlayer;
    }

    public NumPlayerPacket() {
    }

}
