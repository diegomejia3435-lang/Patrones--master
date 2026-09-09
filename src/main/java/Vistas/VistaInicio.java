package Vistas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class VistaInicio extends JFrame {

    // Componentes visuales de la pantalla
    private JTextField txtNombreCliente;
    private JButton btnAbrirCuenta;
    private JLabel lblTitulo, lblSubtitulo;
    private TiendaFacade fachada;

    public VistaInicio(TiendaFacade fachada) {
        this.fachada = fachada;
        initComponentes();
    }

    private void initComponentes() {
        // 1. Configuracion basica de la ventana (JFrame)
        setTitle("Sistema de Delivery - Bienvenido");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centra la ventana en la pantalla
        setLayout(new GridBagLayout()); // Diseñador ordenado por celdas
        getContentPane().setBackground(new Color(245, 245, 245)); // Fondo gris claro

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Margen entre componentes
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // 2. Titulo Principal (Logotipo textual)
        lblTitulo = new JLabel("🍕 MI TIENDA DELIVERY 🚗", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitulo.setForeground(new Color(220, 53, 69)); // Color rojo atractivo
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(lblTitulo, gbc);

        // 3. Subtitulo informativo
        lblSubtitulo = new JLabel("Ingrese su nombre para iniciar su pedido:", SwingConstants.CENTER);
        lblSubtitulo.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridy = 1;
        add(lblSubtitulo, gbc);

        // 4. Campo de texto para el nombre (JTextField)
        txtNombreCliente = new JTextField(15);
        txtNombreCliente.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        add(txtNombreCliente, gbc);

        // 5. Boton de Accion (JButton)
        btnAbrirCuenta = new JButton("Abrir Nueva Cuenta ✨");
        btnAbrirCuenta.setFont(new Font("Arial", Font.BOLD, 14));
        btnAbrirCuenta.setBackground(new Color(40, 167, 69)); // Color verde exito
        btnAbrirCuenta.setForeground(Color.WHITE);
        btnAbrirCuenta.setCursor(new Cursor(Cursor.HAND_CURSOR));
        gbc.gridy = 3;
        add(btnAbrirCuenta, gbc);

        // 6. LOGICA DEL BOTÓN: Evento que conecta con tu Facade
        btnAbrirCuenta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                procesarApertura();
            }
        });
    }

    private void procesarApertura() {
        String nombre = txtNombreCliente.getText().trim();

        // VALIDACIÓN: Que el campo no esté vacío
        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                    "Por favor, ingresa tu nombre para continuar.", 
                    "Campo Vacío", 
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Generamos un ID de orden aleatorio para la simulacion
        int idOrdenAleatorio = new Random().nextInt(9000) + 1000;

        // TRANSICIÓN Y backend: Llama a tu metodo exacto del Facade
        fachada.nuevaSesionCliente(idOrdenAleatorio, nombre);

        // Alerta de exito en pantalla
        JOptionPane.showMessageDialog(this, 
                "¡Cuenta abierta! Bienvenido, " + nombre + ".\nOrden ID: " + idOrdenAleatorio, 
                "Sesión Iniciada", 
                JOptionPane.INFORMATION_MESSAGE);

        // Cierra esta ventana actual
        this.dispose();
          new VistaTienda(fachada).setVisible(true);
        // --- ENLACE A VISTA 2 ---
        // Aqui abriremos la Vista 2 en el siguiente paso de esta forma:
        // new VistaCatalogo(fachada).setVisible(true);
    }
}
