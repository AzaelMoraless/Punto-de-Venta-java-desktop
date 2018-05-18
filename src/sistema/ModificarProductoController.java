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
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author Azael
 */
public class ModificarProductoController implements Initializable {

    @FXML
    private TextField txtBuscar;
    @FXML
    private JFXButton btnBuscar;
    @FXML
    private TextField txtPrecioC;
    @FXML
    private TextField txtDescripcion;
    @FXML
    private TextField txtPrecioV;
    @FXML
    private TextField txtNombre;
    @FXML
    private JFXButton btnGuardar;
    @FXML
    private JFXButton btnCerrar;
    @FXML
    private TextField txtExistencias;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void detectaTecla(KeyEvent event) {
    }

    @FXML
    private void buscar(MouseEvent event) {
    }

    @FXML
    private void guardar(MouseEvent event) {
    }

    @FXML
    private void cerrar(MouseEvent event) {
         ((Node)  (event.getSource())).getScene().getWindow().hide();
    }
    
}
