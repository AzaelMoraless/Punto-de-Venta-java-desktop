/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistema;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Azael
 */
public class ConfirmationController implements Initializable {

    @FXML
    private Label lblPregunta;
    static String msg; 
    static int opcion=-1;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lblPregunta.setText(msg);
    }    
    public  void msgConfirmation(String mensaje){
        try{ 
            ConfirmationController.msg = mensaje;
            FXMLLoader fxmlLoader= new FXMLLoader(getClass().getResource("confirmation.fxml"));
            Parent root1= (Parent)fxmlLoader.load();
            Stage stage= new Stage();
            stage.setScene(new Scene(root1));
            stage.setTitle("Confirmación");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            stage.show();
        }catch(IOException e){
            System.err.println("Error: carga" + e.getMessage());
        }
    }
    @FXML 
    public void opcionPress1(Event evt){
        ((Node)  (evt.getSource())).getScene().getWindow().hide();
    }
    @FXML 
    public void  opcionPress2(Event evt){
        ((Node)  (evt.getSource())).getScene().getWindow().hide();
        
    }
    

}
