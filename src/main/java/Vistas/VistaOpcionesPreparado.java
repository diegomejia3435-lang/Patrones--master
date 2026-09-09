package Vistas;

import Productos.Producto;
import Productos.ProductoPreparado;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VistaOpcionesPreparado extends JDialog {

    private TiendaFacade fachada;
    private Producto productoOriginal;
    private JComboBox<String> cmbOpciones;
    private JButton btnConfirmar;
    private VistaTienda vistaPadre;

    // El constructor recibe el Frame padre y se configura como modal (true)
    public VistaOpcionesPreparado(VistaTienda padre, TiendaFacade fachada, Producto producto) {
        super(padre, "Configurando Producto", true); // true activa el bloqueo modal
        this.vistaPadre = padre;
        this.fachada = fachada;
        this.productoOriginal = producto;
        initComponentes();
        cargarOpcionesDinamicas();
    }

    private void initComponentes() {
        setSize(400, 220);
        setLocationRelativeTo(getParent()); // Se centra exactamente encima de la ventana tienda
        setLayout(new BorderLayout(15, 15));
        getContentPane().setBackground(new Color(250, 250, 250));

        // 1. TÍTULO DINÁMICO
        JLabel lblTitulo = new JLabel(" Configurando: " + productoOriginal.getNombre(), SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 15));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(15, 10, 0, 10));
        add(lblTitulo, BorderLayout.NORTH);

        // 2. PANEL CENTRAL (Contenedor del ComboBox de opciones)
        JPanel pnlCentro = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 15));
        pnlCentro.setBackground(new Color(250, 250, 250));
        
        JLabel lblSeleccione = new JLabel("Elige una variante:");
        lblSeleccione.setFont(new Font("Arial", Font.PLAIN, 13));
        pnlCentro.add(lblSeleccione);

        cmbOpciones = new JComboBox<>();
        cmbOpciones.setFont(new Font("Arial", Font.PLAIN, 13));
        cmbOpciones.setPreferredSize(new Dimension(200, 30));
        pnlCentro.add(cmbOpciones);
        
        add(pnlCentro, BorderLayout.CENTER);

        // 3. BOTÓN DE ACCIÓN INFERIOR
        btnConfirmar = new JButton("Confirmar y Añadir al Carrito");
        btnConfirmar.setFont(new Font("Arial", Font.BOLD, 14));
        btnConfirmar.setBackground(new Color(40, 167, 69)); // Verde éxito
        btnConfirmar.setForeground(Color.WHITE);
        btnConfirmar.setPreferredSize(new Dimension(400, 45));
        btnConfirmar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(btnConfirmar, BorderLayout.SOUTH);

        // 4. LÓGICA DE CONFIRMACIÓN Y TRANSICIÓN
        btnConfirmar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                procesarConfirmacion();
            }
        });
    }

    private void cargarOpcionesDinamicas() {
        String textoOpciones = productoOriginal.getOpcionesDisponibles();
        
        if (textoOpciones != null && !textoOpciones.isEmpty()) {
            // El truco: Rompemos la cadena de SQLite usando las comas como separador
            String[] opcionesArray = textoOpciones.split(",");
            for (String opcion : opcionesArray) {
                cmbOpciones.addItem(opcion.trim()); // Limpiamos espacios y añadimos al ComboBox
            }
        } else {
            cmbOpciones.addItem("Estándar / Tradicional");
        }
    }

    private void procesarConfirmacion() {
        String opcionSeleccionada = (String) cmbOpciones.getSelectedItem();

        // Si el objeto mapeado en el Factory es de verdad un ProductoPreparado, le inyectamos su opción
        if (productoOriginal instanceof ProductoPreparado) {
            ProductoPreparado prep = (ProductoPreparado) productoOriginal;
            prep.setOpcionElegida(opcionSeleccionada); // Seteamos tu variable del patrón State
        }

        // LÓGICA BACKEND: Agregamos el producto con su variante ya configurada al carrito
        fachada.agregarProductoAOrden(productoOriginal);

        // REFRESCADO EN CASCADA: Forzamos a la ventana padre a redibujar la tabla con el nuevo subtotal
        vistaPadre.actualizarInterfazCarrito();

        // CIERRE AUTOMÁTICO: Destruye la ventana modal liberando el foco de la pantalla principal
        this.dispose();
    }
}
