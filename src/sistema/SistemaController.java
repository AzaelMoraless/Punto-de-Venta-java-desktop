package sistema;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import modelo.Proveedor;

public class SistemaController implements Initializable {
    private ObservableList<Proveedor> listaProveedor;
    @FXML private TableView<Proveedor> tblViewProveedores;
    
    private FilteredList<Proveedor> filteredData;
    //columnas 
    
    @FXML private TableColumn<Proveedor,String> clmnId;  
    @FXML private TableColumn<Proveedor,String> clmnRazon_s;  
    @FXML private TableColumn<Proveedor,String> clmnDireccion;  
    @FXML private TableColumn<Proveedor,String> clmnTelefono;  
    @FXML private TableColumn<Proveedor,String> clmnEmail;  
    //
    @FXML private Label label_user;
    @FXML private AnchorPane ventasPanel;  // 1 
    @FXML private AnchorPane productosPanel; // 2 
    @FXML private AnchorPane comprasPanel; //3 
    @FXML private AnchorPane proveedoresPanel; // 4
    @FXML private AnchorPane empleadosPanel; //5
    @FXML private AnchorPane ayudaPanel; //6
    @FXML private AnchorPane panelAcercade; //7
     
    @FXML private HBox hBoxVentas,hBoxProductos,hBoxProv,hBoxCompras,hBoxEmpleados,hBoxAyuda,hBoxAcerca;
    @FXML private TextField txtBusquedaProv;
    View_successfulController msg_exitoso = new View_successfulController();
    private boolean flagLoadProv;
    static String user_n;
    Conexion cc = new Conexion();
    Connection con = cc.conexion();
    ConfirmationController confirma = new ConfirmationController();
    ErrorController er = new ErrorController();
    @FXML
    private HBox hBoxAgregarProducto;
    @FXML
    private TextField txtBuscarEmpleado;
    @FXML
    private TextField txtBuscarProducto;
    @FXML
    private TextField txtBusquedaProv1;

    
    
    @Override 
    public void initialize(URL url, ResourceBundle rb) {
       label_user.setText(user_n); 
    } 
    
    @FXML
    public void filtrarProveedor(){
        if(flagLoadProv){
        txtBusquedaProv.textProperty().addListener((observableValue,oldValue,newValue)->{
        filteredData.setPredicate((Predicate<? super Proveedor>) prove->{
            if(newValue== null || newValue.isEmpty()){
                return true;
            }
            String lowerCaseFilter =newValue.toLowerCase();
            if(prove.getId_proveedor().contains(newValue))
                return true;
            if(prove.getRazon_s().toLowerCase().contains(lowerCaseFilter))
                return true;
            return false;
             });
            });
            SortedList<Proveedor>  sortedData = new SortedList<>(filteredData);
            sortedData.comparatorProperty().bind(tblViewProveedores.comparatorProperty());
            tblViewProveedores.setItems(sortedData);
        }else{
            return;
        }
      
    }
    @FXML 
    public void cerrarSesion(MouseEvent event) throws IOException{
       ((Node)  (event.getSource())).getScene().getWindow().hide();
       lanzarVentana("Login.fxml");
    }
    
    @FXML 
    public void panelVentas(MouseEvent event){ // 1
        setSeleccionColor(hBoxCompras);
        setSeleccionColor(hBoxProductos);
        setSeleccionColor(hBoxProv);
        setSeleccionColor(hBoxEmpleados);
        setSeleccionColor(hBoxAyuda);
        setSeleccionColor(hBoxAcerca);
        hBoxVentas.setBackground(new Background(new BackgroundFill(Color.web("#3C8DBC"), new CornerRadii(2),javafx.geometry.Insets.EMPTY)));   
        visible(2, false);
        visible(1, true);
        visible(3, false);
        visible(4, false);
        visible(5, false);
        visible(6, false);
        visible(7, false);
    }
    @FXML 
    public void mostrarPanelProductos(MouseEvent event){ // 2
        setSeleccionColor(hBoxVentas);
        setSeleccionColor(hBoxCompras);
        setSeleccionColor(hBoxProv);
        setSeleccionColor(hBoxEmpleados);
        setSeleccionColor(hBoxAyuda);
        setSeleccionColor(hBoxAcerca);
        hBoxProductos.setBackground(new Background(new BackgroundFill(Color.web("#3C8DBC"), new CornerRadii(2),javafx.geometry.Insets.EMPTY)));   
        visible(2, true);
        visible(1, false);
        visible(3, false);
        visible(4, false);
        visible(5, false);
        visible(6, false);
        visible(7, false);
    }
    
    @FXML 
    public void mostrarPanelCompras(MouseEvent event){//3
        setSeleccionColor(hBoxVentas);
        setSeleccionColor(hBoxProductos);
        setSeleccionColor(hBoxProv);
        setSeleccionColor(hBoxEmpleados);
        setSeleccionColor(hBoxAyuda);
        setSeleccionColor(hBoxAcerca);
        hBoxCompras.setBackground(new Background(new BackgroundFill(Color.web("#3C8DBC"), new CornerRadii(2),javafx.geometry.Insets.EMPTY)));   
        visible(3, true);
        visible(1, false);
        visible(2, false);
        visible(4, false);
        visible(5, false);
        visible(6, false);
        visible(7, false);
    }
    @FXML 
    public void mostrarPanelProveedores(MouseEvent event){//4
        setSeleccionColor(hBoxVentas);
        setSeleccionColor(hBoxProductos);
        setSeleccionColor(hBoxCompras);
        setSeleccionColor(hBoxEmpleados);
        setSeleccionColor(hBoxAyuda);
        setSeleccionColor(hBoxAcerca);
        hBoxProv.setBackground(new Background(new BackgroundFill(Color.web("#3C8DBC"), new CornerRadii(2),javafx.geometry.Insets.EMPTY)));   
       
        visible(4, true);
        visible(1, false);
        visible(2, false);
        visible(3, false);
        visible(5, false);
        visible(6, false);
        visible(7, false);
         }
    @FXML 
    public void mostrarPanelEmpleados(MouseEvent event){//5
        setSeleccionColor(hBoxVentas);
        setSeleccionColor(hBoxProductos);
        setSeleccionColor(hBoxProv);
        setSeleccionColor(hBoxCompras);
        setSeleccionColor(hBoxAyuda);
        setSeleccionColor(hBoxAcerca);
        hBoxEmpleados.setBackground(new Background(new BackgroundFill(Color.web("#3C8DBC"), new CornerRadii(2),javafx.geometry.Insets.EMPTY)));   
   
        visible(5, true);
        visible(1, false);
        visible(2, false);
        visible(3, false);
        visible(4, false);
        visible(6, false);
        visible(7, false);
    }
    @FXML 
    public void mostrarPanelAyuda(MouseEvent event){//6
        setSeleccionColor(hBoxVentas);
        setSeleccionColor(hBoxProductos);
        setSeleccionColor(hBoxProv);
        setSeleccionColor(hBoxEmpleados);
        setSeleccionColor(hBoxCompras);
        setSeleccionColor(hBoxAcerca);
        
        hBoxAyuda.setBackground(new Background(new BackgroundFill(Color.web("#3C8DBC"), new CornerRadii(2),javafx.geometry.Insets.EMPTY)));   
        visible(1, false);
        visible(6, true);  
        visible(2, false);
         visible(3, false);
        visible(4, false);
        visible(5, false);
        visible(7, false);
    }
    
    
    @FXML 
    public void mostrarPanelAcercade(MouseEvent event){//6
        setSeleccionColor(hBoxVentas);
        setSeleccionColor(hBoxProductos);
        setSeleccionColor(hBoxProv);
        setSeleccionColor(hBoxEmpleados);
        setSeleccionColor(hBoxCompras);
        setSeleccionColor(hBoxAyuda);
        hBoxAcerca.setBackground(new Background(new BackgroundFill(Color.web("#3C8DBC"), new CornerRadii(2),javafx.geometry.Insets.EMPTY)));   
        visible(1, false);
        visible(6, false);  
        visible(2, false);
        visible(3, false);
        visible(4, false);
        visible(5, false);
        visible(7, true);
    }
     public void visible(int n,boolean b){ // visibilidad de paneles
        switch(n){
            case 1: ventasPanel.setVisible(b);
            break;
            case 2: productosPanel.setVisible(b);
            break;
            case 3: comprasPanel.setVisible(b);
            break;
            case 4: proveedoresPanel.setVisible(b);
            break;
            case 5: empleadosPanel.setVisible(b);
            break; 
            case 6: ayudaPanel.setVisible(b);
            break;
            case 7: panelAcercade.setVisible(b);
            break;             
         }
    }
    
    public void setSeleccionColor(HBox hbox){  // cambia de color el HBOX seleccionado
        hbox.setBackground(new Background(new BackgroundFill(Color.web("#1E282C"), new CornerRadii(2),javafx.geometry.Insets.EMPTY)));
    }
    @FXML 
    public void vAgregarProveedor(MouseEvent event){
       lanzarVentana("agregarProveedor.fxml");
    }
    @FXML
    private void vModificarProveedor(MouseEvent event) {
        lanzarVentana("modificarProveedor.fxml");
    }
    @FXML 
    public void cargarTablaProveedores(MouseEvent evt){
         //inicializa lista
       listaProveedor = FXCollections.observableArrayList();
       Proveedor.llenarTablaProveedores(con, listaProveedor);
       //enlazar observable con tableview
        tblViewProveedores.setItems(listaProveedor); 
       //enlazar columnas con atributo
        clmnId.setCellValueFactory(new PropertyValueFactory<Proveedor,String>("id_proveedor"));
        clmnRazon_s.setCellValueFactory(new PropertyValueFactory<Proveedor,String>("razon_s"));
        clmnDireccion.setCellValueFactory(new PropertyValueFactory<Proveedor,String>("direccion"));
        clmnTelefono.setCellValueFactory(new PropertyValueFactory<Proveedor,String>("telefono"));
        clmnEmail.setCellValueFactory(new PropertyValueFactory<Proveedor,String>("email"));
        
        filteredData =new FilteredList<>(listaProveedor,e->true);
        flagLoadProv = true;
    }
    
    @FXML 
    public void deleteProveedor(MouseEvent evt){
        
        ObservableList<Proveedor> proveedorSelected;
        proveedorSelected = tblViewProveedores.getSelectionModel().getSelectedItems();
        if(proveedorSelected.isEmpty()){
            er.msgError("No se ha seleccionado  \n el elemento a eliminar");
            return;
        }
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("confirmacion");
            alert.setContentText("¿Quiere eliminar el registro?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                 String id = tblViewProveedores.getSelectionModel().getSelectedItem().getId_proveedor();
                int id_p = Integer.parseInt(id);
                proveedorSelected.forEach(listaProveedor::remove);
                    try{
                        PreparedStatement st = con.prepareStatement("DELETE FROM aguilas.proveedor WHERE id_prov = ?");
                        st.setInt(1,id_p);
                        st.executeUpdate();  
                         msg_exitoso.msgExitoso("Registro eliminado");
                    }catch(SQLException e){
                        System.out.println("Error: " + e.getMessage());
                    }
            } else {
                System.err.println("false");
            }    
    }
    
    
    public void lanzarVentana(String view_fxml){
         try{ 
            FXMLLoader fxmlLoader= new FXMLLoader(getClass().getResource(view_fxml));
            Parent root1= (Parent)fxmlLoader.load();
            Stage stage= new Stage();
            stage.setScene(new Scene(root1));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            stage.show();
        }catch(IOException e){}
    }

    @FXML
    private void agregarProducto(MouseEvent event) {
        lanzarVentana("agregarProducto.fxml");
    }

    @FXML
    private void modificarProducto(MouseEvent event) {
        lanzarVentana("modificarProducto.fxml");
    }
    
    @FXML
    private void agregarEmpleado(MouseEvent event) {
        lanzarVentana("agregarEmpleado.fxml");
    }
    @FXML
    private void modificarEmpleado(MouseEvent event) {
        lanzarVentana("modificarEmpleado.fxml");
    }
}

