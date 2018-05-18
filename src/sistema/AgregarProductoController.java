/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistema;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author Azael
 */
public class AgregarProductoController implements Initializable {

    @FXML
    private TextField txtExistencias;
    @FXML
    private TextField txtDescripcion;
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void cancelar(MouseEvent event) {
        ((Node)  (event.getSource())).getScene().getWindow().hide();
    }

    @FXML
    private void aceptar(MouseEvent event) {
        
    }
    
}
