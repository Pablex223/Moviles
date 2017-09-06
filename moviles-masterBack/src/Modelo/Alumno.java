package Modelo;

public class Alumno extends Persona{

    public Alumno(int telefono, String email, String nombre, String cedula, String F_nac, String clave, String car) {
        super(telefono, email, nombre, cedula,4,clave);
        this.F_nac=F_nac;
        this.carrera = car;
    }

    public Alumno() {
        super(4);
        this.F_nac="";
        this.carrera = "";
    }

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    public String getF_nac() {
        return F_nac;
    }

    public void setF_nac(String F_nac) {
        this.F_nac = F_nac;
    }
    public Object[] toArray(){
        Object[] r = new Object[8];
        r[0] = getTelefono();
        r[1] = getEmail();
        r[2] = getNombre();
        r[3] = getCedula();
        r[4] = getTipo();
        r[5] = getClave();
        r[6] = getF_nac();
        r[7] = getCarrera();
        return r;
    }
    
    public void fijarAtributo(Object aValue, int columnIndex){
        switch(columnIndex){
             case 0:
                setTelefono(((Integer) aValue));
                break;
            case 1: 
                setEmail(aValue.toString());
                break;
            case 2:
                setNombre(aValue.toString());
                break;
                
           case 3:
                setCedula(aValue.toString());
                break;
                  case 4:
                setTipo(((Integer) aValue));
                break;
                 case 5:
                 setClave(aValue.toString());
                break;
                  case 6:
                setF_nac(aValue.toString());
                break;
            case 7:
                setCarrera(aValue.toString());
                break;
     
            default:
                throw new IndexOutOfBoundsException();
        }
    
    }    
   public static String[] nombreCampos(){
        return NOMBRE_CAMPOS_ALUMNO;
    }
    
    public static int numCampos(){
        return Alumno.class.getClass().getFields().length;
    }
    private String carrera;
    private String F_nac;

    @Override
    public String toString() {
        return "Alumno{" + super.toString() +  "carrera=" + carrera + ", F_nac=" + F_nac + '}';
    }
    
        
    private static final String[] NOMBRE_CAMPOS_ALUMNO = {"Telefono", "Email", "Nombre", "Cedula", "Fecha Nac.", "tipo", "Clave", "Carrera"};
}
