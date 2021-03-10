/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Preferencias;

import java.io.Serializable;

/**
 *
 * @author anfur
 */
public class Preferencias implements Serializable{
    private int idUsuario;
    private boolean relacionSeria;
    private int deportivo;
    private int artistico;
    private int politico;
    private boolean tieneHijos;
    private boolean quiereHijos;
    private boolean interesHombre;
    private boolean interesMujer;
    private boolean interesAmbos;

    public Preferencias(int idUsuario) {
        this.idUsuario = idUsuario;
    }
    
    

    public Preferencias(int idUsuario, boolean relacionSeria, int deportivo, int artistico, int politico, boolean tieneHijos, boolean quiereHijos, boolean interesHombre, boolean interesMujer, boolean interesAmbos) {
        this.idUsuario = idUsuario;
        this.relacionSeria = relacionSeria;
        this.deportivo = deportivo;
        this.artistico = artistico;
        this.politico = politico;
        this.tieneHijos = tieneHijos;
        this.quiereHijos = quiereHijos;
        this.interesHombre = interesHombre;
        this.interesMujer = interesMujer;
        this.interesAmbos = interesAmbos;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public boolean isRelacionSeria() {
        return relacionSeria;
    }

    public void setRelacionSeria(boolean relacionSeria) {
        this.relacionSeria = relacionSeria;
    }

    public int getDeportivo() {
        return deportivo;
    }

    public void setDeportivo(int deportivo) {
        this.deportivo = deportivo;
    }

    public int getArtistico() {
        return artistico;
    }

    public void setArtistico(int artistico) {
        this.artistico = artistico;
    }

    public int getPolitico() {
        return politico;
    }

    public void setPolitico(int politico) {
        this.politico = politico;
    }

    public boolean isTieneHijos() {
        return tieneHijos;
    }

    public void setTieneHijos(boolean tieneHijos) {
        this.tieneHijos = tieneHijos;
    }

    public boolean isQuiereHijos() {
        return quiereHijos;
    }

    public void setQuiereHijos(boolean quiereHijos) {
        this.quiereHijos = quiereHijos;
    }

    public boolean isInteresHombre() {
        return interesHombre;
    }

    public void setInteresHombre(boolean interesHombre) {
        this.interesHombre = interesHombre;
    }

    public boolean isInteresMujer() {
        return interesMujer;
    }

    public void setInteresMujer(boolean interesMujer) {
        this.interesMujer = interesMujer;
    }

    public boolean isInteresAmbos() {
        return interesAmbos;
    }

    public void setInteresAmbos(boolean interesAmbos) {
        this.interesAmbos = interesAmbos;
    }

    

    
    
    
    
}
