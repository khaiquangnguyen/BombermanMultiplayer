/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2015
 *
 * Name: Khai Nguyen, Khoi Le & Alice Shen
 * Date: Nov 17, 2015
 * Time: 9:50:19 PM
 *
 * Project: csci205_final_project
 * Package: Networking.Packet
 * File: ClientIDPacket
 * Description:
 *
 * ****************************************
 */
package Networking.Packet;

/**
 * Send the assigned ID to a client.
 *
 * @author khainguyen
 */
public class ClientIDPacket {
    public short ID;

    public ClientIDPacket(short ID) {
        this.ID = ID;
    }

    public ClientIDPacket() {
    }

}
