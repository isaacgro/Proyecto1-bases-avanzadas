/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;

import BO.PacienteBO;
import BO.UsuarioBO;
import Conexion.Conexion;
import DTO.PacienteDTO;
import Exception.NegocioException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 *
 * @author isaac
 */
public class PacienteInicio extends JFrame {

    private JLabel correoLabel;
    private JLabel contrasenaLabel;
    private JTextField correoField;
    private JPasswordField contrasenaField;
    private JButton aceptarButton;
    private JButton regresarButton;
    private JButton registrarButton;

    public PacienteInicio() {
        initComponents();
        initializeComponents();
    }

    private void initComponents() {
        this.setTitle("Inicio de Sesión - Paciente");
        this.setSize(400, 300);
        this.setLayout(null);

        // Elementos
        correoLabel = new JLabel("Correo electrónico:");
        contrasenaLabel = new JLabel("Contraseña:");
        correoField = new JTextField();
        contrasenaField = new JPasswordField();
        aceptarButton = new JButton("Aceptar");
        regresarButton = new JButton("Regresar");
        registrarButton = new JButton("Registrar");

        // Posiciones
        correoLabel.setBounds(50, 100, 150, 20);
        correoField.setBounds(50, 130, 300, 20);
        contrasenaLabel.setBounds(50, 170, 150, 20);
        contrasenaField.setBounds(50, 200, 300, 20);
        aceptarButton.setBounds(50, 250, 100, 30);
        regresarButton.setBounds(250, 250, 100, 30);
        registrarButton.setBounds(150, 250, 100, 30);

        this.add(correoLabel);
        this.add(correoField);
        this.add(contrasenaLabel);
        this.add(contrasenaField);
        this.add(aceptarButton);
        this.add(regresarButton);
        this.add(registrarButton);

        // Oyentes de Eventos
        aceptarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String correo = correoField.getText();
                String contraseña = new String(contrasenaField.getPassword());

                try {
                    PacienteBO pacienteBO = new PacienteBO(new Conexion());
                    PacienteDTO pacienteDTO = pacienteBO.buscarPacientePorCorreoyContra(correo, contraseña);

                    if (pacienteDTO != null) {
                        JOptionPane.showMessageDialog(null, "Inicio de sesión exitoso como paciente.");
                        dispose();
                        new PacienteMenu(pacienteDTO).setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(null, "Credenciales incorrectas.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NegocioException ex) {
                    JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        regresarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new MenuFrame().setVisible(true);
            }
        });

        registrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new RegistroPaciente().setVisible(true);
            }
        });
    }

    private void initializeComponents() {
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
