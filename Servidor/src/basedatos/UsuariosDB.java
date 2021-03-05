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
    private java.sql.ResultSet Conj_Registros;
    //(Cursor) En él están almacenados los datos.
    
    
    public void abrirConexion() {
        try {
            //Cargar el driver/controlador
            String controlador = "org.mariadb.jdbc.Driver"; // MariaDB la version libre de MySQL (requiere incluir la librería jar correspondiente).
            Class.forName(controlador);
            String URL_BD = "jdbc:mysql://localhost/" + Constantes.bbdd;

            //Realizamos la conexión a una BD con un usuario y una clave.
            Conex = java.sql.DriverManager.getConnection(URL_BD, Constantes.usuario, Constantes.passwd);
            Sentencia_SQL = Conex.createStatement();
            System.out.println("Conexion realizada con éxito");
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }
    
    public void cerrarConexion() {
        try {
            this.Conex.close();
            System.out.println("Desconectado de la Base de Datos"); // Opcional para seguridad
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error de Desconexion", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
    public Usuario buscarPorCorreoClave(String correo, byte[] pass) {
        ArrayList lp = new ArrayList();
        Usuario usuario = null;
        try {
            String Sentencia = "SELECT Correo FROM " + TablaUsuarios + " where Correo = '"+ correo +"' and Clave ='"+pass+"'";
            Conj_Registros = Sentencia_SQL.executeQuery(Sentencia);
            if (!Conj_Registros.wasNull()) {
                usuario = new Usuario(Conj_Registros.getString("Correo"),Conj_Registros.getString("Clave"),Conj_Registros.getString("Nick"),
                                      Conj_Registros.getString("Nombre"),Conj_Registros.getString("Apellidos"));
                return usuario;
            }
            
        } catch (SQLException ex) {
        }
        return usuario;
    }
    
    //------------------------------------------------------
    public int insertarDato(String correo, byte[] pass) {
        //,Nick,Nombre,Apellidos,Foto
        String Sentencia = "INSERT INTO " + Constantes.TablaUsuarios + "(Correo,Clave) VALUES ('" + correo + "'," + "'" + pass + "')";
        int cod = 0;
        try {
            Sentencia_SQL.executeUpdate(Sentencia);
        } catch (SQLException ex) {
            cod = ex.getErrorCode();
        }
        return cod;
    }
    
}
