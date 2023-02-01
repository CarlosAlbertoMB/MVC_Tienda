/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;
import Vistas.VistaEmpleados;
import Modelo.ConexionSQL;
import Vistas.VistaModificarEmpleado;
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
public class ControladorModificarEmpleado implements ActionListener{
    String usuario;
    String contrasena;
    VistaModificarEmpleado vme = new VistaModificarEmpleado();
    ConexionSQL csql = new ConexionSQL();
    
    public ControladorModificarEmpleado(String usuario, String contrasena, VistaModificarEmpleado vme){
        this.vme = vme;
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.vme.tfBuscar.addActionListener(this);
        this.vme.btBuscar.addActionListener(this);
        this.vme.btModificar.addActionListener(this);
        this.vme.btGuardar.addActionListener(this);
        this.vme.btRegresar.addActionListener(this);
        this.vme.tfIdEmpleado.addActionListener(this);
        this.vme.tfIdTienda.addActionListener(this);
        this.vme.tfNombre.addActionListener(this);
        this.vme.tfAPat.addActionListener(this);
        this.vme.tfAMat.addActionListener(this);
        this.vme.tfCargo.addActionListener(this);
        this.vme.tfCalle.addActionListener(this);
        this.vme.tfColonia.addActionListener(this);
        this.vme.tfNumero.addActionListener(this);
        mostrarDatos("");
    }
    
    @Override
    public void actionPerformed(ActionEvent evt) {
        if(evt.getSource() == vme.btBuscar){
            String idABuscar = vme.tfBuscar.getText();
            mostrarDatos(idABuscar);
        }
        else if(evt.getSource() == vme.btModificar){
            modificarEmpleado();
        }
        else if(evt.getSource() == vme.btGuardar){
            actualizar();
        }
        else if(evt.getSource() == vme.btRegresar){
            VistaEmpleados vemp = new VistaEmpleados();
            ControladorEmpleados cemp = new ControladorEmpleados(usuario, contrasena, vemp);
            vemp.setLocationRelativeTo(null);
            vemp.setVisible(true);
            vme.dispose();
        }
        else{
            
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
                
                vme.tablaEmpleado.setModel(modelo);
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
    
    public void modificarEmpleado(){
         int fila = vme.tablaEmpleado.getSelectedRow();
         //if(fila > 0){
            String idEmpleado = vme.tablaEmpleado.getValueAt(fila, 0).toString();
            String idTienda = vme.tablaEmpleado.getValueAt(fila, 1).toString();
            String nombre = vme.tablaEmpleado.getValueAt(fila, 2).toString();
            String aPat = vme.tablaEmpleado.getValueAt(fila, 3).toString();
            String aMat = vme.tablaEmpleado.getValueAt(fila, 4).toString();
            String cargo = vme.tablaEmpleado.getValueAt(fila, 5).toString();
            String calle = vme.tablaEmpleado.getValueAt(fila, 6).toString();
            String colonia = vme.tablaEmpleado.getValueAt(fila, 7).toString();
            String numero = vme.tablaEmpleado.getValueAt(fila, 8).toString();
            vme.tfIdEmpleado.setText(idEmpleado);
            vme.tfIdEmpleado.setEnabled(false);
            //vme.tfIdTienda.setText(idTienda);
            vme.tfNombre.setText(nombre);
            vme.tfAPat.setText(aPat);
            vme.tfAMat.setText(aMat);
            vme.tfCargo.setText(cargo);
            vme.tfCalle.setText(calle);
            vme.tfColonia.setText(colonia);
            vme.tfNumero.setText(numero);
         //}
         
         
         
         /*
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
             JOptionPane.showMessageDialog(null, "No se ha seleccionado la fila");*/
    }
    public void actualizar(){
        try{ 
              csql.conexion(usuario, contrasena);
              String idEmpleado = vme.tfIdEmpleado.getText();
              String nombre = vme.tfNombre.getText();
              String aPat = vme.tfAPat.getText();
              String aMat = vme.tfAMat.getText();
              String cargo = vme.tfCargo.getText();
              String calle = vme.tfCalle.getText();
              String colonia = vme.tfColonia.getText();
              String numero = vme.tfNumero.getText();
              //ELIMINAR EL EMPLEADO
              PreparedStatement ps = csql.getCx().prepareStatement("UPDATE empleado SET nombreE ='"+nombre+"', aPatE = '"+aPat+"', aMatE = '"+aMat+"', cargo = '"+cargo+"', calle = '"+calle+"', colonia = '"+colonia+"', numero = '"+numero+"' WHERE idEmpleado = '"+idEmpleado+"'");
              
              ps.execute();
              JOptionPane.showMessageDialog(null, "empleado actualizado");
              mostrarDatos("");
            
            }catch(SQLException e){
                JOptionPane.showMessageDialog(null, "No se pudo actualizar" + e);
            }
    }
    
}
