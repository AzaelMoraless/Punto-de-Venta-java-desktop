/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Azael
 */
public class DetalleCompra {
    private final StringProperty propertyCodigo_producto;
    private final IntegerProperty propertyCantidad;
    private final DoubleProperty propertySubtotal;
    
    public DetalleCompra(String codigo_producto,int cantidad,double subtotal){
        this.propertyCodigo_producto = new SimpleStringProperty(codigo_producto);
        this.propertyCantidad = new SimpleIntegerProperty(cantidad);
        this.propertySubtotal = new SimpleDoubleProperty(subtotal);
    }
    
    public StringProperty getPropertyCodigo_producto() {//property 
        return propertyCodigo_producto;
    }
    
    public String getCodigoProducto(){
        return propertyCodigo_producto.get();
    }
    public IntegerProperty getPropertyCantidad() {//property 
        return propertyCantidad;
    }
    
    public int getCantidad(){
        return propertyCantidad.get();
    }
    
    public DoubleProperty getPropertyCubtotal() {//property 
        return propertySubtotal;
    }
    
    public double getSubtotal(){
        return propertySubtotal.get();
    }
    
   
    
}
