package GUI;

import DLL.ControladorUsuario;
import BLL.Usuario;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        try {
            ControladorUsuario controladorUsuario = new ControladorUsuario();
            ManejadorInterfazUsuario interfazUsuario = new ManejadorInterfazUsuario(controladorUsuario);

            String[] acciones = { "Iniciar Sesión", "Registrar Usuario", "Salir" };
            int opcion;

            do {
                opcion = JOptionPane.showOptionDialog(
                    null,
                    "Bienvenido a SuperJumbox",
                    "Menú Principal",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    acciones,
                    acciones[0]
                );
                switch (opcion) {
                    case 0: 
                        Usuario usuario = interfazUsuario.iniciarSesion();
                        if (usuario != null) {
                            ManejadorInterfazProducto manejador = new ManejadorInterfazProducto(usuario);
                            manejador.mostrarMenuSegunRol();
                        }
                        break;
                    case 1: 
                        interfazUsuario.registrarUsuario();
                        break;

                    default: 
                        JOptionPane.showMessageDialog(null, "Gracias por usar SuperJumbox ¡Hasta pronto!");
                        break;
                }

            } while (opcion != 2 && opcion != JOptionPane.CLOSED_OPTION);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "❌ Error en el sistema: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
