/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.ConexionSQL;
import Vistas.VistaMenuPrincipal;
import Vistas.VistaRegistroVentas;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author HP
 */
public class ControladorRegistroVentas implements ActionListener{
    String usuario;
    String contrasena;
    VistaRegistroVentas vrv = new VistaRegistroVentas();
    ConexionSQL csql = new ConexionSQL();
    
    public ControladorRegistroVentas(String usuario, String contrasena, VistaRegistroVentas vrv){
        this.vrv = vrv;
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.vrv.btRegresar.addActionListener(this);
        this.vrv.btBuscar.addActionListener(this);
        this.vrv.btAnalizar.addActionListener(this);
        mostrarDatos("");
        mostrarSUM();
        
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        if(evt.getSource() == vrv.btRegresar){
            regresarAlMenu();
        }
        else if(evt.getSource() == vrv.btBuscar){
            String idABuscar = vrv.tfBuscar.getText();
            mostrarDatos(idABuscar);
        }
        else if(evt.getSource() == vrv.btAnalizar){
            analizar();
        }
        
    }
    
    public void regresarAlMenu(){
        VistaMenuPrincipal vmp = new VistaMenuPrincipal();
        ControladorMenu cmp = new ControladorMenu(usuario, contrasena, vmp);
        vmp.setLocationRelativeTo(null);
        vmp.setResizable(false);
        vmp.setVisible(true);
        vrv.dispose();
    }
    
    public void mostrarDatos(String idABuscar){
                //csql.conexion(getUsuario(), getContrasena());
                DefaultTableModel modelo = new DefaultTableModel();
                modelo.addColumn("idVenta");
                modelo.addColumn("idProducto");
                modelo.addColumn("nombreProd");
                modelo.addColumn("precioVenta");
                modelo.addColumn("cantidadVendida");

                
                vrv.tablaVIEW.setModel(modelo);
                String sql;
                if(idABuscar.equals(""))
                    sql = "SELECT idVenta, idProducto, nombreProd, precioVenta, cantidadVen FROM ventaProducto;";
                else
                    sql = "SELECT idVenta, idProducto, nombreProd, precioVenta, cantidadVen FROM ventaProducto WHERE idVenta = '"+idABuscar+"';";
                String datos[] = new String[5];
                
                try{
                    csql.conexion(usuario, contrasena);
                    Statement st = csql.getCx().createStatement();
                    ResultSet rs = st.executeQuery(sql);
                    while(rs.next()){
                        datos[0]=rs.getString(1);
                        datos[1]=rs.getString(2);
                        datos[2]=rs.getString(3);
                        datos[3]=rs.getString(4);
                        datos[4]=rs.getString(5);
                        modelo.addRow(datos);
            }
        }
        catch(Exception ex){
            System.out.println("NOSEPUEDE" + ex);
        }
        
    }
    public void mostrarSUM(){
        DefaultTableModel modelo = new DefaultTableModel();
                modelo.addColumn("idVenta");
                modelo.addColumn("Ganancia total");


                
                vrv.tablaSUM.setModel(modelo);
                String sql;
                sql = "SELECT * FROM totalNetoVenta";

                String datos[] = new String[2];
                
                try{
                    csql.conexion(usuario, contrasena);
                    Statement st = csql.getCx().createStatement();
                    ResultSet rs = st.executeQuery(sql);
                    while(rs.next()){
                        datos[0]=rs.getString(1);
                        datos[1]=rs.getString(2);
                        modelo.addRow(datos);
            }
        }
        catch(Exception ex){
            
        }
    }
    
    public void analizar(){
        vrv.tfBuscar.setText("");
        int fila = vrv.tablaSUM.getSelectedRow();
         //if(fila > 0){
            String idVenta = vrv.tablaSUM.getValueAt(fila, 0).toString();

            //vme.tfIdTienda.setText(idTienda);
            vrv.tfBuscar.setText(idVenta);
            mostrarDatos(vrv.tfBuscar.getText());
    }
    
}
