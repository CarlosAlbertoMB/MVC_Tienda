/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.ConexionSQL;
import Vistas.VistaMenuPrincipal;
import Vistas.VistaVentaNueva;
import java.awt.Color;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author HP
 */
public class ControladorVentaNueva implements ActionListener {
    String usuario;
    String contrasena;
    VistaVentaNueva vvn = new VistaVentaNueva();
    ConexionSQL csql = new ConexionSQL();
    float costoAcumulado = 0;

    
    public ControladorVentaNueva(String usuario, String contrasena, VistaVentaNueva vvn){
        this.vvn = vvn;
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.vvn.btRegresar.addActionListener(this);
        this.vvn.btClearCantidad.addActionListener(this);
        this.vvn.btNuevaVenta.addActionListener(this);
        this.vvn.btGuardarVenta.addActionListener(this);
        this.vvn.btBuscar.addActionListener(this);
        this.vvn.btModificar.addActionListener(this);
        this.vvn.btAgregar.addActionListener(this);
        this.vvn.tfCantidad.addKeyListener(new KeyAdapter(){
            public void keyPressed(KeyEvent ke){
                String value = vvn.tfCantidad.getText();
                int l = value.length();
                if(ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9'){
                    vvn.tfCantidad.setEditable(true);
                } else{
                    vvn.tfCantidad.setEditable(false);
                }
            }
        });
        mostrarProductos("");
        mostrarTablaLista();
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        if(evt.getSource() == vvn.btRegresar){
            limpiarCuadroTransacciones();
            volverAlMenu();
        }
        else if(evt.getSource() == vvn.btModificar){
            if(vvn.btBuscar.equals("")){
                JOptionPane.showMessageDialog(null,"Escribe una id");
            }
            else{
                productoSeleccionado();
            }
            
        }
        else if(evt.getSource() == vvn.btNuevaVenta){
            prepararCampos();   
        }
        else if(evt.getSource() == vvn.btAgregar){
           Boolean resultado = validarExistencia();
           if (resultado == true){
               costoAcumulado = costoAcumulado + obtenerPrecioVenta();
               vvn.tfTotal.setText(""+costoAcumulado+"");
               agregarInsert();
               agregarUpdate();
               mostrarTablaLista();
           }
           else{
               JOptionPane.showMessageDialog(null,"No hay suficientes en existencia");
           }
        }
        else if(evt.getSource() == vvn.btGuardarVenta){
            String cantidad = vvn.tfCantidad.getText();
            String idProducto = vvn.tfIdProducto.getText();
            
            List<String> transacciones = new ArrayList<String>();
            List<String> actualizaciones = new ArrayList<String>();
            String sql;
            
            //String sql = "SELECT * FROM producto";
            sql = "SELECT * FROM currentTransaction";
           
        
            try{
                csql.conexion(usuario, contrasena);
                Statement st = csql.getCx().createStatement();
                ResultSet rs = st.executeQuery(sql);
                while(rs.next()){
                    transacciones.add(rs.getString(1));
                        
                }
            }
            catch(Exception ex){
                
            }
            //Para las actualizaciones
            sql = "SELECT * FROM auxiliarRestas";
           
        
            try{
                csql.conexion(usuario, contrasena);
                Statement st = csql.getCx().createStatement();
                ResultSet rs = st.executeQuery(sql);
                while(rs.next()){
                    actualizaciones.add(rs.getString(1));
                        
                }
            }
            catch(Exception ex){
                
            }
            
            
            enviarDatos(transacciones);
            realizarResta(actualizaciones);
            limpiarCuadroTransacciones();
            reiniciarCampos();
            

                    
        }
        
        else if(evt.getSource() == vvn.btClearCantidad){
            vvn.tfCantidad.setText("");
        }
        else if(evt.getSource() == vvn.btBuscar){
            String idABuscar = vvn.tfBuscar.getText();
            mostrarProductos(idABuscar);
        }
        else{
            
        }
    }
    
    public void mostrarProductos(String idABuscar){
        //csql.conexion(getUsuario(), getContrasena());
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("idProducto");
        modelo.addColumn("idProveedor");
        modelo.addColumn("nombreProducto");
        modelo.addColumn("marcaProducto");
        modelo.addColumn("precioCompra");
        modelo.addColumn("precioVenta");
        modelo.addColumn("cantidad");
        
        
        vvn.tablaProductos.setModel(modelo);
        String sql;
        if(idABuscar.equals("")){
            sql  = "SELECT * FROM producto";
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
    
    public void mostrarTablaLista(){
        DefaultTableModel modeloBase = new DefaultTableModel();
        modeloBase.addColumn("Transaccion actual");
        vvn.tablaVentaActual.setModel(modeloBase);
        String sql  = "SELECT * FROM currentTransaction";
        
        String datos[] = new String[1];
        
        try{
            csql.conexion(usuario, contrasena);
            Statement st = csql.getCx().createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                datos[0]=rs.getString(1);
                modeloBase.addRow(datos);
            }
        }
        catch(Exception ex){
            
        }
    }
    public String generarIDVenta(){
        String idVenta;
        String formatoF = "ddMMyyyyhhmmss";
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(formatoF);
        String fecha = sdf.format(date);
        idVenta = "VN"+fecha;
        return idVenta;
        
        
    }
    public float obtenerPrecioVenta(){
        //List<String> cantidad = new ArrayList<String>();
        float cantidadActual=0;
        int multiplicador = Integer.parseInt(vvn.tfCantidad.getText());
        String idProducto = vvn.tfIdProducto.getText();
        
        String sql;
            
        //String sql = "SELECT * FROM producto";
        sql = "SELECT precioVenta FROM producto where idProducto = '"+idProducto+"'";
           
        
        try{
            csql.conexion(usuario, contrasena);
            Statement st = csql.getCx().createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                cantidadActual = Float.parseFloat(rs.getString(1));
            }
        }
        catch(Exception ex){
            System.out.println("NOSEPUEDE" + ex);
        }
        cantidadActual = cantidadActual * multiplicador;
        
        //vvn.tfTotal
        return cantidadActual;
    }
    public void prepararCampos(){
        
        vvn.tablaProductos.setEnabled(true);
        vvn.tfIdVenta.setText(generarIDVenta());
        vvn.tablaProductos.setBackground(Color.WHITE);
        vvn.btAgregar.setEnabled(true);
        vvn.btModificar.setEnabled(true);
        //vvn.btCalcular.setEnabled(true);
        vvn.btGuardarVenta.setEnabled(true);
    }
    public void reiniciarCampos(){
        vvn.tablaProductos.setEnabled(false);
        vvn.tfNombreProducto.setText("");
        vvn.tablaProductos.setBackground(Color.RED);
        vvn.btAgregar.setEnabled(false);
        //vvn.btCalcular.setEnabled(false);
        vvn.btGuardarVenta.setEnabled(false);
        vvn.tfIdVenta.setText("");
        vvn.tfIdProducto.setText("");
        vvn.tfCantidad.setText("");
        vvn.btModificar.setEnabled(false);
        mostrarProductos("");
        mostrarTablaLista();
    }
    
    public void productoSeleccionado(){
        if(vvn.tfBuscar.equals("")){
            JOptionPane.showMessageDialog(null,"Campo vacío");
        }
        else{
            int fila = vvn.tablaProductos.getSelectedRow();
        
            String idProducto = vvn.tablaProductos.getValueAt(fila, 0).toString();
            String nombreProducto = vvn.tablaProductos.getValueAt(fila, 2).toString();
        
            vvn.tfIdProducto.setText(idProducto);
            vvn.tfNombreProducto.setText(nombreProducto);
        }
    }
    public Boolean validarExistencia(){
        Boolean resultado = false;
        int cantidadExistente = 0;
        String idProducto = vvn.tfIdProducto.getText();
        String sql = "SELECT cantidad FROM PRODUCTO WHERE idProducto = '"+idProducto+"'";
        try{
            csql.conexion(usuario, contrasena);
            Statement st = csql.getCx().createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                cantidadExistente = rs.getInt(1);   
            }
            
        }
        catch(Exception ex){
            
        }
        int campoCantidad = Integer.parseInt(vvn.tfCantidad.getText());
        if(campoCantidad <= cantidadExistente){
            resultado = true;
        }
        else{
            resultado = false;
        }
        return resultado;
    }
    public void agregarInsert(){
        String idProducto = vvn.tfIdProducto.getText();
        String cantidad = vvn.tfCantidad.getText();
        String idVenta = vvn.tfIdVenta.getText();
        String nombreString = "INSERT INTO ventas VALUES('"+idVenta+"','"+idProducto+"','"+cantidad+"');";
        if(idProducto.equals("") || cantidad.equals("")){
                JOptionPane.showMessageDialog(null,"Faltan datos por agregar");
                System.out.println("Faltan datos");
            }
            else{
                String sql = "INSERT INTO currentTransaction VALUES(?)";
                    try{
                        
                        csql.conexion(usuario, contrasena);
                        PreparedStatement ps = csql.getCx().prepareStatement(sql);
                        ps.setString(1, nombreString);
                        ps.execute();
                        
                    } catch(Exception ex){
                        
                    }
            }
    }
    
    public void agregarUpdate(){
        String idProducto = vvn.tfIdProducto.getText();
        String cantidad = vvn.tfCantidad.getText();
        String idVenta = vvn.tfIdVenta.getText();
        String nombreString = "update producto set cantidad = cantidad - "+cantidad+" where idProducto = '"+idProducto+"';";
        if(idProducto.equals("") || cantidad.equals("")){
                JOptionPane.showMessageDialog(null,"Faltan datos por agregar");
                System.out.println("Faltan datos");
            }
            else{
                String sql = "INSERT INTO auxiliarRestas VALUES(?)";
                    try{
                        
                        csql.conexion(usuario, contrasena);
                        PreparedStatement ps = csql.getCx().prepareStatement(sql);
                        ps.setString(1, nombreString);
                        ps.execute();
                        
                    } catch(Exception ex){
                        
                    }
            }
    }
    
    public void enviarDatos(List<String> transacciones){
        Iterator<String> iter = transacciones.iterator();
        String sql = "";
        while(iter.hasNext()){
            sql = iter.next();
            try{
                csql.conexion(usuario, contrasena);
                PreparedStatement ps = csql.getCx().prepareStatement(sql);
                ps.execute();
                
                } 
            catch(Exception ex){
                JOptionPane.showMessageDialog(null,"Error");
            }
        }    
        JOptionPane.showMessageDialog(null,"Ingresado");
    }
    public void realizarResta(List<String> actualizaciones){
        Iterator<String> iter = actualizaciones.iterator();
        String sql = "";
        while(iter.hasNext()){
            sql = iter.next();
            try{
                csql.conexion(usuario, contrasena);
                PreparedStatement ps = csql.getCx().prepareStatement(sql);
                ps.execute();
                
                } 
            catch(Exception ex){
                
            }
        }
    }
    
    public void volverAlMenu(){
        VistaMenuPrincipal vmp = new VistaMenuPrincipal();
        ControladorMenu cmp = new ControladorMenu(usuario, contrasena, vmp);
        vmp.setLocationRelativeTo(null);
        vmp.setResizable(false);
        vmp.setVisible(true);
        vvn.dispose();
    }
    public void limpiarCuadroTransacciones(){
        String sql = "DELETE FROM currentTransaction";
                    try{  
                        csql.conexion(usuario, contrasena);
                        PreparedStatement ps = csql.getCx().prepareStatement(sql);
                        ps.execute();
                        
                    } catch(Exception ex){
                        
                    }
        String sql2 = "DELETE FROM auxiliarRestas";
                    try{  
                        csql.conexion(usuario, contrasena);
                        PreparedStatement ps = csql.getCx().prepareStatement(sql2);
                        ps.execute();
                        
                    } catch(Exception ex){
                        
                    }
    }
}
