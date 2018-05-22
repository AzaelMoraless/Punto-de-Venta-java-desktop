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

public class Proveedor {
    private String rfc; 
    private String nombre;
    private String direccion;
    private String telefono;
    private String email;
    
    public Proveedor(String rfc_p,String nombre_p,String direccion,String telefono,String email){
        this.rfc = rfc_p;
        this.nombre = nombre_p; 
        this.direccion = direccion;
        this.telefono = telefono;
        this.email = email;
    }
    
    public String getRfc() {
        return rfc;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getEmail() {
        return email;
    }
  
    public static void llenarTablaProveedores(Connection connection,ObservableList<Proveedor> lista){
        try {
            Statement stmt = connection.createStatement();
            ResultSet result  = stmt.executeQuery("SELECT * FROM aguilas.proveedor");
            while(result.next()){
                lista.add(
                    new Proveedor(
                        result.getString("rfc"),
                        result.getString("nombre"),
                        result.getString("direccion"),
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
