/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import modelo.TextFieldFormatter;

/**
 * FXML Controller class
 *
 * @author Azael
 */
public class AgregarEmpleadoController implements Initializable {

    @FXML
    private TextField txtTel;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtNick;
    @FXML
    private TextField txtContrasenia;
    @FXML
    private JFXButton btnCancelar;
    @FXML
    private JFXButton btnGuardar;
    @FXML
    private HBox errorNombre;
    @FXML
    private HBox errorNick;
    @FXML
    private HBox errorContra;
    @FXML
    private HBox errorTel;
    @FXML
    private HBox errorEmail;

    Conexion cc = new Conexion();
    Connection con = cc.conexion();
    ErrorController msgErr = new ErrorController();
    View_successfulController msg_exitoso = new View_successfulController();
    @FXML
    private RadioButton rbutton1;
    @FXML
    private RadioButton rbutton2;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       ToggleGroup toggleGroup = new ToggleGroup();
       rbutton1.setToggleGroup(toggleGroup);
       rbutton2.setToggleGroup(toggleGroup);
       
    }    

     @FXML
    private void cancelar(MouseEvent event) {
       ((Node)  (event.getSource())).getScene().getWindow().hide();
    }

    @FXML
    private void aceptar(MouseEvent event){
        String nombre,telefono,nickname,contrasenia,email,puesto="";
        nombre = txtNombre.getText().trim(); telefono = txtTel.getText().trim(); nickname = txtNick.getText().trim();
        contrasenia = txtContrasenia.getText().trim(); email= txtEmail.getText().trim();
        
        if(rbutton1.isSelected())
            puesto = rbutton1.getText();
        else if(rbutton2.isSelected())
            puesto = rbutton2.getText();
        
        if(validarInput()==false || puesto.equals("")){
            msgErr.msgError("Campo(s) incorrectos");
            return;
        } 
        
        try{ 
            Statement consulta=(Statement)con.createStatement();
            consulta.executeUpdate("insert into aguilas.empleado (nombre,telefono,username,contrasena,email_e,puesto) values('"+nombre+"','"+telefono+"','"+nickname+"','"+contrasenia+"','"+email+"','"+puesto+"')");
            msg_exitoso.msgExitoso("Proveedor agregado");
            limpiarCampos();
        }catch(SQLException e){
           msgErr.msgError(e.getMessage());
        }    
    }



    @FXML
    private void validaTel(KeyEvent event) {
        Pattern pattern =Pattern.compile("([(][0-9]{3}[)])-([0-9]{4})-([0-9]{3})"); 
        Matcher mather = pattern.matcher(txtTel.getText().trim());  
        if (mather.find() == true) 
            errorTel.setVisible(false);
        else 
            errorTel.setVisible(true); 
    }

    @FXML
    private void validaEmail(KeyEvent event) {
        Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher mather = pattern.matcher(txtEmail.getText().trim()); 
        if (mather.find() == true) 
            errorEmail.setVisible(false);
        else 
           errorEmail.setVisible(true); 
    }

    @FXML
    private void validaNom(KeyEvent event) {
         Pattern pattern =Pattern.compile("[a-zA-ZñÑáéíóúÁÉÍÓÚ0-9 ]{3,}"); 
        Matcher mather = pattern.matcher(txtNombre.getText().trim());  
        if (mather.find() == true) 
            errorNombre.setVisible(false);
        else 
           errorNombre.setVisible(true);
    }

    @FXML
    private void tfTelefonoKeyRelased(KeyEvent event) {
        TextFieldFormatter tff= new TextFieldFormatter();
        tff.setMask("(###)-####-###");
        tff.setCaracteresValidos("0123456789");
        tff.setTf(txtTel);
        tff.formatter();
    }

    @FXML
    private void validaNick(KeyEvent event) {
          Pattern pattern =Pattern.compile("[a-zA-ZñÑáéíóúÁÉÍÓÚ0-9 ]{3,}"); 
        Matcher mather = pattern.matcher(txtNick.getText().trim());  
        if (mather.find() == true) 
            errorNick.setVisible(false);
        else 
           errorNick.setVisible(true); 
    }

    @FXML
    private void validaContra(KeyEvent event) {
          Pattern pattern =Pattern.compile("[a-zA-ZñÑáéíóúÁÉÍÓÚ0-9 ]{3,}"); 
        Matcher mather = pattern.matcher(txtContrasenia.getText().trim());  
        if (mather.find() == true) 
           errorContra.setVisible(false);
        else 
           errorContra.setVisible(true); 
    }
    
     public boolean validarInput(){
        if(errorNombre.isVisible() || errorNick.isVisible() || errorContra.isVisible() || errorEmail.isVisible() || errorTel.isVisible() ||
           txtNombre.getText().equals("") || txtNick.getText().equals("") || txtContrasenia.getText().equals("") || txtEmail.getText().equals("") ||
                txtTel.getText().equals(""))
            return false;
        else 
            return true;
    }
    
    public void limpiarCampos(){
        txtNombre.setText("");
        txtNick.setText("");
        txtContrasenia.setText("");
        txtEmail.setText("");
        txtTel.setText("");
        rbutton1.setSelected(false);
        rbutton2.setSelected(false);
    }
}
