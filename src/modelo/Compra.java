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
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

/**
 *
 * @author Azael
 */
public class Compra {
    
    private final StringProperty propertyFolio;
    private final StringProperty rfc_proveedor;
    private final StringProperty date;
    private final StringProperty recibio;
    private final StringProperty total;
    
    public Compra(String folio,String rfc_proveedor,String date,String recibio,String total){
        this.propertyFolio = new SimpleStringProperty(folio);
        this.rfc_proveedor = new SimpleStringProperty(rfc_proveedor);
        this.date = new SimpleStringProperty(date);
        this.recibio = new SimpleStringProperty(recibio);
        this.total = new SimpleStringProperty(total);
    }

    public StringProperty getPropertyFolio() {
        return propertyFolio;
    }
    public String getFolio(){
        return propertyFolio.get();
    }
    public StringProperty getRfc_proveedor() {
        return rfc_proveedor;
    }
    
    public String getRFCP(){
        return rfc_proveedor.get();
    }
    public StringProperty getDate() {
        return date;
    }
    
    public String getFecha(){
        return date.get();
    }

    public StringProperty getRecibio() {
        return recibio;
    }
    
    public String getRecibioC(){
        return recibio.get();
    }
    public StringProperty getTotal() {
        return total;
    }
    
    public String getTotalCompra(){
        return total.get();
    }
    
    public static void llenarTablaCompras(Connection connection,ObservableList<Compra> lista){
        try {
            Statement stmt = connection.createStatement();
            ResultSet result  = stmt.executeQuery("SELECT * FROM aguilas.compra");
            while(result.next()){
                lista.add(
                    new Compra(
                        result.getString("folio_c"),
                        result.getString("rfc_prov"),
                        result.getString("fecha"),
                        result.getString("recibio"),
                        result.getString("total_compra")
                    )
                );
            }  
        }catch(SQLException ex) {
            Logger.getLogger(Producto.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
