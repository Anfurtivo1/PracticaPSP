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
        DataInputStream dis;
        Mensaje mensajeServidor;
        SecretKey claveServer;
        
        try {
        dos = new DataOutputStream(cliente.getOutputStream());
        dis = new DataInputStream(cliente.getInputStream());
        
        
        
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
        } catch (Exception e) {
        }
        

    }

}
