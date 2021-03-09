/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package basedatos;

//import Cadena.Cadena;
import static basedatos.Constantes.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author anfur
 */
public class UsuariosDB {
    
    //********************* Atributos *************************
    private java.sql.Connection Conex;
    //Atributo a través del cual hacemos la conexión física.
    private java.sql.Statement Sentencia_SQL;
    //Atributo que nos permite ejecutar una sentencia SQL
    //
    private java.sql.PreparedStatement SentenciaPreparada;
    private java.sql.ResultSet Conj_Registros;
    //(Cursor) En él están almacenados los datos.
    
    
    public synchronized void abrirConexion() {
        try {
            //Cargar el driver/controlador
            String controlador = "org.mariadb.jdbc.Driver"; // MariaDB la version libre de MySQL (requiere incluir la librería jar correspondiente).
            Class.forName(controlador);
            String URL_BD = "jdbc:mysql://localhost/" + Constantes.bbdd;

            //Realizamos la conexión a una BD con un usuario y una clave.
            Conex = java.sql.DriverManager.getConnection(URL_BD, Constantes.usuario, Constantes.passwd);
            //Sentencia_SQL = Conex.createStatement();
            System.out.println("Conexion realizada con éxito");
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }
    
    public synchronized void cerrarConexion() {
        try {
            this.Conex.close();
            System.out.println("Desconectado de la Base de Datos"); // Opcional para seguridad
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error de Desconexion", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
    public synchronized Usuario buscarPorCorreoClave(String correo, byte[] pass) {
        Usuario usuario = null;
        try {
            Sentencia_SQL = Conex.createStatement();
            //String Sentencia = "SELECT Correo FROM " + TablaUsuarios + " where Correo = '"+ correo +"' and Clave ='"+pass+"'";
            String Sentencia = "SELECT idUsuarios,Correo,Clave,Nick,Nombre,Apellidos FROM " + TablaUsuarios + " where Correo = '"+ correo +"'";
            Conj_Registros = Sentencia_SQL.executeQuery(Sentencia);
            if (Conj_Registros.next()) {
                //System.out.println("Encontrado el usuario buscado");
                usuario = new Usuario(Conj_Registros.getInt("idUsuarios"),Conj_Registros.getString("Correo"),Conj_Registros.getBytes("Clave"),Conj_Registros.getString("Nick"),Conj_Registros.getString("Nombre"),Conj_Registros.getString("Apellidos"));
                return usuario;
            }
            
        } catch (SQLException ex) {
            System.out.println("No se ha encontrado el usuario buscado: "+ex);
        }
        return usuario;
    }
    
    //------------------------------------------------------
    public synchronized int insertarDato(String correo,byte[] pass,String nick,String nombre,String apellidos,byte[] foto,int rol) {
        
        //String Sentencia = "INSERT INTO " + Constantes.TablaUsuarios + " VALUES (null,'" + correo + "'," + pass + ","+ "'" + nick + "'," + "'" + nombre + "'," + "'" + apellidos + "'," + "'" + foto + "'," + "'" + 0 + "'," + "'" + rol + "','"+0+"')";
          String sentencia = "INSERT INTO " + Constantes.TablaUsuarios + " VALUES (?,?,?,?,?,?,?,?,?,?)";
          
        int cod = 0;
        try {
            SentenciaPreparada = Conex.prepareStatement(sentencia);
            SentenciaPreparada.setString(1, null);
            SentenciaPreparada.setString(2, correo);
            SentenciaPreparada.setBytes(3, pass);
            SentenciaPreparada.setString(4, nick);
            SentenciaPreparada.setString(5, nombre);
            SentenciaPreparada.setString(6, apellidos);
            SentenciaPreparada.setBytes(7, foto);
            SentenciaPreparada.setBoolean(8, false);
            SentenciaPreparada.setInt(9, 0);
            SentenciaPreparada.setInt(10, 0);
            SentenciaPreparada.executeUpdate();
        } catch (SQLException ex) {
            cod = ex.getErrorCode();
            ex.printStackTrace();
        }finally{
              try {
                  SentenciaPreparada.close();
              } catch (SQLException ex) {
                  Logger.getLogger(UsuariosDB.class.getName()).log(Level.SEVERE, null, ex);
              }
        }
        return cod;
    }
    
    public synchronized int insertarPreferencias(boolean relacionSeria,int deportivo,int artistico,
                                                int politico,boolean tieneHijos,boolean quiereHijos,
                                                boolean interesHombre,boolean interesMujer,int id){
        int cod=0;
        //0 false y 1 true
        String sentencia = "INSERT INTO "+ Constantes.TablaPreferencias+" VALUES(?,?,?,?,?,?,?,?,?,?)";
        
        
        try {
            SentenciaPreparada = Conex.prepareStatement(sentencia);
            SentenciaPreparada.setString(1, null);//IDPref
            SentenciaPreparada.setBoolean(2, relacionSeria);//Relacion seria
            SentenciaPreparada.setInt(3, deportivo);//Deportivo
            SentenciaPreparada.setInt(4, artistico);//Artistico
            SentenciaPreparada.setInt(5, politico);//Politico
            SentenciaPreparada.setBoolean(6, tieneHijos);//TieneHijos
            SentenciaPreparada.setBoolean(7, quiereHijos);//QuiereHijos
            SentenciaPreparada.setBoolean(8, interesMujer);//Mujer
            SentenciaPreparada.setBoolean(9, interesHombre);//Hombre
            SentenciaPreparada.setInt(10, id);//id usuario
            SentenciaPreparada.executeUpdate();
        } catch (SQLException e) {
            cod = e.getErrorCode();
        }finally{
              try {
                  SentenciaPreparada.close();
              } catch (SQLException ex) {
                  Logger.getLogger(UsuariosDB.class.getName()).log(Level.SEVERE, null, ex);
              }
        }
        
        return cod;
    }
    
    
}
