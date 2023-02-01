/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;
import Vistas.*;
import Modelo.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
/**
 *
 * @author HP
 */
public class ControladorMenu implements ActionListener{
    VistaMenuPrincipal vmp=new VistaMenuPrincipal();
    ConexionSQL csql = new ConexionSQL();
    String usuario;
    String contrasena;
    Boolean bandera = true;
    
    public ControladorMenu(String usuario, String contrasena, VistaMenuPrincipal vmp){
         System.out.println("Se abrio");
         this.usuario = usuario;
         this.contrasena = contrasena;
         this.vmp=vmp;
         this.vmp.btMenu1.addActionListener(this); //
         this.vmp.btMenu2.addActionListener(this); //DEBEN SER PUBLICAS LAS VARIABLES
         this.vmp.btMenu3.addActionListener(this);
         this.vmp.btMenu4.addActionListener(this);
         this.vmp.btMenu5.addActionListener(this);
         this.vmp.btMenu6.addActionListener(this);
         this.vmp.btSalir.addActionListener(this);
         this.vmp.btMenu7.addActionListener(this);
         
         if(bandera = false){
             vmp.dispose();
         }
    }

    
    @Override
    public void actionPerformed(ActionEvent evt) {
        if(evt.getSource() == vmp.btMenu1){
            
            if(usuario.equals("GerenteTienda") || usuario.equals("pruebas")){
                vmp.dispose();
                System.out.println("PRESIONASTE EL BT1");
                VistaEmpleados vemp = new VistaEmpleados();
                ControladorEmpleados cemp = new ControladorEmpleados(usuario, contrasena, vemp);
                vemp.setTitle("Registro de empleados");
                vemp.setLocationRelativeTo(null);
                vemp.setVisible(true);
            }
            else{
                JOptionPane.showMessageDialog(null,"Acceso no autorizado");
            }
            
            
            
            
        }
        else if(evt.getSource() == vmp.btMenu2){
            System.out.println("PRESIONASTE EL BT2");
            VistaProductos vp = new VistaProductos();
            ControladorProductos cp = new ControladorProductos(usuario, contrasena, vp);
            vp.setLocationRelativeTo(null);
            vp.setVisible(true);
            vmp.dispose();
            
        }
        else if(evt.getSource() == vmp.btMenu3){
            System.out.println("PRESIONASTE EL BT3");
            VistaProveedores vip = new VistaProveedores();
            ControladorProveedores cip = new ControladorProveedores(usuario, contrasena, vip);
            vip.setLocationRelativeTo(null);
            vip.setResizable(false);
            vip.setVisible(true);
            vmp.dispose();
            
        }
        else if(evt.getSource() == vmp.btMenu4){
            System.out.println("PRESIONASTE EL BT4");
            VistaMobiliario vmob = new VistaMobiliario();
            ControladorMobiliario cmob = new ControladorMobiliario(usuario, contrasena, vmob);
            vmob.setLocationRelativeTo(null);
            vmob.setResizable(false);
            vmob.setVisible(true);
            vmp.dispose();
        }
        else if(evt.getSource() == vmp.btMenu5){
            System.out.println("PRESIONASTE EL BT5");
            VistaVentaNueva vvn = new VistaVentaNueva();
            ControladorVentaNueva cvn = new ControladorVentaNueva(usuario, contrasena, vvn);
            vvn.setLocationRelativeTo(null);
            vvn.setResizable(false);
            vvn.setVisible(true);
            vvn.setDefaultCloseOperation(VistaVentaNueva.DO_NOTHING_ON_CLOSE);
            vmp.dispose();
        }
        else if(evt.getSource() == vmp.btMenu6){
            System.out.println("PRESIONASTE EL BT6");
            VistaRegistroVentas vrv = new VistaRegistroVentas();
            ControladorRegistroVentas crv = new ControladorRegistroVentas(usuario, contrasena, vrv);
            vrv.setLocationRelativeTo(null);
            vrv.setResizable(false);
            vrv.setVisible(true);
            vmp.dispose();
        }
        else if(evt.getSource() == vmp.btMenu7){
            System.out.println("PRESIONASTE EL BT7");
            VistaAuditoriaUsuarios vau = new VistaAuditoriaUsuarios();
            ControladorAuditoriaUsuarios cau = new ControladorAuditoriaUsuarios(usuario,contrasena,vau);
            vau.setLocationRelativeTo(null);
            vau.setResizable(false);
            vau.setExtendedState(JFrame.MAXIMIZED_BOTH);
            vau.setVisible(true);
            vmp.dispose();
        }
        else if(evt.getSource() == vmp.btSalir){
            System.out.println("PRESIONASTE EL BT_Salir");
            JOptionPane.showMessageDialog(null,"Cerraste sesion");
            LoginScreen log = new LoginScreen(); //NUEVA VENTANA DE LOGIN
            ControladorLogin clog = new ControladorLogin(log);
            log.setLocationRelativeTo(null);
            log.setVisible(true);
            vmp.dispose();
            //System.exit(0);
        }
        else{
            
        }
        
    }
    
}
