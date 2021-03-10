/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente;

import Utilidades.EnviarMensaje;
import Utilidades.EnviarNick;
import basedatos.ListaUsuarios;
import basedatos.Usuario;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.ListModel;

/**
 *
 * @author anfur
 */
public class PantallaPrincipal extends javax.swing.JFrame {

    private String id;
    private String admin;
    private ListaUsuarios amigos;
    private ListaUsuarios usuariosMismasPrefs;
    private JButton btnAnadirAmigos;
    private DefaultListModel nicks;
    private DefaultListModel nicksUsuariosPrefs;

    /**
     * Creates new form PantallaPrincipal
     */
    public PantallaPrincipal() {
        initComponents();
    }

    /**
     * Creates new form PantallaPrincipal
     */
    public PantallaPrincipal(String id, String admin) {
        this.id = id;
        this.admin = admin;
        initComponents();
        if (admin.equals("normal")) {
            btnAccederCRUD.setVisible(false);
        }

    }

    /**
     * Creates new form PantallaPrincipal
     */
    public PantallaPrincipal(String id, String admin, ListaUsuarios amigos, ListaUsuarios usuariosMismasPrefs) {
        this.id = id;
        this.admin = admin;
        this.amigos = amigos;
        this.usuariosMismasPrefs = usuariosMismasPrefs;

        Map<String, Integer> mapaAmigos = new HashMap<String, Integer>();
        nicks = new DefaultListModel();
        for (Usuario amigo : amigos.getListaAmigos()) {
            mapaAmigos.put(amigo.getNick(), amigo.getId());
            nicks.addElement(amigo.getNick());
        }

        Map<String, Integer> mapausuariosPrefs = new HashMap<String, Integer>();
        nicksUsuariosPrefs = new DefaultListModel();

        for (Usuario amigo : usuariosMismasPrefs.getListaAmigos()) {
            mapausuariosPrefs.put(amigo.getNick(), amigo.getId());
            nicksUsuariosPrefs.addElement(amigo.getNick());
        }

        initComponents();
        lsAmigos.setModel(nicks);
        lstNuevosUsuarios.setModel(nicksUsuariosPrefs);
        if (admin.equals("normal")) {
            btnAccederCRUD.setVisible(false);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        txaMensajes = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        btnEnviarMensaje = new javax.swing.JButton();
        txtMensaje = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        btnEditarPerfil = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        btnEnviarArchivo = new javax.swing.JButton();
        btnAccederCRUD = new javax.swing.JButton();
        btnCerrarSesion = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        lstNuevosUsuarios = new javax.swing.JList<>();
        jScrollPane3 = new javax.swing.JScrollPane();
        lsAmigos = new javax.swing.JList<>();
        btnEliminarAmigo = new javax.swing.JButton();
        btnAgregarAmigo = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        txaMensajes.setColumns(20);
        txaMensajes.setRows(5);
        jScrollPane1.setViewportView(txaMensajes);

        jLabel1.setText("Escribe tu mensaje...");

        btnEnviarMensaje.setText("Enviar mensaje");
        btnEnviarMensaje.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEnviarMensajeActionPerformed(evt);
            }
        });

        jLabel2.setText("Nuevos usuarios que te podrían interesar");

        btnEditarPerfil.setText("Editar perfil");
        btnEditarPerfil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarPerfilActionPerformed(evt);
            }
        });

        jLabel3.setText("Lista de amigos");

        btnEnviarArchivo.setText("Adjuntar archivo");
        btnEnviarArchivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEnviarArchivoActionPerformed(evt);
            }
        });

        btnAccederCRUD.setText("Administrar usuarios");
        btnAccederCRUD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAccederCRUDActionPerformed(evt);
            }
        });

        btnCerrarSesion.setText("Cerrar sesion");
        btnCerrarSesion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarSesionActionPerformed(evt);
            }
        });

        lstNuevosUsuarios.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        lstNuevosUsuarios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lstNuevosUsuariosMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(lstNuevosUsuarios);

        lsAmigos.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        lsAmigos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lsAmigosMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(lsAmigos);

        btnEliminarAmigo.setText("eliminar amigo");
        btnEliminarAmigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarAmigoActionPerformed(evt);
            }
        });

        btnAgregarAmigo.setText("Agregar amigo");
        btnAgregarAmigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarAmigoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(56, 56, 56)
                        .addComponent(btnCerrarSesion)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel3)
                        .addGap(117, 117, 117)
                        .addComponent(btnAccederCRUD)
                        .addGap(46, 46, 46)
                        .addComponent(btnEditarPerfil)
                        .addGap(32, 32, 32))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(180, 180, 180)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(23, Short.MAX_VALUE)
                        .addComponent(btnEnviarArchivo)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(txtMensaje)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnEnviarMensaje))
                            .addComponent(jLabel1)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 729, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(52, 52, 52)
                        .addComponent(btnAgregarAmigo)
                        .addGap(349, 349, 349)
                        .addComponent(btnEliminarAmigo)))
                .addGap(93, 93, 93))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnEditarPerfil)
                            .addComponent(btnAccederCRUD))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 208, Short.MAX_VALUE)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(btnCerrarSesion))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnEliminarAmigo)
                            .addComponent(btnAgregarAmigo))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnEnviarMensaje)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtMensaje, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnEnviarArchivo)))
                .addGap(25, 25, 25))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnEditarPerfilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarPerfilActionPerformed
        EditarUsuario v = new EditarUsuario(id, admin, amigos, usuariosMismasPrefs);
        v.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnEditarPerfilActionPerformed

    private void btnAccederCRUDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAccederCRUDActionPerformed
        PantallaAdministradores v = new PantallaAdministradores(id, admin, amigos, usuariosMismasPrefs);
        v.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnAccederCRUDActionPerformed

    private void btnCerrarSesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarSesionActionPerformed
        Login v = new Login();
        v.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnCerrarSesionActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened

    }//GEN-LAST:event_formWindowOpened

    private void lsAmigosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lsAmigosMouseClicked
        String amigoSeleccionado = String.valueOf(lsAmigos.getSelectedValue());
        System.out.println(amigoSeleccionado);
    }//GEN-LAST:event_lsAmigosMouseClicked

    private void btnEnviarMensajeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEnviarMensajeActionPerformed
        if (txtMensaje.getText().trim() != "") {
            String nick = (String) lsAmigos.getSelectedValue();
            int idUsuario1 = Integer.parseInt(id);
            int idUsuario2 = -1;
            EnviarNick enviarNick = new EnviarNick();
            if (lsAmigos.getSelectedIndex() != -1) {
                idUsuario2 = enviarNick.enviarNick(lsAmigos.getSelectedValue());
            }
            if (lstNuevosUsuarios.getSelectedIndex() != -1) {
                idUsuario2 = enviarNick.enviarNick(lstNuevosUsuarios.getSelectedValue());
            }

            if (idUsuario2 != -1) {
                EnviarMensaje enviarMensaje = new EnviarMensaje(idUsuario1, idUsuario2);
                enviarMensaje.enviarMensaje(txtMensaje.getText());
            }

        }
    }//GEN-LAST:event_btnEnviarMensajeActionPerformed

    private void lstNuevosUsuariosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lstNuevosUsuariosMouseClicked


    }//GEN-LAST:event_lstNuevosUsuariosMouseClicked

    private void btnEnviarArchivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEnviarArchivoActionPerformed

        try {
            JFileChooser fc = new JFileChooser();
            int valor = fc.showOpenDialog(fc);
            if (valor == JFileChooser.APPROVE_OPTION) {
                String ruta = fc.getSelectedFile().getAbsolutePath();
                byte[] array = Files.readAllBytes(Paths.get(ruta));
                //Insertar en base de datos
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }//GEN-LAST:event_btnEnviarArchivoActionPerformed

    private void btnEliminarAmigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarAmigoActionPerformed
        if (lsAmigos.getSelectedIndex()!=-1) {
            String nick = (String) lsAmigos.getSelectedValue();
            nicks.removeElement(nick);
        }
    }//GEN-LAST:event_btnEliminarAmigoActionPerformed

    private void btnAgregarAmigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarAmigoActionPerformed

        if (lstNuevosUsuarios.getSelectedIndex() != -1) {
            String nick = (String) lstNuevosUsuarios.getSelectedValue();
            //Insertar conexion base de datos
            //EnviarNick enviarNick = new EnviarNick();
            //enviarNick.enviarNick(nick);
            nicks.addElement(nick);
            nicksUsuariosPrefs.removeElement(nick);
        }

    }//GEN-LAST:event_btnAgregarAmigoActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAccederCRUD;
    private javax.swing.JButton btnAgregarAmigo;
    private javax.swing.JButton btnCerrarSesion;
    private javax.swing.JButton btnEditarPerfil;
    private javax.swing.JButton btnEliminarAmigo;
    private javax.swing.JButton btnEnviarArchivo;
    private javax.swing.JButton btnEnviarMensaje;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JList<String> lsAmigos;
    private javax.swing.JList<String> lstNuevosUsuarios;
    private javax.swing.JTextArea txaMensajes;
    private javax.swing.JTextField txtMensaje;
    // End of variables declaration//GEN-END:variables

}
