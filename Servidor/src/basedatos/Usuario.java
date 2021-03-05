/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package basedatos;

/**
 *
 * @author anfur
 */
public class Usuario {
    private String correo;
    private String clave;
    private String nick;
    private String nombre;
    private String apellido;
    private String foto;
    
    

    public Usuario(String correo, String clave, String nick, String nombre, String apellido, String foto) {
        this.correo = correo;
        this.clave = clave;
        this.nick = nick;
        this.nombre = nombre;
        this.apellido = apellido;
        this.foto = foto;
    }
    
    public Usuario(String correo, String clave, String nick, String nombre, String apellido) {
        this.correo = correo;
        this.clave = clave;
        this.nick = nick;
        this.nombre = nombre;
        this.apellido = apellido;
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

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
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
