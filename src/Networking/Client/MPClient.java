/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2015
 *
 * Name: Khai Nguyen, Khoi Le & Alice Shen
 * Date: Nov 14, 2015
 * Time: 12:53:55 AM
 *
 * Project: csci205_final_project
 * Package: Networking
 * File: MPClient
 * Description:
 *
 * ****************************************
 */
package Networking.Client;

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
import com.esotericsoftware.kryonet.Client;
import java.io.IOException;

/**
 * The client for the game
 *
 * @author khainguyen
 */
public class MPClient {
    /**
     * The udp port through which the client will connect with the server.
     */
    private static int udpPort = 10800;
    /**
     * the tcp port through which the client will conenct with the server.
     */
    private static int tcpPort = 10800;
    /**
     * the ip address of the server.
     */
    private String ipConnection = "localhost";
    /**
     * The client object
     */
    public Client client;
    /**
     * The listener for the current client.
     */
    private MPClientListener clientListener;

    public MPClient(String ipConnection) {
        this.ipConnection = ipConnection;
        client = new Client();
        clientListener = new MPClientListener(this);
        registerPacket();
        client.addListener(clientListener);
        new Thread(client).start();
        try {
            client.connect(5000, ipConnection, tcpPort);
            Networking.Network.setIsConnected(true);
        } catch (IOException e) {

        }
    }

    /**
     * register all the packets send and receive through the network
     */
    private void registerPacket() {
        Kryo kryo = client.getKryo();
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
