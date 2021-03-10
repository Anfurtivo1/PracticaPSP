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
public class RegistrarUsuario implements Serializable {
    private byte[]idCifrado;
    private byte[] correoCifrado;
    private byte[] nickCifrado;
    private byte[] nombreCifrado;
    private byte[] apellidoCifrado;
    private byte[] fotoCifrada;
    private byte[] claveResumida;
    private byte[] rolCifrado;
    private SecretKey claveSimetrica;

    public RegistrarUsuario(byte[] correo, byte[] nickCifrado, byte[] nombreCifrado, byte[] apellidoCifrado, byte[] fotoCifrada, byte[] claveResumida,byte[] rolCifrado, SecretKey claveSimetrica) {
        this.correoCifrado = correoCifrado;
        this.nickCifrado = nickCifrado;
        this.nombreCifrado = nombreCifrado;
        this.apellidoCifrado = apellidoCifrado;
        this.fotoCifrada = fotoCifrada;
        this.claveResumida = claveResumida;
        this.rolCifrado = rolCifrado;
        this.claveSimetrica = claveSimetrica;
    }

    public RegistrarUsuario(byte[] idCifrado) {
        this.idCifrado = idCifrado;
    }
    
    
    

    public RegistrarUsuario(byte[] correoCifrado, byte[] claveCifrada) {
        this.correoCifrado = correoCifrado;
        this.nickCifrado = claveCifrada;
    }

    public RegistrarUsuario() {

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

    public byte[] getIdCifrado() {
        return idCifrado;
    }

    public void setIdCifrado(byte[] idCifrado) {
        this.idCifrado = idCifrado;
    }

    
    
    public void setClaveResumida(byte[] claveResumida) {
        this.claveResumida = claveResumida;
    }

    public byte[] getCorreo() {
        return correoCifrado;
    }

    public void setCorreo(byte[] correo) {
        this.correoCifrado = correo;
    }

    public byte[] getNickCifrado() {
        return nickCifrado;
    }

    public void setNickCifrado(byte[] nickCifrado) {
        this.nickCifrado = nickCifrado;
    }

    public byte[] getCorreoCifrado() {
        return correoCifrado;
    }

    public void setCorreoCifrado(byte[] correoCifrado) {
        this.correoCifrado = correoCifrado;
    }

    public byte[] getNombreCifrado() {
        return nombreCifrado;
    }

    public void setNombreCifrado(byte[] nombreCifrado) {
        this.nombreCifrado = nombreCifrado;
    }

    public byte[] getApellidoCifrado() {
        return apellidoCifrado;
    }

    public void setApellidoCifrado(byte[] apellidoCifrado) {
        this.apellidoCifrado = apellidoCifrado;
    }

    public byte[] getFotoCifrada() {
        return fotoCifrada;
    }

    public void setFotoCifrada(byte[] fotoCifrada) {
        this.fotoCifrada = fotoCifrada;
    }

    public byte[] getRolCifrado() {
        return rolCifrado;
    }

    public void setRolCifrado(byte[] rolCifrado) {
        this.rolCifrado = rolCifrado;
    }
    
    
    

}
