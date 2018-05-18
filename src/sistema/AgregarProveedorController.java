/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistema;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javax.swing.JOptionPane;

public class AgregarProveedorController implements Initializable {
    @FXML
    private AnchorPane agregarProvPanel;
    @FXML
    private TextField txtRazonS;
    @FXML
    private TextField txtDireccion;
    @FXML
    private TextField txtTelefono;
    @FXML
    private TextField txtEmail;
    @FXML
    private JFXButton btnGuardar;
    @FXML
    private JFXButton btnCancelar;
    Conexion cc = new Conexion();
    Connection con = cc.conexion();
    Validaciones validar =  new Validaciones();
    ErrorController er = new ErrorController();
    View_successfulController msg_exitoso = new View_successfulController();
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML 
    public void aceptar(MouseEvent event){
        String razon_social,direccion,telefono,email;
        razon_social = txtRazonS.getText().trim(); direccion = txtDireccion.getText().trim(); telefono = txtTelefono.getText().trim();
        email = txtEmail.getText().trim();
        if(validar.validarDatosProveedor(txtRazonS,txtTelefono,txtDireccion,txtEmail))
           return;
        try{ 
            Statement consulta=(Statement)con.createStatement();
            consulta.executeUpdate("insert into aguilas.proveedor (razon_social,direccion,telefono,email) values('"+razon_social+"','"+direccion+"','"+telefono+"','"+email+"')");
            msg_exitoso.msgExitoso("Proveedor agregado");
        }catch(SQLException e){
           JOptionPane.showMessageDialog(null,e.getMessage());
        }    
    }
    @FXML
    public void cancelar(MouseEvent event){
         ((Node)  (event.getSource())).getScene().getWindow().hide();
    }
}
