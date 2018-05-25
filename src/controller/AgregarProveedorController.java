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
import java.util.Observable;
import java.util.Optional;
import modelo.TextFieldFormatter;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialogs;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.text.MaskFormatter;
import modelo.Delimitador;
import modelo.Empleado;
import modelo.Proveedor;

public class AgregarProveedorController implements Initializable {
    @FXML private TextField txtRazonS;
    @FXML private TextField txtDireccion;
    @FXML private TextField txtTelefono;
    @FXML private TextField txtEmail;
    @FXML private TextField txtRFC;
    @FXML private HBox errorRFC;
    @FXML private HBox errorName;
    @FXML private HBox errorDir;
    @FXML private HBox errorTel;
    @FXML private HBox errorEmail;
     ObservableList<String> listcorreos = FXCollections.observableArrayList("@hotmail.com","@gmail.com","@outlook.com","@yahoo.com");
    
    Conexion cc = new Conexion();
    Connection con = cc.conexion();
    ErrorController msgErr = new ErrorController();
    View_successfulController msg_exitoso = new View_successfulController();
    static ObservableList<Proveedor> listaProveedor;
    @FXML
    private AnchorPane agregarProvPanel;
    @FXML
    private JFXButton btnGuardar;
    @FXML
    private JFXButton btnCancelar;
    @FXML
    private ComboBox<String> comboCorreos;
    private String email;
   
    @Override
    public void initialize(URL url, ResourceBundle rb) { 
       comboCorreos.setValue("@hotmail.com");
       comboCorreos.setItems(listcorreos);
       comboCorreos.setStyle("-fx-font: 14px \"Roboto\";");
    }    
    
    @FXML 
    public void aceptar(MouseEvent event){  // agregar un nuevo proveedor
        String rfc,nombre,direccion,telefono,email;
        rfc = txtRFC.getText().trim().toUpperCase(); nombre = txtRazonS.getText().trim(); direccion = txtDireccion.getText().trim(); telefono = txtTelefono.getText().trim();
        email = txtEmail.getText().trim() + comboCorreos.getSelectionModel().getSelectedItem();
        if(validarInput()==false){
            msgErr.msgError("Campo(s) incorrectos");
            return;
        } 
        try{ 
            Statement consulta=(Statement)con.createStatement();
            consulta.executeUpdate("insert into aguilas.proveedor (rfc,nombre,direccion,telefono,email) values('"+rfc+"','"+nombre+"','"+direccion+"','"+telefono+"','"+email+"')");
            msg_exitoso.msgExitoso("Proveedor agregado");
            //SistemaController sis = new SistemaController();
            //sis.actualizarTablaProv();
             
            
            ((Node)  (event.getSource())).getScene().getWindow().hide();
        }catch(SQLException e){
           msgErr.msgError(e.getMessage());
        }    
    }
    
    @FXML
    public void cancelar(MouseEvent event){
         ((Node)  (event.getSource())).getScene().getWindow().hide();
    }
    
    @FXML
    public void validaEmail(KeyEvent event){
       // Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
         //               + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*$"); 
        Matcher mather = pattern.matcher(txtEmail.getText().trim());
        
        if (mather.find() == true) 
            errorEmail.setVisible(false);
        else 
           errorEmail.setVisible(true); 
    }
    
    
    @FXML
    private void validaNom(KeyEvent event) {
        Pattern pattern =Pattern.compile("[a-zA-ZñÑáéíóúÁÉÍÓÚ0-9 ]{3,}"); 
        Matcher mather = pattern.matcher(txtRazonS.getText().trim());  
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
    private void validaDir(KeyEvent event){
        Pattern pattern =Pattern.compile("[#a-zA-ZñÑáéíóúÁÉÍÓÚ0-9 ]{3,}"); 
        Matcher mather = pattern.matcher(txtDireccion.getText().trim());  
        if (mather.find() == true) 
            errorDir.setVisible(false);
        else 
           errorDir.setVisible(true); 
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
    private void tfTelefonoKeyRelased(){
        TextFieldFormatter tff= new TextFieldFormatter();
        tff.setMask("(###)-####-###");
        tff.setCaracteresValidos("0123456789");
        tff.setTf(txtTelefono);
        tff.formatter();
    }
    
    public boolean validarInput(){
        if(errorName.isVisible() || errorRFC.isVisible() || errorDir.isVisible() || errorEmail.isVisible() || errorTel.isVisible() ||
           txtRFC.getText().equals("") || txtTelefono.getText().equals("") || txtDireccion.getText().equals("") || txtEmail.getText().equals("") ||
           txtRazonS.getText().equals(""))
            return false;
        else 
            return true;
    }
    
    public void limpiarCampos(){
        txtRFC.setText("");
        txtTelefono.setText("");
        txtDireccion.setText("");
        txtEmail.setText("");
        txtRazonS.setText("");
    }

    @FXML
    private void otroEmail(MouseEvent event) {
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Email");
        dialog.setHeaderText("Email");
        dialog.setContentText("email:");
 
        Optional<String> result = dialog.showAndWait();
 
        result.ifPresent(name -> {
           if((email(name))==true){
                msgErr.msgError("No es un correo");
                
           }else{
                
                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("confirmacion");
                alert.setContentText("El email es: " + name + " ?");
                Optional<ButtonType> resul= alert.showAndWait();
                if (resul.get() == ButtonType.OK){
                    email = name;
                } else {
                    //System.err.println("false");
                } 
             
           } 
        });
    }
    
    
     public boolean email(String s){
       Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                       + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher mather = pattern.matcher(s);
        
        if (mather.find() == true) 
           return false;
        else 
            return true;
    }
     
     
  
    
}
