/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXButton;
import static controller.ModificarEmpleadoController.id_eVar;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javax.swing.JOptionPane;
import modelo.TextFieldFormatter;

/**
 * FXML Controller class
 *
 * @author Azael
 */
public class ModificarProveedorController implements Initializable {
    @FXML TextField txtBuscar;
    @FXML
    private TextField txtRazonS1;
    @FXML
    private TextField txtTelefono1;
    @FXML
    private TextField txtDireccion1;
    @FXML
    private TextField txtEmail1;
    Conexion cc = new Conexion();
    Connection con = cc.conexion();
    private  String  rfc_var;
    static String rfc;
    ErrorController er = new ErrorController();
    View_successfulController msg_exitoso = new View_successfulController();
    
    
    @FXML
    private AnchorPane modificarPanel;
    @FXML
    private JFXButton btnBuscar;
    @FXML
    private JFXButton btnCerrar;
    @FXML
    private JFXButton btnGuardar;
    @FXML
    private HBox errorEmail;
    @FXML
    private HBox errorTel;
    @FXML
    private HBox errorDir;
    @FXML
    private HBox errorName;
    @FXML
    private Label lblRFC;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        buscarProveedor(rfc);
    }
    @FXML 
    public void buscar(MouseEvent event){
        Statement stmt;
        ResultSet rs;
        String busqueda = txtBuscar.getText().trim(); 
        try{ 
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT * FROM aguilas.proveedor WHERE (rfc = '"+ busqueda +"')");   
            if(rs!=null){
                if(rs.next()){ 
                   lblRFC.setText(rfc);
                   rfc_var = rs.getString("rfc");
                   txtRazonS1.setText(rs.getString("nombre"));
                   txtDireccion1.setText(rs.getString("direccion"));
                   txtTelefono1.setText(rs.getString("telefono"));
                   txtEmail1.setText(rs.getString("email"));  
                }else{  
                    txtBuscar.setText("");
                    er.msgError("No existe el poveedor");
                }
            }
        }catch(SQLException e){
          
        }      
    }
    
    public void buscarProveedor(String rfc_v){
        Statement stmt;
        ResultSet rs;
        try{ 
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT * FROM aguilas.proveedor WHERE (rfc = '"+ rfc +"')");   
            if(rs!=null){
                if(rs.next()){ 
                   rfc_var = rs.getString("rfc");
                   lblRFC.setText(rfc);
                   txtRazonS1.setText(rs.getString("nombre"));
                   txtDireccion1.setText(rs.getString("direccion"));
                   txtTelefono1.setText(rs.getString("telefono"));
                   txtEmail1.setText(rs.getString("email"));  
                }else{  
                    txtBuscar.setText("");
                    er.msgError("No existe el poveedor");
                }
            }
        }catch(SQLException e){
          
        }      
    }
    @FXML
    public void guardar(MouseEvent event){ 
        if(validarInput()==false){
            er.msgError("Campo(s) incorrectos");
            return;
        } 
        String sSQL = "UPDATE aguilas.proveedor SET " 
                        + "nombre = ?"
                        + ",telefono = ?"
                        + ",direccion = ?"
                        + ",email = ?"
                        + "WHERE rfc=?";
        
        try{
            PreparedStatement pstm = con.prepareStatement(sSQL) ;
            pstm.setString(1, txtRazonS1.getText().trim());
            pstm.setString(2, txtTelefono1.getText().trim());
            pstm.setString(3, txtDireccion1.getText().trim());
            pstm.setString(4, txtEmail1.getText().trim());
            pstm.setString(5, rfc_var);
            pstm.executeUpdate(); 
            limpiarCampos();
            msg_exitoso.msgExitoso("Registro actualizado");  
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    @FXML
    public void cerrar(MouseEvent event){
        ((Node)  (event.getSource())).getScene().getWindow().hide();
    }
    
    @FXML 
    public void detectaTecla(KeyEvent evt){
          txtRazonS1.setText(""); txtTelefono1.setText(""); txtDireccion1.setText(""); txtEmail1.setText("");
          txtRazonS1.setPromptText(""); txtTelefono1.setPromptText(""); txtDireccion1.setPromptText(""); txtEmail1.setPromptText("");
    }
    
        @FXML
    public void validaEmail(KeyEvent event){
        Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher mather = pattern.matcher(txtEmail1.getText().trim());
        
        if (mather.find() == true) 
            errorEmail.setVisible(false);
        else 
           errorEmail.setVisible(true); 
    }

    @FXML
    private void validaNom(KeyEvent event) {
        Pattern pattern =Pattern.compile("[a-zA-ZñÑáéíóúÁÉÍÓÚ0-9 ]{3,}"); 
        Matcher mather = pattern.matcher(txtRazonS1.getText().trim());  
        if (mather.find() == true) 
            errorName.setVisible(false);
        else 
           errorName.setVisible(true); 
    }

    @FXML
    private void validaTel(KeyEvent event) {
        Pattern pattern =Pattern.compile("([(][0-9]{3}[)])-([0-9]{4})-([0-9]{3})"); 
        Matcher mather = pattern.matcher(txtTelefono1.getText().trim());  
        if (mather.find() == true) 
            errorTel.setVisible(false);
        else 
            errorTel.setVisible(true); 
    }

    @FXML
    private void validaDir(KeyEvent event){
        Pattern pattern =Pattern.compile("[#a-zA-ZñÑáéíóúÁÉÍÓÚ0-9 ]{3,}"); 
        Matcher mather = pattern.matcher(txtDireccion1.getText().trim());  
        if (mather.find() == true) 
            errorDir.setVisible(false);
        else 
           errorDir.setVisible(true); 
    }

   
    
    @FXML 
    private void tfTelefonoKeyRelased(){
        TextFieldFormatter tff= new TextFieldFormatter();
        tff.setMask("(###)-####-###");
        tff.setCaracteresValidos("0123456789");
        tff.setTf(txtTelefono1);
        tff.formatter();
    }
    
    public boolean validarInput(){
        if(errorDir.isVisible() || errorEmail.isVisible() || errorTel.isVisible() ||
            txtDireccion1.getText().equals("") || txtEmail1.getText().equals("") ||
           txtRazonS1.getText().equals(""))
            return false;
        else 
            return true;
    }
    
    public void limpiarCampos(){
        txtTelefono1.setText("");
        txtDireccion1.setText("");
        txtEmail1.setText("");
        txtRazonS1.setText("");
    }
}
