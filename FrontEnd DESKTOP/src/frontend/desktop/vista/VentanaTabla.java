

package frontend.desktop.vista;

import Control.Control;
import Modelo.Alumno;
import Modelo.Persona;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;


public class VentanaTabla extends JFrame implements Observer{

    public VentanaTabla(Control nuevoGestor){
        control = nuevoGestor;
        ajustarConfiguracionInicial();
        ajustarComponentes(getContentPane());
        agregarEventos();
    }
    

    private void ajustarConfiguracionInicial(){
        setTitle("Sistema de Matricula");
        setSize(1500,800);
        setResizable(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    
    }
    // <editor-fold defaultstate="collapsed" desc="COMPONENTES">
    private void ajustarComponentes(Container c){
        estado = new BarraEstado();
        lbSubtitulo = new JLabel("Haga doble-clic para editar los valores");
        lbUsuario = new JLabel("Usuario");
        panelUsuario = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelUsuario.add(lbUsuario);
        
        panelEncabezado = new JPanel();
        panelEncabezado.setLayout(new BorderLayout());
        panelEncabezado.setBorder(BorderFactory.createEmptyBorder(12,12,6,12));
        panelEncabezado.add(lbSubtitulo, BorderLayout.LINE_START);
        panelEncabezado.add(panelUsuario, BorderLayout.LINE_END);
        
        panelTabla = new JPanel();
        panelTabla.setLayout(new BorderLayout());
        
        panelTabla.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Listado de Personas"),BorderFactory.createEmptyBorder(6,12,12,12)));
        
        
        tablaDatos = new JTable();
        JScrollPane scrollPaneTabla = new JScrollPane(tablaDatos,
                        JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                        JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        
        tablaDatos.setFillsViewportHeight(true);
       configurarTabla(tablaDatos);
        
        panelTabla.add(scrollPaneTabla, BorderLayout.CENTER);
        
        
        
        JPanel panelFormulario = new
                JPanel();
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Informacion Personal"));
        panelFormulario.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        
        gbc.insets = new Insets(8,16,8,16);
        
        //ENTRADA DEL ID
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx =0.25;
        JLabel lbId = new JLabel("Id: ");
        panelFormulario.add(lbId,gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx =0.75;
        txtId = new JTextField(12);
        panelFormulario.add(txtId,gbc);
              
        //ENTRADA DE NOMBRE
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx =0.25;
        JLabel lbNombre = new JLabel("Nombre: ");
        panelFormulario.add(lbNombre,gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx =0.75;
        txtNombre = new JTextField(12);
        panelFormulario.add(txtNombre,gbc);
        
        //ENTRADA DE APELLIDO
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx =0.25;
        JLabel lbApellido = new JLabel("Apellido: ");
        panelFormulario.add(lbApellido,gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.weightx =0.75;
        txtApellido = new JTextField(12);
        panelFormulario.add(txtApellido,gbc);
        
        //ENTRADA TELEFONO
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx =0.25;
        JLabel lbTelefono = new JLabel("Telefono: ");
        panelFormulario.add(lbTelefono,gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.weightx =0.75;
        txtTelefono = new JTextField(12);
        panelFormulario.add(txtTelefono,gbc);
        
        //ENTRADA DE ESTADO
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx =0.25;
        JLabel lbEmail = new JLabel("Email: ");
        panelFormulario.add(lbEmail,gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.weightx =0.75;
        txtEmail = new JTextField(12);
        panelFormulario.add(txtEmail,gbc);
        
         //ENTRADA DE FECHA DE NACIMIENTO
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.weightx =0.25;
        JLabel lbFechaNacimiento = new JLabel("Fecha Nac: ");
        panelFormulario.add(lbFechaNacimiento,gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.weightx =0.75;
        txtFechaNacimiento = new JTextField(12);
        panelFormulario.add(txtFechaNacimiento,gbc);
        
        //BOTON AGREGAR
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        gbc.weightx =0.25;
        gbc.fill = GridBagConstraints.NONE;
        btnAgregar = new JButton("Agregar");
        panelFormulario.add(btnAgregar,gbc);
        
        btnEliminar = new JButton("Eliminar");
        JPanel panelBtnEliminar = 
                new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBtnEliminar.add(btnEliminar);
        
        panelTabla.add(panelBtnEliminar, 
                BorderLayout.PAGE_END);
                
        
        
        c.add(panelFormulario, BorderLayout.LINE_START);
        c.add(panelEncabezado, BorderLayout.PAGE_START);
        c.add(panelTabla, BorderLayout.CENTER);
        c.add(estado, BorderLayout.PAGE_END);
    
    }
    // </editor-fold>
    
     //<editor-fold defaultstate="collapsed" desc="EVENTOS">
    private void agregarEventos(){
        btnAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int telefono = Integer.parseInt(txtTelefono.getText());
                String cedula = txtId.getText();
                String nombre = txtNombre.getText() + ' ' + txtApellido.getText();
                String email = txtEmail.getText();
                String fechaNac = txtFechaNacimiento.getText();
                String clave = "root";
                String carrera = "IDS";
               
                Persona nuevoAlumno = new Alumno(telefono, email, nombre, cedula, fechaNac, clave, carrera);
                control.agregar(nuevoAlumno);
  
            }
        });
        btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //control.eliminar(tablaDatos.getSelectedRow());
                 String cedulaEliminar = (String) tablaDatos.getModel().getValueAt(tablaDatos.getSelectedRow(),3);
                 Persona personaEliminar = new Persona(0);
                 personaEliminar.setCedula(cedulaEliminar);
                 System.out.println(cedulaEliminar);
                 control.eliminar(personaEliminar);
            }
        });
    }
            // </editor-fold> 
    
    public void configurarTabla(JTable tabla){
        //En este llamado se asocia el modelo de la tabla
        // a la tabla (JTable)
        tabla.setModel(control.modeloTabla());
        tabla.setAutoCreateRowSorter(false);
        
        tabla.getModel().addTableModelListener(new TableModelListener() {

            @Override
            public void tableChanged(TableModelEvent e) {
                control.actualizar(String.format("Se actualizó el regsitro: %d",
                        e.getFirstRow() + 1));
            }

            
        });

    }
    
    
    
    
    public void iniciar(){        
        control.registrar(this);
        estado.mostrarMensaje("Programa iniciado ...");
        control.cargarDatos();
        setVisible(true);
    }
    
    @Override
    public void update(Observable modelo, Object evento) {
      
        if(evento instanceof String){
            estado.mostrarMensaje(String.format("Actualización --->   %s",evento));
           tablaDatos.repaint();
        }
        if(evento instanceof Persona){
            Persona persona = (Alumno)evento;
//            tablaDatos.revalidate();
//            JOptionPane.showMessageDialog(null, "Se agrego exitosamente " + persona.getCedula());
            
        }
        if(evento instanceof Integer){
           // tablaDatos.repaint(); 
           // JOptionPane.showMessageDialog(null,String.format("Se elimino exitosamente el registro: %d", ((Integer)evento + 1)));
           int tipoUsuario = (Integer)evento;
            
        }
    }
    
    //Atributos
    private Control control;
    private BarraEstado estado;
    private JTable tablaDatos;
    private JPanel panelEncabezado;
    private JPanel panelTabla;
    private JPanel panelUsuario;
    private JLabel lbSubtitulo;
    private JLabel lbUsuario;
    
    private JTextField txtId;
    private JTextField txtNombre;
    private JTextField txtApellido;
    private JTextField txtTelefono;
    private JTextField txtEmail;
    private JTextField txtFechaNacimiento;
    
    private JButton btnAgregar;
    private JButton btnEliminar;
    
}
