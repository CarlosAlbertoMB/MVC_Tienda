/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package eljarocho_def;

import Vistas.LoginScreen;
import Modelo.ConexionSQL;
import Controlador.*;
/**
 *
 * @author HP
 */
public class ElJarocho {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        //ConexionSQL conn = new ConexionSQL(); //OBJETO PARA ESTABLECER UNA CONEXION CON LA BASE DE DATOS
        //conn.conexion(); //LLAMADA AL METODO QUE LA ESTABLECE
        
        LoginScreen log = new LoginScreen(); //NUEVA VENTANA DE LOGIN
        ControladorLogin clog = new ControladorLogin(log);
        log.setLocationRelativeTo(null);
        log.setVisible(true); //VISIBLE = TRUE
    }
    
}
