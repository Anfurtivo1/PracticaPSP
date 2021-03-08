/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor;

import IniciarSesion.Mensaje;
import basedatos.UsuariosDB;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.Socket;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;

/**
 *
 * @author anfur
 */
public class HiloServidor extends Thread {

    private Socket cliente;
    private ObjectInputStream ois;

    public HiloServidor(Socket cliente, ObjectInputStream ois) {
        this.cliente = cliente;
        this.ois = ois;
    }

    public void start() {
        UsuariosDB bd = new UsuariosDB();
        DataOutputStream dos;
        Mensaje mensajeServidor;
        SecretKey claveServer;
        String accion;

        try {
            DataInputStream datos = new DataInputStream(cliente.getInputStream());
            PrintStream ps = new PrintStream(cliente.getOutputStream());
            datos.readLine();
            accion = datos.readLine();
            System.out.println(accion);

            if (accion.equals("Login")) {
                System.out.println("Se ha entrado al inicio de sesion");
                //Cipher c = Cipher.getInstance("AES/ECB/PKCS5Padding");
                mensajeServidor = (Mensaje) ois.readObject();
                //claveServer = mensajeServidor.getClaveSimetrica();

                //c.init(Cipher.DECRYPT_MODE, claveServer);

                ps.println("");
                ps.println("1");

            }

            if (accion.equals("Registro")) {
                System.out.println("Se ha entrado al registro");
                Cipher c = Cipher.getInstance("AES/ECB/PKCS5Padding");
                mensajeServidor = (Mensaje) ois.readObject();

                byte[] correoCifrado = mensajeServidor.getCorreo();
                byte[] claveResumida = mensajeServidor.getClaveResumida();
                byte[] nickCifrado = mensajeServidor.getNickCifrado();
                byte[] nombreCifrado = mensajeServidor.getNombreCifrado();
                byte[] apellidosCifrado = mensajeServidor.getApellidoCifrado();
                byte[] fotoCifrada = mensajeServidor.getFotoCifrada();
                int rolCifrado = 1;
//                
//                claveServer = mensajeServidor.getClaveSimetrica();
//
//                c.init(Cipher.DECRYPT_MODE, claveServer);
//                byte[] correoDescifrado = c.doFinal(mensajeServidor.getCorreo());
//
//                String correo = new String(correoDescifrado);
//                System.out.println(correo);

                bd.abrirConexion();

                bd.insertarDato(correoCifrado, claveResumida, nickCifrado, nombreCifrado, apellidosCifrado, fotoCifrada, rolCifrado);

                bd.cerrarConexion();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
