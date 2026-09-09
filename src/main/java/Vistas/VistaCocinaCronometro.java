package Vistas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VistaCocinaCronometro extends JDialog {

    private TiendaFacade fachada;
    private JProgressBar barraProgreso;
    private JLabel lblEstado;
    private JLabel lblCronometro;
    private JButton btnEntregar;
    private Timer temporizador;
    private int segundosTranscurridos = 0;

    public VistaCocinaCronometro(TiendaFacade fachada) {
        // Configuramos como modal para mantener la atención en la simulación de cocina
        super((Frame) null, " Cocina en Acción - Patrón State", true);
        this.fachada = fachada;
        initComponentes();
        iniciarTemporizador();
    }

    private void initComponentes() {
        setSize(450, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE); // Impide cerrar la ventana a la fuerza
        setLayout(new BorderLayout(15, 15));
        getContentPane().setBackground(Color.WHITE);

        // 1. CABECERA CON ICONO/TITULO
        JLabel lblTitulo = new JLabel(" Preparando tu Pedido en Cocina ", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(15, 10, 0, 10));
        add(lblTitulo, BorderLayout.NORTH);

        // 2. PANEL CENTRAL: ESTADOS ANIMADOS Y CRONÓMETRO
        JPanel pnlCentro = new JPanel(new GridLayout(3, 1, 10, 10));
        pnlCentro.setBackground(Color.WHITE);
        pnlCentro.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));

        lblEstado = new JLabel("Estado: En Espera...", SwingConstants.CENTER);
        lblEstado.setFont(new Font("Arial", Font.BOLD, 14));
        lblEstado.setForeground(new Color(108, 117, 125)); // Gris para la espera

        barraProgreso = new JProgressBar(0, 5);
        barraProgreso.setValue(0);
        barraProgreso.setStringPainted(false); // Progreso limpio
        barraProgreso.setForeground(new Color(220, 53, 69)); // Rojo preparación

        lblCronometro = new JLabel("Tiempo restante: 5 segundos", SwingConstants.CENTER);
        lblCronometro.setFont(new Font("Arial", Font.PLAIN, 13));

        pnlCentro.add(lblEstado);
        pnlCentro.add(barraProgreso);
        pnlCentro.add(lblCronometro);
        add(pnlCentro, BorderLayout.CENTER);

        // 3. BOTÓN INFERIOR DE ENTREGA (Inicia deshabilitado)
        btnEntregar = new JButton(" Entregar Pedido al Repartidor");
        btnEntregar.setFont(new Font("Arial", Font.BOLD, 14));
        btnEntregar.setEnabled(false); // Apagado hasta que termine la cocina
        btnEntregar.setBackground(Color.LIGHT_GRAY);
        btnEntregar.setForeground(Color.DARK_GRAY);
        btnEntregar.setPreferredSize(new Dimension(450, 50));
        btnEntregar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(btnEntregar, BorderLayout.SOUTH);

        // ACCIÓN DEL BOTÓN FINAL
        btnEntregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Al dar clic se destruye esta ventana y da paso al recibo final
                dispose();
                 VistaBoletaRecibo boletaFinal = new VistaBoletaRecibo(fachada);
                boletaFinal.setVisible(true); // Levanta el ticket de venta en la pantalla
            }
        });
    }

    private void iniciarTemporizador() {
        // El Timer de Swing ejecuta el bloque cada 1000 milisegundos (1 segundo)
        temporizador = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                segundosTranscurridos++;
                barraProgreso.setValue(segundosTranscurridos);
                
                // Calculamos y mostramos la cuenta regresiva visual
                int restante = 5 - segundosTranscurridos;
                if (restante >= 0) {
                    lblCronometro.setText("Tiempo restante: " + restante + " segundos");
                }

                // GESTIÓN DE ANIMACIÓN SECOENCIAL DEL PATRÓN STATE
                if (segundosTranscurridos >= 0 && segundosTranscurridos <= 1) {
                    lblEstado.setText("Estado: En Espera...");
                    lblEstado.setForeground(new Color(108, 117, 125));
                    
                } else if (segundosTranscurridos >= 2 && segundosTranscurridos <= 4) {
                    lblEstado.setText("Estado: Preparando en Cocina (Horneando / Cocinando)...");
                    lblEstado.setForeground(new Color(248, 108, 0)); // Naranja fuego
                    barraProgreso.setForeground(new Color(248, 108, 0));
                    
                    // LLAMADA AL BACKEND: Avanza el objeto al estado de preparación de forma interna
                    fachada.avanzarEstadoCocina(0); 
                    
                } else if (segundosTranscurridos >= 5) {
                    lblEstado.setText("Estado: ¡Listo y Empaquetado!");
                    lblEstado.setForeground(new Color(40, 167, 69)); // Verde éxito
                    barraProgreso.setForeground(new Color(40, 167, 69));
                    lblCronometro.setText("¡Pedido listo para salir!");
                    
                    // LLAMADA AL BACKEND: Finaliza el estado del producto en el backend
                    fachada.avanzarEstadoCocina(0);
                    
                    // Detenemos el reloj para que no siga sumando infinito
                    temporizador.stop(); 
                    
                    // TRANSICIÓN AUTOMÁTICA O HABILITACIÓN DEL BOTÓN
                    btnEntregar.setEnabled(true);
                    btnEntregar.setBackground(new Color(40, 167, 69));
                    btnEntregar.setForeground(Color.WHITE);
                }
            }
        });
        
        temporizador.start(); // Arranca el cronómetro automáticamente al abrir la ventana
    }
}
