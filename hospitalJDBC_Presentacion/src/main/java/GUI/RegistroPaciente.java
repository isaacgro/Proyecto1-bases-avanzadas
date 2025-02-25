/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;

import BO.CitaBO;
import BO.DireccionPacienteBO;
import BO.PacienteBO;
import BO.UsuarioBO;
import Conexion.Conexion;
import DTO.DireccionPacienteDTO;
import DTO.PacienteDTO;
import DTO.UsuarioDTO;
import Excepciones.PersistenciaExcption;
import Exception.NegocioException;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 * @author isaac
 */
public class RegistroPaciente extends JFrame {

    private JLabel lblNombre, lblApellidoPaterno, lblApellidoMaterno, lblFechaNacimiento, lblEdad, lblTelefono, lblCorreo, lblContrasena;
    private JLabel lblCalle, lblNumExt, lblColonia;
    private JTextField txtNombre, txtApellidoPaterno, txtApellidoMaterno, txtFechaNacimiento, txtEdad, txtTelefono, txtCorreo;
    private JTextField txtCalle, txtNumExt, txtColonia;
    private JPasswordField contrasenaField;
    private JButton btnAceptar, btnCancelar;

    public RegistroPaciente() {
        initComponents();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initComponents() {
        setTitle("Registro de Paciente");
        setSize(450, 500);
        setLayout(null);

        // Etiquetas
        lblNombre = new JLabel("Nombre:");
        lblApellidoPaterno = new JLabel("Apellido Paterno:");
        lblApellidoMaterno = new JLabel("Apellido Materno:");
        lblFechaNacimiento = new JLabel("Fecha de Nacimiento (YYYY-MM-DD):");
        lblEdad = new JLabel("Edad:");
        lblTelefono = new JLabel("Teléfono:");
        lblCorreo = new JLabel("Correo Electrónico:");
        lblContrasena = new JLabel("Contraseña:");
        lblCalle = new JLabel("Calle:");
        lblNumExt = new JLabel("Número Exterior:");
        lblColonia = new JLabel("Colonia:");

        // Campos de texto
        txtNombre = new JTextField();
        txtApellidoPaterno = new JTextField();
        txtApellidoMaterno = new JTextField();
        txtFechaNacimiento = new JTextField();
        txtEdad = new JTextField();
        txtTelefono = new JTextField();
        txtCorreo = new JTextField();
        contrasenaField = new JPasswordField();
        txtCalle = new JTextField();
        txtNumExt = new JTextField();
        txtColonia = new JTextField();

        // Botones
        btnAceptar = new JButton("Aceptar");
        btnCancelar = new JButton("Cancelar");

        // Posicionamiento
        int xLabel = 20, xField = 200, y = 20, width = 200, height = 20;
        lblNombre.setBounds(xLabel, y, 150, height);
        txtNombre.setBounds(xField, y, width, height);
        y += 30;
        lblApellidoPaterno.setBounds(xLabel, y, 150, height);
        txtApellidoPaterno.setBounds(xField, y, width, height);
        y += 30;
        lblApellidoMaterno.setBounds(xLabel, y, 150, height);
        txtApellidoMaterno.setBounds(xField, y, width, height);
        y += 30;
        lblFechaNacimiento.setBounds(xLabel, y, 180, height);
        txtFechaNacimiento.setBounds(xField, y, width, height);
        y += 30;
        lblEdad.setBounds(xLabel, y, 150, height);
        txtEdad.setBounds(xField, y, width, height);
        y += 30;
        lblTelefono.setBounds(xLabel, y, 150, height);
        txtTelefono.setBounds(xField, y, width, height);
        y += 30;
        lblCorreo.setBounds(xLabel, y, 150, height);
        txtCorreo.setBounds(xField, y, width, height);
        y += 30;
        lblContrasena.setBounds(xLabel, y, 150, height);
        contrasenaField.setBounds(xField, y, width, height);
        y += 30;
        lblCalle.setBounds(xLabel, y, 150, height);
        txtCalle.setBounds(xField, y, width, height);
        y += 30;
        lblNumExt.setBounds(xLabel, y, 150, height);
        txtNumExt.setBounds(xField, y, width, height);
        y += 30;
        lblColonia.setBounds(xLabel, y, 150, height);
        txtColonia.setBounds(xField, y, width, height);
        y += 30;
        btnAceptar.setBounds(120, y, 100, 30);
        btnCancelar.setBounds(230, y, 100, 30);

        // Añadir componentes
        add(lblNombre);
        add(txtNombre);
        add(lblApellidoPaterno);
        add(txtApellidoPaterno);
        add(lblApellidoMaterno);
        add(txtApellidoMaterno);
        add(lblFechaNacimiento);
        add(txtFechaNacimiento);
        add(lblEdad);
        add(txtEdad);
        add(lblTelefono);
        add(txtTelefono);
        add(lblCorreo);
        add(txtCorreo);
        add(lblContrasena);
        add(contrasenaField);
        add(lblCalle);
        add(txtCalle);
        add(lblNumExt);
        add(txtNumExt);
        add(lblColonia);
        add(txtColonia);
        add(btnAceptar);
        add(btnCancelar);

        // Eventos de botones
        btnAceptar.addActionListener(e -> registrarPaciente());
        btnCancelar.addActionListener(e -> limpiarCampos());
    }

    private void registrarPaciente() {
        if (validarCampos()) {
            try {
                UsuarioBO usuarioBO = new UsuarioBO(new Conexion());

                // Verificar si el correo ya está registrado
                if (usuarioBO.existeCorreo(txtCorreo.getText())) {
                    JOptionPane.showMessageDialog(this, "El correo ya está registrado. Usa otro.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                UsuarioDTO usuarioDTO = new UsuarioDTO(0, txtNombre.getText(), txtApellidoPaterno.getText(), txtApellidoMaterno.getText(), new String(contrasenaField.getPassword()));
                usuarioDTO = usuarioBO.crearUsuario(usuarioDTO);

                if (usuarioDTO.getId_Usuario() > 0) {
                    PacienteBO pacienteBO = new PacienteBO(new Conexion());
                    PacienteDTO pacienteDTO = new PacienteDTO(0, LocalDate.parse(txtFechaNacimiento.getText()), Integer.parseInt(txtEdad.getText()), txtTelefono.getText(), txtCorreo.getText(), usuarioDTO);
                    boolean pacienteCreado = pacienteBO.registrarPaciente(pacienteDTO);

                    if (pacienteCreado) {
                        DireccionPacienteBO direccionBO = new DireccionPacienteBO(new Conexion());
                        DireccionPacienteDTO direccionDTO = new DireccionPacienteDTO(0, txtCalle.getText(), txtNumExt.getText(), txtColonia.getText(), pacienteDTO);
                        direccionBO.registrarDireccion(direccionDTO);

                        JOptionPane.showMessageDialog(this, "Registro exitoso");
                        dispose();
                        new PacienteInicio().setVisible(true);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Error al crear el usuario", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NegocioException ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private boolean validarCampos() {
        // Verificar campos obligatorios
        if (txtNombre.getText().trim().isEmpty()
                || txtApellidoPaterno.getText().trim().isEmpty()
                || txtFechaNacimiento.getText().trim().isEmpty()
                || txtEdad.getText().trim().isEmpty()
                || txtTelefono.getText().trim().isEmpty()
                || txtCorreo.getText().trim().isEmpty()
                || new String(contrasenaField.getPassword()).trim().isEmpty()
                || txtCalle.getText().trim().isEmpty()
                || txtNumExt.getText().trim().isEmpty()
                || txtColonia.getText().trim().isEmpty()) {
            return false;
        }

        // Verificar formato de fecha
        String fechaTexto = txtFechaNacimiento.getText().trim();
        boolean fechaValida = false;

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            formatter.parse(fechaTexto);
            fechaValida = true;
        } catch (DateTimeParseException e) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                formatter.parse(fechaTexto);
                fechaValida = true;
            } catch (DateTimeParseException ex) {
                fechaValida = false;
            }
        }

        if (!fechaValida) {
            JOptionPane.showMessageDialog(this, "Formato de fecha incorrecto. Debe ser DD/MM/YYYY o YYYY-MM-DD", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    private void limpiarCampos() {
        txtNombre.setText("");
        txtApellidoPaterno.setText("");
        txtFechaNacimiento.setText("");
        txtEdad.setText("");
        txtTelefono.setText("");
        txtCorreo.setText("");
        contrasenaField.setText("");
        txtCalle.setText("");
        txtNumExt.setText("");
        txtColonia.setText("");
    }
}
