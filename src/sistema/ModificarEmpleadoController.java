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
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author Azael
 */
public class ModificarEmpleadoController implements Initializable {

    @FXML
    private TextField txtBuscar;
    @FXML
    private JFXButton btnBuscar;
    @FXML
    private JFXButton btnCerrar;
    @FXML
    private JFXButton btnGuardar;
    @FXML
    private TextField txtPuesto;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtNickname;
    @FXML
    private TextField txtTelefono;
    @FXML
    private TextField txtContrasenia;
    @FXML
    private TextField txtNombre;

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
    private void cerrar(MouseEvent event) {
    }

    @FXML
    private void guardar(MouseEvent event) {
    }
    
}
