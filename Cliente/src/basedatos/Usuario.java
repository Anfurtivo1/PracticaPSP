/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package basedatos;
import java.io.Serializable;

/**
 *
 * @author anfur
 */
public class Usuario implements Serializable{
    private int id;
    private String correo;
    private byte[] clave;
    private String nick;
    private String nombre;
    private String apellido;
    private String foto;
    
    

    public Usuario(String correo, byte[] clave, String nick, String nombre, String apellido, String foto) {
        this.correo = correo;
        this.clave = clave;
        this.nick = nick;
        this.nombre = nombre;
        this.apellido = apellido;
        this.foto = foto;
    }

    public Usuario(int id, String correo, byte[] clave, String nick, String nombre, String apellido) {
        this.id = id;
        this.correo = correo;
        this.clave = clave;
        this.nick = nick;
        this.nombre = nombre;
        this.apellido = apellido;
    }

    public Usuario(int id, String nick) {
        this.id = id;
        this.nick = nick;
    }
    
    
    
    public Usuario(String correo, byte[] clave, String nick, String nombre, String apellido) {
        this.correo = correo;
        this.clave = clave;
        this.nick = nick;
        this.nombre = nombre;
        this.apellido = apellido;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public byte[] getClave() {
        return clave;
    }

    public void setClave(byte[] clave) {
        this.clave = clave;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
    
    

    
    
}
