/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;
import Vistas.VistaProductos;
import Vistas.VistaAgregarProducto;
import Modelo.ConexionSQL;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.PreparedStatement;
import javax.swing.JOptionPane;
/**
 *
 * @author HP
 */
public class ControladorAgregarProducto implements ActionListener {
    String usuario;
    String contrasena;
    VistaAgregarProducto vap = new VistaAgregarProducto();
    ConexionSQL csql = new ConexionSQL();
    
    public ControladorAgregarProducto(String usuario, String contrasena, VistaAgregarProducto vap){
        this.vap = vap;
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.vap.btRegresar.addActionListener(this);
        this.vap.btGuardar.addActionListener(this);
        this.vap.tfIdProducto.addActionListener(this);
        this.vap.tfIdProveedor.addActionListener(this);
        this.vap.tfNombreProd.addActionListener(this);
        this.vap.tfMarcaProd.addActionListener(this);
        this.vap.btClearCompra.addActionListener(this);
        this.vap.btClearVenta.addActionListener(this);
        this.vap.btClearCantidad.addActionListener(this);
        this.vap.tfPrecioCompra.addKeyListener(new KeyAdapter(){
            public void keyPressed(KeyEvent ke){
                String value = vap.tfPrecioCompra.getText();
                int l = value.length();
                if(ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9'){
                    vap.tfPrecioCompra.setEditable(true);
                } else{
                    vap.tfPrecioCompra.setEditable(false);
                }
            }
        });
        this.vap.tfPrecioVenta.addKeyListener(new KeyAdapter(){
            public void keyPressed(KeyEvent ke){
                String value = vap.tfPrecioVenta.getText();
                int l = value.length();
                if(ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9'){
                    vap.tfPrecioVenta.setEditable(true);
                } else{
                    vap.tfPrecioVenta.setEditable(false);
                }
            }
        });
        this.vap.tfCantidad.addKeyListener(new KeyAdapter(){
            public void keyPressed(KeyEvent ke){
                String value = vap.tfCantidad.getText();
                int l = value.length();
                if(ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9'){
                    vap.tfCantidad.setEditable(true);
                } else{
                    vap.tfCantidad.setEditable(false);
                }
            }
        });
    }
    @Override
    public void actionPerformed(ActionEvent evt) {
        if(evt.getSource() == vap.btRegresar){
            VistaProductos vp = new VistaProductos();
            ControladorProductos cp = new ControladorProductos(usuario, contrasena, vp);
            vp.setLocationRelativeTo(null);
            vp.setVisible(true);
            vap.dispose();
        }
        else if(evt.getSource() == vap.btClearCompra){
            vap.tfPrecioCompra.setText("");
        }
        else if(evt.getSource() == vap.btClearVenta){
            vap.tfPrecioVenta.setText("");
        }
        else if(evt.getSource() == vap.btClearCantidad){
            vap.tfCantidad.setText("");
        }
        else if(evt.getSource() == vap.btGuardar){
            String idProducto = vap.tfIdProducto.getText();
            String idProveedor = vap.tfIdProveedor.getText();
            String nombreProducto = vap.tfNombreProd.getText();
            String marcaProducto = vap.tfMarcaProd.getText();
            String precioCompra = vap.tfPrecioCompra.getText();
            String precioVenta = vap.tfPrecioVenta.getText();
            String cantidad = vap.tfCantidad.getText();
            
            if(idProducto.equals("") || idProveedor.equals("") || nombreProducto.equals("") || marcaProducto.equals("") || precioCompra.equals("") || precioVenta.equals("") || cantidad.equals("")){
                JOptionPane.showMessageDialog(null,"Faltan datos por agregar");
                System.out.println("Faltan datos");
            }
            else{
                String sql = "INSERT INTO producto VALUES(?,?,?,?,?,?,?)";
                    try{
                        
                        csql.conexion(usuario, contrasena);
                        PreparedStatement ps = csql.getCx().prepareStatement(sql);
                        ps.setString(1, idProducto);
                        ps.setString(2, idProveedor);
                        ps.setString(3, nombreProducto);
                        ps.setString(4, marcaProducto);
                        ps.setString(5, precioCompra);
                        ps.setString(6, precioVenta);
                        ps.setString(7, cantidad);
                        ps.execute();
                        JOptionPane.showMessageDialog(null,"Ingresado el producto con id "+idProducto);
                    } catch(Exception ex){
                        JOptionPane.showMessageDialog(null,"Has ingresado una ID duplicada o un valor que excede los limites" + ex);
                    }
            }
        }
        else{
            
        }
    }
    
}
