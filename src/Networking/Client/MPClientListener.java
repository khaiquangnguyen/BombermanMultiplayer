/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2015
 *
 * Name: Khai Nguyen, Khoi Le & Alice Shen
 * Date: Nov 14, 2015
 * Time: 12:56:41 AM
 *
 * Project: csci205_final_project
 * Package: Networking
 * File: MPClientListener
 * Description:
 *
 * ****************************************
 */
package Networking.Client;

import Networking.Network;
import Networking.Packet.ClientIDPacket;
import Networking.Packet.GameMapPacket;
import Networking.Packet.GameStartPacket;
import Networking.Packet.MonsterCreationPacket;
import Networking.Packet.MonsterLocationPacket;
import Networking.Packet.NumPlayerPacket;
import Networking.Packet.PlantBombPacket;
import Networking.Packet.PlayerCreationPacket;
import Networking.Packet.PlayerLocationPacket;
import Networking.Packet.ServerClosedPacket;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import java.awt.Point;

/**
 * The listener for the client.
 *
 * @author khainguyen
 */
public class MPClientListener extends Listener {
    private MPClient client;

    public MPClientListener(MPClient client) {
        this.client = client;
    }

    @Override
    public void connected(Connection c) {
        Network.setIsConnected(true);
    }

    @Override
    public void disconnected(Connection c) {
        Network.setIsConnected(false);
    }

    @Override
    public void received(Connection c, Object o) {
        if (o instanceof ServerClosedPacket) {
            Network.backToClientMenu();
        }
        ////recevie player location packet
        if (o instanceof ClientIDPacket) {
            ClientIDPacket packet = (ClientIDPacket) o;
            Network.setPlayerID(packet.ID);
            System.out.println("reeive client ID: " + packet.ID);
        } else if (o instanceof PlayerLocationPacket) {
            PlayerLocationPacket packet = (PlayerLocationPacket) o;
            Network.storeMPPlayerLocation(packet.ID, new Point(
                                          packet.x,
                                          packet.y), packet.direction);
        }//receive game map packet
        else if (o instanceof GameMapPacket) {
            GameMapPacket packet = (GameMapPacket) o;
            Network.updateMap(packet.map);
        }//recieve player plant bomb packet
        else if (o instanceof PlantBombPacket) {
            PlantBombPacket packet = (PlantBombPacket) o;
            Network.setPlantBomb(packet.ID, true);
        }//receive monster location packet
        else if (o instanceof MonsterLocationPacket) {
            MonsterLocationPacket packet = (MonsterLocationPacket) o;
            Network.storeMonsterLocation(packet.ID, packet.x, packet.y,
                                         packet.direction);
        } //recieve monster creation packet
        else if (o instanceof MonsterCreationPacket) {
            MonsterCreationPacket packet = (MonsterCreationPacket) o;
            Network.receiveMonsterCreation(packet.x, packet.y,
                                           packet.ID,
                                           packet.type);
        }//receive player creation packet
        else if (o instanceof PlayerCreationPacket) {
            PlayerCreationPacket packet = (PlayerCreationPacket) o;
            Network.receivePlayerCreation(packet.ID);
        } else if (o instanceof GameStartPacket) {
            Network.startMultiPlayerGame();
        } else if (o instanceof NumPlayerPacket) {
            NumPlayerPacket numPlayer = (NumPlayerPacket) o;
            Network.setNumPlayer(numPlayer.numPlayer);
        }
    }
}
