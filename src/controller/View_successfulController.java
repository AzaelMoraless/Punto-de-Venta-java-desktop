/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Azael
 */
public class View_successfulController implements Initializable {

    @FXML
    private Button btnCerrar;
    @FXML
    private Label labelMsg;
    static String mensaje;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       labelMsg.setText(mensaje);
    }    

    @FXML
    private void cerrar(ActionEvent event) {
        ((Node)  (event.getSource())).getScene().getWindow().hide();
    }
    public  void msgExitoso(String msg){
        try{ 
            View_successfulController.mensaje = msg;
            Parent root = FXMLLoader.load(getClass().getResource("/view/view_successful.fxml"));
            Stage stage= new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Exitoso");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            stage.show();
        }catch(IOException e){
            System.err.println("Error: carga" + e.getMessage());
        }
    }

    @FXML
    private void cerrarKey(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER) || event.getCode().equals(KeyCode.ESCAPE)){
            ((Node)  (event.getSource())).getScene().getWindow().hide();
        }
    }
}
