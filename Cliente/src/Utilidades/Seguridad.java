/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilidades;

import IniciarSesion.Mensaje;
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
    
    public byte[] resumirMensaje(byte[] mensaje){
         byte resumen[] = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(mensaje);
            resumen = md.digest();
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Seguridad.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resumen;
    }

    public byte[] cifrarMensaje(String mensaje,Mensaje correoContrasena) {
        byte[] textoCifrado = null;
        try {
            KeyGenerator kg = KeyGenerator.getInstance("AES");
            kg.init(128);
            SecretKey clave = kg.generateKey();
            correoContrasena.setClaveSimetrica(clave);

            Cipher c = Cipher.getInstance("AES/ECB/PKCS5Padding");
            c.init(Cipher.ENCRYPT_MODE, clave);
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
