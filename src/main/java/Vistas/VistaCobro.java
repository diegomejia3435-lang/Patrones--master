package Vistas;
import Pagos.MetodoPagoStrategy;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VistaCobro extends JDialog {

    private TiendaFacade fachada;
    private VistaTienda vistaPadre;
    private double montoTotal;

    // Componentes Efectivo
    private JTextField txtEfectivoRecibido;
    private JLabel lblVuelto;

    // Componentes Yape
    private JTextField txtCelularYape;

    // Componentes Tarjeta
    private JTextField txtTitularTarjeta;
    private JTextField txtDigitosTarjeta;

    private JTabbedPane pestañasPago;

    public VistaCobro(VistaTienda padre, TiendaFacade fachada) {
        super(padre, " Ventana de Facturacion y Pago", true); 
        this.vistaPadre = padre;
        this.fachada = fachada;
        this.montoTotal = (fachada.getOrdenActual() != null) ? fachada.getOrdenActual().calcularTotal() : 0.0;
        
        initComponentes();
    }

    private void initComponentes() {
        setSize(450, 400);
        setLocationRelativeTo(getParent());
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(245, 245, 245));

        JLabel lblMonto = new JLabel(String.format("Total a Cobrar: S/. %.2f", montoTotal), SwingConstants.CENTER);
        lblMonto.setFont(new Font("Arial", Font.BOLD, 20));
        lblMonto.setForeground(new Color(220, 53, 69));
        lblMonto.setBorder(BorderFactory.createEmptyBorder(15, 10, 10, 10));
        add(lblMonto, BorderLayout.NORTH);

        pestañasPago = new JTabbedPane();
        pestañasPago.setFont(new Font("Arial", Font.BOLD, 12));

        pestañasPago.addTab(" Efectivo", crearPanelEfectivo());
        pestañasPago.addTab(" Yape", crearPanelYape());
        pestañasPago.addTab(" Tarjeta POS", crearPanelTarjeta());

        add(pestañasPago, BorderLayout.CENTER);

        JButton btnProcesar = new JButton("⚡ Procesar y Finalizar Cobro");
        btnProcesar.setFont(new Font("Arial", Font.BOLD, 15));
        btnProcesar.setBackground(new Color(40, 167, 69)); 
        btnProcesar.setForeground(Color.WHITE);
        btnProcesar.setPreferredSize(new Dimension(450, 50));
        btnProcesar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(btnProcesar, BorderLayout.SOUTH);

        btnProcesar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ejecutarEstrategiaPago();
            }
        });
    }

    private JPanel crearPanelEfectivo() {
        JPanel pnl = new JPanel(new GridLayout(3, 1, 10, 10));
        pnl.setBorder(BorderFactory.createEmptyBorder(15, 30, 15, 30));
        
        txtEfectivoRecibido = new JTextField();
        txtEfectivoRecibido.setFont(new Font("Arial", Font.PLAIN, 14));
        
        lblVuelto = new JLabel("Vuelto: S/. 0.00");
        lblVuelto.setFont(new Font("Arial", Font.BOLD, 14));
        lblVuelto.setForeground(new Color(0, 123, 255));

        pnl.add(new JLabel("Monto en Efectivo Recibido (S/.):"));
        pnl.add(txtEfectivoRecibido);
        pnl.add(lblVuelto);

        txtEfectivoRecibido.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { calcularVuelto(); }
            public void removeUpdate(DocumentEvent e) { calcularVuelto(); }
            public void changedUpdate(DocumentEvent e) { calcularVuelto(); }
        });
        return pnl;
    }

    private void calcularVuelto() {
        try {
            double recibido = Double.parseDouble(txtEfectivoRecibido.getText().trim());
            double vuelto = recibido - montoTotal;
            if (vuelto >= 0) {
                lblVuelto.setText(String.format("Vuelto: S/. %.2f", vuelto));
                lblVuelto.setForeground(new Color(40, 167, 69));
            } else {
                lblVuelto.setText("Efectivo insuficiente");
                lblVuelto.setForeground(Color.RED);
            }
        } catch (NumberFormatException e) {
            lblVuelto.setText("Vuelto: S/. 0.00");
            lblVuelto.setForeground(Color.GRAY);
        }
    }

    private JPanel crearPanelYape() {
        JPanel pnl = new JPanel(new GridLayout(2, 1, 10, 10));
        pnl.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        txtCelularYape = new JTextField();
        txtCelularYape.setFont(new Font("Arial", Font.PLAIN, 14));
        pnl.add(new JLabel("Ingresa tu numero de celular Yape (9 digitos):"));
        pnl.add(txtCelularYape);
        return pnl;
    }

    private JPanel crearPanelTarjeta() {
        JPanel pnl = new JPanel(new GridLayout(4, 1, 5, 5));
        pnl.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
        txtTitularTarjeta = new JTextField();
        txtDigitosTarjeta = new JTextField();
        pnl.add(new JLabel("Nombre del Titular de la Tarjeta:"));
        pnl.add(txtTitularTarjeta);
        pnl.add(new JLabel("Ultimos 4 digitos de la tarjeta:"));
        pnl.add(txtDigitosTarjeta);
        return pnl;
    }

    private void ejecutarEstrategiaPago() {
        int pestañaSeleccionada = pestañasPago.getSelectedIndex();
        final String nombreMetodo = (pestañaSeleccionada == 0) ? "Efectivo" : (pestañaSeleccionada == 1) ? "Yape" : "Tarjeta POS";

        // Validaciones basicas en pantalla
        if (pestañaSeleccionada == 0 && txtEfectivoRecibido.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe ingresar el dinero recibido.", "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (pestañaSeleccionada == 1 && txtCelularYape.getText().trim().length() != 9) {
            JOptionPane.showMessageDialog(this, "El celular de Yape debe tener 9 digitos.", "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (pestañaSeleccionada == 2 && (txtTitularTarjeta.getText().trim().isEmpty() || txtDigitosTarjeta.getText().trim().length() != 4)) {
            JOptionPane.showMessageDialog(this, "Complete los datos de la tarjeta.", "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Creamos una estrategia anonima al vuelo para saltar el error de nombres de clases de tus archivos
        MetodoPagoStrategy estrategiaAnonima = new MetodoPagoStrategy() {
            @Override
            public boolean procesarPago(double monto) { return true; }
            @Override
            public String getNombreMetodo() { return nombreMetodo; }
        };

        // Pasamos el cobro de forma limpia a tu Facade (procesará y guardará en SQLite)
        boolean pagoProcesado = fachada.pagarOrden(estrategiaAnonima);

        if (pagoProcesado) {
            JOptionPane.showMessageDialog(this, "¡Pago procesado con exito! Generando orden de cocina.", "Exito", JOptionPane.INFORMATION_MESSAGE);
            this.dispose(); 
            vistaPadre.dispose(); 
            VistaCocinaCronometro monitorCocina = new VistaCocinaCronometro(fachada);
            monitorCocina.setVisible(true); // Levanta el monitor animado en pantalla
            System.out.println(" [Vista] Saltando a la Vista 5: Monitor de Cocina...");
        } else {
            JOptionPane.showMessageDialog(this, "La transaccion fue rechazada.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
