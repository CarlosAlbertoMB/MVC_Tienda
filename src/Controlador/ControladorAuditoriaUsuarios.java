/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.ConexionSQL;
import Vistas.VistaAuditoriaUsuarios;
import Vistas.VistaMenuPrincipal;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author HP
 */
public class ControladorAuditoriaUsuarios implements ActionListener {
    String usuario;
    String contrasena;
    ConexionSQL csql = new ConexionSQL();
    VistaAuditoriaUsuarios vau = new VistaAuditoriaUsuarios();
    
    public ControladorAuditoriaUsuarios(String usuario, String contrasena, VistaAuditoriaUsuarios vau){
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.vau = vau;
        this.vau.btRegresar.addActionListener(this);
        mostrarDatos1();
        mostrarDatos2();
        mostrarDatos3();
        mostrarDatos4();
    }
    @Override
    public void actionPerformed(ActionEvent evt) {
        if(evt.getSource() == vau.btRegresar){
            VistaMenuPrincipal vmp = new VistaMenuPrincipal();
            ControladorMenu cmp = new ControladorMenu(usuario, contrasena, vmp);
            vmp.setLocationRelativeTo(null);
            vmp.setVisible(true);
            vau.dispose();
        }
    }
    public void mostrarDatos1(){
        //csql.conexion(getUsuario(), getContrasena());
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("idEmpleado");
        modelo.addColumn("Nombre nuevo");
        modelo.addColumn("Nombre viejo");
        
        modelo.addColumn("A Paterno nuevo");
        modelo.addColumn("A Paterno viejo");
        modelo.addColumn("A Materno Nuevo");
        modelo.addColumn("A Materno Viejo");
        modelo.addColumn("cargo nuevo");
        modelo.addColumn("cargo viejo");
        modelo.addColumn("calle nuevo");
        modelo.addColumn("calle viejo");
        modelo.addColumn("colonia nuevo");
        modelo.addColumn("colonia Viejo");
        modelo.addColumn("numero nuevo");
        modelo.addColumn("numero viejo");
        modelo.addColumn("Usuario");
        modelo.addColumn("Fecha");
        modelo.addColumn("Accion");
        
        vau.TablaEmpleadoL.setModel(modelo);
        String sql = "SELECT * FROM Empleado_log";
        String datos[] = new String[18];
        
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
                datos[5]=rs.getString(6);
                datos[6]=rs.getString(7);
                datos[7]=rs.getString(8);
                datos[8]=rs.getString(9);
                datos[9]=rs.getString(10);
                datos[10]=rs.getString(11);
                datos[11]=rs.getString(12);
                datos[12]=rs.getString(13);
                datos[13]=rs.getString(14);
                datos[14]=rs.getString(15);
                datos[15]=rs.getString(16);
                datos[16]=rs.getString(17);
                datos[17]=rs.getString(18);
                
                modelo.addRow(datos);
            }
        }
        catch(Exception ex){
            
        }
        
    }
    
    public void mostrarDatos2(){
        //csql.conexion(getUsuario(), getContrasena());
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("idProducto");
        modelo.addColumn("Nombre producto nuevo");
        modelo.addColumn("Nombre producto viejo");
        modelo.addColumn("Marca producto nuevo");
        modelo.addColumn("Marca producto viejo");
        modelo.addColumn("Precio compra Nuevo");
        modelo.addColumn("Precio compra Viejo");
        modelo.addColumn("Precio venta nuevo");
        modelo.addColumn("Precio venta viejo");
        modelo.addColumn("cantidad nuevo");
        modelo.addColumn("cantidad viejo");
        modelo.addColumn("Usuario");
        modelo.addColumn("Fecha");
        modelo.addColumn("Accion");
        
        vau.TablaProductoL.setModel(modelo);
        String sql = "SELECT * FROM Producto_log";
        String datos[] = new String[14];
        
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
                datos[5]=rs.getString(6);
                datos[6]=rs.getString(7);
                datos[7]=rs.getString(8);
                datos[8]=rs.getString(9);
                datos[9]=rs.getString(10);
                datos[10]=rs.getString(11);
                datos[11]=rs.getString(12);
                datos[12]=rs.getString(13);
                datos[13]=rs.getString(14);
                
                
                modelo.addRow(datos);
            }
        }
        catch(Exception ex){
            
        }
        
    }
    public void mostrarDatos3(){
        //csql.conexion(getUsuario(), getContrasena());
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("idMobiliario");
        modelo.addColumn("Nombre Nuevo");
        modelo.addColumn("Nombre viejo");
        
        modelo.addColumn("Descripcion nuevo");
        modelo.addColumn("Descripcion viejo");
        
        modelo.addColumn("Usuario");
        modelo.addColumn("Fecha");
        modelo.addColumn("Accion");


        
        vau.TablaMobiliarioL.setModel(modelo);
        String sql = "SELECT * FROM mobiliario_log";
        String datos[] = new String[15];
        
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
                datos[5]=rs.getString(6);
                datos[6]=rs.getString(7);
                datos[7]=rs.getString(8);
                
                
                modelo.addRow(datos);
            }
        }
        catch(Exception ex){
            
        }
        
    }
    public void mostrarDatos4(){
        //csql.conexion(getUsuario(), getContrasena());
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("idVenta");
        modelo.addColumn("Producto");
        modelo.addColumn("Cantidad Vendida");
        modelo.addColumn("Usuario");
        modelo.addColumn("Fecha");
        modelo.addColumn("Accion");

        
        vau.TablaVentasL.setModel(modelo);
        String sql = "SELECT idVentaN, IdProductoFN,cantidadVenN, usuario, fecha, accion FROM Ventas_log";
        String datos[] = new String[6];
        
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
                datos[5]=rs.getString(6);

                
                modelo.addRow(datos);
            }
        }
        catch(Exception ex){
            
        }
        
    }
}
