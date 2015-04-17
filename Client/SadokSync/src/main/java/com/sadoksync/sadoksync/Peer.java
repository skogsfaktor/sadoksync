/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sadoksync.sadoksync;

import com.sadoksync.msg.ClientRemoteInterface;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Pontus
 */
public class Peer {

    Comunity com;
    ClientInterface cri;
    String nick;
    Lobby lb;
    Client cli;
    Properties prop;

    String myVlcPath;
    String myIP;

    public Peer() {


    }

    void setNick(String me) {
        this.nick = me;
        com.setNick(this.nick);
        com.setCri(this.cri);
    }

    void createComunity(String cname, String topic) {
        com.create(cname, cri, topic, nick);
    }

    void registerComunity(String rhost, String registry, int port) {
        //com.Register(rhost, registry, port);

        RegistryConnecter rc = new RegistryConnecter(rhost, registry, port);

        if (rc.Connect()) {
            rc.register(com);
        };
    }

    void registerComunity(String rhost, String registry) {
        this.registerComunity(rhost, registry, 1099);
    }

    void registerComunity() {
        this.registerComunity("localhost", "Sadocsynk", 1099);
    }

    void setComunityTopic(String topic) {
        //System.out.println("Peer: setComunityTopic:" + topic);
        com.setTopic(topic);
    }

    void findAllComunity(String rhost, String registry, int port) {
        //com.findAll(rhost, service, port, cri);

        RegistryConnecter rc = new RegistryConnecter(rhost, registry, port);

        if (rc.Connect()) {
            rc.getAll(cri);
        };
    }

    void joinComunity(String cname, String rhost, String registry, int port) {
        //System.out.println("Peer: findComunity:" + cname);
        //com.find(cname, cri, rhost, service, port);

        RegistryConnecter rc = new RegistryConnecter(rhost, registry, port);

        if (rc.Connect()) {
            rc.getComunity(cname, cri);
        };
    }

    String getNick() {
        return nick;
    }

    void setLobby(Lobby lb) {
        this.lb = lb;
        cri.setLobby(lb);
    }

    void setProp(Properties prop) {
        this.prop = prop;
    }

    void setClient(Client cli) {
        this.cli = cli;
        cri.setClient(cli);
    }

    void setMyIP(String ipAddr) {
        this.myIP = ipAddr;
    }

    void setMyVlcPath(String vlcpath) {
        this.myVlcPath = vlcpath;
    }

    void openLobby() {
        final Lobby flb = lb;
        final Properties fprop = prop;
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                flb.setVisible(true);
                //cli.setVisible(false);
                fprop.setVisible(false);
            }
        });
    }

    void openProperties() {
        final Lobby flb = lb;
        final Properties fprop = prop;
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                flb.setVisible(false);
                //cli.setVisible(false);
                fprop.setVisible(true);
            }
        });
    }

    String getMyIp() {
        return this.myIP;
    }

    String getMyVlc() {
        return myVlcPath;
    }

    void run() {
        com = new Comunity();

        try {
            cri = new ClientInterface(this);
        } catch (RemoteException | MalformedURLException ex) {
            Logger.getLogger(Peer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        prop = new Properties(this);
        lb = new Lobby(this);

        this.setProp(prop);
        this.setLobby(lb);
        this.openProperties();
    }

    void RegPeer(PeerReg peerReg) {
        System.out.println("Peer: RegPeer: Registering " + peerReg.getName() + " with comunity");
        com.RegPeer(peerReg);
    }

    void setComunityHost(ClientRemoteInterface rri) {
        System.out.println("Peer: setComunityHost");
        com.setHost(rri);
        try {
            rri.register(this.getNick(), this.getMyCri(), this.getMyIp());
        } catch (RemoteException ex) {
            Logger.getLogger(Peer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private ClientRemoteInterface getMyCri() {
        return cri;
    }

}