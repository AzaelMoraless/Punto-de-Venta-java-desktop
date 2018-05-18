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
    String id_producto;
    String  nombre;
    String descripcion;
    String id_proveedor;
    String precio_venta;
    String precio_compra;
    String existencias;
    
    public Producto(String id_producto,String nombre,String descripcion,String id_proveedor,String precio_venta,String precio_compra,
                    String existencias){
        this.id_producto = id_producto;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.id_proveedor = id_proveedor;
        this.precio_venta = precio_venta;
        this.precio_compra = precio_compra;
        this.existencias = existencias;
    }

    public String getId_proveedor() {
        return id_proveedor;
    }

    public String getId_producto() {
        return id_producto;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getPrecio_venta() {
        return precio_venta;
    }

    public String getPrecio_compra() {
        return precio_compra;
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
                        result.getString("nombre"),
                        result.getString("descripcion"),
                        result.getString("id_prov"),
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
