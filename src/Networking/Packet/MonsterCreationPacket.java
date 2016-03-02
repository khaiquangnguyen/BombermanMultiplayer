/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2015
 *
 * Name: Khai Nguyen, Khoi Le & Alice Shen
 * Date: Nov 14, 2015
 * Time: 2:16:12 PM
 *
 * Project: csci205_final_project
 * Package: Networking
 * File: MonsterCreationPacket
 * Description:
 *
 * ****************************************
 */
package Networking.Packet;

/**
 * Signal the creation of a monster.
 *
 * @author khainguyen
 */
public class MonsterCreationPacket {
    public int type;
    public int ID;
    public int x;
    public int y;

    public MonsterCreationPacket() {
    }

    public MonsterCreationPacket(int type, int ID, int x, int y) {
        this.type = type;
        this.ID = ID;
        this.x = x;
        this.y = y;
    }

}
