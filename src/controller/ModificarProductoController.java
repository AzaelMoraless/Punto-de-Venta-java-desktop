/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import conexion.Conexion;
import com.jfoenix.controls.JFXButton;
import static controller.ModificarProveedorController.rfc;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Azael
 */
public class ModificarProductoController implements Initializable {

    @FXML
    private TextField txtPrecioC;
    @FXML
    private TextField txtDescripcion;
    @FXML
    private TextField txtPrecioV;
    @FXML
    private JFXButton btnGuardar;
    @FXML
    private JFXButton btnCerrar;
    @FXML
    private TextField txtExistencias;
    static int id_prod;
    
    Conexion cc = new Conexion();
    Connection con = cc.conexion();
    ErrorController er = new ErrorController();
    View_successfulController msg_exitoso = new View_successfulController();
    
    @FXML
    private Label lbIDproduc;
    @FXML
    private HBox errorRFCprov;
    @FXML
    private HBox errorExistencias;
    @FXML
    private HBox errorPrecioC;
    @FXML
    private HBox errorPrecioV;
    @FXML
    private HBox errorDescripcion;
    @FXML
    private TextField txRFCProv;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        buscarProducto(id_prod);
        txtExistencias.lengthProperty().addListener(new ChangeListener<Number>(){
	@Override
	    public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if (newValue.intValue() > oldValue.intValue()) {
                    char ch = txtExistencias.getText().charAt(oldValue.intValue());
                    // Check if the new character is the number or other's
                    if (!(ch >= '0' && ch <= '9' )) {
                        // if it's not number then just setText to previous one
                        txtExistencias.setText(txtExistencias.getText().substring(0,txtExistencias.getText().length()-1)); 
                    }
		}
	    }
        });
    }    


    @FXML
    private void guardar(MouseEvent event) { 
        System.err.println("hola");
        try{
            
            if(validarInput()==false){
                er.msgError("Campo(s) incorrectos");
                return;
            } 
            
            Statement stmt;
            ResultSet rs;
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT * FROM aguilas.proveedor WHERE (rfc = '"+ txRFCProv.getText().trim() +"')");
            
           // if(rs!=null){
                if(rs.next()){ 
                }else{  
                  er.msgError("No existe el poveedor");
                  return;  
                }
               // er.msgError("No existe el poveedor");
                //return;
            //}
            
        String sSQL = "UPDATE aguilas.producto SET " 
                        + "descripcion = ?"
                        + ",id_prov = ?"
                        + ",precio_c = ?"
                        + ",precio_v = ?"
                        + ",existencias = ?"
                        + "WHERE id_p=?";
        
            PreparedStatement pstm = con.prepareStatement(sSQL) ;
            
            pstm.setString(1, txtDescripcion.getText().trim());
            pstm.setString(2, txRFCProv.getText().trim());
            pstm.setDouble(3, Double.parseDouble(txtPrecioC.getText().trim()));
            pstm.setDouble(4, Double.parseDouble(txtPrecioV.getText().trim()));
            pstm.setInt(5, Integer.parseInt(txtExistencias.getText().trim()));
            pstm.setInt(6, id_prod);
            pstm.executeUpdate(); 
            
            limpiarCampos();
            msg_exitoso.msgExitoso("Registro actualizado");  
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        
        
    }

    @FXML
    private void cerrar(MouseEvent event) {
         ((Node)  (event.getSource())).getScene().getWindow().hide();
    }
    
     public void buscarProducto(int id_pro){
        Statement stmt;
        ResultSet rs;
        try{ 
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT * FROM aguilas.producto WHERE (id_p = '"+ id_prod +"')");   
            if(rs!=null){
                if(rs.next()){ 
                   id_prod = rs.getInt("id_p");
                   lbIDproduc.setText(""+id_prod);
                   txtDescripcion.setText(rs.getString("descripcion"));
                   txtPrecioC.setText(rs.getString("precio_c"));
                   txtPrecioV.setText(rs.getString("precio_v"));
                   txtExistencias.setText(rs.getString("existencias")); 
                   txRFCProv.setText(rs.getString("id_prov"));
                }else{  
                    er.msgError("No existe el producto");
                }
            }
        }catch(SQLException e){
          
        }      
    }
     
    @FXML
    private void validaRFC(KeyEvent event) {
        modelo.Delimitador.limitTextField(txRFCProv, 13);
        Pattern pattern =Pattern.compile("^([A-ZÑ\\x26]{3,4}([0-9]{2})(0[1-9]|1[0-2])(0[1-9]|1[0-9]|2[0-9]|3[0-1])[A-Z|\\d]{3})$");
        Matcher mather = pattern.matcher(txRFCProv.getText().trim());  
        if (mather.find() == true) 
            errorRFCprov.setVisible(false);
        else 
           errorRFCprov.setVisible(true); 
    }
    
    @FXML
    private void validaDescrip(KeyEvent event){
        Pattern pattern =Pattern.compile("[#a-zA-ZñÑáéíóúÁÉÍÓÚ0-9 ]{3,}"); 
        Matcher mather = pattern.matcher(txtDescripcion.getText().trim());  
        if (mather.find() == true) 
            errorDescripcion.setVisible(false);
        else 
           errorDescripcion.setVisible(true); 
    }
    @FXML
    private void validaPrecioV(KeyEvent event){
        modelo.Delimitador.limitTextField(txtPrecioV, 15);
        char caracter = event.getCharacter().charAt(0);   
        if (((caracter < '0') || (caracter > '9')) 
        && (caracter != '.' || txtPrecioV.getText().contains(".")) ) {
            event.consume();
        }
    }

    @FXML
    private void validaPrecioC(KeyEvent event){
        modelo.Delimitador.limitTextField(txtPrecioC, 15);
        char caracter = event.getCharacter().charAt(0);   
        if (((caracter < '0') || (caracter > '9')) 
        && (caracter != '.' || txtPrecioC.getText().contains(".")) ) {
            event.consume();
        }
    }
    
    public boolean validarInput(){
        if(errorDescripcion.isVisible() || errorPrecioV.isVisible() || errorExistencias.isVisible() || errorPrecioC.isVisible() || errorRFCprov.isVisible()
           || txtDescripcion.getText().equals("") || txtPrecioC.getText().equals("") || txtPrecioV.getText().equals("") ||
           txtExistencias.getText().equals("") || txRFCProv.getText().equals(""))
            return false;
        else 
            return true;
    }
    
    public void limpiarCampos(){
       txtDescripcion.setText("");
       txtPrecioV.setText("");
       txtPrecioC.setText("");
       txtExistencias.setText("");
       txRFCProv.setText("");
    }
}
