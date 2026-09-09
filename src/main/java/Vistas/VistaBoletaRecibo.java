package Vistas;

import Productos.Orden;
import Productos.Producto;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VistaBoletaRecibo extends JFrame {

    private TiendaFacade fachada;
    private JTextArea txtTicket;

    public VistaBoletaRecibo(TiendaFacade fachada) {
        setTitle(" Ticket de Venta Electrónico");
        this.fachada = fachada;
        initComponentes();
        generarTicketVisual();
    }

    private void initComponentes() {
        setSize(420, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(230, 230, 230));

        // 1. TÍTULO SUPERIOR
        JLabel lblTitulo = new JLabel(" COMPRA FINALIZADA CON ÉXITO", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 15));
        lblTitulo.setForeground(new Color(40, 167, 69)); // Verde éxito
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(15, 10, 5, 10));
        add(lblTitulo, BorderLayout.NORTH);

        // 2. PANEL CENTRAL: EL TICKET DE VENTA (JTextArea estilizado)
        txtTicket = new JTextArea();
        txtTicket.setFont(new Font("Courier New", Font.PLAIN, 13)); // Fuente tipo ticketera
        txtTicket.setEditable(false);
        txtTicket.setMargin(new Insets(15, 15, 15, 15));
        txtTicket.setBackground(new Color(255, 255, 240)); // Color tipo papel recibo
        
        JScrollPane scrollTicket = new JScrollPane(txtTicket);
        scrollTicket.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        add(scrollTicket, BorderLayout.CENTER);

        // 3. BOTÓN INFERIOR DE REINICIO DE SESIÓN
        JButton btnCerrarSesion = new JButton(" Cerrar Cuenta y Siguiente Cliente");
        btnCerrarSesion.setFont(new Font("Arial", Font.BOLD, 14));
        btnCerrarSesion.setBackground(new Color(0, 123, 255)); // Azul
        btnCerrarSesion.setForeground(Color.WHITE);
        btnCerrarSesion.setPreferredSize(new Dimension(420, 50));
        btnCerrarSesion.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(btnCerrarSesion, BorderLayout.SOUTH);

        // 4. LÓGICA DE TRANSICIÓN DE RETORNO A VISTA 1
        btnCerrarSesion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Cerramos la boleta actual
                dispose();

                // Regresamos el flujo al inicio abriendo la Vista 1 limpia
                System.out.println(" [Vista] Sesión cerrada. Reiniciando a Pantalla de Bienvenida...");
                new VistaInicio(fachada).setVisible(true);
            }
        });
    }

    private void generarTicketVisual() {
        Orden orden = fachada.getOrdenActual();
        if (orden == null) {
            txtTicket.setText("No hay ninguna orden activa en esta sesión.");
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("========================================\n");
        sb.append("          MI TIENDA DELIVERY            \n");
        sb.append("========================================\n");
        sb.append(" Cliente: ").append(orden.getNombreCliente()).append("\n");
        sb.append(" Estado de Cuenta: ").append(orden.isPagada() ? "PAGADA" : "PENDIENTE").append("\n");
        sb.append("----------------------------------------\n");
        sb.append(" PRODUCTOS COMPRADOS:\n\n");

        // Recorremos tus productos reales y llamamos a tu método dinámico generarLineaBoleta()
        for (Producto p : orden.getItems()) {
            sb.append(" * ").append(p.generarLineaBoleta()).append("\n");
        }

        sb.append("----------------------------------------\n");
        sb.append(String.format(" TOTAL PAGADO: S/. %.2f\n", orden.calcularTotal()));
        sb.append("========================================\n");
        sb.append("       ¡Gracias por tu preferencia!      \n");
        sb.append("========================================\n");

        txtTicket.setText(sb.toString());
    }
}
