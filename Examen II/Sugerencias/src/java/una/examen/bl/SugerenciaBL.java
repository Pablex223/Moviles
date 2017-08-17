/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package una.examen.bl;

import java.util.List;
import una.examen.dao.SugerenciaDAO;
import una.examen.entity.Sugerencia;

/**
 *
 * @author michaelcw02
 */
public class SugerenciaBL {
    
    SugerenciaDAO sugerenciaDAO;

    public SugerenciaBL() {
        sugerenciaDAO = new SugerenciaDAO();
    }
    
    public boolean addSugerencia(Sugerencia s) {
        return this.sugerenciaDAO.addSugerencia(s);
    }
    
    public List<Sugerencia> findByUserNTextoLike(String username, String texto) {
        return this.sugerenciaDAO.findByUserNTextoLike(username, texto);
    }    
}
