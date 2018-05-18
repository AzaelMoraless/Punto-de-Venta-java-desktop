/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistema;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

public class Validaciones {
    private Pattern patron;
    private Matcher match; 
    public boolean validarDatosProveedor(TextField rs,TextField tel,TextField direccion,TextField email){    
        patron = Pattern.compile("[a-zA-ZñÑáéíóúÁÉÍÓÚ0-9 ]{3,}"); match = patron.matcher(rs.getText());  
        if(muestra_validacion(rs,"No es una razon social") )
            return true;  
        patron = Pattern.compile("([0-9]{3})-([0-9]{3})-([0-9]{4})");  match = patron.matcher(tel.getText().trim()); 
        if(muestra_validacion(tel,"No es un telefono") )
            return true;    
        patron = Pattern.compile("[a-zA-ZñÑáéíóúÁÉÍÓÚ0-9 ]{3,}"); match = patron.matcher(direccion.getText().trim()); 
        if(muestra_validacion(direccion,"No es una direccion") )
            return true; 
        patron = Pattern.compile("([a-z0-9_]{3,10})(@)([a-z]{5,})(\\.[a-z]{2,})$"); match = patron.matcher(email.getText().trim());   
        if(muestra_validacion(email,"No es un email") )
            return true;     
        return false;
    }  
    public static boolean validarCorreo(String email){
        Pattern pattern = Pattern
                .compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher mather = pattern.matcher(email);
        if (mather.find() == true) {
            return true;
        } else {
            return false;
        } 
    }
    public boolean muestra_validacion(TextField tx,String texto){
        if(match.matches()){
        }else {
          tx.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
          tx.setPromptText(texto);
          tx.setText("");
         return true;
        }  
        return false;
    }
    
}
