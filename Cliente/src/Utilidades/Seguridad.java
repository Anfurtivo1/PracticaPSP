/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilidades;

import IniciarSesion.RegistrarUsuario;
import cliente.Login;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

/**
 *
 * @author anfur
 */
public class Seguridad {

    static String Hexadecimal(byte[] resumen) {
        String hex = "";
        for (int i = 0; i < resumen.length; i++) {
            String h = Integer.toHexString(resumen[i] & 0xFF);
            if (h.length() == 1) {
                hex += 0;
            }
            hex += h;
        }
        return hex;
    }

    public byte[] resumirMensaje(String mensaje) {
        byte resumen[] = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] byteMensaje = mensaje.getBytes();
            md.update(byteMensaje);
            resumen = md.digest();

            System.out.println("Mensaje original: " + mensaje);
            System.out.println("Número de bytes del resumen: " + md.getDigestLength());
            System.out.println("Algoritmo: " + md.getAlgorithm());
            System.out.println("Mesaje resumen: " + new String(resumen));
            System.out.println("Mensaje en hexadecimal: " + Hexadecimal(resumen));

        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Seguridad.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resumen;
    }

    public byte[] cifrarMensaje(String mensaje, SecretKey claveSimetrica) {
        byte[] textoCifrado = null;
        try {

            Cipher c = Cipher.getInstance("AES/ECB/PKCS5Padding");
            c.init(Cipher.ENCRYPT_MODE, claveSimetrica);
            byte[] textoPlano = mensaje.getBytes();
            textoCifrado = c.doFinal(textoPlano);

            return textoCifrado;
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchPaddingException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalBlockSizeException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BadPaddingException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
        return textoCifrado;
    }

}
