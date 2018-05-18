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
    private String id_proveedor; 
    private String razon_s;
    private String direccion;
    private String telefono;
    private String email;
    
    public Proveedor(String id_proveedor,String razon_s,String direccion,String telefono,String email){
        this.id_proveedor = id_proveedor;
        this.razon_s = razon_s; 
        this.direccion = direccion;
        this.telefono = telefono;
        this.email = email;
    }
        public String getId_proveedor() {
        return id_proveedor;
    }

    public String getRazon_s() {
        return razon_s;
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
                        result.getString("id_prov"),
                        result.getString("razon_social"),
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
