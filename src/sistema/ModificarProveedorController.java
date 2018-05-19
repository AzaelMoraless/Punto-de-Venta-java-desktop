/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistema;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javax.swing.JOptionPane;

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
    private  int  id_proveedor;
    Validaciones validar =  new Validaciones();
    ErrorController er = new ErrorController();
    //View_successfulController msgExitoso = View_successfulController();
    View_successfulController msg_exitoso = new View_successfulController();
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    @FXML 
    public void buscar(MouseEvent event){
        Statement stmt;
        ResultSet rs;
        String busqueda = txtBuscar.getText().trim(); 
        try{ 
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT * FROM aguilas.proveedor WHERE (razon_social  = '"+ busqueda +"')");   
            if(rs!=null){
                if(rs.next()){ 
                   id_proveedor = rs.getInt("id_prov");
                   txtRazonS1.setText(rs.getString("razon_social"));
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
        String sSQL = "UPDATE aguilas.proveedor SET " 
                        + "razon_social = ?"
                        + ",telefono = ?"
                        + ",direccion = ?"
                        + ",email = ?"
                        + "WHERE id_prov=?";
       // if(validar.validarDatosProveedor(txtRFC,txtRazonS1,txtTelefono1,txtDireccion1,txtEmail1))
         //  return;
        try{
            PreparedStatement pstm = con.prepareStatement(sSQL) ;
            pstm.setString(1, txtRazonS1.getText().trim());
            pstm.setString(2, txtTelefono1.getText().trim());
            pstm.setString(3, txtDireccion1.getText().trim());
            pstm.setString(4, txtEmail1.getText().trim());
            pstm.setInt(5, id_proveedor);
            pstm.executeUpdate();  
            msg_exitoso.msgExitoso("Registro actualizado");
        }catch(SQLException e){
        JOptionPane.showMessageDialog(null, e.getMessage());
        }
        txtRazonS1.setText(""); txtTelefono1.setText(""); txtDireccion1.setText(""); txtEmail1.setText("");
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
}
