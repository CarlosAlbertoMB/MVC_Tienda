/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;
import Vistas.*;
import Modelo.*;
import java.sql.ResultSet;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableModel;
import eljarocho_def.*;
/**
 *
 * @author HP
 */
public class ControladorEmpleados extends DatosInicioSesion implements ActionListener{
    VistaEmpleados vemp = new VistaEmpleados();
    ConexionSQL csql = new ConexionSQL();
    String usuario;
    String contrasena;
    //ElJarocho jdj = new ElJarocho();
    
    
    public ControladorEmpleados(String usuario, String contrasena, VistaEmpleados vemp){
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.vemp=vemp;
        this.vemp.btEmp_01.addActionListener(this);
        this.vemp.btEmp_02.addActionListener(this);
        this.vemp.btEmp_03.addActionListener(this);
        this.vemp.btRegresar.addActionListener(this);
        mostrarDatos();
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        if(evt.getSource() == vemp.btEmp_01){
            VistaAgregarEmpleado vae = new VistaAgregarEmpleado();
            ControladorAgregarEmpleado cae = new ControladorAgregarEmpleado(usuario, contrasena, vae);
            vae.setLocationRelativeTo(null);
            vae.setVisible(true);
            vemp.dispose();
        }
        else if(evt.getSource() == vemp.btEmp_02){
            VistaModificarEmpleado vme = new VistaModificarEmpleado();
            ControladorModificarEmpleado cme = new ControladorModificarEmpleado(usuario, contrasena, vme);
            vme.setLocationRelativeTo(null);
            vme.setVisible(true);
            vemp.dispose();
        }
        else if(evt.getSource() == vemp.btEmp_03){
            VistaEliminarEmpleado vee = new VistaEliminarEmpleado();
            ControladorEliminarEmpleado cee = new ControladorEliminarEmpleado(usuario, contrasena, vee);
            vee.setLocationRelativeTo(null);
            vee.setVisible(true);
            vemp.dispose();
        }
        else if(evt.getSource() == vemp.btRegresar){
            VistaMenuPrincipal vmp = new VistaMenuPrincipal();
            ControladorMenu cmp = new ControladorMenu(usuario, contrasena, vmp);
            vmp.setLocationRelativeTo(null);
            vmp.setVisible(true);
            vemp.dispose();
        }
        else{
            
        }
    }
    public void mostrarDatos(){
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
        
        vemp.tablaEmpleados.setModel(modelo);
        String sql = "SELECT * FROM EMPLEADO";
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
}
