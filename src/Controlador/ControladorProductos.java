/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.ConexionSQL;
import Vistas.VistaAgregarProducto;
import Vistas.VistaEliminarProducto;
import Vistas.VistaMenuPrincipal;
import Vistas.VistaModificarProducto;
import Vistas.VistaProductos;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author HP
 */
public class ControladorProductos implements ActionListener{
    VistaProductos vp = new VistaProductos();
    ConexionSQL csql = new ConexionSQL();
    String usuario;
    String contrasena;
    
    public ControladorProductos(String usuario, String contrasena, VistaProductos vp){
        this.vp = vp;
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.vp.btNuevoProducto.addActionListener(this);
        this.vp.btModificarProducto.addActionListener(this);
        this.vp.btEliminarProducto.addActionListener(this);
        this.vp.btRegresar.addActionListener(this);
        mostrarDatos();
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        if(evt.getSource() == vp.btRegresar){
            VistaMenuPrincipal vmp = new VistaMenuPrincipal();
            ControladorMenu cmp = new ControladorMenu(usuario, contrasena, vmp);
            vmp.setLocationRelativeTo(null);
            vmp.setVisible(true);
            vp.dispose();
        }
        else if(evt.getSource() == vp.btNuevoProducto){
            VistaAgregarProducto vap = new VistaAgregarProducto();
            ControladorAgregarProducto cap = new ControladorAgregarProducto(usuario, contrasena, vap);
            vap.setLocationRelativeTo(null);
            vap.setVisible(true);
            vap.setResizable(false);
            vp.dispose();
        }
        else if(evt.getSource() == vp.btModificarProducto){
            VistaModificarProducto vmp = new VistaModificarProducto();
            ControladorModificarProducto cmp = new ControladorModificarProducto(usuario, contrasena, vmp);
            vmp.setLocationRelativeTo(null);
            vmp.setResizable(false);
            vmp.setVisible(true);
            vp.dispose();
        }
        else if(evt.getSource() == vp.btEliminarProducto){
            VistaEliminarProducto vep = new VistaEliminarProducto();
            ControladorEliminarProducto cep = new ControladorEliminarProducto(usuario, contrasena, vep);
            vep.setLocationRelativeTo(null);
            vep.setResizable(false);
            vep.setVisible(true);
            vp.dispose();
        }
        else{
            
        }
    }
    public void mostrarDatos(){
        //csql.conexion(getUsuario(), getContrasena());
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("idProducto");
        modelo.addColumn("idProveedor");
        modelo.addColumn("nombreProducto");
        modelo.addColumn("marcaProducto");
        modelo.addColumn("precioCompra");
        modelo.addColumn("precioVenta");
        modelo.addColumn("cantidad");
        
        
        vp.tablaProductos.setModel(modelo);
        String sql = "SELECT * FROM producto";
        String datos[] = new String[9];
        
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
                modelo.addRow(datos);
            }
        }
        catch(Exception ex){
            
        }
        
    }
    
}
