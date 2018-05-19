package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javafx.scene.control.Alert;
import javafx.stage.StageStyle;

public class Conexion {
    static Connection conection = null;
    public static Connection conexion(){
        String url = "jdbc:postgresql://localhost:5432/SistemaDB";
        String password = "master";
        try{
            Class.forName("org.postgresql.Driver");
            conection = DriverManager.getConnection(url,  "administrador", password); 
        
        }catch(ClassNotFoundException | SQLException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Error de conexión");
            alert.initStyle(StageStyle.UTILITY);
            alert.showAndWait();
        }
    return conection;
    }
}
