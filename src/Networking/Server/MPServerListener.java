/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2015
 *
 * Name: Khai Nguyen, Khoi Le & Alice Shen
 * Date: Nov 13, 2015
 * Time: 8:53:12 PM
 *
 * Project: csci205_final_project
 * Package: Networking
 * File: MPServerListener
 * Description:
 *
 * ****************************************
 */
package Networking.Server;

import GameManager.GameState;
import Networking.Network;
import Networking.Packet.PlantBombPacket;
import Networking.Packet.PlayerCreationPacket;
import Networking.Packet.PlayerLocationPacket;
import Networking.Packet.ServerClosedPacket;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import java.awt.Point;

/**
 * The listener for the server
 *
 * @author khainguyen
 */
public class MPServerListener extends Listener {

    private MPServer server;

    public MPServerListener(MPServer server) {
        this.server = server;
    }

    @Override
    public void connected(Connection c) {
        Network.setIsConnected(true);
        //If the game already has 4 players, then block connection
        if (server.server.getConnections().length == 4) {
            server.server.sendToTCP(c.getID(), new ServerClosedPacket());
            c.close();
        }
        //if the game already started, also block the connection
        if (GameManager.GameManager.getState() == GameState.GAME) {
            server.server.sendToTCP(c.getID(), new ServerClosedPacket());
            c.close();
        }
        Network.setNumPlayer((short) (server.server.getConnections().length + 1));

    }

    @Override
    public void disconnected(Connection c) {
        Network.setIsConnected(false);
        Network.setNumPlayer((short) (server.server.getConnections().length + 1));
    }

    @Override
    public void received(Connection c, Object o) {
        //receive update location packet
        if (o instanceof PlayerLocationPacket) {
            PlayerLocationPacket packet = (PlayerLocationPacket) o;
            Network.storeMPPlayerLocation(packet.ID, new Point(
                                          packet.x,
                                          packet.y), packet.direction);
            Network.sendPlayerLocation(packet.ID, new Point(
                                       packet.x, packet.y), packet.direction);
        } //receive plant bomb packet
        else if (o instanceof PlantBombPacket) {
            PlantBombPacket packet = (PlantBombPacket) o;
            Network.setPlantBomb(packet.ID, true);
        } //recieve player creation packet
        else if (o instanceof PlayerCreationPacket) {
            PlayerCreationPacket packet = (PlayerCreationPacket) o;
            Network.receivePlayerCreation(packet.ID);
            Network.sendPlayerCreation(packet.ID);
        }
    }
}
