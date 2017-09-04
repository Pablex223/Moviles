

package frontend.desktop.vista;

import frontend.desktop.control.Control;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
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

    public VentanaTabla(Control control){
        this.control = control;
        ajustarConfiguracionInicial();
        ajustarComponentes(getContentPane());
        agregarEventos();
    }
    
    
    private void ajustarConfiguracionInicial(){
        setTitle("Gestion");
        setSize(1500,800);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    
    }
    
    private void ajustarComponentes(Container c){
        estado = new BarraEstado();
        lbSubtitulo = new JLabel("Haga doble-clic para editar los valores");
        txtBuscar = new JTextField(12);
        btnBuscar = new JButton("Buscar");
        
        panelBuscar = new JPanel();
        panelBuscar.setLayout(new FlowLayout(FlowLayout.RIGHT));
        panelBuscar.add(btnBuscar);
        panelBuscar.add(txtBuscar);
        
        panelEncabezado = new JPanel();
        panelEncabezado.setLayout(new BorderLayout());
        
        panelEncabezado.setBorder(BorderFactory.createEmptyBorder(12,12,6,12));
        panelEncabezado.add(lbSubtitulo, BorderLayout.LINE_START);
         panelEncabezado.add(panelBuscar, BorderLayout.LINE_END);
        
        
        panelTabla = new JPanel();
        panelTabla.setLayout(new BorderLayout());
        
        panelTabla.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Listado de Personas"),BorderFactory.createEmptyBorder(6,12,12,12)));
        
        
        tablaDatos = new JTable();
        JScrollPane scrollPaneTabla = new JScrollPane(tablaDatos,
                        JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                        JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        
        tablaDatos.setFillsViewportHeight(true);
      //  configurarTabla(tablaDatos);
        
        panelTabla.add(scrollPaneTabla, BorderLayout.CENTER);
        
        
        
        JPanel panelFormulario = new JPanel();
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
        
        //ENTRADA ESTATURA
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx =0.25;
        JLabel lbEstatura = new JLabel("Estatura: ");
        panelFormulario.add(lbEstatura,gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.weightx =0.75;
        txtEstatura = new JTextField(12);
        panelFormulario.add(txtEstatura,gbc);
        
        //ENTRADA DE ESTADO
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx =0.25;
        JLabel lbEstado = new JLabel("Estado: ");
        panelFormulario.add(lbEstado,gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.weightx =0.75;
        gbc.fill = GridBagConstraints.HORIZONTAL;
//        String [] opciones = {"Activo", "Inactivo"};
//        cmbEstado = new JComboBox(opciones);
        cmbEstado = new JComboBox();
        cmbEstado.addItem("Activo");
        cmbEstado.addItem("Inactivo");
        panelFormulario.add(cmbEstado,gbc);
        
        //BOTON AGREGAR
        gbc.gridx = 0;
        gbc.gridy = 5;
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
            
    
    
    
    public void iniciar(){        
        estado.mostrarMensaje("Programa iniciado ...");
        setVisible(true);
    }
    
    @Override
    public void update(Observable modelo, Object evento) {
     
    }
    
    //Atributo
    private Control control;
    private BarraEstado estado;
    private JTable tablaDatos;
    private JPanel panelEncabezado;
    private JPanel panelTabla;
    private JPanel panelBuscar;
    private JLabel lbSubtitulo;
    
    private JTextField txtId;
    private JTextField txtNombre;
    private JTextField txtApellido;
    private JTextField txtEstatura;
    private JTextField txtBuscar;
    private JComboBox cmbEstado;    
    private JButton btnAgregar;
    private JButton btnEliminar;
    private JButton btnBuscar;
    
}
