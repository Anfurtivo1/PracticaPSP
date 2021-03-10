/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor;

import IniciarSesion.RegistrarUsuario;
import Preferencias.Preferencias;
import Utilidades.Seguridad;
import basedatos.ListaUsuarios;
import basedatos.Mensaje;
import basedatos.Usuario;
import basedatos.UsuariosDB;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import javax.crypto.Cipher;
import javax.crypto.SealedObject;
import javax.crypto.SecretKey;

/**
 *
 * @author anfur
 */
public class HiloServidor extends Thread {

    private UsuariosDB bd = new UsuariosDB();
    private Socket cliente;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private Seguridad s = new Seguridad();
    DataOutputStream dos = null;
    DataInputStream datos = null;

    public HiloServidor(Socket cliente, ObjectInputStream ois, ObjectOutputStream oos) {
        this.cliente = cliente;
        this.ois = ois;
        this.oos = oos;
    }

    public void start() {

        PrintStream ps = null;
        SecretKey claveServer = null;
        RegistrarUsuario mensajeServidor = null;
        PrivateKey clavepriv = null;
        PublicKey clavepubl = null;
        PublicKey clavepublCliente = null;
        String accion;
        DataOutputStream dos = null;

        try {
            datos = new DataInputStream(cliente.getInputStream());
            
            //ps = new PrintStream(cliente.getOutputStream());
            //datos.readLine();
            //accion = datos.readLine();
            datos.readUTF();
            accion = datos.readUTF();
            System.out.println(accion);
            //Cipher c = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            Cipher c = Cipher.getInstance("AES/ECB/PKCS5Padding");

            if (!accion.equals("editarPreferencias") && !accion.equals("editarUsuario") && !accion.equals("enviarMensaje")) {
                mensajeServidor = (RegistrarUsuario) ois.readObject();
                claveServer = mensajeServidor.getClaveSimetrica();
            }
            
            if (accion.equals("enviarMensaje")) {
                System.out.println("Se ha recibido un mensaje");
                Mensaje mensajeCliente = (Mensaje) ois.readObject();
                claveServer = mensajeCliente.getClave();
                c = Cipher.getInstance("AES/ECB/PKCS5Padding");
                c.init(Cipher.DECRYPT_MODE, claveServer);
                byte[] mensajeCifrado = mensajeCliente.getMensajeCifrado();
                byte[] mensajeDescifrado = c.doFinal(mensajeCifrado);
                String mensaje = new String(mensajeDescifrado);
                System.out.println(mensaje);
                
            }

            if (accion.equals("activarUsuario")) {
                System.out.println("Activar un usuario");
                c = Cipher.getInstance("AES/ECB/PKCS5Padding");
                c.init(Cipher.DECRYPT_MODE, claveServer);
                byte[] nickCifrado = mensajeServidor.getNickCifrado();
                byte[] nickDescifrado = c.doFinal(nickCifrado);
                String nick = new String(nickDescifrado);

                bd.abrirConexion();
                int id = bd.buscarPorNick(nick);
                if (id != -1) {
                    bd.activarUsuario(id);
                }

                bd.cerrarConexion();
            }

            if (accion.equals("bajaUsuario")) {
                System.out.println("Dar de baja a un usuario");
                c = Cipher.getInstance("AES/ECB/PKCS5Padding");
                c.init(Cipher.DECRYPT_MODE, claveServer);
                byte[] nickCifrado = mensajeServidor.getNickCifrado();
                byte[] nickDescifrado = c.doFinal(nickCifrado);
                String nick = new String(nickDescifrado);
                
                bd.abrirConexion();
                int id = bd.buscarPorNick(nick);
                if (id != -1) {
                    bd.darBajaUsuario(id);
                }
                bd.cerrarConexion();
            }

            if (accion.equals("editarPreferencias") || accion.equals("editarUsuario")) {
                mensajeServidor = (RegistrarUsuario) ois.readObject();
                claveServer = mensajeServidor.getClaveSimetrica();
            }

            if (accion.equals("Login")) {
                System.out.println("Se ha entrado al inicio de sesion");
                iniciarSesion(ps, claveServer, mensajeServidor);

            }

            if (accion.equals("Registro")) {
                registrarse(ps, claveServer, mensajeServidor);
            }

            if (accion.equals("editarPreferencias")) {
                System.out.println("Se ha entrado a editar las preferencias");
                SealedObject prefs = (SealedObject) ois.readObject();
                c.init(Cipher.DECRYPT_MODE, claveServer);
                Preferencias p = (Preferencias) prefs.getObject(c);
                //System.out.println(p);
                bd.abrirConexion();
                bd.insertarPreferencias(p.isRelacionSeria(), p.getDeportivo(), p.getArtistico(), p.getPolitico(),
                        p.isTieneHijos(), p.isQuiereHijos(),
                        p.isInteresHombre(), p.isInteresMujer(), p.getIdUsuario());
                bd.cerrarConexion();
            }

            if (accion.equals("editarUsuario")) {
                System.out.println("Se ha entrado a editar un usuario");
                SealedObject usu = (SealedObject) ois.readObject();
                c.init(Cipher.DECRYPT_MODE, claveServer);
                Usuario p = (Usuario) usu.getObject(c);
                System.out.println(p);

                //bd.actualizarUsuario(p.getId(), p.getCorreo(), p.getClave(), p.getNick(), p.getNombre(), p.getApellido());

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private synchronized void iniciarSesion(PrintStream ps, SecretKey claveServer, RegistrarUsuario mensajeServidor) {
        try {
            dos = new DataOutputStream(cliente.getOutputStream());
            boolean primeraVez;
            boolean esAdmin;
            boolean esActivado;
            ArrayList<Usuario> listaAmigos;
            Cipher c = Cipher.getInstance("AES/ECB/PKCS5Padding");
            c.init(Cipher.DECRYPT_MODE, claveServer);
            byte[] correoCifrado = mensajeServidor.getCorreo();
            byte[] correoDescifrado = c.doFinal(correoCifrado);
            String correo = new String(correoDescifrado);
            bd.abrirConexion();
            Usuario usuario = bd.buscarPorCorreoClave(correo, mensajeServidor.getClaveResumida());
            primeraVez = bd.esPrimeraVez(usuario.getId());
            esAdmin = bd.esAdmin(usuario.getId());
            esActivado = bd.esActivado(usuario.getId());
            listaAmigos = bd.buscarAmigos(usuario.getId());

            if (usuario != null) {
                byte[] resumen1 = mensajeServidor.getClaveResumida();
                byte[] resumen2 = usuario.getClave();

                boolean valido = MessageDigest.isEqual(resumen1, resumen2);
                if (valido) {
                    System.out.println("Las claves son iguales");
                    //ps.println("");
                    //oos.writeUTF("");
                    //ps.println("Encontrado");
                    dos.writeUTF("Encontrado");
                    //ps.println("" + usuario.getId());
                    dos.writeUTF(""+usuario.getId());

                    if (esActivado) {
                        System.out.println("El usuario esta activado");
                        //ps.println("activado");
                        dos.writeUTF("activado");
                        if (!primeraVez) {
                            bd.cerrarConexion();
                            System.out.println("No es su primera vez");
                            dos.writeUTF("no primera");
                            ListaUsuarios amigos = new ListaUsuarios(listaAmigos);
                            oos.writeObject(amigos);
                            //ps.println("no primera");
                            

                        }

                        if (primeraVez) {
                            System.out.println("Es su primera vez");
                            //ps.println("primera");
                            dos.writeUTF("primera");
                            bd.actualizarPrimeraVez(usuario.getId());
                            bd.cerrarConexion();
                        }
                    }

                    if (!esActivado) {
                        System.out.println("El usuario no esta activado");
                        //ps.println("no activado");
                        dos.writeUTF("no activado");
                        //ps.println("");
                        dos.writeUTF("");
                    }

                    if (!esAdmin) {
                        System.out.println("Es un usuario normal");
                        //ps.println("normal");
                        dos.writeUTF("normal");
                    }

                    if (esAdmin) {
                        System.out.println("Es un admin");
                        //ps.println("admin");
                        dos.writeUTF("admin");
                    }

                } else {
                    System.out.println("Las claves no son iguales");
                    //ps.println("");
                    dos.writeUTF("");
                    //ps.println("No encontrado");
                    dos.writeUTF("No encontrado");
                }

            } else {
                System.out.println("No se ha encontrado el usuario");
                //ps.println("");
                dos.writeUTF("");
                //ps.println("No encontrado");
                dos.writeUTF("No encontrado");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private synchronized void registrarse(PrintStream ps, SecretKey claveServer, RegistrarUsuario mensajeServidor) {
        try {
            System.out.println("Se ha entrado al registro");
            Cipher c = Cipher.getInstance("AES/ECB/PKCS5Padding");

            byte[] correoCifrado = mensajeServidor.getCorreo();
            byte[] claveResumida = mensajeServidor.getClaveResumida();
            byte[] nickCifrado = mensajeServidor.getNickCifrado();
            byte[] nombreCifrado = mensajeServidor.getNombreCifrado();
            byte[] apellidosCifrado = mensajeServidor.getApellidoCifrado();
            byte[] rolCifrado = mensajeServidor.getRolCifrado();
            //byte[] fotoCifrada = mensajeServidor.getFotoCifrada();
            byte[] fotoCifrada = null;

            claveServer = mensajeServidor.getClaveSimetrica();

            c.init(Cipher.DECRYPT_MODE, claveServer);
            byte[] correoDescifrado = c.doFinal(correoCifrado);
            byte[] nickDescifrado = c.doFinal(nickCifrado);
            byte[] nombreDescifrado = c.doFinal(nombreCifrado);
            byte[] apellidoDescifrado = c.doFinal(apellidosCifrado);
            byte[] rolDescifrado = c.doFinal(rolCifrado);
            byte[] fotoDescifrado;

            String correo = new String(correoDescifrado);
            String nick = new String(nickDescifrado);
            String nombre = new String(nombreDescifrado);
            String apellido = new String(apellidoDescifrado);
            String rolS = new String(rolDescifrado);
            int rol = Integer.parseInt(rolS);
            String foto;

            bd.abrirConexion();

            bd.insertarDato(correo, claveResumida, nick, nombre, apellido, fotoCifrada, rol);

            bd.cerrarConexion();
        } catch (Exception e) {
        }

    }

}
