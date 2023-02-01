/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.ConexionSQL;
import Vistas.VistaMenuPrincipal;
import Vistas.VistaProveedores;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author HP
 */
public class ControladorProveedores implements ActionListener {
    String usuario;
    String contrasena;
    VistaProveedores vip = new VistaProveedores();
    ConexionSQL csql = new ConexionSQL();
    
    public ControladorProveedores(String usuario, String contrasena, VistaProveedores vip){
        this.vip = vip;
        this.vip.btRegresar.addActionListener(this);
        this.usuario = usuario;
        this.contrasena = contrasena;
        mostrarDatos();
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        if(evt.getSource() == vip.btRegresar){
            VistaMenuPrincipal vmp = new VistaMenuPrincipal();
            ControladorMenu cmp = new ControladorMenu(usuario, contrasena, vmp);
            vmp.setLocationRelativeTo(null);
            vmp.setResizable(false);
            vmp.setVisible(true);
            vip.dispose();
        }
    }
    
    public void mostrarDatos(){
                //csql.conexion(getUsuario(), getContrasena());
                DefaultTableModel modelo = new DefaultTableModel();
                modelo.addColumn("idProveedor");
                modelo.addColumn("marcaProveedor");
                modelo.addColumn("dia");
                modelo.addColumn("hora");
                
                vip.tablaProveedores.setModel(modelo);
                String sql;
                sql = "SELECT * FROM proveedor";
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
    
}
