/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor;

import IniciarSesion.Mensaje;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

/**
 *
 * @author anfur
 */
public class Servidor {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchPaddingException, IOException, ClassNotFoundException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        ServerSocket server;
        Socket cliente;
        String accion;
        server = new ServerSocket(1234);

        while (true) {
            
            System.out.println("Esperando");
            cliente = server.accept();
            ObjectInputStream ois = new ObjectInputStream(cliente.getInputStream());
            ObjectOutputStream oos = new ObjectOutputStream(cliente.getOutputStream());
            System.out.println("Ha llegado un usuario");

            HiloServidor hilo = new HiloServidor(cliente, ois,oos);
            hilo.start();
        }

    }

}
