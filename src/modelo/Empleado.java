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
public class Empleado {
    private String id_e;
    private  String nombre;
    private String  telefono; 
    private String username;
    private String contrasena;
    private String email_e;        
    private String puesto;
    
    public Empleado(String id_e, String nombre, String telefono, String username, String contrasena, String email_e,
                    String puesto) {
        this.id_e = id_e;
        this.nombre = nombre;
        this.telefono = telefono;
        this.username = username;
        this.contrasena = contrasena;
        this.email_e = email_e;
        this.puesto = puesto;
    }

    public String getPuesto() {
        return puesto;
    }

    public String getId_e() {
        return id_e;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getUsername() {
        return username;
    }

    public String getContrasena() {
        return contrasena;
    }

    public String getEmail_e() {
        return email_e;
    }
    
    public static void llenarTablaEmpleados(Connection connection,ObservableList<Empleado> lista){
        try {
            Statement stmt = connection.createStatement();
            ResultSet result  = stmt.executeQuery("SELECT * FROM aguilas.empleado");
            while(result.next()){
                lista.add(
                    new Empleado(
                        result.getString("id_e"),
                        result.getString("nombre"),
                        result.getString("telefono"),
                        result.getString("username"),
                        result.getString("contrasena"),
                        result.getString("email_e"),
                        result.getString("puesto")
                    )
                );
            }         
        }catch(SQLException ex) {
            Logger.getLogger(Proveedor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}

