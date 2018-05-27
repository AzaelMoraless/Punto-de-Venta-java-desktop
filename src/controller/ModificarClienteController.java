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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javax.swing.JOptionPane;
import modelo.Cliente;
import modelo.Proveedor;
import modelo.TextFieldFormatter;

/**
 * FXML Controller class
 *
 * @author Azael
 */
public class ModificarClienteController implements Initializable {

    @FXML private TextField txtRFC;
    @FXML private TextField txtNombre;
    @FXML private HBox errorRFC;
    @FXML private TextField txtTelefono;
    @FXML private HBox errorName;
    @FXML private HBox errorTel;
    @FXML private TextField txtEmail;
    @FXML private ComboBox<String> comboCorreos;
    @FXML private HBox errorEmail;
    @FXML private JFXButton btnGuardar;
    @FXML private JFXButton btnCancelar;
    static ObservableList<Cliente> listaCliente;
    static String rfc_cliente;
    
   
    Conexion cc = new Conexion();
    Connection con = cc.conexion();
    
    ErrorController msgErr = new ErrorController();
    View_successfulController msg_exitoso = new View_successfulController();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        buscarCliente(rfc_cliente);
    }    
    public void buscarCliente(String rfc_c){
        Statement stmt;
        ResultSet rs;
        try{ 
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT * FROM aguilas.cliente WHERE (RFC_cliente = '"+ rfc_c +"')");   
            if(rs!=null){
                if(rs.next()){ 
                   txtRFC.setText(rfc_c);
                   txtNombre.setText(rs.getString("nombre"));
                   txtTelefono.setText(rs.getString("telefono"));
                   txtEmail.setText(rs.getString("email"));  
                }else{  
                    msgErr.msgError("No existe el cliente");
                }
            }
        }catch(SQLException e){
          
        }      
    }
    
     @FXML
    private void aceptar(MouseEvent event){
         if(validarEntradas()==false){
            msgErr.msgError("Campo(s) incorrectos");
            return;
        } 
        String sSQL = "UPDATE aguilas.cliente SET " 
                        + "nombre = ?"
                        + ",telefono = ?"
                        + ",email = ?"
                        + "WHERE RFC_cliente=?";
        
        try{
            PreparedStatement pstm = con.prepareStatement(sSQL) ;
            pstm.setString(1, txtNombre.getText().trim());
            pstm.setString(2, txtTelefono.getText().trim());
            pstm.setString(3, txtEmail.getText().trim());
            pstm.setString(4, rfc_cliente);
            pstm.executeUpdate(); 
            //limpiarCampos();
            
            listaCliente.clear();
            Cliente.llenarTablaClientes(con, listaCliente);
            msg_exitoso.msgExitoso("Registro actualizado");  
            
            ((Node)  (event.getSource())).getScene().getWindow().hide();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    @FXML
    private void validaNom(KeyEvent event) {
         Pattern pattern =Pattern.compile("[a-zA-ZñÑáéíóúÁÉÍÓÚ0-9 ]{3,}"); 
        Matcher mather = pattern.matcher(txtNombre.getText().trim());  
        if (mather.find() == true) 
            errorName.setVisible(false);
        else 
           errorName.setVisible(true); 
    }

    @FXML
    private void validaTel(KeyEvent event) {
        Pattern pattern =Pattern.compile("([(][0-9]{3}[)])-([0-9]{4})-([0-9]{3})"); 
        Matcher mather = pattern.matcher(txtTelefono.getText().trim());  
        if (mather.find() == true) 
            errorTel.setVisible(false);
        else 
            errorTel.setVisible(true); 
    }

    @FXML
    private void tfTelefonoKeyRelased(KeyEvent event) {
        TextFieldFormatter tff= new TextFieldFormatter();
        tff.setMask("(###)-####-###");
        tff.setCaracteresValidos("0123456789");
        tff.setTf(txtTelefono);
        tff.formatter();
    }

    @FXML
    private void validaEmail(KeyEvent event) {
        Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*$"); 
        Matcher mather = pattern.matcher(txtEmail.getText().trim());
        
        if (mather.find() == true) 
            errorEmail.setVisible(false);
        else 
           errorEmail.setVisible(true);
    }

   

    @FXML
    private void cancelar(MouseEvent event) {
        ((Node)  (event.getSource())).getScene().getWindow().hide();
    }
    
    public boolean validarEntradas(){
        if(errorName.isVisible() ||errorEmail.isVisible() || errorTel.isVisible() 
            || txtTelefono.getText().equals("") || txtEmail.getText().equals("") ||
           txtNombre.getText().equals(""))
            return false;
        else 
            return true;
    }
}
