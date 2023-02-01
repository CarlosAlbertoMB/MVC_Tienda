/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.ConexionSQL;
import Vistas.VistaAgregarEmpleado;
import Vistas.VistaEmpleados;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import javax.swing.JOptionPane;
/**
 *
 * @author HP
 */
public class ControladorAgregarEmpleado extends DatosInicioSesion implements ActionListener{
    VistaAgregarEmpleado vae = new VistaAgregarEmpleado();
    ConexionSQL csql = new ConexionSQL();
    String usuario;
    String contrasena;
    
    public ControladorAgregarEmpleado(String usuario, String contrasena, VistaAgregarEmpleado vae){
        this.vae=vae;
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.vae.tfIdEmpleado.addActionListener(this);
        this.vae.tfIdTienda.addActionListener(this);
        this.vae.tfNombre.addActionListener(this);
        this.vae.tfAPat.addActionListener(this);
        this.vae.tfAMat.addActionListener(this);
        this.vae.btCerrar.addActionListener(this);
        this.vae.btGuardar.addActionListener(this);
        this.vae.tfCargo.addActionListener(this);
        this.vae.tfCalle.addActionListener(this);
        this.vae.tfColonia.addActionListener(this);
        this.vae.tfNumero.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        if(evt.getSource() == vae.btCerrar){
            vae.dispose();
            VistaEmpleados vemp = new VistaEmpleados();
            ControladorEmpleados cemp = new ControladorEmpleados(usuario, contrasena, vemp);
            vemp.setLocationRelativeTo(null);
            vemp.setVisible(true);
        }
        else if(evt.getSource() == vae.btGuardar){
                String idEmpleado = vae.tfIdEmpleado.getText();
                String nombre = vae.tfNombre.getText();
                String aPat = vae.tfAPat.getText();
                String aMat = vae.tfAMat.getText();
                String cargo = vae.tfCargo.getText();
                String calle = vae.tfCalle.getText();
                String colonia = vae.tfColonia.getText();
                String numero = vae.tfNumero.getText();
                /*if(String.valueOf(vae.tfIdEmpleado.getText()) == " " || String.valueOf(vae.tfIdTienda.getText()) == "" || String.valueOf(vae.tfNombre.getText()) == "" || String.valueOf(vae.tfAPat.getText()) == "" || String.valueOf(vae.tfAMat.getText()) == "" 
                && String.valueOf(vae.tfCargo.getText()) == "" || String.valueOf(vae.tfCalle.getText()) == "" || String.valueOf(vae.tfColonia.getText()) == "" || String.valueOf(vae.tfNumero.getText()) == ""){
                    System.out.println("si");
                }
                else{
                    System.out.println("no");
                }*/
                if (idEmpleado.equals("") || nombre.equals("") || aPat.equals("") || aMat.equals("") || cargo.equals("") || calle.equals("") || colonia.equals("") || numero.equals("")){
                    JOptionPane.showMessageDialog(null,"Faltan datos por agregar");
                    System.out.println("Faltan datos");
                }
                else{
                    String sql = "INSERT INTO empleado VALUES(?,?,?,?,?,?,?,?,?)";
                    try{
                        
                        csql.conexion(usuario, contrasena);
                        PreparedStatement ps = csql.getCx().prepareStatement(sql);
                        ps.setString(1, idEmpleado);
                        ps.setString(2, "TI001");
                        ps.setString(3, nombre);
                        ps.setString(4, aPat);
                        ps.setString(5, aMat);
                        ps.setString(6, cargo);
                        ps.setString(7, calle);
                        ps.setString(8, colonia);
                        ps.setString(9, numero);
                        ps.execute();
                        JOptionPane.showMessageDialog(null,"Ingresado el usuario con id "+idEmpleado);
                    } catch(Exception ex){
                        JOptionPane.showMessageDialog(null,"Has ingresado una ID duplicada o un valor que excede los limites");
                    }
                    
                }
            
        }
    }
    
}
