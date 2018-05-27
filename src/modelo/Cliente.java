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
public class Cliente {
    private final StringProperty propertyRFC;
    private final StringProperty propertyNombre;
    private final StringProperty propertyTelefono;
    private final StringProperty propertyEmail;
    
    public Cliente(String rfc,String nombre,String telefono,String email){
        this.propertyRFC = new SimpleStringProperty(rfc);
        this.propertyNombre = new SimpleStringProperty(nombre);
        this.propertyTelefono = new SimpleStringProperty(telefono);
        this.propertyEmail = new SimpleStringProperty(email);
    }

    public StringProperty getPropertyRFC() {
        return propertyRFC;
    }
    
    public String getRFC(){
        return propertyRFC.get();
    }
    public StringProperty getPropertyNombre() {
        return propertyNombre;
    }
    
    public String getNombre(){
        return propertyNombre.get();
    }
    public StringProperty getPropertyTelefono() {
        return propertyTelefono;
    }
    public String getTelefono(){
        return propertyTelefono.get();
    }
    public StringProperty getPropertyEmail() {
        return propertyEmail;
    }
    public String getEmail(){
        return propertyEmail.get();
    }
    
    public static void llenarTablaClientes(Connection connection,ObservableList<Cliente> lista){
        try {
            Statement stmt = connection.createStatement();
            ResultSet result  = stmt.executeQuery("SELECT * FROM aguilas.cliente");
            while(result.next()){
                lista.add(
                    new Cliente(
                        result.getString("RFC_cliente"),
                        result.getString("nombre"),
                        result.getString("telefono"),
                        result.getString("email")
                    )
                );
            }         
        }catch(SQLException ex) {
            Logger.getLogger(Proveedor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
