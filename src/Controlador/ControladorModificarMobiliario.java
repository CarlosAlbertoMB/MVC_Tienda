/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.ConexionSQL;
import Vistas.VistaMobiliario;
import Vistas.VistaModificarMobiliario;
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
public class ControladorModificarMobiliario implements ActionListener{
    String usuario;
    String contrasena;
    VistaModificarMobiliario vmmob = new VistaModificarMobiliario();
    ConexionSQL csql = new ConexionSQL();
    
    public ControladorModificarMobiliario(String usuario, String contrasena, VistaModificarMobiliario vmmob){
        this.vmmob = vmmob;
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.vmmob.btRegresar.addActionListener(this);
        this.vmmob.btBuscar.addActionListener(this);
        this.vmmob.btModificar.addActionListener(this);
        this.vmmob.btGuardar.addActionListener(this);
        mostrarDatos("");
    }
    @Override
    public void actionPerformed(ActionEvent evt) {
        if(evt.getSource() == vmmob.btRegresar){
            VistaMobiliario vmob = new VistaMobiliario();
            ControladorMobiliario cmob = new ControladorMobiliario(usuario, contrasena, vmob);
            vmob.setLocationRelativeTo(null);
            vmob.setResizable(false);
            vmob.setVisible(true);
            vmmob.dispose();
        }
        else if(evt.getSource() == vmmob.btBuscar){
            String idABuscar = vmmob.tfBuscar.getText();
            mostrarDatos(idABuscar);
        }
        else if(evt.getSource() == vmmob.btModificar){
            modificar();
        }
        else if(evt.getSource() == vmmob.btGuardar){
            actualizar();
        }
    }
    
    public void mostrarDatos(String idABuscar){
        //csql.conexion(getUsuario(), getContrasena());
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("idMobiliario");
        modelo.addColumn("idTienda");
        modelo.addColumn("nombreMobiliario");
        modelo.addColumn("Descripcion");
        
        
        vmmob.tablaMobiliario.setModel(modelo);
        String sql;
        if(idABuscar.equals(""))
            sql = "SELECT * FROM mobiliario";
        else
            sql = "SELECT * FROM mobiliario WHERE idMov='"+idABuscar+"'";
        String datos[] = new String[4];
        
        try{
            csql.conexion(usuario, contrasena);
            Statement st = csql.getCx().createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                datos[0]=rs.getString(1);
                datos[1]=rs.getString(2);
                datos[2]=rs.getString(3);
                datos[3]=rs.getString(4);
                modelo.addRow(datos);
            }
        }
        catch(Exception ex){
            
        }
        
    }
    public void modificar(){
        int fila = vmmob.tablaMobiliario.getSelectedRow();
         //if(fila > 0){
            String idMobiliario = vmmob.tablaMobiliario.getValueAt(fila, 0).toString();
            //String idTienda = vmmob.tablaMobiliario.getValueAt(fila, 1).toString();
            String nombre = vmmob.tablaMobiliario.getValueAt(fila, 2).toString();
            String descripcion = vmmob.tablaMobiliario.getValueAt(fila, 3).toString();

            //vme.tfIdTienda.setText(idTienda);
            vmmob.tfIdMobiliario.setText(idMobiliario);
            vmmob.tfNombre.setText(nombre);
            vmmob.tfDescripcion.setText(descripcion);
            vmmob.tfIdMobiliario.setEnabled(false);
    }
    
    public void actualizar(){
        try{ 
              csql.conexion(usuario, contrasena);
              String idMobiliario = vmmob.tfIdMobiliario.getText();
              String idTienda = vmmob.tfIdTienda.getText();
              String nombre = vmmob.tfNombre.getText();
              String descripcion = vmmob.tfDescripcion.getText();

              //ELIMINAR EL EMPLEADO
              PreparedStatement ps = csql.getCx().prepareStatement("UPDATE mobiliario SET nombreMov = '"+nombre+"', descripcion = '"+descripcion+"' WHERE idMov = '"+idMobiliario+"'");
              
              ps.execute();
              JOptionPane.showMessageDialog(null, "registro actualizado");
              mostrarDatos("");
            
            }catch(SQLException e){
                JOptionPane.showMessageDialog(null, "No se pudo actualizar" + e);
            }
    }
    
}
