/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.ConexionSQL;
import Vistas.VistaAgregarMobiliario;
import Vistas.VistaMobiliario;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import javax.swing.JOptionPane;

/**
 *
 * @author HP
 */
public class ControladorAgregarMobiliario implements ActionListener{
    String usuario;
    String contrasena;
    VistaAgregarMobiliario vamob = new VistaAgregarMobiliario();
    ConexionSQL csql = new ConexionSQL();
    
    public ControladorAgregarMobiliario(String usuario, String contrasena, VistaAgregarMobiliario vamob){
        this.vamob = vamob;
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.vamob.btRegresar.addActionListener(this);
        this.vamob.btGuardar.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        if(evt.getSource() == vamob.btRegresar){
            VistaMobiliario vmob = new VistaMobiliario();
            ControladorMobiliario cmob = new ControladorMobiliario(usuario, contrasena, vmob);
            vmob.setLocationRelativeTo(null);
            vmob.setResizable(false);
            vmob.setVisible(true);
            vamob.dispose();
        }
        else if(evt.getSource() == vamob.btGuardar){
            String idMobiliario = vamob.tfIdMobiliario.getText();
            String idTienda = vamob.tfIdTienda.getText();
            String nombre = vamob.tfNombre.getText();
            String descripcion = vamob.tfDescripcion.getText();

            
            if(idMobiliario.equals("") || idTienda.equals("") || nombre.equals("") || descripcion.equals("")){
                JOptionPane.showMessageDialog(null,"Faltan datos por agregar");
                System.out.println("Faltan datos");
            }
            else{
                String sql = "INSERT INTO mobiliario VALUES(?,?,?,?)";
                    try{
                        
                        csql.conexion(usuario, contrasena);
                        PreparedStatement ps = csql.getCx().prepareStatement(sql);
                        ps.setString(1, idMobiliario);
                        ps.setString(2, idTienda);
                        ps.setString(3, nombre);
                        ps.setString(4, descripcion);
                        ps.execute();
                        JOptionPane.showMessageDialog(null,"Ingresado el mueble con id "+idMobiliario);
                    } catch(Exception ex){
                        JOptionPane.showMessageDialog(null,"Has ingresado una ID duplicada o un valor que excede los limites" + ex);
                    }
            }
        }
    }
    
}
