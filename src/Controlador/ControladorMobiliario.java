/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.ConexionSQL;
import Vistas.VistaAgregarMobiliario;
import Vistas.VistaEliminarMobiliario;
import Vistas.VistaMenuPrincipal;
import Vistas.VistaMobiliario;
import Vistas.VistaModificarMobiliario;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author HP
 */
public class ControladorMobiliario implements ActionListener {
    String usuario;
    String contrasena;
    VistaMobiliario vmob = new VistaMobiliario();
    ConexionSQL csql = new ConexionSQL();
    
    public ControladorMobiliario(String usuario, String contrasena, VistaMobiliario vmob){
        this.vmob = vmob;
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.vmob.btRegresar.addActionListener(this);
        this.vmob.btAgregar.addActionListener(this);
        this.vmob.btModificar.addActionListener(this);
        this.vmob.btEliminar.addActionListener(this);
        mostrarDatos();
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        if(evt.getSource() == vmob.btRegresar){
            VistaMenuPrincipal vmp = new VistaMenuPrincipal();
            ControladorMenu cmp = new ControladorMenu(usuario, contrasena, vmp);
            vmp.setLocationRelativeTo(null);
            vmp.setResizable(false);
            vmp.setVisible(true);
            vmob.dispose();
        }
        else if(evt.getSource() == vmob.btAgregar){
            VistaAgregarMobiliario vamob = new VistaAgregarMobiliario();
            ControladorAgregarMobiliario camob = new ControladorAgregarMobiliario(usuario, contrasena, vamob);
            vamob.setLocationRelativeTo(null);
            vamob.setResizable(false);
            vamob.setVisible(true);
            vmob.dispose();
        }
        else if(evt.getSource() == vmob.btModificar){
            VistaModificarMobiliario vmmob = new VistaModificarMobiliario();
            ControladorModificarMobiliario cmmob = new ControladorModificarMobiliario(usuario, contrasena, vmmob);
            vmmob.setLocationRelativeTo(null);
            vmmob.setResizable(false);
            vmmob.setVisible(true);
            vmob.dispose();
            
        }
        else if(evt.getSource() == vmob.btEliminar){
            VistaEliminarMobiliario  vemob = new VistaEliminarMobiliario();
            ControladorEliminarMobiliario cemob = new ControladorEliminarMobiliario(usuario, contrasena, vemob);
            vemob.setLocationRelativeTo(null);
            vemob.setResizable(false);
            vemob.setVisible(true);
            vmob.dispose();
            
        }
    }
    
    public void mostrarDatos(){
        //csql.conexion(getUsuario(), getContrasena());
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("idMobiliario");
        modelo.addColumn("idTienda");
        modelo.addColumn("nombreMobiliario");
        modelo.addColumn("Descripcion");
        
        
        vmob.tablaMobiliario.setModel(modelo);
        String sql = "SELECT * FROM mobiliario";
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
    
    
}
