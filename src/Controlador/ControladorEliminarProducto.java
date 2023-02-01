/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;
import Vistas.VistaEliminarProducto;
import Modelo.ConexionSQL;
import Vistas.VistaProductos;
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
public class ControladorEliminarProducto implements ActionListener{
    String usuario;
    String contrasena;
    VistaEliminarProducto vep = new VistaEliminarProducto();
    ConexionSQL csql = new ConexionSQL();
    
    public ControladorEliminarProducto(String usuario, String contrasena, VistaEliminarProducto vep){
        this.vep = vep;
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.vep.btBuscar.addActionListener(this);
        this.vep.btEliminar.addActionListener(this);
        this.vep.btRegresar.addActionListener(this);
        mostrarDatos("");
    }
    @Override
    public void actionPerformed(ActionEvent evt) {
        if(evt.getSource() == vep.btRegresar){
            VistaProductos vp = new VistaProductos();
            ControladorProductos cp = new ControladorProductos(usuario, contrasena, vp);
            vp.setLocationRelativeTo(null);
            vp.setResizable(false);
            vp.setVisible(true);
            vep.dispose();
        }
        else if(evt.getSource() == vep.btBuscar){
            String idABuscar = vep.tfBuscar.getText();
            mostrarDatos(idABuscar);
        }
        else if(evt.getSource() == vep.btEliminar){
            eliminarProducto();
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
        
        
        vep.tablaProductos.setModel(modelo);
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
    
    public void eliminarProducto(){
         int fila = vep.tablaProductos.getSelectedRow();
         if(fila >= 0){
             String idSeleccionada = vep.tablaProductos.getValueAt(fila, 0).toString();
          try{
              System.out.println(idSeleccionada);  
              csql.conexion(usuario, contrasena);
              
              //ELIMINAR EL producto
              PreparedStatement ps = csql.getCx().prepareStatement("DELETE FROM producto WHERE idProducto='"+idSeleccionada+"'");
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
