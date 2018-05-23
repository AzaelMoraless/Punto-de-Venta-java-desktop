/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
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
    private void aceptar(MouseEvent event) {
        
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
        
    }
}
