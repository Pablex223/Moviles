
package Modelo;

public class Nota {
private float NOTA;
private String CURSO;
private String ESTUDIANTE;
private String grupo;
private String condision;

    public Nota(float NOTA, String CURSO, String ESTUDIANTE, String grupo, String condision) {
        this.NOTA = NOTA;
        this.CURSO = CURSO;
        this.ESTUDIANTE = ESTUDIANTE;
        this.grupo = grupo;
        this.condision = condision;
    }
    
    public Nota()
    {
        this.NOTA = 0;
        this.CURSO = "";
        this.ESTUDIANTE = "";
        this.grupo = "";
        this.condision = "";
    }

    public float getNOTA() {
        return NOTA;
    }

    public void setNOTA(float NOTA) {
        this.NOTA = NOTA;
    }

    public String getCURSO() {
        return CURSO;
    }

    public void setCURSO(String CURSO) {
        this.CURSO = CURSO;
    }

    public String getESTUDIANTE() {
        return ESTUDIANTE;
    }

    public void setESTUDIANTE(String ESTUDIANTE) {
        this.ESTUDIANTE = ESTUDIANTE;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public String getCondision() {
        return condision;
    }

    public void setCondision(String condision) {
        this.condision = condision;
    }
  @Override
    public String toString() {
        return "Cod Curso: "+CURSO+", grupo # "+grupo+", Estudiante: "+ESTUDIANTE+", Condicion:" +condision+ ", nota:" +NOTA;
    }
    public String toprofesorC(){
        return  "Cod Curso: "+CURSO+", grupo # "+grupo;
   }
    public String toprofesorE(){
        return  "Estudiante: "+ESTUDIANTE+", NOta: "+NOTA;    
    }

   
}
