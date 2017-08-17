/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package una.examen.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import una.examen.entity.Usuario;

/**
 *
 * @author michaelcw02
 */
public class UsuarioDAO extends BaseDAO {

    public UsuarioDAO() {
        super();
    }
    
    public Usuario findByID(String username) {
        try {
            String query = "SELECT * FROM USUARIO WHERE ID = ?;";
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()) {
                return usuario(rs);
            }
        } catch (SQLException ex) {
            System.out.println("An exception have occurred in usuario dao");
            System.out.println(ex);
            return null;
        }
        return null;
    }

    public Usuario findByIdNPass(String username, String password) {
        try {
            String query = "SELECT * FROM USUARIO WHERE ID = ? AND CLAVE = ?;";
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()) {
                return usuario(rs);
            }
        } catch (SQLException ex) {
            System.out.println("An exception have occurred in usuario dao");
            System.out.println(ex);
            return null;
        }
        return null;
    }
    
    
    
}
