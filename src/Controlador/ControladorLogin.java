/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;
import Modelo.*;
import Vistas.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;
/**
 *
 * @author HP
 */
public class ControladorLogin extends DatosInicioSesion implements ActionListener {
    LoginScreen ls = new LoginScreen();
    ConexionSQL csql = new ConexionSQL();

    public ControladorLogin(LoginScreen ls){
        this.ls=ls;
        this.ls.tfUsuario.addActionListener(this);
        this.ls.tfContra.addActionListener(this);
        this.ls.btnIngresar.addActionListener(this);
        
    }
    @Override
    public void actionPerformed(ActionEvent evt){
        
        if(evt.getSource() == ls.btnIngresar){
            
            setUsuario(String.valueOf(ls.tfUsuario.getText()));
            setContrasena(String.valueOf(ls.tfContra.getText()));
            csql.conexion(getUsuario(), getContrasena());
            if(csql.getCerrarVentana() == true){
                VistaMenuPrincipal vmp = new VistaMenuPrincipal();
                ControladorMenu cmp = new ControladorMenu(ls.tfUsuario.getText(), ls.tfContra.getText(), vmp);
                vmp.setLocationRelativeTo(null);
                vmp.setVisible(true);
                ls.dispose();
            }
        }
        else{
            System.out.println("No se puede bro");
        }
    }

}

