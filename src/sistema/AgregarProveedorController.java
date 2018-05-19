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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
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
    @FXML
    private TextField txtRFC;
    @FXML
    private HBox errorRFC;
    @FXML
    private HBox errorName;
    @FXML
    private HBox errorDir;
    @FXML
    private HBox errorTel;
    @FXML
    private HBox errorEmail;
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    
    
    @FXML 
    public void aceptar(MouseEvent event){
        String rfc,nombre,direccion,telefono,email;
        rfc = txtRFC.getText().trim(); nombre = txtRazonS.getText().trim(); direccion = txtDireccion.getText().trim(); telefono = txtTelefono.getText().trim();
        email = txtEmail.getText().trim();
        if(validar.validarDatosProveedor(txtRFC,txtRazonS,txtTelefono,txtDireccion,txtEmail))
           return;
        try{ 
            Statement consulta=(Statement)con.createStatement();
            consulta.executeUpdate("insert into aguilas.proveedor (rfc,nombre,direccion,telefono,email) values('"+nombre+"','"+direccion+"','"+telefono+"','"+email+"')");
            msg_exitoso.msgExitoso("Proveedor agregado");
        }catch(SQLException e){
           JOptionPane.showMessageDialog(null,e.getMessage());
        }    
    }
    
    @FXML
    public void cancelar(MouseEvent event){
         ((Node)  (event.getSource())).getScene().getWindow().hide();
    }
    
    @FXML
    public void validaEmail(KeyEvent event){
          Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher mather = pattern.matcher(txtEmail.getText().trim());
        if (mather.find() == true) {
            errorEmail.setVisible(true);
        } else {
           errorEmail.setVisible(false);
        } 
    }
    
}
