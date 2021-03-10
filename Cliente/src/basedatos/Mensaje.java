/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package basedatos;

import java.io.Serializable;
import javax.crypto.SecretKey;

/**
 *
 * @author anfur
 */
public class Mensaje implements Serializable{
    private SecretKey clave;
    private byte[] mensajeCifrado;
    private int idUsuario1;
    private int idUsuario2;

    public Mensaje(SecretKey clave, byte[] mensajeCifrado) {
        this.clave = clave;
        this.mensajeCifrado = mensajeCifrado;
    }

    public SecretKey getClave() {
        return clave;
    }

    public void setClave(SecretKey clave) {
        this.clave = clave;
    }

    public byte[] getMensajeCifrado() {
        return mensajeCifrado;
    }

    public void setMensajeCifrado(byte[] mensajeCifrado) {
        this.mensajeCifrado = mensajeCifrado;
    }

    public int getIdUsuario1() {
        return idUsuario1;
    }

    public void setIdUsuario1(int idUsuario1) {
        this.idUsuario1 = idUsuario1;
    }

    public int getIdUsuario2() {
        return idUsuario2;
    }

    public void setIdUsuario2(int idUsuario2) {
        this.idUsuario2 = idUsuario2;
    }
    
    
    
}
