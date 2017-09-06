/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AccesoDatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * 
 */
public class ConexionBD {
    
    Connection conexion = null;
    Statement comando = null;
    ResultSet registro;
    private String baseDatos = "matricula";
    private static final String MANEJADOR_DB = "com.mysql.jdbc.Driver";
    private static final String PROTOCOLO = "jdbc:mysql:";
    private static final String SERVIDOR_POR_DEFECTO = "localhost";
    String usuario = "root";
    String claveAcceso = "1234";
    
    public Connection Connect() {
           
            String URL_conexion = String.format("%s//%s/%s",PROTOCOLO, SERVIDOR_POR_DEFECTO, baseDatos);
            System.out.println(String.format("Hilera de conexión: %s", URL_conexion));
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conexion = DriverManager.getConnection(URL_conexion, usuario, claveAcceso);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex, "Error en la conexión a la base de datos: " + ex.getMessage(), JOptionPane.ERROR_MESSAGE);
            conexion = null;
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex, "Error en la conexión a la base de datos: " + ex.getMessage(), JOptionPane.ERROR_MESSAGE);
            conexion = null;
        } finally {
           // JOptionPane.showMessageDialog(null, "Conexión Exitosa");
            return conexion;
        }
    }
    public void closeCon(){
        try {
            if(registro!= null){ registro.close();}
            if(comando!=null){comando.close();}
            conexion.close();
        } catch (SQLException ex) {
            Logger.getLogger(ConexionBD.class.getName()).log(Level.SEVERE, null, ex);
        }            
    }
}
    

