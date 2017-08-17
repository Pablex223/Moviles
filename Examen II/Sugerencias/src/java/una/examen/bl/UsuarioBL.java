/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package una.examen.bl;

import una.examen.dao.UsuarioDAO;
import una.examen.entity.Usuario;

/**
 *
 * @author michaelcw02
 */
public class UsuarioBL {
    UsuarioDAO usuarioDAO;

    public UsuarioBL() {
        this.usuarioDAO = new UsuarioDAO();
    }
    
    public Usuario findByID(String username) {
        return this.usuarioDAO.findByID(username);
    }
    
    public Usuario findByIdNPass(String username, String password) {
        return this.usuarioDAO.findByIdNPass(username, password);
    }
}
