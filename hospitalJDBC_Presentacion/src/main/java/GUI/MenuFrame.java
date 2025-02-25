package GUI;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class MenuFrame extends JFrame {

    private JLabel bienvenidaLabel;
    private JButton medicoButton;
    private JButton pacienteButton;

    public MenuFrame() {
        initComponents();
        initializeComponents();
    }

    private void initComponents() {
        this.setTitle("Menú Principal - Hospital");
        this.setSize(400, 300);
        this.setLayout(null);

        // Elementos
        bienvenidaLabel = new JLabel("¡Hola! Selecciona tu perfil");
        medicoButton = new JButton("Médico");
        pacienteButton = new JButton("Paciente");

        // Posiciones
        bienvenidaLabel.setBounds(100, 50, 200, 20);
        medicoButton.setBounds(100, 100, 200, 30);
        pacienteButton.setBounds(100, 150, 200, 30);

        this.add(bienvenidaLabel);
        this.add(medicoButton);
        this.add(pacienteButton);

        // Oyentes de Eventos
        medicoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarFrameMedico();
            }
        });

        pacienteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarFramePaciente();
            }
        });
    }

    private void initializeComponents() {
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private void mostrarFrameMedico() {
        this.dispose();
        new MedicoInicio().setVisible(true);
    }

    private void mostrarFramePaciente() {
        this.dispose();
        new PacienteInicio().setVisible(true);
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MenuFrame().setVisible(true);
            }
        });
    }
}