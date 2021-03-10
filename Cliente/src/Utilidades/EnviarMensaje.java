/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilidades;

import IniciarSesion.RegistrarUsuario;
import basedatos.Mensaje;
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
public class EnviarMensaje {

    private Socket server;
    private InetAddress ip;
    private int idUsuario1;
    private int idUsuario2;

    public EnviarMensaje(int idUsuario1, int idUsuario2) {
        this.idUsuario1 = idUsuario1;
        this.idUsuario2 = idUsuario2;
    }

    public void enviarMensaje(String mensaje) {
        Mensaje mensajeEnviado;
        byte[] mensajeCifrado;
        Seguridad s = new Seguridad();

        try {
            KeyGenerator kg = KeyGenerator.getInstance("AES");
            kg.init(128);
            SecretKey claveSimetrica = kg.generateKey();

            mensajeCifrado = s.cifrarMensaje(mensaje, claveSimetrica);
            
            mensajeEnviado = new Mensaje(claveSimetrica,mensajeCifrado);
            mensajeEnviado.setIdUsuario1(idUsuario1);
            mensajeEnviado.setIdUsuario2(idUsuario2);

            ip = InetAddress.getLocalHost();
            server = new Socket(ip, 1234);
            ObjectOutputStream oos = new ObjectOutputStream(server.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(server.getInputStream());
            DataInputStream datos = new DataInputStream(server.getInputStream());
            DataOutputStream dos = new DataOutputStream(server.getOutputStream());
            PrintStream ps = new PrintStream(server.getOutputStream());
            String enviarMensaje = "enviarMensaje";
            
            //ps.println("");
            dos.writeUTF("");
            //ps.println(baja);
            dos.writeUTF(enviarMensaje);

            oos.writeObject(mensajeEnviado);
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
