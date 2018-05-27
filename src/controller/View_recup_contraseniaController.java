/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import conexion.Conexion;
import Recuperacion.Controlador;
import Recuperacion.Correo;
import Recuperacion.GeneratePassword;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javax.swing.JOptionPane;

public class View_recup_contraseniaController implements Initializable {

    @FXML
    private TextField txtEmail;
    private String nueva_contrasena;
    Conexion cc = new Conexion();
    Connection con = cc.conexion();
    Correo c = new Correo();
    ErrorController error = new ErrorController();
    @FXML
    private Button btnRecuperar;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    
    
    @FXML 
    public void recuperar_password(Event evento ) throws NoSuchAlgorithmException{
        String email_destino = txtEmail.getText().toLowerCase().trim();
        if(Validaciones.validarCorreo(email_destino)){
            Statement stmt;
            ResultSet rs;
            try{ 
                stmt = con.createStatement();
                rs = stmt.executeQuery("SELECT * FROM aguilas.empleado WHERE (email_e  = '"+ email_destino +"')");   
                if(rs!=null){
                    if(rs.next()){ 
                        if(enviarCorreo(email_destino)){
                            String sSQL = "UPDATE aguilas.empleado SET " + "contrasena = ?" + "WHERE email_e=?"; // consulta sql para actualizar
                            PreparedStatement pstm = con.prepareStatement(sSQL) ;
                            pstm.setString(1, nueva_contrasena);
                            pstm.setString(2, email_destino);
                            pstm.executeUpdate();   
                            
                            JOptionPane.showMessageDialog(null, "contraseña enviada");
                        }else{
                          error.msgError("No se pudo enviar el correo");
                        }        
                    }else{      
                        error.msgError("El correo no se encuentra registrado");
                        txtEmail.setText("");
                    }
                }
            }catch(SQLException e){
              error.msgError(e.getMessage());
            }      
        }else{
            txtEmail.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
            txtEmail.setPromptText("No es un correo electrónico");
            txtEmail.setText("");
        }
        
    }
    
    public boolean enviarCorreo(String e_destinatario) throws NoSuchAlgorithmException{
        c.setContrasenia("lvwjyttiqguxpgfn");
        c.setUsuarioCorreo("azael.moraless029@gmail.com");
        c.setAsunto("Recuperacion de contraseña");
        c.setDestinatario(e_destinatario);
        nueva_contrasena = GeneratePassword.generarContrasenia();
        c.setMensaje("contraseña nueva: " + nueva_contrasena); 
        Controlador controller = new Controlador();
        if(controller.enviarCorreo(c))
            return true;
        else
            return false;
    }
}

