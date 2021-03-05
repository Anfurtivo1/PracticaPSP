/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IniciarSesion;

import java.io.Serializable;
import javax.crypto.SecretKey;

/**
 *
 * @author anfur
 */
public class Mensaje implements Serializable{
    private String correo;
    private byte[] nickCifrado;
    private byte[] nombreCifrado;
    private byte[] apellidoCifrado;
    private byte[] fotoCifrada;
    private byte[] claveResumida;
    private SecretKey claveSimetrica;
    
    public Mensaje(String correo, byte[] nickCifrado, byte[] nombreCifrado, byte[] apellidoCifrado, byte[] fotoCifrada, byte[] claveResumida, SecretKey claveSimetrica) {
        this.correo = correo;
        this.nickCifrado = nickCifrado;
        this.nombreCifrado = nombreCifrado;
        this.apellidoCifrado = apellidoCifrado;
        this.fotoCifrada = fotoCifrada;
        this.claveResumida = claveResumida;
        this.claveSimetrica = claveSimetrica;
    }

    public byte[] getApellidoCifrado() {
        return apellidoCifrado;
    }

    public byte[] getFotoCifrada() {
        return fotoCifrada;
    }

    public byte[] getNombreCifrado() {
        return nombreCifrado;
    }
    

    

    public Mensaje(String correo, byte[] claveCifrada) {
        this.correo = correo;
        this.nickCifrado = claveCifrada;
    }

    public void setClaveSimetrica(SecretKey claveSimetrica) {
        this.claveSimetrica = claveSimetrica;
    }

    public SecretKey getClaveSimetrica() {
        return claveSimetrica;
    }

    public byte[] getClaveResumida() {
        return claveResumida;
    }

    public void setClaveResumida(byte[] claveResumida) {
        this.claveResumida = claveResumida;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public byte[] getNickCifrado() {
        return nickCifrado;
    }

    public void setNickCifrado(byte[] nickCifrado) {
        this.nickCifrado = nickCifrado;
    }
    
    
    
}
