/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p2p_filesharing;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Scorpius
 */
public class Node {

    private String ip;
    private int port;
    private String name;

    private DatagramSocket datagramSocket;

    private ArrayList<Neighbour> neighbours;

    public Node(String ip, int port, String name, DatagramSocket datagramSocket) {
        this.port = port;
        this.ip = ip;
        this.name = name;
        this.neighbours = new ArrayList<Neighbour>();

        this.datagramSocket = datagramSocket;
    }

    //implement register leave 
    public void sendPacket(String message, String ip, int port) {

        byte[] messageContentBytes = message.getBytes();
        DatagramPacket packet = new DatagramPacket(messageContentBytes, messageContentBytes.length);

        try {
            packet.setAddress(InetAddress.getByName(ip));
            packet.setPort(port);

            getDatagramSocket().send(packet);
        } catch (UnknownHostException ex) {

        } catch (IOException ex) {
        }
    }

    public void register() {

        String register_msg = "REG " + getIp() + " " + getPort() + " " + getName();
        register_msg = this.getlength(register_msg) + " " + register_msg;
        System.out.println(register_msg);
        this.sendPacket(register_msg, "127.0.0.1", 55555);

    }
    
    public void leave(){
        String leaveMessage = "UNREG " + getIp() + " " + getPort()+ " " + getName() ;
        leaveMessage =  this.getlength(leaveMessage) + " " + leaveMessage;
        System.out.println(leaveMessage);
        this.sendPacket(leaveMessage, Constants.BS_IP, Constants.BS_PORT);
    }

    void print_neighbours() {
        // print neighbours

    }

    public String getlength(String msg) {

        String length = "0000";
        int x = msg.length() + 5;
        if (x < 10 && x > 0) {
            length = "000" + x;
        }
        if (x >= 10 && x < 100) {
            length = "00" + x;

        }
        if (x >= 100 && x < 999) {
            length = "0" + x;

        }
        if (x >= 1000) {
            length = "" + x;

        }

        return length;
    }

    /**
     * @return the ip
     */
    public String getIp() {
        return ip;
    }

    /**
     * @param ip the ip to set
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * @return the port
     */
    public int getPort() {
        return port;
    }

    /**
     * @param port the port to set
     */
    public void setPort(int port) {
        this.port = port;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the datagramSocket
     */
    public DatagramSocket getDatagramSocket() {
        return datagramSocket;
    }

    /**
     * @param datagramSocket the datagramSocket to set
     */
    public void setDatagramSocket(DatagramSocket datagramSocket) {
        this.datagramSocket = datagramSocket;
    }

    /**
     * @return the neighbours
     */
    public ArrayList<Neighbour> getNeighbours() {
        return neighbours;
    }

    /**
     * @param neighbours the neighbours to set
     */
    public void setNeighbours(ArrayList<Neighbour> neighbours) {
        this.neighbours = neighbours;
    }
}
