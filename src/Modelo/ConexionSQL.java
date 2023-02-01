
package Modelo;


import javax.swing.JOptionPane;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import Vistas.*;
import Controlador.*;

/**
 *
 * @author HP
 */

public class ConexionSQL {
    Boolean cerrarVentana = false;
    /*
    Connection conectar = null; //Desconozco
    String bd="eljarocho"; //NOMBRE DE LA BASE DE DATOS
    String url="jdbc:mysql://localhost:3306/"; //URL DE LA BASE
    //static String user="root"; //USUARIO
    //static String password="1234"; //CONTRASEÑA
    String driver = "com.mysql.cj.jdbc.Driver";  //EL DRIVER     
    */
    Connection cx; //CONEXION
    
    
    public Connection conexion(String user, String password){
        
        Connection conectar = null; //Desconozco
        String bd="eljarocho"; //NOMBRE DE LA BASE DE DATOS
        String url="jdbc:mysql://localhost:3306/"; //URL DE LA BASE
        //static String user="root"; //USUARIO
        //static String password="1234"; //CONTRASEÑA
        String driver = "com.mysql.cj.jdbc.Driver";  //EL DRIVER     
        //Connection cx;
        
        try {
            Class.forName(driver); //El driver es enviado para la clase con ese nombre
            cx = DriverManager.getConnection(url+bd, user, password); //cx es la instancia de la conexion (?) 
            //SE PASA CON LA URL DE LA BASE MAS SU NOMBRE, USUARIO Y CONTRASEÑA
            System.out.println("Se conecto el usuario " + user); //MENSAJE DE APOYO
            
            
            //Nueva vista de menu principal
            
            
            cerrarVentana = true;
            
        } catch (ClassNotFoundException |SQLException ex) {
            System.out.println("No se conecto error: " + ex); //EXCEPCION 
            Logger.getLogger(ConexionSQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cx; //REGRESA LA CONEXIÓN
        
    }
    /*public void desconectar(){
        try{
            cx.close(); //DESCONECTA LA BASE DE DATOS
        } catch(Exception ex){
            System.out.println(ex);
            Logger.getLogger(ConexionSQL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }*/
    //METODO HECHO PARA ADMINISTRAR LAS INCERCIONES, AUN NO SABEMOS QUE HACER CON EL
    public void insertInto(String v1, String v2){
        String sql = "INSERT INTO prueba(id, nombre) VALUES(?,?)";
        
        try{
            PreparedStatement preparedStmt = cx.prepareStatement(sql); //PREPARA EL CONJUNTO DE VALORES A ENVIAR
            //BASANDOSE EN LA VARIABLE SQL
            preparedStmt.setString(1, v1); //SE ENVÍAN LOS VALORES CON INDICE PARA LOS SIGNOS EN LA LINEA 50 1 ES EL PRIMERO
            preparedStmt.setString(2, v2); //Y 2 ES EL SEGUNDO SIGNO (V1 Y V2 SON LAS VARIABLES QUE RECIBIRAN LOS VALORES)
            preparedStmt.execute(); //SE ECHA A ANDAR LA INSERCIÓN
            System.out.println("hecho");
        } catch(Exception eseption){
            System.out.println("No we no");
        }
    }
    /*public String[] selectFromEmpleados(String sql){
        String datos[] = new String[9];
        try{
            Statement st = cx.createStatement();
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
            }
        }
        catch(SQLException ex){
            
        }
        return datos;
    }*/
    public Boolean getCerrarVentana(){
        return cerrarVentana;
    }
    public Connection getCx(){
        return cx;
    }
    //NO HACE NADA
    public static void main(String args[]){
        //System.out.println("IS HERE?");
        //ConexionSQL con = new ConexionSQL();
        //con.conexion();
        
        
    }
    
    
}
