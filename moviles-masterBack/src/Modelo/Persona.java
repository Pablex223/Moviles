package Modelo;

public class Persona{
    
    protected int telefono;
    protected String email;
    protected String nombre;
    protected String cedula;
    protected int tipo;
    protected String clave;

    public Persona(int telefono, String email, String nombre, String cedula, int tipo, String clave) {
        this.telefono = telefono;
        this.email = email;
        this.nombre = nombre;
        this.cedula = cedula;
        this.tipo = tipo;
        this.clave = clave;
    }
    

    public Persona(int t) {
         telefono = 0;
        email = "";
        nombre = "";
        cedula = "";
        tipo = t;
        clave = "";
    }
    

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }
    public Object[] toArray(){
        Object[] r = new Object[6];
        r[0] = getTelefono();
        r[1] = getEmail();
        r[2] = getNombre();
        r[3] = getCedula();
        r[4] = getTipo();
        r[5] = getClave();
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
            default:
                throw new IndexOutOfBoundsException();
        }
    
    }    
    
    public static String[] nombreCampos(){
        return NOMBRE_CAMPOS;
    }
    
    public static int numCampos(){
        return Persona.class.getClass().getFields().length;
    }

    @Override
    public String toString() {
        return "Persona{" + "telefono=" + telefono + ", email=" + email + ", nombre=" + nombre + ", cedula=" + cedula + ", tipo=" + tipo + ", clave=" + clave + '}';
    }
    
    
    
    
    private static final String[] NOMBRE_CAMPOS = {"Telefono", "Email", "Nombre", "Cedula", "Tipo", "Clave"};
        
    
  
    
}
