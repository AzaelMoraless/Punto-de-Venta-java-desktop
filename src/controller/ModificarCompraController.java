/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

/**
 * FXML Controller class
 *
 * @author Azael
 */
public class ModificarCompraController implements Initializable {

    @FXML
    private TextField txtFolioCompra;
    @FXML
    private TextField txtRFCProv;
    @FXML
    private DatePicker fechaCompra;
    @FXML
    private ImageView buscarProveedor;
    @FXML
    private TextField txtcodigoProducto;
    @FXML
    private TextField txtDescripcionProducto;
    @FXML
    private TextField txtPrecioC;
    @FXML
    private TextField txtPrecioV;
    @FXML
    private TextField txtExistencias;
    @FXML
    private HBox btnEditar;
    @FXML
    private HBox btnEliminar;
    @FXML
    private HBox btnAgregar;
    @FXML
    private TableView<?> tablaProductos;
    @FXML
    private TableColumn<?, ?> clmnCodigo;
    @FXML
    private TableColumn<?, ?> clmnDescripcion;
    @FXML
    private TableColumn<?, ?> clmnExistencias;
    @FXML
    private TableColumn<?, ?> clmnPrecioC;
    @FXML
    private TableColumn<?, ?> clmnTotal;
    @FXML
    private TextField txtBuscarProducto;
    @FXML
    private JFXButton btnCancelar;
    @FXML
    private JFXButton btnGuardar;
    @FXML
    private Label lblTotalaPagar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void validaFolio(KeyEvent event) {
    }

    @FXML
    private void buscarProveedor(MouseEvent event) {
    }

    @FXML
    private void validaCodigo(KeyEvent event) {
    }

    @FXML
    private void validaDescripcion(KeyEvent event) {
    }

    @FXML
    private void validaPrecioC(KeyEvent event) {
    }

    @FXML
    private void validaPrecioV(KeyEvent event) {
    }

    @FXML
    private void editarProducto(MouseEvent event) {
    }

    @FXML
    private void eliminarProducto(MouseEvent event) {
    }

    @FXML
    private void agregarProducto(MouseEvent event) {
    }

    @FXML
    private void filtrarProducto(KeyEvent event) {
    }

    @FXML
    private void cerrar(MouseEvent event) {
    }

    @FXML
    private void aceptar(MouseEvent event) {
    }
    
}
