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
import javafx.collections.ObservableList;

/**
 *
 * @author Azael
 */
public class Producto {
    String id_p;
    String descripcion;
    String precio_c;
    String precio_v;
    String existencias;
    
    public Producto(String id_producto,String descripcion,String precio_c,String precio_v,
                    String existencias){
        this.id_p = id_producto;
        this.descripcion = descripcion;
        this.precio_c = precio_c;
        this.precio_v = precio_v;
        this.existencias = existencias;
    }
    
    public String getId_producto() {
        return id_p;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getPrecio_venta() {
        return precio_v;
    }

    public String getPrecio_compra() {
        return precio_c;
    }

    public String getExistencias() {
        return existencias;
    }
    
     public static void llenarTablaProductos(Connection connection,ObservableList<Producto> lista){
        try {
            Statement stmt = connection.createStatement();
            ResultSet result  = stmt.executeQuery("SELECT * FROM aguilas.producto");
            while(result.next()){
                lista.add(
                    new Producto(
                        result.getString("id_p"),
                        result.getString("descripcion"),
                        result.getString("precio_c"),
                        result.getString("precio_v"),
                        result.getString("existencias")
                    )
                );
            }         
        }catch(SQLException ex) {
            Logger.getLogger(Producto.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
