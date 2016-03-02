/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2015
 *
 * Name: Khai Nguyen, Khoi Le & Alice Shen
 * Date: Nov 13, 2015
 * Time: 8:28:37 PM
 *
 * Project: csci205_final_project
 * Package: Networking
 * File: MPServer
 * Description:
 *
 * ****************************************
 */
package Networking.Server;

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
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Server;
import java.io.IOException;

/**
 * The server for the game
 *
 * @author khainguyen
 */
public class MPServer {
    /**
     * The server object, which is basically the server of the game.
     */
    public Server server;

    /**
     * The listener for the server
     */
    private static MPServerListener serverListener;
    /**
     * The udp port that the server will open to waits for connection.
     */
    private static int udpPort = 10800;
    /**
     * The tcp port that the server will open to waits for connection.
     */
    private static int tcpPort = 10800;

    /**
     * initiate the server and start binding the server to a port to wait for
     * connections from the client.
     */
    public MPServer() {
        server = new Server();
        serverListener = new MPServerListener(this);
        server.addListener(serverListener);
        registerPacket();
        try {
            server.bind(tcpPort);
            Networking.Network.setIsConnected(true);
        } catch (IOException ex) {
        }
        server.start();
    }

    /**
     * register all the packets send and receive through the network
     */
    private void registerPacket() {
        Kryo kryo = server.getKryo();
        kryo.register(PlayerLocationPacket.class);
        kryo.register(GameMapPacket.class);
        kryo.register(PlantBombPacket.class);
        kryo.register(MonsterLocationPacket.class);
        kryo.register(MonsterCreationPacket.class);
        kryo.register(PlayerCreationPacket.class);
        kryo.register(ClientIDPacket.class);
        kryo.register(NumPlayerPacket.class);
        kryo.register(GameStartPacket.class);
        kryo.register(ServerClosedPacket.class);
    }

}
