/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlet;

import Modelo.Persona;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jpa.PersonaJpaController;
import jpa.exceptions.PreexistingEntityException;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author luisf
 */
@WebServlet(name = "as", urlPatterns = {"/as"})
public class as extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {        
        try {
            JSONObject jo = new JSONObject(request.getReader().lines().reduce("",String::concat));
            
            String act;
            if(Objects.nonNull(act = jo.getString("action"))){
                switch(act){
                    case "login": loginAct(response, jo.getJSONObject("data")); break;
                    case "nuevoUsuario": nuevoUsuarioAct(response, jo.getJSONObject("data")) ;break;
                    default:  response.sendError(HttpServletResponse.SC_BAD_REQUEST, "The request sent by the client was syntactically incorrect.");
                }
            }
            
        }catch(JSONException ex) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
        } catch (Exception ex) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
        }
        
    }
    
    private void loginAct(HttpServletResponse response, JSONObject data) throws IOException{
       response.setContentType("application/json");
        PrintWriter pw = response.getWriter();
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("AutServletPU");
        try{
            Persona persona = new PersonaJpaController(emf).findPersona(data.getString("usuario"));
             pw.write( new JSONObject().put("login", persona.getCont().equals(data.get("contra"))).toString());
             
        }catch(JSONException ex){
             throw ex;
        }catch(Exception ex){
             pw.write( new JSONObject().put("login", false).toString());
        }
        pw.flush();             
    }
    
    private void nuevoUsuarioAct(HttpServletResponse response, JSONObject jsonObject) throws Exception {
        response.setContentType("application/json");
        PrintWriter pw = response.getWriter();
        try{
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("AutServletPU");
            new PersonaJpaController(emf).create(Persona.fromJson(jsonObject));
            pw.write( new JSONObject().put("success", "Usuario creado con exito.").toString());
        }catch(PreexistingEntityException ex){
            pw.write( new JSONObject().put("error", ex.getMessage()).toString());
        }
            pw.flush();             
        
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
