/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package basedatos;

//import Cadena.Cadena;
import Preferencias.Preferencias;
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
            String Sentencia = "SELECT idUsuarios,Correo,Clave,Nick,Nombre,Apellidos FROM " + TablaUsuarios + " where Correo = '" + correo + "'";
            Conj_Registros = Sentencia_SQL.executeQuery(Sentencia);
            if (Conj_Registros.next()) {
                //System.out.println("Encontrado el usuario buscado");
                usuario = new Usuario(Conj_Registros.getInt("idUsuarios"), Conj_Registros.getString("Correo"), Conj_Registros.getBytes("Clave"), Conj_Registros.getString("Nick"), Conj_Registros.getString("Nombre"), Conj_Registros.getString("Apellidos"));
                return usuario;
            }

        } catch (SQLException ex) {
            System.out.println("No se ha encontrado el usuario buscado: " + ex);
        }
        return usuario;
    }

    //------------------------------------------------------
    public synchronized int insertarDato(String correo, byte[] pass, String nick, String nombre, String apellidos, byte[] foto, int rol) {

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
            SentenciaPreparada.setInt(9, rol);
            SentenciaPreparada.setInt(10, 1);
            SentenciaPreparada.executeUpdate();
        } catch (SQLException ex) {
            cod = ex.getErrorCode();
            ex.printStackTrace();
        } finally {
            try {
                SentenciaPreparada.close();
            } catch (SQLException ex) {
                Logger.getLogger(UsuariosDB.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return cod;
    }

    public synchronized int insertarMensajes(int idUsuario1, int idUsuario2, String mensaje) {
        int cod = 0;
        String sentencia = "insert into mensajes values(?,?,?,?,?)";

        try {
            SentenciaPreparada = Conex.prepareStatement(sentencia);
            SentenciaPreparada.setInt(1, 3);
            SentenciaPreparada.setInt(2, idUsuario1);
            SentenciaPreparada.setInt(3, idUsuario2);
            SentenciaPreparada.setString(4, mensaje);
            SentenciaPreparada.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                SentenciaPreparada.close();
            } catch (SQLException ex) {
                Logger.getLogger(UsuariosDB.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return cod;
    }

    public synchronized int eliminarAmigos(int idUsuario, String nick) {
        int cod = 0;
        String sentencia = "delete from usuariosgustados where idUsuario1 = ? and IdUsuario2 in (select idUsuarios from usuarios where nick= ?)";

        try {
            SentenciaPreparada = Conex.prepareStatement(sentencia);
            SentenciaPreparada.setInt(1, idUsuario);
            SentenciaPreparada.setString(2, nick);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                SentenciaPreparada.close();
            } catch (SQLException ex) {
                Logger.getLogger(UsuariosDB.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return cod;
    }

    public synchronized int agregarAmigos(int idUsuario, String nick) {
        int cod = 0;
        String sentencia = "insert into usuariosgustados values(?,(select idUsuarios from usuarios where nick= ?))";

        try {
            SentenciaPreparada = Conex.prepareStatement(sentencia);
            SentenciaPreparada.setInt(1, idUsuario);
            SentenciaPreparada.setString(2, nick);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                SentenciaPreparada.close();
            } catch (SQLException ex) {
                Logger.getLogger(UsuariosDB.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return cod;
    }

    public synchronized int insertarArchivo(int idUsuario1, int idUsuario2, byte[] archivo) {
        int cod = 0;

        String sentencia = "insert into mensajes values(?,?,?,?,?)";

        try {
            SentenciaPreparada = Conex.prepareStatement(sentencia);
            SentenciaPreparada.setInt(1, 4);
            SentenciaPreparada.setInt(2, idUsuario1);
            SentenciaPreparada.setInt(3, idUsuario2);
            SentenciaPreparada.setString(4, "");
            SentenciaPreparada.setBytes(5, archivo);
            SentenciaPreparada.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                SentenciaPreparada.close();
            } catch (SQLException ex) {
                Logger.getLogger(UsuariosDB.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return cod;
    }

    public synchronized int insertarPreferencias(boolean relacionSeria, int deportivo, int artistico,
            int politico, boolean tieneHijos, boolean quiereHijos,
            boolean interesHombre, boolean interesMujer, int id) {
        int cod = 0;
        //0 false y 1 true
        String sentencia = "INSERT INTO " + Constantes.TablaPreferencias + " VALUES(?,?,?,?,?,?,?,?,?,?)";

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
        } finally {
            try {
                SentenciaPreparada.close();
            } catch (SQLException ex) {
                Logger.getLogger(UsuariosDB.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return cod;
    }

    public synchronized int actualizarUsuario(int id, String correo, byte[] pass, String nick, String nombre, String apellidos) {
        int cod = 0;

        String sentencia = "UPDATE " + Constantes.TablaUsuarios + " SET CORREO = ?, CLAVE = ?, NICK = ?, NOMBRE = ?, APELLIDOS = ? WHERE IDUSUARIOS=" + id;
        try {
            SentenciaPreparada = Conex.prepareStatement(sentencia);
            SentenciaPreparada.setString(1, correo);
            SentenciaPreparada.setBytes(2, pass);
            SentenciaPreparada.setString(3, nick);
            SentenciaPreparada.setString(4, nombre);
            SentenciaPreparada.setString(5, apellidos);
            SentenciaPreparada.executeUpdate();

        } catch (SQLException e) {
            cod = e.getErrorCode();
        } finally {
            try {
                SentenciaPreparada.close();
            } catch (SQLException ex) {
                Logger.getLogger(UsuariosDB.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return cod;
    }

    public synchronized boolean esPrimeraVez(int id) {
        boolean primeraVez = false;
        try {
            Sentencia_SQL = Conex.createStatement();
            String Sentencia = "SELECT primeraVez FROM " + TablaUsuarios + " where idUsuarios = '" + id + "'";
            Conj_Registros = Sentencia_SQL.executeQuery(Sentencia);
            if (Conj_Registros.next()) {
                primeraVez = Conj_Registros.getBoolean("primeraVez");
                return primeraVez;
            }
        } catch (SQLException ex) {
            System.out.println("Error en: " + ex);
        }
        return primeraVez;
    }

    public synchronized ArrayList<String> recuperarMensajes(int idUsuario) {
        ArrayList<String> mensajes = new ArrayList<String>();
        try {
            Sentencia_SQL = Conex.createStatement();
            String sentencia = "select mensajeEscrito from mensajes where idUsuario2 = " + idUsuario;
            Conj_Registros = Sentencia_SQL.executeQuery(sentencia);
            while (Conj_Registros.next()) {
                String mensaje = Conj_Registros.getString("MensajeEscrito");
                mensajes.add(mensaje);
            }
            return mensajes;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return mensajes;
    }

    public synchronized void actualizarPrimeraVez(int id) {
        String sentencia = "UPDATE " + Constantes.TablaUsuarios + " SET PRIMERAVEZ = 0 WHERE IDUSUARIOS = " + id;
        try {
            Sentencia_SQL.executeUpdate(sentencia);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public synchronized void darBajaUsuario(int id) {
        String sentencia = "DELETE FROM USUARIOS WHERE USUARIOS.idUsuarios = " + id;
        try {
            Sentencia_SQL.executeUpdate(sentencia);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public synchronized void activarUsuario(int id) {
        String sentencia = "UPDATE USUARIOS SET activado = 1 WHERE USUARIOS.idUsuarios = " + id;
        try {
            Sentencia_SQL.executeUpdate(sentencia);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public synchronized int buscarPorNick(String nick) {
        int id = -1;
        try {
            Sentencia_SQL = Conex.createStatement();
            String sentencia = "Select idUsuarios from usuarios where usuarios.Nick = '" + nick + "'";
            Conj_Registros = Sentencia_SQL.executeQuery(sentencia);
            if (Conj_Registros.next()) {
                id = Conj_Registros.getInt("idUsuarios");
                return id;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return id;
    }

    public synchronized boolean esAdmin(int id) {
        boolean esAdmin = false;
        try {
            Sentencia_SQL = Conex.createStatement();
            String Sentencia = "SELECT rol FROM " + TablaUsuarios + " where idUsuarios = '" + id + "'";
            Conj_Registros = Sentencia_SQL.executeQuery(Sentencia);
            if (Conj_Registros.next()) {
                esAdmin = Conj_Registros.getBoolean("rol");
                return esAdmin;
            }
        } catch (SQLException ex) {
            System.out.println("Error en: " + ex);
        }
        return esAdmin;
    }

    public synchronized boolean esActivado(int id) {
        boolean activado = false;
        try {
            Sentencia_SQL = Conex.createStatement();
            String Sentencia = "SELECT activado FROM " + TablaUsuarios + " where idUsuarios = '" + id + "'";
            Conj_Registros = Sentencia_SQL.executeQuery(Sentencia);
            if (Conj_Registros.next()) {
                activado = Conj_Registros.getBoolean("activado");
                return activado;
            }
        } catch (SQLException ex) {
            System.out.println("Error en: " + ex);
        }
        return activado;
    }

    public synchronized ArrayList<Usuario> buscarAmigos(int id) {
        ArrayList lp = new ArrayList<Usuario>();
        try {
            Sentencia_SQL = Conex.createStatement();
            String Sentencia = "select idusuarios,nick from usuarios where idusuarios in (select idusuario1 from usuariosgustados where idusuario2=" + id + " and idusuario1 in (select idUsuario2 from usuariosgustados where idUsuario1 = " + id + "));";
            Conj_Registros = Sentencia_SQL.executeQuery(Sentencia);
            while (Conj_Registros.next()) {
                Usuario usuario = new Usuario(Conj_Registros.getInt("idUsuarios"), Conj_Registros.getString("Nick"));
                lp.add(usuario);
            }

            return lp;
        } catch (SQLException ex) {
            System.out.println("Error en: " + ex);
        }
        return lp;
    }

    public synchronized ArrayList<Usuario> comprobarPreferencias(Preferencias pref) {
        ArrayList lp = new ArrayList<Usuario>();
        try {
            Sentencia_SQL = Conex.createStatement();
            String sentencia = "select idusuarios,nick from usuarios where idusuarios in (select idUsuario from preferencias where "
                    + "idUsuario!= " + pref.getIdUsuario()
                    + " and relacionSeria= " + pref.isRelacionSeria()
                    + " and quiereHijos= " + pref.isQuiereHijos()
                    + " and interesMujer= " + pref.isInteresMujer()
                    + " and interesHombre = " + pref.isInteresHombre()
                    + " and Deportivos >=25"
                    + " and Artistico>=25"
                    + " and Politico>=25);";
            Conj_Registros = Sentencia_SQL.executeQuery(sentencia);

            while (Conj_Registros.next()) {
                Usuario usuario = new Usuario(Conj_Registros.getInt("idUsuarios"), Conj_Registros.getString("Nick"));
                lp.add(usuario);
            }
            return lp;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return lp;
    }

    public synchronized Preferencias buscarUsuarioPreferencias(int idUsuario) {
        Preferencias prefs = null;
        try {
            Sentencia_SQL = Conex.createStatement();
            //String Sentencia = "SELECT Correo FROM " + TablaUsuarios + " where Correo = '"+ correo +"' and Clave ='"+pass+"'";
            String Sentencia = "select * from preferencias where idUsuario = " + idUsuario;
            Conj_Registros = Sentencia_SQL.executeQuery(Sentencia);
            if (Conj_Registros.next()) {
                prefs = new Preferencias(Conj_Registros.getInt("idUsuario"), Conj_Registros.getBoolean("relacionSeria"),
                        Conj_Registros.getInt("Deportivos"), Conj_Registros.getInt("Artistico"),
                        Conj_Registros.getInt("Politico"), Conj_Registros.getBoolean("tieneHijos"),
                        Conj_Registros.getBoolean("quiereHijos"), Conj_Registros.getBoolean("interesMujer"),
                        Conj_Registros.getBoolean("interesHombre"));
                return prefs;
            }

        } catch (SQLException ex) {
            System.out.println("No se ha encontrado el usuario buscado: " + ex);
        }
        return prefs;
    }

}
