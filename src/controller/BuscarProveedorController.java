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
import java.util.ResourceBundle;
import java.util.function.Predicate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javax.swing.JOptionPane;
import modelo.Proveedor;

/**
 * FXML Controller class
 *
 * @author Azael
 */
public class BuscarProveedorController implements Initializable {
    private ObservableList<Proveedor> listaProveedor;
    @FXML private TableView<Proveedor> tblViewProveedores;

    @FXML private TableColumn<Proveedor,String> clmnRfcProv;  //clmnRfcProv
    @FXML private TableColumn<Proveedor,String> clmnNombreProv;  
    @FXML private TableColumn<Proveedor,String> clmnDirProv;  
    @FXML private TableColumn<Proveedor,String> clmnTelProv;  
    @FXML private TableColumn<Proveedor,String> clmnEmailProv;  
    @FXML private TextField txtBusquedaProv;
    @FXML private JFXButton btnCancelar;
    @FXML private JFXButton btnGuardar;
    private boolean flagLoadProv;
    private FilteredList<Proveedor> filteredData;
    

    
    Conexion cc = new Conexion();
    Connection con = cc.conexion();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarTablaProveedores();
    }    
    
    public void cargarTablaProveedores(){
        listaProveedor = FXCollections.observableArrayList();
        Proveedor.llenarTablaProveedores(con, listaProveedor);
        tblViewProveedores.setItems(listaProveedor); 
        clmnRfcProv.setCellValueFactory(new PropertyValueFactory<Proveedor,String>("rfc"));
        clmnNombreProv.setCellValueFactory(new PropertyValueFactory<Proveedor,String>("nombre"));  
        clmnDirProv.setCellValueFactory(new PropertyValueFactory<Proveedor,String>("direccion"));
        clmnTelProv.setCellValueFactory(new PropertyValueFactory<Proveedor,String>("telefono"));
        clmnEmailProv.setCellValueFactory(new PropertyValueFactory<Proveedor,String>("email"));
     
        filteredData =new FilteredList<>(listaProveedor,e->true);
        flagLoadProv = true;
    
    }
    @FXML
    public void filtrarProveedor(){
        if(flagLoadProv){
        txtBusquedaProv.textProperty().addListener((observableValue,oldValue,newValue)->{
        filteredData.setPredicate((Predicate<? super Proveedor>) prove->{
            if(newValue== null || newValue.isEmpty()){
                return true;
            }
            String lowerCaseFilter =newValue.toLowerCase();
            if(prove.getRfc().contains(newValue.toUpperCase()))
                return true;
            if(prove.getNombre().toLowerCase().contains(lowerCaseFilter))
                return true;
            if(prove.getDireccion().toLowerCase().contains(lowerCaseFilter))
                return true;
            return false;
             });
            });
            SortedList<Proveedor>  sortedData = new SortedList<>(filteredData);
            sortedData.comparatorProperty().bind(tblViewProveedores.comparatorProperty());
            tblViewProveedores.setItems(sortedData);
        }else{
            return;
        } 
    }

    @FXML
    private void cerrar(MouseEvent event){
        ((Node)  (event.getSource())).getScene().getWindow().hide();
    }

    @FXML
    private void aceptar(MouseEvent event){
        ObservableList<Proveedor> prov = tblViewProveedores.getSelectionModel().getSelectedItems();
        Proveedor proveedor = tblViewProveedores.getSelectionModel().getSelectedItem();
        try{
            if(proveedor == null){
                    JOptionPane.showMessageDialog(null, "Selecione un proveedor");
                    return;
            }
            AgregarCompraController.campoRFC.setText(proveedor.getRfc());
           ((Node)(event.getSource())).getScene().getWindow().hide();
        }catch(Exception e){System.err.println("Error " + e.getLocalizedMessage() );}
        
    }
    
}
