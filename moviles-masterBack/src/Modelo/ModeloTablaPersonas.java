

package Modelo;

import java.util.List;
import javax.swing.table.AbstractTableModel;


public class ModeloTablaPersonas extends AbstractTableModel {

    public ModeloTablaPersonas(List<Persona> personas){
        this.personas = personas;
        System.out.println("Modelo Tabla Personas" + personas.size());
    }
    
    
    @Override
    public int getRowCount() { 
        int filas = personas.size();
        return filas;
    }

    @Override
    public int getColumnCount() {
        return Persona.nombreCampos().length;
        //return Persona.numCampos();
    }
    
    @Override
    //Permite obtener el valor de un atributo de la persona
    //que representa la posición del ArrayList correspondiente
    //al valor
    public Object getValueAt(int rowIndex, int columnIndex) {
        return personas.get(rowIndex).toArray()[columnIndex];
    }
    
    
    //Pruebas con los siguientes métodos
    
    @Override 
    public String getColumnName(int columnIndex){
        return Persona.nombreCampos()[columnIndex];
    }
    
    
    @Override 
    public void setValueAt(Object valor, int rowIndex, int columnIndex){
        //Actualiza el atributo de la persona
        personas.get(rowIndex).fijarAtributo(valor, columnIndex);
        //Actualiza la celda del modelo de la tabla
        fireTableCellUpdated(rowIndex, columnIndex);
    }
    
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex){
        //return true;
        return (columnIndex >= 0);
    }
    
//    @Override
//    //Permite conocer la clase asociada a la columna
    public Class getColumnClass(int columnIndex){
        return getValueAt(0, columnIndex).getClass();
    }
    
    
    //Atributo
    private List<Persona> personas;
    
}
