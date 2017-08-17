/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package una.examen.controller;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import una.examen.bl.SugerenciaBL;
import una.examen.entity.Sugerencia;
import una.examen.entity.Usuario;

/**
 *
 * @author michaelcw02
 */
public class SugerenciaServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            String json;

            SugerenciaBL sugerenciaBL = new SugerenciaBL();
            Sugerencia sugerencia = new Sugerencia();
            HttpSession session = request.getSession();

            String action = request.getParameter("action");
            switch (action) {
                case "searchText":
                    Usuario usuario         = (Usuario) session.getAttribute("usuario");
                    String  texto           = request.getParameter("texto");
                    List<Sugerencia> list   = sugerenciaBL.findByUserNTextoLike(usuario.getId(), texto);
                    if(list != null)   json = new Gson().toJson(list);
                    else               json = "{\"data\":\"E~No text\"}";
                    out.print(json);
                    break;
                case "addSugerencia":
                    usuario     = (Usuario) session.getAttribute("usuario");
                    texto       = request.getParameter("texto");
                    sugerencia  = new Sugerencia(usuario, texto);
                    boolean r   = sugerenciaBL.addSugerencia(sugerencia);
                    json = (r) ? "{\"data\":\"C~Success\"}" : "{\"data\":\"E~Failure\"}";
                    out.print(json);
                    break;
                default:
                    out.print("E~No se indico la acción que se desea realizar");
                    break;
            }

        } catch (NumberFormatException e) {
            out.print("E~" + e.getMessage());
        } catch (Exception e) {
            out.print("E~" + e.getMessage());
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
