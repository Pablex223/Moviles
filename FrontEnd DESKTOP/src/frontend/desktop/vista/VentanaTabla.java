

package frontend.desktop.vista;

import Control.Control;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Observable;
import java.util.Observer;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;


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
    
    private void ajustarComponentes(Container c){
        estado = new BarraEstado();
        lbSubtitulo = new JLabel("Haga doble-clic para editar los valores");
        
        panelEncabezado = new JPanel();
        panelEncabezado.setLayout(new FlowLayout(FlowLayout.LEFT));
        panelEncabezado.setBorder(BorderFactory.createEmptyBorder(12,12,6,12));
        panelEncabezado.add(lbSubtitulo);
        
        
        panelTabla = new JPanel();
        panelTabla.setLayout(new BorderLayout());
        
        panelTabla.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Listado de Personas"),BorderFactory.createEmptyBorder(6,12,12,12)));
        
        
        tablaDatos = new JTable();
        JScrollPane scrollPaneTabla = new JScrollPane(tablaDatos,
                        JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                        JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        
        tablaDatos.setFillsViewportHeight(true);
       // configurarTabla(tablaDatos);
        
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
    
    
    private void agregarEventos(){
      
    }
            
    
//    public void configurarTabla(JTable tabla){
//        //En este llamado se asocia el modelo de la tabla
//        // a la tabla (JTable)
//        tabla.setModel(gestorPrincipal.modeloTabla());
//        tabla.setAutoCreateRowSorter(false);
//        
//        tabla.getModel().addTableModelListener(new TableModelListener() {
//
//            @Override
//            public void tableChanged(TableModelEvent e) {
//                gestorPrincipal.actualizar(String.format("Se actualizó el regsitro: %d",
//                        e.getFirstRow() + 1));
//            }
//        });
//        
//        
////        tabla.getColumnModel().getColumn(0).setPreferredWidth(48);
////        tabla.getColumnModel().getColumn(1).setPreferredWidth(120);
////        tabla.getColumnModel().getColumn(2).setPreferredWidth(120);
////        tabla.getColumnModel().getColumn(3).setPreferredWidth(36);
////        tabla.getColumnModel().getColumn(4).setPreferredWidth(24);
//        
//        
//    }
    
    
    
    
    public void iniciar(){        
       // control.registrar(this);
       // control.cargarDatos();
        estado.mostrarMensaje("Programa iniciado ...");
        setVisible(true);
    }
    
    @Override
    public void update(Observable modelo, Object evento) {
        
//        if(evento instanceof String)
//            estado.mostrarMensaje(String.format("Actualización (%s): %s", modelo,evento));
//        
//        if(evento instanceof Persona){
//            Persona persona = (Persona)evento;
//            tablaDatos.repaint();
//            JOptionPane.showMessageDialog(null, "Se agrego exitosamente " + persona);
//            
//        }
//        if(evento instanceof Integer){
//            tablaDatos.repaint(); 
//            JOptionPane.showMessageDialog(null,String.format("Se elimino exitosamente el registro: %d", ((Integer)evento + 1)));
//            
//        }
    }
    
    //Atributos
    private Control control;
    private BarraEstado estado;
    private JTable tablaDatos;
    private JPanel panelEncabezado;
    private JPanel panelTabla;
    private JLabel lbSubtitulo;
    
    private JTextField txtId;
    private JTextField txtNombre;
    private JTextField txtApellido;
    private JTextField txtTelefono;
    private JTextField txtEmail;
    private JTextField txtFechaNacimiento;
    
    private JButton btnAgregar;
    private JButton btnEliminar;
    
}
