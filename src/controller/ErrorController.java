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
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Azael
 */
public class ErrorController implements Initializable{
    @FXML
    private Label lblMsg;
    static String mensajeErr;
    @FXML
    private Button btnCerrar;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lblMsg.setText(mensajeErr);
    }
    @FXML
    public void cerrar(Event event){
        ((Node)  (event.getSource())).getScene().getWindow().hide();
    }
    
    public  void msgError(String mensaje){
        try{ 
            ErrorController.mensajeErr = mensaje;
            URL url = Paths.get("C:\\Users\\Azael\\Documents\\Sistema\\src\\view\\errorView.fxml").toUri().toURL();
            Parent root = FXMLLoader.load(url);
            Stage stage= new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Error");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            stage.show();
        }catch(IOException e){
            System.err.println("Error: carga" + e.getMessage());
        }
    }
}
