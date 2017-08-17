/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package una.examen.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import una.examen.entity.Sugerencia;

/**
 *
 * @author michaelcw02
 */
public class SugerenciaDAO extends BaseDAO {
    
    public SugerenciaDAO() {
        super();
    }

    public boolean addSugerencia(Sugerencia sugerencia) {
        try {
            String query = "INSERT INTO `sugerencias`.`sugerencia` (`usuario`, `texto`, `puntaje`) VALUES (?, ?, ?);";
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, sugerencia.getUsuario().getId());
            pstmt.setString(2, sugerencia.getTexto());
            pstmt.setInt(3, sugerencia.getPuntaje());
            int result = pstmt.executeUpdate();
            return (result == 1) ? true : false;
        } catch (SQLException ex) {
            System.out.println("An exception have occurred in sugerencia dao");
            System.out.println(ex);
            return false;
        }
    }
    
    public List<Sugerencia> findByUserNTextoLike(String username, String texto) {
        LinkedList<Sugerencia> list = new LinkedList<>();
        try {
            String query = "SELECT * FROM sugerencias.SUGERENCIA, sugerencias.USUARIO WHERE SUGERENCIA.USUARIO = USUARIO.ID AND USUARIO = ? AND TEXTO LIKE ?;";
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, username);
            pstmt.setString(2, "%" + texto + "%");
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()) {
                list.add(sugerencia(rs));
            }
            return list;
        } catch (SQLException ex) {
            System.out.println("An exception have occurred in sugerencia dao");
            System.out.println(ex);
            return null;
        }
    }
}
