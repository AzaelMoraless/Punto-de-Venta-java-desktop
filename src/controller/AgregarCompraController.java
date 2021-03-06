/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXButton;
import conexion.Conexion;
import static controller.ModificarProveedorController.rfc;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.converter.LocalDateStringConverter;
import javax.swing.JOptionPane;
import modelo.Compra;
import modelo.DetalleCompra;
import modelo.Producto;
import modelo.Proveedor;

/**
 * FXML Controller class
 *
 * @author Azael
 */
public class AgregarCompraController implements Initializable {

    @FXML private TextField txtFolioCompra;
    @FXML private TextField txtRFCProv;
    @FXML private DatePicker fechaCompra;
    @FXML private ImageView buscarProveedor;
    @FXML private TextField txtcodigoProducto;
    @FXML private TextField txtDescripcionProducto;
    @FXML private TextField txtPrecioC;
    @FXML private TextField txtPrecioV;
    @FXML private TextField txtExistencias;
    @FXML private HBox errorRFC;
    @FXML private HBox errorFolio;
    @FXML private HBox errorDescripcion;
   
    @FXML private TableView<Producto> tablaProductos;
    @FXML private TableColumn<Producto,String> clmnCodigo;
    @FXML private TableColumn<Producto,String> clmnDescripcion;
    @FXML private TableColumn<Producto, String> clmnExistencias;
    @FXML private TableColumn<Producto, String> clmnPrecioC;
    @FXML private TableColumn<Producto,String> clmnTotal;
    @FXML private JFXButton btnGuardar;
    @FXML private JFXButton btnCancelar;
    @FXML private HBox btnAgregar;
    @FXML private HBox btnEliminar;
    @FXML private HBox btnEditar;
    private FilteredList<Producto> filteredDataProducto;
    private boolean flagLoadProduct;
    @FXML
    private TextField txtBuscarProducto;
    @FXML
    private HBox errorCodigo;
    static TextField campoRFC; // campo statico para obtener rfc de busqueda
    
    
    private ObservableList<Producto> listaProducto;
    
    static ObservableList<Compra> listaCompra;
    Conexion cc = new Conexion();
    Connection con = cc.conexion();
    View_successfulController msg_exitoso = new View_successfulController();
    ErrorController msgErr = new ErrorController();
    
    private ArrayList <DetalleCompra> arrayDetalleCompra = new ArrayList <DetalleCompra> ();
    private ArrayList <Producto> arrayProductos= new ArrayList <Producto> ();
    private String codigo,descripcion,rfc_prov, folio_compra;
    private Double precio_c,precio_v;
    private int existencias;
    private  LocalDate fecha;
    private double total_compra;
    
    @FXML
    private Label lblTotalaPagar;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fechaCompra.setValue(LocalDate.now()); 
        
        campoRFC = txtRFCProv;
        
        eventoFecha();
        cargarTablaProductos();
        txtExistencias.lengthProperty().addListener(new ChangeListener<Number>(){
	@Override
	    public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if (newValue.intValue() > oldValue.intValue()) {
                    char ch = txtExistencias.getText().charAt(oldValue.intValue());
                    if (!(ch >= '0' && ch <= '9' )) {
                        txtExistencias.setText(txtExistencias.getText().substring(0,txtExistencias.getText().length()-1)); 
                    }
		}
	    }
        });
    }    

    public void eventoFecha(){
        Callback<DatePicker, DateCell> dayCellFactory = dp -> new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {

                super.updateItem(item, empty);

                this.setDisable(false);
                this.setBackground(null);
                this.setTextFill(Color.BLACK);

                // deshabilitar las fechas futuras
                if (item.isAfter(LocalDate.now())) {
                    this.setDisable(true);
                }

                // marcar los dias de quincena
                int day = item.getDayOfMonth();
                if(day == 15 || day == 30) {

                    Paint color = Color.ORANGE;
                    BackgroundFill fill = new BackgroundFill(color, null, null);

                    this.setBackground(new Background(fill));
                    this.setTextFill(Color.WHITESMOKE);
                }

                // fines de semana en color verde
                DayOfWeek dayweek = item.getDayOfWeek();
                if (dayweek == DayOfWeek.SATURDAY || dayweek == DayOfWeek.SUNDAY) {
                    this.setTextFill(Color.GREEN);
                }
            }
        };
        fechaCompra.setDayCellFactory(dayCellFactory);
    }

    @FXML
    private void buscarProveedor(MouseEvent event) {
        lanzarVentana("/view/buscarProveedor.fxml");
    }

    @FXML //agregar Compra
    private void aceptar(MouseEvent event) {  // guarda la compra en la base de datos 
        if(validarEntradasCompra()==false || listaProducto.isEmpty()){
            msgErr.msgError("Campo(s) de compra\n\tincorrectos");
            return; 
        }
        
        int id_empleado = SistemaController.llave_id_empleado;
        
        folio_compra = txtFolioCompra.getText();
        fecha = fechaCompra.getValue();
        
        
        double total = 0;
        for(int i=0; i<arrayDetalleCompra.size(); i++){
            total+= arrayDetalleCompra.get(i).getSubtotal();
        }
        
        // agregar compra a la base de datos 
        try{ 
            Statement consulta=(Statement)con.createStatement();
            consulta.executeUpdate("insert into aguilas.compra(folio_c,fecha,rfc_prov,recibio,total_compra) "
                    + "values('"+folio_compra+"','"+fecha+"','"+rfc_prov+"','"+id_empleado+"','"+total+"')");     
        }catch(SQLException e){
           msgErr.msgError("Error ventas " + e.getMessage());
        }   
        //agregar productos a la base de datos
        try{ 
            Statement consulta=(Statement)con.createStatement();
            
            for(int i=0; i<arrayProductos.size(); i++){
                consulta.executeUpdate("insert into aguilas.producto(codigo_prod,descripcion,rfc_prov,precio_c,precio_v,existencias) "
                
                + "values('"+arrayProductos.get(i).getCodigo()+"','"+arrayProductos.get(i).getDescripcion()+"','"
                + rfc_prov+"','"+arrayProductos.get(i).getPrecio_compra()+"','"
                + arrayProductos.get(i).getPrecio_venta()+"','"+arrayProductos.get(i).getExistencias()+"')");
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Error: producto " + e.getMessage());
        }  
        
        //agregar detalles de la compra a la base de datos 
        try{ 
            Statement consulta=(Statement)con.createStatement();
            for(int i=0; i<arrayProductos.size(); i++){
                consulta.executeUpdate( "insert into aguilas.det_compra(folio_c,codigo_prod,cantidad,subtotal_detalle) "
                    + "values('"+folio_compra+"','"+arrayDetalleCompra.get(i).getCodigoProducto()+"','"
                    + arrayDetalleCompra.get(i).getCantidad()+"','"+arrayDetalleCompra.get(i).getSubtotal()+"')");
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Error: detalle compra " + e.getMessage());
        }  
        limpiarCamposCompra();
        String fech= fecha.toString();
         Compra compra = new Compra(folio_compra, rfc_prov, fech, ""+id_empleado, ""+total_compra);
            
        listaCompra.add(compra);
        
        msg_exitoso.msgExitoso("Compra registrada");
        
        //listaProducto.clear();
        ((Node)  (event.getSource())).getScene().getWindow().hide();
          
    }

    @FXML
    private void cerrar(MouseEvent event) {
         ((Node)  (event.getSource())).getScene().getWindow().hide();
    }

    @FXML
    private void agregarProducto(MouseEvent event) {
        
        if(validarEntradasProductos()==false){   // validar datos de entrada
            msgErr.msgError("Campo(s) incorrectos");
            return; 
        }
        inicializaVariables();
        double subtotal = precio_c * existencias;
        total_compra += subtotal;
        lblTotalaPagar.setText(""+total_compra);
        Producto producto = new Producto(codigo, descripcion,""+precio_c, ""+precio_v, ""+existencias,""+subtotal);
        DetalleCompra dCompra = new DetalleCompra(codigo, existencias, subtotal);
        
        arrayProductos.add(producto);
        arrayDetalleCompra.add(dCompra);
        
        listaProducto.add(producto);
        limpiarCamposProducto();
    }
    
    public void inicializaVariables(){
        codigo = txtcodigoProducto.getText().trim().toUpperCase();
        descripcion = txtDescripcionProducto.getText().trim(); 
        precio_c = Double.parseDouble(txtPrecioC.getText().trim()); 
        precio_v = Double.parseDouble(txtPrecioV.getText().trim());
        existencias = Integer.parseInt(txtExistencias.getText().trim());
        rfc_prov = txtRFCProv.getText().trim();   
    }
    
    @FXML
    private void eliminarProducto(MouseEvent event) {
        ObservableList<Producto> productoSelected;
        productoSelected = tablaProductos.getSelectionModel().getSelectedItems();
        Producto producto = tablaProductos.getSelectionModel().getSelectedItem();
        
        if(productoSelected.isEmpty()){
            msgErr.msgError("No se ha seleccionado  \n el elemento a eliminar");
            return;
        }
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("confirmacion");
        alert.setContentText("¿QUIERES ELIMINAR EL REGISTRO? \nProducto \n id_prod: " 
                            + producto.getCodigo()+ "\t\t Descripcion: " + producto.getDescripcion()
                            + "\n Existencias: " + producto.getExistencias() +"\t\t Precio " + producto.getPrecio_venta());
            
            
            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == ButtonType.OK){        
                String codigo = producto.getCodigo();
                // eliminar del arrayList
                int i=0; 
                while(!arrayProductos.get(i).getCodigo().equalsIgnoreCase(codigo)){
                     i++;
                }
                arrayProductos.remove(i);
                arrayDetalleCompra.remove(i);
                
                total_compra -= Double.parseDouble(producto.getSubtotal());
                lblTotalaPagar.setText(""+total_compra);
                productoSelected.forEach(listaProducto::remove);      
            }  
    }

    @FXML
    private void editarProducto(MouseEvent event) {
        ObservableList<Producto> productoSelected;
        productoSelected  = tablaProductos.getSelectionModel().getSelectedItems();
        Producto producto = tablaProductos.getSelectionModel().getSelectedItem();
        
        if(productoSelected.isEmpty()){
            msgErr.msgError("No se ha seleccionado  \n el elemento a eliminar");
            return;
        }else{
            txtcodigoProducto.setText(producto.getCodigo());
            txtDescripcionProducto.setText(producto.getDescripcion());
            txtPrecioC.setText(producto.getPrecio_compra());
            txtPrecioV.setText(producto.getPrecio_venta());
            txtExistencias.setText(producto.getExistencias());
            
            int i=0; 
                while(!arrayProductos.get(i).getCodigo().equalsIgnoreCase(codigo)){
                     i++;
                }
                arrayProductos.remove(i);
                arrayDetalleCompra.remove(i);
                
                total_compra -= Double.parseDouble(producto.getSubtotal());
                lblTotalaPagar.setText(""+total_compra);
                productoSelected.forEach(listaProducto::remove);  
        }
    }


    @FXML
    private void filtrarProducto(KeyEvent event) {
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
            sortedData.comparatorProperty().bind(tablaProductos.comparatorProperty());
            tablaProductos.setItems(sortedData);
        }else{
            return;
        } 
    }

    @FXML
    private void validaFolio(KeyEvent event) {
        Pattern pattern =Pattern.compile("[#a-zA-Z0-9 ]{3,}"); 
        Matcher mather = pattern.matcher(txtFolioCompra.getText().trim());  
        if (mather.find() == true) 
            errorFolio.setVisible(false);
        else 
           errorFolio.setVisible(true); 
    }
    @FXML
    private void validaCodigo(KeyEvent event) {
        Pattern pattern =Pattern.compile("[#a-zA-Z0-9 ]{3,}"); 
        Matcher mather = pattern.matcher(txtcodigoProducto.getText().trim());  
        if (mather.find() == true) 
            errorCodigo.setVisible(false);
        else 
           errorCodigo.setVisible(true); 
    }

    @FXML
    private void validaDescripcion(KeyEvent event) {
        Pattern pattern =Pattern.compile("[#a-zA-ZñÑáéíóúÁÉÍÓÚ0-9 ]{3,}"); 
        Matcher mather = pattern.matcher(txtDescripcionProducto.getText().trim());  
        if (mather.find() == true) 
            errorDescripcion.setVisible(false);
        else 
           errorDescripcion.setVisible(true); 
    }

    @FXML
    private void validaPrecioC(KeyEvent event) {
        modelo.Delimitador.limitTextField(txtRFCProv, 15);
        char caracter = event.getCharacter().charAt(0);   
        if (((caracter < '0') || (caracter > '9')) 
        && (caracter != '.' || txtPrecioC.getText().contains(".")) ) {
            event.consume();
        }
    }

    @FXML
    private void validaPrecioV(KeyEvent event) {
        modelo.Delimitador.limitTextField(txtPrecioV, 15);
        char caracter = event.getCharacter().charAt(0);   
        if (((caracter < '0') || (caracter > '9')) 
        && (caracter != '.' || txtPrecioV.getText().contains(".")) ) {
            event.consume();
        }
    }
    
     public void lanzarVentana(String ruta_view_fxml){
         try{ 
            //URL url = Paths.get(ruta_view_fxml).toUri().toURL();
            //Parent root = FXMLLoader.load(url);
            Parent root = FXMLLoader.load(getClass().getResource(ruta_view_fxml));
            Stage stage= new Stage();
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            stage.show();
        }catch(IOException e){
             System.err.println("ERROR " + e.getMessage());
        }
    }
    
   private void cargarTablaProductos(){  
       listaProducto = FXCollections.observableArrayList();
        tablaProductos.setItems(listaProducto); 
        clmnCodigo.setCellValueFactory(cellData -> cellData.getValue().codigo());
        clmnDescripcion.setCellValueFactory(new PropertyValueFactory<Producto, String>("descripcion"));  
        clmnPrecioC.setCellValueFactory(cellData -> cellData.getValue().precio_compra());     
        clmnExistencias.setCellValueFactory(new PropertyValueFactory<Producto, String>("existencias")); 
        clmnTotal.setCellValueFactory(cellData -> cellData.getValue().subtotal()); 
        filteredDataProducto =new FilteredList<>(listaProducto,p->true);
        flagLoadProduct = true;
    }
    public boolean validarEntradasProductos(){
        if(txtcodigoProducto.getText().equals("") || txtDescripcionProducto.getText().equals("") || txtPrecioC.getText().equals("") ||
           txtPrecioV.getText().equals("") || txtExistencias.getText().equals("") || errorCodigo.isVisible() || 
           errorDescripcion.isVisible())
            return false;
        return true;
    } 
    
    public boolean validarEntradasCompra(){
        if(txtFolioCompra.getText().equals("") || txtRFCProv.getText().equals(""))
            return false;
         return true;
    }
    
    public void limpiarCamposProducto(){
        txtcodigoProducto.setText("");
        txtDescripcionProducto.setText("");
        txtPrecioC.setText("");
        txtPrecioV.setText("");
        txtExistencias.setText("");
    }
    
    public void limpiarCamposCompra(){
        txtFolioCompra.setText("");
        txtRFCProv.setText("");
        lblTotalaPagar.setText("00.00");           
    }

    
}
