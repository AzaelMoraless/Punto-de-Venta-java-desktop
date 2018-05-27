/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import conexion.Conexion;
import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import modelo.Empleado;
import modelo.TextFieldFormatter;

/**
 * FXML Controller class
 *
 * @author Azael
 */
public class ModificarEmpleadoController implements Initializable {
    @FXML private JFXButton btnCerrar;
    @FXML private JFXButton btnGuardar;
    @FXML private TextField txtEmail;
    @FXML  private TextField txtNickname;
    @FXML private TextField txtTelefono;
    @FXML private TextField txtContrasenia;
    @FXML private TextField txtNombre;
    ObservableList<String> listcorreos = FXCollections.observableArrayList("@hotmail.com","@gmail.com","@outlook.com","@yahoo.com");
     
    Conexion cc = new Conexion();
    Connection con = cc.conexion();
    ErrorController msgErr = new ErrorController();
    //static 
    static  int  id_eVar;
    static ObservableList<Empleado> listaEmpleado;
    //Validaciones validar =  new Validaciones();
    
    ErrorController error = new ErrorController();
    View_successfulController msg_exitoso = new View_successfulController();
    @FXML private HBox errorEmail;
    @FXML private HBox errorTel;
    @FXML private HBox errorContra;
    @FXML private HBox errorNick;
    @FXML private HBox errorNombre;
    @FXML private RadioButton rbutton2;
    @FXML private RadioButton rbutton1;
    @FXML private ComboBox<String> comboCorreos;
    
    @Override
    public void initialize(URL url, ResourceBundle rb){
       buscarEmpleado(id_eVar);
       ToggleGroup toggleGroup = new ToggleGroup();
       rbutton1.setToggleGroup(toggleGroup);
       rbutton2.setToggleGroup(toggleGroup);
       
      // comboCorreos.setValue("@ello");
       comboCorreos.setItems(listcorreos);
       comboCorreos.setStyle("-fx-font: 14px \"Roboto\";");
    }    

    @FXML
    private void guardar(MouseEvent event) {
        String email = txtEmail.getText().trim() + comboCorreos.getSelectionModel().getSelectedItem();
        if(validarInput()==false){
            msgErr.msgError("Campo(s) incorrectos");
            return;
        } 
        String puesto="";
        if(rbutton1.isSelected()){
            msgErr.msgError("No se puede agregar \n otro administrador");
            return;
        }else if(rbutton2.isSelected())
            puesto = "vendedor";
        
         String sSQL = "UPDATE aguilas.empleado SET " 
                        + "nombre = ?"
                        + ",telefono = ?"
                        + ",username = ?"
                        + ",contrasena = ?"
                        + ",email_e = ?"
                        + ",puesto = ?"
                        + "WHERE id_e=?";
        try{
            PreparedStatement pstm = con.prepareStatement(sSQL) ;
            pstm.setString(1, txtNombre.getText().trim());
            pstm.setString(2, txtTelefono.getText().trim());
            pstm.setString(3, txtNickname.getText().trim());
            pstm.setString(4, txtContrasenia.getText().trim());
            pstm.setString(5, email);
            pstm.setString(6, puesto);
            pstm.setInt(7, id_eVar);
            pstm.executeUpdate();  
            
            
            listaEmpleado.clear();
            Empleado.llenarTablaEmpleados(con, listaEmpleado);
            msg_exitoso.msgExitoso("Registro actualizado");
            ((Node)  (event.getSource())).getScene().getWindow().hide();
        }catch(SQLException e){
                 JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    public void buscarEmpleado(int id){  // buscar Empleado   
        id_eVar = id;
        Statement stmt;
        ResultSet rs;
        try{ 
            stmt = con.createStatement(); 
            rs = stmt.executeQuery("SELECT * FROM aguilas.empleado WHERE (id_e = '"+ id_eVar +"')");   
            if(rs!=null){
                if(rs.next()){ 
                   txtNombre.setText(rs.getString("nombre"));
                   txtTelefono.setText(rs.getString("telefono"));
                   txtNickname.setText(rs.getString("username"));
                   txtContrasenia.setText(rs.getString("contrasena"));
                   
                   String[] part = rs.getString("email_e").split("@");
                   txtEmail.setText(part[0]);
                   comboCorreos.setValue("@"+part[1]);

                   if(rs.getString("puesto").equals("administrador"))
                       rbutton1.setSelected(true);
                   else if(rs.getString("puesto").equals("vendedor"))
                       rbutton2.setSelected(true);
                }else{  
                    error.msgError("No existe el poveedor");
                }
            }
        }catch(SQLException e){
          System.err.println("EROROR22: " + e.getMessage());
        }      
         
    }
    
    public void lanzarVentana(String ruta_view_fxml){
        try{ 
            URL url = Paths.get(ruta_view_fxml).toUri().toURL();
            Parent root = FXMLLoader.load(url);
            Stage stage= new Stage();
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            stage.show();
        }catch(IOException e){
             System.err.println("ERROR " + e.getMessage());
        }
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
    private void validaEmail(KeyEvent event) {
        Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*$"); 
        Matcher mather = pattern.matcher(txtEmail.getText().trim());
        
        if (mather.find() == true) 
            errorEmail.setVisible(false);
        else 
           errorEmail.setVisible(true); 
    }

    @FXML
    private void validaNom(KeyEvent event) {
         Pattern pattern =Pattern.compile("[a-zA-ZñÑáéíóúÁÉÍÓÚ0-9 ]{3,}"); 
        Matcher mather = pattern.matcher(txtNombre.getText().trim());  
        if (mather.find() == true) 
            errorNombre.setVisible(false);
        else 
           errorNombre.setVisible(true);
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
    private void validaNick(KeyEvent event) {
          Pattern pattern =Pattern.compile("[a-zA-ZñÑáéíóúÁÉÍÓÚ0-9 ]{3,}"); 
        Matcher mather = pattern.matcher(txtNickname.getText().trim());  
        if (mather.find() == true) 
            errorNick.setVisible(false);
        else 
           errorNick.setVisible(true); 
    }

    @FXML
    private void validaContra(KeyEvent event) {
          Pattern pattern =Pattern.compile("[a-zA-ZñÑáéíóúÁÉÍÓÚ0-9 ]{3,}"); 
        Matcher mather = pattern.matcher(txtContrasenia.getText().trim());  
        if (mather.find() == true) 
           errorContra.setVisible(false);
        else 
           errorContra.setVisible(true); 
    }
    
     public boolean validarInput(){
        if(errorNombre.isVisible() || errorNick.isVisible() || errorContra.isVisible() || errorEmail.isVisible() || errorTel.isVisible() ||
           txtNombre.getText().equals("") || txtNickname.getText().equals("") || txtContrasenia.getText().equals("") || txtEmail.getText().equals("") ||
                txtTelefono.getText().equals(""))
            return false;
        else 
            return true;
    }
    
    public void limpiarCampos(){
        txtNombre.setText("");
        txtNickname.setText("");
        txtContrasenia.setText("");
        txtEmail.setText("");
        txtTelefono.setText("");
        rbutton1.setSelected(false);
        rbutton2.setSelected(false);
    }
    @FXML
    private void cerrar(MouseEvent event) {
    ((Node)  (event.getSource())).getScene().getWindow().hide();
    }
    
}
