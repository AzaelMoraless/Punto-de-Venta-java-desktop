/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXButton;
import conexion.Conexion;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import modelo.Cliente;
import modelo.TextFieldFormatter;

/**
 * FXML Controller class
 *
 * @author Azael
 */
public class AgregarClienteController implements Initializable {

    @FXML
    private TextField txtRFC;
    @FXML
    private TextField txtNombre;
    @FXML
    private HBox errorRFC;
    @FXML
    private TextField txtTelefono;
    @FXML
    private HBox errorName;
    @FXML
    private HBox errorTel;
    @FXML
    private TextField txtEmail;
    @FXML
    private ComboBox<String> comboCorreos;
    @FXML
    private HBox errorEmail;
    @FXML
    private JFXButton btnCancelar;
    @FXML
    private JFXButton btnGuardar;
    ObservableList<String> listcorreos = FXCollections.observableArrayList("@hotmail.com","@gmail.com","@outlook.com","@yahoo.com");
    static ObservableList<Cliente> listaCliente;
    
    Conexion cc = new Conexion();
    Connection con = cc.conexion();
    ErrorController msgErr = new ErrorController();
    View_successfulController msg_exitoso = new View_successfulController();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       comboCorreos.setValue("@hotmail.com");
       comboCorreos.setItems(listcorreos);
       comboCorreos.setStyle("-fx-font: 14px \"Roboto\";");
    }    
    
    @FXML
    private void aceptar(MouseEvent event) {
        String rfc,nombre,telefono,email;
        rfc = txtRFC.getText().trim().toUpperCase(); nombre = txtNombre.getText().trim(); 
        telefono = txtTelefono.getText().trim();
        email = txtEmail.getText().trim() + comboCorreos.getSelectionModel().getSelectedItem();
        if(validarEntradas()==false){
            msgErr.msgError("Campo(s) incorrectos");
            return;
        } 
        try{ 
            Statement consulta=(Statement)con.createStatement();
            consulta.executeUpdate("insert into aguilas.cliente (RFC_cliente,nombre,telefono,email)"
                    + "values('"+rfc+"','"+nombre+"','"+telefono+"','"+email+"')");
            
            
            Cliente p = new Cliente(rfc,nombre,telefono,email);
            listaCliente.add(p);
            
            msg_exitoso.msgExitoso("Cliente agregado");
            ((Node)  (event.getSource())).getScene().getWindow().hide();
        }catch(SQLException e){
           msgErr.msgError(e.getMessage());
        }    
    }
     @FXML
    private void cancelar(MouseEvent event) {
         ((Node)  (event.getSource())).getScene().getWindow().hide();
    }

     @FXML
    private void validaRFC(KeyEvent event) {
        modelo.Delimitador.limitTextField(txtRFC, 13);
        Pattern pattern =Pattern.compile("^([A-ZÑ\\x26]{3,4}([0-9]{2})(0[1-9]|1[0-2])(0[1-9]|1[0-9]|2[0-9]|3[0-1])[A-Z|\\d]{3})$");
        Matcher mather = pattern.matcher(txtRFC.getText().trim().toUpperCase()); 
        if (mather.find() == true) 
            errorRFC.setVisible(false);
        else 
           errorRFC.setVisible(true); 
    }

    @FXML
    private void validaNom(KeyEvent event) {
        Pattern pattern =Pattern.compile("[a-zA-ZñÑáéíóúÁÉÍÓÚ0-9 ]{3,}"); 
        Matcher mather = pattern.matcher(txtNombre.getText().trim());  
        if (mather.find() == true) 
            errorName.setVisible(false);
        else 
           errorName.setVisible(true); 
    }

    @FXML
    private void validaTel(KeyEvent event) {
        Pattern pattern =Pattern.compile("([(][0-9]{3}[)])-([0-9]{4})-([0-9]{3})"); 
        Matcher mather = pattern.matcher(txtTelefono.getText().trim());  
        if (mather.find() == true) 
            errorTel.setVisible(false);
        else 
            errorTel.setVisible(true); 
    }

    @FXML
    private void tfTelefonoKeyRelased(KeyEvent event) {
        TextFieldFormatter tff= new TextFieldFormatter();
        tff.setMask("(###)-####-###");
        tff.setCaracteresValidos("0123456789");
        tff.setTf(txtTelefono);
        tff.formatter();
    }

    @FXML
    private void validaEmail(KeyEvent event) {
        Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*$"); 
        Matcher mather = pattern.matcher(txtEmail.getText().trim());
        
        if (mather.find() == true) 
            errorEmail.setVisible(false);
        else 
           errorEmail.setVisible(true); 
    }
    
    public boolean validarEntradas(){
        if(errorName.isVisible() || errorRFC.isVisible() ||  errorEmail.isVisible() || errorTel.isVisible() ||
           txtRFC.getText().equals("") || txtTelefono.getText().equals("") || txtEmail.getText().equals("") ||
           txtNombre.getText().equals(""))
            return false;
        else 
            return true;
    }
    
}
