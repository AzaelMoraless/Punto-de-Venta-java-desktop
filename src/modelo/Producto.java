/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

/**
 *
 * @author Azael
 */
public class Producto {
    private final StringProperty codigoProducto;
    private final StringProperty descripcion;
    private final StringProperty precio_c;
    private final StringProperty precio_v;
    private final StringProperty existencias;
    private final StringProperty subtotal;
    
    public Producto(String codigoProducto,String descripcion,String precio_c,String precio_v,
                    String existencias,String subtotal){
        
        this.codigoProducto = new SimpleStringProperty(codigoProducto);
        this.descripcion = new SimpleStringProperty(descripcion);
        this.precio_c = new SimpleStringProperty(precio_c);
        this.precio_v = new SimpleStringProperty(precio_v);
        this.existencias = new SimpleStringProperty(existencias);
        this.subtotal = new SimpleStringProperty(subtotal);
    }
    
    public String getCodigo() {
        return codigoProducto.get();
    }
    public StringProperty codigo() {
        return codigoProducto;
    }
    
    public StringProperty subtotal(){
        return subtotal;
    }
    public String getSubtotal(){
        return subtotal.get();
    }
    public String getDescripcion() {
        return descripcion.get();
    }
    
    public StringProperty descripcion() {
        return descripcion;
    }
    
    public String getPrecio_venta() {
        return precio_v.get();
    }
    
    public StringProperty precio_venta() {
        return precio_v;
    }
        
    public String getPrecio_compra() {
        return precio_c.get();
    }

    public StringProperty precio_compra() {
        return precio_c;
    }
    public String getExistencias() {
        return existencias.get();
    }
    
    public StringProperty existencias() {
        return existencias;
    }
     public static void llenarTablaProductos(Connection connection,ObservableList<Producto> lista){
        try {
            Statement stmt = connection.createStatement();
            ResultSet result  = stmt.executeQuery("SELECT * FROM aguilas.producto");
            while(result.next()){
                lista.add(
                    new Producto(
                        result.getString("codigo_prod"),
                        result.getString("descripcion"),
                        result.getString("precio_c"),
                        result.getString("precio_v"),
                        result.getString("existencias"),
                        ""
                    )
                );
            }  
        }catch(SQLException ex) {
            Logger.getLogger(Producto.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
}
