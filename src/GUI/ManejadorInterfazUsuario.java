package GUI;

import javax.swing.JOptionPane;
import DLL.ControladorUsuario;
import BLL.Usuario;
import java.sql.SQLException;


public class ManejadorInterfazUsuario {
    private ControladorUsuario controlador;

    public ManejadorInterfazUsuario(ControladorUsuario controlador) {
        this.controlador = controlador;
    }

    public Usuario iniciarSesion() {
        String nombre = JOptionPane.showInputDialog("Ingrese nombre:");
        String email = JOptionPane.showInputDialog("Ingrese email:");
        String password = JOptionPane.showInputDialog("Ingrese contraseña:");

        if (nombre == null || email == null || password == null) {
            JOptionPane.showMessageDialog(null, "Ingreso de datos incorrecto");
            return null;
        }

        try {
            Usuario usuario = controlador.login(nombre, email, password);
            if (usuario != null) {
                JOptionPane.showMessageDialog(null, "Bienvenido " + usuario.getNombre());
                return usuario;
            } else {
                JOptionPane.showMessageDialog(null, "Ingreso de datos incorrecto");
                return null;
            }
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(null, "Error de validación: " + e.getMessage());
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error de conexión: " + e.getMessage());
        }

        return null;
    }

    public void registrarUsuario() {
        String nombre = JOptionPane.showInputDialog("Ingrese su nombre:");
        String email = JOptionPane.showInputDialog("Ingrese su email:");
        String tipo = JOptionPane.showInputDialog("Ingrese tipo de usuario:");
        String password = JOptionPane.showInputDialog("Ingrese su contraseña:");

        
        if (nombre == null || email == null || tipo == null || password == null ||
            nombre.trim().isEmpty() || email.trim().isEmpty() || tipo.trim().isEmpty() || password.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Todos los datos son obligatorios");
            return;
        }

        try {
            boolean registrado = controlador.registrarUsuario(nombre.trim(), email.trim().toLowerCase(), tipo.trim(), password);

            if (registrado) {
                JOptionPane.showMessageDialog(null, "Usuario registrado con éxito: " + nombre);
            } else {
                JOptionPane.showMessageDialog(null, "Ya existe un usuario con ese nombre o mail");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al registrar usuario:\n" + e.getMessage());
            e.printStackTrace(); 
            
        }
    }
}

   