/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daleabeja;

import com.mysql.jdbc.Connection;
import static daleabeja.SignIn.txtUsuario;
import java.applet.Applet;
import java.applet.AudioClip;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import sql.sql_con;

/**
 *
 * @author Raul Novelo
 */
public class BestScores extends javax.swing.JFrame {
    
    DefaultTableModel model;

    public BestScores() {
        initComponents();
        initTable();
        fetchBestScores();
    }

    public void startGame() throws Exception {
        java.io.File file1 = new java.io.File("golpe.au"); // llamar sonidos y agregarlos a su clase
        AudioClip sonido1 = Applet.newAudioClip(file1.toURL());

        java.io.File file2 = new java.io.File("bee.wav");
        AudioClip sonido2 = Applet.newAudioClip(file2.toURL());

        java.io.File file3 = new java.io.File("beep.au");
        AudioClip sonido3 = Applet.newAudioClip(file3.toURL());
        DaleAbeja bee = new DaleAbeja(sonido1, sonido2, sonido3); // enviarlos al metodo abeja contructor
        bee.setVisible(true);
    }

    public void initTable(){
        String data[][] = {{"", "", ""}};
        String headers[] = {"Usuario", "Puntaje"};
       
        model = new DefaultTableModel(data, headers);
        
        tblPuntaje.setModel(model);
    }
    
    public void addRowToTable(int index, String user, String score){
        //Add data to model
        System.out.println(index + " " + user + " " + score);
        String[] data = {user, score};
        model.addRow(data);
    }
    
    public String fetchUserName(Connection con, String userId) throws SQLException{
        PreparedStatement psUser;
        String userQueryStr = "SELECT name_user FROM users WHERE id_user='" + userId + "'";
        psUser = con.prepareStatement(userQueryStr);
        ResultSet resUser = psUser.executeQuery();
        resUser.next();
        return resUser.getString("name_user");
    }
    
    public void fetchBestScores(){
        try {
            Connection con = null;
            con = sql_con.getConnection();
            PreparedStatement psScores;
            ResultSet resScores;
            psScores = con.prepareStatement("SELECT id_user, score_sc FROM scores ORDER BY score_sc DESC");
            resScores = psScores.executeQuery();
            int i = 0;
            while (resScores.next()) {
                String userName = "";
                userName = fetchUserName(con, resScores.getString("id_user"));
                //Print it on the table
                addRowToTable(i, userName, resScores.getString("score_sc"));
                i+=1;
            }
            con.close();
        } catch (Exception ex) {
            Logger.getLogger(SignIn.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        btnNuevoJuego = new javax.swing.JButton();
        btnQuit = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblPuntaje = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("DejaVu Sans Condensed", 3, 48)); // NOI18N
        jLabel1.setText("Best Scores");

        btnNuevoJuego.setText("Nuevo Juego");
        btnNuevoJuego.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoJuegoActionPerformed(evt);
            }
        });

        btnQuit.setText("Salir");
        btnQuit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuitActionPerformed(evt);
            }
        });

        tblPuntaje.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null}
            },
            new String [] {
                "Usuario", "Puntaje"
            }
        ));
        jScrollPane1.setViewportView(tblPuntaje);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(165, 165, 165)
                        .addComponent(btnQuit)
                        .addGap(57, 57, 57)
                        .addComponent(btnNuevoJuego))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(76, 76, 76)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 342, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(111, 111, 111)
                        .addComponent(jLabel1)))
                .addContainerGap(82, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 171, Short.MAX_VALUE)
                .addGap(36, 36, 36)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNuevoJuego)
                    .addComponent(btnQuit))
                .addGap(55, 55, 55))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnNuevoJuegoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoJuegoActionPerformed
        try {

            DaleAbeja da = new DaleAbeja();
            da.setUsuario(txtUsuario.getText());
            startGame();
        } catch (Exception ex) {
            Logger.getLogger(SignIn.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnNuevoJuegoActionPerformed

    private void btnQuitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuitActionPerformed
        System.exit(0);
    }//GEN-LAST:event_btnQuitActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnNuevoJuego;
    private javax.swing.JButton btnQuit;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblPuntaje;
    // End of variables declaration//GEN-END:variables
}
