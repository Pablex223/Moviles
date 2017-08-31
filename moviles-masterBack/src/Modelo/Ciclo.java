package Modelo;

public class Ciclo {
    private String id;
    private int anno;
    private int nciclo;
    private String Finicio;
    private String Ffinal;

   public Ciclo(String id, int anno, int nciclo, String Finicio, String Ffinal) {
        this.id = id;
        this.anno = anno;
        this.nciclo = nciclo;
        this.Finicio = Finicio;
        this.Ffinal = Ffinal;
    }

   public Ciclo()
   {
       this.id = "";
        this.anno = 0;
        this.nciclo = 0;
        this.Finicio = "";
        this.Ffinal = "";
   }
   
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public int getAnno() {
        return anno;
    }

    public void setAnno(int anno) {
        this.anno = anno;
    }

    

    public int getNciclo() {
        return nciclo;
    }

    public void setNciclo(int nciclo) {
        this.nciclo = nciclo;
    }

    
    

    public String getFinicio() {
        return Finicio;
    }

    public void setFinicio(String Finicio) {
        this.Finicio = Finicio;
    }

    public String getFfinal() {
        return Ffinal;
    }

    public void setFfinal(String Ffinal) {
        this.Ffinal = Ffinal;
    }
    
}
