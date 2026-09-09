package Vistas;

import Productos.Producto;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class VistaTienda extends JFrame {

    private TiendaFacade fachada;
    
    // Componentes del Panel Izquierdo (Catálogo)
    private JPanel pnlCatalogo;
    private JScrollPane scrollCatalogo;
    
    // Componentes del Panel Derecho (Carrito)
    private JTable tblCarrito;
    private DefaultTableModel modeloTabla;
    private JLabel lblTotal;
    private JButton btnPagar;

    public VistaTienda(TiendaFacade fachada) {
        this.fachada = fachada;
        initComponentes();
        cargarProductosDesdeBD();
        actualizarInterfazCarrito();
    }

    private void initComponentes() {
        setTitle(" Mi Tienda - Catálogo y Carrito ");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(240, 240, 240));

        // --- 1. CABECERA ---
        String cliente = (fachada.getOrdenActual() != null) ? fachada.getOrdenActual().getNombreCliente() : "Cliente";
        JLabel lblCliente = new JLabel("  Cliente Activo: " + cliente, SwingConstants.LEFT);
        lblCliente.setFont(new Font("Arial", Font.BOLD, 16));
        lblCliente.setPreferredSize(new Dimension(900, 40));
        lblCliente.setOpaque(true);
        lblCliente.setBackground(new Color(220, 53, 69));
        lblCliente.setForeground(Color.WHITE);
        add(lblCliente, BorderLayout.NORTH);

        // --- 2. PANEL IZQUIERDO: CATÁLOGO ---
        pnlCatalogo = new JPanel();
        // Usamos un Grid de 3 columnas para que los botones de comida se acomoden solos
        pnlCatalogo.setLayout(new GridLayout(0, 3, 10, 10)); 
        pnlCatalogo.setBackground(Color.WHITE);
        pnlCatalogo.setBorder(BorderFactory.createTitledBorder("Menú de Productos (Desde Base de Datos)"));
        
        scrollCatalogo = new JScrollPane(pnlCatalogo);
        scrollCatalogo.setPreferredSize(new Dimension(500, 500));
        add(scrollCatalogo, BorderLayout.WEST);

        // --- 3. PANEL DERECHO: CARRITO / ORDEN ---
        JPanel pnlDerecho = new JPanel(new BorderLayout(5, 5));
        pnlDerecho.setBorder(BorderFactory.createTitledBorder("Tu Carrito de Compras"));
        pnlDerecho.setBackground(Color.WHITE);

        // Configuración de la Tabla del Carrito
        String[] columnas = {"ID", "Producto", "Categoría", "Precio (S/.)"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; } // Tabla no editable
        };
        tblCarrito = new JTable(modeloTabla);
        tblCarrito.setFont(new Font("Arial", Font.PLAIN, 13));
        tblCarrito.setRowHeight(22);
        JScrollPane scrollTabla = new JScrollPane(tblCarrito);
        pnlDerecho.add(scrollTabla, BorderLayout.CENTER);

        // --- 4. PARTE INFERIOR DEL PANEL DERECHO (Total y Botón de Pago) ---
        JPanel pnlInferiorDerecho = new JPanel(new GridLayout(2, 1, 5, 5));
        pnlInferiorDerecho.setBackground(Color.WHITE);

        lblTotal = new JLabel("Total a Pagar: S/ 0.00", SwingConstants.RIGHT);
        lblTotal.setFont(new Font("Arial", Font.BOLD, 18));
        lblTotal.setForeground(new Color(40, 167, 69));
        pnlInferiorDerecho.add(lblTotal);

        btnPagar = new JButton("Pagar Cuenta y Procesar Delivery");
        btnPagar.setFont(new Font("Arial", Font.BOLD, 15));
        btnPagar.setBackground(new Color(0, 123, 255)); // Azul llamativo
        btnPagar.setForeground(Color.WHITE);
        btnPagar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        pnlInferiorDerecho.add(btnPagar);
        
        pnlDerecho.add(pnlInferiorDerecho, BorderLayout.SOUTH);
        add(pnlDerecho, BorderLayout.CENTER);

        // --- LOGICA DEL BOTÓN PAGAR ---
        btnPagar.addActionListener(new ActionListener() {
            @Override
             public void actionPerformed(ActionEvent e) {
                if (fachada.getOrdenActual() == null || fachada.getOrdenActual().getItems().isEmpty()) {
                    JOptionPane.showMessageDialog(VistaTienda.this, "El carrito esta vacio. Agrega productos antes de pagar.", "Carrito Vacio", JOptionPane.WARNING_MESSAGE);
                    return;
                }
  
                VistaCobro modalPago = new VistaCobro(VistaTienda.this, fachada);
                modalPago.setVisible(true); // Bloquea la tienda hasta procesar la transacción
            }
        });
    }

    // Método que jala los productos leídos de SQLite a través de tu fachada
    private void cargarProductosDesdeBD() {
        List<Producto> listaProductos = fachada.getCatalogo();
        
        for (Producto prod : listaProductos) {
            // Creamos un botón personalizado para cada comida/bebida
            String textoBoton = "<html><center><b>" + prod.getNombre() + "</b><br><font color='green'>S/. " + prod.getPrecio() + "</font></center></html>";
            JButton btnProd = new JButton(textoBoton);
            btnProd.setFont(new Font("Arial", Font.PLAIN, 12));
            btnProd.setBackground(new Color(248, 249, 250));
            btnProd.setCursor(new Cursor(Cursor.HAND_CURSOR));
            
            // Lógica secuencial al presionar un producto
            btnProd.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (prod.getRequierePreparacion() == 0) {
                        // COMPORTAMIENTO 1: Gaseosa / Producto Directo -> Va de frente al carrito
                        fachada.agregarProductoAOrden(prod);
                        actualizarInterfazCarrito();
                    } else {
                    VistaOpcionesPreparado modalOpciones = new VistaOpcionesPreparado(VistaTienda.this, fachada, prod);
                    modalOpciones.setVisible(true); // Bloquea la tienda hasta que el usuario confirme
                

                    }
                }
            });
            pnlCatalogo.add(btnProd);
        }
        pnlCatalogo.revalidate();
        pnlCatalogo.repaint();
    }

    // Refresca la tabla y el JLabel del total leyendo tu objeto Orden en tiempo real
    public void actualizarInterfazCarrito() {
        modeloTabla.setRowCount(0); // Limpiamos la tabla visual
        
        if (fachada.getOrdenActual() != null) {
            // Recorremos los ítems actuales usando tu método getItems()
            for (Producto p : fachada.getOrdenActual().getItems()) {
                Object[] fila = {p.getId(), p.getNombre(), p.getCategoria(), "S/. " + p.getPrecio()};
                modeloTabla.addRow(fila);
            }
            // Mostramos el total usando tu método calcularTotal()
            lblTotal.setText(String.format("Total a Pagar: S/ %.2f", fachada.getOrdenActual().calcularTotal()));
        }
        
    }
}
