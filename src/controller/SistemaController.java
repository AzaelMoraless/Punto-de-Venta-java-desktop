package controller;

import conexion.Conexion;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
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
import modelo.Empleado;
import modelo.Producto;
import modelo.Proveedor;

public class SistemaController implements Initializable {
    private ObservableList<Proveedor> listaProveedor;
    private ObservableList<Empleado> listaEmpleado;
    private ObservableList<Producto> listaProducto;
    
    @FXML private TableView<Proveedor> tblViewProveedores;
    @FXML private TableView<Empleado> tblViewEmpleados;
    @FXML private TableView<Producto> tblViewProductos;
    
    
    private FilteredList<Proveedor> filteredData;
    private FilteredList<Empleado> filteredDataEmpleado;
    private FilteredList<Producto> filteredDataProducto;
    
    //columnas proveedor
    
    @FXML private TableColumn<Proveedor,String> clmnRfcProv;  //clmnRfcProv
    @FXML private TableColumn<Proveedor,String> clmnNombreProv;  
    @FXML private TableColumn<Proveedor,String> clmnDirProv;  
    @FXML private TableColumn<Proveedor,String> clmnTelProv;  
    @FXML private TableColumn<Proveedor,String> clmnEmailProv;  
    
    ///columnas empleado
    @FXML private TableColumn<Empleado,String> clmnIdEmpleado;
    @FXML private TableColumn<Empleado,String> clmnNombreEmple;
    @FXML private TableColumn<Empleado,String> clmnTelEmpleado;
    @FXML private TableColumn<Empleado,String> clmnNickNEmple;
    @FXML private TableColumn<Empleado,String> clmnContraEmple;
    @FXML private TableColumn<Empleado,String> clmnEmailEmple;
    @FXML private TableColumn<Empleado,String> clmnPuestoEmple;
    
    //columnas producto
    @FXML private TableColumn<Producto,String> clmnDescriProduct;
    @FXML private TableColumn<Producto,String> clmnExisProduct;
    @FXML private TableColumn<Producto,String> clmnIdProduc;
    @FXML private TableColumn<Producto,String> clmnPrecioVProduc;
    @FXML private TableColumn<Producto,String> clmnPrecioCProd;
    
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
    ErrorController er = new ErrorController();
    private boolean flagLoadProv;
    private boolean flagLoadEmple;
    private boolean flagLoadProduct;
    static String user_n;
    static int llave_id_empleado;
    
    //conexion a la base de datos 
    Conexion cc = new Conexion();
    Connection con = cc.conexion();
    
    ConfirmationController confirma = new ConfirmationController();
   
    

    @FXML private TextField txtBuscarEmpleado;
    @FXML private TextField txtBusquedaProv1;
    @FXML private TextField txtBuscarProducto;
    @FXML
    private HBox hBoxClientes;
    @FXML
    private AnchorPane panelClientes;
    @FXML
    private TextField txtBuscarCliente;
  

    
    
  

    
    
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
            if(prove.getRfc().contains(newValue.toUpperCase()))
                return true;
            if(prove.getNombre().toLowerCase().contains(lowerCaseFilter))
                return true;
            if(prove.getDireccion().toLowerCase().contains(lowerCaseFilter))
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
    public void filtrarEmpleado(){
        if(flagLoadEmple){
        txtBuscarEmpleado.textProperty().addListener((observableValue,oldValue,newValue)->{
        filteredDataEmpleado.setPredicate((Predicate<? super Empleado>) emple->{
            if(newValue== null || newValue.isEmpty()){
                return true;
            }
            String lowerCaseFilter =newValue.toLowerCase();
            if(emple.getId_e().contains(newValue))
                return true;
            if(emple.getNombre().toLowerCase().contains(lowerCaseFilter))
                return true;
            
            return false;
             });
            });
            SortedList<Empleado>  sortedData = new SortedList<>(filteredDataEmpleado);
            sortedData.comparatorProperty().bind(tblViewEmpleados.comparatorProperty());
            tblViewEmpleados.setItems(sortedData);
        }else{
            return;
        } 
    }
    
    @FXML
    public void filtrarProducto(){  // filtrar productos
        if(flagLoadProduct){
        txtBuscarProducto.textProperty().addListener((observableValue,oldValue,newValue)->{
        filteredDataProducto.setPredicate((Predicate<? super Producto>) produc->{
            if(newValue== null || newValue.isEmpty()){
                return true;
            }
            String lowerCaseFilter =newValue.toLowerCase();
            if(produc.getCodigo().contains(newValue))
                return true;
            if(produc.getDescripcion().toLowerCase().contains(lowerCaseFilter))
                return true;
            return false;
             });
            });
            SortedList<Producto>  sortedData = new SortedList<>(filteredDataProducto);
            sortedData.comparatorProperty().bind(tblViewProductos.comparatorProperty());
            tblViewProductos.setItems(sortedData);
        }else{
            return;
        } 
    }
    
    @FXML 
    public void cerrarSesion(MouseEvent event) throws IOException{
       ((Node)  (event.getSource())).getScene().getWindow().hide();
       lanzarVentana("C:\\Users\\Azael\\Documents\\Sistema\\src\\view\\Login.fxml");
    }
 
    @FXML 
    public void cargarTablaProveedores(MouseEvent evt){ // cargar datos proveedores 
       //inicializa lista
       listaProveedor = FXCollections.observableArrayList();
       Proveedor.llenarTablaProveedores(con, listaProveedor);
       //enlazar observable con tableview
        tblViewProveedores.setItems(listaProveedor); 
       //enlazar columnas con atributo
      
        clmnRfcProv.setCellValueFactory(new PropertyValueFactory<Proveedor,String>("rfc"));
        clmnNombreProv.setCellValueFactory(new PropertyValueFactory<Proveedor,String>("nombre"));  
        clmnDirProv.setCellValueFactory(new PropertyValueFactory<Proveedor,String>("direccion"));
        clmnTelProv.setCellValueFactory(new PropertyValueFactory<Proveedor,String>("telefono"));
        clmnEmailProv.setCellValueFactory(new PropertyValueFactory<Proveedor,String>("email"));
     
        filteredData =new FilteredList<>(listaProveedor,e->true);
        flagLoadProv = true;
    }
    
    @FXML 
    public void cargarTablaEmpleados(MouseEvent evt){ // cargar datos proveedores 
       //inicializa lista
       listaEmpleado = FXCollections.observableArrayList();
       Empleado.llenarTablaEmpleados(con, listaEmpleado);
       //enlazar observable con tableview
        tblViewEmpleados.setItems(listaEmpleado); 
       //enlazar columnas con atributo
        clmnIdEmpleado.setCellValueFactory(new PropertyValueFactory<Empleado,String>("id_e"));
        clmnNombreEmple.setCellValueFactory(new PropertyValueFactory<Empleado,String>("nombre")); 
        clmnTelEmpleado.setCellValueFactory(new PropertyValueFactory<Empleado,String>("telefono"));  
        clmnNickNEmple.setCellValueFactory(new PropertyValueFactory<Empleado,String>("username"));
        clmnContraEmple.setCellValueFactory(new PropertyValueFactory<Empleado,String>("contrasena")); 
        clmnEmailEmple.setCellValueFactory(new PropertyValueFactory<Empleado,String>("email_e"));
        clmnPuestoEmple.setCellValueFactory(new PropertyValueFactory<Empleado,String>("puesto")); 
     
        filteredDataEmpleado =new FilteredList<>(listaEmpleado,e->true);
        flagLoadEmple = true;
    }
    @FXML
    private void cargarTablaProducts(MouseEvent event){  
               //inicializa lista
       listaProducto = FXCollections.observableArrayList();
       Producto.llenarTablaProductos(con, listaProducto);
       //enlazar observable con tableview
        tblViewProductos.setItems(listaProducto); 
       //enlazar columnas con atributo
        clmnIdProduc.setCellValueFactory(cellData -> cellData.getValue().codigo());
        //clmnIdProduc.setCellValueFactory(new PropertyValueFactory<Producto,String>("idProduc"));
        clmnDescriProduct.setCellValueFactory(new PropertyValueFactory<Producto, String>("descripcion"));  
       // clmnPrecioCProd.setCellValueFactory(new PropertyValueFactory<Producto, String>("precio_c"));
        clmnPrecioCProd.setCellValueFactory(cellData -> cellData.getValue().precio_compra());
        //clmnPrecioVProduc.setCellValueFactory(new PropertyValueFactory<Producto, String>("precio_v"));
         clmnPrecioVProduc.setCellValueFactory(cellData -> cellData.getValue().precio_venta());
        
        clmnExisProduct.setCellValueFactory(new PropertyValueFactory<Producto, String>("existencias"));
     
        filteredDataProducto =new FilteredList<>(listaProducto,p->true);
        flagLoadProduct = true;
    }
    
    
    @FXML 
    public void deleteProveedor(MouseEvent evt){  // eliminar proveedor 
        ObservableList<Proveedor> proveedorSelected;
        proveedorSelected = tblViewProveedores.getSelectionModel().getSelectedItems();
        if(proveedorSelected.isEmpty()){
            er.msgError("No se ha seleccionado el\n elemento a eliminar");
            return;
        }
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("confirmacion");
            alert.setContentText("¿QUIERES ELIMINAR EL REGISTRO?" +  "\nProveedor \nRFC: " +tblViewProveedores.getSelectionModel().getSelectedItem().getRfc()
                                + "\t\t Nombre: "+ tblViewProveedores.getSelectionModel().getSelectedItem().getNombre()
                                + "\nTelefono: " + tblViewProveedores.getSelectionModel().getSelectedItem().getTelefono() 
                                + "\t Dirección: " + tblViewProveedores.getSelectionModel().getSelectedItem().getDireccion() );
            Optional<ButtonType> result = alert.showAndWait();
            
            
            if (result.get() == ButtonType.OK){
                 String rfc = tblViewProveedores.getSelectionModel().getSelectedItem().getRfc();
                 proveedorSelected.forEach(listaProveedor::remove);
                    try{
                        PreparedStatement st = con.prepareStatement("DELETE FROM aguilas.proveedor WHERE rfc = ?");
                        st.setString(1,rfc);
                        st.executeUpdate();  
                         msg_exitoso.msgExitoso("Registro eliminado");
                         
                    }catch(SQLException e){
                        System.out.println("Error: " + e.getMessage());
                    }
            } else {
                System.err.println("false");
            }    
    }
    
    @FXML 
    public void deleteEmpleado(MouseEvent evt){  // eliminar Empleado 
        ObservableList<Empleado> empleadoSelected;
        empleadoSelected = tblViewEmpleados.getSelectionModel().getSelectedItems();
        Empleado empleado = tblViewEmpleados.getSelectionModel().getSelectedItem();
        
        
        if(empleadoSelected.isEmpty()){
            er.msgError("No se ha seleccionado  \n el elemento a eliminar");
            return;
        }
        if(empleado.getPuesto().equals("administrador")){
            er.msgError("No se puede eliminar \n el elemento");
            return;
        }
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("confirmacion");
            alert.setContentText("¿QUIERES ELIMINAR EL REGISTRO? \nEmpleado \n id_e: " + empleado.getId_e() + "\t Nombre: " + empleado.getNombre()
                                 + "\n Telefono: " + empleado.getTelefono() +"\t Nickname: " + empleado.getUsername());
            
            
            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == ButtonType.OK){
                String id = tblViewEmpleados.getSelectionModel().getSelectedItem().getId_e();
                int id_em = Integer.parseInt(id);
                empleadoSelected.forEach(listaEmpleado::remove);
                    try{
                        PreparedStatement st = con.prepareStatement("DELETE FROM aguilas.empleado WHERE id_e = ?");
                        st.setInt(1,id_em);
                        st.executeUpdate();  
                        msg_exitoso.msgExitoso("Registro eliminado");
                    }catch(SQLException e){
                        System.out.println("Error: " + e.getMessage());
                    }
            } else {
                System.err.println("false");
            }    
    }
    
    @FXML 
    public void deleteProducto(MouseEvent evt){  // eliminar Producto 
        ObservableList<Producto> productoSelected;
        productoSelected = tblViewProductos.getSelectionModel().getSelectedItems();
        Producto producto = tblViewProductos.getSelectionModel().getSelectedItem();
        
        if(productoSelected.isEmpty()){
            er.msgError("No se ha seleccionado  \n el elemento a eliminar");
            return;
        }

            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("confirmacion");
            alert.setContentText("¿QUIERES ELIMINAR EL REGISTRO? \nProducto \n id_prod: " 
                                + producto.getCodigo()+ "\t\t Descripcion: " + producto.getDescripcion()
                                + "\n Existencias: " + producto.getExistencias() +"\t\t Precio " + producto.getPrecio_venta());
            
            
            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == ButtonType.OK){
                String id = tblViewProductos.getSelectionModel().getSelectedItem().getCodigo();
                int id_em = Integer.parseInt(id);
                productoSelected.forEach(listaEmpleado::remove);
                    try{
                        PreparedStatement st = con.prepareStatement("DELETE FROM aguilas.producto WHERE id_e = ?");
                        st.setInt(1,id_em);
                        st.executeUpdate();  
                        msg_exitoso.msgExitoso("Registro eliminado");
                    }catch(SQLException e){
                         er.msgError(e.getMessage());
                    }
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
    public void vAgregarProveedor(MouseEvent event){
       lanzarVentana("C:\\Users\\Azael\\Documents\\Sistema\\src\\view\\agregarProveedor.fxml");
       AgregarProveedorController.listaProveedor = listaProveedor;
       
    }
    @FXML
    private void vModificarProveedor(MouseEvent event) {   
        try{  
            ObservableList<Proveedor> provSelected;
            provSelected = tblViewProveedores.getSelectionModel().getSelectedItems();
            Proveedor prov = tblViewProveedores.getSelectionModel().getSelectedItem();

            if(provSelected.isEmpty()){
                er.msgError("No se ha seleccionado  \n el elemento a modificar");
                return;
            }else{
             String rfc_p = prov.getRfc();
             ModificarProveedorController.rfc = rfc_p;
             lanzarVentana("C:\\Users\\Azael\\Documents\\Sistema\\src\\view\\modificarProveedor.fxml");
             ModificarProveedorController.listaProveedor = listaProveedor;
            }
        }catch(Exception e){
            System.err.println("EROROR: " + e.getMessage());
        }
    }
    
    private void agregarProducto(MouseEvent event) {
        lanzarVentana("C:\\Users\\Azael\\Documents\\Sistema\\src\\view\\agregarProducto.fxml");
    }


    
    @FXML
    private void modificarEmpleado(MouseEvent event) {
        try{  
            ObservableList<Empleado> empleadoSelected;
            empleadoSelected = tblViewEmpleados.getSelectionModel().getSelectedItems();
            Empleado empleado = tblViewEmpleados.getSelectionModel().getSelectedItem();

            if(empleadoSelected.isEmpty()){
                er.msgError("No se ha seleccionado  \n el elemento a eliminar");
                return;
            }else{
             String id = empleado.getId_e();
             int id_e = Integer.parseInt(id);
             ModificarEmpleadoController.id_eVar = id_e;
             lanzarVentana("C:\\Users\\Azael\\Documents\\Sistema\\src\\view\\modificarEmpleado.fxml");
             ModificarEmpleadoController.listaEmpleado = listaEmpleado;
            }
        }catch(Exception e){
            System.err.println("EROROR: " + e.getMessage());
        }
    }
    
    @FXML
    private void modificarProducto(MouseEvent event) {
        try{  
            ObservableList<Producto> productoSelected;
            productoSelected = tblViewProductos.getSelectionModel().getSelectedItems();
            Producto producto = tblViewProductos.getSelectionModel().getSelectedItem();

            if(productoSelected.isEmpty()){
                er.msgError("No se ha seleccionado  \n el elemento a eliminar");
                return;
            }else{
             String id = producto.getCodigo();
             //int id_p = Integer.parseInt(id);
            // ModificarProductoController.id_prod = id_p;
             lanzarVentana("C:\\Users\\Azael\\Documents\\Sistema\\src\\view\\modificarProducto.fxml");
             
            }
        }catch(Exception e){
            System.err.println("EROROR: " + e.getMessage());
        }
    }
    
    @FXML
    private void agregarEmpleado(MouseEvent event) {
        lanzarVentana("C:\\Users\\Azael\\Documents\\Sistema\\src\\view\\agregarEmpleado.fxml");
        AgregarEmpleadoController.listaEmpleado = listaEmpleado;
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
    private void vAgregarCliente(MouseEvent event) {
    }

    @FXML
    private void vModificarCliente(MouseEvent event) {
    }

    @FXML
    private void deleteCliente(MouseEvent event) {
    }

    @FXML
    private void cargarTablaClientes(MouseEvent event) {
    }

    @FXML
    private void agregarCompra(MouseEvent event) {
        lanzarVentana("C:\\Users\\Azael\\Documents\\Sistema\\src\\view\\agregarCompra.fxml");
    }
   
   
}

