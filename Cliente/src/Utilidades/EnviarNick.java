/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilidades;

import IniciarSesion.RegistrarUsuario;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

/**
 *
 * @author anfur
 */
public class EnviarNick {
    private Socket server;
    private InetAddress ip;
    private Seguridad s = new Seguridad();
    
    public void enviarNick(String nick){
        RegistrarUsuario nickUsuario = new RegistrarUsuario();
        byte [] nickCifrado;
        
        try {
            KeyGenerator kg = KeyGenerator.getInstance("AES");
            kg.init(128);
            SecretKey claveSimetrica = kg.generateKey();

            nickCifrado = s.cifrarMensaje(nick, claveSimetrica);
            
            nickUsuario.setIdCifrado(nickCifrado);
            nickUsuario.setClaveSimetrica(claveSimetrica);
            
            ip = InetAddress.getLocalHost();
            server = new Socket(ip, 1234);
            
            ObjectOutputStream oos = new ObjectOutputStream(server.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(server.getInputStream());
            DataInputStream datos = new DataInputStream(server.getInputStream());
            DataOutputStream dos = new DataOutputStream(server.getOutputStream());
            PrintStream ps = new PrintStream(server.getOutputStream());
            String enviarNick = "enviarNick";
            
            //ps.println("");
            dos.writeUTF("");
            //ps.println(activar);
            dos.writeUTF(enviarNick);

            oos.writeObject(nickUsuario);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
}
