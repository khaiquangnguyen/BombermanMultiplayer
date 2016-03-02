/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2015
 *
 * Name: Khai Nguyen, Khoi Le & Alice Shen
 * Date: Nov 14, 2015
 * Time: 10:26:57 AM
 *
 * Project: csci205_final_project
 * Package: Networking
 * File: PlantBombPacket
 * Description:
 *
 * ****************************************
 */
package Networking.Packet;

/**
 * A packet which is used to signal the creation of a bomb in the game.
 *
 * @author khainguyen
 */
public class PlantBombPacket {
    public short ID;

    public PlantBombPacket() {
    }

    public PlantBombPacket(short ID) {
        this.ID = ID;
    }

}
