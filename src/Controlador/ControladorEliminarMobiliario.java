/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.ConexionSQL;
import Vistas.VistaEliminarMobiliario;
import Vistas.VistaMobiliario;
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
public class ControladorEliminarMobiliario implements ActionListener{
    String usuario;
    String contrasena;
    VistaEliminarMobiliario vemob = new VistaEliminarMobiliario();
    ConexionSQL csql = new ConexionSQL();
    
    public ControladorEliminarMobiliario(String usuario, String contrasena, VistaEliminarMobiliario vemob){
        this.vemob = vemob;
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.vemob.btRegresar.addActionListener(this);
        this.vemob.btBuscar.addActionListener(this);
        this.vemob.btEliminar.addActionListener(this);
        mostrarDatos("");
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        if(evt.getSource() == vemob.btRegresar){
            VistaMobiliario vmob = new VistaMobiliario();
            ControladorMobiliario cmob = new ControladorMobiliario(usuario, contrasena, vmob);
            vmob.setLocationRelativeTo(null);
            vmob.setResizable(false);
            vmob.setVisible(true);
            vemob.dispose();
        }
        else if(evt.getSource() == vemob.btBuscar){
            String idABuscar = vemob.tfBuscar.getText();
            mostrarDatos(idABuscar);
        }
        else if(evt.getSource() == vemob.btEliminar){
            eliminarMobiliario();
        }
    }
    
    
    public void mostrarDatos(String idABuscar){
                //csql.conexion(getUsuario(), getContrasena());
                DefaultTableModel modelo = new DefaultTableModel();
                modelo.addColumn("idMobiliario");
                modelo.addColumn("idTienda");
                modelo.addColumn("nombreMobiliario");
                modelo.addColumn("descripcion");
                
                vemob.tablaMobiliario.setModel(modelo);
                String sql;
                if(idABuscar.equals(""))
                 sql = "SELECT * FROM mobiliario";
                else
                    sql = "SELECT * FROM mobiliario WHERE idMov='"+idABuscar+"'";
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
                        modelo.addRow(datos);
            }
        }
        catch(Exception ex){
            
        }
        
    }
    
    public void eliminarMobiliario(){
         int fila = vemob.tablaMobiliario.getSelectedRow();
         if(fila >= 0){
             String idSeleccionada = vemob.tablaMobiliario.getValueAt(fila, 0).toString();
          try{
              System.out.println(idSeleccionada);  
              csql.conexion(usuario, contrasena);
              
              //ELIMINAR EL MUEBLE
              PreparedStatement ps = csql.getCx().prepareStatement("DELETE FROM mobiliario WHERE idMov='"+idSeleccionada+"'");
              ps.execute();
              JOptionPane.showMessageDialog(null, "Registro eliminado");
              mostrarDatos("");
            
            }catch(SQLException e){
                JOptionPane.showMessageDialog(null, "No se pudo eliminar" + e);
            }
         }
         else
             JOptionPane.showMessageDialog(null, "No se ha seleccionado la fila");
    }
    
}
