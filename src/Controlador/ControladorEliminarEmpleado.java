/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.ConexionSQL;
import Vistas.VistaEliminarEmpleado;
import Vistas.VistaEmpleados;
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
public class ControladorEliminarEmpleado implements ActionListener {
    VistaEliminarEmpleado vee = new VistaEliminarEmpleado();
    ConexionSQL csql = new ConexionSQL();
    String usuario;
    String contrasena;
    
    public ControladorEliminarEmpleado(String usuario, String contrasena, VistaEliminarEmpleado vee){
        this.vee = vee;
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.vee.tfBuscar.addActionListener(this);
        this.vee.btSearch.addActionListener(this);
        this.vee.btEliminar.addActionListener(this);
        this.vee.btRegresar.addActionListener(this);
        mostrarDatos("");
        //this.vee.tablaEmpleados.addActionListener(this);
    }
    @Override
    public void actionPerformed(ActionEvent evt) {
        if(evt.getSource() == vee.btSearch){
            String idABuscar = vee.tfBuscar.getText();
            mostrarDatos(idABuscar);
            
        }
        else if(evt.getSource() == vee.btEliminar){
            eliminarEmpleado();
        }
        else if(evt.getSource() == vee.btRegresar){
            VistaEmpleados vemp = new VistaEmpleados();
            ControladorEmpleados cemp = new ControladorEmpleados(usuario, contrasena, vemp);
            vemp.setLocationRelativeTo(null);
            vemp.setVisible(true);
            vee.dispose();
        }
    }
    
    
    
    
    
    public void mostrarDatos(String idABuscar){
                //csql.conexion(getUsuario(), getContrasena());
                DefaultTableModel modelo = new DefaultTableModel();
                modelo.addColumn("idEmpleado");
                modelo.addColumn("idTienda");
                modelo.addColumn("nombreE");
                modelo.addColumn("aPatE");
                modelo.addColumn("aMatE");
                modelo.addColumn("cargo");
                modelo.addColumn("calle");
                modelo.addColumn("colonia");
                modelo.addColumn("numero");
                
                vee.tablaEmpleados.setModel(modelo);
                String sql;
                if(idABuscar.equals(""))
                 sql = "SELECT * FROM empleado";
                else
                    sql = "SELECT * FROM empleado WHERE idEmpleado='"+idABuscar+"'";
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
                        datos[7]=rs.getString(8);
                        datos[8]=rs.getString(9);
                        modelo.addRow(datos);
            }
        }
        catch(Exception ex){
            
        }
        
    }
    
    public void eliminarEmpleado(){
         int fila = vee.tablaEmpleados.getSelectedRow();
         if(fila >= 0){
             String idSeleccionada = vee.tablaEmpleados.getValueAt(fila, 0).toString();
          try{
              System.out.println(idSeleccionada);  
              csql.conexion(usuario, contrasena);
              //BORRAR TELEFONOS Y CORREOS ASOCIADOS
              PreparedStatement psT = csql.getCx().prepareStatement("DELETE FROM telefonoE WHERE idEmpleadoF='"+idSeleccionada+"'");
              PreparedStatement psC = csql.getCx().prepareStatement("DELETE FROM correoE WHERE idEmpleadoFE='"+idSeleccionada+"'");
              //ELIMINAR EL EMPLEADO
              PreparedStatement ps = csql.getCx().prepareStatement("DELETE FROM empleado WHERE idEmpleado='"+idSeleccionada+"'");
              psT.execute();
              psC.execute();
              ps.execute();
              JOptionPane.showMessageDialog(null, "empleado eliminado");
              mostrarDatos("");
            
            }catch(SQLException e){
                JOptionPane.showMessageDialog(null, "No se pudo eliminar" + e);
            }
         }
         else
             JOptionPane.showMessageDialog(null, "No se ha seleccionado la fila");
    }
    
    
}
