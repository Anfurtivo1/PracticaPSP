/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package basedatos;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author anfur
 */
public class ListaUsuarios implements Serializable{
    private ArrayList<Usuario> listaAmigos;

    public ListaUsuarios(ArrayList<Usuario> listaAmigos) {
        this.listaAmigos = listaAmigos;
    }

    public ArrayList<Usuario> getListaAmigos() {
        return listaAmigos;
    }

    public void setListaAmigos(ArrayList<Usuario> listaAmigos) {
        this.listaAmigos = listaAmigos;
    }
    
    
    
}
