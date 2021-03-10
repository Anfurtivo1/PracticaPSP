/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilidades;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author anfur
 */
public class ListaMensajes implements Serializable{
    ArrayList<String> mensajes;

    public ListaMensajes(ArrayList<String> mensajes) {
        this.mensajes = mensajes;
    }

    public ArrayList<String> getMensajes() {
        return mensajes;
    }

    public void setMensajes(ArrayList<String> mensajes) {
        this.mensajes = mensajes;
    }
    
    
    
}
