package AccesoDatos;

import Modelo.*;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JPanel;
import Modelo.*;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AccesoDB {

    public void validaUser(Persona a, String us, String pass) {
        try {
            ConexionBD bd = new ConexionBD();
            bd.Connect();
            bd.comando = bd.conexion.createStatement();
            String comandoListar = "SELECT * FROM persona "
                    + "WHERE CEDULA =" + us
                    + " And PASS = " + pass;
            bd.registro = bd.comando.executeQuery(comandoListar);
            while (bd.registro.next()) {
                a.setCedula(bd.registro.getString("cedula"));
                a.setClave(bd.registro.getString("pass"));
                a.setEmail(bd.registro.getString("correo"));
                a.setNombre(bd.registro.getString("nombre"));
                a.setTelefono(bd.registro.getInt("telefono"));
                a.setTipo(bd.registro.getInt("tipo"));
                if (a instanceof Alumno) {
                    ((Alumno) a).setF_nac("F_NACIMIENTO");
                }
            }

            bd.closeCon();

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
     public void getTodosAlumnos(ConjuntoPersonas resultado){
        try {
             ConexionBD bd = new ConexionBD();
            bd.Connect();
            bd.comando = bd.conexion.createStatement();
            String comandoListar = "select * from PERSONA";
            bd.registro = bd.comando.executeQuery(comandoListar);
            while (bd.registro.next()) {
                resultado.agregar(alumno( bd.registro));
            }
        } catch (SQLException ex) { System.out.print("error personas"); }

       }
      private Alumno alumno(ResultSet rs){
        try {
            Alumno a= new Alumno();
           a.setCedula(rs.getString("cedula"));
                a.setClave(rs.getString("pass"));
                a.setEmail(rs.getString("correo"));
                a.setNombre(rs.getString("nombre"));
                a.setTelefono(rs.getInt("telefono"));
                a.setTipo(rs.getInt("tipo"));
                //if(a instanceof Alumno)
                a.setF_nac(rs.getString("F_NACIMIENTO"));
                a.setCarrera(rs.getString("carrera"));
            return a;
        } catch (SQLException ex) {
            System.out.print("error ciudad s");
            return null;
        }
    }
    //<editor-fold desc="Metodos de Busqueda">
    public void BuscarCursoCod(Curso a, String cod) {
        try {
            ConexionBD bd = new ConexionBD();
            bd.Connect();
            bd.comando = bd.conexion.createStatement();
            String comandoListar = "SELECT * FROM curso WHERE codigo='" + cod + "'";
            bd.registro = bd.comando.executeQuery(comandoListar);
            while (bd.registro.next()) {
                a.setCodigo(bd.registro.getString("codigo"));
                a.setNombre(bd.registro.getString("nombre"));
                a.setCreditos(bd.registro.getInt("creditos"));
                a.setHsemanales(bd.registro.getInt("H_SEMANALES"));
                a.setCarrera(bd.registro.getString("COD_CARRERA"));
                a.setNum_ciclo(bd.registro.getInt("NUM_CICLO"));
            }
            bd.closeCon();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void condicion(String est, String cur, Nota n) {
        try {
            ConexionBD bd = new ConexionBD();
            bd.Connect();
            bd.comando = bd.conexion.createStatement();
            String comandoListar = "SELECT * FROM nota WHERE Estudiante='" + est + "' "
                    + "And curso='" + cur + "'";
            bd.registro = bd.comando.executeQuery(comandoListar);
            while (bd.registro.next()) {
                n.setCURSO(bd.registro.getString("curso"));
                n.setCondision(bd.registro.getString("condicion"));
                n.setESTUDIANTE(bd.registro.getString("estudiante"));
                n.setGrupo(bd.registro.getString("Grupo"));
                n.setNOTA(bd.registro.getFloat("nota"));

            }
            bd.closeCon();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void Cursoencurso(String est, String cur, Nota n) {
        try {
            ConexionBD bd = new ConexionBD();
            bd.Connect();
            bd.comando = bd.conexion.createStatement();
            String comandoListar = "SELECT * FROM nota WHERE Estudiante='" + est + "' "
                    + "And curso='" + cur + "' and CONDICION ='encurso'";
            bd.registro = bd.comando.executeQuery(comandoListar);
            while (bd.registro.next()) {
                n.setCURSO(bd.registro.getString("curso"));
                n.setCondision(bd.registro.getString("condicion"));
                n.setESTUDIANTE(bd.registro.getString("estudiante"));
                n.setGrupo(bd.registro.getString("Grupo"));
                n.setNOTA(bd.registro.getFloat("nota"));

            }
            bd.closeCon();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void BuscarCursoCar2(Curso a, String car, String cod) {
        try {
            ConexionBD bd = new ConexionBD();
            bd.Connect();
            bd.comando = bd.conexion.createStatement();
            String comandoListar = "SELECT * FROM curso WHERE codigo='" + cod + "' "
                    + "And COD_CARRERA='" + car + "'";
            bd.registro = bd.comando.executeQuery(comandoListar);
            while (bd.registro.next()) {
                a.setCodigo(bd.registro.getString("codigo"));
                a.setNombre(bd.registro.getString("nombre"));
                a.setCreditos(bd.registro.getInt("creditos"));
                a.setHsemanales(bd.registro.getInt("H_SEMANALES"));
                a.setCarrera(bd.registro.getString("COD_CARRERA"));
                a.setNum_ciclo(bd.registro.getInt("NUM_CICLO"));
            }
            bd.closeCon();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void BuscarCursoNom(Curso a, String nom) {
        try {
            ConexionBD bd = new ConexionBD();
            bd.Connect();
            bd.comando = bd.conexion.createStatement();
            String comandoListar = "SELECT * FROM curso WHERE nombre ='" + nom + "'";
            bd.registro = bd.comando.executeQuery(comandoListar);
            while (bd.registro.next()) {
                a.setCodigo(bd.registro.getString("codigo"));
                a.setNombre(bd.registro.getString("nombre"));
                a.setCreditos(bd.registro.getInt("creditos"));
                a.setHsemanales(bd.registro.getInt("H_SEMANALES"));
                a.setCarrera(bd.registro.getString("COD_CARRERA"));
                a.setNum_ciclo(bd.registro.getInt("NUM_CICLO"));
            }
            bd.closeCon();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void BuscarCursoCar(ArrayList<Curso> l, String carr) {
        try {
            ConexionBD bd = new ConexionBD();
            bd.Connect();
            bd.comando = bd.conexion.createStatement();
            String comandoListar = "SELECT * FROM curso WHERE COD_CARRERA ='" + carr + "'";
            bd.registro = bd.comando.executeQuery(comandoListar);
            while (bd.registro.next()) {
                Curso a = new Curso();
                a.setCodigo(bd.registro.getString("codigo"));
                a.setNombre(bd.registro.getString("nombre"));
                a.setCreditos(bd.registro.getInt("creditos"));
                a.setHsemanales(bd.registro.getInt("H_SEMANALES"));
                a.setCarrera(bd.registro.getString("COD_CARRERA"));
                a.setNum_ciclo(bd.registro.getInt("NUM_CICLO"));
                l.add(a);
            }
            bd.closeCon();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
    public void BuscarCursoCar(ArrayList<Curso> l, String carr, String clc) {
        try {
            ConexionBD bd = new ConexionBD();
            bd.Connect();
            bd.comando = bd.conexion.createStatement();
            String comandoListar = "SELECT * FROM curso WHERE COD_CARRERA ='" + carr + "' AND NUM_CICLO='"+clc+"'";
            bd.registro = bd.comando.executeQuery(comandoListar);
            while (bd.registro.next()) {
                Curso a = new Curso();
                a.setCodigo(bd.registro.getString("codigo"));
                a.setNombre(bd.registro.getString("nombre"));
                a.setCreditos(bd.registro.getInt("creditos"));
                a.setHsemanales(bd.registro.getInt("H_SEMANALES"));
                a.setCarrera(bd.registro.getString("COD_CARRERA"));
                a.setNum_ciclo(bd.registro.getInt("NUM_CICLO"));
                l.add(a);
            }
            bd.closeCon();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void BuscarCursosCar(Curso x, String carrera, ArrayList<Curso> l) {
        try {
            ConexionBD bd = new ConexionBD();
            bd.Connect();
            bd.comando = bd.conexion.createStatement();
            String comandoListar = "SELECT * FROM curso WHERE COD_CARRERA ='" + carrera + "'";
            bd.registro = bd.comando.executeQuery(comandoListar);
            while (bd.registro.next()) {
                Curso a = new Curso();
                a.setNum_ciclo(bd.registro.getInt("Num_ciclo"));
                a.setNombre(bd.registro.getString("nombre"));
                a.setCreditos(bd.registro.getInt("creditos"));
                a.setHsemanales(bd.registro.getInt("H_SEMANALES"));
                a.setCodigo(bd.registro.getString("codigo"));
                a.setNum_ciclo(bd.registro.getInt("NUM_CICLO"));
                l.add(a);
            }
            bd.closeCon();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void BuscarCarreraCod(Carrera a, String cod) {
        try {
            ConexionBD bd = new ConexionBD();
            bd.Connect();
            bd.comando = bd.conexion.createStatement();
            String comandoListar = "SELECT * FROM carrera WHERE codigo ='" + cod + "'";
            bd.registro = bd.comando.executeQuery(comandoListar);
            while (bd.registro.next()) {
                a.setCodigo(bd.registro.getString("codigo"));
                a.setNombre(bd.registro.getString("nombre"));
            }
            bd.closeCon();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void BuscarCarreraNom(Carrera a, String nom) {
        try {
            ConexionBD bd = new ConexionBD();
            bd.Connect();
            bd.comando = bd.conexion.createStatement();
            String comandoListar = "SELECT * FROM carrera WHERE nombre ='" + nom + "'";
            bd.registro = bd.comando.executeQuery(comandoListar);
            while (bd.registro.next()) {
                a.setCodigo(bd.registro.getString("codigo"));
                a.setNombre(bd.registro.getString("nombre"));
            }
            bd.closeCon();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void BuscarEStId(Persona a, String id) {
        try {
            ConexionBD bd = new ConexionBD();
            bd.Connect();
            bd.comando = bd.conexion.createStatement();
            String comandoListar = "SELECT * FROM persona WHERE CEDULA='" + id + "' And TIPO = 4";
            bd.registro = bd.comando.executeQuery(comandoListar);

            while (bd.registro.next()) {
                a.setCedula(bd.registro.getString("cedula"));
                a.setClave(bd.registro.getString("pass"));
                a.setEmail(bd.registro.getString("correo"));
                a.setNombre(bd.registro.getString("nombre"));
                a.setTelefono(bd.registro.getInt("telefono"));
                a.setTipo(bd.registro.getInt("tipo"));
                //if(a instanceof Alumno)
                ((Alumno) a).setF_nac("F_NACIMIENTO");
                ((Alumno) a).setCarrera(bd.registro.getString("carrera"));

            }
            bd.closeCon();

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void BuscarEstNom(String nom, ArrayList<Alumno> l) {
        try {
            ConexionBD bd = new ConexionBD();
            bd.Connect();
            bd.comando = bd.conexion.createStatement();
            String comandoListar = "SELECT * FROM persona WHERE nombre='" + nom + "' And TIPO = 4";
            bd.registro = bd.comando.executeQuery(comandoListar);
            while (bd.registro.next()) {
                Alumno a = new Alumno();
                a.setCedula(bd.registro.getString("cedula"));
                a.setClave(bd.registro.getString("pass"));
                a.setEmail(bd.registro.getString("correo"));
                a.setNombre(bd.registro.getString("nombre"));
                a.setTelefono(bd.registro.getInt("telefono"));
                a.setTipo(bd.registro.getInt("tipo"));
                a.setF_nac(bd.registro.getString("F_NACIMIENTO"));
                a.setCarrera(bd.registro.getString("Carrera"));
                l.add(a);
            }
            bd.closeCon();

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void BuscarEstCarr(String carr, ArrayList<Alumno> l) {
        try {
            ConexionBD bd = new ConexionBD();
            bd.Connect();
            bd.comando = bd.conexion.createStatement();
            String comandoListar = "SELECT * FROM persona WHERE carrera='" + carr + "'";
            bd.registro = bd.comando.executeQuery(comandoListar);
            while (bd.registro.next()) {
                Alumno a = new Alumno();
                a.setCedula(bd.registro.getString("cedula"));
                a.setClave(bd.registro.getString("pass"));
                a.setEmail(bd.registro.getString("correo"));
                a.setNombre(bd.registro.getString("nombre"));
                a.setTelefono(bd.registro.getInt("telefono"));
                a.setTipo(bd.registro.getInt("tipo"));
                a.setF_nac(bd.registro.getString("F_NACIMIENTO"));
                a.setCarrera(bd.registro.getString("Carrera"));
                l.add(a);
            }
            bd.closeCon();

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void BuscarPrfId(Persona a, String id) {
        try {
            ConexionBD bd = new ConexionBD();
            bd.Connect();
            bd.comando = bd.conexion.createStatement();
            String comandoListar = "SELECT * FROM persona WHERE CEDULA='" + id + "' And TIPO = 3";
            bd.registro = bd.comando.executeQuery(comandoListar);

            while (bd.registro.next()) {
                a.setCedula(bd.registro.getString("cedula"));
                a.setClave(bd.registro.getString("pass"));
                a.setEmail(bd.registro.getString("correo"));
                a.setNombre(bd.registro.getString("nombre"));
                a.setTelefono(bd.registro.getInt("telefono"));
                a.setTipo(bd.registro.getInt("tipo"));
                if (a instanceof Alumno) {
                    ((Alumno) a).setF_nac("F_NACIMIENTO");
                }
            }
            bd.closeCon();

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void BuscarPersona(Persona a, String ced) {
        try {
            ConexionBD bd = new ConexionBD();
            bd.Connect();
            bd.comando = bd.conexion.createStatement();
            String comandoListar = "SELECT * FROM persona WHERE cedula='" + ced;
            bd.registro = bd.comando.executeQuery(comandoListar);
            while (bd.registro.next()) {

                a.setCedula(bd.registro.getString("cedula"));
                a.setClave(bd.registro.getString("pass"));
                a.setEmail(bd.registro.getString("correo"));
                a.setNombre(bd.registro.getString("nombre"));
                a.setTelefono(bd.registro.getInt("telefono"));
                a.setTipo(bd.registro.getInt("tipo"));

            }
            bd.closeCon();

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

    }

    public void BuscarPrfNombre(String nom, ArrayList<Profesor> l) {
        try {
            ConexionBD bd = new ConexionBD();
            bd.Connect();
            bd.comando = bd.conexion.createStatement();
            String comandoListar = "SELECT * FROM persona WHERE nombre='" + nom + "' And TIPO = 3";
            bd.registro = bd.comando.executeQuery(comandoListar);
            while (bd.registro.next()) {
                Profesor a = new Profesor();
                a.setCedula(bd.registro.getString("cedula"));
                a.setClave(bd.registro.getString("pass"));
                a.setEmail(bd.registro.getString("correo"));
                a.setNombre(bd.registro.getString("nombre"));
                a.setTelefono(bd.registro.getInt("telefono"));
                a.setTipo(bd.registro.getInt("tipo"));
                l.add(a);
            }
            bd.closeCon();

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void BuscarAdmId(Persona a, String id) {
        try {
            ConexionBD bd = new ConexionBD();
            bd.Connect();
            bd.comando = bd.conexion.createStatement();
            String comandoListar = "SELECT * FROM persona WHERE CEDULA='" + id + "' And TIPO = 1";
            bd.registro = bd.comando.executeQuery(comandoListar);

            while (bd.registro.next()) {
                a.setCedula(bd.registro.getString("cedula"));
                a.setClave(bd.registro.getString("pass"));
                a.setEmail(bd.registro.getString("correo"));
                a.setNombre(bd.registro.getString("nombre"));
                a.setTelefono(bd.registro.getInt("telefono"));
                a.setTipo(bd.registro.getInt("tipo"));
            }
            bd.closeCon();

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void BuscarMtr(Persona a, String id) {
        try {
            ConexionBD bd = new ConexionBD();
            bd.Connect();
            bd.comando = bd.conexion.createStatement();
            String comandoListar = "SELECT * FROM persona WHERE CEDULA='" + id + "' And TIPO = 2";
            bd.registro = bd.comando.executeQuery(comandoListar);

            while (bd.registro.next()) {
                a.setCedula(bd.registro.getString("cedula"));
                a.setClave(bd.registro.getString("pass"));
                a.setEmail(bd.registro.getString("correo"));
                a.setNombre(bd.registro.getString("nombre"));
                a.setTelefono(bd.registro.getInt("telefono"));
                a.setTipo(bd.registro.getInt("tipo"));
            }
            bd.closeCon();

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void mostrar(Persona a, ArrayList<Persona> l) {
        try {
            ConexionBD bd = new ConexionBD();
            bd.Connect();
            bd.comando = bd.conexion.createStatement();
            String comandoListar = "SELECT * FROM persona";
            bd.registro = bd.comando.executeQuery(comandoListar);
            while (bd.registro.next()) {
                a.setCedula(bd.registro.getString("cedula"));
                a.setClave(bd.registro.getString("pass"));
                a.setEmail(bd.registro.getString("correo"));
                a.setNombre(bd.registro.getString("nombre"));
                a.setTelefono(bd.registro.getInt("telefono"));
                a.setTipo(bd.registro.getInt("tipo"));
                l.add(a);
            }
            bd.closeCon();

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void mostrarCAs(ArrayList<Carrera> l) {
        try {
            ConexionBD bd = new ConexionBD();
            bd.Connect();
            bd.comando = bd.conexion.createStatement();
            String comandoListar = "SELECT * FROM carrera";
            bd.registro = bd.comando.executeQuery(comandoListar);
            while (bd.registro.next()) {
                Carrera a = new Carrera();
                a.setCodigo(bd.registro.getString("CODIGO"));
                a.setNombre(bd.registro.getString("NOMBRE"));
                l.add(a);
            }
            bd.closeCon();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void mostrar(Ciclo a, ArrayList<Ciclo> l) {
        try {
            ConexionBD bd = new ConexionBD();
            bd.Connect();
            bd.comando = bd.conexion.createStatement();
            String comandoListar = "SELECT * FROM ciclo";
            bd.registro = bd.comando.executeQuery(comandoListar);
            while (bd.registro.next()) {
                a.setAnno(bd.registro.getInt("anno"));
                a.setFfinal(bd.registro.getString("fecha_final"));
                a.setFinicio(bd.registro.getString("fecha_inicio"));
                a.setId(bd.registro.getString("id"));
                a.setNciclo(bd.registro.getInt("num_ciclo"));
                l.add(a);
            }
            bd.closeCon();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void mostrarCUs(ArrayList<Curso> l, String cod) {
        try {
            ConexionBD bd = new ConexionBD();
            bd.Connect();
            bd.comando = bd.conexion.createStatement();
            String comandoListar = "SELECT * FROM curso";
            bd.registro = bd.comando.executeQuery(comandoListar);
            while (bd.registro.next()) {
                Curso a = new Curso();
                a.setCodigo(bd.registro.getString("codigo"));
                a.setNombre(bd.registro.getString("nombre"));
                a.setCreditos(bd.registro.getInt("creditos"));
                a.setHsemanales(bd.registro.getInt("h_semanales"));
                a.setCarrera(bd.registro.getString("COD_CARRERA"));
                a.setNum_ciclo(bd.registro.getInt("num_ciclo"));
                l.add(a);
            }
            bd.closeCon();

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void mostrar(Grupo a, ArrayList<Grupo> l) {
        try {
            ConexionBD bd = new ConexionBD();
            bd.Connect();
            bd.comando = bd.conexion.createStatement();
            String comandoListar = "SELECT * FROM grupo";
            bd.registro = bd.comando.executeQuery(comandoListar);
            while (bd.registro.next()) {
                a.setId(bd.registro.getString("id_grupo"));
                a.setNumero(bd.registro.getInt("numero"));
                a.setProfesor(bd.registro.getString("id_prof"));
                a.setCurso(bd.registro.getString("cod_curso"));
                l.add(a);
            }
            bd.closeCon();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void Buscar(Persona a, String id) {
        try {
            ConexionBD bd = new ConexionBD();
            bd.Connect();
            bd.comando = bd.conexion.createStatement();
            String comandoListar = "SELECT * FROM persona WHERE CEDULA =" + id;
            bd.registro = bd.comando.executeQuery(comandoListar);
            while (bd.registro.next()) {
                a.setCedula(bd.registro.getString("cedula"));
                a.setClave(bd.registro.getString("pass"));
                a.setEmail(bd.registro.getString("correo"));
                a.setNombre(bd.registro.getString("nombre"));
                a.setTelefono(bd.registro.getInt("telefono"));
                a.setTipo(bd.registro.getInt("tipo"));
                if (a instanceof Alumno) {
                    ((Alumno) a).setF_nac(bd.registro.getString("F_NACIMIENTO"));
                    ((Alumno) a).setCarrera(bd.registro.getString("Carrera"));
                    
                }
            }
            bd.closeCon();

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void Buscar(Carrera a, String cod) {
        try {
            ConexionBD bd = new ConexionBD();
            bd.Connect();
            bd.comando = bd.conexion.createStatement();
            String comandoListar = "SELECT * FROM carrera WHERE codigo ='" + cod + "'";
            bd.registro = bd.comando.executeQuery(comandoListar);
            while (bd.registro.next()) {
                a.setCodigo(bd.registro.getString("codigo"));
                a.setNombre(bd.registro.getString("nombre"));
            }
            bd.closeCon();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void Buscar(Grupo a, String cod, int num) {
        try {
            ConexionBD bd = new ConexionBD();
            bd.Connect();
            bd.comando = bd.conexion.createStatement();
            String comandoListar = "SELECT * FROM grupo WHERE ID_GRUPO = '" + cod
                    + "' && From grupo where NUMERO= '" + num + "'";
            bd.registro = bd.comando.executeQuery(comandoListar);
            while (bd.registro.next()) {
                a.setId(bd.registro.getString("ID_GRUPO"));
                a.setNumero(bd.registro.getInt("NUMERO"));
                a.setProfesor(bd.registro.getString("ID_PROF"));
                a.setCurso(bd.registro.getString("COD_CURSO"));
            }
            bd.closeCon();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void Buscar(Grupo a, String cod) {
        try {
            ConexionBD bd = new ConexionBD();
            bd.Connect();
            bd.comando = bd.conexion.createStatement();
            String comandoListar = "SELECT * FROM grupo WHERE ID_GRUPO ='" + cod + "'";
            bd.registro = bd.comando.executeQuery(comandoListar);
            while (bd.registro.next()) {
                a.setId(bd.registro.getString("ID_GRUPO"));
                a.setNumero(bd.registro.getInt("NUMERO"));
                a.setProfesor(bd.registro.getString("ID_PROF"));
                a.setCurso(bd.registro.getString("COD_CURSO"));
            }
            bd.closeCon();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void Buscar(Ciclo a, String cod) {
        try {
            ConexionBD bd = new ConexionBD();
            bd.Connect();
            bd.comando = bd.conexion.createStatement();
            String comandoListar = "SELECT * FROM ciclo WHERE id =" + cod;
            bd.registro = bd.comando.executeQuery(comandoListar);
            while (bd.registro.next()) {
                a.setId(bd.registro.getString("id"));
                a.setAnno(bd.registro.getInt("anno"));
                a.setNciclo(bd.registro.getInt("num_ciclo"));
                a.setFfinal(bd.registro.getString("fecha_final"));
                a.setFinicio(bd.registro.getString("fecha_inicio"));

            }
            bd.closeCon();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void BuscarGrpCrs(String curso, ArrayList<Grupo> l) {
        try {
            ConexionBD bd = new ConexionBD();
            bd.Connect();
            bd.comando = bd.conexion.createStatement();
            String comandoListar = "SELECT * FROM grupo WHERE COD_CURSO ='" + curso + "'";
            bd.registro = bd.comando.executeQuery(comandoListar);
            while (bd.registro.next()) {
                Grupo a = new Grupo();
                a.setProfesor(bd.registro.getString("ID_PROF"));
                a.setCurso(bd.registro.getString("COD_CURSO"));
                a.setNumero(bd.registro.getInt("NUMERO"));
                a.setId(bd.registro.getString("ID_GRUPO"));
                l.add(a);
            }
            bd.closeCon();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void BuscarGrpPrf(String profe, ArrayList<Grupo> l) {
        try {
            ConexionBD bd = new ConexionBD();
            bd.Connect();
            bd.comando = bd.conexion.createStatement();
            String comandoListar = "SELECT * FROM grupo WHERE ID_PROF ='" + profe + "'";
            bd.registro = bd.comando.executeQuery(comandoListar);
            while (bd.registro.next()) {
                Grupo a = new Grupo();
                a.setId(bd.registro.getString("ID_GRUPO"));
                a.setNumero(bd.registro.getInt("NUMERO"));
                a.setProfesor(bd.registro.getString("ID_PROF"));
                a.setCurso(bd.registro.getString("COD_CURSO"));
                l.add(a);
            }
            bd.closeCon();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void Historial(String a, ArrayList<Nota> l) {
        try {
            ConexionBD bd = new ConexionBD();
            bd.Connect();
            bd.comando = bd.conexion.createStatement();
            String comandoListar = "SELECT * FROM NOTA WHERE condicion !='encurso' AND ESTUDIANTE='" + a+"'";
            bd.registro = bd.comando.executeQuery(comandoListar);
            while (bd.registro.next()) {
                Nota n = new Nota();
                n.setCURSO(bd.registro.getString("curso"));
                n.setCondision(bd.registro.getString("condicion"));
                n.setESTUDIANTE(bd.registro.getString("estudiante"));
                n.setGrupo(bd.registro.getString("Grupo"));
                n.setNOTA(bd.registro.getFloat("nota"));
              
               l.add(n);
            }
            bd.closeCon();

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
    public void EstDeGrupo(String cod_Car,int num_Grupo, ArrayList<Nota> l) {
        try {
            ConexionBD bd = new ConexionBD();
            bd.Connect();
            bd.comando = bd.conexion.createStatement();
            String comandoListar = "SELECT * FROM NOTA WHERE GRUPO = '"+num_Grupo+"' AND "+"curso='"+cod_Car+"' AND condicion ='encurso'";
            bd.registro = bd.comando.executeQuery(comandoListar);
            while (bd.registro.next()) {
                Nota n = new Nota();
                n.setCURSO(bd.registro.getString("curso"));
                n.setCondision(bd.registro.getString("condicion"));
                n.setESTUDIANTE(bd.registro.getString("estudiante"));
                n.setGrupo(bd.registro.getString("Grupo"));
                n.setNOTA(bd.registro.getFloat("nota"));
                l.add(n);
            }
            bd.closeCon();

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void notasDEgrupo(String a, ArrayList<Nota> l) {
        try {
            ConexionBD bd = new ConexionBD();
            bd.Connect();
            bd.comando = bd.conexion.createStatement();
            String comandoListar = "SELECT * FROM NOTA WHERE grupo = '"+a+"' AND condicion ='encurso'";
            bd.registro = bd.comando.executeQuery(comandoListar);
            while (bd.registro.next()) {
                Nota n = new Nota();
                n.setCURSO(bd.registro.getString("curso"));
                n.setCondision(bd.registro.getString("condicion"));
                n.setESTUDIANTE(bd.registro.getString("estudiante"));
                n.setGrupo(bd.registro.getString("Grupo"));
                n.setNOTA(bd.registro.getFloat("nota"));
                l.add(n);
            }
            bd.closeCon();

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
    
    public void notaESt(String a,String est,Nota n) {
        try {
            ConexionBD bd = new ConexionBD();
            bd.Connect();
            bd.comando = bd.conexion.createStatement();
            String comandoListar = "SELECT * FROM NOTA WHERE grupo = '"+a+"' AND Estudiante ='"+est+"' AND condicion ='encurso'";
            bd.registro = bd.comando.executeQuery(comandoListar);
            while (bd.registro.next()) {
                n.setCURSO(bd.registro.getString("curso"));
                n.setCondision(bd.registro.getString("condicion"));
                n.setESTUDIANTE(bd.registro.getString("estudiante"));
                n.setGrupo(bd.registro.getString("Grupo"));
                n.setNOTA(bd.registro.getFloat("nota"));
            }
            bd.closeCon();

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
    
    public void Matriculados(String w, ArrayList<Nota> l) {
        try {
            ConexionBD bd = new ConexionBD();
            bd.Connect();
            bd.comando = bd.conexion.createStatement();
            String comandoListar = "SELECT * FROM NOTA WHERE CONDICION = 'encurso' AND ESTUDIANTE='" + w + "'";
            bd.registro = bd.comando.executeQuery(comandoListar);
            while (bd.registro.next()) {
                Nota n = new Nota();
                n.setCURSO(bd.registro.getString("curso"));
                n.setCondision(bd.registro.getString("condicion"));
                n.setESTUDIANTE(bd.registro.getString("estudiante"));
                n.setGrupo(bd.registro.getString("grupo"));
                n.setNOTA(bd.registro.getInt("nota"));
                l.add(n);
            }
            bd.closeCon();

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    //</editor-fold>
    //<editor-fold desc="Metodos de Insercion">
    public void agrega(Persona a) {
        try {
            ConexionBD bd = new ConexionBD();
            bd.Connect();
            String fec = "";
            if (a instanceof Alumno) {
                Alumno alu = (Alumno) a;
                fec = alu.getF_nac();
            }
            Statement s = bd.conexion.createStatement();
            String car = (a instanceof Alumno) ? ((Alumno) a).getCarrera() : "";
            s.executeUpdate("INSERT INTO Persona  VALUES('" + a.getCedula() + "','" + a.getClave() + "','" + a.getTipo() + "','" + a.getNombre() + "','" + fec + "','" + a.getEmail() + "','" + a.getTelefono() + "','" + car + "')");
            bd.closeCon();

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public void matricula(Nota n) {
        try {
            ConexionBD bd = new ConexionBD();
            bd.Connect();
            Statement s = bd.conexion.createStatement();
            s.executeUpdate("INSERT INTO nota VALUES("
                    + n.getNOTA() + ",'" + n.getCURSO() + "','"
                    + n.getESTUDIANTE() + "','" + n.getGrupo() + "','" + n.getCondision() + "')");
            bd.closeCon();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void agrega(Carrera a) {
        try {
            ConexionBD bd = new ConexionBD();
            bd.Connect();
            String fec = "";
            Statement s = bd.conexion.createStatement();
            s.executeUpdate("INSERT INTO carrera  VALUES('" + a.getCodigo() + "','" + a.getNombre() + "')");
            bd.closeCon();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public void agrega(Curso a) {
        try {
            ConexionBD bd = new ConexionBD();
            bd.Connect();
            String fec = "";
            Statement s = bd.conexion.createStatement();
            s.executeUpdate("INSERT INTO CURSO VALUES('" + a.getCodigo() + "','" + a.getNombre() + "','"
                    + a.getCreditos() + "','" + a.getHsemanales() + "','"
                    + a.getCarrera() + "','" + a.getNum_ciclo() + "')");
            bd.closeCon();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public void agrega(Grupo a) {
        try {
            ConexionBD bd = new ConexionBD();
            bd.Connect();
            String fec = "";
            Statement s = bd.conexion.createStatement();
            String sql = "INSERT INTO grupo VALUES('" + a.getId() + "','" + a.getNumero() + "','"
                    + a.getProfesor() + "','" + a.getCurso() + "')";
            s.executeUpdate(sql);
            bd.closeCon();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public void agrega(Ciclo a) {
        try {
            ConexionBD bd = new ConexionBD();
            bd.Connect();
            String fec = "";
            Statement s = bd.conexion.createStatement();
            s.executeUpdate("INSERT INTO Ciclo VALUES('" + a.getId() + "','" + a.getAnno() + "','"
                    + a.getNciclo() + "','" + a.getFinicio() + "','"
                    + a.getFfinal() + "')");
            bd.closeCon();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    //</editor-fold>
    //<editor-fold desc="Metodos de Actualizar">
    public void Actualiza(Persona a) {
        try {
            ConexionBD bd = new ConexionBD();
            bd.Connect();
            Statement s = bd.conexion.createStatement();
            //  s.execute("SET SQL_SAFE_UPDATES = 0");
            String sql
                    = "update persona set PASS = '" + a.getClave() + "', NOMBRE = '" + a.getNombre() + "', CORREO = '" + a.getEmail() + "',TELEFONO =" + a.getTelefono()
                    + " WHERE CEDULA = '" + a.getCedula() + "'";

            s.executeUpdate(sql);
            bd.closeCon();

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public void Actualiza(Carrera a) {
        try {
            ConexionBD bd = new ConexionBD();
            bd.Connect();
            String sql = "update Carrera set NOMBRE = '" + a.getNombre() + "' WHERE codigo = '" + a.getCodigo() + "'";
            sql = String.format(sql, a.getNombre(), a.getCodigo());
            Statement s = bd.conexion.createStatement();
            s.executeUpdate(sql);
            bd.closeCon();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public void Actualiza(Curso a) {
        try {
            ConexionBD bd = new ConexionBD();
            bd.Connect();
            String sql = "update curso set NOMBRE = '" + a.getNombre()
                    + "',CREDITOS = '" + a.getCreditos() + "',NUM_CICLO = '" + a.getNum_ciclo()
                    + "',H_SEMANALES= '" + a.getHsemanales() + "' where Codigo = '" + a.getCodigo() + "'";
            Statement s = bd.conexion.createStatement();
            s.executeUpdate(sql);
            bd.closeCon();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public void Actualiza(Ciclo a, String id) {
        try {
            ConexionBD bd = new ConexionBD();
            bd.Connect();
            String sql = "update ciclo set id = '" + a.getId() + "',anno = '" + a.getAnno() + "',num_ciclo='" + a.getNciclo()
                    + "',fecha_inicio='" + a.getFinicio() + "',fecha_final='" + a.getFfinal() + "'WHERE id = '" + id + "'";
            Statement s = bd.conexion.createStatement();
            s.executeUpdate(sql);
            bd.closeCon();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
    public void Actualiza(Nota a) {
        try {
            ConexionBD bd = new ConexionBD();
            bd.Connect();
            String sql = "update nota set nota = '" + a.getNOTA()
                   +"'WHERE estudiante = '" + a.getESTUDIANTE()+ "' "
                    + " And grupo = '"+a.getGrupo()
                    +"' AND condicion = 'encurso'";
            Statement s = bd.conexion.createStatement();
            s.executeUpdate(sql);
            bd.closeCon();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public void Actualiza(Grupo a) {
        try {
            ConexionBD bd = new ConexionBD();
            bd.Connect();
            String sql = "update grupo set ID_PROF = '" + a.getProfesor() + "' where ID_GRUPO = '" + a.getId() + "'";
            sql = String.format(sql, a.getProfesor(), a.getNumero(), a.getId());
            Statement s = bd.conexion.createStatement();
            s.executeUpdate(sql);
            bd.closeCon();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
    //</editor-fold>  

    //<editor-fold desc="Metodos de eliminar">
    public boolean eliminar(String id) {
        try {
            ConexionBD bd = new ConexionBD();
            bd.Connect();
            Statement s = bd.conexion.createStatement();
            String sql = "delete from persona WHERE cedula='" + id + "'";
            s.executeUpdate(sql);
            bd.closeCon();
            return true;
        } catch (SQLException e) {
            System.err.println(e.getMessage());

        }
        return false;

    }

    public boolean Desmatricula(String id, String cod) {
        try {
            ConexionBD bd = new ConexionBD();
            bd.Connect();
            Statement s = bd.conexion.createStatement();
            String sql = "delete from Nota WHERE Curso='" + cod + "' AND ESTUDIANTE ='" + id + "' AND CONDICION = 'encurso'";
            s.executeUpdate(sql);
            bd.closeCon();
            return true;
        } catch (SQLException e) {
            System.err.println(e.getMessage());

        }
        return false;

    }

    public boolean estaECurso(Nota n, String ced, String curs) {

        try {
            ConexionBD bd = new ConexionBD();
            bd.Connect();
            Statement s = bd.conexion.createStatement();
            String comandoListar
                    = "SELECT Curso FROM NOTA WHERE CURSO ='" + curs + "' AND ESTUDIANTE='" + ced + "' AND CONDICION = encurso";
            bd.registro = bd.comando.executeQuery(comandoListar);
            if (bd.registro.next()) {
                n.setCURSO(bd.registro.getString("curso"));
                n.setCondision(bd.registro.getString("condicion"));
                n.setESTUDIANTE(bd.registro.getString("estudiante"));
                n.setGrupo(bd.registro.getString("grupo"));
                n.setNOTA(bd.registro.getInt("nota"));
                return true;
            }
            bd.closeCon();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return false;
    }

    public String pasoCurso(String ced, String curs) {

        try {
            ConexionBD bd = new ConexionBD();
            bd.Connect();
            Statement s = bd.conexion.createStatement();
            String comandoListar = "SELECT Curso FROM NOTA WHERE CURSO ='" + curs + "' AND ESTUDIANTE='" + ced
                    + "' AND Nota != 0";
            bd.registro = bd.comando.executeQuery(comandoListar);
            if (bd.registro.next()) {
                return curs;
            }
            bd.closeCon();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return "";
    }

    public boolean eliminarCurso(String id) {
        try {
            ConexionBD bd = new ConexionBD();
            bd.Connect();
            Statement s = bd.conexion.createStatement();
            String sql = "delete from curso WHERE CODIGO='" + id + "'";
            s.executeUpdate(sql);
            bd.closeCon();
            return true;
        } catch (SQLException e) {
            System.err.println(e.getMessage());

        }
        return false;

    }

    public boolean eliminarCarrera(String id) {
        try {
            ConexionBD bd = new ConexionBD();
            bd.Connect();
            Statement s = bd.conexion.createStatement();
            String sql = "delete from carrera WHERE CODIGO='" + id + "'";
            s.executeUpdate(sql);
            bd.closeCon();
            return true;
        } catch (SQLException e) {
            System.err.println(e.getMessage());

        }
        return false;

    }
    //</editor-fold> 

    public void ofertaAcd(String Carrera, int ciclo, ArrayList<Curso> l) {
        try {
            ConexionBD bd = new ConexionBD();
            bd.Connect();
            bd.comando = bd.conexion.createStatement();
            String comandoListar = "SELECT * FROM CURSO WHERE COD_CARRERA ='" + Carrera + "'"
                    + " AND NUM_CICLO=" + ciclo;
            bd.registro = bd.comando.executeQuery(comandoListar);
            while (bd.registro.next()) {
                Curso a = new Curso();
                a.setNum_ciclo(bd.registro.getInt("Num_ciclo"));
                a.setNombre(bd.registro.getString("nombre"));
                a.setCreditos(bd.registro.getInt("creditos"));
                a.setHsemanales(bd.registro.getInt("H_SEMANALES"));
                a.setCodigo(bd.registro.getString("codigo"));
                a.setNum_ciclo(bd.registro.getInt("NUM_CICLO"));
                l.add(a);
            }
            bd.closeCon();

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
