/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor;

import IniciarSesion.Mensaje;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;

/**
 *
 * @author anfur
 */
public class HiloServidor extends Thread {

    private Socket cliente;

    public HiloServidor(Socket cliente) {
        this.cliente = cliente;
    }

    public void start() {
        DataOutputStream dos;
        Mensaje mensajeServidor;
        SecretKey claveServer;
        String accion;

        try {
            //dos = new DataOutputStream(cliente.getOutputStream());
            DataInputStream dis = new DataInputStream(cliente.getInputStream());

            accion = dis.readUTF();
            //numero=0;

            if (accion.equals("Login")) {
                System.out.println("Se ha entrado al inicio de sesion");
                Cipher c = Cipher.getInstance("AES/ECB/PKCS5Padding");
                ObjectInputStream ois = new ObjectInputStream(cliente.getInputStream());
                mensajeServidor = (Mensaje) ois.readObject();
                claveServer = mensajeServidor.getClaveSimetrica();

                c.init(Cipher.DECRYPT_MODE, claveServer);
                byte[] nickDescifrado = c.doFinal(mensajeServidor.getNickCifrado());
                byte[] nombreDescifrado = c.doFinal(mensajeServidor.getNombreCifrado());
                byte[] apellidoDescifrado = c.doFinal(mensajeServidor.getApellidoCifrado());
                byte[] fotoDescifrado = c.doFinal(mensajeServidor.getFotoCifrada());

                String nick = new String(nickDescifrado);
                String nombre = new String(nombreDescifrado);
                String apellido = new String(apellidoDescifrado);
            }
            
            if (accion.equals("Registro")) {
                System.out.println("Se ha entrado al registro");
            }
            
        } catch (Exception e) {
        }

    }

}
