/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;

import BO.MedicoBO;
import BO.UsuarioBO;
import Conexion.Conexion;
import DTO.MedicoDTO;
import Exception.NegocioException;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

/**
 *
 * @author isaac
 */
public class MedicoInicio extends JFrame {

    private static final Logger LOGGER = Logger.getLogger(MedicoInicio.class.getName());
    private JTextField cedulaField;
    private JPasswordField contrasenaField;

    public MedicoInicio() {
        initComponents();
    }

    private void initComponents() {
        this.setTitle("Inicio de Sesión - Médico");
        this.setSize(400, 300);
        this.setLayout(new GridLayout(4, 1, 10, 10));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel cedulaLabel = new JLabel("Cédula profesional:", SwingConstants.CENTER);
        cedulaField = new JTextField();
        JLabel contrasenaLabel = new JLabel("Contraseña:", SwingConstants.CENTER);
        contrasenaField = new JPasswordField();
        JButton aceptarButton = new JButton("Aceptar");
        JButton regresarButton = new JButton("Regresar");

        this.add(cedulaLabel);
        this.add(cedulaField);
        this.add(contrasenaLabel);
        this.add(contrasenaField);
        this.add(aceptarButton);
        this.add(regresarButton);

        aceptarButton.addActionListener(e -> autenticarMedico());
        regresarButton.addActionListener(e -> {
            dispose();
            new MenuFrame().setVisible(true);
        });

        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private void autenticarMedico() {
        String cedula = cedulaField.getText().trim();
        String contrasena = new String(contrasenaField.getPassword()).trim();

        if (cedula.isEmpty() || contrasena.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debes ingresar tu cédula y contraseña.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            MedicoBO medicoBO = new MedicoBO(new Conexion());
            MedicoDTO medicoDTO = medicoBO.obtenerMedicoPorCedula(cedula);

            if (medicoDTO != null && medicoBO.autenticarMedico(cedula, contrasena)) {
                JOptionPane.showMessageDialog(this, "Inicio de sesión exitoso como médico.");
                LOGGER.log(Level.INFO, "Inicio de sesión exitoso: {0}", medicoDTO.getUsuario().getNombre());
                dispose();
                new MedicoMenu(medicoDTO).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Credenciales incorrectas.", "Error", JOptionPane.ERROR_MESSAGE);
                LOGGER.log(Level.WARNING, "Intento de inicio de sesión fallido");
            }
        } catch (NegocioException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            LOGGER.log(Level.SEVERE, "Error en autenticación", ex);
        }
    }
}
