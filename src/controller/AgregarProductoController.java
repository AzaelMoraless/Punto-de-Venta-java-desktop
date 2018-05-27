/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import conexion.Conexion;
import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.util.Callback;
import modelo.TextFieldFormatter;

/**
 * FXML Controller class
 *
 * @author Azael
 */
public class AgregarProductoController implements Initializable {

    @FXML
    private TextField txtExistencias;
    @FXML
    private TextField txtPrecioC;
    @FXML
    private TextField txtNombre;
    @FXML
    private JFXButton btnCancelar;
    @FXML
    private JFXButton btnGuardar;
    @FXML
    private TextField txtPrecioV;
    @FXML
    private TextField txtRFCprov;
    @FXML
    private HBox errorDescripcion;
    @FXML
    private HBox errorPrecioV;
    @FXML
    private HBox errorPrecioC;
    @FXML
    private HBox errorExistencias;
    @FXML
    private HBox errorRFCprov;
    
    Conexion cc = new Conexion();
    Connection con = cc.conexion();
    ErrorController msgErr = new ErrorController();
    View_successfulController msg_exitoso = new View_successfulController();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txtExistencias.lengthProperty().addListener(new ChangeListener<Number>(){
	@Override
	    public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if (newValue.intValue() > oldValue.intValue()) {
                    char ch = txtExistencias.getText().charAt(oldValue.intValue());
                    // Check if the new character is the number or other's
                    if (!(ch >= '0' && ch <= '9' )) {
                        // if it's not number then just setText to previous one
                        txtExistencias.setText(txtExistencias.getText().substring(0,txtExistencias.getText().length()-1)); 
                    }
		}
	    }
        });
    }    

    @FXML
    private void cancelar(MouseEvent event) {
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }

    @FXML
    private void aceptar(MouseEvent event){
        
        
        if(validarInput()==false){
            msgErr.msgError("Campo(s) incorrectos");
            return;
        } 
        String descripcion,rfc_prov;
        Double precio_c,precio_v;
        int existencias;
        descripcion = txtNombre.getText().trim(); precio_c = Double.parseDouble(txtPrecioC.getText().trim()); precio_v = Double.parseDouble(txtPrecioV.getText().trim());
        existencias = Integer.parseInt(txtExistencias.getText().trim());
        rfc_prov = txtRFCprov.getText().trim();
        try{ 
            Statement consulta=(Statement)con.createStatement();
            consulta.executeUpdate("insert into aguilas.producto(descripcion,id_prov,precio_c,precio_v,existencias) values('"+descripcion+"','"+rfc_prov+"','"+precio_c+"','"+precio_v+"','"+existencias+"')");
            msg_exitoso.msgExitoso("Producto agregado");
            limpiarCampos();
        }catch(SQLException e){
           msgErr.msgError(e.getMessage());
        }    
        
    }
    @FXML
    private void validaRFC(KeyEvent event) {
        modelo.Delimitador.limitTextField(txtRFCprov, 13);
        Pattern pattern =Pattern.compile("^([A-ZÑ\\x26]{3,4}([0-9]{2})(0[1-9]|1[0-2])(0[1-9]|1[0-9]|2[0-9]|3[0-1])[A-Z|\\d]{3})$");
        Matcher mather = pattern.matcher(txtRFCprov.getText().trim());  
        if (mather.find() == true) 
            errorRFCprov.setVisible(false);
        else 
           errorRFCprov.setVisible(true); 
    }
    
    @FXML
    private void validaDescrip(KeyEvent event){
        Pattern pattern =Pattern.compile("[#a-zA-ZñÑáéíóúÁÉÍÓÚ0-9 ]{3,}"); 
        Matcher mather = pattern.matcher(txtNombre.getText().trim());  
        if (mather.find() == true) 
            errorDescripcion.setVisible(false);
        else 
           errorDescripcion.setVisible(true); 
    }
    @FXML
    private void validaPrecioV(KeyEvent event){
        modelo.Delimitador.limitTextField(txtPrecioV, 15);
        char caracter = event.getCharacter().charAt(0);   
        if (((caracter < '0') || (caracter > '9')) 
        && (caracter != '.' || txtPrecioV.getText().contains(".")) ) {
            event.consume();
        }
    }
    
    
    @FXML
    private void validaPrecioC(KeyEvent event){
        modelo.Delimitador.limitTextField(txtPrecioC, 15);
        char caracter = event.getCharacter().charAt(0);   
        if (((caracter < '0') || (caracter > '9')) 
        && (caracter != '.' || txtPrecioC.getText().contains(".")) ) {
            event.consume();
        }
    }
    
    public boolean validarInput(){
        if(errorDescripcion.isVisible() || errorPrecioV.isVisible() || errorExistencias.isVisible() || errorPrecioC.isVisible() || errorRFCprov.isVisible()
           || txtNombre.getText().equals("") || txtPrecioC.getText().equals("") || txtPrecioV.getText().equals("") ||
           txtExistencias.getText().equals("") || txtRFCprov.getText().equals(""))
            return false;
        else 
            return true;
    }
    
    public void limpiarCampos(){
       txtNombre.setText("");
       txtPrecioV.setText("");
       txtPrecioC.setText("");
       txtExistencias.setText("");
       txtRFCprov.setText("");
    }
}   
