/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor;

import IniciarSesion.Mensaje;
import Utilidades.Seguridad;
import basedatos.Usuario;
import basedatos.UsuariosDB;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.security.MessageDigest;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;

/**
 *
 * @author anfur
 */
public class HiloServidor extends Thread {

    private UsuariosDB bd = new UsuariosDB();
    private Socket cliente;
    private ObjectInputStream ois;
    private Seguridad s = new Seguridad();

    public HiloServidor(Socket cliente, ObjectInputStream ois) {
        this.cliente = cliente;
        this.ois = ois;
    }

    public void start() {
        DataOutputStream dos;
        DataInputStream datos = null;
        PrintStream ps = null;
        SecretKey claveServer;
        String accion;

        try {
            datos = new DataInputStream(cliente.getInputStream());
            ps = new PrintStream(cliente.getOutputStream());
            datos.readLine();
            accion = datos.readLine();
            System.out.println(accion);
            Cipher c = Cipher.getInstance("AES/ECB/PKCS5Padding");

            Mensaje mensajeServidor = (Mensaje) ois.readObject();
            claveServer = mensajeServidor.getClaveSimetrica();

            if (accion.equals("Login")) {
                System.out.println("Se ha entrado al inicio de sesion");
                iniciarSesion(ps, claveServer, mensajeServidor);
            }

            if (accion.equals("Registro")) {
                registrarse(ps, claveServer, mensajeServidor);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }

    }

    private synchronized void iniciarSesion(PrintStream ps, SecretKey claveServer, Mensaje mensajeServidor) {
        try {

            Cipher c = Cipher.getInstance("AES/ECB/PKCS5Padding");
            c.init(Cipher.DECRYPT_MODE, claveServer);
            byte[] correoCifrado = mensajeServidor.getCorreo();
            byte[] correoDescifrado = c.doFinal(correoCifrado);
            String correo = new String(correoDescifrado);
            ps.println("");
            ps.println("1");
            bd.abrirConexion();
            Usuario usuario = bd.buscarPorCorreoClave(correo, mensajeServidor.getClaveResumida());
            bd.cerrarConexion();

            if (usuario != null) {
                byte[] resumen1 = mensajeServidor.getClaveResumida();
                byte[] resumen2 = usuario.getClave();

                boolean valido = MessageDigest.isEqual(resumen1, resumen2);
                if (valido) {
                    System.out.println("Las claves son iguales");
                    ps.println("");
                    ps.println("1");
                } else {
                    System.out.println("Las claves no son iguales");
                    ps.println("");
                    ps.println("0");
                }

            } else {
                System.out.println("No se ha encontrado el usuario");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private synchronized void registrarse(PrintStream ps, SecretKey claveServer, Mensaje mensajeServidor) {
        try {
            System.out.println("Se ha entrado al registro");
            Cipher c = Cipher.getInstance("AES/ECB/PKCS5Padding");

            byte[] correoCifrado = mensajeServidor.getCorreo();
            byte[] claveResumida = mensajeServidor.getClaveResumida();
            byte[] nickCifrado = mensajeServidor.getNickCifrado();
            byte[] nombreCifrado = mensajeServidor.getNombreCifrado();
            byte[] apellidosCifrado = mensajeServidor.getApellidoCifrado();
            //byte[] fotoCifrada = mensajeServidor.getFotoCifrada();
            byte[] fotoCifrada = null;
            int rolCifrado = 1;

            claveServer = mensajeServidor.getClaveSimetrica();

            c.init(Cipher.DECRYPT_MODE, claveServer);
            byte[] correoDescifrado = c.doFinal(correoCifrado);
            byte[] nickDescifrado = c.doFinal(nickCifrado);
            byte[] nombreDescifrado = c.doFinal(nombreCifrado);
            byte[] apellidoDescifrado = c.doFinal(apellidosCifrado);
            byte[] fotoDescifrado;

            String correo = new String(correoDescifrado);
            String nick = new String(nickDescifrado);
            String nombre = new String(nombreDescifrado);
            String apellido = new String(apellidoDescifrado);
            String foto;

            bd.abrirConexion();

            bd.insertarDato(correo, claveResumida, nick, nombre, apellido, fotoCifrada, rolCifrado);

            bd.cerrarConexion();
        } catch (Exception e) {
        }

    }

}
