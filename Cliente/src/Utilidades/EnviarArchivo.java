/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilidades;

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
public class EnviarArchivo {

    private Socket server;
    private InetAddress ip;
    private Seguridad s = new Seguridad();

    public void enviarArchivo(byte[] fichero, int usuarioId1, int usuarioId2) {
        Mensaje archivo;
        byte[] ficheroCifrado;
        String ficheroS = new String(fichero);

        try {
            KeyGenerator kg = KeyGenerator.getInstance("AES");
            kg.init(128);
            SecretKey claveSimetrica = kg.generateKey();

            ficheroCifrado = s.cifrarMensaje(ficheroS, claveSimetrica);

            archivo = new Mensaje(ficheroCifrado);
            archivo.setClave(claveSimetrica);

            ip = InetAddress.getLocalHost();
            server = new Socket(ip, 1234);

            ObjectOutputStream oos = new ObjectOutputStream(server.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(server.getInputStream());
            DataInputStream datos = new DataInputStream(server.getInputStream());
            DataOutputStream dos = new DataOutputStream(server.getOutputStream());
            PrintStream ps = new PrintStream(server.getOutputStream());
            String enviarArchivo = "enviarArchivo";

            //ps.println("");
            dos.writeUTF("");
            //ps.println(activar);
            dos.writeUTF(enviarArchivo);//Acción

            oos.writeObject(archivo);

        } catch (Exception e) {
        }

    }

}
