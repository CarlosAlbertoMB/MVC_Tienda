/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.ConexionSQL;
import Vistas.VistaModificarProducto;
import Vistas.VistaProductos;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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
public class ControladorModificarProducto implements ActionListener{
    String usuario;
    String contrasena;
    VistaModificarProducto vmp = new VistaModificarProducto();
    ConexionSQL csql = new ConexionSQL();
    
    public ControladorModificarProducto(String usuario, String contrasena, VistaModificarProducto vmp){
        this.vmp = vmp;
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.vmp.btBuscar.addActionListener(this);
        this.vmp.btModificar.addActionListener(this);
        this.vmp.btGuardar.addActionListener(this);
        this.vmp.btRegresar.addActionListener(this);
        this.vmp.btClearCantidad.addActionListener(this);
        this.vmp.btClearCompra.addActionListener(this);
        this.vmp.btClearVenta.addActionListener(this);
        this.vmp.tfPrecioCompra.addKeyListener(new KeyAdapter(){
            public void keyPressed(KeyEvent ke){
                String value = vmp.tfPrecioCompra.getText();
                int l = value.length();
                if(ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9'){
                    vmp.tfPrecioCompra.setEditable(true);
                } else{
                    vmp.tfPrecioCompra.setEditable(false);
                }
            }
        });
        this.vmp.tfPrecioVenta.addKeyListener(new KeyAdapter(){
            public void keyPressed(KeyEvent ke){
                String value = vmp.tfPrecioVenta.getText();
                int l = value.length();
                if(ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9'){
                    vmp.tfPrecioVenta.setEditable(true);
                } else{
                    vmp.tfPrecioVenta.setEditable(false);
                }
            }
        });
        this.vmp.tfCantidad.addKeyListener(new KeyAdapter(){
            public void keyPressed(KeyEvent ke){
                String value = vmp.tfCantidad.getText();
                int l = value.length();
                if(ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9'){
                    vmp.tfCantidad.setEditable(true);
                } else{
                    vmp.tfCantidad.setEditable(false);
                }
            }
        });
        mostrarDatos("");
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        if(evt.getSource() == vmp.btRegresar){
            VistaProductos vp = new VistaProductos();
            ControladorProductos cp = new ControladorProductos(usuario, contrasena, vp);
            vp.setLocationRelativeTo(null);
            vp.setResizable(false);
            vp.setVisible(true);
            vmp.dispose();
        }
        else if(evt.getSource() == vmp.btClearCompra){
            vmp.tfPrecioCompra.setText("");
        }
        else if(evt.getSource() == vmp.btClearVenta){
            vmp.tfPrecioVenta.setText("");
        }
        else if(evt.getSource() == vmp.btClearCantidad){
            vmp.tfCantidad.setText("");
        }
        else if(evt.getSource() == vmp.btBuscar){
            String idABuscar = vmp.tfBuscar.getText();
            mostrarDatos(idABuscar);
        }
        else if(evt.getSource() == vmp.btModificar){
            modificarProducto();
        }
        else if(evt.getSource() == vmp.btGuardar){
            actualizar();
        }
    }
    
    public void mostrarDatos(String idABuscar){
        //csql.conexion(getUsuario(), getContrasena());
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("idProducto");
        modelo.addColumn("idProveedor");
        modelo.addColumn("nombreProducto");
        modelo.addColumn("marcaProducto");
        modelo.addColumn("precioCompra");
        modelo.addColumn("precioVenta");
        modelo.addColumn("cantidad");
        
        
        vmp.tablaProductos.setModel(modelo);
        String sql = "SELECT * FROM producto";
        if(idABuscar.equals("")){
           //sql = "SELECT * FROM producto";
           
        }
        else{
            sql = "SELECT * FROM producto WHERE idProducto = '"+idABuscar+"'";
        }
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
    
    public void modificarProducto(){
        int fila = vmp.tablaProductos.getSelectedRow();
        
        String idProducto = vmp.tablaProductos.getValueAt(fila, 0).toString();
        String idProveedor = vmp.tablaProductos.getValueAt(fila, 1).toString();
        String nombreProducto = vmp.tablaProductos.getValueAt(fila, 2).toString();
        String marcaProducto = vmp.tablaProductos.getValueAt(fila, 3).toString();
        String precioCompra = vmp.tablaProductos.getValueAt(fila, 4).toString();
        String precioVenta = vmp.tablaProductos.getValueAt(fila, 5).toString();
        String cantidad = vmp.tablaProductos.getValueAt(fila, 6).toString();
        
       vmp.tfIdProducto.setText(idProducto);
       vmp.tfIdProducto.setEnabled(false);
       vmp.tfIdProveedor.setText(idProveedor);
       vmp.tfNombreProducto.setText(nombreProducto);
       vmp.tfMarcaProducto.setText(marcaProducto);
       vmp.tfPrecioCompra.setText(precioCompra);
       vmp.tfPrecioVenta.setText(precioVenta);
       vmp.tfCantidad.setText(cantidad);
    }
    
    public void actualizar(){
        try{ 
              csql.conexion(usuario, contrasena);
              String idProducto = vmp.tfIdProducto.getText();
              String idProveedor = vmp.tfIdProveedor.getText();
              String nombreProducto = vmp.tfNombreProducto.getText();
              String marcaProducto = vmp.tfMarcaProducto.getText();
              String precioCompra = vmp.tfPrecioCompra.getText();
              String precioVenta = vmp.tfPrecioVenta.getText();
              String cantidad = vmp.tfCantidad.getText();
              
              //ELIMINAR EL EMPLEADO
              PreparedStatement ps = csql.getCx().prepareStatement("UPDATE producto SET idProveedorF ='"+idProveedor+"', nombreProd = '"+nombreProducto+"', marcaProd = '"+marcaProducto+"', precioCompra = '"+precioCompra+"', precioVenta = '"+precioVenta+"', cantidad = '"+cantidad+"' WHERE idProducto = '"+idProducto+"'");
              
              ps.execute();
              JOptionPane.showMessageDialog(null, "producto actualizado");
              mostrarDatos("");
            
            }catch(SQLException e){
                JOptionPane.showMessageDialog(null, "No se pudo actualizar" + e);
            }
    }
}
