import BO.CourseBO;
import BO.StudentBO;
import Model.Course;
import Model.Student;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author luisf69v@gmail.com
 */
@WebServlet(name = "rgsServlet", urlPatterns = {"/rgs2"})
public final class Servlet extends HttpServlet {

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
            String act;
            if((act = request.getParameter("action")) != null){
                switch(act){
                    case "studentsList":    this.getStudentsList(response)        ;break;
                    case "courseList"  :    this.getCourseList  (response)        ;break;
                    case "saveStudent" :    this.saveStudent(request, response)   ;break;
                    case "saveCourse"  :    this.saveCourse (request, response)   ;break;
                    
                    default:  response.sendError(HttpServletResponse.SC_BAD_REQUEST, "The request sent by the client was syntactically incorrect.");
                }
            }
            
        }catch(JSONException | SQLException | ClassNotFoundException ex) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
        }
    }
     
    
    private void getStudentsList(HttpServletResponse response) 
            throws IOException, SQLException, ClassNotFoundException {
        try( PrintWriter pw = response.getWriter() )
        {
            
            response.setContentType("text/html; charset=utf-8");
            
            StringBuilder sb = new  StringBuilder();
            new StudentBO().getStudentList().stream().forEach(e -> 
                    sb
                    .append(e.getId())
                    .append("/")
                    .append(e.getName())
                    .append("/")
                    .append(e.getLastN1())
                    .append("/")
                    .append(e.getLastN2())
                    .append("/")
                    .append(e.getAge())
                    .append(";")
            );
            pw.write( sb.toString() );
            pw.flush();            
            
            
        }
    }
    
    private void getCourseList(HttpServletResponse response) 
            throws IOException, SQLException, ClassNotFoundException {
        try( PrintWriter pw = response.getWriter() )
        {
            JSONArray array = new JSONArray();
            response.setContentType("text/html; charset=utf-8");
            StringBuilder sb = new  StringBuilder();
            
            new CourseBO().getCourseList().stream().forEach(c ->  sb
                    .append(c.getId())                   
                    .append("/")
                    .append(c.getName())
                    .append("/")
                    .append(c.getDescr())
                    .append("/")
                    .append(c.getCredits())
                    .append(";")
            );
            pw.write( sb.toString() );
            pw.flush();            
        }
    }
    
    private void saveStudent(HttpServletRequest request, HttpServletResponse response) 
            throws IOException, SQLException, ClassNotFoundException {
            try{
                PrintWriter pw = response.getWriter();
                new StudentBO()
                        .saveStudent(
                                new Student(
                                        request.getParameter("id"),
                                        request.getParameter("name"),
                                        request.getParameter("lastN1"), 
                                        request.getParameter("lastN2"),
                                        Integer.valueOf(request.getParameter("age"))
                                )
                        );
                response.setContentType("application/json");
                pw.write("Student created with success.");
                pw.flush();                
            } catch (SQLException ex) {
                if(ex.getErrorCode() == 1062){
                    PrintWriter pw = response.getWriter();
                    pw.write("Error: Student with id:".concat(request.getParameter("id")).concat(" already exists."));
                    pw.flush();                
                }else{
                    throw ex;
                }                
            }
    }
    
    private void saveCourse(HttpServletRequest request, HttpServletResponse response) 
            throws IOException, SQLException, ClassNotFoundException {
            try{
                PrintWriter pw = response.getWriter();
                new CourseBO()
                        .saveCourse(
                                new Course(
                                        Integer.valueOf(request.getParameter("id")),
                                        request.getParameter("cName"),
                                        request.getParameter("descr"), 
                                        Integer.valueOf(request.getParameter("credits"))
                                )
                        );
                response.setContentType("application/json");
                pw.write("{\"msg\": \"Course created with success.\"}");
                pw.flush();                
            } catch (SQLException ex) {
                throw ex;                              
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
