/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frontend.desktop;

import frontend.desktop.control.Control;
import frontend.desktop.vista.VentanaTabla;

/**
 *
 * @author luisf
 */
public class FrontEndDESKTOP {

    
    public static void main(String[] args) {
       Control control = new Control();
       VentanaTabla ventana = new VentanaTabla(control);
       ventana.iniciar();
    }
    
}
